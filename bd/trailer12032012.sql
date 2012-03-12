-- phpMyAdmin SQL Dump
-- version 2.11.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-03-2012 a las 21:39:25
-- Versión del servidor: 5.0.51
-- Versión de PHP: 5.2.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de datos: `trailer`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargos`
--

CREATE TABLE `cargos` (
  `idcargo` varchar(20) NOT NULL,
  `nombre` varchar(100) default NULL,
  `codigo` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `cargos`
--

INSERT INTO `cargos` (`idcargo`, `nombre`, `codigo`, `numero`) VALUES
('car-10', 'administrador', 'admin', 10),
('car-11', 'marketing', 'mar', 11),
('car-12', 'jefe de ventas', 'ven', 12),
('car-13', 'cortador', 'cor', 13),
('car-14', 'diseÃƒÆ’Ã‚Â±ador', 'dis', 14),
('car-15', 'empaquetador', 'emp', 15),
('car-16', 'contador', 'cont', 16),
('car-17', 'jefe en almacen', 'alm', 17),
('car-18', 'jefe en sistemas', 'sis', 18),
('car-19', 'ayudante en sistema', 'sist', 19),
('car-20', 'mantenimiento de computadoras', 'man', 20),
('car-21', 'deshilador', 'des', 21),
('car-22', 'editar', 'editar', 22),
('car-4', 'planta', 'Planta', 4),
('car-5', 'respoanble area de costura', 'RESAL', 5),
('car-6', 'responsable area de corte', 'resco', 6),
('car-7', 'operario', 'operario', 7),
('car-8', 'operador de bordadora', 'bord', 8),
('car-9', 'serigrafista', 'seri', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoriafuncion`
--

