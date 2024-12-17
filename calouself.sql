-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2024 at 11:33 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `calouself`
--

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `Item_id` varchar(255) NOT NULL,
  `Item_name` varchar(255) NOT NULL,
  `Item_size` varchar(255) NOT NULL,
  `Item_price` varchar(255) NOT NULL,
  `Item_category` varchar(255) NOT NULL,
  `Item_status` varchar(255) DEFAULT 'Pending',
  `Item_wishlist` varchar(255) DEFAULT '0',
  `Item_offer_status` varchar(255) DEFAULT 'No Offer'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`Item_id`, `Item_name`, `Item_size`, `Item_price`, `Item_category`, `Item_status`, `Item_wishlist`, `Item_offer_status`) VALUES
('IT001', 'Air Jordine', '43', '20000', 'Shoes', 'Pending', '0', 'Declined'),
('IT002', 'abvc', '100', '3000', 'Sandals', 'Approved', '0', 'Accepted'),
('IT003', 'Pink Hoodie', 'M', '30000', 'Hoodie', 'Pending', '0', 'No Offer'),
('IT004', 'jud', 'a', '100', 'man', 'Approved', '0', 'Accepted'),
('IT005', 'Dikoi', '15', '20000', 'Not Human', 'Approved', '0', 'No Offer'),
('IT006', 'Fer', '123', '10', 'Alien', 'Approved', '0', 'No Offer');

-- --------------------------------------------------------

--
-- Table structure for table `offers`
--

CREATE TABLE `offers` (
  `Offer_id` varchar(255) NOT NULL,
  `User_id` varchar(255) NOT NULL,
  `Item_id` varchar(255) NOT NULL,
  `Offer_price` varchar(255) NOT NULL,
  `Offer_status` enum('Pending','Accepted','Declined') DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `offers`
--

INSERT INTO `offers` (`Offer_id`, `User_id`, `Item_id`, `Offer_price`, `Offer_status`) VALUES
('OF001', 'US001', 'IT002', '69.0', 'Accepted');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `User_id` varchar(255) NOT NULL,
  `Item_id` varchar(255) NOT NULL,
  `transaction_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`User_id`, `Item_id`, `transaction_id`) VALUES
('US001', 'IT002', 'TR001');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `User_id` varchar(255) NOT NULL,
  `Username` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Phone_Number` varchar(255) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`User_id`, `Username`, `Password`, `Phone_Number`, `Address`, `Role`) VALUES
('US001', 'Ferry', 'FerryF1!', '+62898843385', 'Sesame Street', 'Buyer'),
('US002', 'Ucup', 'UcupGaming1!', '+62817263542', 'Jalan Kuy', 'Seller'),
('US003', 'judson', '@abcdefg', '+62123456789', 'aa', 'Buyer'),
('US004', 'diki', '@abcdefgh', '+62123456789', 'aa', 'Seller'),
('US005', 'Ferrys', 'FerryF1!', '+62123456789', 'Jalan Jalan', 'Buyer');

-- --------------------------------------------------------

--
-- Table structure for table `wishlists`
--

CREATE TABLE `wishlists` (
  `Wishlist_id` varchar(255) NOT NULL,
  `Item_id` varchar(255) NOT NULL,
  `User_id` varchar(255) DEFAULT 'US001'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wishlists`
--

INSERT INTO `wishlists` (`Wishlist_id`, `Item_id`, `User_id`) VALUES
('WL001', 'IT002', 'US001'),
('WL002', 'IT004', 'US001'),
('WL003', 'IT004', 'US001');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`Item_id`);

--
-- Indexes for table `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`Offer_id`),
  ADD KEY `User_id` (`User_id`),
  ADD KEY `Item_id` (`Item_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `FK_Item_Transactions` (`Item_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`User_id`);

--
-- Indexes for table `wishlists`
--
ALTER TABLE `wishlists`
  ADD PRIMARY KEY (`Wishlist_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `offers`
--
ALTER TABLE `offers`
  ADD CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`User_id`) REFERENCES `users` (`User_id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FK_Item_Transactions` FOREIGN KEY (`Item_id`) REFERENCES `items` (`Item_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
