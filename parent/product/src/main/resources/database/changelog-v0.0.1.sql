
USE `product_master` ;

-- -----------------------------------------------------
-- Table `product_master`.`association_value_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`association_value_entry` (
                                                                          `id` BIGINT NOT NULL,
                                                                          `association_key` VARCHAR(255) NOT NULL,
    `association_value` VARCHAR(255) NULL DEFAULT NULL,
    `saga_id` VARCHAR(255) NOT NULL,
    `saga_type` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `IDXk45eqnxkgd8hpdn6xixn8sgft` (`saga_type` ASC, `association_key` ASC, `association_value` ASC) VISIBLE,
    INDEX `IDXgv5k1v2mh6frxuy5c0hgbau94` (`saga_id` ASC, `saga_type` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`association_value_entry_seq`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`association_value_entry_seq` (
                                                                              `next_val` BIGINT NULL DEFAULT NULL)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`brands` (
                                                         `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255) NULL DEFAULT NULL,
    `id_brand` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id_brand`),
    UNIQUE INDEX `UKoce3937d2f4mpfqrycbr0l93m` (`name` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`categories` (
                                                             `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(255) NULL DEFAULT NULL,
    `id_category` VARCHAR(255) NOT NULL,
    `image` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id_category`),
    UNIQUE INDEX `UKt8o6pivur7nn124jehx7cygw5` (`name` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`dead_letter_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`dead_letter_entry` (
                                                                    `enqueued_at` DATETIME(6) NOT NULL,
    `last_touched` DATETIME(6) NULL DEFAULT NULL,
    `processing_started` DATETIME(6) NULL DEFAULT NULL,
    `sequence_index` BIGINT NOT NULL,
    `sequence_number` BIGINT NULL DEFAULT NULL,
    `cause_message` VARCHAR(1023) NULL DEFAULT NULL,
    `aggregate_identifier` VARCHAR(255) NULL DEFAULT NULL,
    `cause_type` VARCHAR(255) NULL DEFAULT NULL,
    `dead_letter_id` VARCHAR(255) NOT NULL,
    `event_identifier` VARCHAR(255) NOT NULL,
    `message_type` VARCHAR(255) NOT NULL,
    `payload_revision` VARCHAR(255) NULL DEFAULT NULL,
    `payload_type` VARCHAR(255) NOT NULL,
    `processing_group` VARCHAR(255) NOT NULL,
    `sequence_identifier` VARCHAR(255) NOT NULL,
    `time_stamp` VARCHAR(255) NOT NULL,
    `token_type` VARCHAR(255) NULL DEFAULT NULL,
    `type` VARCHAR(255) NULL DEFAULT NULL,
    `diagnostics` BLOB NULL DEFAULT NULL,
    `meta_data` BLOB NULL DEFAULT NULL,
    `payload` BLOB NOT NULL,
    `token` BLOB NULL DEFAULT NULL,
    PRIMARY KEY (`dead_letter_id`),
    UNIQUE INDEX `UKhlr8io86j74qy298xf720n16v` (`processing_group` ASC, `sequence_identifier` ASC, `sequence_index` ASC) VISIBLE,
    INDEX `IDXe67wcx5fiq9hl4y4qkhlcj9cg` (`processing_group` ASC) VISIBLE,
    INDEX `IDXrwucpgs6sn93ldgoeh2q9k6bn` (`processing_group` ASC, `sequence_identifier` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`product` (
                                                          `optlock` INT NULL DEFAULT NULL,
                                                          `price` FLOAT NOT NULL,
                                                          `stock` INT NULL DEFAULT NULL,
                                                          `created_at` DATETIME(6) NULL DEFAULT NULL,
    `updated_at` DATETIME(6) NULL DEFAULT NULL,
    `gender_name` VARCHAR(20) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `created_by` VARCHAR(255) NULL DEFAULT NULL,
    `id_brand` VARCHAR(255) NOT NULL,
    `id_product` VARCHAR(255) NOT NULL,
    `updated_by` VARCHAR(255) NULL DEFAULT NULL,
    `status` ENUM('ADDED_TO_ORDER', 'BOUGHT', 'CREATED', 'NOT_BOUGHT', 'RESERVED') NOT NULL,
    PRIMARY KEY (`id_product`),
    UNIQUE INDEX `UKjmivyxk9rmgysrmsqw15lqr5b` (`name` ASC) VISIBLE,
    UNIQUE INDEX `UKcxi8b15htc9jyey53so43533d` (`gender_name` ASC) VISIBLE,
    INDEX `FKtldr2v77cm9ju3453je9xnhj7` (`id_brand` ASC) VISIBLE,
    CONSTRAINT `FKtldr2v77cm9ju3453je9xnhj7`
    FOREIGN KEY (`id_brand`)
    REFERENCES `product_master`.`brands` (`id_brand`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`product_category` (
                                                                   `id_category` VARCHAR(255) NOT NULL,
    `id_product` VARCHAR(255) NOT NULL,
    INDEX `FK110n0nxo6urwxft1k8ssw3j4s` (`id_category` ASC) VISIBLE,
    INDEX `FKt4sn9fs5ju7d8mcoporlyhfun` (`id_product` ASC) VISIBLE,
    CONSTRAINT `FK110n0nxo6urwxft1k8ssw3j4s`
    FOREIGN KEY (`id_category`)
    REFERENCES `product_master`.`categories` (`id_category`),
    CONSTRAINT `FKt4sn9fs5ju7d8mcoporlyhfun`
    FOREIGN KEY (`id_product`)
    REFERENCES `product_master`.`product` (`id_product`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`product_lookup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`product_lookup` (
                                                                 `id_product` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id_product`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`saga_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`saga_entry` (
                                                             `revision` VARCHAR(255) NULL DEFAULT NULL,
    `saga_id` VARCHAR(255) NOT NULL,
    `saga_type` VARCHAR(255) NULL DEFAULT NULL,
    `serialized_saga` BLOB NULL DEFAULT NULL,
    PRIMARY KEY (`saga_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `product_master`.`token_entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_master`.`token_entry` (
                                                              `segment` INT NOT NULL,
                                                              `owner` VARCHAR(255) NULL DEFAULT NULL,
    `processor_name` VARCHAR(255) NOT NULL,
    `timestamp` VARCHAR(255) NOT NULL,
    `token_type` VARCHAR(255) NULL DEFAULT NULL,
    `token` BLOB NULL DEFAULT NULL,
    PRIMARY KEY (`segment`, `processor_name`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
