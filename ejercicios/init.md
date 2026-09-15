# PRÁCTICA GUIADA: demostrar que init() se ejecuta una sola vez

El **servlet es un singleton** — el contenedor crea una única instancia y la reutiliza en cada petición. init() se dispara solo en ese momento (al desplegar o en la primera petición); doGet() se dispara en cada recarga.


Crea un Servlet que registre la hora en la que se ejecuta init() y cuente cuántas veces se ha invocado doGet(). Muestra esos datos en una JSP (sin JSTL, solo EL) y comprueba, recargando el navegador, que la hora nunca cambia mientras el contador sí.

## InitDemoServlet.java

```
@WebServlet("/init-demo")
public class InitDemoServlet extends HttpServlet {

    private String horaInicializacion;
    private int contadorPeticiones = 0;

    @Override
    public void init() throws ServletException {
        horaInicializacion = LocalDateTime.now().toString();
        System.out.println(">>> init() ejecutado — instancia " + this.hashCode());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        contadorPeticiones++;
        System.out.println(">>> doGet() nº " + contadorPeticiones + " — instancia " + this.hashCode());

        request.setAttribute("horaInit", horaInicializacion);
        request.setAttribute("contador", contadorPeticiones);
        request.setAttribute("instancia", this.hashCode());

        request.getRequestDispatcher("/resultado.jsp").forward(request, response);
    }
}
```

## resultado.jsp

```
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Demo init()</title></head>
<body>
    <h1>¿Cuántas veces se llama a init()?</h1>

    <p><b>Hora de inicialización del Servlet:</b> ${horaInit}</p>
    <p><b>Petición número:</b> ${contador}</p>
    <p><b>Identificador de instancia (hashCode):</b> ${instancia}</p>

    <hr>
    <p>Recarga esta página (F5) varias veces:</p>
    <ul>
        <li>La <b>hora de inicialización</b> no cambia.</li>
        <li>El <b>identificador de instancia</b> tampoco.</li>
        <li>El <b>contador</b> sube en cada recarga.</li>
    </ul>
</body>
</html>
```

--- 

## Responde a estas preguntas

- ¿Por qué la hora no cambia al recargar?
- Si guardaras un carrito de compra como atributo de instancia (en vez de HttpSession), ¿qué usuarios lo verían?

---

## Arquitectura de la práctica

### MVC aplicado a este ejemplo

| Capa MVC | Elemento | Responsabilidad |
|---|---|---|
| **Controlador** | `InitDemoServlet` | Recibe la petición, actualiza el estado y decide qué vista mostrar |
| **Vista** | `resultado.jsp` | Presenta los datos (hora de inicialización, contador) sin lógica de negocio |
| **Modelo** | `horaInicializacion`, `contadorPeticiones` | El estado/datos que se muestran |

### A nivel de arquitectura: cliente-servidor

MVC organiza el código *dentro* del servidor — es un patrón de diseño, no la arquitectura general. A nivel de arquitectura, esta práctica es **cliente-servidor**:

- **Cliente** → el navegador. Envía la petición HTTP y se limita a mostrar el HTML que recibe.
- **Servidor** → Tomcat (el contenedor de servlets). Recibe la petición, la procesa mediante el Servlet y devuelve la respuesta generada por la JSP.