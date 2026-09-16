# Versiones de Java en entornos empresariales

Guía rápida sobre cómo elegir y gestionar versiones de JDK en proyectos reales (y por qué no da igual cuál uses).

## 1. LTS vs no LTS: la primera decisión que hay que tomar

Java saca una versión nueva cada 6 meses, pero solo algunas tienen **soporte largo (LTS)**. Las demás son versiones "puente" con soporte de solo 6 meses, pensadas para quien quiere probar features nuevas ya, **no para producción**.

| | LTS (17, 21, 25...) | No LTS (18, 19, 20, 22, 23, 24, 26, 27...) |
|---|---|---|
| Soporte | Varios años | ~6 meses, hasta la siguiente versión |
| Uso recomendado | Producción, proyectos serios | Pruebas, curiosidad, features muy nuevas |
| Soporte de librerías/herramientas | Maduro, ya probado | A veces va por detrás |

**Regla de oro:** en un proyecto empresarial (y en el aula) siempre trabajamos sobre una **LTS**.

## 2. "Usar siempre la última" no siempre es buena idea

Cuando sale una versión de Java nuevísima, el ecosistema (librerías, plugins de Maven/Gradle, procesadores de anotaciones) tarda un tiempo en ponerse al día, porque algunas de estas herramientas usan APIs internas de la JVM que cambian entre versiones.

**Ejemplo típico: Lombok.** Genera código en tiempo de compilación (getters, setters, constructores...) usando mecanismos internos del compilador. Cada vez que sale una versión de Java muy reciente, es habitual que Lombok tarde en soportarla oficialmente, y mientras tanto el build puede fallar o dar errores raros que no tienen nada que ver con tu código.

**Conclusión práctica:** antes de adoptar la última versión de Java en un proyecto, comprueba que tus dependencias clave (Lombok, el framework, los plugins de build) ya la soportan oficialmente. Si no, quédate en la LTS anterior hasta que se pongan al día.

## 3. JDK instalado ≠ "Java version" del proyecto

Esto confunde a mucha gente al empezar. Son dos cosas distintas:

| Concepto | Qué es |
|---|---|
| **JDK instalado (SDK)** | El compilador y runtime reales de tu máquina. Es quien hace el trabajo. |
| **Java version / nivel de compatibilidad** | El bytecode objetivo que le pides al compilador que genere. Es el "contrato mínimo" de tu `.jar`/`.war`. |

Puedes tener el **JDK 25 instalado** y compilar indicando **`java.version = 17`** en el `pom.xml`:

```xml
<properties>
    <java.version>17</java.version>
</properties>
```

Esto activa internamente `javac --release 17`, que hace dos cosas:

1. Genera bytecode ejecutable en cualquier JVM 17 o superior.
2. Bloquea en tiempo de compilación el uso de APIs que no existían en Java 17, aunque tu JDK sea más moderno.

**La única regla que no puedes romper:** el JDK instalado tiene que ser **igual o superior** al `java.version` indicado. Nunca al revés (no puedes compilar para 21 con un JDK 17).

Esto es lo que permite que, en un aula con gente en JDK 25, 26 o 27, todos generen exactamente el mismo bytecode objetivo si se ponen de acuerdo en el `java.version` del proyecto — el JDK físico deja de ser un problema.

## 4. Antes de elegir versión: mira el servidor y el framework, no al revés

En un proyecto real, la versión de Java **no la eliges libremente** — la marca el entorno donde vas a desplegar. Antes de tocar nada, comprueba:

- **Versión mínima de Java que exige tu servidor de aplicaciones.** Ej.: Tomcat 11.x / Jakarta EE 11 exige Java 17 como mínimo.
- **Rango de versiones de Java soportadas oficialmente por tu framework.** Ej.: Spring Boot publica en su documentación oficial un mínimo y un máximo probado (ahora mismo: mínimo 17, probado hasta 26).
- **Compatibilidad de las librerías clave** (Lombok, drivers de BD, etc.) con esa versión.

Si cualquiera de estos tres no coincide, no compila, o compila pero falla en producción de forma rara. Este chequeo de compatibilidad **antes** de fijar versiones es justo lo que se espera de un programador profesional — es un paso de checklist, no una intuición.

## 5. Resumen para el aula

- JDK instalado en las máquinas: **25** (LTS, ya soporta de sobra Tomcat 11 / Jakarta EE 11).
- `java.version` en los proyectos Spring: **21** (LTS, dentro del rango soportado por Spring Boot y por Tomcat 11).
- Nadie descarga JDKs "porque IntelliJ lo sugiere" — todos usan el mismo, instalado por el centro.
- Antes de subir la versión de Java en un proyecto (en clase o en el trabajo, el día de mañana): comprobar servidor → framework → librerías, en ese orden.
