<%--
  Created by IntelliJ IDEA.
  User: melol
  Date: 16/09/2026
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ha ocurrido un error</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #0E2438; color: #fff;
            display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
        .card { background: #fff; color: #0E2438; padding: 44px 40px; border-radius: 12px;
            text-align: center; max-width: 440px; box-shadow: 0 20px 50px rgba(0,0,0,.3); }
        .error-icon { color: #E8432A; font-size: 2.2rem; }
        p.msg { color: #55606B; margin-top: 16px; }
        a { display: inline-block; margin-top: 24px; color: #E8432A; font-weight: bold; text-decoration: none; }
    </style>
</head>
<body>
<div class="card">
    <div class="error-icon">&#9888;</div>
    <h1>Vaya, algo ha fallado</h1>
    <p class="msg">${mensajeError}</p>
    <a href="index.html">&larr; Volver al inicio</a>
</div>
</body>
</html>