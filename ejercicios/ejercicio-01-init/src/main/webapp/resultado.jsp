<%--
  Created by IntelliJ IDEA.
  User: melol
  Date: 15/09/2026
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Demo init()</title></head>
<body>
<h1>¿Cuántas veces se llama a init()?</h1>

<p><b>Hora de inicialización del Servlet:</b> ${horaInit}</p>
<p><b>Petición número:</b> ${contador}</p>
<p><b>Petición número (atomic):</b> ${contadorAtomic}</p>
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
