# Buenas prácticas de diseño: SOLID y patrones

> **No entra en la prueba 1 (13 de octubre).** Se trabajará aplicándolo con Spring y se evaluará en la prueba práctica (RA5.g: «Se han aplicado los principios y patrones de diseño de la programación orientada a objetos»).

### Principios SOLID

| Principio | Idea |
|---|---|
| **S** · Responsabilidad única | Una clase debe tener una, y solo una, razón para cambiar |
| **O** · Abierto/cerrado | Abierta a extensión, cerrada a modificación |
| **L** · Sustitución de Liskov | Un objeto de una subclase debe poder sustituir a uno de la superclase sin romper el programa |
| **I** · Segregación de interfaces | Ningún cliente debe depender de métodos que no usa |
| **D** · Inversión de dependencias | Los módulos de alto nivel no dependen de los de bajo nivel: ambos dependen de abstracciones. Es la base de la inyección de dependencias de Spring |

### Patrones de diseño

Soluciones probadas y reutilizables para problemas que se repiten.

| Tipo | Ejemplos | Para qué |
|---|---|---|
| **De creación** | Singleton, Factory Method, Abstract Factory, Builder | Crear objetos de forma flexible |
| **Estructurales** | Adapter, Decorator, Composite, Proxy | Componer clases y objetos |
| **De comportamiento** | Observer, Strategy, Template Method, Command | Cómo interactúan los objetos |
| **De presentación web** | MVC | Organizar el recorrido petición → controlador → modelo → vista |

Recuerda: las **arquitecturas** (monolítica, microservicios, SOA…) responden a cómo se despliega o se integra el sistema; **MVC** es un patrón que organiza el código (eje 3 del apartado 3).
