-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.19 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para farmaciadb
DROP DATABASE IF EXISTS `farmaciadb`;
CREATE DATABASE IF NOT EXISTS `farmaciadb` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `farmaciadb`;

-- Volcando estructura para tabla farmaciadb.farmacias
DROP TABLE IF EXISTS `farmacias`;
CREATE TABLE IF NOT EXISTS `farmacias` (
  `idFarmacias` int NOT NULL AUTO_INCREMENT,
  `nombreFarmacia` varchar(45) NOT NULL,
  `direccionFarmacia` varchar(45) NOT NULL,
  `telefonoFarmacia` int NOT NULL,
  PRIMARY KEY (`idFarmacias`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla farmaciadb.farmacias: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `farmacias` DISABLE KEYS */;
REPLACE INTO `farmacias` (`idFarmacias`, `nombreFarmacia`, `direccionFarmacia`, `telefonoFarmacia`) VALUES
	(3, 'similares', 'allende 234', 57739738),
	(5, ' Ahorro', 'Purisima #312', 5676787),
	(7, ' DelAhorro', 'allende 233', 123123),
	(8, ' isseg', 'morelos 123', 12312423);
/*!40000 ALTER TABLE `farmacias` ENABLE KEYS */;

-- Volcando estructura para tabla farmaciadb.productos
DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `caducidad` datetime NOT NULL,
  `stock` int NOT NULL,
  `idFarmacia` int NOT NULL,
  `activo` tinyint NOT NULL,
  PRIMARY KEY (`idProducto`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla farmaciadb.productos: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
REPLACE INTO `productos` (`idProducto`, `nombre`, `caducidad`, `stock`, `idFarmacia`, `activo`) VALUES
	(5, ' Amoxicilina', '2025-08-02 01:00:00', 2, 5, 1),
	(7, ' Ibuprogeno', '2025-09-02 00:00:00', 300, 7, 1),
	(10, ' Paracetamol', '2025-02-04 00:00:00', 3, 3, 1),
	(11, ' Paracetamol', '2025-05-02 00:00:00', 12, 3, 1),
	(12, ' Omeprazol', '2024-04-18 00:00:00', 1230, 1, 1),
	(13, ' Omeprazol', '2025-05-18 00:00:00', 1230, 8, 1),
	(14, ' Ibuprofeno', '2028-12-07 00:00:00', 1348, 5, 1),
	(15, ' Peroxican', '2045-05-10 00:00:00', 100, 8, 1);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
