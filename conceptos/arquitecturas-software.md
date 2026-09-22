# Arquitecturas de software — guía de referencia

## La idea clave: son 3 ejes distintos, no una lista de opciones excluyentes

El error típico es tratar "monolítica", "cliente-servidor", "3 capas", "MVC", "microservicios", "SOA"... como si fueran alternativas de una misma pregunta. En realidad responden a **tres preguntas distintas**, y una misma aplicación tiene una respuesta en cada eje al mismo tiempo.

| Eje | Pregunta | Opciones típicas |
|---|---|---|
| **1. Despliegue físico** | ¿En cuántas unidades/procesos independientes se despliega? | Monolítica · Cliente-servidor (2 niveles) · N niveles (físico) · Microservicios · Serverless |
| **2. Organización interna del código** | ¿Cómo se reparte la lógica dentro de una unidad? | Capas lógicas (Presentación/Aplicación/Datos) · MVC |
| **3. Integración/comunicación entre sistemas** | ¿Cómo se relacionan varios sistemas distintos entre sí? | SOA · EDA · (REST no es un eje: es el protocolo que usa cualquiera de los anteriores) |

Una misma práctica puede ser, a la vez: monolítica (eje 1) + organizada en capas/MVC (eje 2). Eso no es contradictorio — son respuestas a preguntas distintas.

---

## Eje 1 — Despliegue físico

| Arquitectura | Qué resuelve | Nº de unidades desplegadas | Cuándo elegirla |
|---|---|---|---|
| **Monolítica** | Simplicidad de desarrollo y despliegue | 1 (todo junto: un WAR, un proceso) | Proyectos pequeños o equipos reducidos |
| **Cliente-servidor** | Separar quién pide y quién procesa | 2 roles (cliente y servidor), pero no dice nada de cuántas piezas tiene el servidor por dentro | Prácticamente cualquier aplicación web |
| **N niveles (físico)** | Escalar y mantener cada responsabilidad por separado | 3+ procesos/máquinas realmente independientes (presentación, aplicación, datos cada uno en su propio proceso) | Aplicaciones medianas-grandes con necesidad real de escalar por separado |
| **Microservicios** | Escalar y desplegar partes de forma independiente | Muchas (una por servicio de negocio), cada una con su propia BD | Sistemas grandes con equipos separados |
| **Serverless** | Eliminar la gestión de infraestructura | 0 gestionadas por ti (funciones que el proveedor despliega bajo demanda) | Funciones puntuales, picos de demanda variables |

**Punto importante:** "cliente-servidor" y "monolítica" no son incompatibles. Cliente-servidor solo dice que hay un cliente que pide y un servidor que responde; monolítica dice que, del lado del servidor, todo va en una sola unidad. La inmensa mayoría de apps web sencillas son **las dos cosas a la vez**.

---

## Eje 2 — Organización interna del código (dentro de una unidad)

Esto responde a "¿cómo reparto responsabilidades dentro de mi Servlet/WAR/proceso?", **no** a cuántos procesos hay.

| Vocabulario "capas" | Vocabulario MVC | Responsabilidad |
|---|---|---|
| Presentación | Vista | Mostrar datos, sin lógica de negocio |
| Aplicación | Controlador | Recibir la petición, aplicar lógica, decidir qué mostrar |
| Datos | Modelo | El estado/datos que se manejan |

Son **dos nombres para la misma idea**, no dos arquitecturas distintas.

### ⚠️ La trampa: capas lógicas ≠ niveles físicos

- **Capas lógicas**: separación de responsabilidades dentro del código, aunque todo corra en el mismo proceso. Un único Servlet+JSP con variables de estado ya está "en capas" lógicamente (Presentación=JSP, Aplicación=Servlet, Datos=variables), aunque sea monolítico.
- **Niveles físicos (3-tier real)**: presentación, aplicación y datos corriendo en **procesos/máquinas separados** (ej: navegador → Tomcat en un servidor → MySQL en otro servidor), cada uno reiniciable y escalable por separado.

