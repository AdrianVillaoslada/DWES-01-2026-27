# Chuleta de Interfaces Funcionales y Lambdas en Java

La programación funcional en Java es un enfoque de programación que se basa en el uso de funciones y expresiones en lugar de utilizar instrucciones imperativas. 

Java 8 introdujo características de programación funcional en el lenguaje, como las expresiones lambda y la API Stream, que proporcionan herramientas para trabajar de manera más concisa y expresiva con colecciones de datos. 

Se procesa los datos como si fuera un flujo.

![alt text](image-2.png)

La API Stream de Java proporciona una serie de operaciones que se pueden realizar en un flujo de datos. 

Estas operaciones se pueden clasificar en tres categorías: 

- operaciones intermedias (Intermediate operations)
- operaciones terminales (Terminal operations)
- operaciones de cortocircuito (Short-circuit operations).

![alt text](image-3.png)


## Qué es una interfaz funcional

Una **interfaz funcional** tiene **un único método abstracto**.  

Se puede implementar con **expresiones lambda** o **referencias a métodos**.

```
@FunctionalInterface
interface Operacion {
    int aplicar(int a, int b);
}
```

## Principales interface funcionales de java.util.function

| Interface             | Método principal      | Parámetros | Devuelve        | Ejemplo                      | Uso típico                       |
| --------------------- | --------------------- | ---------- | --------------- | ---------------------------- | -------------------------------- |
| **Predicate<T>**      | `boolean test(T t)`   | 1          | `boolean`       | `p -> p.getPrecio() > 100`   | Filtrar con `filter()`           |
| **Function<T,R>**     | `R apply(T t)`        | 1          | otro tipo (`R`) | `p -> p.getNombre()`         | Transformar con `map()`          |
| **Consumer<T>**       | `void accept(T t)`    | 1          | nada            | `p -> System.out.println(p)` | Ejecutar acción (`forEach()`)    |
| **Supplier<T>**       | `T get()`             | 0          | `T`             | `() -> new Producto()`       | Proveer valores (sin entrada)    |
| **UnaryOperator<T>**  | `T apply(T t)`        | 1          | mismo tipo      | `x -> x * 2`                 | Modificar valores del mismo tipo |
| **BinaryOperator<T>** | `T apply(T t1, T t2)` | 2          | mismo tipo      | `(a,b) -> a + b`             | Combinar valores (`reduce()`)    |


## Ejemplo completo con Streams

```
productos.stream()
    .filter(p -> p.getPrecio() > 100)       // Predicate
    .map(Producto::getNombre)               // Function
    .forEach(System.out::println);          // Consumer

```

## Ejemplo con reduce

```
        // Lista de números
        List<Integer> numeros = Arrays.asList(2, 4, 6, 8, 10);

        // Usando reduce con la interfaz funcional
        int resultado = numeros.stream()
                .reduce(0, (a, b) -> a + b); // 0 es el valor inicial (identidad)

        System.out.println("La suma total es: " + resultado);

        // (((((0 + 2) + 4) + 6) + 8) + 10) = 30
```


## Resumen

| Tipo                | Función     | Método     | Ejemplo común       |
| ------------------- | ----------- | ---------- | ------------------- |
| `Predicate<T>`      | Filtrar     | `test()`   | `filter()`          |
| `Function<T,R>`     | Transformar | `apply()`  | `map()`             |
| `Consumer<T>`       | Acción      | `accept()` | `forEach()`         |
| `Supplier<T>`       | Proveer     | `get()`    | `Stream.generate()` |
| `UnaryOperator<T>`  | Modificar   | `apply()`  | `map()`             |
| `BinaryOperator<T>` | Reducir     | `apply()`  | `reduce()`          |

---

## Composición de Predicados

La interfaz `Predicate<T>` permite **combinar condiciones** de forma legible usando métodos por defecto:

| Método | Descripción | Ejemplo |
|---------|--------------|----------|
| `and()` | Verdadero si **ambos** predicados son verdaderos | `p -> p.getPrecio() > 100 && p.getPrecio() < 500` <br> → `precioMayor100.and(precioMenor500)` |
| `or()` | Verdadero si **alguno** es verdadero | `esPortatil.or(esTablet)` |
| `negate()` | Invierte el resultado (NO lógico) | `esCaro.negate()` |

---

### Ejemplo práctico

```java
Predicate<Producto> precioMayor100 = p -> p.getPrecio() > 100;
Predicate<Producto> precioMenor500 = p -> p.getPrecio() < 500;
Predicate<Producto> esBarato = precioMenor500.and(precioMayor100.negate());

productos.stream()
    .filter(precioMayor100.and(precioMenor500)) // entre 100 y 500
    .forEach(p -> System.out.println(p.getNombre()));
