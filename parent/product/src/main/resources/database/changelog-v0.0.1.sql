CREATE TABLE `product` (
  `optlock` int DEFAULT NULL,
  `price` float NOT NULL,
  `stock` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `id_product` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `status` enum('ADDED_TO_ORDER','BOUGHT','CREATED','NOT_BOUGHT','RESERVED') DEFAULT NULL,
  PRIMARY KEY (`id_product`),
  UNIQUE KEY `UKjmivyxk9rmgysrmsqw15lqr5b` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `product_look_up` (
  `id_product` varchar(255) NOT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci