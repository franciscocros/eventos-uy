# EventosUY – Sistema de Gestión y Venta de Entradas para Eventos

Aplicación desarrollada como tarea del Taller de Programación para la Facultad de Ingeniería (UDELAR), orientada a la gestión integral de eventos y la venta de entradas.

El sistema permite administrar organizadores, eventos, ediciones y usuarios, así como realizar la compra y consulta de entradas mediante una arquitectura distribuida compuesta por múltiples módulos.

---

## Tecnologías

- Java 21
- Jakarta Servlets
- JSP
- HTML
- CSS
- JavaScript
- Maven
- JUnit
- Apache Tomcat
- Git

---

## Arquitectura

El proyecto está dividido en tres módulos independientes:

```
EventosUy
├── ServidorCentral
├── ServidorWeb
└── ServidorMobile
```

### ServidorCentral

Contiene la lógica de negocio del sistema.

Responsabilidades:

- Gestión de usuarios y organizadores
- Gestión de instituciones y patrocinios
- Administración de eventos
- Administración de ediciones de eventos
- Venta y consulta de entradas
- Exposición de servicios mediante Web Services (SOAP)

---

### ServidorWeb

Aplicación web desarrollada con Servlets y JSP que consume los servicios del servidor central.

Incluye la interfaz de usuario para las operaciones principales del sistema.

---

### ServidorMobile

Versión adaptada para dispositivos móviles (responsive) que consume los mismos servicios del sistema.

---

## Funcionalidades

- Registro y autenticación de usuarios
- Administración de perfiles
- Gestión de eventos
- Gestión de ediciones de eventos
- Compra de entradas
- Consulta de eventos y ediciones
- Administración de categorías e instituciones
- Navegación mediante interfaz web responsive
- Comunicación entre módulos mediante Web Services

---

## Herramientas utilizadas

- Maven para la gestión de dependencias y construcción del proyecto.
- Git para control de versiones colaborativo.
- JUnit para pruebas unitarias.
- Apache Tomcat para el despliegue de las aplicaciones web.

---

## Ejecución

### Requisitos

- Java JDK 21
- Maven
- Apache Tomcat

Compilar cada módulo utilizando Maven.

```bash
mvn clean install
```

Desplegar los módulos web generados sobre Apache Tomcat.

---

## Objetivos del proyecto

Durante el desarrollo se aplicaron conceptos de:

- Arquitectura MVC
- Desarrollo de aplicaciones web en Java
- Servicios Web SOAP
- Programación orientada a objetos
- Diseño modular
- Trabajo colaborativo con Git
- Pruebas unitarias
- Gestión de dependencias con Maven

---

## Autor

Juan Francisco Cros

Proyecto desarrollado con fines académicos para Ingeniería en Computación – UDELAR.