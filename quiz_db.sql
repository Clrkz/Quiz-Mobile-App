-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2019 at 10:33 AM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quiz_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `already_take`
--

CREATE TABLE `already_take` (
  `student_id` varchar(567) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `already_take`
--

INSERT INTO `already_take` (`student_id`, `question_id`) VALUES
('1600402', 0),
('1600402', 57),
('1600402', 56),
('1600402', 0),
('1600402', 66),
('1600402', 64),
('1600402', 65),
('1600402', 63);

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `correct` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`id`, `question_id`, `answer`, `correct`) VALUES
(206, 56, 'q1 a', '0'),
(207, 56, 'q1 b', '0'),
(208, 56, 'q1 c', '1'),
(209, 56, 'q1 d', '0'),
(210, 57, 'q2 a', '0'),
(211, 57, 'q2 b', '1'),
(212, 57, 'q3 c', '0'),
(213, 57, 'q4 d', '0'),
(218, 63, 'Assss', '0'),
(219, 63, 'Bsss', '1'),
(220, 63, 'Cssss', '0'),
(221, 63, 'Dsss', '0'),
(222, 64, 'Aaaa', '0'),
(223, 64, 'Bbbb', '0'),
(224, 64, 'Ccc', '1'),
(225, 64, 'Ddd', '0'),
(226, 65, 'Apple', '0'),
(227, 65, 'Bravo', '0'),
(228, 65, 'Charlie', '0'),
(229, 65, 'Delta', '1'),
(230, 66, 'Ahehe', '0'),
(231, 66, 'Bhehe', '0'),
(232, 66, 'Chehe', '0'),
(233, 66, 'Dhehe', '1');

-- --------------------------------------------------------

--
-- Table structure for table `professor`
--

CREATE TABLE `professor` (
  `id` int(11) NOT NULL,
  `prf_fname` varchar(50) NOT NULL,
  `prf_mname` varchar(50) NOT NULL,
  `prf_lname` varchar(50) NOT NULL,
  `prf_email` varchar(50) NOT NULL,
  `prf_password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `professor`
--

INSERT INTO `professor` (`id`, `prf_fname`, `prf_mname`, `prf_lname`, `prf_email`, `prf_password`) VALUES
(1, 'Clarence', '', 'Andayay', '1001', '91ec1f9324753048c0096d036a694f86');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `quiz_name_id` int(11) NOT NULL,
  `question` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `quiz_name_id`, `question`) VALUES
(56, 39, 'Question 1'),
(57, 39, 'Question 2'),
(63, 40, 'What is Scince?'),
(64, 40, 'What is Math'),
(65, 40, 'What is Dog?'),
(66, 40, 'Scientific name of astarabus electus mitus?');

-- --------------------------------------------------------

--
-- Table structure for table `quiz_name`
--

CREATE TABLE `quiz_name` (
  `id` int(11) NOT NULL,
  `quiz_name` varchar(100) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quiz_name`
--

INSERT INTO `quiz_name` (`id`, `quiz_name`, `status`) VALUES
(39, 'Quiz 1', 1),
(40, 'Quiz no 2', 1);

-- --------------------------------------------------------

--
-- Table structure for table `quiz_record`
--

CREATE TABLE `quiz_record` (
  `id` int(11) NOT NULL,
  `std_number` varchar(50) NOT NULL,
  `quiz_name_id` int(11) NOT NULL,
  `score` varchar(4333) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quiz_record`
--

INSERT INTO `quiz_record` (`id`, `std_number`, `quiz_name_id`, `score`) VALUES
(11, '1600402', 39, '0'),
(12, '1600402', 40, '0');

-- --------------------------------------------------------

--
-- Table structure for table `quiz_score`
--

CREATE TABLE `quiz_score` (
  `student_id` mediumtext NOT NULL,
  `quiz_name_id` int(11) NOT NULL,
  `correct` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quiz_score`
--

INSERT INTO `quiz_score` (`student_id`, `quiz_name_id`, `correct`) VALUES
('1600402', 39, 1),
('1600402', 39, 0),
('1600402', 39, 1),
('1600402', 40, 0),
('1600402', 40, 0),
('1600402', 40, 0),
('1600402', 40, 1);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `std_number` varchar(50) NOT NULL,
  `std_fname` varchar(50) NOT NULL,
  `std_mname` varchar(50) NOT NULL,
  `std_lname` varchar(50) NOT NULL,
  `std_password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`std_number`, `std_fname`, `std_mname`, `std_lname`, `std_password`) VALUES
('1600402', 'cc', 'mm', 'll', '91ec1f9324753048c0096d036a694f86');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `professor`
--
ALTER TABLE `professor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quiz_name`
--
ALTER TABLE `quiz_name`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quiz_record`
--
ALTER TABLE `quiz_record`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`std_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answer`
--
ALTER TABLE `answer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;

--
-- AUTO_INCREMENT for table `professor`
--
ALTER TABLE `professor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `quiz_name`
--
ALTER TABLE `quiz_name`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `quiz_record`
--
ALTER TABLE `quiz_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
