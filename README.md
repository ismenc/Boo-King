# Boo-King

Boo-King es una aplicación de préstamo de libros entre **personas, no bibliotecas** que permite almacenar datos de libros, arrendadores y préstamos en una base de datos MySQL. Los arrendatarios se han obviado para simplificar un poco el proyecto.

## Funcionamiento

Nuestra aplicación ofrece al usuario un menú con las siguientes opciones:
```
[1] Añadir arrendador
[2] Añadir libro
[3] Nuevo préstamo
[4] Modificar libro
[5] Borrar arrendador
[6] Consultar arrendador
[7] Consultar libro
[8] Consultar préstamo
[9] Buscar arrendadores nombre
[10] Buscar prestamos nombre
[11] Salir
```

Tras elegir una opción, el programa preguntará al usuario por los datos necesarios para operar.

### Aplicación Java

Java es el encargado de realizar las operaciones correspondientes según la opción de menú elegida. La clase principal (ejecutable) es [Main.java](src/com/booking/ejecutable/Main.java).
En secciones siguientes explicamos cómo Java interacciona con la base de datos.

### Servidor MySQL

El servidor MySQL será responsable de **almacenar** los datos de nuestra aplicación. Para ello utilizaremos estas 4 tablas:

Nombre | Descripción
------------ | ------------ 
Arrendador | *Datos de la persona que realizará préstmos de libros*
Libro | *Datos de un libro*
Préstamo | *Préstamo realizado por un arrendador*
Stack | *Tabla intermedia préstamo-libro que almacena la cantidad para cada libro en un préstamo*

Las relaciones son:

Tablas | Relación
------------ | ------------ 
Arrendador-Préstamo | 1-N
Préstamo-Stack | 1-N
Stack-Libro | N-1
(Préstamo-Libro) | N-M


### Hibernate

Para conectar nuestro programa Java con MySQL hacemos uso de la librería **Hibernate**, que nos permitirá abstraernos de SQL y trabajar con objetos de forma que el proyecto se agiliza mucho más. También nos ayudará a validar los datos de entrada ahorrandonos el trabajo de programarlo en Java.

## Estructura del proyecto

Hemos organizado el proyecto en paquetes de forma que su estructura queda definida así:

Paquete | Descripción
------------ | ------------ 
[ejecutable](./src/com/booking/ejecutable/) | Paquete con los archivos que serán ejecutados
[persistencia](./src/com/booking/persistencia/) | Clases que definen los objetos que manipularemos
[modelo](./src/com/booking/modelo/) | Clases con utilidades recurridas
[dao](./src/com/booking/dao/) | Clases que definen la interacción con la BD