Muchos diagramas de "modelo de 3 niveles" dibujan la Presentación como "Cliente/navegador" (HTML/CSS/JS ejecutándose en el propio navegador, típico de un frontend con API REST detrás). Si tu presentación se genera **en el servidor** (como una JSP), la presentación no vive físicamente en el cliente — con lo cual, aunque lógicamente sigas teniendo 3 capas, físicamente solo tienes 2 participantes (navegador + servidor), no 3.

---

## Eje 3 — Integración entre sistemas distintos

| | Qué es | Qué resuelve | Se usa cuando |
|---|---|---|---|
| **SOA** | Estilo arquitectónico: exponer funcionalidad de negocio como servicios reutilizables, normalmente coordinados por un bus (ESB) | Reutilización de servicios entre muchos sistemas de una organización | Integración entre sistemas heterogéneos, gobernanza centralizada |
| **EDA** | Estilo arquitectónico: los sistemas se comunican emitiendo/escuchando eventos, sin llamarse directamente | Desacoplamiento — un sistema no necesita saber quién más reacciona | Sistemas que reaccionan a cambios de estado en tiempo real |
| **REST** | Un **protocolo/estilo de comunicación** sobre HTTP (recursos, verbos, sin estado) | Cómo hablan dos sistemas, no cómo se organizan | Es el "idioma" que puede usar tanto SOA, como microservicios, como un simple cliente-servidor |

**SOA no es sinónimo de "servicios con API REST".** SOA se puede implementar con SOAP/WSDL (lo clásico) o con REST (lo moderno) — REST es solo el transporte. Y al revés: una API REST **no implica SOA**; un único backend monolítico que expone un endpoint REST solo para su propio frontend es cliente-servidor con REST, sin nada de SOA (no hay reutilización entre sistemas ni orquestación).

**Microservicios vs SOA:** microservicios se suele ver como una evolución de SOA — misma idea de servicios independientes, pero sin bus centralizado (ESB), cada servicio con su propia BD, despliegue totalmente autónomo. 
- gobernanza centralizada + ESB → SOA clásico; 
- autonomía total y despliegue independiente → microservicios.

---

## Ejemplo aplicado: la práctica del Servlet (init único en memoria)

| Eje | Respuesta en esta práctica | Por qué |
|---|---|---|
| Despliegue físico | **Monolítica + cliente-servidor (2 niveles)** | Todo (Servlet + JSP + estado) va en un único WAR desplegado en un único Tomcat; el navegador es el único cliente |
| Organización interna | **En capas / MVC** | `InitDemoServlet` = Controlador/Aplicación, `resultado.jsp` = Vista/Presentación, las variables de estado = Modelo/Datos |
| Integración entre sistemas | No aplica | Solo hay un sistema; SOA/EDA solo tienen sentido cuando hablas de *varios* sistemas |

No es una arquitectura de 3 niveles físicos porque no hay una base de datos en un proceso separado (son variables en memoria de la misma JVM), y la presentación no se genera en el cliente, sino en el servidor.

---

## Serverless — puntos clave

| Punto | Explicación |
|---|---|
| **No gestionas servidores** | El proveedor (AWS, Azure, Google Cloud...) se encarga de aprovisionar, escalar y mantener la infraestructura. Tú solo subes código. |
| **Unidad = función (FaaS)** | El despliegue no es una app completa, sino funciones sueltas (ej. AWS Lambda) que se ejecutan ante un evento concreto. |
| **Se ejecuta por eventos** | Una función se activa por un disparador: una petición HTTP, un archivo subido, un mensaje en una cola, un cron... No hay un proceso escuchando permanentemente. |
| **Escalado automático, incluso a cero** | Si no hay peticiones, no hay instancias corriendo (ni coste). Si hay un pico, el proveedor lanza tantas copias de la función como haga falta, en paralelo. |
| **Pago por uso real** | Se factura por invocaciones y tiempo de ejecución (ms), no por tener el servidor encendido 24/7 como en monolítica o microservicios clásicos. |
| **Stateless entre ejecuciones** | Cada invocación es independiente; no puedes guardar estado en memoria entre una llamada y la siguiente (a diferencia del Servlet, que mantenía `contadorPeticiones` en memoria). El estado va a un servicio externo (BD, almacenamiento). |
| **Cold start** | Si la función lleva tiempo sin usarse, la primera invocación tarda más porque el proveedor tiene que "arrancarla" desde cero. |

