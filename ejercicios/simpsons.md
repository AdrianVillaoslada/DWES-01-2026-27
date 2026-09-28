# Personajes de Springfield: primer contacto con el API Stream

Aplicación Jakarta EE mínima (Servlet + JSP + JSTL) que filtra y ordena personajes de Los Simpson usando **streams**.

## Cómo arrancarla

- JDK 25 · Tomcat 11 · Maven
- `mvn package` → genera `target/simpsons.war`
- Desplegar en Tomcat y abrir `http://localhost:8080/simpsons/`

## Estructura (el mismo reparto que usaremos en un futuro en Spring MVC)

```
modelo/Personaje.java              ← record con los datos de un personaje
repositorio/PersonajeRepositorio   ← la "BD": una List.of(...) en memoria
servicio/CriteriosBusqueda         ← lo que el usuario eligió en el formulario
servicio/PersonajeServicio         ← AQUÍ están todos los streams
controlador/PersonajesServlet      ← GET /personajes: lee parámetros → servicio → JSP
WEB-INF/vistas/personajes.jsp      ← formulario + tabla de resultados
```

Flujo de una petición:

```
Navegador ──GET /personajes?lugar=...&ordenarPor=edad──▶ Servlet
Servlet ──CriteriosBusqueda──▶ Servicio ──stream()...toList()──▶ List<Personaje>
Servlet ──request.setAttribute + forward──▶ JSP ──▶ tabla HTML
```

## Qué operación de stream activa cada campo del formulario

| Campo del formulario | Operación | Tipo |
|---|---|---|
| Nombre contiene, Lugar, Edad mín./máx., Solo familia Simpson | `filter(p -> ...)` | intermedia |
| Ordenar por + Descendente | `sorted(Comparator.comparing(...))`, `reversed()` | intermedia |
| Mostrar como máximo | `limit(n)` | intermedia |
| (el resultado) | `toList()` | **terminal** |
| Desplegable de lugares | `map(Personaje::lugar).distinct().sorted()` | intermedia + terminal |
| Edad media | `mapToInt(Personaje::edad).average()` | terminal → `OptionalDouble` |
| El mayor | `max(Comparator.comparingInt(...))` | terminal → `Optional` |
| Tabla "¿Dónde están?" | `collect(groupingBy(..., counting()))` | terminal → `Map` |

**Idea clave:** las operaciones *intermedias* devuelven otro stream y se pueden encadenar. La *terminal* cierra la tubería y produce el resultado. Hasta que no llega la terminal, no se ejecuta nada.

## Lo mismo sin streams (como en 1º)

```java
List<Personaje> resultado = new ArrayList<>();
for (Personaje p : personajes) {
    if (p.lugar().equals("Escuela Primaria") && p.edad() <= 12) {
        resultado.add(p);
    }
}
resultado.sort(...);   // y aún faltaría el límite...
```

```java
personajes.stream()
        .filter(p -> p.lugar().equals("Escuela Primaria"))
        .filter(p -> p.edad() <= 12)
        .sorted(Comparator.comparingInt(Personaje::edad))
        .limit(5)
        .toList();
```

## Ejercicios propuestos

1. Añadir un filtro por **ocupación** (desplegable generado con `map` + `distinct`, igual que los lugares).
2. Añadir la opción **"ordenar por longitud del nombre"** en `crearComparador`.
3. Mostrar la **suma de edades** de los resultados (`mapToInt(...).sum()`).
4. Mostrar solo los **nombres** separados por comas: `map(Personaje::nombre)` + `Collectors.joining(", ")`.
5. Comprobar con `anyMatch` si entre los resultados **hay algún menor** y mostrar un aviso.
6. Reto: agrupar por **apellido** y enseñar qué familias tienen más de un miembro.
