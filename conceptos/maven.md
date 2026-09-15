# Maven — conceptos básicos

## ¿Qué es Maven?

Una herramienta que automatiza la construcción de un proyecto Java: descarga las librerías que necesitas, compila el código y empaqueta la aplicación — todo con un solo comando, sin que tengas que hacerlo a mano.

**Antes de Maven**, para usar una librería (por ejemplo, la API de Servlets) tenías que:
- Buscarla y descargar el `.jar` tú mismo.
- Añadirlo al classpath del proyecto a mano.
- Repetir esto en cada máquina donde abrieras el proyecto.

**Con Maven**, simplemente describes qué necesitas en un archivo, y Maven se encarga del resto.

Maven es el gestor de construcción del proyecto: tú declaras qué necesitas en `pom.xml`, y él descarga, organiza y compila todo automáticamente.

---

## ¿Por qué aparece `pom.xml`?

`pom.xml` = **P**roject **O**bject **M**odel. Es el archivo de configuración central del proyecto — su "ficha técnica". Maven lo lee para saber qué es el proyecto, qué librerías necesita y cómo construirlo.

Es justo ese archivo el que, cuando creaste el proyecto Jakarta EE, declaró que necesitabas `jakarta.servlet-api` — por eso el import `jakarta.servlet.*` resuelve en tu Servlet sin que descargaras nada manualmente.

---

## Ejemplo mínimo comentado

```xml
<project>
    <groupId>com.instituto</groupId>       <!-- quién lo hace -->
    <artifactId>init-demo</artifactId>     <!-- nombre del proyecto -->
    <version>1.0-SNAPSHOT</version>        <!-- versión -->
    <packaging>war</packaging>             <!-- cómo se empaqueta: war = app web -->

    <dependencies>
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>        <!-- la pone el servidor, no va dentro del .war -->
        </dependency>
    </dependencies>
</project>
```

---

## Lo más importante: las dependencias

Cuando escribes un bloque `<dependency>`, le estás diciendo a Maven: *"necesito esta librería"*. Maven entonces:

1. Comprueba si ya la tienes descargada en tu repositorio local (`~/.m2`).
2. Si no, la descarga de un repositorio remoto (Maven Central).
3. La añade automáticamente al classpath del proyecto.

Es exactamente lo que ha pasado con `jakarta.servlet-api`: no la descargaste tú, lo hizo Maven al leer el `pom.xml`.

---

## ¿Y los plugins?

Un **plugin** no es una librería para tu código — es una herramienta que actúa durante la **construcción** del proyecto (compilar, empaquetar, testear...). Maven por sí solo hace muy poco: cada fase del build la ejecuta realmente un plugin por detrás.

```xml
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.4.0</version>
    </plugin>
</plugins>
```

`maven-war-plugin` es el que sabe empaquetar un proyecto web en un `.war`, colocando tus clases compiladas y `src/main/webapp` en la estructura exacta que espera un servidor como Tomcat:

```
mi-app.war
└── WEB-INF/
    ├── classes/    ← tus .class compilados
    ├── lib/        ← dependencias necesarias en runtime
    └── web.xml     ← si lo usas
```

Como ya declaraste `<packaging>war</packaging>`, Maven ya sabía que tenía que usar este plugin — lo declaras explícitamente sobre todo para fijar una **versión concreta** (build reproducible en cualquier máquina) o para **configurarlo**, por ejemplo:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.4.0</version>
    <configuration>
        <failOnMissingWebXml>false</failOnMissingWebXml>
    </configuration>
</plugin>
```

Eso le dice: "no falles si no hay `web.xml`" — útil trabajando con anotaciones (`@WebServlet`) en vez de descriptor XML.

**Dependencias vs. plugins, en una frase:** dependencias = lo que usa tu código; plugins = lo que hace Maven por ti durante la construcción.

---

## La estructura de carpetas que genera

```
proyecto/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/         → tu código .java (servlets, clases…)
    │   ├── resources/    → ficheros de configuración
    │   └── webapp/       → HTML, JSP, WEB-INF/
    └── test/
        └── java/         → tests
```

Es una estructura **estándar**: cualquiera que abra un proyecto Maven sabe dónde está cada cosa, sin tener que preguntar ni explicarlo.




