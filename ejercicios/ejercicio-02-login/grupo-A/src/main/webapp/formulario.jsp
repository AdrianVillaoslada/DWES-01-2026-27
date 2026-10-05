<%--
  Created by IntelliJ IDEA.
  User: melol
  Date: 18/09/2026
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de alta</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #F7F5F0; color: #0E2438; }
        .form-card { max-width: 460px; margin: 60px auto; background: #fff; padding: 32px 36px;
            border-radius: 10px; box-shadow: 0 8px 30px rgba(0,0,0,.08); }
        label { display: block; font-weight: bold; margin: 16px 0 6px; }
        input, select { width: 100%; padding: 9px; border: 1px solid #ccc; border-radius: 6px;
            box-sizing: border-box; font-size: 1rem; }
        button { margin-top: 24px; background: #E8432A; color: #fff; border: none; padding: 12px 28px;
            border-radius: 8px; font-weight: bold; cursor: pointer; }
        button:hover { background: #c93a22; }
        .error-msg { background: #FDEDEA; color: #E8432A; border: 1px solid #E8432A;
      border-radius: 6px; padding: 10px 14px; margin-bottom: 16px; font-weight: bold; }    </style>
</head>
<body>
<div class="form-card">
    <h1>Formulario de alta</h1>

    <!-- PENDIENTE!!!! SI EL NOMBRE ESTÁ VACÍO SE MOSTRARÁ UN MENSAJE DE ERROR -->
    <% if (request.getAttribute("mensaje") != null) {%>
        <div class="error-msg">${mensaje}</div>
    <%}%>


    <form action="alta" method="post">

        <label for="nombre">Nombre</label>
        <!--<input type="text" id="nombre" name="nombre" required>-->
        <input type="text" id="nombre" name="nombre">

        <label for="email">Email</label>
<%--        <input type="email" id="email" name="email" required value="${email}">--%><!-- leer email como atributo -->
        <input type="email" id="email" name="email" required value="${param.email}">

        <label for="tecnologia">Tecnología con la que más te gustaría trabajar</label>
        <select id="tecnologia" name="tecnologia">

<%--      <%--%>
<%--        List<String> tecnologias = (List<String>) request.getAttribute("tecnologias");--%>
<%--        String tecnologiaSeleccionada = (String) request.getAttribute("tecnologia");--%>
<%--        for (String t : tecnologias) {--%>
<%--      %>--%>
<%--      <option value="<%= t %>" <%= t.equals(tecnologiaSeleccionada) ? "selected" : "" %>><%= t %></option>--%>
<%--      <%--%>
<%--        }--%>
<%--      %>--%>

            <c:forEach var="t" items="${tecnologias}">
                <option value="${t}" ${ t == tecnologia ? 'selected':''} >${t}</option>
            </c:forEach>

        </select>

        <label for="nivel">Tu nivel actual</label>
        <select id="nivel" name="nivel" multiple>
            <!-- CHUNGO EN OBSERVACIÓN PARA VER SI VÍA SCRIPTING PUEDO TENER UNA SOLUCIÓN SENCILLA -->
<%--            <%--%>
<%--                if (request.getAttribute("niveles") != null){--%>
<%--                    String[] niveles = (String[])request.getAttribute("niveles");--%>
<%--                    for (String nivel : niveles) {--%>
<%--            %>--%>
<%--                <option value="Principiante" ${'Principiante' == nivel ? 'selected':''} >Principiante</option>--%>
<%--                <option value="Intermedio" ${'Intermedio' == nivel ? 'selected':''}>Intermedio</option>--%>
<%--                <option value="Avanzado" ${'Avanzado' == nivel ? 'selected':''}>Avanzado</option>--%>
<%--            <% }// end for--%>
<%--            // Cuando niveles es null se pintan los tres options..--%>
<%--                }else{%>--%>
<%--                    <option value="Principiante" ${'Principiante' == nivel ? 'selected':''} >Principiante</option>--%>
<%--                    <option value="Intermedio" ${'Intermedio' == nivel ? 'selected':''}>Intermedio</option>--%>
<%--                    <option value="Avanzado" ${'Avanzado' == nivel ? 'selected':''}>Avanzado</option>--%>
<%--            <%}%>--%>

<%--                    <c:set var="nivelesSeleccionados" value="${fn:join(niveles, ',')}"/>--%>
<%--                    <option value="Principiante" ${ fn:contains(nivelesSeleccionados,'Principiante') ? 'selected':''} >Principiante</option>--%>
<%--                    <option value="Intermedio" ${ fn:contains(nivelesSeleccionados,'Intermedio')? 'selected':''}>Intermedio</option>--%>
<%--                    <option value="Avanzado" ${ fn:contains(nivelesSeleccionados,'Avanzado')? 'selected':''}>Avanzado</option>--%>


<%--                <option value="Principiante" ${paramValues.nivel.stream().toList().contains('Principiante')?'selected':''}>Principiante</option>--%>
<%--                <option value="Intermedio" ${paramValues.nivel.stream().toList().contains('Intermedio')?'selected':''}>Intermedio</option>--%>
<%--                <option value="Avanzado" ${paramValues.nivel.stream().toList().contains('Avanzado')?'selected':''}>Avanzado</option>--%>

            <option value="Principiante" ${niveles.contains('Principiante')?'selected':''}>Principiante</option>
            <option value="Intermedio" ${niveles.contains('Intermedio')?'selected':''}>Intermedio</option>
            <option value="Avanzado" ${niveles.contains('Avanzado')?'selected':''}>Avanzado</option>

        </select>

        <button type="submit">Enviar</button>
    </form>
</div>
</body>
</html>
