-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 01, 2018 at 12:35 AM
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
('epsileer', NULL, 'etb7-FnrHls:APA91bE1MmyneFyJl-CJU1PWzZ0PYhVZlfeu8WYgI7jREc_dBite4i_Opex7lEcCwn_1WBT5PVMdHmwvOIWnBuQW1lb3uMQn_HF-syeqjQJB8Jp7sE3lZ6dJQOzoAFTZPnR_-C77Wm58', NULL),
('sahushivam', NULL, 'dsrCzYd9IH0:APA91bGWo1abdYEIR3_JitRjK0O_B2PqdNFJG-DXL919A-HNira-KSfVE7zvswf5PWfoWZt90SMLckJ12jB77uP-TcNC1Isx01b5K8YOs1Pj9djuARzllPpH4_KGw5IncWdhD6FcAKDY', NULL),
('subodh898', NULL, 'fx419Q15qMM:APA91bHEo94l8DEFX7vVgS-mvFPRSsQgNVUw-q_FXRw9jz_SDxgxmPacQIVR5u6zKJKW1Ofj9Pd9ui-cSOjkIpKLcaG7AR4cYKjeffGIaB00t_-0AvortQBwVs8PkYTVfGhw4ri9XnRN', NULL),
('subodhrai', NULL, 'd-yGKIbJFZI:APA91bFH8ki7z3ZyiwWjmMAVhcnea-drjBgbn9ROY_hX1eUVShZ-7Toy1BYy-pkzGRV9lt6fMDwC-TwKzRFB2ebal_Si_EawkdY701xObs4OjenOIo4D7XfR-X_7wiJ5djEePhm38zxO', NULL),
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
