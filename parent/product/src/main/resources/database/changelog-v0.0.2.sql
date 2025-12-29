USE `product_master` ;

-- -----------------------------------------------------
-- Table `category_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category_lookup` (
                                                 `id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `brand_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brand_lookup` (
                                              `id` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;