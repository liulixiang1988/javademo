-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
`end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT='创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)

)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
INSERT INTO seckill ( NAME, NUMBER, start_time, end_time)
VALUES
('1000元秒杀iphone6', 100, '2016-6-12 00:00:00', '2016-6-13 00:00:00'),
('500元秒杀ipad2', 250, '2016-6-12 00:00:00', '2016-6-13 00:00:00'),
('300元秒杀小米6', 200, '2016-6-12 00:00:00', '2016-6-13 00:00:00'),
('100元秒杀红米note', 500, '2016-6-12 00:00:00', '2016-6-13 00:00:00');