CREATE DATABASE IF NOT EXISTS `fsd`;
USE fsd;

CREATE TABLE IF NOT EXISTS `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `username` varchar(30) NOT NULL,
                         `password` varchar(80) NOT NULL,
                         `age` int(11) DEFAULT NULL,
                         `registration_date` date DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci