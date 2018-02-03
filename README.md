# Sobre Boo-King

Boo-King es una aplicación de préstamo de libros entre **personas** que permite almacenar datos de libros, arrendadores y préstamos en una base de datos MySQL. La aplicación nos permitirá manipular estos datos de forma amigable. Los arrendatarios se han obviado para simplificar un poco el proyecto.

## Funcionamiento

Nuestra aplicación ofrece al usuario un menú con las siguientes opciones:
```
+------------ MENÚ -------------+
|            Básico             |
|-------------------------------|
| [1] Insertar dato		        |
| [2] Actualizar dato	     	|
| [3] Borrar dato		        |
| [4] Consultar objeto	     	|
|-------------------------------|
|           Avanzado            |
|-------------------------------|
| [5] Listar arrendadores nombre|
| [6] Listar prestamos nombre	|
| [7] Préstamos en un año	    |
| [8] Estadísticas globales	    |
| [9] Salir			            |
+-------------------------------+
```

Tras elegir una opción, el programa interactuará con el usuario para pedirle los datos necesarios para operar.

### Aplicación Java

**Java** es el encargado de realizar las operaciones correspondientes según la opción de menú elegida. Éste tendrá que realizar operaciones lógicas y manipular las librerías que harán de interfaz con la base de datos.

La clase principal (ejecutable) es [Main.java](src/com/booking/ejecutable/Main.java).
El proyecto está organizado en paquetes de forma que su estructura queda de la siguiente forma:

Paquete | Descripción
------------ | ------------ 
[ejecutable](./src/com/booking/ejecutable/) | Paquete con los archivos que serán ejecutados
[persistencia](./src/com/booking/persistencia/) | Clases que definen los objetos que manipularemos
[modelo](./src/com/booking/modelo/) | Clases con utilidades recurridas
[dao](./src/com/booking/dao/) | Clases que definen la interacción con la BD

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

Para conectar nuestro programa Java con MySQL hacemos uso de la librería **Hibernate**, que nos permitirá abstraernos de SQL y trabajar con objetos de forma que el proyecto se agiliza mucho más. También hacemos uso de **Hibernate validator**, que nos ayudará a validar los datos de entrada ahorrándonos el trabajo de programar validaciones en Java.