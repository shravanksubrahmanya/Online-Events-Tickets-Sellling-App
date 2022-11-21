-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 26, 2019 at 11:06 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eventzo`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `transactionid` varchar(20) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `ticket_type` varchar(50) NOT NULL,
  `noofticket` int(11) NOT NULL,
  `totalamount` int(10) NOT NULL,
  `fname` varchar(60) NOT NULL,
  `lname` varchar(20) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `email` varchar(50) NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bid`, `event_id`, `user_id`, `transactionid`, `event_name`, `ticket_type`, `noofticket`, `totalamount`, `fname`, `lname`, `phone`, `email`, `status`) VALUES
(12, 31, 19, 'tr65236723', 'Sunlight', 'golden', 1, 8900, 'Shravan', 'KS', '1932834899', 'shravanksubrahmanya@gmail.com', 1),
(13, 29, 18, 'tr837366425', 'Cosmos', 'Golden', 2, 13000, 'Pramod', 'N', '9148327468', 'pramodgowda26598@gmail.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE IF NOT EXISTS `comments` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `comments` varchar(100) NOT NULL,
  `fname` varchar(30) NOT NULL,
  `lname` varchar(30) NOT NULL,
  `image` varchar(500) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`cid`, `user_id`, `event_id`, `comments`, `fname`, `lname`, `image`) VALUES
(13, 19, 28, 'This is the best program In India', 'Shravan', 'K S', '1553139483523.JPG');

-- --------------------------------------------------------

--
-- Table structure for table `enews`
--

DROP TABLE IF EXISTS `enews`;
CREATE TABLE IF NOT EXISTS `enews` (
  `messageid` int(20) NOT NULL AUTO_INCREMENT,
  `event_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `messages` text NOT NULL,
  PRIMARY KEY (`messageid`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enews`
--

INSERT INTO `enews` (`messageid`, `event_id`, `user_id`, `messages`) VALUES
(19, 28, 19, 'dancing program is almost completed'),
(18, 28, 19, 'dancing couples are coming'),
(17, 28, 19, 'Judges are coming'),
(16, 28, 19, 'Now the program has started');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `event_name` varchar(40) NOT NULL,
  `description` text NOT NULL,
  `synopsis` text NOT NULL,
  `type` varchar(20) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `location` text NOT NULL,
  `image` varchar(500) NOT NULL,
  `status` varchar(10) NOT NULL,
  `video` text NOT NULL,
  `hosttype` varchar(20) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`event_id`, `user_id`, `event_name`, `description`, `synopsis`, `type`, `date`, `time`, `location`, `image`, `status`, `video`, `hosttype`) VALUES
(32, 18, 'Equinox', 'Best program In the India', 'party', 'Programs', '2019-03-22', '03:14:00', 'Mangalore', '1553146387756.JPG', '1', 'www.youtube.com', 'cost'),
(31, 18, 'Sunlight', 'Biggest party in the Karnataka.. Tickets are almost sold out', 'Biggest party in Karnataka', 'Others', '2019-04-26', '03:55:00', 'Karnataka', '1553146107572.JPG', '1', 'www.youtube.com', 'cost'),
(30, 18, 'Ganesha Festival', 'This is the largest festival celebrated all over the India', 'This is the biggest festival celebrated all over India', 'Festival', '2019-03-23', '03:30:00', 'India', '1553145610052.JPG', '1', 'www.youtube.com', 'free'),
(29, 19, 'Cosmos', 'It will be the biggest party held in the world.. Youths from all over the world are waiting to participate in it', 'The Biggest Party held in the world', 'Programs', '2019-04-04', '10:55:00', 'Mangalore', '1553141978287.JPG', '1', 'www.youtube.com', 'cost'),
(28, 19, 'Dance India Dance', 'It is the top trending event in India...There are thousands of youths who wish to participate in this event.', '#1 Dancing show in India', 'Cultural Programs', '2019-04-27', '09:30:00', 'Mumbai', '1553141555696.JPG', '1', 'www.youtube.com', 'free');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE IF NOT EXISTS `feedback` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `feedback` text NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`fid`, `uid`, `feedback`, `status`) VALUES
(8, 19, 'This application is awesome..but this applications user interface needs to be improved', 1);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
CREATE TABLE IF NOT EXISTS `groups` (
  `uid` int(20) NOT NULL,
  `connectedid` int(20) NOT NULL,
  `grpname` text NOT NULL,
  `fname` varchar(30) NOT NULL,
  `lname` varchar(30) NOT NULL,
  `image` varchar(500) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `dob` varchar(20) NOT NULL,
  `moreinfo` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`uid`, `connectedid`, `grpname`, `fname`, `lname`, `image`, `gender`, `dob`, `moreinfo`) VALUES
