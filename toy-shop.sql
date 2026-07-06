CREATE DATABASE  IF NOT EXISTS `toyshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `toyshop`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: toyshop
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receiver_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `zipcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address_detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'N',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `default_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,'김민훈','010-5322-1256','50855','경남 김해시 진영읍 가산산단길 13','탄옴스','Y','2026-06-06 19:58:25','N'),(3,5,'김민훈','010-2532-1255','06035','서울 강남구 가로수길 9','신사에스테반','N','2026-06-08 15:49:58','N'),(4,6,'김정훈','010-5235-1258','03900','서울 마포구 가양대로 2','현대힐스테이트 104동 1401호','Y','2026-07-02 10:47:52','Y'),(5,5,'김민훈','010-2563-1223','13549','경기 성남시 분당구 대왕판교로 12','아이파크아파트 106동 1021호','Y','2026-07-02 11:35:35','Y'),(6,3,'김상훈','010-2535-1235','06941','서울 동작구 알마타길 10','대방빌','Y','2026-07-02 11:35:35','Y');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `qty` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (26,5,18,1,'2026-07-02 10:43:35'),(33,4,13,1,'2026-07-02 17:53:04');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inquiry`
--

DROP TABLE IF EXISTS `inquiry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inquiry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `writer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mail_receive` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `answer_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `member_id` bigint DEFAULT NULL,
  `answer_content` text COLLATE utf8mb4_unicode_ci,
  `answered_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inquiry`
--

