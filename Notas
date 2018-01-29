create table arrendador (id int AUTO_INCREMENT primary key not null, nombre varchar(50) not null, 
	entidad varchar(50), telefono varchar(11) not null, direccion varchar(100) not null, 
	codigoPostal varchar(10) not null);


create table libro(id int AUTO_INCREMENT primary key not null, titulo varchar(50) not null, 
	autor varchar(50) not null, editorial varchar(50) not null, categoria int not null, 
	anoPublicacion varchar(4) not null);
	
	
BEGIN;

create table prestamo(id int AUTO_INCREMENT primary key not null, fecha date not null, duracionDias int not null, idArrendador int not null,
                     FOREIGN KEY(idArrendador) REFERENCES arrendador(id));
create table stack(id int AUTO_INCREMENT primary key not null, idPrestamo int not null, idLibro int not null, cantidad int not null,
                  FOREIGN KEY(idPrestamo) REFERENCES prestamo(id),
                   FOREIGN KEY(idLibro) REFERENCES libro(id));
COMMIT

