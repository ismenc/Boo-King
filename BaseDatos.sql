
/* --------------------------- CONSULTAS SQL ---------------------------

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
*/

-- ----------------------- SCRIPT -----------------------
-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-02-2018 a las 09:44:19
-- Versión del servidor: 10.1.29-MariaDB
-- Versión de PHP: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `booking`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `arrendador`
--

CREATE TABLE `arrendador` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `entidad` varchar(50) DEFAULT NULL,
  `telefono` varchar(11) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `codigoPostal` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `arrendador`
--

INSERT INTO `arrendador` (`id`, `nombre`, `entidad`, `telefono`, `direccion`, `codigoPostal`) VALUES
(1, 'Ismael', '', '34999888777', 'Benajete, 100', '41510'),
(2, 'Juan Manue Pere Romuald', 'Ayala', '34656565656', 'Polígono Azucena, 26', '5100'),
(4, 'Paco Manue', NULL, '111222333', 'Arrabal, 15', '51000'),
(5, 'Lucía', NULL, '123456789', 'Lucero de Almadena, 5', '43260'),
(6, 'Agapito Pérez Blancaflor', NULL, '255638994', 'Melainventao, 1000', '41510'),
(7, 'Asunción del Amor de Dios', 'Google', '222555888', 'MountainView, 8', '26262'),
(8, 'Alejander Jiménez Gómez', 'Mahou', '444666332', 'Lalalandia, 19', '18023');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libro`
--

CREATE TABLE `libro` (
  `id` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `autor` varchar(50) NOT NULL,
  `editorial` varchar(50) NOT NULL,
  `categoria` int(11) NOT NULL,
  `anoPublicacion` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `libro`
--

INSERT INTO `libro` (`id`, `titulo`, `autor`, `editorial`, `categoria`, `anoPublicacion`) VALUES
(1, 'Don Quixote de la Mancha', 'Cervantes', 'Cervicales', 0, '1605'),
(2, 'The Obstacle is the Way', 'Ryan Holiday', 'Yokse', 4, '2005'),
(3, 'Awakening the Giant Within', 'Anthony Robbins', 'Clave debolsillo', 4, '1998'),
(4, 'Emotional Intelligence', 'Daniel Goleman', 'Unknown', 4, '1953'),
(5, 'Spiderman', 'Stan Lee', 'Marvel', 1, '1980'),
(6, 'Campos de castilla', 'Antonio Machado', 'Clave', 2, '1912'),
(7, 'Las biografías están sobrevaloradas', 'Yo', 'Obelisco', 3, '1994');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `duracionDias` int(11) NOT NULL,
  `idArrendador` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id`, `fecha`, `duracionDias`, `idArrendador`) VALUES
(2, '2017-05-14', 5, 2),
(4, '2017-06-03', 10, 1),
(5, '2017-06-30', 7, 1),
(6, '2017-11-08', 100, 5),
(7, '2018-01-02', 280, 8),
(8, '2018-01-08', 27, 5),
(9, '2018-01-17', 145, 4),
(10, '2018-02-01', 31, 7),
(11, '2018-02-04', 57, 5),
(12, '2018-02-04', 160, 6),
(13, '2018-02-04', 189, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stack`
--

CREATE TABLE `stack` (
  `id` int(11) NOT NULL,
  `idLibro` int(11) DEFAULT NULL,
  `cantidad` int(11) NOT NULL,
  `idPrestamo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `stack`
--

INSERT INTO `stack` (`id`, `idLibro`, `cantidad`, `idPrestamo`) VALUES
(2, 2, 3, 2),
(4, 1, 3, 4),
(5, 1, 3, 5),
(6, 2, 1, 5),
(7, 3, 2, 6),
(8, 5, 3, 6),
(9, 7, 1, 6),
(10, 1, 1, 6),
(11, 2, 1, 7),
(12, 3, 6, 8),
(13, 2, 3, 8),
(14, 6, 1, 9),
(15, 5, 1, 10),
(16, 7, 1, 11),
(17, 6, 1, 11),
(18, 6, 40, 12),
(19, 7, 65, 12),
(20, 3, 1, 13);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `arrendador`
--
ALTER TABLE `arrendador`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `libro`
--
ALTER TABLE `libro`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idArrendador` (`idArrendador`);

--
-- Indices de la tabla `stack`
--
ALTER TABLE `stack`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPrestamo` (`idPrestamo`),
  ADD KEY `idLibro` (`idLibro`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `arrendador`
--
ALTER TABLE `arrendador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `libro`
--
ALTER TABLE `libro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `stack`
--
ALTER TABLE `stack`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `prestamo_ibfk_1` FOREIGN KEY (`idArrendador`) REFERENCES `arrendador` (`id`);

--
-- Filtros para la tabla `stack`
--
ALTER TABLE `stack`
  ADD CONSTRAINT `stack_ibfk_1` FOREIGN KEY (`idPrestamo`) REFERENCES `prestamo` (`id`),
  ADD CONSTRAINT `stack_ibfk_2` FOREIGN KEY (`idLibro`) REFERENCES `libro` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
