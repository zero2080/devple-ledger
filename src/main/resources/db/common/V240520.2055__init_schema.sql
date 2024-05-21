CREATE TABLE `user` (
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `login_id` VARCHAR(200) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `nickname` VARCHAR(200) NOT NULL,
    `email` VARCHAR(200) NULL,
    `phone` VARCHAR(20) NULL,
    `role` INT(1) DEFAULT 0 NOT NULL COMMENT '사용자 역할 0: 일반 사용자 / 1: 관리자',
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME NULL,
    `updated_by` VARCHAR(20) NULL,
    UNIQUE KEY `uk_user_login_id_role` (`login_id`,`role`)
) COMMENT '사용자';


CREATE TABLE `category` (
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL COMMENT '카테고리명',
    `description` VARCHAR(500) NULL COMMENT '카테고리 설명',
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME NULL,
    `updated_by` VARCHAR(20) NULL
) COMMENT '카테고리';


CREATE TABLE `amount_history` (
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL COMMENT '회원 ID',
    `parent_id` INT UNSIGNED NULL COMMENT '사용 출처',
    `category_code` INT UNSIGNED NOT NULL COMMENT '카테고리 코드',
    `amount` INT UNSIGNED NOT NULL,
    `status` INT(1) NOT NULL COMMENT '0: 수입 / 1: 지출',
    `type` INT (1) NOT NULL COMMENT '0: 지갑 / 1: 계좌 / 2: 카드',
    `event_datetime` DATETIME NOT NULL COMMENT '거래 일시',
    `description` VARCHAR(200) NULL,
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME NULL,
    `updated_by` VARCHAR(20) NULL,
    CONSTRAINT `fk_amount_user_id` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT `fk_amount_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `amount_history`(`id`)
) COMMENT '거래 내역';


CREATE TABLE `wallet` (
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL COMMENT '회원 ID',
    `amount` INT UNSIGNED NOT NULL COMMENT '잔액',
    `last_history_head` INT UNSIGNED NULL COMMENT '마지막 거래 ID',
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME NULL,
    `updated_by` VARCHAR(20) NULL,
    CONSTRAINT `fk_wallet_user_id` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT `fk_wallet_last_history_head` FOREIGN KEY (`last_history_head`) REFERENCES `amount_history`(`id`)
) COMMENT '지갑';


CREATE TABLE `bank_account`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT UNSIGNED NOT NULL COMMENT '회원 ID',
    `bank_name` VARCHAR(200) NOT NULL COMMENT '은행명',
    `amount` INT UNSIGNED NOT NULL COMMENT '잔액',
    `account_number` VARCHAR(200) NOT NULL COMMENT '계좌번호',
    `last_history_head` INT UNSIGNED NULL COMMENT '마지막 거래 ID',
    `created_at` DATETIME NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` DATETIME NULL,
    `updated_by` VARCHAR(20) NULL,
    CONSTRAINT `fk_bank_account_user_id` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT `fk_bank_account_last_history_head` FOREIGN KEY (`last_history_head`) REFERENCES `amount_history`(`id`)
) COMMENT '계좌';
