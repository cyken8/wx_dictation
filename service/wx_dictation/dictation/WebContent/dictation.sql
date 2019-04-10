/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.5.49 : Database - dictation
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dictation` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dictation`;

/*Table structure for table `t_book` */

DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `bookid` varchar(32) NOT NULL COMMENT '课本编号',
  `bookname` varchar(50) NOT NULL COMMENT '课本名称',
  `versionid` varchar(50) DEFAULT NULL COMMENT '课本版本',
  `langid` varchar(10) NOT NULL COMMENT '语言类型编号',
  `booktypeid` varchar(10) NOT NULL COMMENT '课本来源类型编号,保留',
  `cid` varchar(10) DEFAULT NULL COMMENT '课本所属年级分类编号',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面路径',
  `status` varchar(3) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book` */

insert  into `t_book`(`bookid`,`bookname`,`versionid`,`langid`,`booktypeid`,`cid`,`cover`,`status`) values ('B10001','小学语文一年级上册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10002','小学语文一年级下册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10003','小学语文二年级上册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10004','小学语文二年级下册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10005','小学语文三年级上册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10006','小学语文三年级下册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10007','小学语文四年级上册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10008','小学语文四年级下册','BV100','BL100','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10009','小学语文一年级上册','BV100','BL101','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10010','小学语文一年级下册','BV100','BL101','BT100','1','https://www.tarsadata.com/books/yuwenbubian11.jpg','1');

/*Table structure for table `t_book_category` */

DROP TABLE IF EXISTS `t_book_category`;

CREATE TABLE `t_book_category` (
  `cid` varchar(32) NOT NULL COMMENT '课本分类',
  `cname` varchar(20) DEFAULT NULL COMMENT '课本分类描述',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book_category` */

insert  into `t_book_category`(`cid`,`cname`) values ('1','一年级'),('10','提高班'),('2','二年级'),('3','三年级'),('4','四年级'),('5','五年级'),('6','六年级'),('7','七年级'),('8','八年级'),('9','九年级');

/*Table structure for table `t_collection` */

DROP TABLE IF EXISTS `t_collection`;

CREATE TABLE `t_collection` (
  `userid` varchar(32) NOT NULL COMMENT '用户id',
  `item1` varchar(32) DEFAULT NULL COMMENT '保留项1',
  `item2` varchar(32) DEFAULT NULL COMMENT '保留项2',
  `item3` varchar(32) DEFAULT NULL COMMENT '保留项3',
  `item4` varchar(32) DEFAULT NULL COMMENT '保留项4',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_collection` */

insert  into `t_collection`(`userid`,`item1`,`item2`,`item3`,`item4`) values ('U0000001','',NULL,NULL,NULL),('U0000002','',NULL,NULL,NULL),('U0000003',NULL,NULL,NULL,NULL);

/*Table structure for table `t_collection_item` */

DROP TABLE IF EXISTS `t_collection_item`;

CREATE TABLE `t_collection_item` (
  `itemid` varchar(32) NOT NULL COMMENT '收藏项id',
  `bookid` varchar(32) NOT NULL COMMENT '包含哪本课本',
  `userid` varchar(32) NOT NULL COMMENT '属于哪个收藏本',
  `state` varchar(3) NOT NULL COMMENT '状态',
  PRIMARY KEY (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_collection_item` */

insert  into `t_collection_item`(`itemid`,`bookid`,`userid`,`state`) values ('I1000','B10001','U0000001','1'),('I1002','B10002','U0000001','1'),('I1003','B10003','U0000001','1');

/*Table structure for table `t_type_user` */

DROP TABLE IF EXISTS `t_type_user`;

CREATE TABLE `t_type_user` (
  `usertypeid` varchar(5) NOT NULL COMMENT '用户类型编号',
  `usertypename` varchar(20) DEFAULT NULL COMMENT '用户类型描述',
  PRIMARY KEY (`usertypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_type_user` */

insert  into `t_type_user`(`usertypeid`,`usertypename`) values ('UT001','超级管理员'),('UT002','普通管理员'),('UT003','学生'),('UT004','教师');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userid` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `state` varchar(5) NOT NULL COMMENT '状态',
  `usertypeid` varchar(5) DEFAULT NULL COMMENT '用户类型ID',
  `orgid` varchar(5) DEFAULT NULL COMMENT '用户机构ID',
  `gradeid` varchar(5) DEFAULT NULL COMMENT '年级编号',
  `classid` varchar(5) DEFAULT NULL COMMENT '年级编号',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `provinceid` varchar(5) DEFAULT NULL COMMENT '省份',
  `cityid` varchar(6) DEFAULT NULL COMMENT '城市',
  `open_id` varchar(1000) NOT NULL COMMENT '微信用户唯一标识',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userid`,`username`,`password`,`avatar`,`state`,`usertypeid`,`orgid`,`gradeid`,`classid`,`birthday`,`sex`,`email`,`provinceid`,`cityid`,`open_id`) values ('o3Cwg0cLtijEmNfwkBjhTojpKbZo','Evan',NULL,'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLns60AgRWAiaboibBkWXibjOJH3QcRomKHMszahFBWtCewl6bMMuZUiaBzBiasFZTZBTDW9jsxO1qoXibQ/132','1','UT003','OR001','GR001','CL001','2019-09-01','男','','PR001','CI001',''),('U0000001','CYK','123','https://www.tarsadata.com/books/yuwenbubian11.jpg','1','UT003','OR001','GR001','CL001','2019-04-05','男','2278091010@qq.com','PR001','CT0001',''),('U0000002','ZZL','123','https://www.tarsadata.com/books/yuwenbubian11.jpg','1','UT003','OR001','GR001','CL001','2019-04-05','男','2278091010@qq.com','PR001','CT0001',''),('U0000003','TYT','123','https://www.tarsadata.com/books/yuwenbubian11.jpg','1','UT003','OR001','GR001','CL001','2019-04-05','男','2278091010@qq.com','PR001','CT0001',''),('U000000717570670','t1','202cb962ac59075b964b07152d234b70',NULL,'1','UT004',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'');

/*Table structure for table `t_user_session` */

DROP TABLE IF EXISTS `t_user_session`;

CREATE TABLE `t_user_session` (
  `sid` int(32) NOT NULL COMMENT '记录主键',
  `session_key` varchar(1000) NOT NULL COMMENT '微信用户的一个标识',
  `open_id` varchar(1000) NOT NULL COMMENT '小程序账号的唯一标识',
  `expire_date` datetime NOT NULL COMMENT 'session_key的有效时间',
  `client_key` varchar(1000) NOT NULL COMMENT '客户端的登录态数据',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_session` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