CREATE TABLE `categoriafuncion` (
  `idcategoriafuncion` varchar(20) NOT NULL,
  `nombre` varchar(100) default NULL,
  `codigo` varchar(20) default NULL,
  `seguridad` varchar(20) default NULL,
  `estado` varchar(20) default NULL,
  PRIMARY KEY  (`idcategoriafuncion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `categoriafuncion`
--

INSERT INTO `categoriafuncion` (`idcategoriafuncion`, `nombre`, `codigo`, `seguridad`, `estado`) VALUES
('caf1000', 'sistema', NULL, NULL, 'Activo'),
('caf10001', 'materia prima', NULL, NULL, 'Activo'),
('caf10002', 'Productos', NULL, NULL, 'Activo'),
('caf1001', 'parametros', NULL, NULL, 'Activo'),
('caf1002', 'egresos', '', NULL, 'Activo'),
('caf1003', 'Orden Produccion', NULL, NULL, 'Activo'),
('caf1004', 'Produccion', NULL, NULL, 'Incativo'),
('caf1005', 'resportes', NULL, NULL, 'Activo'),
('caf1006', 'corte', NULL, NULL, 'Activo'),
('caf1007', 'Distribucion', NULL, NULL, 'Activo'),
('caf1008', 'Entregas', NULL, NULL, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoriaproducto`
--

CREATE TABLE `categoriaproducto` (
  `idcategoriaproducto` varchar(20) NOT NULL,
  `nombre` varchar(100) default NULL,
  `descripcion` varchar(200) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcategoriaproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `categoriaproducto`
--

INSERT INTO `categoriaproducto` (`idcategoriaproducto`, `nombre`, `descripcion`, `numero`) VALUES
('ctp-1', 'Varios', 'varios', 1),
('ctp-11', 'logos', 'logos', 11),
('ctp-15', 'poleras', 'poleras ', 15),
('ctp-16', 'neceseres', 'neceseres ', 16),
('ctp-17', 'conjuntos ', 'conjuntos ', 17),
('ctp-19', 'guardapolvos', 'guardapolvos de todos los tipos ', 19),
('ctp-2', 'gorras', 'gorras', 2),
('ctp-21', 'mochilas', 'mochila', 21),
('ctp-22', 'agarradores', 'agarrradores', 22),
('ctp-23', 'almohada', 'almohadas', 23),
('ctp-25', 'bananero', 'bananero', 25),
('ctp-29', 'barbijos', 'barbijos', 29),
('ctp-36', 'bolsitas ', 'bolsita', 36),
('ctp-4', 'bolson', 'bolsones', 4),
('ctp-45', 'maletin ', 'maletin', 45),
('ctp-48', 'Carteras', NULL, 48),
('ctp-49', 'estucheras', NULL, 49),
('ctp-5', 'bufandas', 'bufandas', 5),
('ctp-50', 'pantalon', 'pantalon de vestir', 50),
('ctp-6', 'camisas', 'camisas', 6),
('ctp-7', 'chalecos', 'chalecos', 7),
('ctp-8', 'chamarras', 'chamaras', 8),
('ctp-9', 'deportivos', 'deportivos\n', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `idcategoria` varchar(20) NOT NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(40) NOT NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`idcategoria`, `codigo`, `nombre`, `numero`) VALUES
('cat-1', 'tel', 'tela', 1),
('cat-10', 'carr', 'argolla', 10),
('cat-11', 'cin', 'cinta', 11),
('cat-12', 'cap', 'campanita', 12),
('cat-13', 'cha', 'chapas', 13),
('cat-14', 'hua', 'huato', 14),
('cat-15', 'lig', 'liga', 15),
('cat-16', 'esp', 'esponja', 16),
('cat-17', 'CAR', 'CARRITO', 17),
('cat-18', 'ML', 'MEDIA LUNA', 18),
('cat-2', 'bot', 'boton', 2),
('cat-3', 'cie', 'cierre', 3),
('cat-4', 'rib', 'ribets', 4),
('cat-5', 'corr', 'correa', 5),
('cat-6', 'scr', 'scrach', 6),
('cat-7', 'vie', 'vies', 7),
('cat-8', 'ven', 'vena', 8),
('cat-9', 'arg', 'argolla', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudades`
--

CREATE TABLE `ciudades` (
  `idciudad` varchar(20) NOT NULL,
  `nombre` varchar(100) default NULL,
  `codigo` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `ciudades`
--

INSERT INTO `ciudades` (`idciudad`, `nombre`, `codigo`, `numero`) VALUES
('ciu-1', 'cochabamba', 'cbba', 1),
('ciu-2', 'la paz', 'lpz', 2),
('ciu-3', 'santa cruz', 'stz', 3),
('ciu-4', 'oruro', 'oru', 4),
('ciu-5', 'sucre', 'sc', 5),
('ciu-6', 'tarija', 'tari', 6),
('ciu-7', 'potosi', 'poto', 7),
('ciu-8', 'pando', 'pan', 8),
('ciu-9', 'beni', 'ben', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `idcliente` varchar(20) NOT NULL,
  `idciudad` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `apellido1` varchar(50) default NULL,
  `direccion` varchar(200) default NULL,
  `email` varchar(50) default NULL,
  `fax` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `estado` varchar(20) default NULL,
  `telefono` varchar(20) default NULL,
  `observacion` varchar(1000) default NULL,
  `nit` varchar(20) default NULL,
  `fechanacimiento` date default NULL,
  PRIMARY KEY  (`idcliente`),
  KEY `fk_relationship_7` (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`idcliente`, `idciudad`, `codigo`, `nombre`, `apellido1`, `direccion`, `email`, `fax`, `numero`, `estado`, `telefono`, `observacion`, `nit`, `fechanacimiento`) VALUES
('cli-1', 'ciu-1', 'cli001', 'valeria', 'czermak', NULL, NULL, NULL, 1, 'Activo', NULL, NULL, '3721232010', NULL),
('cli-10', 'ciu-1', 'uvv3', 'ubad', 'uusad', 'c/moxos ', 'ada', '21312312', 10, 'Activo', '21321312', 'qqqqqqqqqqq', NULL, '2011-12-28'),
('cli-11', 'ciu-1', 'uvv4', 'ubad', 'uusad', 'c/moxos ', 'ada', '21312312', 11, 'Activo', '21321312', 'qqqqqqqqqqq', '21321312312', '2011-12-28'),
('cli-2', 'ciu-1', 'cli002', 'hugo ', 'flores', NULL, NULL, NULL, 2, 'Activo', '70749610', NULL, NULL, NULL),
('cli-3', 'ciu-1', 'cli003', 'alina', 'cuadros', NULL, NULL, '79710431', 3, 'Activo', '4246291', NULL, NULL, NULL),
('cli-4', 'ciu-1', 'cli004', 'claudia', 'medrano soria', '2da circunvalaciÃ³n', NULL, NULL, 4, 'Activo', NULL, 'es la agencia de publicidad grafos', '3123678012', NULL),
('cli-5', 'ciu-1', 'cli005', 'roberto', 'peÃ±a', NULL, NULL, NULL, 5, 'Activo', NULL, NULL, '984951011', NULL),
('cli-6', 'ciu-1', 'cli006', 'hans', 'wieler', NULL, 'hanswieler@hotmail.com', NULL, 6, 'Activo', '70716655', 'mr. t', NULL, NULL),
('cli-7', 'ciu-1', 'cli007', 'marco', 'montenegro', NULL, NULL, NULL, 7, 'Activo', NULL, 'mountblack', '2863478013', NULL),
('cli-8', 'ciu-1', 'uvv', 'ubaldo ', 'villazon', 'c/moxos # 2002', 'uvillazon@doptima.com', '99999', 8, 'Activo', '9999', 'niguno', '23333', '1982-06-11'),
('cli-9', 'ciu-1', 'uvv2', 'ubad', 'uusad', 'c/moxos ', 'ada', '21312312', 9, 'Activo', '21321312', 'qqqqqqqqqqq', NULL, '2011-12-28');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `colores`
--

CREATE TABLE `colores` (
  `idcolor` varchar(20) NOT NULL,
  `nombre` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcolor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `colores`
--

INSERT INTO `colores` (`idcolor`, `nombre`, `codigo`, `numero`) VALUES
('col-1', 'rojo', 'ro', 1),
('col-10', 'negro', 'negr', 10),
('col-100', 'cafe claro', 'caf2', 100),
('col-101', 'chocolate', 'choco', 101),
('col-102', 'ladrillo refractado', 'lad', 102),
('col-103', 'marron', 'ma4', 103),
('col-104', 'salmon oscuro', 'sal1', 104),
('col-105', 'salmon', 'sal', 105),
('col-106', 'salmon claro', 'sal2', 106),
('col-107', 'naranja', 'na', 107),
('col-108', 'naranja oscuro', 'na1', 108),
('col-109', 'coral', 'co', 109),
('col-11', 'amarillo', 'ama', 11),
('col-110', 'coral claro', 'co1', 110),
('col-111', 'tomate', 'to', 111),
('col-112', 'rojo anaranjado', 'ro2', 112),
('col-113', 'rosa', 'ros', 113),
('col-114', 'rosa oscuro', 'ros2', 114),
('col-115', 'rosa claro', 'ros3', 115),
('col-116', 'violeta rojo palido', 'vio', 116),
('col-117', 'marron carmesi', 'ma5', 117),
('col-118', 'violeta medio rojo', 'vio1', 118),
('col-119', 'violeta rojo', 'vio2', 119),
('col-12', 'cafe', 'caf', 12),
('col-120', 'magenta', 'mag', 120),
('col-121', 'violeta', 'vio3', 121),
('col-122', 'ciruela', 'cir', 122),
('col-123', 'orquidea', 'or', 123),
('col-124', 'orquidea medio', 'orq1', 124),
('col-125', 'orquidea oscuro', 'orq', 125),
('col-126', 'violeta oscuro', 'vio4', 126),
('col-127', 'violeta azul', 'vio5', 127),
('col-128', 'purpura', 'pur', 128),
('col-129', 'purpura medio', 'pur1', 129),
('col-13', 'marron', 'ma', 13),
('col-130', 'cardo', 'car', 130),
('col-131', 'cahoba', 'ca', 131),
('col-132', 'verde pino', 'verde', 132),
('col-133', 'militar', 'mil', 133),
('col-134', 'PLOMO', NULL, 134),
('col-135', 'PLOMO', 'PL', 135),
('col-136', 'hoja seca', 'hoja', 136),
('col-137', 'celeste', 'c1', 137),
('col-138', 'verde lechuga', 'ver2', 138),
('col-139', 'fucxia', 'fu', 139),
('col-14', 'marfil', 'mar', 14),
('col-140', 'celeste claro', 'cel5', 140),
('col-141', 'plomo oscuro', 'plo4', 141),
('col-142', 'violeta claro', 'vio8', 142),
('col-143', 'plomo claro', 'pl2', 143),
('col-144', 'plomo oscuro', 'pl4', 144),
('col-15', 'guindo', 'guin', 15),
('col-16', 'dorado', 'dor', 16),
('col-17', 'plateado', 'pla', 17),
('col-18', 'naranja claro', 'nar', 18),
('col-19', 'azul marino', 'azu', 19),
('col-2', 'azul', 'azul', 2),
('col-20', 'azul pastel', 'az', 20),
('col-21', 'amarillo bandera', 'ama1', 21),
('col-22', 'amarillo patito', 'ama2', 22),
('col-23', 'amarillo oscuro', 'ama3', 23),
('col-24', 'amarillo claro', 'ama4', 24),
('col-25', 'azulino', 'azul1', 25),
('col-26', 'azul desertor', 'azul2', 26),
('col-27', 'azul cielo profundo', 'azul3', 27),
('col-28', 'azul cielo', 'azul4', 28),
('col-29', 'azul cielo claro', 'azul5', 29),
('col-3', 'blanco', 'blanco', 3),
('col-30', 'azul acero', 'azul6', 30),
('col-31', 'azul acero claro', 'azul7', 31),
('col-32', 'azul claro', 'azul8', 32),
('col-33', 'azul polvo', 'azul9', 33),
('col-34', 'turqueza palido', 'tur', 34),
('col-35', 'azul medianoche', 'azul10', 35),
('col-36', 'azul aciano', 'azul11', 36),
('col-37', 'azul pizarra oscuro', 'azul12', 37),
('col-38', 'azul pizarra', 'azul13', 38),
('col-39', 'veich', 'veich', 39),
('col-4', 'universal', 'univ', 4),
('col-40', 'nieve', 'nie', 40),
('col-41', 'blanco fantasma', 'blanco1', 41),
('col-42', 'blanco humo', 'blanco2', 42),
('col-43', 'blanco floral', 'Blanco3', 43),
('col-44', 'lino', 'lin', 44),
('col-45', 'blanco antiguo', 'blanco4', 45),
('col-46', 'almendra blanqueado', 'almen', 46),
('col-47', 'avellana', 'ave', 47),
('col-48', 'blanco navajo', 'blanco5', 48),
('col-49', 'mocasin', 'moca', 49),
('col-50', 'limon muselina', 'limo', 50),
('col-51', 'CARACOL', 'CARA', 51),
('col-52', 'miel rocio', 'miel', 52),
('col-53', 'crema menta', 'crema', 53),
('col-54', 'azul celeste', 'azul14', 54),
('col-55', 'azul alice', 'azul15', 55),
('col-56', 'lavanda', 'lava', 56),
('col-57', 'lavanda rubor', 'lava1', 57),
('col-58', 'rosa niebla', 'rosa', 58),
('col-59', 'gris oscuro pizarra', 'gris', 59),
('col-6', 'verde', 'v1', 6),
('col-60', 'gris oscuro', 'gris1', 60),
('col-61', 'gris pizarra', 'gris2', 61),
('col-62', 'gris claro pizarra', 'gris3', 62),
('col-63', 'gris', 'gris4', 63),
('col-64', 'gris claro', 'gris5', 64),
('col-65', 'turqueza oscuro', 'tur1', 65),
('col-66', 'turqueza medio', 'tur2', 66),
('col-67', 'turqueza', 'tur3', 67),
('col-68', 'cian', 'cia', 68),
('col-69', 'cian claro', 'cia1', 69),
('col-7', 'verde botella', 'v2', 7),
('col-70', 'azul cadete', 'azul16', 70),
('col-71', 'aguamarina medio', 'agua1', 71),
('col-72', 'aguamarina', 'agua2', 72),
('col-73', 'verde oscuro', 'verde1', 73),
('col-74', 'verde oliva oscuro', 'verde2', 74),
('col-75', 'verde mar oscuro', 'verde3', 75),
('col-76', 'verde mar', 'verde4', 76),
('col-77', 'verde mar medio', 'verde5', 77),
('col-78', 'verde mar claro', 'verde6', 78),
('col-79', 'verde palido', 'verde7', 79),
('col-8', 'verde limon', 'v3', 8),
('col-80', 'verde primavera', 'verde8', 80),
('col-81', 'verde cesped', 'verde9', 81),
('col-82', 'verde BANDERA', 'verde10', 82),
('col-83', 'verde media primaver', 'verde11', 83),
('col-84', 'verde amarillo', 'verde12', 84),
('col-85', 'verde bosque', 'verde13', 85),
('col-86', 'monotoma', 'oli', 86),
('col-87', 'kaki oscuro', 'kaki1', 87),
('col-88', 'oro', 'or1', 88),
('col-89', 'oro vara claro', 'or2', 89),
('col-9', 'transparente', 'Trasn', 9),
('col-90', 'oro vara', 'or3', 90),
('col-91', 'oro vara oscuro', 'or4', 91),
('col-92', 'rosado marron', 'ros1', 92),
('col-93', 'Piel roja', 'pie', 93),
('col-94', 'marron silla', 'ma1', 94),
('col-95', 'siena', 'si1', 95),
('col-96', 'madera', 'mad', 96),
('col-97', 'madera corpulento', 'mad1', 97),
('col-98', 'trigo', 'tri', 98),
('col-99', 'cafe arenoso', 'caf1', 99);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `componentegrupo`
--

CREATE TABLE `componentegrupo` (
  `idcomponentegrupo` varchar(20) NOT NULL,
  `idgrupo` varchar(20) default NULL,
  `fecha` date default NULL,
  `idproceso` varchar(20) default NULL,
  `idmateriaprima` varchar(20) default NULL,
  `idproducto` varchar(20) default NULL,
  `detalle` varchar(20) default NULL,
  `unidad` varchar(20) default NULL,
  `cantidad` int(11) default NULL,
  `precio` decimal(11,2) default NULL,
  `total` decimal(11,2) default NULL,
  `producto` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `iditems` varchar(20) default NULL,
  `id` varchar(20) default NULL,
  PRIMARY KEY  (`idcomponentegrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `componentegrupo`
--

INSERT INTO `componentegrupo` (`idcomponentegrupo`, `idgrupo`, `fecha`, `idproceso`, `idmateriaprima`, `idproducto`, `detalle`, `unidad`, `cantidad`, `precio`, `total`, `producto`, `numero`, `iditems`, `id`) VALUES
('cpg-10', 'grupo', '2011-05-11', NULL, 'mtp-1', NULL, 'tela', 'metros', 1, '0.00', '12.00', 'prd-1', 10, 'iditems', 'mtp-1'),
('cpg-11', 'grupo', '2011-05-11', 'prc-5', NULL, NULL, 'Gastos de bordadon ', 'proceso', 1, '12.00', '12.00', 'prd-1', 11, 'iditems', 'prc-5'),
('cpg-12', 'grupo', '2011-05-11', NULL, 'mtp-12', NULL, 'hilo numero1 rojo', 'metros', 1, '13.00', '13.00', 'prd-1', 12, 'iditems', 'mtp-12'),
('cpg-13', 'grupo', '2011-05-11', NULL, 'mtp-13', NULL, 'hilo rojo', 'pieza', 1, '14.00', '14.00', 'prd-1', 13, 'iditems', 'mtp-13'),
('cpg-14', 'grupo', '2011-05-11', NULL, 'mtp-14', NULL, 'hilo costura blanco', 'pieza', 1, '12.00', '12.00', 'prd-1', 14, 'iditems', 'mtp-14'),
('cpg-46', 'grupo', '2011-05-11', NULL, 'mtp-14', NULL, 'hilo costura blanco', 'pieza', 1, '0.00', '12.00', 'prd-3', 46, 'iditems', 'mtp-14'),
('cpg-47', 'grupo', '2011-05-11', NULL, 'mtp-13', NULL, 'hilo rojo', 'pieza', 1, '0.00', '14.00', 'prd-3', 47, 'iditems', 'mtp-13'),
('cpg-48', 'grupo', '2011-05-11', NULL, 'mtp-12', NULL, 'hilo numero1 rojo', 'metros', 1, '0.00', '13.00', 'prd-3', 48, 'iditems', 'mtp-12'),
('cpg-49', 'grupo', '2011-05-11', 'prc-2', NULL, NULL, 'IVA IMPUESTOS', 'proceso', 1, '12.00', '12.00', 'prd-3', 49, 'iditems', 'prc-2'),
('cpg-50', 'grupo', '2011-05-11', NULL, 'mtp-1', NULL, 'tela', 'metros', 1, '0.00', '2.00', 'prd-3', 50, 'iditems', 'mtp-1'),
('cpg-51', 'grupo', '2011-05-11', NULL, 'mtp-2', NULL, 'hilo', 'pieza', 1, '23.00', '23.00', 'prd-3', 51, 'iditems', 'mtp-2'),
('cpg-52', 'grupo', '2011-05-11', 'prc-4', NULL, NULL, 'bordado tomado como ', 'proceso', 1, '1.00', '1.00', 'prd-3', 52, 'iditems', 'prc-4'),
('cpg-53', 'grupo', '2011-05-11', 'prc-5', NULL, NULL, 'Gastos de bordadon ', 'proceso', 1, '2.00', '2.00', 'prd-3', 53, 'iditems', 'prc-5'),
('cpg-54', 'grupo', '2011-05-11', 'prc-6', NULL, NULL, 'DepreciaciÃƒÆ’Ã‚Â³n ', 'proceso', 1, '1.00', '1.00', 'prd-3', 54, 'iditems', 'prc-6'),
('cpg-75', 'gru-2', '2011-06-20', NULL, 'mtp-128', NULL, 'cierre verde botella', 'rollo', 1, '0.00', '0.00', 'producto', 75, 'iditems', 'mtp-128'),
('cpg-76', 'gru-2', '2011-06-20', NULL, 'mtp-12', NULL, 'hilo numero1 rojo', 'metros', 1, '0.00', '13.00', 'producto', 76, 'iditems', 'mtp-12'),
('cpg-77', 'gru-2', '2011-06-20', NULL, 'mtp-13', NULL, 'hilo rojo', 'pieza', 1, '0.00', '14.00', 'producto', 77, 'iditems', 'mtp-13'),
('cpg-78', 'gru-2', '2011-06-20', NULL, 'mtp-14', NULL, 'hilo costura blanco', 'pieza', 1, '0.00', '12.00', 'producto', 78, 'iditems', 'mtp-14'),
('cpg-89', 'grupo', '2011-07-08', NULL, 'mtp-14', NULL, 'hilo costura blanco', 'pieza', 1, '0.00', '90.00', 'prd-8', 89, 'iditems', 'mtp-14'),
('cpg-90', 'grupo', '2011-07-08', NULL, 'mtp-13', NULL, 'hilo rojo', 'pieza', 1, '0.00', '3.00', 'prd-8', 90, 'iditems', 'mtp-13'),
('cpg-91', 'grupo', '2011-07-08', NULL, 'mtp-12', NULL, 'hilo numero1 rojo', 'metros', 1, '0.00', '2.00', 'prd-8', 91, 'iditems', 'mtp-12'),
('cpg-92', 'grupo', '2011-07-08', NULL, 'mtp-128', NULL, 'cierre verde botella', 'rollo', 1, '0.00', '23.00', 'prd-8', 92, 'iditems', 'mtp-128');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compras`
--

CREATE TABLE `compras` (
  `idcompra` varchar(20) NOT NULL,
  `numerodocumento` varchar(20) default NULL,
  `idproveedor` varchar(20) default NULL,
  `idempleado` varchar(20) default NULL,
  `fecha` date default NULL,
  `hora` time default NULL,
  `tipodocumento` varchar(100) default NULL,
  `montototal` decimal(11,2) default NULL,
  `montoapagar` decimal(11,2) default NULL,
  `descuento` decimal(11,2) default NULL,
  `descuentobs` decimal(11,2) default NULL,
  `observacion` varchar(100) default NULL,
  `idusuario` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `ordenproduccion` varchar(20) default NULL,
  PRIMARY KEY  (`idcompra`),
  KEY `idempleado` (`idempleado`),
  KEY `idproveedor` (`idproveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `compras`
--

INSERT INTO `compras` (`idcompra`, `numerodocumento`, `idproveedor`, `idempleado`, `fecha`, `hora`, `tipodocumento`, `montototal`, `montoapagar`, `descuento`, `descuentobs`, `observacion`, `idusuario`, `numero`, `ordenproduccion`) VALUES
('com-1', '000001', 'pro-44', 'emp-29', '2012-01-04', '11:33:00', 'Nota Venta', '183.75', '183.75', NULL, NULL, 'regularizacion', 'usr-1000', 1, NULL),
('com-10', '210002', 'pro-44', 'emp-29', '2012-01-15', '15:25:48', 'Nota Venta', '3.78', '3.78', NULL, NULL, '18 BS/25 MTR', 'usr-1000', 10, NULL),
('com-11', '210002', 'pro-44', 'emp-29', '2012-01-15', '15:45:13', 'Nota Venta', '36.00', '36.00', NULL, NULL, '220 UDS/ 220 BS', 'usr-1000', 11, NULL),
('com-2', '00000001', 'pro-44', 'emp-29', '2012-01-14', '12:16:13', 'Nota Venta', '46.85', '46.85', NULL, NULL, 'regularizacion', 'usr-1000', 2, NULL),
('com-3', '0000000001', 'pro-44', 'emp-29', '2012-01-14', '12:23:56', 'Nota Venta', '13.66', '13.66', NULL, NULL, '85 bs/ 300 mtr', 'usr-1000', 3, NULL),
('com-4', '21002', 'pro-44', 'emp-29', '2012-01-18', '14:26:40', 'Nota Venta', '6.09', '6.09', NULL, NULL, '10 bs/ 50mtr', 'usr-1000', 4, NULL),
('com-5', '210001', 'pro-44', 'emp-29', '2012-01-15', '14:30:56', 'Nota Venta', '27.73', '27.73', NULL, NULL, '65 bs/ 91 mtr (rollo)', 'usr-1000', 5, NULL),
('com-6', '210001', 'pro-44', 'emp-29', '2012-01-15', '14:34:28', 'Nota Venta', '15.84', '15.84', NULL, NULL, '110 bs/500 uds', 'usr-1000', 6, NULL),
('com-7', '210002', 'pro-44', 'emp-29', '2012-01-15', '15:03:58', 'Nota Venta', '3.67', '3.67', NULL, NULL, '30 BS/ 25 MTR', 'usr-1000', 7, NULL),
('com-8', '210002', 'pro-44', 'emp-29', '2012-01-31', '15:13:08', 'Nota Venta', '46.07', '46.07', NULL, NULL, '55 Bs/88 MTR', 'usr-1000', 8, NULL),
('com-9', '210002', 'pro-44', 'emp-29', '2012-01-15', '15:17:42', 'Nota Venta', '3.18', '3.18', NULL, NULL, '30 BS/ 88 MTR', 'usr-1000', 9, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cortes`
--

CREATE TABLE `cortes` (
  `idcorte` varchar(20) NOT NULL,
  `op` int(11) default NULL,
  `iddetalleop` varchar(20) default NULL,
  `idtela` varchar(20) default NULL,
  `idmolde` varchar(20) default NULL,
  `nombrei` varchar(200) default NULL,
  `nombret` varchar(200) default NULL,
  `nombrem` varchar(200) default NULL,
  `tela` double(15,3) default NULL,
  `hoja` double(15,3) default NULL,
  `uds` double(15,3) default NULL,
  `totaltela` double(15,3) default NULL,
  `totalunidades` double(15,3) default NULL,
  `numero` int(11) default NULL,
  `idusuario` varchar(20) default NULL,
  `fecha` date default NULL,
  PRIMARY KEY  (`idcorte`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `cortes`
--

INSERT INTO `cortes` (`idcorte`, `op`, `iddetalleop`, `idtela`, `idmolde`, `nombrei`, `nombret`, `nombrem`, `tela`, `hoja`, `uds`, `totaltela`, `totalunidades`, `numero`, `idusuario`, `fecha`) VALUES
('cor-1', 1362, 'deo-1', 'mtp-3', 'mol-124', 'bolson mod trailer M', 'lona negro', '6221 bolson mod trailer', 0.970, 18.000, 1.000, 17.460, 18.000, 1, 'usr-1000', '2012-01-31');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cotizacion`
--

CREATE TABLE `cotizacion` (
  `idproducto` varchar(20) NOT NULL,
  `costounitario` decimal(11,2) default NULL,
  `preciounitario` decimal(11,2) default NULL,
  `cantidad` int(11) default NULL,
  `utilidad` decimal(11,2) default NULL,
  `utilidadtotal` decimal(11,2) default NULL,
  `preciototal` decimal(11,2) default NULL,
  `observacion` varchar(200) default NULL,
  PRIMARY KEY  (`idproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `cotizacion`
--

INSERT INTO `cotizacion` (`idproducto`, `costounitario`, `preciounitario`, `cantidad`, `utilidad`, `utilidadtotal`, `preciototal`, `observacion`) VALUES
('prd-1', '63.00', '71.00', 10, '8.00', '80.00', '710.00', 'BELLA HOLANDESAtnBELLA HOLANDESAtn'),
('prd-2', '562.00', '612.00', 0, '50.00', '0.00', '0.00', 'COTizacin de producto'),
('prd-3', '69.00', '0.00', 0, '0.00', '0.00', '0.00', '3 coloresnrojo azul verde'),
('prd-4', '119.00', '128.00', 11, '9.00', '99.00', '1408.00', NULL),
('prd-5', '0.00', '0.00', 0, '0.00', '0.00', '0.00', 'EL BORDADO FUE NE EL LADO DERECHO '),
('prd-6', '130.00', '0.00', 0, '0.00', '0.00', '0.00', NULL),
('prd-7', '130.00', '0.00', 0, '0.00', '0.00', '0.00', NULL),
('prd-8', '118.00', '122.00', 32, '4.00', '128.00', '3904.00', NULL),
('prd-81', '8.00', NULL, 0, NULL, '0.00', '0.00', 'POLERA POLO DE MUJER C/ CUELLO CADETE BOTONES C/ PUNO M'),
('prd-9', '39.00', '0.00', 0, '0.00', '0.00', '0.00', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credito`
--

CREATE TABLE `credito` (
  `idordenproduccion` varchar(20) NOT NULL,
  `idcliente` varchar(20) default NULL,
  `idempresa` varchar(20) default NULL,
  `total` decimal(11,2) default NULL,
  `saldo` decimal(11,2) default NULL,
  `observacion` varchar(20) default NULL,
  `responsable` varchar(20) default NULL,
  `fecha` date default NULL,
  PRIMARY KEY  (`idordenproduccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `credito`
--

INSERT INTO `credito` (`idordenproduccion`, `idcliente`, `idempresa`, `total`, `saldo`, `observacion`, `responsable`, `fecha`) VALUES
('ord-1', NULL, 'epr-46', '1530.00', NULL, NULL, 'res-44', '2012-01-04'),
('ord-2', NULL, 'epr-6', '97.00', NULL, NULL, 'res-108', '2012-01-04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `idcuenta` varchar(20) NOT NULL,
  `nombre` varchar(20) default NULL,
  `tipo` varchar(20) default NULL COMMENT '{DIARIO , MENSUAL , ANUAL}',
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`idcuenta`, `nombre`, `tipo`, `numero`) VALUES
('cue-2', 'refrigerio', 'cup-11', 2),
('cue-3', 'pasajes de taxis1', 'cup-10', 3),
('cue-4', 'viaticos', 'cup-12', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentaprincipal`
--

CREATE TABLE `cuentaprincipal` (
  `idcuentaprincipal` varchar(20) NOT NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `tipo` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idcuentaprincipal`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `cuentaprincipal`
--

INSERT INTO `cuentaprincipal` (`idcuentaprincipal`, `codigo`, `nombre`, `tipo`, `numero`) VALUES
('cup-1', 'uc', 'utilidad de la confeccion', 'ingresos', 1),
('cup-10', 'gtg', 'gastos de transporte gasolina taxis', 'Egresos', 10),
('cup-11', 'gig', 'gastos de insumos en general', 'Egresos', 11),
('cup-12', 'gag', 'gastos de alimentacion en general', 'Egresos', 12),
('cup-13', 'Iva', 'impuestos iva', 'Impuestos', 13),
('cup-2', 'ub', 'utilidad de bordado', 'ingresos', 2),
('cup-3', 'gb', 'utilidad para gastos de bordado', 'ingresos', 3),
('cup-4', 'gm', 'depreciacion de maquina', 'ingresos', 4),
('cup-5', 'gc', 'gastos de celular', 'ingresos', 5),
('cup-6', 'ge', 'gastos de envio', 'ingresos', 6),
('cup-7', 'uo', 'utilidad de otros gastos', 'ingresos', 7),
('cup-8', 'mpsf', 'personal salario fijo', 'Egresos', 8),
('cup-9', 'gee', 'gastos de envio', 'Egresos', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecompra`
--

CREATE TABLE `detallecompra` (
  `idcompra` varchar(20) default NULL,
  `iddetallecompra` varchar(20) NOT NULL,
  `detalle` varchar(100) default NULL,
  `idmateriaprima` varchar(20) default NULL,
  `unidad` varchar(20) default NULL,
  `cantidad` decimal(11,2) default NULL,
  `preciounitario` decimal(11,2) default NULL,
  `preciototal` decimal(11,2) default NULL,
  `numero` int(11) default NULL,
  `op` varchar(20) default NULL,
  PRIMARY KEY  (`iddetallecompra`),
  KEY `idcompra` (`idcompra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `detallecompra`
--

INSERT INTO `detallecompra` (`idcompra`, `iddetallecompra`, `detalle`, `idmateriaprima`, `unidad`, `cantidad`, `preciounitario`, `preciototal`, `numero`, `op`) VALUES
('com-1', 'dec-1', 'lona negro', 'mtp-3', 'metros', '17.50', '10.50', '183.75', 1, '1362'),
('com-10', 'dec-10', 'LIGA 2.5CM blanco', 'mtp-14', 'metros', '5.40', '0.70', '3.78', 10, '1362'),
('com-11', 'dec-11', 'MEDIA LUNA METALICA  plateado', 'mtp-15', 'unidades', '36.00', '1.00', '36.00', 11, '1362'),
('com-2', 'dec-2', 'ribet negro', 'mtp-4', 'metros', '130.15', '0.36', '46.85', 2, '1362'),
('com-3', 'dec-3', 'vena negro', 'mtp-5', 'metros', '48.78', '0.28', '13.66', 3, '1362'),
('com-4', 'dec-4', 'cinta de buso rojo', 'mtp-8', 'metros', '33.84', '0.18', '6.09', 4, '1362'),
('com-5', 'dec-5', 'cierre n8 negro', 'mtp-9', 'metros', '38.52', '0.72', '27.73', 5, '1362'),
('com-6', 'dec-6', 'carrito n8 negro', 'mtp-10', 'unidades', '72.00', '0.22', '15.84', 6, '1362'),
('com-7', 'dec-7', 'SCRACH 2.5CM negro', 'mtp-11', 'metros', '3.06', '1.20', '3.67', 7, '1362'),
('com-8', 'dec-8', 'CORREA 4CM negro', 'mtp-12', 'metros', '56.88', '0.81', '46.07', 8, '1362'),
('com-9', 'dec-9', 'CORREA 2.5CM negro', 'mtp-13', 'metros', '9.36', '0.34', '3.18', 9, '1362');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallecredito`
--

CREATE TABLE `detallecredito` (
  `iddetallecredito` varchar(20) NOT NULL,
  `idordenproduccion` varchar(20) default NULL,
  `importe` decimal(11,2) default NULL,
  `saldo` decimal(11,2) default NULL,
  `fecha` date default NULL,
  `idusuario` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`iddetallecredito`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `detallecredito`
--

INSERT INTO `detallecredito` (`iddetallecredito`, `idordenproduccion`, `importe`, `saldo`, `fecha`, `idusuario`, `numero`) VALUES
('dec-1', 'ord-1', NULL, NULL, '2012-01-04', 'usr-1000', 1),
('dec-2', 'ord-2', NULL, NULL, '2012-01-04', 'usr-1000', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalledistribucion`
--

CREATE TABLE `detalledistribucion` (
  `iddetalledistribucion` varchar(20) NOT NULL,
  `idmateriaprima` varchar(20) default NULL,
  `iddetalleorden` varchar(20) default NULL,
  `idempleado` varchar(20) default NULL,
  `uds` double(15,3) default NULL,
  `detalle` varchar(200) default NULL,
  `total` double(15,3) default NULL,
  `fecha` date default NULL,
  `iddistribucion` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `idusuario` varchar(20) default NULL,
  PRIMARY KEY  (`iddetalledistribucion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `detalledistribucion`
--

INSERT INTO `detalledistribucion` (`iddetalledistribucion`, `idmateriaprima`, `iddetalleorden`, `idempleado`, `uds`, `detalle`, `total`, `fecha`, `iddistribucion`, `numero`, `idusuario`) VALUES
('ddi-41', 'mtp-15', 'dis-1', 'emp-17', 2.000, 'MEDIA LUNA METALICA  plateado', 36.000, '2012-01-31', 'dis-1', 41, 'usr-1000'),
('ddi-42', 'mtp-13', 'dis-1', 'emp-17', 0.400, 'CORREA 2.5CM negro', 7.200, '2012-01-31', 'dis-1', 42, 'usr-1000'),
('ddi-43', 'mtp-13', 'dis-1', 'emp-17', 0.120, 'CORREA 2.5CM negro', 2.160, '2012-01-31', 'dis-1', 43, 'usr-1000'),
('ddi-44', 'mtp-5', 'dis-1', 'emp-17', 2.710, 'vena negro', 48.780, '2012-01-31', 'dis-1', 44, 'usr-1000'),
('ddi-45', 'mtp-4', 'dis-1', 'emp-17', 7.230, 'ribet negro', 130.140, '2012-01-31', 'dis-1', 45, 'usr-1000'),
('ddi-46', 'mtp-9', 'dis-1', 'emp-17', 2.140, 'cierre n8 negro', 38.520, '2012-01-31', 'dis-1', 46, 'usr-1000'),
('ddi-47', 'mtp-10', 'dis-1', 'emp-17', 4.000, 'carrito n8 negro', 72.000, '2012-01-31', 'dis-1', 47, 'usr-1000'),
('ddi-48', 'mtp-8', 'dis-1', 'emp-17', 1.880, 'cinta de buso rojo', 33.840, '2012-01-31', 'dis-1', 48, 'usr-1000'),
('ddi-49', 'mtp-11', 'dis-1', 'emp-17', 0.170, 'SCRACH 2.5CM negro', 3.060, '2012-01-31', 'dis-1', 49, 'usr-1000'),
('ddi-50', 'mtp-12', 'dis-1', 'emp-17', 3.160, 'CORREA 4CM negro', 56.880, '2012-01-31', 'dis-1', 50, 'usr-1000'),
('ddi-51', 'mtp-14', 'dis-1', 'emp-17', 0.300, 'LIGA 2.5CM blanco', 5.400, '2012-01-31', 'dis-1', 51, 'usr-1000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleorden`
--

CREATE TABLE `detalleorden` (
  `iddetalleorden` varchar(20) NOT NULL,
  `idordenproduccion` varchar(20) default NULL,
  `idproducto` varchar(20) default NULL,
  `detalle` varchar(20) default NULL,
  `talla` varchar(20) default NULL,
  `preciounitario` double(15,3) default NULL,
  `cantidad` int(11) default NULL,
  `total` double(15,3) default NULL,
  `numero` int(11) default NULL,
  `saldocantidad` int(12) default NULL,
  `idtela` varchar(20) default NULL,
  `idcolor` varchar(20) default NULL,
  `detalleitem` varchar(200) default NULL,
  `detallebordado` varchar(200) default NULL,
  `detallecostura` varchar(200) default NULL,
  `estado` varchar(20) default NULL,
  PRIMARY KEY  (`iddetalleorden`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `detalleorden`
--

INSERT INTO `detalleorden` (`iddetalleorden`, `idordenproduccion`, `idproducto`, `detalle`, `talla`, `preciounitario`, `cantidad`, `total`, `numero`, `saldocantidad`, `idtela`, `idcolor`, `detalleitem`, `detallebordado`, `detallecostura`, `estado`) VALUES
('deo-1', 'ord-1', 'prd-170', 'bolson mod trailer', 'M', 85.000, 18, 1530.000, 1, 18, 'mtp-3', 'col-10', 'cinta roja', '3m', 'xx', 'completado'),
('deo-2', 'ord-2', 'prd-171', 'polera tipo polo de ', 'S', 48.500, 2, 97.000, 2, 2, 'mtp-16', 'col-19', 'n', 'pi kimberly clark proffesional', 'n', 'completado'),
('deo-3', 'ord-2', 'prd-171', 'polera tipo polo de ', 'M', 48.500, 20, 970.000, 3, 20, 'mtp-16', 'col-19', 'n', 'pi kimberly clark proffesional', 'n', 'completado'),
('deo-4', 'ord-2', 'prd-171', 'polera tipo polo de ', 'L', 48.500, 4, 194.000, 4, 4, 'mtp-16', 'col-19', 'n', 'kimberly clark proffesional', 'n', 'completado'),
('deo-5', 'ord-2', 'prd-172', 'poleras tipo polo de', 'M', 54.000, 1, 54.000, 5, 1, 'mtp-16', 'col-19', 'n', 'kimberly clark proffesional', 'n', 'completado'),
('deo-6', 'ord-2', 'prd-172', 'polera polo varÃ³n', 'M', 48.500, 8, 388.000, 6, 8, 'mtp-16', 'col-19', 'MANGA CORTA CON PUÃ‘O', 'PI: KIMBERLY CLARK PROFESSIONAL', 'N', 'completado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleproduccionproceso`
--

CREATE TABLE `detalleproduccionproceso` (
  `iddetalleproduccionproceso` varchar(20) NOT NULL,
  `idproduccionproceso` varchar(20) NOT NULL,
  `detalle` varchar(20) default NULL,
  `unidad` varchar(20) default NULL,
  `cantidad` varchar(20) default NULL,
  `id` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `tipo` varchar(20) default NULL,
  PRIMARY KEY  (`iddetalleproduccionproceso`),
  KEY `idproduccionproceso` (`idproduccionproceso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `detalleproduccionproceso`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `distribuciones`
--

CREATE TABLE `distribuciones` (
  `iddistribucion` varchar(20) NOT NULL,
  `iddetalleorden` varchar(20) default NULL,
  `idempleado` varchar(20) default NULL,
  `detalle` varchar(200) default NULL,
  `encargado` varchar(100) default NULL,
  `cantidad` double(15,3) default NULL,
  `fecha` date default NULL,
  `op` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `idusuario` varchar(20) default NULL,
  PRIMARY KEY  (`iddistribucion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `distribuciones`
--

INSERT INTO `distribuciones` (`iddistribucion`, `iddetalleorden`, `idempleado`, `detalle`, `encargado`, `cantidad`, `fecha`, `op`, `numero`, `idusuario`) VALUES
('dis-1', 'deo-1', 'emp-17', 'bolson mod trailer M', 'eddy', 18.000, '2012-01-14', '1362', 1, 'usr-1000');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `egreso`
--

CREATE TABLE `egreso` (
  `idegreso` varchar(20) NOT NULL,
  `fecha` date default NULL,
  `hora` time default NULL,
  `idcuenta` varchar(20) default NULL,
  `idempleado` varchar(20) default NULL,
  `total` decimal(11,2) default NULL,
  `detalle` varchar(200) default NULL,
  `numero` int(11) default NULL,
  `ordenproduccion` varchar(20) default NULL,
  PRIMARY KEY  (`idegreso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `egreso`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `idempleado` varchar(20) NOT NULL,
  `idcargo` varchar(20) default NULL,
  `idciudad` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `apellido1` varchar(50) default NULL,
  `direccion` varchar(200) default NULL,
  `telefono` varchar(20) default NULL,
  `celular` varchar(20) default NULL,
  `estado` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `descripcion` varchar(100) default NULL,
  `email` varchar(50) default NULL,
  `fechabaja` timestamp NOT NULL default '0000-00-00 00:00:00',
  `fechainicio` date default NULL,
  `refnombre` varchar(200) default NULL,
  `reftelefono` varchar(20) default NULL,
  PRIMARY KEY  (`idempleado`),
  KEY `fk_relationship_11` (`idcargo`),
  KEY `fk_relationship_12` (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`idempleado`, `idcargo`, `idciudad`, `codigo`, `nombre`, `apellido1`, `direccion`, `telefono`, `celular`, `estado`, `numero`, `descripcion`, `email`, `fechabaja`, `fechainicio`, `refnombre`, `reftelefono`) VALUES
('emp-1', 'car-11', 'ciu-1', 'e1', 'alejandra ', 'parada', NULL, '4539477', '72772719', 'Activo', 1, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-10', 'car-20', 'ciu-1', 'e10', 'brayle', NULL, NULL, NULL, '70728178', 'Activo', 10, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-11', 'car-7', 'ciu-1', 'e11', 'saul', 'cordova', NULL, '4442728', '67550880', 'Activo', 11, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-12', 'car-5', 'ciu-1', 'e12', 'costa', 'acapa', NULL, NULL, '77935562', 'Activo', 12, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-13', 'car-15', 'ciu-1', 'e13', 'rosario (charito)', 'heredia', NULL, NULL, '76442347', 'Activo', 13, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-14', 'car-7', 'ciu-1', 'e2', 'cristian felipe', 'zegarra', NULL, '4333678', NULL, 'Activo', 14, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-15', 'car-7', 'ciu-1', 'e14', 'ruben', 'copatiti', NULL, '4755791', NULL, 'Activo', 15, NULL, NULL, '0000-00-00 00:00:00', '1969-12-31', NULL, NULL),
('emp-16', 'car-7', 'ciu-1', 'e15', 'carlos', 'revollo', NULL, NULL, '72267493', 'Activo', 16, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-17', 'car-7', 'ciu-1', 'e16', 'eddy', 'vargas', NULL, '4446942', '71760123', 'Activo', 17, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-18', 'car-7', 'ciu-1', 'e17', 'elias', 'tola', NULL, NULL, '76929124', 'Activo', 18, NULL, NULL, '0000-00-00 00:00:00', '1969-12-31', NULL, NULL),
('emp-19', 'car-7', 'ciu-1', 'e18', 'elias', 'vegamonte', NULL, NULL, '79750379', 'Activo', 19, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-20', 'car-7', 'ciu-1', 'e19', 'emigdio', 'choque', NULL, NULL, '72731095', 'Activo', 20, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-21', 'car-18', 'ciu-1', 'e20', 'herbert', 'navarro', NULL, '4445523', '70776706', 'Activo', 21, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-22', 'car-7', 'ciu-1', 'e21', 'hernan', 'miranda', NULL, NULL, '71460647', 'Activo', 22, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-23', 'car-7', 'ciu-1', 'e22', 'iris alina', 'flores bautista', NULL, NULL, '77991790', 'Activo', 23, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-24', 'car-9', 'ciu-1', 'e23', 'jorge', 'arias', NULL, '4445318', '77438044', 'Activo', 24, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-25', 'car-7', 'ciu-1', 'e24', 'justino', 'choque', 'antezana #338', NULL, '71717607', 'Activo', 25, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-26', 'car-7', 'ciu-1', 'e25', 'jose', 'vaca', NULL, NULL, '74330549', 'Activo', 26, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-27', 'car-7', 'ciu-1', 'e26', 'jose luis', 'barreto', 'blanco galindo y beijin ', NULL, '77453233', 'Activo', 27, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-28', 'car-7', 'ciu-1', 'e27', 'jaime', 'soliz', NULL, '4748914', '60760477', 'Activo', 28, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-29', 'car-19', 'ciu-1', 'e28', 'joaquin', 'nogales', 'zenon salinas #1036', '4245664', '73756146', 'Activo', 29, NULL, 'roy_fireemblem@hotmail.com', '0000-00-00 00:00:00', '2010-12-13', NULL, NULL),
('emp-3', 'car-7', 'ciu-1', 'e3', 'alcira ', 'flores', NULL, '4731096', '76956330', 'Activo', 3, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-30', 'car-15', 'ciu-1', 'e29', 'maria', 'herbas', NULL, '4268904', '72403368', 'Activo', 30, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-31', 'car-5', 'ciu-1', 'e30', 'pamela', 'flores', NULL, '4730123', '65706052', 'Activo', 31, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-32', 'car-9', 'ciu-1', 'e31', 'pablo', 'orellana', NULL, '4545103', '70726525', 'Activo', 32, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-33', 'car-7', 'ciu-1', 'e32', 'jose', 'torrico', 'joya esq trullillo #6012 zona villa pagador', NULL, '70367152', 'Activo', 33, NULL, NULL, '0000-00-00 00:00:00', '1969-12-31', NULL, NULL),
('emp-34', 'car-21', 'ciu-1', 'e33', 'virginia', 'apaza leon', NULL, NULL, '79753234', 'Activo', 34, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-35', 'car-7', 'ciu-1', 'e34', 'willian', 'calle', NULL, NULL, '72467178', 'Activo', 35, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-36', 'car-20', 'ciu-1', '5190630', 'ubaldo ', 'villazon', '9999', 'i999', '99999', 'Activo', 36, NULL, 'kasdjahdkj', '0000-00-00 00:00:00', '2011-12-20', 'natalio villazon11', '4255424111'),
('emp-4', 'car-7', 'ciu-1', 'e4', 'ariel', 'yugar', NULL, NULL, '60783372', 'Activo', 4, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-5', 'car-7', 'ciu-1', 'e5', 'americo', 'unoques', NULL, NULL, '75540518', 'Activo', 5, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-6', 'car-7', 'ciu-1', 'e6', 'ifronia', 'nese', NULL, NULL, '71461934', 'Activo', 6, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-7', 'car-7', 'ciu-1', 'e7', 'americo', 'choque', NULL, NULL, '70359018', 'Activo', 7, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-8', 'car-6', 'ciu-1', 'e8', 'abner', 'ancasi', NULL, NULL, NULL, 'Activo', 8, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL),
('emp-9', 'car-7', 'ciu-1', 'e9', 'ana', 'ajuacho', NULL, NULL, '71784869', 'Activo', 9, NULL, NULL, '0000-00-00 00:00:00', '0000-00-00', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresas`
--

CREATE TABLE `empresas` (
  `idempresa` varchar(20) NOT NULL,
  `idciudad` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `direccion` varchar(200) default NULL,
  `telefono` varchar(20) default NULL,
  `fax` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `fechareg` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `email` varchar(50) default NULL,
  `descripcion` varchar(100) default NULL,
  `estado` varchar(20) default NULL,
  `nit` varchar(20) default NULL,
  `responsable` varchar(100) default NULL,
  `paginaweb` varchar(200) default NULL,
  `fechaaniversario` date default NULL,
  PRIMARY KEY  (`idempresa`),
  KEY `fk_relationship_8` (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `empresas`
--

INSERT INTO `empresas` (`idempresa`, `idciudad`, `codigo`, `nombre`, `direccion`, `telefono`, `fax`, `numero`, `fechareg`, `email`, `descripcion`, `estado`, `nit`, `responsable`, `paginaweb`, `fechaaniversario`) VALUES
('epr-10', 'ciu-1', 'emp009', 'pil andina s.a.', 'av. blanco galindo km. 10', '4260164', NULL, 10, '2011-06-20 18:56:04', 'equintanilla@pilandina.com.bo', NULL, 'Activo', '1020757027', 'edwin quintanilla', NULL, NULL),
('epr-11', 'ciu-1', 'emp010', 'faboce s.r.l.', 'av. villarroel km. 8 1/2', '4270088', NULL, 11, '2011-07-27 12:58:45', 'bpadilla@faboce.com.bo', NULL, 'Activo', '1023273024', 'bertha padilla', NULL, NULL),
('epr-12', 'ciu-1', 'emp011', 'industrias de aceite s.a.', 'av. cap. ustariz km. 10,5', '4262800', NULL, 12, '2011-07-18 15:38:29', 'impulsocba@fino.com.bo', NULL, 'Activo', '1023233029', 'neiza vega', NULL, NULL),
('epr-13', 'ciu-1', 'emp012', 'fondo de la comunidad', 'av. ballivian', '4523001', NULL, 13, '2011-07-18 15:38:54', 'sromero@fco.com.bo', NULL, 'Activo', '1023303022', 'silvia romero', NULL, NULL),
('epr-14', 'ciu-1', 'emp013', 'grupo e s.r.l.', 'av. salamanca, edif. fundes', '4662377', NULL, 14, '2011-07-18 15:39:35', 'adima.jaldin@grupo-e.net', NULL, 'Activo', '168408022', 'adima jaldin', NULL, NULL),
('epr-15', 'ciu-1', 'emp014', 'industrias ravi', 'av. cap ustariz km. 4', '4432165', NULL, 15, '2011-07-19 15:57:21', 'joseluisalmendras@gruporavi.com', NULL, 'Activo', '1023289022', 'jose luis almendras', NULL, NULL),
('epr-16', 'ciu-1', 'emp015', 'cucina', NULL, '4455228', '77915999', 16, '2011-07-19 16:03:31', NULL, NULL, 'Activo', NULL, 'gaston mendez', NULL, NULL),
('epr-17', 'ciu-1', 'emp016', 'heladeria gracia s.r.l.', 'a. heroinas y 25 de mayo', '4501300', NULL, 17, '2011-07-18 10:17:02', NULL, NULL, 'Activo', '152838029', 'lilian hoffmann', NULL, NULL),
('epr-18', 'ciu-1', 'emp017', 'panchita', 'eeee', NULL, NULL, 18, '2011-07-18 15:10:21', NULL, NULL, 'Activo', '4444', 'aaaa', NULL, NULL),
('epr-2', 'ciu-1', 'emp001', 'Unilever andina bolivia s.a.', 'av. blanco galindo km. 10.5', '4351515', NULL, 2, '2011-06-20 18:47:42', 'eduardo.velasco@unilever.com', 'adquisiciones', 'Activo', '1023225025', 'eduardo velasco', NULL, NULL),
('epr-20', 'ciu-1', 'emp019', 'imcabez s.R.l.', NULL, NULL, NULL, 20, '2011-07-18 12:57:33', NULL, NULL, 'Activo', '150188025', 'jean christian caussin', NULL, NULL),
('epr-22', 'ciu-1', 'emp021', 'compaÃ±Ã­a sadec cbba s.r.l.', NULL, '4503333', NULL, 22, '2011-07-18 15:36:01', NULL, NULL, 'Activo', '151474025', 'gilbert sampieri', NULL, NULL),
('epr-23', 'ciu-1', 'emp022', 'decurion', 'c. tarapaca nro.317 entre mayor rocha y ecuador', '4282953', NULL, 23, '2011-07-18 12:49:33', NULL, NULL, 'Activo', NULL, 'saul lopez', NULL, NULL),
('epr-24', 'ciu-1', 'emp023', 'urrutibehety', NULL, '4281858', NULL, 24, '2011-07-18 12:48:32', NULL, NULL, 'Activo', '1028691022', 'tania vasquez', NULL, NULL),
('epr-25', 'ciu-1', 'emp024', 'fabe', 'av. centenario', '4305014', NULL, 25, '2011-07-18 11:41:11', NULL, NULL, 'Activo', '1023251028', 'alejandro escalante', NULL, NULL),
('epr-26', 'ciu-1', 'emp025', 'fdta valles', 'av. salamanca, edif. sisteco, 2do piso', '4525160', NULL, 26, '2011-07-18 12:52:26', NULL, NULL, 'Activo', '1008355026', 'martha vega', NULL, NULL),
('epr-27', 'ciu-3', 'emp026', 'telecel s.a.', NULL, '77390389', NULL, 27, '2011-07-18 12:54:16', NULL, NULL, 'Activo', '1020255020', 'nathaly cuellar', NULL, NULL),
('epr-28', 'ciu-1', 'emp027', 'logo digital s.r.l.', NULL, '4489494', NULL, 28, '2011-07-18 12:55:10', NULL, NULL, 'Activo', '1022905029', 'alvaro garcÃ­a mesa', NULL, NULL),
('epr-29', 'ciu-1', 'emp028', 'entel s.a.', 'av. pando', '4125166', NULL, 29, '2011-07-18 12:56:00', NULL, NULL, 'Activo', '1020703023', 'maria luisa cespedes', NULL, NULL),
('epr-3', 'ciu-1', 'emp002', 'copelme', 'carretera sacaba km 4.5', '4720600', '4720601', 3, '2011-06-20 18:47:50', 'negabujder@yahoo.com', NULL, 'Activo', '1023095027', 'carmen abujder', NULL, NULL),
('epr-30', 'ciu-1', 'emp029', 'fridosa', 'c. haynataqui y melchor perez', '800103738', NULL, 30, '2011-07-18 12:57:13', NULL, NULL, 'Activo', '1028547029', 'vladimir cosio', NULL, NULL),
('epr-33', 'ciu-1', 'emp032', 'modena cafe', NULL, NULL, NULL, 33, '2011-07-18 15:03:11', NULL, NULL, 'Activo', NULL, NULL, NULL, NULL),
('epr-34', 'ciu-1', 'emp018', 'minoil s.a.', NULL, '4434918', NULL, 34, '2011-07-18 15:37:40', NULL, NULL, 'Activo', '1020317028', 'vanessa mora', NULL, NULL),
('epr-35', 'ciu-1', 'emp030', 'gedesa ltda.', 'c. antezana entre colombia y ecuador', '4523227', NULL, 35, '2011-07-18 15:41:31', NULL, NULL, 'Activo', '1023143022', 'guelly arevalo', NULL, NULL),
('epr-36', 'ciu-1', 'emp031', 'maxam fanexa sam', 'av. del ejercito', '4232816', NULL, 36, '2011-07-18 15:44:39', NULL, NULL, 'Activo', '1023249027', 'melvy rodriguez', NULL, NULL),
('epr-37', 'ciu-1', 'emp033', 'rotular', 'c. pedro blanco y av. santa cruz, lado viva', '4799067', NULL, 37, '2011-07-18 15:46:02', NULL, NULL, 'Activo', '3743769011', 'paola angulo', NULL, NULL),
('epr-38', 'ciu-1', 'emp034', 'organica del sur s.r.l.', 'av. cap. ustariz km. 8.5', '4379000', NULL, 38, '2011-07-18 15:48:52', NULL, NULL, 'Activo', '176302029', 'claudia villca', NULL, NULL),
('epr-39', 'ciu-1', 'emp035', 'cadexco', 'av. kyllman nro. 1681', '4599421', NULL, 39, '2011-07-18 16:13:29', NULL, NULL, 'Activo', '1020783021', 'ana marÃ­a vega', NULL, NULL),
('epr-4', 'ciu-1', 'emp003', 'embol s.a.', 'av. blanco galindo km.10', '4263000', NULL, 4, '2011-06-20 18:47:56', 'hgalindo@embol-sa.com', NULL, 'Activo', '1007039026', 'hugo galindo', NULL, NULL),
('epr-40', 'ciu-1', 'emp036', 'compacto s.r.l', NULL, NULL, NULL, 40, '2011-07-18 16:15:22', NULL, NULL, 'Activo', '1023253025', 'miriam claure', NULL, NULL),
('epr-41', 'ciu-1', 'emp037', 'save the children', NULL, '4248283', NULL, 41, '2011-07-18 16:16:57', NULL, NULL, 'Activo', '1018121029', 'boris jaldin', NULL, NULL),
('epr-42', 'ciu-1', 'emp038', 'unitepc', NULL, '4258862', NULL, 42, '2011-07-18 16:18:15', NULL, NULL, 'Activo', '1009199021', 'sonia zambrana', NULL, NULL),
('epr-43', 'ciu-1', 'emp039', 'bluecom', NULL, '4663100', NULL, 43, '2011-07-18 16:20:39', NULL, NULL, 'Activo', '173716027', 'janaina ribero', NULL, NULL),
('epr-44', 'ciu-1', 'emp040', 'trailer group', 'c. agustÃ­n aspiazu nro. 640', '4233928', '4542962', 44, '2011-07-18 16:28:41', 'marthamagne@trailer-group.com.bo', NULL, 'Activo', '1335586014', 'martha magne', NULL, NULL),
('epr-45', 'ciu-1', 'emp041', 'asociaciÃ³n ric', 'av. humboldt nro. 445', '4247213', '4286139', 45, '2011-07-19 12:58:56', NULL, NULL, 'Activo', '1007331022', 'ruth magne', NULL, NULL),
('epr-46', 'ciu-1', 'emp042', 'chromart s.r.l.', 'av. america este final y av. uyuni final nro. 200', '4483303', NULL, 46, '2011-07-19 13:00:33', NULL, NULL, 'Activo', '1020941022', 'cinthia espada', NULL, NULL),
('epr-47', 'ciu-1', 'emp043', 'multiagro s.a.', 'av. blanco galindo', NULL, NULL, 47, '2011-07-19 13:01:29', NULL, NULL, 'Activo', '130985020', NULL, NULL, NULL),
('epr-48', 'ciu-1', 'emp044', 'bellcos bolivia s.a.', 'av. uyuni', NULL, NULL, 48, '2011-07-19 13:03:39', NULL, NULL, 'Activo', '120771025', NULL, NULL, NULL),
('epr-49', 'ciu-5', 'emp045', 'cofan', 'pasaje armando alba nro. 80', '6453882', NULL, 49, '2011-07-19 13:05:05', NULL, NULL, 'Activo', '1029033025', 'luis fernando solares gumucio', NULL, NULL),
('epr-5', 'ciu-1', 'emp004', 'coboce', 'av. san martin, galeria chicago', '4257730', '4257733', 5, '2011-06-20 18:48:02', 'rcalderon@coboce.com', NULL, 'Activo', '1023299028', 'ing. ramiro calderon', NULL, NULL),
('epr-50', 'ciu-1', 'emp046', 'boliviana de aviaciÃ³n', 'c. jordan esquina nataniel aguirre', '4140873', NULL, 50, '2011-07-19 13:09:29', NULL, NULL, 'Activo', '154422029', 'cleidy zambrana', NULL, NULL),
('epr-51', 'ciu-1', 'emp047', 'ende', 'plaza colon', NULL, NULL, 51, '2011-07-19 13:11:15', NULL, NULL, 'Activo', '1023187029', 'ronald coronel', NULL, NULL),
('epr-52', 'ciu-1', 'emp020', 'tiny tots s.r.l.', NULL, NULL, NULL, 52, '2011-07-19 16:02:46', NULL, NULL, 'Activo', '165936022', 'carla villalobos', NULL, NULL),
('epr-53', 'ciu-1', 'emp048', 'multiinternacional', NULL, '4299405', NULL, 53, '2011-07-19 16:55:22', NULL, NULL, 'Activo', NULL, 'jessica roa', NULL, NULL),
('epr-54', 'ciu-1', 'emp049', 'sicme', NULL, '4582880', NULL, 54, '2011-07-19 17:01:17', NULL, NULL, 'Activo', NULL, 'remberto quina', NULL, NULL),
('epr-55', 'ciu-1', 'emp050', 'aceite granos', NULL, '4415156', NULL, 55, '2011-07-20 17:45:52', NULL, NULL, 'Activo', NULL, 'david pozo', NULL, NULL),
('epr-56', 'ciu-1', 'emp051', 'bolhispania', NULL, NULL, NULL, 56, '2011-07-20 17:52:08', NULL, NULL, 'Activo', NULL, NULL, NULL, NULL),
('epr-57', 'ciu-1', 'emp052', 'fabrica de fideos condor', NULL, '4434826', NULL, 57, '2011-07-21 16:22:22', NULL, NULL, 'Activo', NULL, 'marco arandia', NULL, NULL),
('epr-58', 'ciu-1', 'emp053', 'norland', 'carretera sacaba, km.5', '4118364', NULL, 58, '2011-07-25 10:53:51', NULL, NULL, 'Activo', NULL, 'diana moya', NULL, NULL),
('epr-59', 'ciu-3', 'emp054', 'global milenio', NULL, '76348282', NULL, 59, '2011-07-29 10:46:50', NULL, NULL, 'Activo', NULL, 'iris ibarra', NULL, NULL),
('epr-6', 'ciu-1', 'emp005', 'kimberly bolivia s.a.', 'av. santa cruz', '4154900', NULL, 6, '2011-06-20 18:49:45', 'maria.e.jimenez@kcc.com', NULL, 'Activo', '1028633023', 'maria elena jimenez', NULL, NULL),
('epr-60', 'ciu-1', 'emp055', 'distribuidora el abasto', NULL, NULL, NULL, 60, '2011-07-29 12:03:50', NULL, NULL, 'Activo', NULL, 'cesar paz', NULL, NULL),
('epr-61', 'ciu-1', 'emp056', 'wilda', NULL, '4725050', NULL, 61, '2011-08-18 12:57:14', 'wilda@supernet.com.bo', NULL, 'Activo', NULL, 'daniel delgadillo', NULL, NULL),
('epr-62', 'ciu-1', 'emp057', 'otras empresas', NULL, NULL, NULL, 62, '2011-09-21 15:48:55', NULL, NULL, 'Activo', NULL, NULL, NULL, NULL),
('epr-63', 'ciu-1', 'emp058', 'honorable alcaldÃ­a municipal', NULL, NULL, NULL, 63, '2011-09-21 15:49:22', NULL, NULL, 'Activo', NULL, NULL, NULL, NULL),
('epr-64', 'ciu-1', 'emp059', 'prefectura de cochabamba', NULL, NULL, NULL, 64, '2011-09-21 15:56:39', NULL, NULL, 'Activo', NULL, NULL, NULL, NULL),
('epr-7', 'ciu-5', 'emp006', 'fancesa', 'pasaje alba nro. 80', '6453882', NULL, 7, '2011-06-20 18:51:40', 'l.solares@fancesa.com', NULL, 'Activo', '1016259020', 'luis fernando solares', NULL, NULL),
('epr-8', 'ciu-1', 'emp007', 'chromart s.r.l.', 'av. america este final y av. uyuni final nro. 200', '4483303', NULL, 8, '2011-06-20 18:53:00', 'cuentas@chromart.com.bo', NULL, 'Activo', '1020941022', 'cinthia espada', NULL, NULL),
('epr-9', 'ciu-3', 'emp008', 'freelo', 'equipetrol c. 7 oeste nro. 12', '3524348', NULL, 9, '2011-07-19 13:06:14', 'pfrias@freelo.com.bo', NULL, 'Activo', '134651028', 'paola frias', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcion`
--

CREATE TABLE `funcion` (
  `idfuncion` varchar(20) NOT NULL,
  `idcategoriafuncion` varchar(20) default NULL,
  `descripcion` varchar(100) default NULL,
  `mostrar` varchar(20) default NULL,
  PRIMARY KEY  (`idfuncion`),
  KEY `fk_relationship_4` (`idcategoriafuncion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `funcion`
--

INSERT INTO `funcion` (`idfuncion`, `idcategoriafuncion`, `descripcion`, `mostrar`) VALUES
('fun1000', 'caf1000', 'usuarios', 'si'),
('fun1001', 'caf1000', 'roles', 'si'),
('fun1002', 'caf10001', 'proveedores', 'si'),
('fun1003', 'caf1000', 'ciudades', 'si'),
('fun1004', 'caf1000', 'Personal', 'si'),
('fun1005', 'caf1000', 'cargos', 'si'),
('fun1006', 'caf1000', 'clientes', 'si'),
('fun1007', 'caf1000', 'empresas', 'si'),
('fun1008', 'caf1000', 'responsables', 'si'),
('fun1009', 'caf10001', 'categorias', 'si'),
('fun1010', 'caf1000', 'unidades', 'si'),
('fun1011', 'caf10001', 'colores', 'si'),
('fun1012', 'caf1000', 'procesos', 'si'),
('fun1013', 'caf10002', 'Categoria Productos', 'si'),
('fun1014', 'caf1000', 'Cuentas Principales', 'si'),
('fun2000', 'caf10001', 'materia prima', 'si'),
('fun2001', 'caf10001', 'Inventario Materia Prima', 'si'),
('fun2002', 'caf10001', 'Realizar Compra', 'si'),
('fun2003', 'caf10001', 'Compras', 'si'),
('fun2004', 'caf1001', 'Grupos', 'no'),
('fun2006', 'caf10002', 'Productos', 'si'),
('fun2007', 'caf1001', 'Bordados', 'si'),
('fun2010', 'caf10002', 'Inventario Productos', 'si'),
('fun2011', 'caf1001', 'Moldes', 'si'),
('fun3000', 'caf1002', 'cuentas', 'si'),
('fun3001', 'caf1002', 'Listar Egresos', 'si'),
('fun3002', 'caf1002', 'Realizar Egreso', 'si'),
('fun4000', 'caf1003', 'Orden De Produccion', 'si'),
('fun4001', 'caf1003', 'Listas de Ordenes', 'si'),
('fun5000', 'caf1004', 'Produccion de Procesos', 'si'),
('fun5001', 'caf1004', 'Listar Produccion de Procesos', 'si'),
('fun6000', 'caf1005', 'Consultas Materia Prima', 'si'),
('fun6001', 'caf1005', 'Stock Materia Prima', 'si'),
('fun6002', 'caf1005', 'Consulta Productos', 'si'),
('fun7000', 'caf1006', 'realizar corte', 'si'),
('fun8000', 'caf1007', 'Proceso Distribucion', 'si'),
('fun8001', 'caf1007', 'Recepciones', 'si'),
('fun9000', 'caf1008', 'Entrega Nota De Remision', 'si');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupos`
--

CREATE TABLE `grupos` (
  `idgrupo` varchar(20) NOT NULL,
  `nombre` varchar(20) default NULL,
  `descripcion` varchar(200) default NULL,
  `numero` int(11) default NULL,
  `costo` decimal(11,2) default NULL,
  PRIMARY KEY  (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `grupos`
--

INSERT INTO `grupos` (`idgrupo`, `nombre`, `descripcion`, `numero`, `costo`) VALUES
('gru-1', 'VARIOS', 'VARIOS', 1, '0.00'),
('gru-2', 'logos', 'lOGO / bORDADO', 2, '39.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `kardexmateriaprima`
--

CREATE TABLE `kardexmateriaprima` (
  `idkardexmateriaprima` varchar(20) NOT NULL,
  `idmateriaprima` varchar(20) default NULL,
  `fechamodificacion` date default NULL,
  `saldocantidad` decimal(11,2) default NULL,
  `saldobs` decimal(11,2) default NULL,
  `cantidad` decimal(11,2) default NULL,
  `precio1bs` decimal(11,2) default NULL,
  `precio1sus` decimal(11,2) default NULL,
  `costounitario` decimal(11,2) default NULL,
  `precio2bs` decimal(11,2) default NULL,
  `precio2sus` decimal(11,2) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idkardexmateriaprima`),
  KEY `idmateriaprima` (`idmateriaprima`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `kardexmateriaprima`
--

INSERT INTO `kardexmateriaprima` (`idkardexmateriaprima`, `idmateriaprima`, `fechamodificacion`, `saldocantidad`, `saldobs`, `cantidad`, `precio1bs`, `precio1sus`, `costounitario`, `precio2bs`, `precio2sus`, `numero`) VALUES
('mtp-1', 'mtp-1', '2012-01-25', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 1),
('mtp-10', 'mtp-10', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.22', '0.00', '0.00', 10),
('mtp-11', 'mtp-11', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '1.20', '0.00', '0.00', 11),
('mtp-12', 'mtp-12', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.81', '0.00', '0.00', 12),
('mtp-13', 'mtp-13', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.34', '0.00', '0.00', 13),
('mtp-14', 'mtp-14', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.70', '0.00', '0.00', 14),
('mtp-15', 'mtp-15', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '1.00', '0.00', '0.00', 15),
('mtp-16', 'mtp-16', '2012-02-07', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 16),
('mtp-2', 'mtp-2', '2012-01-25', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', 2),
('mtp-3', 'mtp-3', '2012-01-31', '0.04', '0.42', '0.00', '0.00', '0.00', '10.50', '0.00', '0.00', 3),
('mtp-4', 'mtp-4', '2012-01-31', '0.01', '0.00', '0.00', '0.00', '0.00', '0.36', '0.00', '0.00', 4),
('mtp-5', 'mtp-5', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.28', '0.00', '0.00', 5),
('mtp-8', 'mtp-8', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.18', '0.00', '0.00', 8),
('mtp-9', 'mtp-9', '2012-01-31', '0.00', '0.00', '0.00', '0.00', '0.00', '0.72', '0.00', '0.00', 9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materiaprima`
--

CREATE TABLE `materiaprima` (
  `idmateriaprima` varchar(20) NOT NULL,
  `idcategoria` varchar(20) default NULL,
  `idcolor` varchar(20) default NULL,
  `idunidad` varchar(20) default NULL,
  `nombre` varchar(20) default NULL,
  `caracteristica` varchar(2000) default NULL,
  `stockminimo` int(11) default NULL,
  `estado` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `codigo` varchar(20) default NULL,
  `calidad` varchar(20) default NULL,
  `imagen` varchar(2000) default NULL,
  PRIMARY KEY  (`idmateriaprima`),
  KEY `idcategoria` (`idcategoria`),
  KEY `idcolor` (`idcolor`),
  KEY `idunidad` (`idunidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `materiaprima`
--

INSERT INTO `materiaprima` (`idmateriaprima`, `idcategoria`, `idcolor`, `idunidad`, `nombre`, `caracteristica`, `stockminimo`, `estado`, `numero`, `codigo`, `calidad`, `imagen`) VALUES
('mtp-1', 'cat-1', 'col-112', 'uni-1', 'tela algodon', 'ninguno', 10, 'Activo', 1, NULL, 'ninguno', NULL),
('mtp-10', 'cat-17', 'col-10', 'uni-5', 'carrito n8', 'metalico', 10, 'Activo', 10, NULL, 'metal', NULL),
('mtp-11', 'cat-6', 'col-10', 'uni-1', 'SCRACH 2.5CM', '2.5 CM DE ANCHO', 5, 'Activo', 11, NULL, 'UNICA', NULL),
('mtp-12', 'cat-5', 'col-10', 'uni-1', 'CORREA 4CM', '4 CM DE ANCHO', 5, 'Activo', 12, NULL, 'UNICA', NULL),
('mtp-13', 'cat-5', 'col-10', 'uni-1', 'CORREA 2.5CM', 'ANCHO DE 2.5 CM', 5, 'Activo', 13, NULL, 'UNICA', NULL),
('mtp-14', 'cat-15', 'col-3', 'uni-1', 'LIGA 2.5CM', '19 BS / ROLLO', 10, 'Activo', 14, NULL, 'UNICA', NULL),
('mtp-15', 'cat-18', 'col-17', 'uni-5', 'MEDIA LUNA METALICA ', '220 UDS/ 220 BS', 10, 'Activo', 15, NULL, 'METALICA', NULL),
('mtp-16', 'cat-1', 'col-19', 'uni-1', 'pique', NULL, 2, 'Activo', 16, NULL, 'unica', NULL),
('mtp-2', 'cat-1', 'col-33', 'uni-1', 'tela algodon', 'niguno', 10, 'Activo', 2, NULL, 'ninguno', NULL),
('mtp-3', 'cat-1', 'col-10', 'uni-1', 'lona', 'lona normal', 1, 'Activo', 3, NULL, 'unica', NULL),
('mtp-4', 'cat-4', 'col-10', 'uni-1', 'ribet', '2cm de ancho', 5, 'Activo', 4, NULL, '2da', NULL),
('mtp-5', 'cat-8', 'col-10', 'uni-1', 'vena', NULL, 5, 'Activo', 5, NULL, 'unica', NULL),
('mtp-8', 'cat-11', 'col-1', 'uni-1', 'cinta de buso', '1cm ancho', 10, 'Activo', 8, NULL, 'unica', NULL),
('mtp-9', 'cat-3', 'col-10', 'uni-1', 'cierre n8', 'por metro', 10, 'Activo', 9, NULL, 'unica', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `moldes`
--

CREATE TABLE `moldes` (
  `idmolde` varchar(20) NOT NULL,
  `idcategoria` varchar(20) default NULL,
  `nombre` varchar(200) default NULL,
  `codigo` varchar(20) default NULL,
  `imagen` varchar(300) default NULL,
  `numero` int(20) default NULL,
  `observacion` varchar(300) default NULL,
  `sastre` double(15,3) default NULL,
  `hilo` double(15,3) default NULL,
  PRIMARY KEY  (`idmolde`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `moldes`
--

INSERT INTO `moldes` (`idmolde`, `idcategoria`, `nombre`, `codigo`, `imagen`, `numero`, `observacion`, `sastre`, `hilo`) VALUES
('mol-100', 'ctp-8', 'chamarra ignacio 2', '67', NULL, 100, NULL, NULL, NULL),
('mol-101', 'ctp-8', 'chamarra unilever', '68', NULL, 101, 'molde de 10 piezas o.p. 832', NULL, NULL),
('mol-102', 'ctp-8', 'chamarra de tigo polar', '69', NULL, 102, NULL, NULL, NULL),
('mol-103', 'ctp-8', 'chamarra angel jurado', '70', NULL, 103, 'molde de 8 piezas', NULL, NULL),
('mol-104', 'ctp-8', 'chamarra fridosa', '71', NULL, 104, 'molde de 9 piezas', NULL, NULL),
('mol-105', 'ctp-8', 'chamarra de oxfort o lona', '72', NULL, 105, 'molde de 12 piezas', NULL, NULL),
('mol-106', 'ctp-8', 'chamarra deportiva para cite', '73', NULL, 106, 'o.p. 1019', NULL, NULL),
('mol-107', 'ctp-8', 'chamarra de skip', '74', NULL, 107, 'o.p. 1106', NULL, NULL),
('mol-108', 'ctp-8', 'chamarra negro y rojo', '75', NULL, 108, NULL, NULL, NULL),
('mol-109', 'ctp-8', 'chamarra polar varon y mujer', '76', NULL, 109, 'molde de 8 piezas', NULL, NULL),
('mol-11', 'ctp-7', 'chaleco termico freelo', '1', NULL, 11, 'compuesto de 10 piezas #1075 anteriormente', NULL, NULL),
('mol-110', 'ctp-19', 'guardapolvo de urrutibety', '1', NULL, 110, 'molde de 27 piezas #1171 anteriormente', NULL, NULL),
('mol-111', 'ctp-19', 'guardapolvo corto', '2', NULL, 111, 'molde de 9 piezas #1166 anteriormente', NULL, NULL),
('mol-112', 'ctp-19', 'guardapolvo de mujer copelme', '3', NULL, 112, 'molde de 28 piezas #1137 anteriormente', NULL, NULL),
('mol-113', 'ctp-19', 'guardapolvo de fino doctor', '4', NULL, 113, 'molde de 22 piezas #1118 anteriormente', NULL, NULL),
('mol-114', 'ctp-19', 'guardapolvo de trabajo', '5', NULL, 114, 'molde de 35 piezas #1117 anteriormente trailer y tambien maxam', NULL, NULL),
('mol-115', 'ctp-19', 'guardapolvo largo', '6', NULL, 115, 'molde de 33 piezas #1046 anteriormente', NULL, NULL),
('mol-116', 'ctp-19', 'guardapolvo de cocinero', '7', NULL, 116, 'molde de 7 piezas #1146 anteriormente', NULL, NULL),
('mol-117', 'ctp-19', 'guardapolvo guarderia', '8', 'php/uploads/molde_1312585310.JPG', 117, 'molde de 8 piezas #1147,1148 anteriormente', NULL, NULL),
('mol-118', 'ctp-19', 'guardapolvo de multi agro', '9', NULL, 118, 'molde de 8 piezas #1149 anteriormente', NULL, NULL),
('mol-119', 'ctp-19', 'guardapolvo de sadec', '10', NULL, 119, 'molde de 18 piezas #1150 anteriormente o.p.285', NULL, NULL),
('mol-12', 'ctp-7', 'chaleco ups', '2', NULL, 12, 'conformado de 11 piezas #1131 anteriormente', NULL, NULL),
('mol-120', 'ctp-19', 'guardapolvo de medico pedido uto', '11', NULL, 120, 'molde de 9 piezas #1151', NULL, NULL),
('mol-121', 'ctp-19', 'guardapolvo de chef grande', '12', NULL, 121, '#1152', NULL, NULL),
('mol-122', 'ctp-1', 'prueba1', '454871', NULL, 122, 'jjj1', 1.251, 0.123),
('mol-123', 'ctp-4', 'bolson mod. trailer', '3221', NULL, 123, 'unica talla', 12.000, 0.500),
('mol-124', 'ctp-4', 'bolson mod trailer', '6221', NULL, 124, 'talla unica', 12.000, 0.500),
('mol-13', 'ctp-7', 'chaleco cuello cadete copelme', '3', NULL, 13, 'conformado de 18 piezas #1100 anteriormente', NULL, NULL),
('mol-14', 'ctp-7', 'chaleco jean', '4', NULL, 14, 'conformado por 26 piezas #1085 anteriormente', NULL, NULL),
('mol-15', 'ctp-7', 'chaleco de entel', '5', NULL, 15, 'conformado de 5 piezas #1181 anteriormente. modelo sencillo', NULL, NULL),
('mol-16', 'ctp-7', 'chaleco entel', '6', NULL, 16, 'conformado 10 piezas #1181 anteriormente. este contiene mas bolsillos ', NULL, NULL),
('mol-17', 'ctp-7', 'chaleco de ret con bolsillos', '7', NULL, 17, 'conformado de 18 piezas #1007 anteriormente', NULL, NULL),
('mol-18', 'ctp-7', 'chaleco modelo silvia', '8', 'php/uploads/molde_1312585385.JPG', 18, 'conformado 60 piezas\n#1034 anteriormente ', NULL, NULL),
('mol-19', 'ctp-7', 'chaleco modelo scott de dos colores', '9', NULL, 19, 'conformado de 28 piezas #1167 anteriormente hecho para pil', NULL, NULL),
('mol-20', 'ctp-7', 'chaleco de sedal polar', '10', NULL, 20, 'molde de 9 piezas #1168 anteriormente', NULL, NULL),
('mol-21', 'ctp-7', 'chaleco de plastico', '11', NULL, 21, 'molde de 3 piezas #1125 anteriormente con cinta reflectiva', NULL, NULL),
('mol-22', 'ctp-7', 'chaleco de mujer peque;o', '12', NULL, 22, 'molde de 9 piezas #1189 anteriormente', NULL, NULL),
('mol-23', 'ctp-7', 'chaleco modelo silvia rios', '13', NULL, 23, 'molde de 20 piezas #1190 anteriormente', NULL, NULL),
('mol-24', 'ctp-7', 'chaleco maxam fosforecente', '14', NULL, 24, 'molde de 8 piezas', NULL, NULL),
('mol-25', 'ctp-7', 'chaleco de garzon', '15', NULL, 25, 'molde de 6 piezas', NULL, NULL),
('mol-26', 'ctp-7', 'chaleco ades con polar', '16', NULL, 26, 'molde 5 piezas hecho de taslan', NULL, NULL),
('mol-27', 'ctp-7', 'chaleco modelo maxam', '17', NULL, 27, 'molde de 10 piezas con cinta reflectiva', NULL, NULL),
('mol-28', 'ctp-7', 'chaleco de taslan con forro ', '18', NULL, 28, 'molde de 15 piezas #1114 anteriormente', NULL, NULL),
('mol-29', 'ctp-7', 'chaleco mod. polar alejandra de mujer', '19', NULL, 29, 'molde de 7 piezas #1115 anteriormente rojo con azul pastel', NULL, NULL),
('mol-30', 'ctp-7', 'chaleco motors-club-copelme', '20', NULL, 30, 'molde de 14 piezas #1116 anteriormente', NULL, NULL),
('mol-31', 'ctp-7', 'chaleco omo', '23', NULL, 31, 'molde de 14 piezas 0.p.1020', NULL, NULL),
('mol-32', 'ctp-7', 'chalecos amigos s.m.', '24', NULL, 32, 'molde de 6 piezas o.p.1077', NULL, NULL),
('mol-33', 'ctp-7', 'chaleco garzon senertlo', '25', NULL, 33, 'o.p. 968', NULL, NULL),
('mol-34', 'ctp-8', 'chamarra corta con cuello cadete', '1', NULL, 34, 'se perdio', NULL, NULL),
('mol-35', 'ctp-8', 'chamarra corta clasico', '2', NULL, 35, 'chamarra jean clasico #1192 anteriormente', NULL, NULL),
('mol-36', 'ctp-8', 'chamarra de don luis', '3', NULL, 36, '#1078 anteriormente usado para pil regia', NULL, NULL),
('mol-37', 'ctp-8', 'chamarra de luis', '4', NULL, 37, '#1176 anteriormente', NULL, NULL),
('mol-38', 'ctp-8', 'chamarra de marcla', '5', NULL, 38, '#1132 anteriormente', NULL, NULL),
('mol-39', 'ctp-8', 'chamarra de copelme en kaki', '6', NULL, 39, 'molde de 10 piezas #1138 anteriormente', NULL, NULL),
('mol-40', 'ctp-8', 'chamarra de luisito', '7', NULL, 40, '#1154 anteriormente', NULL, NULL),
('mol-41', 'ctp-8', 'chamarra de cintura piel de durazno', '8', NULL, 41, 'molde de 27 piezas #1130 anteriormente', NULL, NULL),
('mol-42', 'ctp-8', 'chamarra de mujer kotex', '9', NULL, 42, 'molde de 11 piezas #1128 anteriormente', NULL, NULL),
('mol-43', 'ctp-8', 'chamarra de militares', '10', NULL, 43, 'molde de 14 piezas #1142 anteriormente', NULL, NULL),
('mol-44', 'ctp-8', 'chamarra de doble cara', '11', NULL, 44, '#1148 anteriormente', NULL, NULL),
('mol-45', 'ctp-8', 'chamarra del diablo', '12', NULL, 45, 'molde de 27 piezas #1127 anteriormente', NULL, NULL),
('mol-46', 'ctp-8', 'chamarra taqui;a', '13', NULL, 46, 'molde de 15 piezas #2003 anteriormente', NULL, NULL),
('mol-47', 'ctp-8', 'chamarra de gordito', '14', NULL, 47, 'molde de 30 piezas #1045 anteriormente', NULL, NULL),
('mol-48', 'ctp-8', 'chamarra corte torerito jean', '15', NULL, 48, '#1193', NULL, NULL),
('mol-49', 'ctp-8', 'chamarra de mariachis omo', '16', NULL, 49, '#1082 anteriormente', NULL, NULL),
('mol-50', 'ctp-8', 'chamarra con ri;onera de fino', '17', NULL, 50, 'molde de 29 piezas #1074 anteriormente', NULL, NULL),
('mol-51', 'ctp-8', 'chamarra sgt', '18', NULL, 51, '#1087 anteriormente', NULL, NULL),
('mol-52', 'ctp-8', 'chaqueta de copelme', '19', NULL, 52, 'molde de 18 piezas #1184 anteriormente', NULL, NULL),
('mol-53', 'ctp-8', 'chamarra san simon con capucha', '20', NULL, 53, '#1199 anteriormente', NULL, NULL),
('mol-54', 'ctp-8', 'chamarra del mas', '21', NULL, 54, '#2004 anteriormente', NULL, NULL),
('mol-55', 'ctp-8', 'chamarra de trailer y omo', '22', NULL, 55, '#1097 anteriormente con zig zag en el pecho', NULL, NULL),
('mol-56', 'ctp-8', 'chamarra de mujer modelo arquero', '23', NULL, 56, '#1157 anteriormente', NULL, NULL),
('mol-57', 'ctp-8', 'chamarra de gimnacio lurdes', '24', NULL, 57, '#1153 anteriormente', NULL, NULL),
('mol-58', 'ctp-2', 'gorro de papanoel', '26', NULL, 58, '#1105 anteriormente', NULL, NULL),
('mol-59', 'ctp-2', 'sombrero de bruja', '27', NULL, 59, '#1102 anteriormente', NULL, NULL),
('mol-60', 'ctp-8', 'chamarra con capucha peque;o', '28', NULL, 60, '#1174 anteriormente', NULL, NULL),
('mol-61', 'ctp-8', 'chamarra doble cara', '29', NULL, 61, 'molde de 16 piezas #2005 anteriormente', NULL, NULL),
('mol-62', 'ctp-8', 'chamarra de militar jean', '30', NULL, 62, '#2012 anteriormente', NULL, NULL),
('mol-63', 'ctp-8', 'chamarra de jean cruzado', '31', NULL, 63, '#2013 anteriormente', NULL, NULL),
('mol-64', 'ctp-8', 'chamarra prodem', '32', NULL, 64, '#2008 anteriormente', NULL, NULL),
('mol-65', 'ctp-8', 'chamarra de adidas de marianela', '33', NULL, 65, '#2010 anteriormente', NULL, NULL),
('mol-66', 'ctp-8', 'chamarra decorredor', '34', NULL, 66, '#2011 anteriormente', NULL, NULL),
('mol-67', 'ctp-8', 'chamarra de corredor con franja', '35', NULL, 67, '#2009 anteriormente', NULL, NULL),
('mol-68', 'ctp-8', 'chamarra de jean de soraida', '36', NULL, 68, NULL, NULL, NULL),
('mol-69', 'ctp-51', 'parka maxam fanexa', '37', NULL, 69, 'molde de 18 piezas #2015 anteriormente', NULL, NULL),
('mol-70', 'ctp-8', 'chamarra maxam', '37', NULL, 70, 'molde de 18 piezas', NULL, NULL),
('mol-71', 'ctp-8', 'chamarra de seguridad grelme', '38', NULL, 71, '#2016 anteriormente', NULL, NULL),
('mol-72', 'ctp-8', 'chamarra amor de dios', '39', NULL, 72, '#2017 anteriormente', NULL, NULL),
('mol-73', 'ctp-8', 'chamarra amor de dios de mujer', '40', NULL, 73, '#2018 anteriormente', NULL, NULL),
('mol-74', 'ctp-8', 'chamarra de mujer con cintura', '41', NULL, 74, '#2019', NULL, NULL),
('mol-75', 'ctp-8', 'chamarra de varon', '42', NULL, 75, '#2020', NULL, NULL),
('mol-76', 'ctp-8', 'chamarra pepsodent 12 hours', '43', NULL, 76, '2021', NULL, NULL),
('mol-77', 'ctp-8', 'chamarra copelme negro rojo', '44', NULL, 77, 'molde 16 piezas #2022', NULL, NULL),
('mol-78', 'ctp-8', 'chanarra con collareta mod. reyna', '45', NULL, 78, 'molde de 35 piezas #2023', NULL, NULL),
('mol-79', 'ctp-51', 'parka de mujer maxam', '46', NULL, 79, 'molde de 19 piezas #2024', NULL, NULL),
('mol-80', 'ctp-8', 'chamarra de ignasio chopitea', '47', NULL, 80, '#2025', NULL, NULL),
('mol-81', 'ctp-8', 'chamarra chino', '48', NULL, 81, '#2026', NULL, NULL),
('mol-82', 'ctp-8', 'chamarra pil blanco azul', '49', NULL, 82, '#2027', NULL, NULL),
('mol-83', 'ctp-8', 'chamarra de pil de 3 colores', '50', NULL, 83, '#2028', NULL, NULL),
('mol-84', 'ctp-8', 'canguro marko miranda', '51', NULL, 84, '#2029', NULL, NULL),
('mol-85', 'ctp-8', 'chamarra de mujer copelme', '52', NULL, 85, 'molde de 14 piezas #2030', NULL, NULL),
('mol-86', 'ctp-8', 'chamarra de panchita', '53', NULL, 86, '#2031', NULL, NULL),
('mol-87', 'ctp-8', 'chamarra de pedigree', '54', NULL, 87, '#2032', NULL, NULL),
('mol-88', 'ctp-8', 'chamarra de trailer mod. 1', '55', NULL, 88, 'molde de 20 piezas #2033', NULL, NULL),
('mol-89', 'ctp-8', 'chamarra deportivo pepsodent', '56', NULL, 89, '#2034', NULL, NULL),
('mol-90', 'ctp-8', 'chamarra de archer', '57', NULL, 90, 'molde de 14 piezas #2035 o.p.271', NULL, NULL),
('mol-91', 'ctp-8', 'chamarra de mujer mod. trailer', '58', NULL, 91, 'molde de 14 piezas o.p. 410 oficial para mujer ', NULL, NULL),
('mol-92', 'ctp-8', 'chamarra para maxam de varon', '59', NULL, 92, 'molde de 26 piezas #2037', NULL, NULL),
('mol-93', 'ctp-8', 'chamarra tinkus umss', '60', NULL, 93, 'molde de 19 piezas #2038', NULL, NULL),
('mol-94', 'ctp-8', 'chamarra de coboce con v', '61', NULL, 94, '#2039', NULL, NULL),
('mol-95', 'ctp-8', 'chamarra deportiva para cite', '62', NULL, 95, 'molde de 17 piezas #2040', NULL, NULL),
('mol-96', 'ctp-8', 'chamarra crf', '63', NULL, 96, 'molde de 27 piezas', NULL, NULL),
('mol-97', 'ctp-8', 'chamarra modelo lux', '64', NULL, 97, 'molde de 16 piezas', NULL, NULL),
('mol-98', 'ctp-8', 'chamarra faboce', '65', NULL, 98, NULL, NULL, NULL),
('mol-99', 'ctp-8', 'chamarra faboce mujer', '66', NULL, 99, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientokardexmateriaprima`
--

CREATE TABLE `movimientokardexmateriaprima` (
  `idmovimientokardexmateriaprima` varchar(20) NOT NULL,
  `idmateriaprima` varchar(20) default NULL,
  `entrada` decimal(11,2) default NULL,
  `salida` decimal(11,2) default NULL,
  `saldo` decimal(11,2) default NULL,
  `costounitario` decimal(11,2) default NULL,
  `ingreso` decimal(11,2) default NULL,
  `egreso` decimal(11,2) default NULL,
  `saldobs` decimal(11,2) default NULL,
  `fecha` date default NULL,
  `hora` time default NULL,
  `descripcion` varchar(100) default NULL,
  `numero` int(11) default NULL,
  `saldocantidad` decimal(11,2) default NULL,
  `operacion` varchar(20) default NULL,
  `id` varchar(20) default NULL,
  PRIMARY KEY  (`idmovimientokardexmateriaprima`),
  KEY `idmateriaprima` (`idmateriaprima`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `movimientokardexmateriaprima`
--

INSERT INTO `movimientokardexmateriaprima` (`idmovimientokardexmateriaprima`, `idmateriaprima`, `entrada`, `salida`, `saldo`, `costounitario`, `ingreso`, `egreso`, `saldobs`, `fecha`, `hora`, `descripcion`, `numero`, `saldocantidad`, `operacion`, `id`) VALUES
('mmp-1', 'mtp-1', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2012-01-25', '12:07:13', 'Creacion de Materia Prima', 1, '0.00', '0', NULL),
('mmp-10', 'mtp-5', '48.78', '0.00', '48.78', '0.28', '13.66', '0.00', '13.66', '2012-01-14', '12:23:56', 'Compra de materia prima 0000000001', 10, '0.00', 'com-3', NULL),
('mmp-15', 'mtp-8', '0.00', '0.00', '33.84', '0.00', '0.00', '0.00', '6.09', '2012-01-31', '14:24:06', 'Creacion de Materia Prima', 15, '0.00', '0', NULL),
('mmp-16', 'mtp-8', '33.84', '0.00', '33.84', '0.18', '6.09', '0.00', '6.09', '2012-01-18', '14:26:40', 'Compra de materia prima 21002', 16, '0.00', 'com-4', NULL),
('mmp-17', 'mtp-9', '0.00', '0.00', '38.52', '0.00', '0.00', '0.00', '27.73', '2012-01-31', '14:28:40', 'Creacion de Materia Prima', 17, '0.00', '0', NULL),
('mmp-18', 'mtp-9', '38.52', '0.00', '38.52', '0.72', '27.73', '0.00', '27.73', '2012-01-15', '14:30:56', 'Compra de materia prima 210001', 18, '0.00', 'com-5', NULL),
('mmp-19', 'mtp-10', '0.00', '0.00', '72.00', '0.00', '0.00', '0.00', '15.84', '2012-01-31', '14:31:41', 'Creacion de Materia Prima', 19, '0.00', '0', NULL),
('mmp-2', 'mtp-2', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2012-01-25', '12:07:53', 'Creacion de Materia Prima', 2, '0.00', '0', NULL),
('mmp-20', 'mtp-10', '72.00', '0.00', '72.00', '0.22', '15.84', '0.00', '15.84', '2012-01-15', '14:34:28', 'Compra de materia prima 210001', 20, '0.00', 'com-6', NULL),
('mmp-26', 'mtp-11', '0.00', '0.00', '3.06', '0.00', '0.00', '0.00', '3.67', '2012-01-31', '15:00:54', 'Creacion de Materia Prima', 26, '0.00', '0', NULL),
('mmp-27', 'mtp-11', '3.06', '0.00', '3.06', '1.20', '3.67', '0.00', '3.67', '2012-01-15', '15:03:58', 'Compra de materia prima 210002', 27, '0.00', 'com-7', NULL),
('mmp-3', 'mtp-3', '0.00', '0.00', '17.50', '0.00', '0.00', '0.00', '183.75', '2012-01-31', '10:25:12', 'Creacion de Materia Prima', 3, '0.00', '0', NULL),
('mmp-34', 'mtp-12', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2012-01-31', '15:09:46', 'Creacion de Materia Prima', 34, '0.00', '0', NULL),
('mmp-35', 'mtp-12', '56.88', '0.00', '56.88', '0.81', '46.07', '0.00', '46.07', '2012-01-31', '15:13:08', 'Compra de materia prima 210002', 35, '0.00', 'com-8', NULL),
('mmp-4', 'mtp-3', '17.50', '0.00', '17.50', '10.50', '183.75', '0.00', '183.75', '2012-01-04', '11:33:00', 'Compra de materia prima 000001', 4, '0.04', 'com-1', NULL),
('mmp-43', 'mtp-13', '0.00', '0.00', '9.36', '0.00', '0.00', '0.00', '3.18', '2012-01-31', '15:15:12', 'Creacion de Materia Prima', 43, '0.00', '0', NULL),
('mmp-44', 'mtp-13', '9.36', '0.00', '9.36', '0.34', '3.18', '0.00', '3.18', '2012-01-15', '15:17:42', 'Compra de materia prima 210002', 44, '0.00', 'com-9', NULL),
('mmp-5', 'mtp-3', '0.00', '17.46', '0.04', '10.50', '0.00', '183.33', '0.42', '2012-01-31', '11:49:05', 'Proceso Corte OP1362', 5, '0.00', 'cor-1', 'mmp-4'),
('mmp-54', 'mtp-14', '0.00', '0.00', '5.40', '0.00', '0.00', '0.00', '3.78', '2012-01-31', '15:22:12', 'Creacion de Materia Prima', 54, '0.00', '0', NULL),
('mmp-55', 'mtp-14', '5.40', '0.00', '5.40', '0.70', '3.78', '0.00', '3.78', '2012-01-15', '15:25:48', 'Compra de materia prima 210002', 55, '0.00', 'com-10', NULL),
('mmp-6', 'mtp-4', '0.00', '0.00', '130.15', '0.00', '0.00', '0.00', '46.85', '2012-01-31', '12:12:53', 'Creacion de Materia Prima', 6, '0.00', '0', NULL),
('mmp-66', 'mtp-15', '0.00', '0.00', '36.00', '0.00', '0.00', '0.00', '36.00', '2012-01-31', '15:42:58', 'Creacion de Materia Prima', 66, '0.00', '0', NULL),
('mmp-67', 'mtp-15', '36.00', '0.00', '36.00', '1.00', '36.00', '0.00', '36.00', '2012-01-15', '15:45:13', 'Compra de materia prima 210002', 67, '0.00', 'com-11', NULL),
('mmp-68', 'mtp-15', '0.00', '36.00', '0.00', '1.00', '0.00', '36.00', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 68, '0.00', 'ddi-41', 'mmp-67'),
('mmp-69', 'mtp-13', '0.00', '7.20', '2.16', '0.34', '0.00', '2.45', '0.73', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 69, '0.00', 'ddi-42', 'mmp-44'),
('mmp-7', 'mtp-4', '130.15', '0.00', '130.15', '0.36', '46.85', '0.00', '46.85', '2012-01-14', '12:16:13', 'Compra de materia prima 00000001', 7, '0.01', 'com-2', NULL),
('mmp-70', 'mtp-13', '0.00', '2.16', '0.00', '0.34', '0.00', '0.73', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 70, '0.00', 'ddi-43', 'mmp-44'),
('mmp-71', 'mtp-5', '0.00', '48.78', '0.00', '0.28', '0.00', '13.66', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 71, '0.00', 'ddi-44', 'mmp-10'),
('mmp-72', 'mtp-4', '0.00', '130.14', '0.01', '0.36', '0.00', '46.85', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 72, '0.00', 'ddi-45', 'mmp-7'),
('mmp-73', 'mtp-9', '0.00', '38.52', '0.00', '0.72', '0.00', '27.73', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 73, '0.00', 'ddi-46', 'mmp-18'),
('mmp-74', 'mtp-10', '0.00', '72.00', '0.00', '0.22', '0.00', '15.84', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 74, '0.00', 'ddi-47', 'mmp-20'),
('mmp-75', 'mtp-8', '0.00', '33.84', '0.00', '0.18', '0.00', '6.09', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 75, '0.00', 'ddi-48', 'mmp-16'),
('mmp-76', 'mtp-11', '0.00', '3.06', '0.00', '1.20', '0.00', '3.67', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 76, '0.00', 'ddi-49', 'mmp-27'),
('mmp-77', 'mtp-12', '0.00', '56.88', '0.00', '0.81', '0.00', '46.07', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 77, '0.00', 'ddi-50', 'mmp-35'),
('mmp-78', 'mtp-14', '0.00', '5.40', '0.00', '0.70', '0.00', '3.78', '0.00', '2012-01-31', '15:45:27', 'Proceso Distribucion OP1362', 78, '0.00', 'ddi-51', 'mmp-55'),
('mmp-79', 'mtp-16', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2012-02-07', '10:37:40', 'Creacion de Materia Prima', 79, '0.00', '0', NULL),
('mmp-9', 'mtp-5', '0.00', '0.00', '48.78', '0.00', '0.00', '0.00', '13.66', '2012-01-31', '12:21:04', 'Creacion de Materia Prima', 9, '0.00', '0', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientoproducto`
--

CREATE TABLE `movimientoproducto` (
  `idmovimientoproducto` varchar(20) NOT NULL,
  `idproducto` varchar(20) NOT NULL,
  `entrada` double(15,3) default NULL,
  `salida` double(15,3) default NULL,
  `saldo` double(15,3) default NULL,
  `costounitario` double(15,3) default NULL,
  `ingreso` double(15,3) default NULL,
  `egreso` double(15,3) default NULL,
  `saldobs` double(15,3) default NULL,
  `fecha` date default NULL,
  `hora` time default NULL,
  `descripcion` varchar(200) default NULL,
  `numero` int(11) default NULL,
  `saldocantidad` double(15,3) default NULL,
  `operacion` varchar(20) default NULL,
  PRIMARY KEY  (`idmovimientoproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `movimientoproducto`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientoproductointermedio`
--

CREATE TABLE `movimientoproductointermedio` (
  `idmovimientoproductointermedio` varchar(20) NOT NULL,
  `idproductointermedio` varchar(20) default NULL,
  `entrada` int(11) default NULL,
  `salida` int(11) default NULL,
  `saldo` int(11) default NULL,
  `saldocantidad` int(11) default NULL,
  `operacion` varchar(20) default NULL,
  `descripcion` varchar(200) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idmovimientoproductointermedio`),
  KEY `idproductointermedio` (`idproductointermedio`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `movimientoproductointermedio`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opbordado`
--

CREATE TABLE `opbordado` (
  `idopbordado` varchar(20) NOT NULL,
  `idop` varchar(20) default NULL,
  `idproducto` varchar(20) default NULL,
  `op` varchar(20) default NULL,
  `iddetalleorden` varchar(20) default NULL,
  `observacion` varchar(200) default NULL,
  `fecha` date default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idopbordado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `opbordado`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenproduccion`
--

CREATE TABLE `ordenproduccion` (
  `idordenproduccion` varchar(20) NOT NULL,
  `numeroorden` varchar(20) default NULL,
  `idcliente` varchar(20) default NULL,
  `idempresa` varchar(20) default NULL,
  `idresponsable` varchar(20) default NULL,
  `fechaentrega` date default NULL,
  `fecharecepcion` date default NULL,
  `montototal` double(15,3) default NULL,
  `montoapagar` double(15,3) default NULL,
  `saldo` double(15,3) default NULL,
  `observacion` varchar(200) default NULL,
  `estado` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `idusuario` varchar(20) default NULL,
  `cliente` varchar(200) default NULL,
  `numerorecibo` varchar(20) default NULL,
  PRIMARY KEY  (`idordenproduccion`),
  KEY `numeroorden` (`numeroorden`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `ordenproduccion`
--

INSERT INTO `ordenproduccion` (`idordenproduccion`, `numeroorden`, `idcliente`, `idempresa`, `idresponsable`, `fechaentrega`, `fecharecepcion`, `montototal`, `montoapagar`, `saldo`, `observacion`, `estado`, `numero`, `idusuario`, `cliente`, `numerorecibo`) VALUES
('ord-1', '1362', NULL, 'epr-46', 'res-44', '2012-01-15', '2012-01-04', 1530.000, 0.000, 0.000, NULL, 'recepcion', 1, 'usr-1000', 'chromart s.r.l.', NULL),
('ord-2', '1363', NULL, 'epr-6', 'res-108', '2012-02-10', '2012-01-04', 1649.000, 0.000, 0.000, NULL, 'recepcion', 2, 'usr-1000', 'kimberly bolivia s.a.', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `procesos`
--

CREATE TABLE `procesos` (
  `idproceso` varchar(20) NOT NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(200) default NULL,
  `detalle` varchar(20) default NULL,
  `idempleado` varchar(20) default NULL,
  `tipoproceso` varchar(20) default NULL COMMENT '{INDIVIDUAL , GRUPO }',
  `numero` int(11) default NULL,
  `estado` varchar(20) default NULL COMMENT '{ACTIVO , INACTIVO\r\n}',
  `item` varchar(20) default NULL,
  `idcuenta` varchar(20) default NULL,
  `formula` varchar(200) default NULL,
  PRIMARY KEY  (`idproceso`),
  KEY `idempleado` (`idempleado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `procesos`
--

INSERT INTO `procesos` (`idproceso`, `codigo`, `nombre`, `detalle`, `idempleado`, `tipoproceso`, `numero`, `estado`, `item`, `idcuenta`, `formula`) VALUES
('prc-1', 'p1', 'prueba', NULL, 'emp-1', 'items', 1, 'Activo', 'Costo', NULL, NULL),
('prc-10', 'iten', 'envio', 'item gastos de envio', NULL, 'items', 10, 'Activo', 'gastos de envio', 'cup-6', '0.25'),
('prc-11', 'itot', 'otros', 'item de otros gastos', NULL, 'items', 11, 'Activo', 'utilidad de otros ga', 'cup-7', '3.50'),
('prc-12', 'itiva', 'iva', 'item impuestos iva', NULL, 'items', 12, 'Activo', 'impuestos iva', 'cup-13', 'preciodeventa * 0.06'),
('prc-2', 'cor', 'corte', 'niguno', 'emp-34', 'proceso', 2, 'Activo', 'Utilidad', NULL, NULL),
('prc-3', 'ser', 'serigrafiado', 'Proceso de serigrafi', 'emp-35', 'proceso', 3, 'Activo', NULL, NULL, NULL),
('prc-4', 'cos', 'costura', 'proceso de costura', 'emp-35', 'proceso', 4, 'Activo', NULL, NULL, NULL),
('prc-5', 'bor', 'bordado', 'proceso de bordado', 'emp-35', 'proceso', 5, 'Activo', NULL, NULL, NULL),
('prc-6', 'Itbor', 'bordado', 'Items Bordado', NULL, 'items', 6, 'Activo', 'utilidad de bordado', 'cup-2', 'PUNTOS * 0.0004935'),
('prc-7', 'Itgb', 'gasto de bordado', 'gastos de bordado', NULL, 'items', 7, 'Activo', 'utilidad para gastos', 'cup-3', 'bordado * 0.15'),
('prc-8', 'Itdm', 'depreciacion de maquinaria', 'item de depreciacion', NULL, 'items', 8, 'Activo', 'depreciacion de maqu', 'cup-4', 'sastre / 12'),
('prc-9', 'itcel', 'celular', 'item gastos de celul', NULL, 'items', 9, 'Activo', 'gastos de celular', 'cup-5', '0.10');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccionprocesos`
--

CREATE TABLE `produccionprocesos` (
  `idproduccionproceso` varchar(20) NOT NULL,
  `numeroorden` varchar(20) default NULL,
  `idproceso` varchar(20) NOT NULL,
  `idempleado` varchar(20) NOT NULL,
  `fechaentrega` date default NULL,
  `fecharecepcion` date default NULL,
  `observacion` varchar(250) default NULL,
  `estado` varchar(20) NOT NULL,
  `numero` int(11) default NULL,
  `numeroproduccion` varchar(20) NOT NULL,
  PRIMARY KEY  (`idproduccionproceso`),
  UNIQUE KEY `numeroproduccion` (`numeroproduccion`),
  KEY `produccionprocesos_fk1` (`idproceso`),
  KEY `idempleado` (`idempleado`),
  KEY `numeroorden` (`numeroorden`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `produccionprocesos`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productointermedios`
--

CREATE TABLE `productointermedios` (
  `idproductointermedio` varchar(20) NOT NULL,
  `numeroorden` varchar(20) NOT NULL,
  `idproduccionproceso` varchar(20) default NULL,
  `detalle` varchar(200) default NULL,
  `unidad` varchar(20) default NULL,
  `saldocantidad` int(11) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idproductointermedio`),
  KEY `numeroorden` (`numeroorden`),
  KEY `idproduccionproceso` (`idproduccionproceso`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `productointermedios`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `idproducto` varchar(20) NOT NULL,
  `nombre` varchar(200) default NULL,
  `codigo` varchar(20) default NULL,
  `imagen` varchar(200) default NULL,
  `medidas` varchar(200) default NULL,
  `tela` varchar(200) default NULL,
  `idgrupo` varchar(20) default NULL,
  `idcategoriaproducto` varchar(20) default NULL,
  `costounitario` decimal(11,2) default NULL,
  `precio1bs` decimal(11,2) default NULL,
  `utilidad` double default NULL,
  `numero` int(11) default NULL,
  `descripcion` varchar(200) default NULL,
  `saldocantidad` decimal(11,2) default NULL,
  `precio2bs` decimal(11,2) default NULL,
  `logo` varchar(20) default NULL,
  `canal` varchar(20) default NULL,
  `puntadas` varchar(20) default NULL,
  `ancho` varchar(20) default NULL,
  `alto` varchar(20) default NULL,
  `idempresa` varchar(20) default NULL,
  `kardex` varchar(200) default NULL,
  PRIMARY KEY  (`idproducto`),
  KEY `idcategoriaproducto` (`idcategoriaproducto`),
  KEY `idgrupo` (`idgrupo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `productos`
--

INSERT INTO `productos` (`idproducto`, `nombre`, `codigo`, `imagen`, `medidas`, `tela`, `idgrupo`, `idcategoriaproducto`, `costounitario`, `precio1bs`, `utilidad`, `numero`, `descripcion`, `saldocantidad`, `precio2bs`, `logo`, `canal`, `puntadas`, `ancho`, `alto`, `idempresa`, `kardex`) VALUES
('prd-100', 'wilda', NULL, 'php/uploads/producto_1313695431.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 100, 'universal', NULL, NULL, 'si', '19', '3.950', '9.00', '3.54', 'epr-61', 'x-24"w"'),
('prd-101', 'fideos condor', NULL, 'php/uploads/producto_1313695548.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 101, 'bolisillos', NULL, NULL, 'si', '10', '11.200', '9.02', '6.30', 'epr-57', 'bx-3"c"'),
('prd-102', 'tigo', NULL, 'php/uploads/producto_1313695988.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 102, 'chamarras', NULL, NULL, 'si', '30', '2.700', '8.71', '5.73', 'epr-27', 'c-21"t"'),
('prd-103', 'distri....hyp', NULL, 'php/uploads/producto_1313791664.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 103, 'poleras', NULL, NULL, 'si', '1', '3.450', '8.00', '5.24', 'epr-2', 'otros logos unilever'),
('prd-104', 'arpon ltda', NULL, 'php/uploads/producto_1313791117.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 104, 'poleras', NULL, NULL, 'si', '2', '1.690', '8.00', '1.50', 'epr-2', 'otros logos unilever'),
('prd-105', 'distribui.. uni.k', NULL, 'php/uploads/producto_1313791197.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 105, 'poleras', NULL, NULL, 'si', '3', '4.950', '8.10', '5.20', 'epr-2', 'otros logos unilever'),
('prd-106', 'hipermaxi', NULL, 'php/uploads/producto_1313791252.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 106, 'poleras \noveroles', NULL, NULL, 'si', '4', '4.300', '7.00', '3.46', 'epr-2', 'otros logos unilever'),
('prd-107', 'movimarket', NULL, 'php/uploads/producto_1313791307.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 107, 'poleras', NULL, NULL, 'si', '10', '3.430', '11.00', '2.90', 'epr-2', 'unilever'),
('prd-108', 'unitepc', NULL, 'php/uploads/producto_1314390789.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 108, 'espalda', NULL, NULL, 'si', '1', '10.900', '24.00', '5.40', 'epr-42', 'logos con letra"u"'),
('prd-109', 'unitepc', NULL, 'php/uploads/producto_1314390286.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 109, 'busos\ncamisas', NULL, NULL, 'si', '8', '2.640', '8.09', '1.82', 'epr-42', 'logos con letra"u"'),
('prd-110', 'unitepc con sobretela', NULL, 'php/uploads/producto_1314390303.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 110, 'chmarras', NULL, NULL, 'si', '9', '6.420', '8.02', '7.28', 'epr-42', 'logos con letra"u"'),
('prd-111', 'suave', NULL, 'php/uploads/producto_1314390967.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 111, 'gorras', NULL, NULL, 'si', '4', '3.820', '6.50', '3.83', 'epr-2', 'extras unilever'),
('prd-112', 'suave', NULL, 'php/uploads/producto_1314710510.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 112, 'mandiles \nmochilas', NULL, NULL, 'si', '18', '5.450', '8.51', '5.06', 'epr-2', 'extras unilever'),
('prd-113', 'plenitud y huggies', NULL, 'php/uploads/producto_1314885432.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 113, 'manga derecha', NULL, NULL, 'si', '15', '3.180', '7.46', '5.26', 'epr-2', 'kimberly bolivia'),
('prd-114', 'scott kotex', NULL, 'php/uploads/producto_1314885459.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 114, 'manga izquierda', NULL, NULL, 'si', '18', '8.650', '7.08', '11.01', 'epr-2', 'kimberly bolivia'),
('prd-115', 'suave', NULL, 'php/uploads/producto_1316545748.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 115, 'mandiles', NULL, NULL, 'si', '18', '5.450', '8.51', '5.06', 'epr-2', 'extras unilever'),
('prd-116', 'organica', NULL, 'php/uploads/producto_1315853052.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 116, 'gorras\npoleras', NULL, NULL, 'si', '10', '4.790', '9.56', '4.18', 'epr-38', 'x-16"o"'),
('prd-117', 'plaza hipermercado', NULL, 'php/uploads/producto_1315932477.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 117, 'poleras', NULL, NULL, 'si', '7', '5.190', '9.06', '3.53', 'epr-2', 'otros  logos unilever'),
('prd-118', 'kimberly bolivia s.a.', NULL, 'php/uploads/producto_1315938805.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 118, 'pecho', NULL, NULL, 'si', '3', '1.680', '9.20', '1.14', 'epr-6', 'kimberly bolivia'),
('prd-119', 'faboce ceramicas', NULL, 'php/uploads/producto_1315941464.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 119, 'poleras\ngorras', NULL, NULL, 'si', '11', '3.710', '8.26', '2.50', 'epr-11', 'soboce anaranjado'),
('prd-120', 'faboce ceramicas', NULL, 'php/uploads/producto_1315941495.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 120, 'espaldas', NULL, NULL, 'si', '16', '16.710', '6.70', '24.00', 'epr-11', 'soboce anaranjado'),
('prd-121', 'organica', NULL, 'php/uploads/producto_1316545577.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 121, 'espalda', NULL, NULL, 'si', '8', '11.393', '9.50', '9.01', 'epr-38', 'x-16"o"'),
('prd-122', 'usaid', NULL, 'php/uploads/producto_1317476279.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 122, 'poleras \nchamarras', NULL, NULL, 'si', '14', '11.390', '8.18', '11.10', 'epr-26', 'logos con letra "u"'),
('prd-123', 'skip inteligent', NULL, 'php/uploads/producto_1318279863.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 123, 'poleras', NULL, NULL, 'si', '12', '4.050', '9.02', '7.60', 'epr-2', 'otros  logos unilever'),
('prd-124', 'dumbo', NULL, 'php/uploads/producto_1320850312.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 124, 'universal', NULL, NULL, 'si', '18', '9.100', '11.08', '3.70', 'epr-17', 'dumbo'),
('prd-125', 'tunari restaurante', NULL, 'php/uploads/producto_1320850726.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 125, 'poleras', NULL, NULL, 'si', '2', '4.300', '10.82', '5.10', 'epr-62', 'logos con letra"t"'),
('prd-126', 'tele/c', NULL, 'php/uploads/producto_1320851184.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 126, 'poleras', NULL, NULL, 'si', '2', '2.350', '10.82', '5.10', 'epr-62', 'logos con letra "t"'),
('prd-127', 'lux rexona', NULL, 'php/uploads/producto_1320857409.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 127, 'universal', NULL, NULL, 'si', '5', '2.580', '10.02', '6.64', 'epr-2', 'lux'),
('prd-128', 'lux rexona', NULL, 'php/uploads/producto_1320857702.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 128, 'espaldas', NULL, NULL, 'si', '18', '4.750', '15.00', '10.00', 'epr-2', 'logos mixtos unilever'),
('prd-129', 'rexona con guion', NULL, 'php/uploads/producto_1320859001.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 129, 'poleras', NULL, NULL, 'si', 'logo ', '1.490', '6.00', '6.00', 'epr-2', 'rexona'),
('prd-130', 'dove+mencare', NULL, 'php/uploads/producto_1320859060.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 130, 'pecho', NULL, NULL, 'si', '5', '3.710', '8.00', '7.00', 'epr-2', 'dove'),
('prd-131', 'dove+mencare', NULL, 'php/uploads/producto_1320859117.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 131, 'espalda', NULL, NULL, 'si', '6', '8.400', '15.00', '13.00', 'epr-2', 'dove'),
('prd-132', 'MITSUBOSHI', NULL, 'php/uploads/producto_1321034493.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 132, 'ESPALDAS', NULL, NULL, 'si', '1', '8.890', '24.27', '5.14', 'epr-62', 'BX-13'),
('prd-133', 'MITSUBOSHI', NULL, 'php/uploads/producto_1321034518.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 133, 'POLERAS \nGORRAS\nCHALECOS\nGUARDAPOLVOS', NULL, NULL, 'si', '2', '2.870', '7.06', '7.26', 'epr-62', 'BX-13   M'),
('prd-134', 'ARCHER', NULL, 'php/uploads/producto_1321034991.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 134, 'GORRAS', NULL, NULL, 'si', '3', '5.960', '6.44', '5.00', 'epr-62', 'C-1"A"'),
('prd-135', 'ARCHER ORURO', NULL, 'php/uploads/producto_1321035006.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 135, 'CHALECOS', NULL, NULL, 'si', '25', '5.560', '8.40', '5.14', 'epr-62', 'C-1"A"'),
('prd-136', 'EXPOTECO 2011 ARCHER', NULL, 'php/uploads/producto_1321035024.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 136, 'CHALECOS \nMANDILES', NULL, NULL, 'si', '26', '7.030', '10.16', '6.00', 'epr-62', 'C-1"A"'),
('prd-137', 'careta de moreno', NULL, 'php/uploads/producto_1321647580.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 137, 'poleras', NULL, NULL, 'si', '3', '4.330', '6.00', '6.80', 'epr-62', 'carnaval2009'),
('prd-138', 'umss', NULL, 'php/uploads/producto_1321647566.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 138, 'mangas', NULL, NULL, 'si', '2', '2.110', '4.12', '6.40', 'epr-62', 'logos con letra"u"'),
('prd-139', 'eventos y convenciones', NULL, 'php/uploads/producto_1321654086.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 139, 'universal', NULL, NULL, 'si', '4', '4.600', '9.24', '8.12', 'epr-5', 'coboce'),
('prd-14', 'unitepc con sobretela', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 14, 'deportivos\npoleras\ngorras', NULL, NULL, 'si', '9', '6420', '7.27', '8.01', 'epr-21', NULL),
('prd-140', 'iso14001 iso9001', NULL, 'php/uploads/producto_1321654099.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 140, 'pecho', NULL, NULL, 'si', '12', '5.650', '7.61', '6.38', 'epr-5', 'coboce'),
('prd-141', 'eventos convenciones', NULL, 'php/uploads/producto_1321654123.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 141, 'espalda', NULL, NULL, 'si', '5', '14.880', '22.52', '19.82', 'epr-5', 'coboce'),
('prd-142', 'bandera', NULL, 'php/uploads/producto_1321654148.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 142, 'unversal', NULL, NULL, 'si', '16', '1.990', '6.00', '4.32', 'epr-5', 'c.2"b"'),
('prd-143', 'santiago', NULL, 'php/uploads/producto_1322170246.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 143, 'pecho\nderecho', NULL, NULL, 'si', '15', '4.740', '8.63', '8.16', 'epr-62', 'logos con letra s'),
('prd-144', 'aguila', NULL, 'php/uploads/producto_1322170262.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 144, 'pecho izquierdo', NULL, NULL, 'si', '28', '7.413', '5.86', '8.16', 'epr-62', 'logos con letra"s"'),
('prd-145', 'kimberly bolivia', NULL, 'php/uploads/producto_1322504241.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 145, 'pecho', NULL, NULL, 'si', '3', '1.680', '9.20', '1.14', 'epr-6', 'kimberly bolivia'),
('prd-146', 'kotex', NULL, 'php/uploads/producto_1322504227.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 146, 'pecho\n', NULL, NULL, 'si', '21', '4.110', '5.50', '6.00', 'epr-6', 'kimberly bolivia'),
('prd-147', 'plenitud con sobretela', NULL, 'php/uploads/producto_1322504207.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 147, 'pecho', NULL, NULL, 'si', NULL, '2.580', '7.50', '4.50', 'epr-6', 'kimberly-clark'),
('prd-148', 'huggies con cigueÃ±a', NULL, 'php/uploads/producto_1322504187.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 148, 'pecho', NULL, NULL, 'si', '3', '6.810', '8.70', '5.50', 'epr-6', 'kimberly-clark'),
('prd-149', 'pleni- scott-kotex-huggies', NULL, 'php/uploads/producto_1322504161.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 149, 'espalda', NULL, NULL, 'si', '1', '17.050', '20.05', '15.08', 'epr-6', 'kimberly-clark'),
('prd-15', 'untepc ', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 15, 'camisas\nbuzos', NULL, NULL, 'si', '8', '2.640', '8.01', '7.27', 'epr-21', NULL),
('prd-150', 'scott', NULL, 'php/uploads/producto_1322504115.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 150, 'pecho', NULL, NULL, 'si', '2', '4.320', '6.65', '4.00', 'epr-6', 'x-20"s"'),
('prd-151', 'rik', NULL, 'php/uploads/producto_1322173836.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 151, 'mandiles', NULL, NULL, 'si', '6', '4.370', '10.00', '4.40', 'epr-2', 'rik'),
('prd-152', 'veliz srl', NULL, 'php/uploads/producto_1322174606.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 152, 'pecho', NULL, NULL, 'si', '18', '7.570', '10.66', '5.53', 'epr-62', 'c-23"v"'),
('prd-153', 'veliz srl contorno', NULL, 'php/uploads/producto_1322174619.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 153, 'pecho ', NULL, NULL, 'si', '18', '7.570', '10.66', '5.53', 'epr-62', 'c-23"v"'),
('prd-154', 'entel', NULL, 'php/uploads/producto_1322174830.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 154, 'pecho', NULL, NULL, 'si', '13', '2.260', '8.03', '5.10', 'epr-29', 'c-5"e"'),
('prd-155', 'copelme', NULL, 'php/uploads/producto_1322175437.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 155, 'espalda', NULL, NULL, 'si', '10', '4.180', '22.00', '7.04', 'epr-3', 'logos actualizados  copelme'),
('prd-156', 'copelme', NULL, 'php/uploads/producto_1322175453.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 156, 'polera \ngorras', NULL, NULL, 'si', '13', '3.400', '7.60', '5.85', 'epr-3', 'logos actualizados copelme'),
('prd-157', 'fondo de la comunidad', NULL, 'php/uploads/producto_1322175830.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 157, 'universal', NULL, NULL, 'si', '22', '2.815', '9.00', '1.80', 'epr-13', 'x-6"f"'),
('prd-158', 'kimberly bolivia professional', NULL, 'php/uploads/producto_1322504554.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 158, 'pecho', NULL, NULL, 'si', '22', '4.730', '9.14', '3.70', 'epr-6', 'kimberly bolivia'),
('prd-159', 'kimberly professional', NULL, 'php/uploads/producto_1322504568.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 159, 'pecho', NULL, NULL, 'si', '23', '4.730', '9.14', '3.70', 'epr-6', 'kimberly bolivia'),
('prd-16', 'unitepc', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 16, 'espalda', NULL, NULL, 'si', '6', '6.350', '16.31', '3.67', 'epr-21', NULL),
('prd-160', 'icteo', NULL, 'php/uploads/producto_1322678792.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 160, 'pecho', NULL, NULL, 'si', '2', '2.830', '8.74', '4.00', 'epr-62', 'b-20"s"'),
('prd-161', '15 noches de talentos', NULL, 'php/uploads/producto_1322678774.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 161, 'pecho', NULL, NULL, 'si', '4', '10.630', '9.52', '8.84', 'epr-62', 'c-14"n"'),
('prd-162', 'leoncitos', NULL, 'php/uploads/producto_1323119619.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 162, 'poleras \npecho', NULL, NULL, 'si', '1', '6.570', '8.70', '7.30', 'epr-20', 'ron abuelo'),
('prd-163', 'play back', NULL, 'php/uploads/producto_1323119636.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 163, 'poleras \npecho', NULL, NULL, 'si', '2', '6.170', '6.40', '8.61', 'epr-20', 'ron abuelo'),
('prd-164', 'discoteque gay les', NULL, 'php/uploads/producto_1323119700.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 164, 'poleras\npecho', NULL, NULL, 'si', '1', '8.070', '10.02', '7.00', 'epr-20', 'abuelo panama'),
('prd-165', 'sadec', NULL, 'php/uploads/producto_1323193985.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 165, 'pecho', NULL, NULL, 'si', '18', '1.430', '7.72', '2.60', 'epr-41', 'c-20"s"'),
('prd-166', 'movimiento de la verdad', NULL, 'php/uploads/producto_1323975787.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 166, 'universal', NULL, NULL, 'si', '4', '3.000', '10.18', '3.42', 'epr-62', 'bx-13"m"'),
('prd-167', 'ron abuelo panama', NULL, 'php/uploads/producto_1323975824.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 167, 'espalda', NULL, NULL, 'si', '2', '13.010', '20.00', '11.20', 'epr-20', 'abuelo panama'),
('prd-168', 'mc', NULL, 'php/uploads/producto_1323975745.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 168, 'pecho', NULL, NULL, 'si', '27', '2.850', '4.00', '4.12', 'epr-62', 'c-13'),
('prd-169', 'lobo', NULL, 'php/uploads/producto_1323975763.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 169, 'pecho\n', NULL, NULL, 'si', '16', '3.350', '7.26', '7.74', 'epr-62', 'logos dÂ· mas'),
('prd-17', 'unitepc con sobretela', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 17, 'gorras', NULL, NULL, 'si', '7', '5.500', '6.84', '6.25', 'epr-21', NULL),
('prd-170', 'bolson mod trailer', 'bol1', NULL, '80x30x30', 'lona u oxford', 'gru-1', 'ctp-4', NULL, NULL, NULL, 170, 'bolson de viaje', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('prd-171', 'polera polo mujer', 'pol02', NULL, NULL, 'pique', 'gru-1', 'ctp-15', NULL, NULL, NULL, 171, 'manga corta con puÃ±o', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('prd-172', 'polera polo varÃ³n', 'pol01', NULL, NULL, 'pique', 'gru-1', 'ctp-15', NULL, NULL, NULL, 172, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('prd-18', 'unitepc', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 18, 'espalda para deportivos', NULL, NULL, 'si', '1', '10.900', '24.00', '5.40', 'epr-21', NULL),
('prd-29', 'sadec espalda', NULL, 'php/uploads/producto_1323120138.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 29, 'camisas', NULL, NULL, 'si', '1', '10.700', '13.50', '17.55', 'epr-22', 'LOGOS CON LETRA"S"'),
('prd-3', 'logo cerveceria nacional ', NULL, 'php/uploads/producto_1309877952.jpg', NULL, NULL, 'gru-2', 'ctp-11', '232.00', '0.00', 3, 3, '3 coloresnrojo azul verde', '232.00', NULL, 'si', '12', '332', '2.04', '2.26', 'epr-1', NULL),
('prd-32', 'urrutibehety', NULL, 'php/uploads/producto_1315849375.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 32, 'bolsillo', NULL, NULL, 'si', '7', '2.060', '9.01', '4.26', 'epr-24', 'l'),
('prd-33', 'urrutibehety espalda', NULL, 'php/uploads/producto_1316545610.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 33, 'mandiles', NULL, NULL, 'si', '18', '7.770', '8.04', '17.02', 'epr-24', 'l'),
('prd-34', 'urrutibehety', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 34, 'camisas\n', NULL, NULL, 'si', '22', '1.600', '7.00', '3.31', 'epr-24', 'l'),
('prd-35', 'finolight', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 35, 'poleras', NULL, NULL, 'si', '1', '3.800', '10.00', '7.72', 'epr-31', NULL),
('prd-36', 'corofino', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 36, 'gorras', NULL, NULL, 'si', '2', '3.800', '6.01', '7.80', 'epr-31', NULL),
('prd-37', 'finolight', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 37, 'poleras', NULL, NULL, 'si', '3', '5.210', '3.50', '8.52', 'epr-31', NULL),
('prd-38', 'finolight', NULL, NULL, NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 38, 'poleras', NULL, NULL, 'si', '4', '1.850', '3.50', '8.52', 'epr-31', NULL),
('prd-45', 'trailer jeans', NULL, 'php/uploads/producto_1312820214.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 45, 'polera \ngorras\nchammarras\n', NULL, NULL, 'si', '1', '1.770', '7.90', '4.00', 'epr-44', 'TRAILER GRUP'),
('prd-47', 'MAXAN.FANEXA', NULL, 'php/uploads/producto_1313603642.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 47, 'POLERA \nBOLSILLOS\nCHAMARRAS', NULL, NULL, 'si', '18', '1.430', '8.86', '1.64', 'epr-36', 'B-6_F'),
('prd-5', 'VIDRIO LUX', NULL, 'php/uploads/producto_1310074172.jpg', NULL, NULL, 'gru-2', 'ctp-11', '0.00', '0.00', 4, 5, 'EL BORDADO FUE NE EL LADO DERECHO ', '23.00', NULL, 'si', '2', '1000', '1.00', '7.10', 'epr-1', NULL),
('prd-51', 'MAXAN ESPALDA', NULL, 'php/uploads/producto_1315936314.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 51, 'OVEROLES \nCAMARRAS\n', NULL, NULL, 'si', '24', '2.510', '20.00', '3.24', 'epr-36', 'B-6_F'),
('prd-78', 'omo con mancha', NULL, 'php/uploads/producto_1312481807.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 78, 'chalecos \npoleras\ngorras', NULL, NULL, 'si', '22', '4.450', '8.40', '6.00', 'epr-2', 'omo'),
('prd-79', 'fridosa embutidos', NULL, 'php/uploads/producto_1312483029.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 79, 'bolsitas', NULL, NULL, 'si', '21', '4.701', '6.66', '4.03', 'epr-30', 'b-6_f'),
('prd-8', 'bb', 'p2', NULL, NULL, NULL, 'gru-2', 'ctp-2', '118.00', '122.00', 4, 8, NULL, '231.00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('prd-80', 'coboce cemento', NULL, 'php/uploads/producto_1312483476.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 80, 'poleras', NULL, NULL, 'si', '14', '4.020', '4.75', '7.71', 'epr-5', 'coboce'),
('prd-82', 'surf mariposa', NULL, 'php/uploads/producto_1312572433.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 82, 'poleras', NULL, NULL, 'si', '18', '3.860', '8.00', '4.30', 'epr-2', 'surf'),
('prd-87', 'integracion bolivia con energia', NULL, 'php/uploads/producto_1312820317.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 87, 'gorras', NULL, NULL, 'si', '20', '4.110', '10.23', '4.81', 'epr-51', 'c-5"e"'),
('prd-88', 'bandera', NULL, 'php/uploads/producto_1312820389.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 88, 'lateral gorras', NULL, NULL, 'si', '16', '1.990', '6.00', '4.32', 'epr-51', 'c-2"b"'),
('prd-89', 'omo con mancha', NULL, 'php/uploads/producto_1312901673.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 89, 'mochilas', NULL, NULL, 'si', '18', '13.930', '14.75', '10.46', 'epr-2', 'diseÃ±os grandes unilever'),
('prd-90', 'tigo', NULL, 'php/uploads/producto_1312997455.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 90, 'universal', NULL, NULL, 'si', '19', '1.130', '7.00', '5.02', 'epr-27', 'c-21"t"'),
('prd-91', 'comite mixto', NULL, 'php/uploads/producto_1313603410.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 91, 'poleras', NULL, NULL, 'si', '30', '1.200', '7.60', '2.00', 'epr-12', 'logo con letra"c"'),
('prd-92', 'ades', NULL, 'php/uploads/producto_1313603876.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 92, 'toallas\nbolsillos', NULL, NULL, 'si', '2', '2.360', '7.61', '3.14', 'epr-2', 'ades'),
('prd-93', 'knorr', NULL, 'php/uploads/producto_1313604076.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 93, 'poleras\nbolsillos', NULL, NULL, 'si', '6', '5.500', '5.10', '5.50', 'epr-2', 'knorr'),
('prd-94', 'sedal', NULL, 'php/uploads/producto_1313604230.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 94, 'bolisillo', NULL, NULL, 'si', '17', '1.270', '6.70', '2.40', 'epr-2', 'sedal'),
('prd-95', 'iper,omo,sedal', NULL, 'php/uploads/producto_1313607069.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 95, 'poleras', NULL, NULL, 'si', '23', '7.690', '7.30', '15.66', 'epr-2', 'logos mixtos unilever'),
('prd-96', 'knorr,ades', NULL, 'php/uploads/producto_1313607210.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 96, 'poleras', NULL, NULL, 'si', '24', '8.150', '8.00', '13.80', 'epr-2', 'logos mixtos unilever'),
('prd-97', '3m ', NULL, 'php/uploads/producto_1314710112.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 97, 'gorras\npoleras', NULL, NULL, 'si', '25', '6.260', '9.64', '5.15', 'epr-46', 'c-13"m"'),
('prd-98', 'conspicuiti', NULL, 'php/uploads/producto_1313695826.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 98, 'polera', NULL, NULL, 'si', '23', '3.400', '9.00', '2.62', 'epr-46', 'c-13"m"'),
('prd-99', '3m cinta reflectiva', NULL, 'php/uploads/producto_1313695754.JPG', NULL, NULL, 'gru-2', 'ctp-11', NULL, NULL, NULL, 99, 'poleras', NULL, NULL, 'si', '24', '7.140', '10.74', '5.40', 'epr-46', 'c-13"m"');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `idproveedor` varchar(20) NOT NULL,
  `idciudad` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `telefono` varchar(20) default NULL,
  `representante` varchar(200) default NULL,
  `direccion` varchar(200) default NULL,
  `web` varchar(200) default NULL,
  `email` varchar(50) default NULL,
  `numero` int(11) default NULL,
  `pais` varchar(20) default NULL,
  `observacion` varchar(20) default NULL,
  PRIMARY KEY  (`idproveedor`),
  KEY `fk_relationship_6` (`idciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`idproveedor`, `idciudad`, `codigo`, `nombre`, `telefono`, `representante`, `direccion`, `web`, `email`, `numero`, `pais`, `observacion`) VALUES
('pro-1', 'ciu-1', 'coc', 'Tienda dona coca', '909090', 'dona coca', 'c/moxos', NULL, NULL, 1, 'bolivia', NULL),
('pro-10', 'ciu-1', 'a6', 'waldo aliaga', '4271524', NULL, NULL, NULL, NULL, 10, 'bolivia', NULL),
('pro-11', 'ciu-4', 'a7', 'susana aguirre', '5244123', NULL, NULL, NULL, NULL, 11, 'bolivia', NULL),
('pro-12', 'ciu-1', 'a8', 'angel moya', '71954667', NULL, NULL, NULL, NULL, 12, 'bolivia', 'toallas'),
('pro-13', 'ciu-1', 'a9', 'yola ayaviri', '4751823', NULL, NULL, NULL, NULL, 13, 'bolivia', 'dueÃƒÆ’Ã‚Â±a del car'),
('pro-14', 'ciu-3', 'a10', 'aujas de costura para maquinas', '4460552', NULL, NULL, NULL, NULL, 14, 'bolivia', NULL),
('pro-15', 'ciu-1', 'a11', 'doÃƒÆ’Ã‚Â±a adeleila', '4256370', NULL, 'av. barrientos esq pulacayo', NULL, NULL, 15, 'bolivia', 'puesto al lado del 2'),
('pro-16', 'ciu-1', 'a12', 'adornos elisabeth huan veliz', NULL, NULL, 'washington entre ayacucho y cochabamba', NULL, NULL, 16, 'bolivia', NULL),
('pro-17', 'ciu-1', 'b', 'bordado zurita', '4298094', NULL, NULL, NULL, NULL, 17, 'bolivia', NULL),
('pro-18', 'ciu-1', 'b1', 'bordado cluber', '4216221', NULL, NULL, NULL, NULL, 18, 'bolivia', NULL),
('pro-19', 'ciu-1', 'b2', 'bordados danly', '4481921', NULL, NULL, NULL, NULL, 19, 'bolivia', NULL),
('pro-2', 'ciu-1', 'asia', 'importadora asia', '909090', 'gilber sandoval', NULL, NULL, NULL, 2, 'bolivia', NULL),
('pro-20', 'ciu-1', 'b3', 'betzabe', '4820083', NULL, NULL, NULL, NULL, 20, 'bolivia', 'puesto nÃƒâ€šÃ‚Âª15 '),
('pro-21', 'ciu-3', 'b4', 'bordadora santa cruz', '3555936', 'carlos la fuente', NULL, NULL, NULL, 21, 'bolivia', 'dueÃƒÆ’Ã‚Â±o del pal'),
('pro-22', 'ciu-1', 'b5', 'boton', '4542661', 'alfredo quispe', NULL, NULL, NULL, 22, 'bolivia', 'proovedor de boton e'),
('pro-23', 'ciu-1', 'b6', 'bordado tienda', '4751961', NULL, NULL, NULL, NULL, 23, 'bolivia', NULL),
('pro-24', 'ciu-1', 'b7', 'bordadora eninfo', '4746376', 'angelica', NULL, NULL, NULL, 24, 'bolivia', NULL),
('pro-25', 'ciu-1', 'b8', 'bolsas de celofan', '4223079', NULL, 'calle lanza', NULL, NULL, 25, 'bolivia', NULL),
('pro-26', 'ciu-1', 'b9', 'bazar ias', '4263010', 'doÃƒÆ’Ã‚Â±a teresa', NULL, NULL, NULL, 26, 'bolivia', 'boton de jheans'),
('pro-27', 'ciu-1', 'b10', 'bolivianita bordado', '4668672', NULL, 'av. ayacucho', NULL, NULL, 27, 'bolivia', NULL),
('pro-28', 'ciu-1', 'b11', 'tienda doÃƒÆ’Ã‚Â±a bacilia', '4226614', NULL, NULL, NULL, NULL, 28, 'bolivia', NULL),
('pro-29', 'ciu-1', 'b12', 'caseta', '4550238', 'blanca calisaya', NULL, NULL, NULL, 29, 'bolivia', NULL),
('pro-3', 'ciu-1', 'pun', 'importadora punata', '909090', 'erio flores', NULL, NULL, NULL, 3, 'bolivia', NULL),
('pro-30', 'ciu-1', 'b13', 'bolivia mar', '4556727', NULL, NULL, NULL, NULL, 30, 'bolivia', NULL),
('pro-31', 'ciu-1', 'prv1', 'nelson caceres', '45552445', 'nelson caceres', NULL, NULL, NULL, 31, 'bolivia', 'telas'),
('pro-32', 'ciu-1', 'prv2', 'blanca calizaya', '4550238 dom. 4562754', 'blanca calizaya', 'caseta #15', NULL, NULL, 32, 'bolivia', NULL),
('pro-33', 'ciu-3', 'prv4', 'carver etiquetas', '365285 - 372579', 'victor zambrana', NULL, NULL, NULL, 33, 'bolivia', NULL),
('pro-34', 'ciu-1', 'prov5', 'camisas ester', '12483316', 'ester', NULL, NULL, NULL, 34, 'bolivia', NULL),
('pro-35', 'ciu-1', 'prov6', 'esponjas', '4232670', 'urcente', 'aroma 427 entre esteban arce y 24 de mayo', NULL, NULL, 35, 'bolivia', NULL),
('pro-36', 'ciu-1', 'prov.8', 'tienda guetzi', '4290284', NULL, NULL, NULL, NULL, 36, 'bolivia ', NULL),
('pro-37', 'ciu-1', 'prov10', 'hebi landia', '4512383 dom. 4503840', 'ramiro colque', 'esteban arce # 738', NULL, NULL, 37, 'bolivia', NULL),
('pro-38', 'ciu-1', 'prov11', 'lavadora azul azul', '4327611-70712260', NULL, NULL, NULL, NULL, 38, 'bolivia', NULL),
('pro-39', 'ciu-1', 'prov.', 'bruno nina', '4591969-72278242', 'bruno nina', NULL, NULL, NULL, 39, 'bolivia', NULL),
('pro-4', 'ciu-3', 'a', 'monica alcocer', '5243693', NULL, NULL, NULL, NULL, 4, 'Bolivia', 'Proovedora para ropa'),
('pro-40', 'ciu-1', 'prov', 'placticoa transparentes', '4126082-4251245-4554', NULL, NULL, NULL, NULL, 40, 'bolivia ', NULL),
('pro-41', 'ciu-1', 'prov. 13', 'punto creativo', '4506371 - 72252824', NULL, NULL, NULL, NULL, 41, NULL, NULL),
('pro-42', 'ciu-1', 'prov. 14', 'publicron ', '4277254-72296969', 'javier', NULL, NULL, NULL, 42, NULL, NULL),
('pro-43', 'ciu-1', 'prov. 15', 'rosmery', '4556470-76999922', NULL, 'puesto # 18', NULL, NULL, 43, NULL, NULL),
('pro-44', 'ciu-1', '01010101', 'regularizacion', NULL, 's/n', NULL, NULL, NULL, 44, 'bolivia', 'es para regularizar '),
('pro-5', 'ciu-1', 'a1', 'antonio arizaga', '4223861', NULL, NULL, NULL, NULL, 5, 'bolivia', NULL),
('pro-6', 'ciu-1', 'a2', 'lilian aguilar', '4332908', NULL, NULL, NULL, NULL, 6, 'bolivia', NULL),
('pro-7', 'ciu-1', 'a3', 'sandra aguilar', '4578071', NULL, NULL, NULL, NULL, 7, 'bolivia', NULL),
('pro-8', 'ciu-1', 'a4', 'rosaura allendre', '4421331', NULL, NULL, NULL, NULL, 8, 'bolivia', NULL),
('pro-9', 'ciu-1', 'a5', 'tito antezana', '4540049', NULL, NULL, NULL, NULL, 9, 'bolivia', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recepciones`
--

CREATE TABLE `recepciones` (
  `idrecepcion` varchar(20) NOT NULL,
  `iddistribucion` varchar(20) default NULL,
  `op` varchar(20) default NULL,
  `cantidad` double(15,3) default NULL,
  `fecha` date default NULL,
  `hora` time default NULL,
  `detalle` varchar(200) default NULL,
  `idusuario` varchar(20) default NULL,
  `saldocantidad` double(15,3) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idrecepcion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `recepciones`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `responsables`
--

CREATE TABLE `responsables` (
  `idresponsable` varchar(20) NOT NULL,
  `idempresa` varchar(20) default NULL,
  `idciudad` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `apellido1` varchar(50) default NULL,
  `direccion` varchar(200) default NULL,
  `email` varchar(50) default NULL,
  `fax` varchar(20) default NULL,
  `numero` int(11) default NULL,
  `estado` varchar(20) default NULL,
  `telefono` varchar(20) default NULL,
  `celular` varchar(20) default NULL,
  `observacion` varchar(200) default NULL,
  `fechanacimiento` date default NULL,
  PRIMARY KEY  (`idresponsable`),
  KEY `fk_relationship_10` (`idciudad`),
  KEY `fk_relationship_9` (`idempresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `responsables`
--

INSERT INTO `responsables` (`idresponsable`, `idempresa`, `idciudad`, `codigo`, `nombre`, `apellido1`, `direccion`, `email`, `fax`, `numero`, `estado`, `telefono`, `celular`, `observacion`, `fechanacimiento`) VALUES
('res-10', 'epr-2', 'ciu-1', '009', 'ignacio', 'chopitea', 'c. casto rojas y melchor perez, edif. veronica iii, dept. 6a', 'ignacio.chopitea@unilever.com', NULL, 10, 'Activo', '4772219 - 76966692', NULL, NULL, '2012-12-23'),
('res-100', 'epr-57', 'ciu-1', '087', 'marco ', 'ARANDIA', NULL, NULL, NULL, 100, 'Activo', '70779377', NULL, NULL, NULL),
('res-101', 'epr-58', 'ciu-1', '088', 'diana', 'moya', NULL, NULL, NULL, 101, 'Activo', NULL, NULL, NULL, NULL),
('res-102', 'epr-59', 'ciu-3', '090', 'iris', ' ibarra', NULL, NULL, NULL, 102, 'Activo', NULL, NULL, NULL, NULL),
('res-103', 'epr-60', 'ciu-1', '089', 'cesar', 'paz', NULL, NULL, NULL, 103, 'Activo', NULL, NULL, NULL, NULL),
('res-104', 'epr-61', 'ciu-1', '091', 'daniel', ' delgadillo', NULL, 'wilda@supernet.com.bo', NULL, 104, 'Activo', '4725050', NULL, NULL, NULL),
('res-105', 'epr-62', 'ciu-1', NULL, NULL, NULL, NULL, NULL, NULL, 105, 'Activo', NULL, NULL, NULL, NULL),
('res-106', 'epr-63', 'ciu-1', NULL, NULL, NULL, NULL, NULL, NULL, 106, 'Activo', NULL, NULL, NULL, NULL),
('res-107', 'epr-64', 'ciu-1', NULL, NULL, NULL, NULL, NULL, NULL, 107, 'Activo', NULL, NULL, NULL, NULL),
('res-108', 'epr-6', 'ciu-1', '095', 'denisse', 'melgar', NULL, NULL, NULL, 108, 'Activo', '4154900', NULL, NULL, NULL),
('res-11', 'epr-2', 'ciu-1', '010', 'jaqueline', 'revollo', 'av. blanco galindo km. 10.5', 'jaqueline.revollo@unilever.com', NULL, 11, 'Activo', '4351515 - 70794051', NULL, NULL, NULL),
('res-12', 'epr-2', 'ciu-1', '011', 'jorge', 'sejas', 'av. blanco galindo km. 10.5', 'jorge.sejas@unilever.com', NULL, 12, 'Activo', '4772103 - 70736101', NULL, NULL, '2012-11-21'),
('res-13', 'epr-2', 'ciu-1', '012', 'jose luis', 'paraba', 'av. blanco galindo km. 10.5', 'jose-luis.paraba@unilever.com', NULL, 13, 'Activo', '75495712', NULL, NULL, NULL),
('res-14', 'epr-2', 'ciu-1', '013', 'juan', 'mercado', 'av. blanco galindo km. 10.5', 'juan.mercado@unilever.com', NULL, 14, 'Activo', '70720161', NULL, NULL, NULL),
('res-15', 'epr-2', 'ciu-1', '014', 'sarah', 'galindo', 'av. blanco galindo km. 10.5', 'sarah.galindo@unilever.com', NULL, 15, 'Activo', '70732453', NULL, NULL, NULL),
('res-16', 'epr-2', 'ciu-1', '015', 'luis fernando', 'aguilar', 'av. blanco galindo km. 10.5', 'luis.aguilar@unilever.com', NULL, 16, 'Activo', '75495714', NULL, NULL, NULL),
('res-17', 'epr-2', 'ciu-1', '016', 'marco', 'sanjinez', 'av. blanco galindo km. 10.5', 'marco.sanjinez@unilever.com', NULL, 17, 'Activo', '75495741', NULL, NULL, '2012-11-22'),
('res-18', 'epr-2', 'ciu-1', '017', 'marisol', 'cadima', 'c. jordan casi ayacucho, edif. celca', 'marisol.cadima@unilever.com', NULL, 18, 'Activo', '72221087', NULL, NULL, NULL),
('res-19', 'epr-2', 'ciu-1', '018', 'mircko', 'rojas', 'av. blanco galindo km. 10.5', 'mircko.rojas@unilever.com', NULL, 19, 'Activo', '75495718', NULL, NULL, NULL),
('res-2', 'epr-2', 'ciu-1', '001', 'eduardo', 'velasco', 'av. blanco galindo km. 10.5', 'eduardo.velasco@unilever.com', NULL, 2, 'Activo', '4351515', NULL, NULL, NULL),
('res-20', 'epr-2', 'ciu-1', '019', 'pablo', 'morsoletto', 'av. blanco galindo km. 10.5', 'pablo.morsoletto@unilever.com', NULL, 20, 'Activo', '70715736', NULL, NULL, NULL),
('res-21', 'epr-2', 'ciu-1', '020', 'ricardo', 'lujan', 'av. blanco galindo km. 10.5', 'ricardo.lujan@unilever.com', NULL, 21, 'Activo', '75495722', NULL, NULL, NULL),
('res-22', 'epr-2', 'ciu-1', '021', 'sandra', 'delgadillo', 'av. blanco galindo km. 10.5', 'sandra.delgadillo@unilever.com', NULL, 22, 'Activo', '4772211 - 75495725', NULL, NULL, NULL),
('res-23', 'epr-2', 'ciu-1', '022', 'sergio', 'alvarez', 'av. blanco galindo km. 10.5', 'sergio.alvarez-daher@unilever.com', NULL, 23, 'Activo', '4351515', NULL, NULL, NULL),
('res-24', 'epr-2', 'ciu-1', '023', 'vanessa', 'zambrana', 'av. blanco galindo km. 10.5', 'vanessa.zambrana@unilever.com', NULL, 24, 'Activo', '4772199 - 75495726', NULL, NULL, NULL),
('res-25', 'epr-3', 'ciu-1', '025', 'carmen', ' abujder', NULL, 'negabujder@yahoo.es', NULL, 25, 'Activo', '4720600', NULL, NULL, NULL),
('res-26', 'epr-3', 'ciu-1', '024', 'carlos', 'lora', 'carretera sacaba km 4.5', 'clora@copelme.com', NULL, 26, 'Activo', '76970173', NULL, NULL, NULL),
('res-28', 'epr-3', 'ciu-1', '026', 'jose', 'mariscal', 'carretera sacaba km. 4.5', NULL, NULL, 28, 'Activo', '70380369', NULL, NULL, NULL),
('res-29', 'epr-3', 'ciu-1', '027', 'mauricio', 'salvatierra', 'carretera sacaba km. 4.5', 'msalazar@copelme.com', NULL, 29, 'Activo', '67402394', NULL, NULL, NULL),
('res-3', 'epr-2', 'ciu-1', '002', 'vanessa', 'lizarazu', 'av. blanco galindo km 10.5', 'vanessa.lizarazu@unilever.com', NULL, 3, 'Activo', '70758098', NULL, NULL, NULL),
('res-30', 'epr-4', 'ciu-1', '029', 'hugo ', 'galindo', NULL, NULL, NULL, 30, 'Activo', '70350349', NULL, NULL, NULL),
('res-31', 'epr-5', 'ciu-1', '031', 'ing. ramiro', ' calderon', NULL, NULL, NULL, 31, 'Activo', '71732282', NULL, NULL, NULL),
('res-32', 'epr-6', 'ciu-1', '047', 'maria elena ', 'jimenez', NULL, NULL, NULL, 32, 'Activo', NULL, NULL, NULL, NULL),
('res-33', 'epr-7', 'ciu-5', '046', 'luis fernando', ' solares', 'pasaje armando alba nro. 80', NULL, NULL, 33, 'Activo', '6453882', NULL, NULL, NULL),
('res-34', 'epr-8', 'ciu-1', '048', 'cinthia', ' espada', 'av. america este final y av. uyuni final nro. 200', NULL, NULL, 34, 'Activo', '4483303', NULL, NULL, NULL),
('res-35', 'epr-9', 'ciu-3', '056', 'paola', ' frias', NULL, NULL, NULL, 35, 'Activo', '77839999', NULL, NULL, NULL),
('res-36', 'epr-10', 'ciu-1', '028', 'edwin ', 'quintanilla', NULL, NULL, NULL, 36, 'Activo', '72223022', NULL, NULL, NULL),
('res-39', 'epr-4', 'ciu-1', '030', 'gabriel', 'castro', 'Av. Uyuni nro. 1103', 'burncba@gmail.com', NULL, 39, 'Activo', '70350179 - 4799000', NULL, NULL, NULL),
('res-4', 'epr-2', 'ciu-1', '003', 'andrea', 'torres', 'av. blanco galindo km 10.5', 'andrea.torres-ortiz@unilever.com', NULL, 4, 'Activo', '4351515', NULL, NULL, NULL),
('res-41', 'epr-7', 'ciu-5', '032', 'amelia', 'ruiz', 'pasaje armando alba nro. 80', 'a.ruiz@fancesa.com', NULL, 41, 'Activo', '6453882', NULL, NULL, NULL),
('res-42', 'epr-8', 'ciu-1', '033', 'gabriela', 'gomez', 'av. america este final y av. uyuni final nro. 200', 'cuentas1@chromart.com', NULL, 42, 'Activo', '79960238', NULL, NULL, NULL),
('res-43', 'epr-8', 'ciu-1', '034', 'natalia', 'toranzo', 'av. america este final y av. uyuni final nro. 200', 'cuentas3@chromart.com', NULL, 43, 'Activo', '70344829', NULL, NULL, NULL),
('res-44', 'epr-8', 'ciu-1', '035', 'luz', 'marquina', 'av. america este final y av. uyuni final nro. 200', 'cuentas2@chromart.com', NULL, 44, 'Activo', '70713603', NULL, NULL, NULL),
('res-45', 'epr-9', 'ciu-3', '036', 'arcelina', 'haiek', 'equipetrol c. 7 oeste nro. 12', 'ahaiek@freelo.com.bo', NULL, 45, 'Activo', '76001185', NULL, NULL, NULL),
('res-46', 'epr-9', 'ciu-3', '037', 'juan pablo', 'quiroga', 'equipetrol c. 7 oeste nro. 12', 'jpquiroga@freelo.com.bo', NULL, 46, 'Activo', '79880518', NULL, NULL, NULL),
('res-47', 'epr-11', 'ciu-1', '039', 'bertha', ' padilla', NULL, NULL, NULL, 47, 'Activo', '4710164', NULL, NULL, NULL),
('res-48', 'epr-12', 'ciu-1', '049', 'neiza', ' vega', NULL, NULL, NULL, 48, 'Activo', '72221590', NULL, NULL, NULL),
('res-49', 'epr-13', 'ciu-1', '040', 'silvia ', 'romero', NULL, NULL, NULL, 49, 'Activo', '4523001', NULL, NULL, NULL),
('res-5', 'epr-2', 'ciu-2', '004', 'claudia', 'vera', NULL, 'claudia.vera@unilever.com', NULL, 5, 'Activo', '70125822 - 76796020', NULL, NULL, NULL),
('res-50', 'epr-14', 'ciu-1', '052', 'adima', ' jaldin', NULL, NULL, NULL, 50, 'Activo', '4662733', NULL, NULL, NULL),
('res-51', 'epr-15', 'ciu-1', '051', 'jose luis ', 'almendras', NULL, NULL, NULL, 51, 'Activo', '70771945', NULL, NULL, NULL),
('res-52', 'epr-16', 'ciu-1', '053', 'gaston', ' mendez', NULL, NULL, NULL, 52, 'Activo', '4455228-77915999', NULL, NULL, NULL),
('res-53', 'epr-17', 'ciu-1', '038', 'lilian', ' hoffmann', NULL, NULL, NULL, 53, 'Activo', '4501300', NULL, NULL, NULL),
('res-55', 'epr-18', 'ciu-1', '082', 'a', 'a', NULL, NULL, NULL, 55, 'Activo', NULL, NULL, NULL, NULL),
('res-57', 'epr-20', 'ciu-1', '083', 'jean christian', 'caussin', NULL, NULL, NULL, 57, 'Activo', NULL, NULL, NULL, NULL),
('res-59', 'epr-22', 'ciu-1', '058', 'gilbert', 'sampieri', NULL, NULL, NULL, 59, 'Activo', '4503333-71721425', NULL, NULL, NULL),
('res-6', 'epr-2', 'ciu-1', '005', 'nayra', 'iriarte', 'Eudoro galindo, condominio gran bretaÃƒÆ’Ã‚Â±a', 'nayra.iriarte@unilever.com', NULL, 6, 'Activo', '4772245 - 70738729', NULL, NULL, '2012-09-23'),
('res-60', 'epr-23', 'ciu-1', '059', 'saul', 'lopez', 'tarapaca nro 317 entre mayor rocha y ecuador', NULL, NULL, 60, 'Activo', '4582953 - 72245857', NULL, NULL, NULL),
('res-61', 'epr-24', 'ciu-1', '050', 'tania', 'vasquez', NULL, NULL, NULL, 61, 'Activo', '4281858', NULL, NULL, NULL),
('res-62', 'epr-25', 'ciu-1', '057', 'alejandro ', 'escalante', NULL, NULL, NULL, 62, 'Activo', '4288400', NULL, NULL, NULL),
('res-63', 'epr-26', 'ciu-1', '061', 'martha ', 'vega', NULL, NULL, NULL, 63, 'Activo', '4525160', NULL, NULL, NULL),
('res-64', 'epr-27', 'ciu-3', '054', 'nathaly ', 'cuellar', NULL, NULL, NULL, 64, 'Activo', '77390389', NULL, NULL, NULL),
('res-65', 'epr-28', 'ciu-1', '062', 'alvaro ', 'garcÃ­a mesa', NULL, NULL, NULL, 65, 'Activo', '4489494', NULL, NULL, NULL),
('res-66', 'epr-29', 'ciu-1', '055', 'maria luisa ', 'cespedes', NULL, NULL, NULL, 66, 'Activo', '4125166 - 72250201', NULL, NULL, NULL),
('res-67', 'epr-30', 'ciu-1', '063', 'vladimir ', 'cosio', NULL, NULL, NULL, 67, 'Activo', '70795011', NULL, NULL, NULL),
('res-7', 'epr-2', 'ciu-1', '006', 'fabiola', 'garcia', 'av. blanco galindo km. 10.5', 'fabiola.garcia@unilever.com', NULL, 7, 'Activo', '4351515 - 77900404', NULL, NULL, NULL),
('res-70', 'epr-33', 'ciu-1', '084', 'a', 'a', NULL, NULL, NULL, 70, 'Activo', NULL, NULL, NULL, NULL),
('res-71', 'epr-34', 'ciu-1', '067', 'vanessa ', 'mora', NULL, NULL, NULL, 71, 'Activo', '4434918', NULL, NULL, NULL),
('res-72', 'epr-35', 'ciu-1', '068', 'guelly ', 'arevalo', NULL, NULL, NULL, 72, 'Activo', NULL, NULL, NULL, NULL),
('res-73', 'epr-36', 'ciu-1', '074', 'melvy ', 'rodriguez', NULL, NULL, NULL, 73, 'Activo', '4232816', NULL, NULL, NULL),
('res-74', 'epr-37', 'ciu-1', '069', 'paola ', 'angulo', NULL, NULL, NULL, 74, 'Activo', '4799067', NULL, NULL, '2012-01-17'),
('res-75', 'epr-38', 'ciu-1', '071', 'claudia ', 'villca', NULL, NULL, NULL, 75, 'Activo', '4379000', NULL, NULL, NULL),
('res-76', 'epr-39', 'ciu-1', '072', 'ana marÃ­a ', 'vega', NULL, NULL, NULL, 76, 'Activo', NULL, NULL, NULL, NULL),
('res-77', 'epr-40', 'ciu-1', '075', 'miriam ', 'claure', NULL, NULL, NULL, 77, 'Activo', NULL, NULL, NULL, NULL),
('res-78', 'epr-41', 'ciu-1', '077', 'boris ', 'jaldin', NULL, NULL, NULL, 78, 'Activo', '4248283', NULL, NULL, NULL),
('res-79', 'epr-42', 'ciu-1', '078', 'sonia ', 'zambrana', NULL, NULL, NULL, 79, 'Activo', '4258862', NULL, NULL, NULL),
('res-8', 'epr-2', 'ciu-1', '007', 'gabriela', 'pacheco', 'av. blanco galindo km. 10.5', 'gabriela.pacheco@unilever.com', NULL, 8, 'Activo', '4772244 - 70709411', NULL, NULL, NULL),
('res-80', 'epr-43', 'ciu-1', '079', 'janaina', ' ribero', NULL, NULL, NULL, 80, 'Activo', '4663100-70732443', NULL, NULL, NULL),
('res-81', 'epr-44', 'ciu-1', '066', 'martha ', 'magne', NULL, NULL, NULL, 81, 'Activo', '77435557', NULL, NULL, NULL),
('res-84', 'epr-45', 'ciu-1', '076', 'ruth ', 'magne', NULL, NULL, NULL, 84, 'Activo', '4247213-4286139', NULL, NULL, NULL),
('res-86', 'epr-47', 'ciu-1', '081', 'a', 'a', NULL, NULL, NULL, 86, 'Activo', NULL, NULL, NULL, NULL),
('res-87', 'epr-48', 'ciu-1', '080', 'a', 'a', NULL, NULL, NULL, 87, 'Activo', NULL, NULL, NULL, NULL),
('res-88', 'epr-49', 'ciu-5', '045', 'luis fernando ', 'solares gumucio', NULL, NULL, NULL, 88, 'Activo', '6453882', NULL, NULL, NULL),
('res-89', 'epr-50', 'ciu-1', '070', 'cleidy ', 'zambrana', NULL, NULL, NULL, 89, 'Activo', '4140873', NULL, NULL, NULL),
('res-9', 'epr-2', 'ciu-1', '008', 'geraldine', 'mercado', 'av. blanco galindo km. 10.5', 'geraldine.mercado@unilever.com', NULL, 9, 'Activo', '4351515 - 70352523', NULL, NULL, NULL),
('res-90', 'epr-51', 'ciu-1', '065', 'ronald ', 'coronel', NULL, NULL, NULL, 90, 'Activo', '4520317', NULL, NULL, NULL),
('res-91', 'epr-52', 'ciu-1', '073', 'carla', 'villalobos', NULL, NULL, NULL, 91, 'Activo', NULL, NULL, NULL, NULL),
('res-92', 'epr-10', 'ciu-1', '041', 'jose', 'padilla', NULL, NULL, NULL, 92, 'Activo', '72244192', NULL, NULL, NULL),
('res-93', 'epr-4', 'ciu-1', '042', 'bernardo', 'vargas', NULL, NULL, NULL, 93, 'Activo', '70350369', NULL, NULL, NULL),
('res-94', 'epr-2', 'ciu-1', '043', 'enrique', 'roman', NULL, NULL, NULL, 94, 'Activo', '70715536', NULL, NULL, NULL),
('res-95', 'epr-53', 'ciu-1', '064', 'jessica', ' roa', NULL, NULL, NULL, 95, 'Activo', NULL, NULL, NULL, NULL),
('res-96', 'epr-54', 'ciu-1', '060', 'remberto ', 'quina', NULL, NULL, NULL, 96, 'Activo', '4582880-76902100', NULL, NULL, NULL),
('res-97', 'epr-15', 'ciu-1', '044', 'olivia', 'baÃ±ado', NULL, NULL, NULL, 97, 'Activo', '77951954', NULL, NULL, NULL),
('res-98', 'epr-55', 'ciu-1', '086', 'david ', 'pozo', NULL, NULL, NULL, 98, 'Activo', '72251371', NULL, NULL, NULL),
('res-99', 'epr-56', 'ciu-1', '085', 'walter', 'martinez', NULL, NULL, NULL, 99, 'Activo', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `idrol` varchar(20) NOT NULL,
  `nombre` varchar(100) default NULL,
  `numero` int(11) default NULL,
  `estado` varchar(20) default NULL,
  `seguridad` varchar(20) default NULL,
  PRIMARY KEY  (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `rol`
--

INSERT INTO `rol` (`idrol`, `nombre`, `numero`, `estado`, `seguridad`) VALUES
('rol-1000', 'Administrador', 1000, 'Activo', NULL),
('rol-1002', 'bORDADO', 1002, 'Activo', 'admin1307547210'),
('rol-1003', 'Recepcion', 1003, 'Activo', 'admin1327909669');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolfuncion`
--

CREATE TABLE `rolfuncion` (
  `idrol` varchar(20) default NULL,
  `idfuncion` varchar(20) default NULL,
  KEY `fk_relationship_2` (`idrol`),
  KEY `fk_relationship_3` (`idfuncion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `rolfuncion`
--

INSERT INTO `rolfuncion` (`idrol`, `idfuncion`) VALUES
('rol-1002', 'fun1006'),
('rol-1002', 'fun1007'),
('rol-1002', 'fun2007'),
('rol-1002', 'fun4001'),
('rol-1000', 'fun1000'),
('rol-1000', 'fun1001'),
('rol-1000', 'fun1002'),
('rol-1000', 'fun1003'),
('rol-1000', 'fun1004'),
('rol-1000', 'fun1005'),
('rol-1000', 'fun1006'),
('rol-1000', 'fun1007'),
('rol-1000', 'fun1008'),
('rol-1000', 'fun1009'),
('rol-1000', 'fun1010'),
('rol-1000', 'fun1011'),
('rol-1000', 'fun1012'),
('rol-1000', 'fun1013'),
('rol-1000', 'fun1014'),
('rol-1000', 'fun2000'),
('rol-1000', 'fun2001'),
('rol-1000', 'fun2002'),
('rol-1000', 'fun2003'),
('rol-1000', 'fun2004'),
('rol-1000', 'fun2006'),
('rol-1000', 'fun2007'),
('rol-1000', 'fun2010'),
('rol-1000', 'fun2011'),
('rol-1000', 'fun3000'),
('rol-1000', 'fun3001'),
('rol-1000', 'fun3002'),
('rol-1000', 'fun4000'),
('rol-1000', 'fun4001'),
('rol-1000', 'fun5000'),
('rol-1000', 'fun5001'),
('rol-1000', 'fun6000'),
('rol-1000', 'fun6001'),
('rol-1000', 'fun6002'),
('rol-1000', 'fun7000'),
('rol-1000', 'fun8000'),
('rol-1000', 'fun8001'),
('rol-1000', 'fun9000'),
('rol-1003', 'fun1006'),
('rol-1003', 'fun1007'),
('rol-1003', 'fun1008'),
('rol-1003', 'fun1013'),
('rol-1003', 'fun2006'),
('rol-1003', 'fun4000'),
('rol-1003', 'fun4001');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidades`
--

CREATE TABLE `unidades` (
  `idunidad` varchar(20) NOT NULL,
  `nombre` varchar(20) default NULL,
  `codigo` varchar(20) default NULL,
  `detalle` varchar(20) default NULL,
  `numero` int(11) default NULL,
  PRIMARY KEY  (`idunidad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Volcar la base de datos para la tabla `unidades`
--

INSERT INTO `unidades` (`idunidad`, `nombre`, `codigo`, `detalle`, `numero`) VALUES
('uni-1', 'metros', 'mts', NULL, 1),
('uni-2', 'pieza', 'pza', NULL, 2),
('uni-3', 'rollo', 'rollo', NULL, 3),
('uni-4', 'paquete', 'pa', NULL, 4),
('uni-5', 'unidades', 'unid', NULL, 5),
('uni-6', 'cono', 'co', NULL, 6),
('uni-7', 'cajita', 'ca', NULL, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idusuario` varchar(20) NOT NULL,
  `idrol` varchar(20) default NULL,
  `nombre` varchar(100) default NULL,
  `apellido1` varchar(50) default NULL,
  `apellido2` varchar(50) default NULL,
  `ci` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `telefono` varchar(20) default NULL,
  `celular` varchar(20) default NULL,
  `login` varchar(20) default NULL,
  `paswd` varchar(100) default NULL,
  `fechareg` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `numero` int(11) default NULL,
  `estado` varchar(20) default NULL,
  PRIMARY KEY  (`idusuario`),
  KEY `fk_relationship_1` (`idrol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idusuario`, `idrol`, `nombre`, `apellido1`, `apellido2`, `ci`, `email`, `telefono`, `celular`, `login`, `paswd`, `fechareg`, `numero`, `estado`) VALUES
('usr-1000', 'rol-1000', 'ubaldo1', 'villazon1', 'villca1', '5190630', 'uvillazon@doptima.com', '111111', '111111', 'admin', '21232f297a57a5a743894a0e4a801fc3', '2011-03-16 23:00:00', 1000, 'Activo'),
('usr-1004', 'rol-1002', 'daniel', 'guzman', 'rocha', '4078527', NULL, NULL, NULL, 'daniel', 'cfae9b1abce80c47c6b6c52de078b474', '2011-07-18 00:00:00', 1004, 'Activo'),
('usr-1006', 'rol-1000', 'herbert', 'navarro', NULL, '1234567', NULL, NULL, NULL, 'herbert', '74b0328a08e7d9e213b1ea77ba32198d', '2012-01-30 00:00:00', 1006, 'Activo'),
('usr-1007', 'rol-1003', 'alejandra', 'parada', NULL, '3791228', NULL, '4539477', NULL, 'alejandra', '703042aefd627a8c86c4de140cc80c6e', '2012-01-30 00:00:00', 1007, 'Activo');

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `fk_relationship_7` FOREIGN KEY (`idciudad`) REFERENCES `ciudades` (`idciudad`);

--
-- Filtros para la tabla `compras`
--
ALTER TABLE `compras`
  ADD CONSTRAINT `compras_fk` FOREIGN KEY (`idempleado`) REFERENCES `empleados` (`idempleado`),
  ADD CONSTRAINT `compras_fk1` FOREIGN KEY (`idproveedor`) REFERENCES `proveedores` (`idproveedor`);

--
-- Filtros para la tabla `detallecompra`
--
ALTER TABLE `detallecompra`
  ADD CONSTRAINT `detallecompra_fk` FOREIGN KEY (`idcompra`) REFERENCES `compras` (`idcompra`);

--
-- Filtros para la tabla `detalleproduccionproceso`
--
ALTER TABLE `detalleproduccionproceso`
  ADD CONSTRAINT `detalleproduccionproceso_fk` FOREIGN KEY (`idproduccionproceso`) REFERENCES `produccionprocesos` (`idproduccionproceso`);

--
-- Filtros para la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD CONSTRAINT `fk_relationship_11` FOREIGN KEY (`idcargo`) REFERENCES `cargos` (`idcargo`),
  ADD CONSTRAINT `fk_relationship_12` FOREIGN KEY (`idciudad`) REFERENCES `ciudades` (`idciudad`);

--
-- Filtros para la tabla `empresas`
--
ALTER TABLE `empresas`
  ADD CONSTRAINT `fk_relationship_8` FOREIGN KEY (`idciudad`) REFERENCES `ciudades` (`idciudad`);

--
-- Filtros para la tabla `funcion`
--
ALTER TABLE `funcion`
  ADD CONSTRAINT `fk_relationship_4` FOREIGN KEY (`idcategoriafuncion`) REFERENCES `categoriafuncion` (`idcategoriafuncion`);

--
-- Filtros para la tabla `kardexmateriaprima`
--
ALTER TABLE `kardexmateriaprima`
  ADD CONSTRAINT `kardexmateriaprima_fk` FOREIGN KEY (`idmateriaprima`) REFERENCES `materiaprima` (`idmateriaprima`);

--
-- Filtros para la tabla `materiaprima`
--
ALTER TABLE `materiaprima`
  ADD CONSTRAINT `materiaprima_fk` FOREIGN KEY (`idcategoria`) REFERENCES `categorias` (`idcategoria`),
  ADD CONSTRAINT `materiaprima_fk1` FOREIGN KEY (`idcolor`) REFERENCES `colores` (`idcolor`),
  ADD CONSTRAINT `materiaprima_fk2` FOREIGN KEY (`idunidad`) REFERENCES `unidades` (`idunidad`);

--
-- Filtros para la tabla `movimientokardexmateriaprima`
--
ALTER TABLE `movimientokardexmateriaprima`
  ADD CONSTRAINT `movimientokardexmateriaprima_fk` FOREIGN KEY (`idmateriaprima`) REFERENCES `materiaprima` (`idmateriaprima`);

--
-- Filtros para la tabla `movimientoproductointermedio`
--
ALTER TABLE `movimientoproductointermedio`
  ADD CONSTRAINT `movimientoproductointermedio_fk` FOREIGN KEY (`idproductointermedio`) REFERENCES `productointermedios` (`idproductointermedio`);

--
-- Filtros para la tabla `procesos`
--
ALTER TABLE `procesos`
  ADD CONSTRAINT `procesos_fk` FOREIGN KEY (`idempleado`) REFERENCES `empleados` (`idempleado`);

--
-- Filtros para la tabla `produccionprocesos`
--
ALTER TABLE `produccionprocesos`
  ADD CONSTRAINT `produccionprocesos_fk` FOREIGN KEY (`numeroorden`) REFERENCES `ordenproduccion` (`numeroorden`),
  ADD CONSTRAINT `produccionprocesos_fk1` FOREIGN KEY (`idproceso`) REFERENCES `procesos` (`idproceso`),
  ADD CONSTRAINT `produccionprocesos_fk2` FOREIGN KEY (`idempleado`) REFERENCES `empleados` (`idempleado`);

--
-- Filtros para la tabla `productointermedios`
--
ALTER TABLE `productointermedios`
  ADD CONSTRAINT `productointermedios_fk` FOREIGN KEY (`numeroorden`) REFERENCES `ordenproduccion` (`numeroorden`),
  ADD CONSTRAINT `productointermedios_fk1` FOREIGN KEY (`idproduccionproceso`) REFERENCES `produccionprocesos` (`idproduccionproceso`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_fk` FOREIGN KEY (`idcategoriaproducto`) REFERENCES `categoriaproducto` (`idcategoriaproducto`),
  ADD CONSTRAINT `productos_fk1` FOREIGN KEY (`idgrupo`) REFERENCES `grupos` (`idgrupo`);

--
-- Filtros para la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD CONSTRAINT `fk_relationship_6` FOREIGN KEY (`idciudad`) REFERENCES `ciudades` (`idciudad`);

--
-- Filtros para la tabla `responsables`
--
ALTER TABLE `responsables`
  ADD CONSTRAINT `fk_relationship_10` FOREIGN KEY (`idciudad`) REFERENCES `ciudades` (`idciudad`),
  ADD CONSTRAINT `fk_relationship_9` FOREIGN KEY (`idempresa`) REFERENCES `empresas` (`idempresa`);

--
-- Filtros para la tabla `rolfuncion`
--
ALTER TABLE `rolfuncion`
  ADD CONSTRAINT `fk_relationship_2` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`),
  ADD CONSTRAINT `fk_relationship_3` FOREIGN KEY (`idfuncion`) REFERENCES `funcion` (`idfuncion`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_relationship_1` FOREIGN KEY (`idrol`) REFERENCES `rol` (`idrol`);
