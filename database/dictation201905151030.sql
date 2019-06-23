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

/*Table structure for table `t_action` */

DROP TABLE IF EXISTS `t_action`;

CREATE TABLE `t_action` (
  `actionid` int(32) NOT NULL AUTO_INCREMENT COMMENT '功能主键',
  `actionname` varchar(32) NOT NULL COMMENT '功能中文描述',
  `actincolumnid` int(32) NOT NULL COMMENT '功能分栏',
  `action` varchar(32) NOT NULL COMMENT '功能英文描述',
  `viewmode` varchar(32) DEFAULT NULL COMMENT '视图模式（保留）',
  PRIMARY KEY (`actionid`),
  UNIQUE KEY `action` (`action`),
  KEY `fk_taction_tactioncolumn_actioncolumnid` (`actincolumnid`),
  CONSTRAINT `fk_taction_tactioncolumn_actioncolumnid` FOREIGN KEY (`actincolumnid`) REFERENCES `t_actioncolumn` (`actioncolumnid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `t_action` */

insert  into `t_action`(`actionid`,`actionname`,`actincolumnid`,`action`,`viewmode`) values (1,'添加收藏',1,'ad collection','1'),(2,'删除收藏',1,'delete collection','1'),(3,'添加标准单元',4,'add unit st',NULL),(4,'删除标准单元',4,'delete unit st',NULL),(5,'添加标准课时',5,'add course st',NULL),(6,'删除标准课时',5,'delete course st',NULL),(7,'添加标准单词',6,'add word st',NULL),(8,'添加自定义单元',4,'add unit ex',NULL),(10,'删除自定义单元',4,'delete unit ex',NULL),(11,'添加自定义课时',5,'add course ex',NULL),(12,'删除自定义课时',5,'delete course ex',NULL),(16,'添加自定义单词',6,'add word ex',NULL),(17,'布置作业',2,'add homework',NULL),(18,'查看作业',2,'get homework',NULL);

/*Table structure for table `t_actioncolumn` */

DROP TABLE IF EXISTS `t_actioncolumn`;

CREATE TABLE `t_actioncolumn` (
  `actioncolumnid` int(32) NOT NULL AUTO_INCREMENT COMMENT '功能分栏主键',
  `actioncolumnname` varchar(32) NOT NULL COMMENT '功能分类描述',
  PRIMARY KEY (`actioncolumnid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_actioncolumn` */

insert  into `t_actioncolumn`(`actioncolumnid`,`actioncolumnname`) values (1,'课本管理'),(2,'作业管理 '),(3,'成绩管理'),(4,'单元管理'),(5,'课时管理'),(6,'词汇管理'),(7,'用户管理');

/*Table structure for table `t_actionrole` */

DROP TABLE IF EXISTS `t_actionrole`;

CREATE TABLE `t_actionrole` (
  `arid` varchar(32) NOT NULL COMMENT '权限组映射主键',
  `action` varchar(32) NOT NULL COMMENT '功能英文描述',
  `roleid` varchar(32) NOT NULL COMMENT '权限组（角色组）编号',
  PRIMARY KEY (`arid`),
  KEY `fk_tactionrole_trole_roleid` (`roleid`),
  KEY `fk_tactionrole_taction_action` (`action`),
  CONSTRAINT `fk_tactionrole_taction_action` FOREIGN KEY (`action`) REFERENCES `t_action` (`action`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tactionrole_trole_roleid` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`roleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_actionrole` */

/*Table structure for table `t_book` */

DROP TABLE IF EXISTS `t_book`;

CREATE TABLE `t_book` (
  `bookid` varchar(32) NOT NULL COMMENT '课本编号',
  `bookname` varchar(50) NOT NULL COMMENT '课本名称',
  `versionid` varchar(50) DEFAULT NULL COMMENT '课本版本',
  `langid` varchar(10) NOT NULL COMMENT '语言类型编号',
  `typeid` varchar(10) NOT NULL COMMENT '标准还是自定义',
  `cid` varchar(10) DEFAULT NULL COMMENT '课本所属年级分类编号',
  `cover` varchar(200) DEFAULT NULL COMMENT '封面路径',
  `status` varchar(3) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`bookid`),
  KEY `fk_tbook_tversion_versionid` (`versionid`),
  KEY `fk_tboo_type_typeid` (`typeid`),
  KEY `fk_tbook_tlang_langid` (`langid`),
  KEY `fk_tboo_tcategory` (`cid`),
  CONSTRAINT `fk_tbook_tlang_langid` FOREIGN KEY (`langid`) REFERENCES `t_book_lang` (`langid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tbook_tversion_versionid` FOREIGN KEY (`versionid`) REFERENCES `t_book_version` (`versionid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tboo_tcategory` FOREIGN KEY (`cid`) REFERENCES `t_book_category` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tboo_type_typeid` FOREIGN KEY (`typeid`) REFERENCES `t_book_type` (`typeid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book` */

insert  into `t_book`(`bookid`,`bookname`,`versionid`,`langid`,`typeid`,`cid`,`cover`,`status`) values ('000000250196890','测试添加课本','BV100','BL100','BT100','BC109','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10001','小学语文一年级上册(人教版)','BV100','BL100','BT100','BC100','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10002','小学语文一年级下册(人教版)','BV100','BL100','BT100','BC100','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10003','小学语文二年级上册(人教版)','BV100','BL100','BT100','BC101','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10004','小学语文二年级上册(人教版)','BV100','BL100','BT100','BC101','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10005','小学语文三年级上册(人教版)','BV100','BL100','BT100','BC102','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10006','小学语文三年级上册(人教版)','BV100','BL100','BT100','BC102','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10009','小学英语一年级上册(人教版)','BV100','BL101','BT100','BC100','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10011','小学英语二年级下册(人教版)','BV100','BL101','BT100','BC101','https://www.tarsadata.com/books/yuwenbubian11.jpg','1'),('B10012','小学英语二年级上册(人教版)','BV100','BL101','BT100','BC101','https://www.tarsadata.com/books/yuwenbubian11.jpg','1');

/*Table structure for table `t_book_category` */

DROP TABLE IF EXISTS `t_book_category`;

CREATE TABLE `t_book_category` (
  `cid` varchar(32) NOT NULL COMMENT '课本分类',
  `cname` varchar(20) DEFAULT NULL COMMENT '课本分类描述',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book_category` */

insert  into `t_book_category`(`cid`,`cname`) values ('BC100','一年级'),('BC101','二年级'),('BC102','三年级'),('BC103','四年级'),('BC104','五年级'),('BC105','六年级'),('BC106','七年级'),('BC107','八年级'),('BC108','九年级'),('BC109','提高班');

/*Table structure for table `t_book_lang` */

DROP TABLE IF EXISTS `t_book_lang`;

CREATE TABLE `t_book_lang` (
  `langid` varchar(32) NOT NULL COMMENT '课本语言编号',
  `langname` varchar(32) DEFAULT NULL COMMENT '课本语言描述',
  PRIMARY KEY (`langid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book_lang` */

insert  into `t_book_lang`(`langid`,`langname`) values ('BL100','中文'),('BL101','英文');

/*Table structure for table `t_book_type` */

DROP TABLE IF EXISTS `t_book_type`;

CREATE TABLE `t_book_type` (
  `typeid` varchar(32) NOT NULL COMMENT '课本来源类型编号',
  `typename` varchar(32) DEFAULT NULL COMMENT '课本来源类型描述',
  PRIMARY KEY (`typeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book_type` */

insert  into `t_book_type`(`typeid`,`typename`) values ('BT100','标准'),('BT101','自定义');

/*Table structure for table `t_book_version` */

DROP TABLE IF EXISTS `t_book_version`;

CREATE TABLE `t_book_version` (
  `versionid` varchar(32) NOT NULL COMMENT '课本版本id',
  `versionname` varchar(32) DEFAULT NULL COMMENT '课本版本描述',
  PRIMARY KEY (`versionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_book_version` */

insert  into `t_book_version`(`versionid`,`versionname`) values ('BV100','人教版'),('BV101','苏教版');

/*Table structure for table `t_city` */

DROP TABLE IF EXISTS `t_city`;

CREATE TABLE `t_city` (
  `cityid` varchar(32) NOT NULL COMMENT '城市主键',
  `cityname` varchar(32) NOT NULL COMMENT '城市描述',
  `provinceid` varchar(32) NOT NULL COMMENT '所属省份',
  PRIMARY KEY (`cityid`),
  KEY `fk_tcity_tprovince_provinceid` (`provinceid`),
  CONSTRAINT `fk_tcity_tprovince_provinceid` FOREIGN KEY (`provinceid`) REFERENCES `t_province` (`provinceid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_city` */

insert  into `t_city`(`cityid`,`cityname`,`provinceid`) values ('CITY10001','惠州','PRO10001'),('CITY10002','北京','PRO10001'),('CITY10003','上海','PRO10001'),('CITY10004','天津','PRO10001');

/*Table structure for table `t_class` */

DROP TABLE IF EXISTS `t_class`;

CREATE TABLE `t_class` (
  `classid` varchar(32) NOT NULL COMMENT '班级主键',
  `classname` varchar(32) NOT NULL COMMENT '班级描述',
  `gradeid` varchar(32) NOT NULL COMMENT '所属年级',
  PRIMARY KEY (`classid`),
  KEY `fk_tclass_tgrade_gradeid` (`gradeid`),
  CONSTRAINT `fk_tclass_tgrade_gradeid` FOREIGN KEY (`gradeid`) REFERENCES `t_grade` (`gradeid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_class` */

insert  into `t_class`(`classid`,`classname`,`gradeid`) values ('CLA10001','1班','GRA1001'),('CLA10002','2班','GRA1001'),('CLA10003','3班','GRA1001'),('CLA10004','4班','GRA1001');

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

insert  into `t_collection`(`userid`,`item1`,`item2`,`item3`,`item4`) values ('000000174136529',NULL,NULL,NULL,NULL),('000000327994667',NULL,NULL,NULL,NULL),('000000435293653',NULL,NULL,NULL,NULL),('000000885568900',NULL,NULL,NULL,NULL),('000001008529165',NULL,NULL,NULL,NULL),('000001048955912',NULL,NULL,NULL,NULL),('000001246525898',NULL,NULL,NULL,NULL),('000001254333827',NULL,NULL,NULL,NULL),('000001771794181',NULL,NULL,NULL,NULL),('U100001',NULL,NULL,NULL,NULL);

/*Table structure for table `t_collection_item` */

DROP TABLE IF EXISTS `t_collection_item`;

CREATE TABLE `t_collection_item` (
  `itemid` varchar(32) NOT NULL COMMENT '收藏项id',
  `bookid` varchar(32) NOT NULL COMMENT '包含哪本课本',
  `userid` varchar(32) NOT NULL COMMENT '属于哪个收藏本',
  `state` varchar(3) NOT NULL COMMENT '状态',
  PRIMARY KEY (`itemid`),
  KEY `fk_tcolitem_tbook_bookid` (`bookid`),
  KEY `fk_tcolitem_tcollection_userid` (`userid`),
  CONSTRAINT `fk_tcolitem_tbook_bookid` FOREIGN KEY (`bookid`) REFERENCES `t_book` (`bookid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tcolitem_tcollection_userid` FOREIGN KEY (`userid`) REFERENCES `t_collection` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_collection_item` */

insert  into `t_collection_item`(`itemid`,`bookid`,`userid`,`state`) values ('I000000150262330','B10009','U100001','1'),('I000000272370688','B10009','000001246525898','1'),('I000000274009579','B10001','000001048955912','1'),('I000000284661495','B10001','000000327994667','1'),('I000000576510835','B10001','000000435293653','1'),('I000000657343365','B10001','000000885568900','1'),('I000000687178227','B10009','000000174136529','1'),('I000000940235551','B10001','000000174136529','1'),('I000001091665515','B10001','000001254333827','1'),('I000001135211893','B10001','000001008529165','1'),('I000001627233360','B10001','000001771794181','1'),('I000001729358718','B10001','000001246525898','1'),('I000002050498465','B10001','U100001','1');

/*Table structure for table `t_course` */

DROP TABLE IF EXISTS `t_course`;

CREATE TABLE `t_course` (
  `courseid` varchar(32) NOT NULL COMMENT '课时主键',
  `coursename` varchar(32) NOT NULL COMMENT '课时名称',
  `unitid` varchar(32) NOT NULL COMMENT '所属单元编号',
  PRIMARY KEY (`courseid`),
  KEY `fk_tcourse_tunit_unitid` (`unitid`),
  CONSTRAINT `fk_tcourse_tunit_unitid` FOREIGN KEY (`unitid`) REFERENCES `t_unit` (`unitid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_course` */

insert  into `t_course`(`courseid`,`coursename`,`unitid`) values ('000000470481259','测试课时1','UN10005'),('000001154637087','测试课时1','000001963957562'),('000001688075376','测试课时','000001208459554'),('CT10001','金木水火土','UN10001'),('CT10002','口目耳','UN10001'),('CT10003','日月水火','UN10001'),('CT10004','对韵歌','UN10001'),('CT10005','语文园地一','UN10001'),('CT10006','第一单元复习','UN10001'),('CT10007','单韵母','UN10002'),('CT10008','单韵母','UN10002'),('CT10009','声母','UN10002'),('CT10010','复韵母','UN10002'),('CT10011','HelloWord','UN10008'),('CT10012','Home','UN10008'),('CT10013','School','UN10008'),('CT10014','Frient','UN10011');

/*Table structure for table `t_grade` */

DROP TABLE IF EXISTS `t_grade`;

CREATE TABLE `t_grade` (
  `gradeid` varchar(32) NOT NULL COMMENT '年级主键',
  `gradename` varchar(32) NOT NULL COMMENT '年级描述',
  PRIMARY KEY (`gradeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

insert  into `t_grade`(`gradeid`,`gradename`) values ('GRA1001','一年级'),('GRA1002','二年级'),('GRA1003','三年级'),('GRA1004','四年级');

/*Table structure for table `t_homework` */

DROP TABLE IF EXISTS `t_homework`;

CREATE TABLE `t_homework` (
  `hwid` varchar(32) NOT NULL COMMENT '作业编号',
  `hwname` varchar(32) NOT NULL COMMENT '作业名',
  `classid` varchar(32) NOT NULL COMMENT '班级编号',
  `hwstate` tinyint(1) NOT NULL COMMENT '是否有效',
  PRIMARY KEY (`hwid`),
  KEY `fk_thomework_tclass_classid` (`classid`),
  CONSTRAINT `fk_thomework_tclass_classid` FOREIGN KEY (`classid`) REFERENCES `t_class` (`classid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_homework` */

insert  into `t_homework`(`hwid`,`hwname`,`classid`,`hwstate`) values ('000000156030444','1班5月12日10时2分','CLA10001',1),('000000233374079','2班5月12日14时11分','CLA10002',1),('000000268324545','1班5月12日9时8分','CLA10001',1),('000000300456079','1班4月11日1时23分','CLA10001',1),('000000361726846','1班5月12日1时28分','CLA10001',1),('000000543501208','1班5月12日9时42分','CLA10001',1),('000000672402587','1班4月12日0时18分','CLA10001',1),('000000847559162','1班4月12日0时19分','CLA10001',1),('000001263612224','1班5月12日11时35分','CLA10001',1),('000001384001018','1班5月12日0时21分','CLA10001',1),('000001453381940','2班5月12日16时16分','CLA10002',1),('000001665122109','3班5月12日17时13分','CLA10003',1),('000001923546269','3班5月12日17时15分','CLA10003',1);

/*Table structure for table `t_homework_item` */

DROP TABLE IF EXISTS `t_homework_item`;

CREATE TABLE `t_homework_item` (
  `hwid` varchar(32) NOT NULL COMMENT '属于哪个作业',
  `wordid` varchar(32) NOT NULL COMMENT '作业里的单词',
  PRIMARY KEY (`hwid`,`wordid`),
  KEY `fk_thwitem_thomework_wordid` (`wordid`),
  CONSTRAINT `fk_thwitem_thomework_hwid` FOREIGN KEY (`hwid`) REFERENCES `t_homework` (`hwid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_thwitem_thomework_wordid` FOREIGN KEY (`wordid`) REFERENCES `t_word` (`wordid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_homework_item` */

insert  into `t_homework_item`(`hwid`,`wordid`) values ('000000361726846','W000000025681032'),('000001923546269','W000000067064363'),('000000233374079','W000000070296948'),('000000268324545','W000000070296948'),('000000300456079','W000000070296948'),('000000361726846','W000000070296948'),('000001263612224','W000000070296948'),('000001453381940','W000000070296948'),('000001665122109','W000000070296948'),('000001923546269','W000000070296948'),('000000233374079','W000000126155652'),('000001263612224','W000000126155652'),('000000233374079','W000000374769411'),('000000268324545','W000000374769411'),('000000543501208','W000000374769411'),('000000156030444','W000000390463162'),('000000543501208','W000000647366917'),('000000156030444','W000000884814876'),('000001263612224','W000000954030256'),('000000156030444','W000000999619322'),('000001665122109','W000001274899635'),('000001923546269','W000001643099327'),('000001453381940','W000001823420550'),('000001665122109','W000001823420550'),('000000543501208','W000001911727372'),('000001453381940','W000001911727372');

/*Table structure for table `t_homework_progress` */

DROP TABLE IF EXISTS `t_homework_progress`;

CREATE TABLE `t_homework_progress` (
  `hpid` int(32) NOT NULL AUTO_INCREMENT COMMENT '作业进度表主键',
  `userid` varchar(32) NOT NULL COMMENT '学生编号',
  `hwid` varchar(32) NOT NULL COMMENT '作业编号',
  `score` double(4,1) NOT NULL COMMENT '分数',
  `state` tinyint(1) NOT NULL COMMENT '是否提交作业',
  PRIMARY KEY (`hpid`),
  KEY `fk_thomeworkprogress_tuser_userid` (`userid`),
  KEY `fk_thomeworkprogress_thomework_hwid` (`hwid`),
  CONSTRAINT `fk_thomeworkprogress_thomework_hwid` FOREIGN KEY (`hwid`) REFERENCES `t_homework` (`hwid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_thomeworkprogress_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_homework_progress` */

/*Table structure for table `t_organization` */

DROP TABLE IF EXISTS `t_organization`;

CREATE TABLE `t_organization` (
  `orgid` varchar(32) NOT NULL COMMENT '机构编号',
  `orgname` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `cityid` varchar(32) NOT NULL COMMENT '所属城市',
  PRIMARY KEY (`orgid`),
  KEY `fk_torg_tcity_cityid` (`cityid`),
  CONSTRAINT `fk_torg_tcity_cityid` FOREIGN KEY (`cityid`) REFERENCES `t_city` (`cityid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_organization` */

insert  into `t_organization`(`orgid`,`orgname`,`cityid`) values ('ORG10001','财经学院','CITY10001'),('ORG10002','中心小学','CITY10001'),('ORG10003','惠南中心','CITY10001'),('ORG10004','东里二中','CITY10001');

/*Table structure for table `t_province` */

DROP TABLE IF EXISTS `t_province`;

CREATE TABLE `t_province` (
  `provinceid` varchar(32) NOT NULL COMMENT '省份主键',
  `provincename` varchar(32) NOT NULL COMMENT '省份描述',
  PRIMARY KEY (`provinceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_province` */

insert  into `t_province`(`provinceid`,`provincename`) values ('PRO10001','广东省'),('PRO10002','广西省'),('PRO10003','江西省'),('PRO10004','吉林省');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `roleid` varchar(32) NOT NULL COMMENT '用户角色编号',
  `rolename` varchar(32) NOT NULL COMMENT '用户角色描述',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`roleid`,`rolename`) values ('UT001','超级管理员'),('UT002','普通管理员'),('UT003','学生'),('UT004','教师');

/*Table structure for table `t_score` */

DROP TABLE IF EXISTS `t_score`;

CREATE TABLE `t_score` (
  `scid` varchar(32) NOT NULL COMMENT '成绩记录id',
  `userid` varchar(32) NOT NULL COMMENT '用户id',
  `courseid` varchar(32) NOT NULL COMMENT '课时id',
  `score` double(4,1) NOT NULL DEFAULT '0.0' COMMENT '成绩',
  `createtime` datetime NOT NULL COMMENT '提交时间',
  `scstate` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否生效',
  PRIMARY KEY (`scid`,`userid`),
  KEY `fk_tscore_tuser_userid` (`userid`),
  KEY `fk_tscore_tuser_courseid` (`courseid`),
  CONSTRAINT `fk_tscore_tuser_courseid` FOREIGN KEY (`courseid`) REFERENCES `t_course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tscore_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_score` */

insert  into `t_score`(`scid`,`userid`,`courseid`,`score`,`createtime`,`scstate`) values ('SC000000734427028','000001008529165','CT10001',0.0,'2019-05-12 08:56:47',1),('SC000000955575656','000001246525898','CT10001',0.0,'2019-05-12 09:41:37',1),('SC000001064309949','000001008529165','CT10001',0.0,'2019-05-12 08:55:40',1);

/*Table structure for table `t_unit` */

DROP TABLE IF EXISTS `t_unit`;

CREATE TABLE `t_unit` (
  `unitid` varchar(32) NOT NULL COMMENT '单元主键',
  `unitname` varchar(32) NOT NULL COMMENT '单元名称',
  `bookid` varchar(32) NOT NULL COMMENT '所属课本编号',
  PRIMARY KEY (`unitid`),
  KEY `fk_tunit_tbook_bookid` (`bookid`),
  CONSTRAINT `fk_tunit_tbook_bookid` FOREIGN KEY (`bookid`) REFERENCES `t_book` (`bookid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_unit` */

insert  into `t_unit`(`unitid`,`unitname`,`bookid`) values ('000001208459554','测试单元','000000250196890'),('000001963957562','测试单元','B10002'),('UN10001','第一单元','B10001'),('UN10002','第二单元','B10001'),('UN10005','第一单元','B10002'),('UN10006','第二单元','B10002'),('UN10007','第二单元','B10003'),('UN10008','UNITONE','B10009'),('UN10009','UNITTWO','B10009'),('UN10010','UNITTHREE','B10009'),('UN10011','UNITONE','B10011');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userid` varchar(32) NOT NULL COMMENT '用户ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `roleid` varchar(10) DEFAULT NULL COMMENT '用户类型ID',
  `orgid` varchar(32) DEFAULT NULL COMMENT '用户机构ID',
  `gradeid` varchar(32) DEFAULT NULL COMMENT '年级编号',
  `classid` varchar(32) DEFAULT NULL COMMENT '班级编号编号',
  `provinceid` varchar(32) DEFAULT NULL COMMENT '省份',
  `cityid` varchar(32) DEFAULT NULL COMMENT '城市',
  `open_id` varchar(200) DEFAULT NULL COMMENT '微信用户唯一标识',
  `state` varchar(10) NOT NULL COMMENT '状态',
  PRIMARY KEY (`userid`),
  KEY `fk_tuser_torg_orgind` (`orgid`),
  KEY `fk_tuser_tgrade_gradeid` (`gradeid`),
  KEY `fk_tuser_tclass_classid` (`classid`),
  KEY `fk_tuser_tprovince_provinceid` (`provinceid`),
  KEY `fk_tuser_tcity_cityid` (`cityid`),
  KEY `fk_tuser_trole_roleid` (`roleid`),
  CONSTRAINT `fk_tuser_tcity_cityid` FOREIGN KEY (`cityid`) REFERENCES `t_city` (`cityid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuser_tclass_classid` FOREIGN KEY (`classid`) REFERENCES `t_class` (`classid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuser_tgrade_gradeid` FOREIGN KEY (`gradeid`) REFERENCES `t_grade` (`gradeid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuser_torg_orgind` FOREIGN KEY (`orgid`) REFERENCES `t_organization` (`orgid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuser_tprovince_provinceid` FOREIGN KEY (`provinceid`) REFERENCES `t_province` (`provinceid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuser_trole_roleid` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`roleid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userid`,`username`,`password`,`avatar`,`sex`,`birthday`,`email`,`roleid`,`orgid`,`gradeid`,`classid`,`provinceid`,`cityid`,`open_id`,`state`) values ('000001008529165','teacher6','123','avatar','1','2019-05-12','email','UT004','ORG10001','GRA1001','CLA10001','PRO10001','CITY10001','open_id','1'),('000001246525898','t8','123','avatar','1','2019-05-12','email','UT004','ORG10001','GRA1001','CLA10001','PRO10001','CITY10001','open_id','1'),('000001765595137','t1','123','avatar','1','2019-05-15','email','UT004','ORG10002','GRA1001','CLA10003','PRO10001','CITY10001','open_id','1');

/*Table structure for table `t_user_class` */

DROP TABLE IF EXISTS `t_user_class`;

CREATE TABLE `t_user_class` (
  `ucid` int(64) NOT NULL AUTO_INCREMENT COMMENT '教师-班级映射表主键',
  `userid` varchar(32) NOT NULL COMMENT '用户(教师编号)',
  `classid` varchar(32) NOT NULL COMMENT '班级编号',
  PRIMARY KEY (`ucid`),
  KEY `fk_tuserclass_tuser_userid` (`userid`),
  KEY `fk_tuserclass_tclass_classid` (`classid`),
  CONSTRAINT `fk_tuserclass_tclass_classid` FOREIGN KEY (`classid`) REFERENCES `t_class` (`classid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuserclass_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_class` */

insert  into `t_user_class`(`ucid`,`userid`,`classid`) values (5,'000001246525898','CLA10001'),(6,'000001765595137','CLA10003');

/*Table structure for table `t_user_session` */

DROP TABLE IF EXISTS `t_user_session`;

CREATE TABLE `t_user_session` (
  `sid` int(32) NOT NULL AUTO_INCREMENT COMMENT '记录主键',
  `session_key` varchar(250) NOT NULL COMMENT '微信用户的一个标识',
  `open_id` varchar(250) NOT NULL COMMENT '小程序账号的唯一标识',
  `expire_date` datetime NOT NULL COMMENT 'session_key的有效时间',
  `client_key` varchar(500) NOT NULL COMMENT '客户端的登录态数据',
  PRIMARY KEY (`sid`),
  UNIQUE KEY `UNIQUE_OPENID` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `t_user_session` */

insert  into `t_user_session`(`sid`,`session_key`,`open_id`,`expire_date`,`client_key`) values (39,'Tjz87iTcYteLf3SkXNF9Rw==','o3Cwg0cLtijEmNfwkBjhTojpKbZo','2019-04-20 15:18:26','2d1c6dcfb89f296194141f7f2fe4a5a9');

/*Table structure for table `t_userrole` */

DROP TABLE IF EXISTS `t_userrole`;

CREATE TABLE `t_userrole` (
  `urid` varchar(32) NOT NULL COMMENT '用户角色映射主键',
  `userid` varchar(32) NOT NULL COMMENT '用户编号',
  `roleid` varchar(32) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`urid`),
  KEY `fk_tuserole_tuser_userid` (`userid`),
  KEY `fk_tuserole_trole_roleid` (`roleid`),
  CONSTRAINT `fk_tuserole_trole_roleid` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`roleid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tuserole_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_userrole` */

/*Table structure for table `t_word` */

DROP TABLE IF EXISTS `t_word`;

CREATE TABLE `t_word` (
  `wordid` varchar(64) NOT NULL COMMENT '单词主键',
  `wordtext` varchar(200) NOT NULL COMMENT '单词文本',
  `voiceurl` varchar(200) NOT NULL COMMENT '音频链接',
  `lengtypeid` varchar(32) DEFAULT NULL COMMENT '文本长度类别id',
  `cometypeid` varchar(32) DEFAULT NULL COMMENT '来源类别id',
  `courseid` varchar(32) NOT NULL COMMENT '所属课时id',
  PRIMARY KEY (`wordid`),
  KEY `fk_tword_twordlengttype_lengtypeid` (`lengtypeid`),
  KEY `fk_tword_tcometype_tcometypeid` (`cometypeid`),
  KEY `fk_tword_tcourse_courseid` (`courseid`),
  CONSTRAINT `fk_tword_tcometype_tcometypeid` FOREIGN KEY (`cometypeid`) REFERENCES `t_word_cometype` (`cometypeid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tword_tcourse_courseid` FOREIGN KEY (`courseid`) REFERENCES `t_course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tword_twordlengttype_lengtypeid` FOREIGN KEY (`lengtypeid`) REFERENCES `t_word_lengtype` (`lengtypeid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word` */

insert  into `t_word`(`wordid`,`wordtext`,`voiceurl`,`lengtypeid`,`cometypeid`,`courseid`) values ('W000000025681032','连接','dictation/resource/vocabulary/连接.mp3','WL1002','WC1002','CT10001'),('W000000067064363','高兴','dictation/resource/vocabulary/高兴.mp3','WL1002','WC1002','CT10001'),('W000000070296948','你好','dictation/resource/vocabulary/你好.mp3','WL1002','WC1001','CT10001'),('W000000092095890','股份','dictation/resource/vocabulary/股份.mp3','WL1002','WC1002','CT10001'),('W000000126155652','来回','dictation/resource/vocabulary/来回.mp3','WL1002','WC1001','CT10001'),('W000000132476196','开心','dictation/resource/vocabulary/开心.mp3','WL1002','WC1002','CT10002'),('W000000185743128','输入','dictation/resource/vocabulary/输入.mp3','WL1002','WC1002','CT10001'),('W000000234704377','自定义','dictation/resource/vocabulary/自定义.mp3','WL1002','WC1002','CT10001'),('W000000339109895','中英文','dictation/resource/vocabulary/中英文.mp3','WL1002','WC1002','CT10001'),('W000000374769411','二','dictation/resource/vocabulary/二.mp3','WL1002','WC1001','CT10001'),('W000000390463162','Word','dictation/resource/vocabulary/Word.mp3','WL1002','WC1001','CT10011'),('W000000400681627','牛头','dictation/resource/vocabulary/牛头.mp3','WL1002','WC1001','CT10002'),('W000000590896343','上下','dictation/resource/vocabulary/上下.mp3','WL1002','WC1002','CT10001'),('W000000647366917','一','dictation/resource/vocabulary/一.mp3','WL1002','WC1001','CT10001'),('W000000735733438','后果','dictation/resource/vocabulary/后果.mp3','WL1002','WC1001','CT10002'),('W000000769828165','英文','dictation/resource/vocabulary/英文.mp3','WL1002','WC1002','CT10002'),('W000000806611467','录入','dictation/resource/vocabulary/录入.mp3','WL1002','WC1002','CT10002'),('W000000854193136','English','dictation/resource/vocabulary/English.mp3','WL1002','WC1002','CT10001'),('W000000855997358','小鸟','dictation/resource/vocabulary/小鸟.mp3','WL1002','WC1001','CT10002'),('W000000884814876','Hello','dictation/resource/vocabulary/Hello.mp3','WL1002','WC1001','CT10011'),('W000000905652499','教育','dictation/resource/vocabulary/教育.mp3','WL1002','WC1002','CT10001'),('W000000954030256','测试','dictation/resource/vocabulary/测试.mp3','WL1002','WC1002','CT10001'),('W000000955704863','学位','dictation/resource/vocabulary/学位.mp3','WL1002','WC1002','CT10002'),('W000000999619322','apple','dictation/resource/vocabulary/apple.mp3','WL1002','WC1001','CT10011'),('W000001000879224','少云','dictation/resource/vocabulary/少云.mp3','WL1002','WC1001','CT10002'),('W000001039301197','词语','dictation/resource/vocabulary/词语.mp3','WL1002','WC1002','CT10001'),('W000001045513254','多少','dictation/resource/vocabulary/多少.mp3','WL1002','WC1001','CT10002'),('W000001110674953','高兴','dictation/resource/vocabulary/高兴.mp3','WL1002','WC1002','CT10002'),('W000001134027889','下去','dictation/resource/vocabulary/下去.mp3','WL1002','WC1001','CT10001'),('W000001274899635','雨水','dictation/resource/vocabulary/雨水.mp3','WL1002','WC1001','CT10001'),('W000001310685381','说明','dictation/resource/vocabulary/说明.mp3','WL1002','WC1002','CT10002'),('W000001317427138','水果','dictation/resource/vocabulary/水果.mp3','WL1002','WC1001','CT10002'),('W000001345600558','座位','dictation/resource/vocabulary/座位.mp3','WL1002','WC1002','CT10001'),('W000001350416216','单词','dictation/resource/vocabulary/单词.mp3','WL1002','WC1002','CT10001'),('W000001643099327','添加','dictation/resource/vocabulary/添加.mp3','WL1002','WC1002','CT10001'),('W000001698842832','brain','dictation/resource/vocabulary/brain.mp3','WL1002','WC1001','CT10011'),('W000001753698823','录入','dictation/resource/vocabulary/录入.mp3','WL1002','WC1002','CT10001'),('W000001823420550','水中月','dictation/resource/vocabulary/水中月.mp3','WL1002','WC1001','CT10001'),('W000001863793817','解读','dictation/resource/vocabulary/解读.mp3','WL1002','WC1002','CT10001'),('W000001911727372','三','dictation/resource/vocabulary/三.mp3','WL1002','WC1001','CT10001'),('W000001922016238','banana','dictation/resource/vocabulary/banana.mp3','WL1002','WC1002','CT10011'),('W000001928648071','展开','dictation/resource/vocabulary/展开.mp3','WL1002','WC1002','CT10002'),('W100007','自定二','dictation/resource/vocabulary/自定二.mp3','WL1002','WC1002','CT10001');

/*Table structure for table `t_word_cometype` */

DROP TABLE IF EXISTS `t_word_cometype`;

CREATE TABLE `t_word_cometype` (
  `cometypeid` varchar(32) NOT NULL COMMENT '单词来源类别编号',
  `cometypename` varchar(32) NOT NULL COMMENT '单词来源类别描述',
  PRIMARY KEY (`cometypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word_cometype` */

insert  into `t_word_cometype`(`cometypeid`,`cometypename`) values ('WC1001','标准'),('WC1002','自定义');

/*Table structure for table `t_word_ex` */

DROP TABLE IF EXISTS `t_word_ex`;

CREATE TABLE `t_word_ex` (
  `weid` varchar(32) NOT NULL COMMENT '扩张单词项主键',
  `userid` varchar(32) NOT NULL COMMENT '单词所属用户id',
  `wordid` varchar(64) NOT NULL COMMENT '扩展单词编号',
  PRIMARY KEY (`weid`),
  KEY `fk_wordid` (`wordid`),
  KEY `fk_twordex_tuser_userid` (`userid`),
  CONSTRAINT `fk_twordex_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_wordid` FOREIGN KEY (`wordid`) REFERENCES `t_word` (`wordid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word_ex` */

insert  into `t_word_ex`(`weid`,`userid`,`wordid`) values ('WE000000439867078','000001008529165','W000001753698823'),('WE000000698981342','000001246525898','W000001922016238'),('WE000001369197366','000001246525898','W000000954030256');

/*Table structure for table `t_word_lengtype` */

DROP TABLE IF EXISTS `t_word_lengtype`;

CREATE TABLE `t_word_lengtype` (
  `lengtypeid` varchar(32) NOT NULL COMMENT '单词长度类型编号',
  `lengtypename` varchar(32) NOT NULL COMMENT '单词长度类型描述',
  PRIMARY KEY (`lengtypeid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word_lengtype` */

insert  into `t_word_lengtype`(`lengtypeid`,`lengtypename`) values ('WL1001','单字'),('WL1002','词语');

/*Table structure for table `t_word_userselect` */

DROP TABLE IF EXISTS `t_word_userselect`;

CREATE TABLE `t_word_userselect` (
  `wusid` varchar(32) NOT NULL COMMENT '用户筛选记录主键',
  `userid` varchar(32) NOT NULL COMMENT '用户编号',
  `wordid` varchar(32) NOT NULL COMMENT '单词编号',
  PRIMARY KEY (`wusid`),
  KEY `fk_tworduserselect_tuser_userid` (`userid`),
  KEY `fk_tworduserselect_tword_wordid` (`wordid`),
  CONSTRAINT `fk_tworduserselect_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tworduserselect_tword_wordid` FOREIGN KEY (`wordid`) REFERENCES `t_word` (`wordid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word_userselect` */

/*Table structure for table `t_word_wrong` */

DROP TABLE IF EXISTS `t_word_wrong`;

CREATE TABLE `t_word_wrong` (
  `wwid` varchar(32) NOT NULL COMMENT '错字集记录主键',
  `userid` varchar(32) NOT NULL COMMENT '用户id',
  `bookid` varchar(32) NOT NULL COMMENT '单词所属课本id',
  `wordid` varchar(32) NOT NULL COMMENT '单词编号',
  PRIMARY KEY (`wwid`),
  KEY `fk_tww_tuser_userid` (`userid`),
  KEY `fk_tww_tbook_bookid` (`bookid`),
  KEY `fk_tww_tword_wordid` (`wordid`),
  CONSTRAINT `fk_tww_tbook_bookid` FOREIGN KEY (`bookid`) REFERENCES `t_book` (`bookid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tww_tuser_userid` FOREIGN KEY (`userid`) REFERENCES `t_user` (`userid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tww_tword_wordid` FOREIGN KEY (`wordid`) REFERENCES `t_word` (`wordid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_word_wrong` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
