-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 30, 2018 at 07:50 PM
-- Server version: 5.7.23-0ubuntu0.16.04.1
-- PHP Version: 7.0.32-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `codechef`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE `Admin` (
  `username` varchar(100) NOT NULL,
  `accesstoken` varchar(100) NOT NULL,
  `refreshtoken` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`username`, `accesstoken`, `refreshtoken`) VALUES
('admin', '536a0c45dc56329beef7e05c5de2c0b47e4424d1', '49c40b999375d5e8eec82abe9ea9a4b7d5126abd');

-- --------------------------------------------------------

--
-- Table structure for table `contest`
--

CREATE TABLE `contest` (
  `sdate` int(100) NOT NULL,
  `edate` int(100) NOT NULL,
  `eventcode` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `contest`
--

INSERT INTO `contest` (`sdate`, `edate`, `eventcode`) VALUES
(1537102800, 1537102800, 'CCWC2018'),
(1537709400, 1537709400, 'COOK98'),
(1541246400, 1541246400, 'IEMCO6'),
(1538791200, 1538791200, 'MITC2018');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `username` varchar(100) NOT NULL,
  `eventcode` varchar(100) NOT NULL,
  `status` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`username`, `eventcode`, `status`) VALUES
('epilux', 'CCWC2018', NULL),
('epilux', 'IEMCO6', NULL),
('epsileer', 'MITC2018', NULL),
('sahushivam', 'CCWC2018', NULL),
('sahushivam', 'COOK98', NULL),
('sahushivam', 'MITC2018', NULL),
('subodhrai', 'LTIME64', NULL),
('subodhrai', 'MITC2018', NULL),
('subodhrai898', 'ELE32018', NULL),
('subodhrai898', 'IEMCO6', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(100) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `device` varchar(1000) NOT NULL,
  `email` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `name`, `device`, `email`) VALUES
('epilux', NULL, 'dhvuD7JEdh4:APA91bEjujPH5banxehQSD4cOZ0Tvl4Q77N6krXybwR_9nskEvcU29aFQNunucJoPeQcmLloJWfn61z5UjI4OxToiz4iMp1HhfYL3OpY017_OOopW-h5wWDIcdg6GFPfx_bS-rnN0gMN', NULL),
('epsile', NULL, 'kdsjfakdsjfa', NULL),
('epsileer', NULL, 'cO9GKxwmqiA:APA91bHHx785E5AJKYuOM8xb6qPhVrlln2fRZ7Uc6ckQoxGSSmmdzNg7fAyi2PlkTBxX_OjihdzpOeup7Bsm3RLCL7neaZyQuuliQbKe81xZE68WsZDX-LzAEF_vCbuCaYMfA4puuH-g', NULL),
('sahushivam', NULL, 'dsrCzYd9IH0:APA91bGWo1abdYEIR3_JitRjK0O_B2PqdNFJG-DXL919A-HNira-KSfVE7zvswf5PWfoWZt90SMLckJ12jB77uP-TcNC1Isx01b5K8YOs1Pj9djuARzllPpH4_KGw5IncWdhD6FcAKDY', NULL),
('subodh898', NULL, 'fx419Q15qMM:APA91bHEo94l8DEFX7vVgS-mvFPRSsQgNVUw-q_FXRw9jz_SDxgxmPacQIVR5u6zKJKW1Ofj9Pd9ui-cSOjkIpKLcaG7AR4cYKjeffGIaB00t_-0AvortQBwVs8PkYTVfGhw4ri9XnRN', NULL),
('subodhrai', NULL, 'f3h4HY5wUMU:APA91bFwfy9vBzc-EV8fhsYCbBB1cSNMeEkr6pVOP9fqnRh22ADkRCAiHixp8MSSyuzpsvMhbkRLowVOVS9AjYAoIJtJsjF96xC2Ml7DRp8_MY7_wHcqJSiQ4ZH-c3g1BWNnNXeaL3yn', NULL),
('subodhrai898', NULL, 'ceDeYx7pa9w:APA91bH-nYk-4NICLgJAv3iEycoo4WERzVqUkfkkFN9OSgSHp2olliAnv_Z0_9edXX9gSi0WacYIMP2Yzy7bLsQWmJUBMm4_fzkk4wsCcIneC6j0l4t2nQDX1j20EZGVzweQb2qKuWAo', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `contest`
--
ALTER TABLE `contest`
  ADD PRIMARY KEY (`eventcode`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`username`,`eventcode`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