(19, 20, 'friends', 'Gautami', 'Doddamani', '1553139754448.JPG', 'Female', '03/21/99', 'Student at alva\'s degree college'),
(19, 18, 'friends', 'Pramod', 'N', '1553139349098.JPG', 'Male', '02/09/99', 'Student at alva\'s degree college');

-- --------------------------------------------------------

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
CREATE TABLE IF NOT EXISTS `notice` (
  `notificationid` int(20) NOT NULL AUTO_INCREMENT,
  `uid` int(20) NOT NULL,
  `grpname` text NOT NULL,
  `event_id` int(11) NOT NULL,
  `event_name` varchar(50) NOT NULL,
  `event_image` varchar(500) NOT NULL,
  `synopsis` varchar(500) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`notificationid`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notice`
--

INSERT INTO `notice` (`notificationid`, `uid`, `grpname`, `event_id`, `event_name`, `event_image`, `synopsis`, `type`) VALUES
(7, 19, 'friends', 28, 'Dance India Dance', 'Matrix{[1.0, 0.0, 0.0][0.0, 1.0, 0.0][0.0, 0.0, 1.0]}', '#1 Dancing show in India', 'public');

-- --------------------------------------------------------

--
-- Table structure for table `reaction`
--

DROP TABLE IF EXISTS `reaction`;
CREATE TABLE IF NOT EXISTS `reaction` (
  `rid` int(20) NOT NULL AUTO_INCREMENT,
  `event_id` int(20) NOT NULL,
  `u_id` int(20) NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reaction`
--

INSERT INTO `reaction` (`rid`, `event_id`, `u_id`) VALUES
(78, 32, 19),
(77, 28, 19);

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
CREATE TABLE IF NOT EXISTS `tickets` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL,
  `price` int(10) NOT NULL,
  `notickets` int(10) NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`tid`, `uid`, `event_id`, `type`, `price`, `notickets`) VALUES
(14, 18, 32, 'platinum', 6900, 299),
(13, 18, 31, 'golden', 8900, 199),
(12, 19, 29, 'Platinum', 7800, 198),
(11, 19, 29, 'Golden', 6500, 198);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(30) NOT NULL,
  `lname` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` bigint(20) NOT NULL,
  `dob` varchar(20) NOT NULL,
  `password` varchar(15) NOT NULL,
  `user_image` varchar(500) NOT NULL,
  `moreinfo` text,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`uid`, `fname`, `lname`, `email`, `gender`, `phone`, `dob`, `password`, `user_image`, `moreinfo`, `status`) VALUES
(20, 'Gauthami', 'Doddamani', 'gautamiparashuram1999@gmail.com', 'Female', 9876543211, '03/21/99', '123456789', '1553139754448.JPG', 'Student at alva\'s degree college', 1),
(19, 'Shravan', 'K S', 'shravanksubrahmanya@gmail.com', 'Male', 9481440309, '09/03/98', '123456789', '1553139483523.JPG', 'Student at alva\'s degree college', 1),
(18, 'Pramod', 'N', 'pramodgowda26598@gmail.com', 'Male', 9148327468, '02/09/99', '123456789', '1553139349098.JPG', 'Student at alva\'s degree college', 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
