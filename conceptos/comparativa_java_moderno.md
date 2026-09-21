# Comparativa de Paradigmas: Java Clásico (1º) vs. Java Moderno en Spring (2º)

Esta guía sirve como referencia para la transición del estilo de programación imperativo aprendido en el primer curso hacia un enfoque declarativo y moderno aplicado al desarrollo backend con Spring.

## Tabla Comparativa

| Concepto | En 1º Curso (Imperativo / Clásico) | En 2º Curso (Declarativo / Funcional en Spring) |
| :--- | :--- | :--- |
| **Paradigma** | **Imperativo**: Decimos paso a paso las instrucciones al servidor. | **Declarativo**: Expresamos la lógica de negocio de forma fluida. |
| **Modelado de DTOs** | Clases tradicionales con atributos privados, constructores interminables y métodos getter/setter (o uso de Lombok). | **Records**: Modelos de datos compactos, portadores de datos puros e inmutables por defecto definidos en una sola línea. |
| **Filtrado / Transformación** | Control de flujo manual mediante bucles `for`/`foreach`, acumuladores `List.add()` y condicionales `if`. | **Stream API**: Procesamiento a través de tuberías fluidas de datos empleando operadores como `.filter()`, `.map()` y `.toList()`. |
| **Paso de funciones** | Instanciación de clases anónimas, interfaces pesadas o herencia polimórfica compleja. | **Expresiones Lambda / Referencias a métodos**: Capacidad de pasar comportamiento y lógica por parámetro de forma directa (`::`). |

---

## Ejemplo Práctico: Transformación de Datos en un Endpoint de Spring

A continuación se muestra el contraste entre resolver un problema común de backend (filtrar usuarios activos y convertirlos a DTOs) utilizando la lógica estructurada tradicional frente al estándar de desarrollo actual de segundo curso.

### Supuesto de partida (El DTO como Record)
```java
// Definición del DTO moderno en Java 2º Curso
public record UserDTO(String username, String email) {}
```

### 1. Enfoque de 1º Curso: Estilo Imperativo (Bucles `for` e `if`)
Este enfoque se centra en el **cómo** realizar la tarea paso a paso, manteniendo variables mutables y gestionando manualmente la colección de destino.

```java
// Código largo, propenso a errores y con mutabilidad innecesaria
List<User> users = userRepository.findAll();
List<UserDTO> dtos = new ArrayList<>();

for (User u : users) {
    if (u.isActive()) {
        dtos.add(new UserDTO(u.getUsername(), u.getEmail()));
    }
}

return dtos;
```

### 2. Enfoque de 2º Curso: Estilo Declarativo (Stream API + Programación Funcional)
Este enfoque se centra en el **qué** se quiere conseguir. El flujo de datos es inmutable, legible y reduce drásticamente el código boilerplate.

```java
// Código declarativo, inmutable y limpio utilizando Java Moderno
return userRepository.findAll().stream()
        .filter(User::isActive)
        .map(u -> new UserDTO(u.getUsername(), u.getEmail()))
        .toList();
```