**Relación con los otros ejes:**

- Eje de **despliegue físico**: es el extremo opuesto a monolítica — ni siquiera hay "una unidad" fija desplegada por ti; son funciones efímeras que el proveedor crea y destruye según demanda.
- Se combina muy bien con **EDA**: las funciones serverless típicamente se disparan reaccionando a eventos.
- No responde a si es "cliente-servidor" o "SOA" — eso pertenece a otros ejes (comunicación / integración), no al de despliegue.

**Cuándo se elige:** tareas puntuales, picos de tráfico irregulares, procesamiento de eventos (subir una imagen → generar miniatura), APIs con tráfico muy variable donde pagar por un servidor fijo sale caro.

---

## Programación orientada a eventos vs. EDA

| | Programación orientada a eventos (Java/Spring) | EDA (Event-Driven Architecture) |
|---|---|---|
| **Dónde vive** | Dentro de **una** aplicación/proceso | Entre **varios** sistemas/servicios independientes |
| **Ejemplo típico** | `ApplicationListener`, `@EventListener` de Spring, `addActionListener` de Swing, el patrón Observer | Un microservicio de "Pedidos" publica un evento en Kafka/RabbitMQ y otros servicios (Facturación, Stock, Notificaciones) lo consumen sin conocerse entre sí |
| **A qué eje pertenece** | Eje 2 — organización interna del código (mismo grupo que MVC) | Eje 3 — integración entre sistemas (mismo grupo que SOA) |
| **Acoplamiento que evita** | Que una clase tenga que llamar directamente a otra (acopla el flujo de control dentro del programa) | Que un **servicio** tenga que conocer/llamar directamente a otro servicio (acopla sistemas completos) |
| **Necesita un bus de mensajes** | No, es un mecanismo interno (memoria) | Sí, normalmente (Kafka, RabbitMQ, SNS/SQS...) para que los sistemas se desacoplen de verdad |

**Diferencia clave:** si el ejemplo es `ApplicationEventPublisher` + `@EventListener` **dentro de una misma aplicación Spring Boot**, es programación orientada a eventos (equivalente al patrón Observer), no una arquitectura. Solo se convierte en EDA cuando **varias aplicaciones/servicios separados** se comunican a través de un broker sin llamarse directamente entre sí.

---

## Tabla resumen

| Concepto | Eje al que pertenece | Se puede combinar con... |
|---|---|---|
| Monolítica | Despliegue | Cliente-servidor, MVC, capas |
| Cliente-servidor | Despliegue (nº de roles) | Monolítica o N niveles; MVC; REST o SOAP |
| N niveles (físico) | Despliegue | MVC/capas (organización interna de cada nivel) |
| Microservicios | Despliegue | SOA/EDA para integrarlos; REST/mensajería para comunicarlos |
| Serverless | Despliegue | Suele combinarse con microservicios y EDA |
| MVC | Organización interna | Cualquier tipo de despliegue |
| Capas (Presentación/Aplicación/Datos) | Organización interna | Es el mismo patrón que MVC con otro nombre |
| SOA | Integración entre sistemas | REST o SOAP como protocolo; a veces con EDA |
| EDA | Integración entre sistemas | Microservicios, serverless, SOA |
| REST | Protocolo de comunicación | Cualquier arquitectura — no es una arquitectura en sí misma |

---

## Errores típicos a evitar

1. Decir "esto no puede ser monolítico porque tiene capas" → falso, capas es un eje distinto del despliegue.
2. Decir "esto es SOA porque usa una API REST" → falso, REST es el protocolo, no la arquitectura de integración.
3. Confundir "3 capas lógicas" con "3 niveles físicos" — lo primero es sobre organización del código, lo segundo sobre procesos/máquinas separados.
4. Asumir que la capa de presentación siempre vive en el cliente — depende de si el servidor genera el HTML (JSP, Thymeleaf...) o si el cliente lo genera con JS (SPA + API REST).
