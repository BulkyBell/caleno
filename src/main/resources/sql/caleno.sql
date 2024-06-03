-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-06-2024 a las 17:47:13
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `caleno`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employeedata`
--

CREATE TABLE `employeedata` (
  `id` int(11) NOT NULL,
  `employee_id` int(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL,
  `phoneNum` varchar(100) NOT NULL,
  `position` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `employeedata`
--

INSERT INTO `employeedata` (`id`, `employee_id`, `firstName`, `lastName`, `gender`, `phoneNum`, `position`, `image`, `date`) VALUES
(1, 1, 'test1', 'test1', 'Female', '123456789', 'Position 2', 'C:\\Users\\Sergio\\Downloads\\7iWkzuO.png', '2024-05-28'),
(2, 2, 'sagx', 'Pepe', 'Male', '3464567', 'Position 2', 'C:\\Users\\Sergio\\Desktop\\pfp giatnx\\20240209_131158.jpg', '2024-05-22'),
(3, 3, 'paco', 'Porras', 'Male', '1234', 'Position 3', 'C:\\\\Users\\\\Sergio\\\\Desktop\\\\pfp giatnx\\\\mark evans.jpg', '2024-05-22'),
(4, 4, 'juanito', 'Juan', 'Female', '98765', 'Position 1', 'C:\\Users\\Sergio\\Desktop\\pfp giatnx\\pfp.jpg', '2024-05-24'),
(17, 5, 'piedra', 'verde', 'Female', '12356486', 'Position 3', 'C:\\Users\\Sergio\\Downloads\\7iWkzuO.png', '2024-05-28'),
(20, 6, 'piedra', 'azul', 'Female', '12356486', 'Position 3', '', '2024-05-31'),
(21, 7, 'nombre1', 'nombre2', 'Female', '1234567', 'Position 2', 'C:\\\\Users\\\\Sergio\\\\Downloads\\\\Imagen de WhatsApp 2024-05-30 a las 23.56.38_9300963d.jpg', '2024-05-31'),
(22, 8, 'nuevo', 'empleado', 'Male', '0867', 'Position 3', 'C:\\\\Users\\\\Sergio\\\\Pictures\\\\Screenshots\\\\Captura de pantalla 2024-06-03 035601.png', '2024-06-03');

--
-- Disparadores `employeedata`
--
DELIMITER $$
CREATE TRIGGER `after_employeedata_delete` AFTER DELETE ON `employeedata` FOR EACH ROW BEGIN
    DELETE FROM employee_info
    WHERE employee_id = OLD.employee_id;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_employeedata_insert` AFTER INSERT ON `employeedata` FOR EACH ROW BEGIN
    INSERT INTO employee_info (employee_id, firstName, lastName, position, salary, date)
    VALUES (NEW.employee_id, NEW.firstName, NEW.lastName, NEW.position, 0, NEW.date);
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_employeedata_update` AFTER UPDATE ON `employeedata` FOR EACH ROW BEGIN
    UPDATE employee_info
    SET firstName = NEW.firstName,
        lastName = NEW.lastName,
        position = NEW.position,
        date = NEW.date
    WHERE employee_id = NEW.employee_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employee_info`
--

CREATE TABLE `employee_info` (
  `id` int(11) NOT NULL,
  `employee_id` int(100) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `lastName` varchar(100) NOT NULL,
  `position` varchar(100) NOT NULL,
  `salary` double NOT NULL,
  `active` tinyint(1) DEFAULT 0,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `employee_info`
--

INSERT INTO `employee_info` (`id`, `employee_id`, `firstName`, `lastName`, `position`, `salary`, `active`, `date`) VALUES
(1, 1, 'test1', 'test1', 'Position 2', 300, 1, '2024-05-28'),
(2, 2, 'sagx', 'Pepe', 'Position 2', 300, 0, '2024-05-22'),
(3, 3, 'paco', 'Porras', 'Position 3', 0, 1, '2024-05-22'),
(4, 4, 'juanito', 'Juan', 'Position 1', 0, 0, '2024-05-24'),
(14, 5, 'piedra', 'verde', 'Position 3', 800, 1, '2024-05-28'),
(17, 6, 'piedra', 'azul', 'Position 3', 0, 0, '2024-05-31'),
(18, 7, 'nombre1', 'nombre2', 'Position 2', 0, 0, '2024-05-31'),
(19, 8, 'nuevo', 'empleado', 'Position 3', 0, 0, '2024-06-03');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `employeedata`
--
ALTER TABLE `employeedata`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `employee_info`
--
ALTER TABLE `employee_info`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `employeedata`
--
ALTER TABLE `employeedata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `employee_info`
--
ALTER TABLE `employee_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