LOCK TABLES `inquiry` WRITE;
/*!40000 ALTER TABLE `inquiry` DISABLE KEYS */;
INSERT INTO `inquiry` VALUES (1,'배송문의','김민','zzangmait@naver.com','Y','배송건 ','수고하십니다. 금일 주문하면 배송은 언제 이루어질까요?','답변완료','2026-06-05 10:00:32',NULL,'네 금일 배송예정입니다.','2026-07-02 14:30:33'),(2,'배송문의','김민훈','zzangmait@naver.com','Y','배송 문의','수고하십니다. 금일 구매하면 오늘 배송될까요?','답변완료','2026-06-07 16:53:58',5,'네 금일 배송예정입니다. \r\n문의감사합니다','2026-07-02 14:41:25');
/*!40000 ALTER TABLE `inquiry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'USER',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `zipcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birth` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `use_yn` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'Y',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE',
  `mileage` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (3,'staff2','$2a$10$BsxoKYVDmku8spQdc9HBsOZcoWbdlKZUW1ec.5HnRg6Kixucu0LWy','김상훈','010-2535-1235','zzangmait@naver.com','서울 동작구 알마타길 10','USER','2026-06-07 14:08:41','06941','대방빌','20020123','M','Y','ACTIVE',4517),(4,'admin','$2a$10$/Qds6S6qW9jmRP7ZxdOCX.4WlxFhflwAlBmdwlPlbA0P1nwOPX2Dq','토이샵','010-5232-1563','zzangmait@naver.com','경남 김해시 가야테마길 110-9','ADMIN','2026-06-07 14:18:17','50811','삼방아파트','19670222','M','Y','ACTIVE',2000),(5,'staff1','$2a$10$jYj3uPIgVZnZq5n.bZRF/espy/AKPoBdXPjld72Wm02ruNQQhUJ5W','김민훈','010-2563-1223','zzangmait@naver.com','경기 성남시 분당구 대왕판교로 364','USER','2026-06-07 14:23:07','13543','아이파크아파트','20030720','M','Y','ACTIVE',2828),(6,'staff3','$2a$10$KUR5lEWB5I0sOtNRILUT2.3MEhsbnq0YNYjKI3DNoBq4O/ZNcQukC','김정훈','010-5235-1258','zzangmait@naver.com','서울 마포구 가양대로 2','USER','2026-07-02 10:10:36','03900','현대힐스테이트 104동 1401호','19950603','남성','Y','ACTIVE',3016);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mileage_history`
--

DROP TABLE IF EXISTS `mileage_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mileage_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `amount` int NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mileage_history`
--

LOCK TABLES `mileage_history` WRITE;
/*!40000 ALTER TABLE `mileage_history` DISABLE KEYS */;
INSERT INTO `mileage_history` VALUES (1,1,2000,'JOIN','회원가입 마일리지 지급','2026-06-06 23:55:11'),(2,3,2000,'JOIN','회원가입 축하 마일리지','2026-06-07 14:08:41'),(3,4,2000,'JOIN','회원가입 축하 마일리지','2026-06-07 14:18:17'),(4,5,2000,'JOIN','회원가입 축하 마일리지','2026-06-07 14:23:07'),(5,5,199,'ORDER','구매금액 1% 적립','2026-06-08 02:45:51'),(6,3,430,'ORDER','구매금액 1% 적립','2026-06-08 02:45:53'),(7,5,629,'ORDER','구매금액 1% 적립','2026-06-08 02:45:55'),(8,5,-199,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-06-08 02:46:49'),(9,3,-430,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-06-08 02:47:28'),(10,5,199,'ORDER','구매금액 1% 적립','2026-06-08 02:47:30'),(11,3,199,'ORDER','구매금액 1% 적립','2026-06-08 02:48:48'),(12,3,430,'ORDER','구매금액 1% 적립','2026-06-08 02:48:49'),(13,3,199,'ORDER','구매금액 1% 적립','2026-06-08 02:53:20'),(14,6,2000,'JOIN','회원가입 축하 마일리지','2026-07-02 10:10:36'),(15,3,-199,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-07-02 17:18:13'),(16,3,-199,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-07-02 17:18:27'),(17,3,199,'ORDER','구매금액 1% 적립','2026-07-02 17:19:03'),(18,3,199,'ORDER','구매금액 1% 적립','2026-07-02 17:19:04'),(19,3,1689,'ORDER','구매금액 1% 적립','2026-07-02 17:23:18'),(20,3,-199,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-07-02 17:23:19'),(21,3,-199,'ORDER_CANCEL','배송완료 되돌리기로 구매 적립 마일리지 회수','2026-07-02 17:23:20'),(22,3,199,'ORDER','구매금액 1% 적립','2026-07-02 17:23:42'),(23,3,199,'ORDER','구매금액 1% 적립','2026-07-02 17:23:42'),(24,6,-1800,'USE','주문 시 마일리지 사용','2026-07-02 17:53:49'),(25,6,-100,'USE','주문 시 마일리지 사용','2026-07-02 18:23:25'),(26,6,100,'USE_CANCEL','주문취소로 사용 마일리지 복구','2026-07-02 21:12:13'),(27,6,220,'ORDER','구매금액 1% 적립','2026-07-02 21:13:41'),(28,6,99,'ORDER','구매금액 1% 적립','2026-07-02 21:13:58'),(29,6,2497,'ORDER','구매금액 1% 적립','2026-07-02 21:14:47');
/*!40000 ALTER TABLE `mileage_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` int NOT NULL,
  `qty` int NOT NULL,
  `total_price` int NOT NULL,
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (6,5,2,'원피스 쵸파로보',19900,1,19900,'/images/onepiece-chopa1.png','2026-06-07 17:43:31'),(7,5,1,'핫휠 메이커킷',43000,1,43000,'/images/hotwheels-5kits.png','2026-06-07 17:43:31'),(8,6,1,'핫휠 메이커킷',43000,1,43000,'/images/hotwheels-5kits.png','2026-06-07 18:31:37'),(9,7,2,'원피스 쵸파로보',19900,1,19900,'/images/onepiece-chopa1.png','2026-06-08 01:19:36'),(10,8,2,'원피스 쵸파로보',19900,1,19900,'/images/onepiece-chopa1.png','2026-06-08 01:24:43'),(11,9,2,'원피스 쵸파로보',19900,1,19900,'/images/onepiece-chopa1.png','2026-06-08 01:30:22'),(12,10,11,'SD 마징가',99000,1,99000,'/images/upload/1c42a07b-14a4-4b4f-afc0-c65d03f1e1f3.png','2026-07-02 17:22:43'),(13,10,20,'ZD TOYS ZD 토이즈 마블 아이언맨 마크85 MK85 2.0 에디션 액션 피규어',69900,1,69900,'/images/upload/9dd2f0d9-3ef1-4bf5-aff3-b9fc9e128ac6.png','2026-07-02 17:22:43'),(14,11,10,'마징가 지그버젼',220000,1,220000,'/images/upload/a0ad0139-3e87-4f2e-9434-94b7d89bf66b.png','2026-07-02 17:25:57'),(15,11,17,'휴대용 레트로게임',9900,3,29700,'/images/upload/7a19ffd0-3905-4130-8af9-0b2a9ddd9225.png','2026-07-02 17:25:57'),(16,12,19,'나노블럭 소방차',22000,1,22000,'/images/upload/4b85a079-faf7-4212-9601-9d3f6b33f815.png','2026-07-02 17:41:37'),(17,13,13,'손오공 굿즈',9900,1,9900,'/images/upload/db77c3ae-1b3f-4884-b273-1ce61fe5fa07.png','2026-07-02 17:53:49'),(18,14,14,'포켓몬 트레이너 -로드',15000,1,15000,'/images/upload/4096f15e-4ce0-401b-817a-534df32cd10f.png','2026-07-02 17:56:16'),(19,15,12,'메가 리자몽 피규어',39000,1,39000,'/images/upload/3579c1d1-9c76-481b-816a-9caf1beea59d.png','2026-07-02 18:23:25');
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `total_price` int NOT NULL,
  `delivery_fee` int NOT NULL,
  `final_price` int NOT NULL,
  `order_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'ORDERED',
  `payment_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'BANK',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT 'WAITING',
  `toss_payment_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `order_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `order_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `receiver_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delivery_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `depositor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bank_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tracking_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delivery_company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (5,5,'ORD20260607174331',62900,3000,65900,'DONE','BANK','PAID',NULL,'2026-06-07 17:43:31','김민훈','010-7210-1527','김민훈','010-7210-1527','경기 성남시 분당구 대왕판교로 364 아이파크아파트','','김민훈','국민은행 563445-12-125545 예금주: 토이샵','122-2223-21231','우체국택배'),(6,3,'ORD20260607183137',43000,3000,46000,'DONE','BANK','PAID',NULL,'2026-06-07 18:31:37','김상훈','010-2535-1235','김상훈','010-2535-1235','서울 동작구 알마타길 10 대방빌','','김상훈','카카오뱅크 2213-12-2599566 예금주: 토이샵','122-2223-21233','우체국택배'),(7,5,'ORD20260608011936',19900,3000,22900,'DONE','BANK','PAID',NULL,'2026-06-08 01:19:36','김민훈','010-2563-1223','김민훈','010-2563-1223','경기 성남시 분당구 대왕판교로 364 아이파크아파트','','김민훈','국민은행 563445-12-125545 예금주: 토이샵','122-2223-21235','우체국택배'),(8,3,'ORD20260608012443',19900,3000,22900,'DONE','CARD','PAID','tviva2026060801241019SI8','2026-06-08 01:24:43','김상훈','010-5533-1256',NULL,NULL,NULL,NULL,NULL,NULL,'122-2223-21278','우체국택배'),(9,3,'ORD20260608013022',19900,3000,22900,'DONE','CARD','PAID','tviva2026060801293954Ga0','2026-06-08 01:30:22','김상훈','010-2535-1235','김상훈','010-2535-1235','서울 동작구 알마타길 10 대방빌','',NULL,NULL,'122-2223-21298','우체국택배'),(10,3,'ORD20260702172243',168900,3000,171900,'DONE','BANK','PAID',NULL,'2026-07-02 17:22:43','김상훈','010-2535-1235','김상훈','010-2535-1235','서울 동작구 알마타길 10','문 앞에 놓아주세요','김상훈','국민은행 563445-12-125545 예금주: 토이샵','122-2223-21255','우체국택배'),(11,6,'ORD20260702172557',249700,3000,252700,'DONE','CARD','PAID','tviva20260702172519AvuG6','2026-07-02 17:25:57','김정훈','010-5235-1258','김정훈','010-5235-1258','서울 마포구 가양대로 2','',NULL,NULL,'122-2223-21288','우체국택배'),(12,6,'ORD20260702174137',22000,3000,25000,'DONE','BANK','PAID',NULL,'2026-07-02 17:41:37','김정훈','010-5235-1258','김정훈','010-5235-1258','서울 마포구 가양대로 2','','김정훈','국민은행 563445-12-125545 예금주: 토이샵','122-2223-21259','우체국택배'),(13,6,'ORD20260702175349',9900,3000,11100,'DONE','BANK','PAID',NULL,'2026-07-02 17:53:49','김정훈','010-5235-1258','김정훈','010-5235-1258','서울 마포구 가양대로 2','','김정훈','국민은행 563445-12-125545 예금주: 토이샵','122-2223-21293','우체국택배'),(14,6,'ORD20260702175616',15000,3000,18000,'CANCEL','CARD','CANCEL','tviva20260702175552GtZx1','2026-07-02 17:56:16','김정훈','010-5235-1258','김정훈','010-5235-1258','서울 마포구 가양대로 2','',NULL,NULL,NULL,NULL),(15,6,'ORD20260702182325',39000,3000,41900,'CANCEL','CARD','CANCEL','tviva20260702182301Wruc0','2026-07-02 18:23:25','김정훈','010-5235-1258','김정훈','010-5235-1258','서울 마포구 가양대로 2','',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` int NOT NULL,
  `stock_qty` int DEFAULT '0',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'핫휠 메이커킷','레이싱카',43000,10,'/images/hotwheels-5kits.png','로봇과 우주 장난감 감성이 담긴 빈티지 스타일 장난감입니다.','2026-06-03 15:21:15'),(2,'원피스 쵸파로보','프라모델',19900,6,'/images/onepiece-chopa1.png','원피스 쵸파 로보 슈퍼 1호','2026-06-03 23:48:40'),(3,'RC 피카츄','레이싱카',28000,12,'/images/upload/b2efd4a2-22d4-4b4b-96b7-2c8e4860ba0c.png','3세~7세 아이들 사이에서 인기가 좋고!\r\n주문만 하면 자꾸만 택배 언제오냐고 난리라네요^^\r\n배송까지 빠르다고 칭찬 받는 그런 제품입니다!\r\n','2026-06-08 16:55:08'),(4,'리락쿠마 인형(중)','인형',15000,5,'/images/upload/354cd3dd-8545-4db8-a287-01ef6fe03022.png','리락쿠마 인형입니다. 크기는 30CM 정도로 중형 크기입니다.','2026-06-08 17:20:24'),(5,'마이멜로디 인형(중)','인형',15000,3,'/images/upload/2868c01d-67dc-4cc0-87a0-642db789ca8d.png','산리오 인기 캐릭터입니다.\r\n크기는 30CM정도 됩니다.','2026-06-08 17:26:16'),(6,'사야자쿠 프라모델','프라모델',40000,5,'/images/upload/855580d8-3600-405a-a089-1218b07d2973.png','1/144모델입니다.','2026-06-08 17:56:56'),(7,'어메임 고스트','프라모델',38000,20,'/images/upload/8add73a1-93b1-4ca0-bcbf-a807e1edfbfb.png','신상품 프라모델입니다.','2026-06-08 19:00:14'),(8,'나루토','프라모델',18000,3,'/images/upload/21719de9-f38c-4c6d-b24c-8ac513c81a08.png','신상 나루토입니다.','2026-06-08 19:07:32'),(9,'철인 28호','초합금',199000,2,'/images/upload/804a0427-9c77-41ca-a412-2a2ff3e4f763.png','철인 28호 옛버젼 신제품입니다.','2026-06-08 19:10:12'),(10,'마징가 지그버젼','초합금',220000,2,'/images/no-image.png','마징가 지그버젼입니다.','2026-06-08 19:11:43'),(11,'SD 마징가','초합금',99000,21,'/images/upload/1c42a07b-14a4-4b4f-afc0-c65d03f1e1f3.png','초합금혼 SD크기입니다.','2026-06-08 19:13:20'),(12,'메가 리자몽 피규어','피규어',39000,5,'/images/upload/3579c1d1-9c76-481b-816a-9caf1beea59d.png','메가 리자몽 X버젼입니다.\r\n\r\n크기는 15CM 내외입니다.','2026-06-08 19:16:01'),(13,'손오공 굿즈','피규어',9900,4,'/images/upload/db77c3ae-1b3f-4884-b273-1ce61fe5fa07.png','5개 밖에 안남은 초특가 상품!!','2026-06-08 19:18:25'),(14,'포켓몬 트레이너 -로드','피규어',15000,6,'/images/upload/4096f15e-4ce0-401b-817a-534df32cd10f.png','포켓몬 트레이너 로드 입니다.','2026-06-08 19:19:34'),(15,'타노스 (대)','피규어',99000,2,'/images/upload/d75f4fe6-495e-477c-9efc-477a6fe2c2c4.png','타노스 무려 40CM입니다','2026-06-08 19:20:42'),(16,'무선패드 컨트롤러','게임기',59000,26,'/images/upload/d0e61f01-6114-447e-9497-3af0587a36cf.png','무선패드입니다.','2026-06-08 19:22:35'),(17,'휴대용 레트로게임','게임기',9900,62,'/images/upload/7a19ffd0-3905-4130-8af9-0b2a9ddd9225.png','휴대용 게임기입니다. 레트로 버젼 특가 9900원!','2026-06-08 19:23:34'),(18,'네오지오 아케이드 휴대가능','게임기',55000,30,'/images/upload/3265a5ab-4cb4-4779-9376-d319a759103a.png','휴대용 미니 인기있는 네오지오 게임기입니다.','2026-06-08 19:24:38'),(19,'나노블럭 소방차','기타',22000,5,'/images/upload/4b85a079-faf7-4212-9601-9d3f6b33f815.png','3가지 버젼을 만들수 있습니다.','2026-06-08 19:25:36'),(20,'ZD TOYS ZD 토이즈 마블 아이언맨 마크85 MK85 2.0 에디션 액션 피규어','초합금',69900,2,'/images/upload/9dd2f0d9-3ef1-4bf5-aff3-b9fc9e128ac6.png','원산지 중국\r\n제조원 ZD TOYS\r\n제품 크기 약 18CM\r\n사용연령 15세이상','2026-07-02 16:46:53');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_qna`
--

DROP TABLE IF EXISTS `product_qna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_qna` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `writer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `answer_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `member_id` bigint DEFAULT NULL,
  `answer_content` text COLLATE utf8mb4_unicode_ci,
  `answered_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_qna`
--

LOCK TABLES `product_qna` WRITE;
/*!40000 ALTER TABLE `product_qna` DISABLE KEYS */;
INSERT INTO `product_qna` VALUES (1,NULL,'김민','마징가 문의','마징가는 입고 계획이 없으실지요?','답변완료','2026-06-05 12:14:34',NULL,'추후 입고하도록 하겠습니다.','2026-07-02 14:09:11'),(2,NULL,'김민훈','마징가 입고','수고하십니다. 마징가는 입고 계획이 없나요?','답변완료','2026-06-07 16:54:26',5,'추후 입고하도록 하겠습니다.','2026-07-02 14:09:08'),(3,NULL,'김상훈','마리오 관련제품','혹시 마리오 관련제품도 입고 될까요?','답변완료','2026-06-07 16:59:08',3,'추후 입고하도록 하겠습니다.\r\n문의 감사합니다.','2026-07-02 14:46:19');
/*!40000 ALTER TABLE `product_qna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recent_product`
--

DROP TABLE IF EXISTS `recent_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recent_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `viewed_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_member` (`member_id`),
  KEY `idx_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recent_product`
--

LOCK TABLES `recent_product` WRITE;
/*!40000 ALTER TABLE `recent_product` DISABLE KEYS */;
INSERT INTO `recent_product` VALUES (2,1,1,'2026-06-07 00:02:28'),(34,3,2,'2026-06-08 01:31:15'),(36,3,1,'2026-06-08 02:35:55'),(37,4,1,'2026-06-08 02:56:42'),(46,4,3,'2026-06-08 17:17:51'),(47,4,4,'2026-06-08 17:20:27'),(48,4,5,'2026-06-08 17:26:20'),(51,4,8,'2026-06-08 19:07:52'),(52,4,7,'2026-06-08 19:07:55'),(53,4,6,'2026-06-08 19:07:57'),(57,4,17,'2026-06-08 19:29:34'),(59,5,15,'2026-06-08 19:29:56'),(61,5,5,'2026-06-08 19:30:02'),(62,5,8,'2026-06-08 19:30:05'),(63,5,13,'2026-06-08 19:30:08'),(64,5,16,'2026-06-08 19:30:10'),(67,5,11,'2026-06-08 19:30:19'),(68,5,18,'2026-07-02 10:43:33'),(72,5,10,'2026-07-02 16:18:15'),(75,5,17,'2026-07-02 16:21:24'),(81,5,1,'2026-07-02 16:21:56'),(83,5,2,'2026-07-02 16:23:59'),(85,4,2,'2026-07-02 16:43:12'),(87,4,19,'2026-07-02 16:47:37'),(88,3,20,'2026-07-02 17:19:26'),(89,3,11,'2026-07-02 17:19:31'),(90,4,20,'2026-07-02 17:24:02'),(91,4,11,'2026-07-02 17:24:04'),(95,6,1,'2026-07-02 17:26:22'),(96,6,11,'2026-07-02 17:26:27'),(97,6,10,'2026-07-02 17:26:29'),(98,6,18,'2026-07-02 17:34:21'),(99,6,19,'2026-07-02 17:41:24'),(100,4,13,'2026-07-02 17:53:03'),(102,6,13,'2026-07-02 17:53:34'),(103,6,17,'2026-07-02 17:54:05'),(107,6,12,'2026-07-02 21:13:01'),(109,6,14,'2026-07-02 21:13:29'),(110,4,18,'2026-07-04 02:11:24'),(111,5,19,'2026-07-04 03:10:57');
/*!40000 ALTER TABLE `recent_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating` int NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `member_id` bigint DEFAULT NULL,
  `writer` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,2,NULL,4,'조립감 훌륭','반다이라서 그런지 만족합니다.','2026-06-05 20:18:28',1,NULL,NULL),(2,2,NULL,4,'원피스 쵸파로보','반다이제라서 조립할만하고 장식용도 좋습니다.','2026-07-02 13:40:10',3,'김상훈',NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wishlist_member_product` (`member_id`,`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (3,1,1,'2026-06-07 00:02:34'),(4,5,1,'2026-06-07 14:23:18'),(5,3,1,'2026-06-08 02:35:56'),(6,5,17,'2026-06-08 19:29:17'),(7,4,17,'2026-06-08 19:29:35'),(8,6,18,'2026-07-02 10:44:43');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-04  4:01:40
