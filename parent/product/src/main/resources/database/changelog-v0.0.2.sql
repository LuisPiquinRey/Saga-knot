USE `product_master` ;

-- -----------------------------------------------------
-- Table `brand_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brand_lookup` (
                                              `id_brand` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NULL,
    `description` TEXT NULL,
    PRIMARY KEY (`id_brand`)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `category_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category_lookup` (
                                                 `id_category` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NULL,
    `description` TEXT NULL,
    `image` VARCHAR(500) NULL,
    PRIMARY KEY (`id_category`)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `product_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_lookup` (
                                                `id_product` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NULL,
    `description` TEXT NULL,
    `price` DOUBLE NULL,
    `stock` INT NULL,
    `id_brand` VARCHAR(255) NULL,
    `id_category` VARCHAR(255) NULL,
    PRIMARY KEY (`id_product`)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;
