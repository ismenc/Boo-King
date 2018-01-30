/*
create table arrendador (id int AUTO_INCREMENT primary key not null, nombre varchar(50) not null, 
	entidad varchar(50), telefono varchar(11) not null, direccion varchar(100) not null, 
	codigoPostal varchar(10) not null);


create table libro(id int AUTO_INCREMENT primary key not null, titulo varchar(50) not null, 
	autor varchar(50) not null, editorial varchar(50) not null, categoria int not null, 
	anoPublicacion varchar(4) not null);
	
	
BEGIN;

create table prestamo(id int AUTO_INCREMENT primary key not null, fecha date not null, duracionDias int not null, idArrendador int,
                     FOREIGN KEY(idArrendador) REFERENCES arrendador(id));
create table stack(id int AUTO_INCREMENT primary key not null, idPrestamo int, idLibro int not null, cantidad int not null,
                  FOREIGN KEY(idPrestamo) REFERENCES prestamo(id),
                   FOREIGN KEY(idLibro) REFERENCES libro(id));
COMMIT
*/

-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-01-2018 a las 18:04:19
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
(2, 'Juan Manue Pere Romuald', 'Ayala', '34656565656', 'Polígono Azucena, 26', '5100');

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
(2, 'The Obstacle is the Way', 'Ryan Holiday', 'Yokse', 4, '2005');

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
(2, '2018-01-26', 5, 2),
(4, '2018-01-26', 4, 1);

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
(4, 1, 3, 4);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `libro`
--
ALTER TABLE `libro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `stack`
--
ALTER TABLE `stack`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
