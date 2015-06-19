
-- phpMyAdmin SQL Dump
-- version 2.11.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 19, 2015 at 01:55 AM
-- Server version: 5.1.57
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `a8370414_vhiefa`
--

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `id_mhs` int(11) NOT NULL AUTO_INCREMENT,
  `nama` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `nim` varchar(100) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`id_mhs`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=17 ;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` VALUES(1, 'Afifatul Mukaroh', '24010311120022');
INSERT INTO `mahasiswa` VALUES(6, 'Julia Robert', '12345678');
INSERT INTO `mahasiswa` VALUES(5, 'Peter Parker', '262819187171');
INSERT INTO `mahasiswa` VALUES(7, 'Kate Middleton', '24010312173628');
INSERT INTO `mahasiswa` VALUES(8, 'Angelina Jolie', '2393882817171919');
INSERT INTO `mahasiswa` VALUES(9, 'Robert Pattinson', '7186281918181');
INSERT INTO `mahasiswa` VALUES(10, 'Leonardo De Caprio', '268172911827');
INSERT INTO `mahasiswa` VALUES(11, 'Sponge Bob Square Pants', '2402882919171');
INSERT INTO `mahasiswa` VALUES(12, 'Patrick Star', '81826199171');
INSERT INTO `mahasiswa` VALUES(13, 'Uncle Ben', '728262919');
INSERT INTO `mahasiswa` VALUES(14, 'Mr. Crab', '8182691911617');
INSERT INTO `mahasiswa` VALUES(15, 'Lady Gaga', '24010311120087');
