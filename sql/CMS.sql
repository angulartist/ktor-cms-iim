-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 26, 2019 at 11:28 AM
-- Server version: 5.7.23
-- PHP Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `CMS`
--

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`id`, `title`, `text`) VALUES
(1, 'Windowing', 'Learn about windowing'),
(15, 'Triggers', 'Leaarn about triggers'),
(19, 'Watermark', 'Build a perfect watermark'),
(20, 'Batch vs Streaming processing', 'Which one to choose ?'),
(23, 'Python Cookbook', 'Code snippets using Python 2.7'),
(24, 'Cloud Pub/Sub', 'A messaging system that sends base64 payloads accross the cloud'),
(25, 'Cloud Functions', 'Receive post-aggregated data from your pipeline and store it to cloud Firestore'),
(26, 'Output to BigQuery', 'Output results ot bigquery'),
(27, 'Jupyter Notebook', 'Design your pipeline using Jupyter');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `article_id` int(11) DEFAULT NULL,
  `text` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `article_id`, `text`) VALUES
(15, 4, 'toto'),
(16, 4, 'toto'),
(17, 4, 'toto'),
(18, 4, 'toto'),
(26, 15, 'fefzer'),
(27, 15, 'fefzer'),
(28, 1, 'fdsf'),
(29, 1, 'gdsg'),
(30, 15, 'trtre'),
(31, 15, 'trtr'),
(32, 15, 'rerezt'),
(33, 15, 'trtret'),
(34, 15, 'rezrez'),
(35, 15, 'teztzt'),
(36, 15, 'rezrezr'),
(37, 1, 'jkuk'),
(38, 1, 'uiuyiy'),
(39, 1, 'uytutyu'),
(40, 1, 'uykuyk'),
(58, 1, 'dsqd'),
(59, 1, 'ggtert'),
(60, 1, 'fsdfg'),
(61, 1, 'erzer'),
(62, 1, 'zetz'),
(63, 15, 'fdsf'),
(64, 15, 'rezrz'),
(65, 15, 'rezrt'),
(67, 19, 'hzha');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `isAdmin` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `isAdmin`) VALUES
(1, 'admin@app.io', 'admin', 1),
(2, 'pol@live.fe', 'pol123', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
