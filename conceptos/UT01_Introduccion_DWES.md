# UT01 · Introducción al desarrollo web en entorno servidor

Aplicaciones web, HTTP, servidores, APIs y arquitecturas: los cimientos antes de entrar en Jakarta EE y Spring Boot.

## Índice

1. [Aplicaciones web](#1-aplicaciones-web)
2. [El protocolo HTTP](#2-el-protocolo-http)
3. [Servidores web y servidores de aplicaciones](#3-servidores-web-y-servidores-de-aplicaciones)
4. [Servicios web y APIs](#4-servicios-web-y-apis)
5. [Arquitecturas de software](#5-arquitecturas-de-software)
6. [Este módulo en la práctica](#6-este-módulo-en-la-práctica)
7. [Errores típicos a evitar](#7-errores-típicos-a-evitar)

Material relacionado: [SPA vs MPA](spa-vs-mpa-esquema.md) · [Qué pasa al escribir una URL](url.md) · [Aclaración Jakarta EE](aclaracion-jakarta-ee.md) · [Práctica del init](../ejercicios/init.md)

---

## 1. Aplicaciones web

### Aplicación de escritorio vs. aplicación web

| | Aplicación de escritorio | Aplicación web |
|---|---|---|
| **Instalación** | En cada equipo; puede haber problemas de compatibilidad con el hardware o el sistema operativo | Solo en el servidor; el cliente no instala nada |
| **Acceso** | Desde el equipo donde está instalada | Cualquier usuario con conexión y un navegador |
| **Red** | Solo la necesita si la base de datos no es local | Siempre |
| **Interfaz** | Depende del lenguaje y del sistema operativo | Siempre HTML, CSS y JavaScript (y sus frameworks) |

### Página web vs. aplicación web

| | Qué es | ¿Necesita servidor? |
|---|---|---|
| **Página web estática** | Documento cuyo contenido no cambia salvo que alguien lo edite a mano | No necesariamente: puede verse incluso sin servidor |
| **Página web dinámica** | Su contenido cambia según el usuario, sus acciones o los datos del servidor | Sí |
| **Aplicación web** | Software más complejo que combina páginas dinámicas (y estáticas) con otras tecnologías; se parece a una aplicación de escritorio pero se ejecuta en el navegador | Siempre: servidor web y, normalmente, servidor de aplicaciones y base de datos |

### Ventajas de la generación dinámica de páginas

- **Una plantilla, muchos contenidos:** una sola plantilla muestra cualquier artículo, producto o perfil a partir de sus datos, sin crear un HTML a mano para cada uno.
- **Contenido personalizado:** lo que se muestra depende del usuario (por ejemplo, «Hola, Laura» y sus pedidos). Esto es imposible con páginas estáticas.
- **Datos siempre actualizados:** la página se construye con la información del servidor en el momento de la petición.

Cuando el HTML se genera **en el servidor** (aplicación **MPA**, como Jakarta EE con JSP o Spring MVC con Thymeleaf), además:

- el HTML llega completo y listo para pintar (bueno para el SEO y para la primera carga);
- el estado de la sesión vive en el servidor, más fácil de controlar y auditar. Por eso es habitual en aplicaciones de banca, seguros o la Administración.

La comparación completa entre SPA y MPA está en [SPA vs MPA](spa-vs-mpa-esquema.md).

### Front-end vs. back-end: dónde se ejecuta cada cosa

| | Front-end (cliente) | Back-end (servidor) |
|---|---|---|
| **Dónde se ejecuta** | En el navegador del usuario | En el servidor web o de aplicaciones |
| **Tecnologías** | HTML y CSS (estructura y estilo), JavaScript (interactividad); React, Vue, Angular para SPA | Java (Servlets/JSP, Spring), PHP, Python, .NET, Ruby… |
| **Qué hace** | Animaciones, validaciones de formulario inmediatas, actualizar contenido sin recargar (AJAX) | Lógica de negocio, acceso a bases de datos, generación de contenido dinámico y APIs, seguridad |

**Regla práctica:** lo que se puede hacer sin preguntar al servidor (mostrar un menú, comprobar el formato de un email) puede ejecutarse en el cliente. Lo que necesita datos o reglas de negocio (comprobar usuario y contraseña en la base de datos) **tiene que** ejecutarse en el servidor. Una validación en el cliente nunca sustituye a la del servidor.

### Perfiles profesionales

| Perfil | Qué hace |
|---|---|
| **Front-end developer** | Diseño y maquetación con HTML, CSS y JavaScript (y sus frameworks); presentación en cualquier dispositivo |
| **Back-end developer** | Lógica de negocio, acceso a datos y administración del servidor. Java (Servlets/JSP, Spring), PHP, Python, ASP.NET, Ruby… |
| **Full-stack developer** | Conoce ambas partes sin ser necesariamente experto en una sola tecnología |

### El desarrollo web actual: *time to market*

No basta con que la aplicación funcione: también importa ponerla en manos de los usuarios cuanto antes (**time to market**), garantizando accesibilidad, estabilidad, escalabilidad y seguridad. Por eso el despliegue es parte del desarrollo, no un paso aparte.

---

## 2. El protocolo HTTP

Es el idioma en el que hablan cliente y servidor. Todo lo que viene después (servidores, APIs, arquitecturas) se apoya en él.

### Qué es HTTP y qué añade HTTPS

| | HTTP | HTTPS |
|---|---|---|
| **Qué es** | Protocolo de transferencia de hipertexto para la comunicación entre cliente y servidor | HTTP cifrado con SSL/TLS |
| **Clave** | **Sin estado:** cada petición es independiente; la sesión la crea la propia aplicación (cookies, tokens…) | Garantiza confidencialidad, integridad y autenticación. Sin cifrar, los datos viajan en texto plano y son vulnerables a ataques *man-in-the-middle* |

### Anatomía de una petición y una respuesta

| Parte | Qué contiene | Ejemplo |
|---|---|---|
| **Línea inicial** | Petición: método + ruta + versión. Respuesta: versión + código + texto | `POST /?id=1 HTTP/1.1` · `HTTP/1.1 200 OK` |
| **Cabeceras** | Metadatos de la petición o la respuesta | `Host: miweb.com` · `Content-Type: application/json` |
| **Cuerpo** | Datos enviados (POST/PUT) o recurso devuelto | `{ "usuario": "pepe" }` |

### Métodos HTTP

| Método | Para qué se usa |
|---|---|
| **GET** | Obtener un recurso. Los parámetros van en la URL (*query string*) |
| **POST** | Crear un recurso o enviar datos. Los datos van en el cuerpo, no se ven en la URL |
| **PUT** | Reemplazar por completo un recurso existente |
| **PATCH** | Modificar parcialmente un recurso |
| **DELETE** | Borrar un recurso |
| **HEAD** | Igual que GET pero sin cuerpo; sirve para comprobar si un recurso existe |

### Códigos de estado

| Familia | Significado | Ejemplos |
|---|---|---|
| **1XX** | Informativa: la petición se recibió y el proceso continúa | — |
| **2XX** | Éxito | 200 OK |
| **3XX** | Redirección: hace falta otra acción, el recurso se ha movido | 301, 302 |
| **4XX** | Error del cliente | 403 Forbidden · 404 Not Found · 405 Method Not Allowed |
| **5XX** | Error del servidor | 500 Internal Server Error |

### Cabeceras habituales

| En la petición | En la respuesta |
|---|---|
| **Accept:** formato en que el cliente quiere los datos | **Content-Type:** formato de los datos devueltos |
| **Host:** dominio al que va la petición | **Content-Length:** tamaño en bytes |
| **Content-Type:** formato de los datos enviados en el cuerpo | **Cache-Control:** cuánto tiempo se pueden cachear |
| **User-Agent:** información del navegador | **Server:** software del servidor |

### De una URL a un recurso del servidor

`http://www.miweb.com:80/dir1/a.html` = protocolo + dominio + puerto + recurso. El servidor web escucha por defecto en el puerto **80** (**443** con HTTPS). Si no se indica un archivo, sirve el `index.html` del directorio.

| Enlace absoluto | Enlace relativo |
|---|---|
| `<a href="http://miweb.com/a.html">`: URL completa; siempre lleva a esa web | `<a href="dir2/a.html">`: solo la ruta; se busca en el mismo servidor y directorio que la página actual |

Lo que ocurre paso a paso al escribir una URL (DNS, TCP, HTTP, renderizado) está en [url.md](url.md).

---

## 3. Servidores web y servidores de aplicaciones

| | Servidor web | Servidor de aplicaciones |
|---|---|---|
| **Qué hace** | Recibe peticiones HTTP/HTTPS y devuelve recursos **estáticos** (HTML, CSS, imágenes, JavaScript) | Proporciona un **entorno de ejecución** para aplicaciones: ejecuta código del servidor, procesa peticiones dinámicas (validar un login, generar un informe) y gestiona la lógica de negocio y el acceso a datos |
| **Ejemplos** | Apache HTTP Server, Nginx, Microsoft IIS | Apache Tomcat (contenedor de servlets), WildFly, GlassFish, Payara |
| **¿Puede ejecutar una aplicación Jakarta EE?** | No: no implementa la API de Servlet | Sí, si implementa las especificaciones que usa la aplicación |

### Cómo se integran

Es habitual poner un **servidor web delante del servidor de aplicaciones** como **proxy inverso**: Nginx o Apache reciben todas las peticiones, sirven directamente los recursos estáticos y reenvían las dinámicas a Tomcat.

### Los servidores en el ecosistema Jakarta EE

- **Tomcat** es un **contenedor web**: implementa solo algunas especificaciones (Servlet, JSP, Expression Language y WebSocket). **JSTL no viene incluido**: hay que añadirlo como dependencia.
- **WildFly, GlassFish, Payara, Open Liberty…** implementan el perfil completo de Jakarta EE (CDI, JPA, EJB…). GlassFish es la implementación de referencia.
- **Spring Boot** incluye un Tomcat **embebido**: la aplicación arranca con su propio servidor, sin instalarlo aparte.
- **Node.js** es un entorno de ejecución de JavaScript en el servidor; sirve para comparar cómo distintos lenguajes resuelven el mismo problema.

Quién define la API y quién la implementa (Eclipse Foundation, servidor, `scope provided`…) está explicado en [Aclaración Jakarta EE](aclaracion-jakarta-ee.md).

---

## 4. Servicios web y APIs

Hasta ahora el servidor devolvía **páginas** para personas. También puede devolver **datos** para otras aplicaciones.

### Qué es un servicio web (API)

Un conjunto de reglas y protocolos que permite a **otra aplicación** comunicarse de forma remota para usar un servicio. Cada funcionalidad tiene un **endpoint** (una URL) y un mismo servicio puede tener varios clientes.

| Página web dinámica | Servicio web (API) |
|---|---|
| Genera **HTML** para que lo vea una **persona** en el navegador | Expone **datos y funcionalidades** para que los consuman **otras aplicaciones**, normalmente en **JSON** |

### Tendencia: el back-end como servicio universal

El servidor expone su funcionalidad a través de una **API** que cualquier cliente puede consumir: una SPA, una app móvil, una aplicación de escritorio u otro sistema. El back-end ya no está ligado a una interfaz concreta: devuelve datos en formatos estándar (**JSON**, XML) y cada cliente los representa a su manera.

### Tipos de API más usados

| Tipo | Qué es |
|---|---|
| **REST** | Usa los métodos HTTP sobre recursos identificados por URLs; intercambia normalmente JSON. Es el que usaremos en el módulo |
| **GraphQL** | Lenguaje de consulta: el cliente pide exactamente los datos que necesita, ni más ni menos |
| **WebSocket** | Conexión persistente y bidireccional para tiempo real (chats, notificaciones) |

Una API **no es una arquitectura**: es la forma en que una aplicación se deja usar desde fuera. Puede tenerla una aplicación monolítica, cada microservicio o un servicio SOA (lo verás en el apartado siguiente).

---

## 5. Arquitecturas de software

### La idea clave: 4 ejes

El error típico es tratar «monolítica», «cliente-servidor», «3 capas», «MVC», «microservicios», «SOA»… como si fueran alternativas de una misma pregunta.

En realidad responden a **preguntas distintas**, y una misma aplicación tiene una respuesta para cada una **al mismo tiempo**.

| Eje | Pregunta | Opciones típicas |
|---|---|---|
| **1. Comunicación** | ¿Quién pide y quién responde? | **Cliente-servidor** (prácticamente toda aplicación web) |
| **2. Despliegue** | ¿En cuántas unidades se despliega la aplicación? ¿En cuántos niveles físicos se reparte? | Unidades: **monolítica** · **microservicios** · **serverless**<br>Niveles: **2** · **3** · **N niveles** |
| **3. Organización interna del código** | ¿Cómo se reparten las responsabilidades dentro del código? | **Capas lógicas** (presentación / negocio / acceso a datos) · patrón **MVC** |
| **4. Integración entre sistemas** | ¿Cómo se relacionan varios sistemas distintos? | **SOA** · **EDA** |

**REST no es ningún eje:** es un estilo de comunicación sobre HTTP que puede usar cualquiera de las arquitecturas.

**No hay una arquitectura «perfecta»:** la elección depende de los requisitos de cada proyecto.

### Vocabulario: capa ≠ nivel

| Término | Qué es | Pregunta a la que responde |
|---|---|---|
| **Capa** (*layer*) | Separación **lógica** del código por responsabilidades | ¿Qué parte del código hace cada cosa? |
| **Nivel** (*tier*) | Separación **física**: procesos o máquinas distintos | ¿Dónde se ejecuta cada parte? |

Varias capas lógicas pueden ejecutarse en un mismo nivel físico. Una aplicación con 3 capas lógicas puede estar desplegada en 1, 2 o 3 niveles. En este módulo, «capa» siempre es lógica y «nivel» siempre es físico: no hablamos de «capas físicas».

### Vocabulario: la palabra «servicio» significa cuatro cosas

| Cuando decimos… | Nos referimos a… | Eje |
|---|---|---|
| **Servicio web / API** | Lo que un servidor expone para que lo usen otras aplicaciones (endpoints) | No es un eje: es cómo se usa una aplicación desde fuera (apartado 4) |
| **Microservicio** | Una **unidad de despliegue** pequeña e independiente, con su propia BD. Normalmente expone una API | 2. Despliegue |
| **Servicio SOA** | Una funcionalidad de negocio que **varios sistemas** de una organización reutilizan | 4. Integración |
| **Servicio (`@Service`)** | Una **clase** de la capa de negocio dentro del código | 3. Organización interna |

### Eje 1 · Comunicación: cliente-servidor

Un **cliente** (navegador, app) envía una **petición** (GET, POST…) y un **servidor** la procesa y devuelve una **respuesta** (HTML, JSON, un código de estado…). La interfaz está en el cliente; la funcionalidad, en el servidor. Es la base de todas las aplicaciones web.

**Ventajas:**

- **Control centralizado:** el servidor gestiona accesos, recursos e integridad de los datos.
- **Escalabilidad:** cliente y servidor crecen por separado.
- **Portabilidad:** el navegador independiza la aplicación del sistema operativo.
- **Mantenimiento sencillo:** se actualiza el servidor sin tocar los clientes.
- **Seguridad:** los datos sensibles permanecen en el servidor.

**Cliente-servidor no dice nada de cómo es el servidor por dentro.** Por eso **no es incompatible con monolítica**: cliente-servidor dice quién habla con quién; monolítica dice cuántas unidades se despliegan en el servidor. La inmensa mayoría de aplicaciones web sencillas son las dos cosas a la vez.

### Eje 2 · Despliegue

#### 2a. ¿En cuántas unidades se despliega la aplicación?

| Arquitectura | Qué es | Unidades | Cuándo elegirla |
|---|---|---|---|
| **Monolítica** | Interfaz, lógica de negocio y acceso a datos en un solo bloque que se ejecuta en un mismo proceso | 1 (un WAR, un proceso) | Proyectos pequeños o equipos reducidos. Fácil de desarrollar y desplegar; difícil de mantener al crecer, y escalar implica escalar todo el bloque |
| **Microservicios** | La aplicación se divide en servicios pequeños y autónomos, cada uno centrado en una tarea, con su propia BD y desplegado por separado | Muchas | Sistemas grandes con equipos separados. Ejemplo: **Netflix**, con buena parte de sus servicios en Spring Boot |
| **Serverless** | El proveedor cloud gestiona toda la infraestructura; el equipo solo escribe funciones | Ninguna gestionada por ti | Funciones puntuales y picos de demanda variables. Escalado automático y pago por uso |

#### 2b. ¿En cuántos niveles físicos se reparte?

| Niveles | Ejemplo |
|---|---|
| **2 niveles** | Navegador → Tomcat, con los datos en memoria o en un fichero del propio servidor |
| **3 niveles** | Navegador → Tomcat → MySQL en otra máquina |
| **N niveles** | Navegador → balanceador → varios Tomcat → caché → BD |

**Monolítica y 3 niveles son compatibles:** un único WAR en Tomcat que usa un MySQL en otra máquina es monolítico y está desplegado en 3 niveles.

### Eje 3 · Organización interna del código: capas lógicas y MVC

#### Capas lógicas

| Capa | Responsabilidad | En Spring |
|---|---|---|
| **Presentación** | Recibir la petición y mostrar el resultado | `@Controller` + plantilla Thymeleaf |
| **Lógica de negocio** | Reglas del negocio: validar stock, calcular descuentos… | `@Service` |
| **Acceso a datos** (persistencia) | Guardar y recuperar información | `@Repository` |

Cada capa solo habla con la **adyacente**, a través de interfaces bien definidas. Así se puede cambiar una capa sin afectar a las demás.

La **base de datos** (PostgreSQL, MongoDB…) **no es una capa del código**: es el sistema donde la capa de acceso a datos guarda la información. Si está en otra máquina, es otro **nivel físico** (eje 2b).

#### MVC: un patrón de diseño, no una arquitectura

**MVC (Modelo-Vista-Controlador)** es un **patrón de diseño** para organizar la parte web de una aplicación, es decir, el recorrido petición → respuesta. Vive dentro de la **capa de presentación**.

| Pieza | Qué hace | En Jakarta EE | En Spring |
|---|---|---|---|
| **Controlador** | Recibe la petición, pide los datos y decide qué vista mostrar | Servlet | `@Controller` |
| **Modelo** | Los datos que se van a mostrar | Atributos del request (`setAttribute`) | Objeto `Model` |
| **Vista** | Presenta los datos, sin lógica de negocio | JSP | Plantilla Thymeleaf |

Spring Web MVC toma su nombre de este patrón.

#### Ejemplo: la práctica de los Simpson

El recorrido de una petición:

```
Navegador ──GET /personajes?lugar=...──▶ PersonajesServlet      (presentación · Controlador)
                                            │ llama a
                                            ▼
                                         PersonajeServicio      (negocio: filtra, ordena)
                                            │ llama a
                                            ▼
                                         PersonajeRepositorio   (acceso a datos: obtiene todos)
                                            │ devuelve List<Personaje>   ← entidades
                                            ▼
                     servlet: request.setAttribute("personajes", lista)  ← Modelo de MVC
                                            │ forward
                                            ▼
Navegador ◀──────────── HTML ────────────  personajes.jsp       (presentación · Vista)
```


**Regla para no liarse:** el controlador no filtra ni calcula (eso es negocio) y el servicio no sabe nada de `request`, JSP ni HTML (eso es presentación). Si mañana los personajes vienen de MySQL, solo cambia el repositorio.

En Spring será exactamente igual: `@Controller` + Thymeleaf (presentación), `@Service` (negocio), `@Repository` (acceso a datos), y las entidades serán clases con `@Entity`.

### Eje 4 · Integración entre sistemas

| | Qué es | Qué resuelve |
|---|---|---|
| **SOA** (*Service-Oriented Architecture*) | La funcionalidad de negocio se expone como **servicios reutilizables** con interfaces bien definidas, normalmente coordinados por un bus (ESB) | Reutilizar servicios e integrar muchos sistemas distintos de una organización |
| **EDA** (*Event-Driven Architecture*) | Los sistemas se comunican **emitiendo y escuchando eventos**, sin llamarse directamente | Desacoplar sistemas; muy útil en sistemas distribuidos con comunicación asíncrona |

**SOA no es «tener una API REST».** SOA puede implementarse con SOAP (lo clásico) o con REST (lo moderno). Y un back-end monolítico que expone una API REST para su propio front-end es cliente-servidor con REST, no SOA.

**SOA vs. microservicios:** SOA integra los sistemas de **toda una organización**; los microservicios dividen **una aplicación** en piezas pequeñas, cada una dueña de su BD.

### Ejemplo aplicado: la práctica del init

| Eje | Pregunta | Respuesta | Por qué |
|---|---|---|---|
| 1. Comunicación | ¿Quién habla con quién? | **Cliente-servidor** | El navegador envía la petición HTTP; Tomcat la procesa y responde |
| 2a. Despliegue | ¿Cuántas unidades? | **Monolítica** | Servlet + JSP + estado en un único WAR, en un único Tomcat |
| 2b. Despliegue | ¿Cuántos niveles físicos? | **2 niveles** | Navegador + Tomcat; los datos son variables en memoria de la misma JVM |
| 3. Organización interna | ¿Cómo se organiza el código? | **MVC** | `InitDemoServlet` = Controlador, `resultado.jsp` = Vista, variables de estado = Modelo |
| 4. Integración | ¿Se relaciona con otros sistemas? | **No aplica** | Solo hay un sistema |

**¿Y si cambiamos la práctica?**

- Guardamos los datos en un MySQL en otra máquina → sigue siendo **monolítica**, pero pasa a **3 niveles**.
- Separamos el código en Servlet → Servicio → Repositorio → sigue siendo monolítica, ahora **con capas lógicas** además de MVC.
- Dividimos la aplicación en un servicio de usuarios y otro de pedidos, cada uno con su WAR y su BD → **microservicios**.

---

## 6. Este módulo en la práctica

| | Qué usaremos |
|---|---|
| **Lenguaje** | Java |
| **Base** | Jakarta EE (Servlet, JSP): lo que hay debajo de Spring |
| **Framework** | Spring y Spring Boot. Spring no sustituye a Jakarta EE: lo usa por debajo, y su `DispatcherServlet` es un servlet |
| **Servidor** | Tomcat (externo con Jakarta EE; embebido con Spring Boot) |
| **Organización del código** | MVC + capas: controladores, servicios y repositorios |
| **APIs** | REST con JSON, con endpoints que responden a GET, POST, PUT y DELETE |
| **Buenas prácticas** | SOLID y patrones aparecerán de forma natural al escribir servicios con Spring |

---

## 7. Errores típicos a evitar

1. Decir «es cliente-servidor, así que no puede ser monolítica» → falso: responden a preguntas distintas (quién habla con quién / cuántas unidades se despliegan).
2. Decir «no puede ser monolítica porque tiene capas» → falso: las capas son organización del código, no despliegue.
3. Decir «capas y MVC son lo mismo» → falso: MVC es un patrón de la parte web y vive dentro de la capa de presentación.
4. Confundir 3 capas lógicas con 3 niveles físicos → las capas son código; los niveles, procesos o máquinas.
5. Pensar que monolítica implica 2 niveles → un único WAR con la BD en otra máquina es monolítico y de 3 niveles.
6. Decir «es SOA porque tiene una API REST» → falso: REST es el estilo de comunicación, no la arquitectura de integración.
7. Confundir «servicio web», «microservicio», «servicio SOA» y la clase `@Service` → son cuatro cosas distintas.
8. Asumir que la presentación siempre vive en el cliente → depende de si el HTML lo genera el servidor (JSP, Thymeleaf) o el navegador (SPA + API REST).
9. Pensar que un servidor web como Nginx puede ejecutar servlets → hace falta un contenedor o servidor de aplicaciones que implemente la API.
10. Confiar solo en la validación del cliente → la del servidor es obligatoria.
