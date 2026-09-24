# Jakarta Server Pages (JSP)

https://jakarta.ee/specifications/pages/

## ¿Qué es JSP?

**Jakarta Server Pages (JSP)** es una tecnología basada en Java que permite crear páginas web dinámicas.  
Se ejecuta en el **servidor** (dentro de un contenedor como Tomcat, Jetty, GlassFish, etc.) y genera **HTML** que se envía al navegador del cliente.


- Combina **HTML + Java** en un mismo archivo.
- Se traduce a un **servlet Java** por el servidor de aplicaciones.
- Facilita separar la lógica de presentación del código Java.

Los archivos JSP se crean dentro de la carpeta webapp. 

---

## Estructura Básica de un JSP

Un archivo JSP suele tener la extensión `.jsp`.

Ejemplo mínimo:

```jsp
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hola JSP</title>
</head>
<body>
    <h1>¡Hola desde JSP!</h1>
    <p>La hora actual es: <%= new java.util.Date() %></p>
</body>
</html>
```

---

## Elementos clave en JSP

### Expresiones 

```
<p>El resultado de 2 + 3 es: <%= 2 + 3 %></p>
```
  
### Scriplets

```
<%
    int contador = 5;
    out.println("El contador vale: " + contador);
%>

```

### Declaraciones

```
<%! 
    int suma(int a, int b) {
        return a + b;
    }
%>

<p>La suma de 4 y 7 es: <%= suma(4, 7) %></p>

```

---

## Directivas de JSP

### Directiva page

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

```

Se pueden hacer imports por separado:

```
<%@ page import="java.util.List" %>
```

### Directiva include

```
<%@ include file="header.jsp" %>

```

### Directiva taglib

```
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

```
---


## Declaración de Objetos Implicitos: JSP proporciona objetos implícitos para interactuar con la solicitud, respuesta, sesión y contexto de aplicación:

Al igual que en los servlets desde JSP también es posible acceder a la petición request y otros objetos implícitos.

- **request:** Representa la solicitud del cliente.
- **response:** Representa la respuesta al cliente.
- **session:** Representa la sesión del usuario.
- **application:** Representa el contexto de la aplicación.
- **out:** Representa el objeto de escritura de la respuesta.
- **config:** Representa la configuración del servlet.
- **pageContext:** Proporciona un contexto de página más amplio.

Ejemplo visto en clase:

```
<a class="boton" href="${pageContext.request.contextPath}/alta">Darme de alta</a>
```

--- 

## Expresiones EL en JSP

### ${param}

- param es un mapa implícito (Map<String, String>) disponible en EL.
- Cada clave es el nombre de un parámetro del request (lo que envía un formulario o query string).

### ${paramValues}

- paramValues es otro mapa implícito (Map<String, String[]>).
- Sirve cuando un parámetro puede tener varios valores, por ejemplo en un select multiple o en varios checkboxes con el mismo name.
- Devuelve un array de Strings (String[]).


### Otros

| Expresión | Qué representa | Ejemplo |
|---|---|---|
| `${pageScope}` | Atributos guardados con alcance de página (`pageContext.setAttribute`) | `${pageScope.mensaje}` |
| `${requestScope}` | Atributos guardados en el `request` (`request.setAttribute`) | `${requestScope.usuario}` |
| `${sessionScope}` | Atributos guardados en la `session` | `${sessionScope.carrito}` |
| `${applicationScope}` | Atributos guardados en el `ServletContext` (`application`) | `${applicationScope.contador}` |
| `${header}` | Cabeceras HTTP de la petición (un solo valor por nombre) | `${header["User-Agent"]}` |
| `${headerValues}` | Cabeceras HTTP con varios valores (array de Strings) | `${headerValues["Accept"][0]}` |
| `${cookie}` | Cookies enviadas por el cliente | `${cookie.JSESSIONID.value}` |
| `${initParam}` | Parámetros de inicialización definidos en `web.xml` | `${initParam.nombreApp}` |
| `${pageContext}` | Acceso al propio objeto `PageContext` (request, response, session…) | `${pageContext.request.method}` |
| `${empty}` | Operador: comprueba si algo es `null` o está vacío (String, colección, array) | `${empty listaUsuarios}` |
| `${a == b}` / `${a eq b}` | Operadores de comparación (también `ne`, `lt`, `gt`, `le`, `ge`) | `${rol eq "admin"}` |
| `${a && b}` / `${a and b}` | Operadores lógicos (también `||`/`or`, `!`/`not`) | `${logueado && esAdmin}` |
| `${a ? b : c}` | Operador ternario | `${stock > 0 ? "Disponible" : "Agotado"}` |


---

## Buenas prácticas

- Evitar lógica compleja en JSP → usar Servlets o Beans.
- Usar JSTL y Expresiones EL (${...}) en lugar de scriptlets.
- Separar:
  - Presentación → JSP
  - Lógica de negocio → Java (servlets, servicios).
  - Usar codificación UTF-8 siempre para evitar problemas de caracteres.

---

# Ejemplos de JSTL vistos en clase

```
<a class="boton" href="<c:url value='/alta'/>">Darme de alta</a>

<select id="nivel" name="nivel" multiple>
          <c:set var="nivelesSel" value=",${fn:join(niveles, ',')},"/>
          <option value="Principiante" ${fn:contains(nivelesSel, ',Principiante,') ? 'selected' : ''}>Principiante</option>
          <option value="Intermedio" ${fn:contains(nivelesSel, ',Intermedio,') ? 'selected' : ''}>Intermedio</option>
          <option value="Avanzado" ${fn:contains(nivelesSel, ',Avanzado,') ? 'selected' : ''}>Avanzado</option>
</select>


<c:forEach var="t" items="${tecnologias}">
    <option value="${t}" ${t == tecnologia ? 'selected': ''}>${t}</option>
</c:forEach>

```