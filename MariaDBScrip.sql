-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.11.7-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for salecomputercomponent
CREATE DATABASE IF NOT EXISTS `salecomputercomponent` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci */;
USE `salecomputercomponent`;

-- Dumping structure for table salecomputercomponent.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `cartid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`cartid`),
  KEY `FK1f3la5ofi3papq9kbt0yqkab7` (`userid`),
  CONSTRAINT `FK1f3la5ofi3papq9kbt0yqkab7` FOREIGN KEY (`userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.cart: ~0 rows (approximately)

-- Dumping structure for table salecomputercomponent.cart_item
CREATE TABLE IF NOT EXISTS `cart_item` (
  `cartid` int(11) DEFAULT NULL,
  `cartitemid` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `quantity` float NOT NULL,
  PRIMARY KEY (`cartitemid`),
  UNIQUE KEY `UK6fnabryl5o7jiwvt64ed4n5jx` (`productid`),
  KEY `FK4teld0nibbe4l2rucr2yybw2e` (`cartid`),
  CONSTRAINT `FK4teld0nibbe4l2rucr2yybw2e` FOREIGN KEY (`cartid`) REFERENCES `cart` (`cartid`),
  CONSTRAINT `FK9pw5b35944xvbr3h8avlakscl` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.cart_item: ~0 rows (approximately)

-- Dumping structure for table salecomputercomponent.category
CREATE TABLE IF NOT EXISTS `category` (
  `categoryid` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.category: ~4 rows (approximately)
INSERT INTO `category` (`categoryid`, `description`, `name`) VALUES
	(1, 'Là một loại máy tính cá nhân được thiết kế để di động và có thể sử dụng ở nhiều nơi khác nhau', 'Laptop '),
	(2, 'Dùng để di chuyển chuột', 'Chuột'),
	(3, 'Bàn phím dùng để bấm máy', 'Keyboard'),
	(4, 'Hiển thị màn hình', 'Màn hình');

-- Dumping structure for table salecomputercomponent.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_price` float NOT NULL,
  `userid` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpnm1eeupqm4tykds7k3okqegv` (`userid`),
  CONSTRAINT `FKpnm1eeupqm4tykds7k3okqegv` FOREIGN KEY (`userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.orders: ~4 rows (approximately)
INSERT INTO `orders` (`id`, `total_price`, `userid`, `created_at`, `status`) VALUES
	(1, 8600, 1, '2024-07-21 23:54:46.878000', 'Pending'),
	(2, 1900, 1, '2024-07-21 23:54:58.668000', 'Pending'),
	(3, 2000, 2, '2024-07-21 23:56:26.918000', 'Pending'),
	(4, 2, 1, '2024-07-22 02:29:12.206000', 'Pending');

-- Dumping structure for table salecomputercomponent.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `orderdetailid` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `productid` int(11) DEFAULT NULL,
  `quantity` float NOT NULL,
  PRIMARY KEY (`orderdetailid`),
  KEY `FK715ucxrvknue1cfnb458qh5n5` (`orderid`),
  KEY `FKlsvx45m4io6a6fdwpa14pvn92` (`productid`),
  CONSTRAINT `FK715ucxrvknue1cfnb458qh5n5` FOREIGN KEY (`orderid`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKlsvx45m4io6a6fdwpa14pvn92` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.order_detail: ~8 rows (approximately)
INSERT INTO `order_detail` (`orderdetailid`, `orderid`, `price`, `productid`, `quantity`) VALUES
	(1, 1, 1400, 1, 5),
	(2, 1, 500, 2, 2),
	(3, 1, 200, 3, 3),
	(4, 2, 1400, 1, 1),
	(5, 2, 500, 2, 1),
	(6, 3, 1400, 1, 1),
	(7, 3, 200, 11, 3),
	(8, 4, 1, 14, 2);

-- Dumping structure for table salecomputercomponent.product
CREATE TABLE IF NOT EXISTS `product` (
  `categoryid` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `productid` int(11) NOT NULL AUTO_INCREMENT,
  `quantity` float NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`productid`),
  KEY `FK4ort9abhumpx4t2mlngljr1vi` (`categoryid`),
  CONSTRAINT `FK4ort9abhumpx4t2mlngljr1vi` FOREIGN KEY (`categoryid`) REFERENCES `category` (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.product: ~14 rows (approximately)
INSERT INTO `product` (`categoryid`, `price`, `productid`, `quantity`, `description`, `image`, `name`) VALUES
	(1, 1400, 1, 20, 'Là chiếc laptop sở hữu một thiết kế vô cùng thanh lịch.', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579173/taxozkk63nwbuvhmanco.jpg', 'Laptop Acer Aspire 3 '),
	(1, 500, 2, 15, 'một chiếc laptop HP cơ bản để phục vụ cho việc học hoặc làm việc văn phòng', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579383/jdxjgf9ezryd7qrg4kyh.jpg', 'Laptop HP 15 fd0303TU i3 '),
	(2, 200, 3, 10, 'HIỆU SUẤT CAO CHO CHƠI GAME', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579505/ul5fhscihvruqyzijim0.jpg', 'Chuột Logitech G102 gaming'),
	(2, 100, 4, 50, 'Chuột được thiết kế mới siêu đẹp với màu hồng đặc biệt đảm bảo thị trường có 1 0 2 ', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579662/auoxcffqtfo0gov48uiq.jpg', 'Chuột Gaming T28 Có Dây Chống ồn Dành Cho Game Thủ'),
	(2, 155, 5, 20, 'là loại chuột giá rẻ được ưa chuộng vì độ bền và cảm giác thoải mái khi cầm, mang lại hiệu quả tốt nhất cho game thủ.', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579728/fbmnkjeh2aguhijtmugy.jpg', 'Chuột máy tính có dây logitech'),
	(2, 130, 6, 90, 'Chuột Gaming Siêu Bền, Thiết Kế Siêu Nhẹ - Sự Lựa Chọn Lí Tưởng cho Mọi Người', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579825/tcc0tsspgurdbdih1hi0.jpg', 'Chuột Cơ Gaming Chuyên Chơi Game Máy Tính PC Laptop'),
	(2, 120, 7, 20, 'Chuột máy tính', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721579907/udixwrgc5ocqmvzfwtkh.jpg', 'Chuột máy tính không dây cao cấp '),
	(2, 130, 8, 20, 'Chọn từ các màu sắc sống động, cài đặt lấy cảm hứng từ trò chơi và phương tiện, hay lập trình màu sắc của riêng bạn từ khoảng 16,8 triệu màu.', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580117/psjolvorl4mk0l6ydaxd.jpg', 'Chuột Máy Tính Logitech G102 Lightsync game có dây'),
	(4, 560, 9, 20, 'ẢO ĐẢM HÀNG CHẤT LƯỢNG, CẢ NHÀ YÊN TÂM TUYỆT ĐỐI !', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580274/zz3dw0krozgr0ahlogf3.jpg', 'Màn Hình hãng GLOWY 17|19|22|24|27'),
	(4, 600, 10, 10, 'Thanh lý lô Màn hình vi tính 19 inch. 20 inch. 22 inch. 24inch sáng đẹp không lỗi', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580354/g2s1kznedvvhqp92rhnj.jpg', 'Màn hình vi tính 19 inch'),
	(3, 200, 11, 30, 'bàn phím chuột máy tính có 2 loại màu là bàn phím màu trắng và bàn phím màu đen', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580412/awg0umbcf7a1m6g1akdx.jpg', 'Bàn phím nút tròn giả cơ'),
	(3, 120, 12, 40, 'Bộ phím chuột LED chỉ dùng cho máy tính bàn (PC) và Laptop ', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580456/o0vgp3zdr5kly184ynl1.jpg', 'Bộ Bàn phím kèm Chuột Gaming G21B'),
	(3, 110, 13, 20, 'Bàn Phím bluetooth không dây mini  Tương thích gần như hầu hết hệ điều hành hiện nay', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721580562/xi2jjhu1h0mumhu14f4e.jpg', 'bàn phím không dây bluetooth'),
	(1, 1, 14, 1, '123456', 'http://res.cloudinary.com/drjoyphxe/image/upload/v1721590095/oyrqnbvg1jlusybtdku3.jpg', '76_Đỗ Chí Tường');

-- Dumping structure for table salecomputercomponent.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- Dumping data for table salecomputercomponent.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `created_at`, `address`, `email`, `name`, `password`, `role`) VALUES
	(1, NULL, 'Phú yên', 'conguyetnaduongvulan@gmail.com', 'Đỗ Chí Tường', '123456789', 'ADMIN'),
	(2, NULL, 'Tỉnh Phú Yên-Huyện Sông Hinh-Thi trấn hai riêng', 'tranphu.chituong.9d4@gmail.com', 'Phạm Thoại', 'Tuong2003', 'USER');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
