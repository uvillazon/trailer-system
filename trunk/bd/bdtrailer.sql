-- phpMyAdmin SQL Dump
-- version 2.11.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-03-2012 a las 21:40:14
-- Versión del servidor: 5.0.51
-- Versión de PHP: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `bdtrailer`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `basededatos`
--

CREATE TABLE `basededatos` (
  `idbasededatos` varchar(20) default NULL,
  `basededatos` varchar(20) default NULL,
  `idsistema` varchar(20) default NULL,
  `estado` varchar(20) default NULL,
  KEY `idsistema` (`idsistema`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `basededatos`
--

INSERT INTO `basededatos` (`idbasededatos`, `basededatos`, `idsistema`, `estado`) VALUES
('bdt-1000', 'trailer', 'sis-1000', 'Activo'),
('bdt-1001', 'trailer1', 'sis-1001', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sistema`
--

CREATE TABLE `sistema` (
  `idsistema` varchar(20) default NULL,
  `nombre` varchar(50) default NULL,
  `email` varchar(50) default NULL,
  `estado` char(20) default NULL,
  KEY `idsistema` (`idsistema`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `sistema`
--

INSERT INTO `sistema` (`idsistema`, `nombre`, `email`, `estado`) VALUES
('sis-1000', 'trailer', 'sdf', 'Activo'),
('sis-1001', 'trailer1', 'sdf', 'Activo');

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `basededatos`
--
ALTER TABLE `basededatos`
  ADD CONSTRAINT `basededatos_sistema` FOREIGN KEY (`idsistema`) REFERENCES `sistema` (`idsistema`);
