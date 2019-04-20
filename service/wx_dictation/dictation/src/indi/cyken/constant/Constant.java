package indi.cyken.constant;

public interface Constant {
	/**
	 * 用户激活状态
	 */
	String USER_IS_ACTIVE="1";
	
	//前端请求处理成功
	Integer WRITE_BACK_SUCCESS=1;
	//前端请求处理失败
	Integer WRITE_BACK_FAIL=0;
	
	
	///////////数据库主键命名前缀////////////////
	String PREFIX_SCORE ="SC";					//t_score主键前缀
	String PREFIX_WORD ="W";					//t_word主键前缀
	String PREFIX_MAP_WORDEX_USER="WE";		//t_word_ex主键前缀
	String PREFIX_MAP_WORDWRONG_USER="WW";	//t_word_wrong主键前缀

	
	
	
}
