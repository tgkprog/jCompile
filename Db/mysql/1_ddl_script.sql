--
-- Host: localhost
-- Generation Time: Dec 04, 2014 at 09:05 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

SET time_zone = "+00:00";


--
-- Database: `JCompileDb`
--
-- Drop might not work in phpmyadmin - run from console or remove disable drop from config
-- Drop data base if exists mohStarter;

CREATE DATABASE IF NOT EXISTS `JCompileDb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `JCompileDb`;


-- --------------------------------------------------------

--
-- Table structure for table `CodeQuestions`
--

DROP TABLE IF EXISTS `CodeQuestions`;

CREATE TABLE IF NOT EXISTS `CodeQuestions` (
`Id` int(11) NOT NULL,
  `question_Summary` varchar(2400) NOT NULL,
  `question` Text DEFAULT NULL,
  `score` int(11) DEFAULT NULL,  
  `tester_class_name` varchar(255) DEFAULT NULL,
   `test_properties` Text DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

