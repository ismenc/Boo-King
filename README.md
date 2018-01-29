# Boo-King

Boo-King es una aplicación de préstamo de libros que permite almacenar datos de libros, arrendadores y préstamos en una base de datos MySQL.

## Funcionamiento

### Aplicación Java

Nuestra aplicación Java muestra un menú con las opciones que se ofrecen al usuario. El menú ofrece las siguientes opciones:
```
[1] Añadir arrendador
[2] Añadir libro
[3] Nuevo préstamo
[4] Modificar libro
[5] Borrar arrendador
[6] Consultar arrendador
[7] Consultar libro
[8] Consultar préstamo
[9] Salir
```
Una vez elegida cualquiera de las opciones, el programa Java se encarga de solicitar los datos necesarios o realizar las operaciones correspondientes. En secciones siguientes explicamos cómo Java interacciona con la base de datos.

### Servidor MySQL

El servidor MySQL será responsable de almacenar todos los datos de nuestra aplicación. Para ello utilizaremos 4 tablas:
* Arrendador - *Datos de la persona que realizará préstmos de libros*
* Libro - *Datos de un libro*
* Préstamo - *Préstamo realizado por un arrendador*
* Stack - *Tabla intermedia préstamo-libro que almacena la cantidad para cada libro en un préstamo*

Las relaciones son:
* Arrendador-Préstamo: 1-N
* Préstamo-Libro: N-M

### Hibernate

Para conectar nuestro programa Java con MySQL hacemos uso de la librería Hibernate, que nos permitirá abstraernos de SQL y trabajar con objetos de forma que el proyecto se agiliza mucho más.
