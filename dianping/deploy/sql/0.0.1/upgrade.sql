CREATE TABLE `dianpingdb`.`t_user`
(
    `id`         int(0)       NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `created_at` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `telphone`   varchar(40)  NOT NULL,
    `password`   varchar(255) NOT NULL,
    `nick_name`  varchar(40)  NOT NULL,
    `gender`     int(0)       NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `telphone_index` (`telphone`) USING BTREE
);