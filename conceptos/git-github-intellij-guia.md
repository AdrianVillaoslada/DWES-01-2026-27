# Git y GitHub desde IntelliJ — guía paso a paso

Cómo subir tu proyecto a GitHub, mantenerlo actualizado a diario, y seguir trabajando desde otro ordenador (por ejemplo, tu portátil de casa) sin perder nada.

## 0. Antes de empezar (una vez por cada ordenador)

1. Vincula tu cuenta de GitHub en IntelliJ: `Ctrl+Alt+S` → **Version Control → GitHub** → botón **+** → **Log In via GitHub**.
2. Comprueba que el email configurado en Git coincide con uno **verificado** de tu cuenta de GitHub: **Settings → Version Control → Git**. Si no coincide, tus commits no contarán como actividad en tu perfil.

Este paso se repite en cada ordenador nuevo desde el que vayas a trabajar (también en tu portátil de casa).

```
git config --global user.name "Tu Nombre Apellido"
git config --global user.email "tu-email-verificado@ejemplo.com"
```

## 1. Activar Git en un proyecto que no lo tenía

Si el proyecto se creó sin control de versiones:

**VCS → Enable Version Control Integration…** → elige **Git**.

## 2. Añadir `.gitignore` y `.gitattributes`

Créalos en la raíz del proyecto (al lado de `pom.xml`), **antes** de hacer el primer commit.

**`.gitignore`**

```
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### IntelliJ IDEA ###
.idea/modules.xml
.idea/jarRepositories.xml
.idea/compiler.xml
.idea/libraries/
*.iws
*.iml
*.ipr

### Eclipse ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

### Mac OS ###
.DS_Store

### Windows ###
Thumbs.db

### Secretos / entornos ###
.env
application-local.properties
application-local.yml
```

**`.gitattributes`** (evita problemas de saltos de línea entre Windows y Linux/Mac)

```
* text=auto

mvnw text eol=lf
*.sh text eol=lf

mvnw.cmd text eol=crlf
*.bat text eol=crlf

*.jar binary
*.class binary
```

> Si el proyecto se generó con Spring Initializr, ya existe además un `.gitignore` dentro de la carpeta `.idea/` — no lo toques, es complementario y no hace falta cambiarlo.

## 3. Primer commit (todo el proyecto)

1. Abre la ventana de Commit.
![alt text](image-5.png)
2. Selecciona todos los archivos.
3. Escribe un mensaje descriptivo, por ejemplo `Commit inicial: estructura del proyecto`.
4. Pulsa **Commit** (todavía no *Commit and Push*, esto es solo local).

## 4. Renombrar la rama a `main` (si se llama `master`)

En la ventana de Git (parte inferior de IntelliJ), pestaña de ramas → clic derecho sobre `master` → **Rename** → escribe `main`.

## 5. Subir el proyecto como repositorio nuevo en GitHub

**Git → GitHub → Share Project on GitHub**

- Funciona igual aunque el proyecto ya tenga Git y un commit local: crea el repositorio en GitHub y sube lo que ya tienes.
- Rellena nombre del repositorio y descripción, marca **Public**, pulsa **Share**.

No hace falta usar **Manage Remotes** — esa opción sirve solo para conectar con un repositorio que **ya existe** en GitHub, no para crear uno nuevo.

## 6. Rutina diaria: commit + push

Cada día que trabajes en el proyecto:

1. Ventana de Commit.
2. Revisa los cambios y escribe un mensaje claro (nada de "cambios" o "update").
3. **Commit and Push**.
4. Comprueba en `github.com/tu-usuario` que aparece el commit.

> No uses la casilla **Amend** para los commits del día a día: sustituye el commit anterior en vez de crear uno nuevo, y no cuenta como actividad de un día distinto. Amend es solo para corregir un despiste justo después de commitear, antes de hacer push.

## 7. Trabajar desde otro ordenador (por ejemplo, tu portátil de casa)

El repositorio ya existe en GitHub — ahora toca **clonarlo** (traerte una copia) al segundo ordenador, en vez de crear uno nuevo.

1. Repite el paso 0 (cuenta de GitHub vinculada) en el portátil.
2. Abre IntelliJ → pantalla de bienvenida → **Get from VCS** (o, con un proyecto ya abierto: **File → New → Project from Version Control**).
3. Elige **GitHub**, tu cuenta, y busca el repositorio en la lista — o pega directamente la URL (botón verde "Code" en GitHub → copiar HTTPS).
4. Elige la carpeta donde guardarlo y pulsa **Clone**.

> ¿Por qué "Get from VCS" y no "Manage Remotes"? En el portátil no existe todavía ninguna carpeta con el proyecto, así que no hay nada que "gestionar" — **Manage Remotes** solo sirve para cambiar a qué repositorio de GitHub apunta un proyecto que **ya tienes en el disco**, no para descargar uno nuevo. Regla rápida: si el proyecto no existe todavía en ese ordenador → **Get from VCS** (clonar). Si ya existe en ese ordenador y solo falta enlazarlo o corregir la URL del remoto → **Manage Remotes**.

A partir de aquí, sigues la misma rutina diaria del paso 6 en este ordenador.

**Muy importante trabajando entre dos ordenadores**: antes de empezar a trabajar en cualquiera de los dos, actualiza primero con **Git → Pull** (`Ctrl+T`) para traer los cambios que hayas subido desde el otro equipo. Si no lo haces, puedes acabar con historiales distintos en cada máquina y conflictos al hacer push.
