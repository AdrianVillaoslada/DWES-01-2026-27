# Introducción al DWES. Arquitecturas y tecnologías de programación web

![alt text](image.png)
<center><sub><i>fuente: https://ddi-dev.com/</i></sub></center>

<img src="1760463574088.gif"/>

1. **DNS** – El navegador busca la IP del dominio: primero mira su caché, y si no la tiene, pregunta en cascada al servidor raíz → al de ".com" → al del dominio específico, hasta obtener la IP.

2. **Conexión TCP** – Se establece la conexión con el servidor mediante el "triple apretón de manos": SYN (cliente) "¿Hola, me oyes?" → SYN-ACK (servidor)"Sí te oigo, ¿y tú a mí?"   → ACK "Sí, te oigo"(cliente).
 
3. **Petición HTTP** – El navegador envía la solicitud (GET) y el servidor responde con un código de estado: 1xx (info), 2xx (éxito), 3xx (redirección), 4xx (error del cliente) o 5xx (error del servidor).

4. **Renderizado** – Con el HTML, CSS y JS recibidos, el navegador construye la página en paralelo: el HTML se convierte en el árbol DOM, el CSS en el CSSOM, y ambos se combinan en el "render tree", que luego se distribuye en pantalla (layout) y se pinta (painting).

5. **Página cargada** – El motor de renderizado y el de JavaScript trabajan juntos para mostrar la interfaz final al usuario.

## Conceptos teóricos
- [Introducción al desarrollo web](./conceptos/UT01_Introduccion_DWES.pdf)
- [JakartaEE](./conceptos/Jakarta.pdf)
    - Ver por encima, con un Servlet y una JSP básicos para entender el flujo básico **request → controlador → modelo → vista → response** (esa culturilla legacy antes de saltar directo a Spring y así entender qué hacen por debajo  sus anotaciones)."
        - Spring Boot no sustituye Jakarta EE, lo construye encima: su Dispatcher­Servlet es, en el fondo, un único Servlet.
    - [Servlets](./conceptos/Intro_Servlets.pdf)
    - [JSP](./conceptos/jsp.md)
- [Maven](./conceptos/maven.md)


## Práctica
- [Método init() de un Servlet](./ejercicios/init.md)
- [Alta usuario. Métodos GET y POST](./ejercicios/alta-usuario-profesor.md)

---
## Página principal del curso
[VOLVER PÁGINA PRINCIPAL](https://github.com/profeMelola/DWES-00-2026-27)

## Licencia

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Licencia de Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />Este obra está bajo una <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">licencia de Creative Commons Reconocimiento-NoComercial-CompartirIgual 4.0 Internacional</a>.
