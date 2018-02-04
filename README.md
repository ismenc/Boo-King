# Boo-King


Boo-King es una aplicación de préstamo de libros entre **personas** que permite almacenar datos de libros, arrendadores y préstamos en una base de datos MySQL. La aplicación nos permitirá manipular estos datos de una forma mucho más *user-friendly*. Los receptores de préstamos se han obviado para simplificar un poco el proyecto :sweat_smile:.
> No ha sido un proyecto fácil y, aunque el resultado no es atractivo visualmente, se han aprendido muchas cosas y ha merecido la pena :dancer:.

* Navegación
  * [Repositorio GitHub](https://github.com/ismenc/Boo-King)
  * [Página del proyecto](https://ismenc.github.io/Boo-King/)
  * [Historial de versiones](https://github.com/ismenc/Boo-King/network)
  * [Descargas](https://github.com/ismenc/Boo-King/releases)
  * [Tareas y mejoras propuestas](https://github.com/ismenc/Boo-King/issues)

- - - -

## Índice

* Índice de contenidos
  * [Funcionalidad](#funcionamiento-de-la-aplicación)
    * [Base de datos](#base-de-datos-mysql)
    * [Aplicación Java](#aplicación-java)
    * [Hibernate](#hibernate)
  * [Documentación](#documentación)
    * [Manual del proyecto](#manual-del-proyecto)
    * [Documentación JavaDoc](#documentación-javadoc)
    * [Historial de versiones](#historial-de-versiones)
  * [Cómo ejecutar la aplicación](#cómo-ejecutar-la-aplicación)
    * [Base de datos](#base-de-datos)
    * [Lanzar aplicación Java](#lanzar-aplicación-java)
  * [Cumplimiento de objetivos](#objetivos)
    * [Acceso a datos](#acceso-a-datos)
    * [Desarrollo de interfaces](#desarrollo-de-interfaces)
  * [Desarrollo](#especificaciones-de-desarrollo)
    * [Acceso a datos](#acceso-a-datos-1)
    * [Desarrollo de interfaces](#desarrollo-de-interfaces-1)

- - - -


## Funcionamiento de la aplicación

Nuestra aplicación ofrece al usuario un menú con las siguientes opciones:
```
+------------ MENÚ -------------+
|            Básico             |
|-------------------------------|
| [1] Insertar dato             |
| [2] Actualizar dato           |
| [3] Borrar dato               |
| [4] Consultar objet           |
|-------------------------------|
|           Avanzado            |
|-------------------------------|
| [5] Listar arrendadores nombre|
| [6] Listar prestamos nombre   |
| [7] Préstamos en un año       |
| [8] Estadísticas globales     |
| [9] Salir                     |
+-------------------------------+
```

Tras elegir una opción, el programa interactuará con el usuario para pedirle los datos necesarios para operar.

### Base de datos MySQL

El servidor *MySQL* será responsable de **almacenar y establecer las relaciones** entre los datos de nuestra aplicación.
> La base de datos necesaria para la aplicación se encuentra en el archivo [BaseDatos.sql](./BaseDatos.sql).

En nuestro caso nos hemos servido de PHPMyAdmin para crear los elementos necesarios para el proyecto, los cuáles son los siguientes.

#### Tablas

Nombre | Descripción
------------ | ------------ 
Arrendador | *Datos de la persona que realizará préstmos de libros*
Libro | *Datos de un libro*
Préstamo | *Préstamo realizado por un arrendador*
Stack | *Tabla intermedia préstamo-libro que almacena la cantidad para cada libro en un préstamo*

#### Relaciones

Tablas | Relación
------------ | ------------ 
Arrendador-Préstamo | 1-N
Préstamo-Stack | 1-N
Stack-Libro | N-1
(Préstamo-Libro) | N-M

### Aplicación Java

*Java* es el encargado de realizar las operaciones lógicas correspondientes según la opción de menú elegida. Éste tendrá que manipular datos y hacer uso las librerías que harán de interfaz entre él y la base de datos.
> La clase principal (ejecutable) es [Main.java](src/com/booking/ejecutable/Main.java).

#### Estructuración en paquetes

El código del proyecto está organizado en paquetes de forma que su estructura queda de la siguiente forma:

Paquete | Descripción
------------ | ------------ 
[ejecutable](./src/com/booking/ejecutable/) | Paquete con los archivos que serán ejecutados
[persistencia](./src/com/booking/persistencia/) | Clases que definen los objetos que manipularemos
[modelo](./src/com/booking/modelo/) | Clases con utilidades recurridas
[dao](./src/com/booking/dao/) | Clases que definen la interacción con la BD

### Hibernate

Para conectar nuestro programa Java con MySQL hacemos uso de la librería *Hibernate*, que nos permitirá abstraernos de SQL y trabajar con objetos de forma que el proyecto se agiliza mucho más. También hacemos uso de **Hibernate validator**, que nos ayudará a validar los datos de entrada ahorrándonos el trabajo de programar validaciones en Java. Cabe destacar que Hibernate a su vez hace uso de la librería *JDBC* para conectarse con la base de datos, una librería que podría conectar por sí sola Java con la BD pero de forma mucho más primitiva.
> Para ello nos hemos basado en la ayuda de la profesora @Fátima y la [wiki curso de Hibernate](http://cursohibernate.es/doku.php).

#### Diagrama de clases

![Imagen de clases](/doc/DiagramaClases.jpg "**Aquí va la imagen de clases**")

#### Consultas realizadas

En las clases Java del paquete [dao](./src/com/booking/dao/), realizamos consultas en varios métodos. 

Método | Clase | Tipo de consulta
------------ | ------------ | ------------ 
guardar | [GenericDAO](./src/com/booking/dao/GenericDao.java) | Básica
borrar | [GenericDAO](./src/com/booking/dao/GenericDao.java) | Básica
actualizar | [GenericDAO](./src/com/booking/dao/GenericDao.java) | Básica
obtener | [GenericDAO](./src/com/booking/dao/GenericDao.java) | Básica
obtenerPorNombre | [ArrendadorDAO](./src/com/booking/dao/ArrendadorDAO.java) | HQL
totalArrendadores | [ArrendadorDAO](./src/com/booking/dao/ArrendadorDAO.java) | HQL
obtenerPorNombre | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
prestamosEnUnAno | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
librosPrestadosEnUnAno | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
totalPrestamos | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
fechaPrimerPrestamo | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
mediaLibrosPrestados | [PrestamoDAO](./src/com/booking/dao/PrestamoDAO.java) | HQL
totalLibrosPrestados | [StackDAO](./src/com/booking/dao/StackDAO.java) | HQL

#### Eliminación en cascada

Se ha aplicado la eliminación en cascada en las clases siguientes.

Clase | Clase víctima del cascada | Razón
------------ | ------------ | ------------ 
[Arrendador](./src/com/booking/persistencia/Arrendador.java) | [Préstamo](./src/com/booking/persistencia/Prestamo.java) | Naturaleza de la relación (préstamo no puede existir sin arrendador)
[Préstamo](./src/com/booking/persistencia/Prestamo.java) | [Stack](./src/com/booking/persistencia/Stack.java) | Naturaleza de la relación (no puede haber pilas de libros prestados sin su préstamo)

- - - -


## Documentación

Toda la **documentación** se encuentra en la carpeta [doc](./doc/) incluyendo los siguientes documentos.

### Manual del proyecto

Se trata de un white paper o manual dónde se explica el proyecto Boo-King. 
> Puedes encontrarlo en el [dosier del proyecto](./doc/dosier/Booking.pdf).

### Documentación Javadoc

Es la documentación web generada a partir de los comentarios Javadoc en código, principalmente para desarrolladores y colaboradores.
> Puedes acceder a ella a través del [índice Javadoc](./doc/index.html).

### Historial de versiones

Es un representación gráfica de todas las versiones (commits) por las que ha pasado el proyecto. Te recomiendo que le eches un vistazo.
> Puedes ver el árbol de versiones en este [enlace](https://github.com/ismenc/Boo-King/network).


- - - -


## ¿Cómo ejecutar la aplicación?

### Base de datos

1. Deberemos instalar la base de datos MySQL con PHPMyAdmin.
2. Desde PHPMyAdmin, crear la base de datos 'booking'.
3. Importar el archivo [BaseDatos.sql](BaseDatos.sql) en la base de datos que acabamos de crear.

### Lanzar aplicación Java

1. Configurar datos de acceso a BD en el archivo [hibernate.cfg.xml](./src/hibernate.cfg.xml).
2. Lanzar Main.java desde un entorno de desarrollo Java.u

- - - -

## Objetivos

### Acceso a Datos

* Requisitos mínimos
	* - [X] La BD debe constar de al menos tres tablas relacionadas entre si
	* - [X] Deben existir relaciones 1:N (al menos 1)
	* - [X] Debe existir algún campo autonumérico
	* - [X] Tipo enumerado en alguna tabla
	* - [X] Validaciones con Hibernate Validator
	* - [X] Deben poder realizarse operaciones de inserción en todas las tablas de la BD (>= 3)
	* - [X] Deben poder realizarse operaciones de consultas HQL de todas las tablas de la BD (>= 3)
	* - [X] Deben poder realizarse operaciones de modificación de al menos una tabla de la BD
	* - [X] Deben poder realizarse operaciones de baja de al menos una tabla de la BD
	* - [X] Utilización del patrón DAO
	* - [X] Gestión de transacciones (?)

* Aspiras alto, amigo
	* - [X] Existencia de una relación N:M implementada como dos relaciones y 1:N
	* - [ ] Existencia de relación 1:N ordenada
	* - [X] Tipo fecha en alguna tabla
	* - [ ] Paginación
	* - [X] Métodos java de validación
	* - [X] Existencia de claves únicas
	* - [X] Complejidad de la aplicación, de las consultas y operaciones realizadas
	* - [X] Calidad de software y estilo de programación. YEA
	* - [X] Calidad de la documentación
  
### Desarrollo de interfaces

> :construction: This is work in progress my frienderino! :construction:

- - - -

## Especificaciones de desarrollo

### Acceso a datos

* Los **requisitos de la base de datos** se han descrito en la sección [Base de datos](#base-de-datos-mysql).
* Los **requisitos de consultas** se han descritos en [Consultas realizadas](#consultas-realizadas).
* La explicación del **cascade** está en [Eliminación en cascada](#eliminación-en-cascada).

#### Cosas que caben destacar

* En todo momento he deseado superarme programando este proyecto, a nivel de organización, documentación, control de versiones y presentación. Sé que no he hecho un proyecto atractivo por fuera, pero he intentado que por dentro sea *adorable* :koala:.
> La verdad es que estoy tan ~~muerto~~ fuera de combate que no quiero ni saber qué nota tendrá.
* He invertido la mayoría del tiempo en re-escribir código y documentar la aplicación para que sea mantenible.
* He tenido dificultades puntuales con alguna consulta HQL.
* He aprendido cómo conectar lenguajes lógicos con bases de datos de forma muy práctica.
* He aprendido mazo de Git & GitHub :octocat: con mucho esfuerzo, pero por que yo he decido que así sea :heart:.
> Creo que ha valido mucho la pena.
* A mi pesar he tenido que renunciar a presentar la interfaz gráfica y funcionalidades como la paginación en los plazos.

### Desarrollo de interfaces

> :construction: Estamos repartiendo alquitrán por aquí, vuelve más tarde :construction:
