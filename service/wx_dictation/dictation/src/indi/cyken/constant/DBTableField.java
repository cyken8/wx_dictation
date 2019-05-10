package indi.cyken.constant;

/**
 * 自定义与标准课本，单元，课时，单词的标识
 * @author Yong
 *
 */
public interface DBTableField {
	
	//标准单词
	String WORD_COMETYPE_STANDARD="WC1001";
	//自定义单词
	String WORD_COMETYPE_EX="WC1002";
	
	//标准课时
	String COURSE_COMETYPE_STANDAR="";
	//自定义课时
	String COURSE_COMETYPE_EX="";
	
	//标准单元
	String UNIT_COMETYPE_STANDAR="";
	//自定义单元
	String UNIT_COMETYPE_EX="";
	
	//标准课本
	String BOOK_COMETYPE_STANDAR="BT100";
	//自定义keb 
	String BOOK_COMETYPE_EX="";
	
	//单词长度类别
	String WORD_LENGTYPE_SHORTWORD="WL1001";
	String WORD_LENGTYPE_LONGWORD="WL1002";
	
	//角色
	String USER_ROLE_STUDENT="UT003";		//学生角色
	String USER_ROLE_TEACHER="UT004";		//教师角色	
	

}
