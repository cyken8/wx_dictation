package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.WordSelectedDao;
import indi.cyken.domain.Course;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.utils.DataSourceUtils;

public class WordSelectedDaoImpl implements WordSelectedDao{
	/**
	 * 根据用户id和课时id获取课时下被筛选过的单词
	 */
	@Override
	public List<Word> getSelectedWordsByUidCid(String userid, String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT w1.wordid,w1.wordtext,w1.voiceurl,w1.lengtypeid,w1.cometypeid,w1.courseid FROM t_word w1,t_word_userselect wu WHERE wu.userid=? and wu.wordid=w1.wordid and w1.cometypeid= ? AND w1.courseid=?\r\n" + 
				"UNION \r\n" + 
				"SELECT w2.wordid,w2.wordtext,w2.voiceurl,w2.lengtypeid,w2.cometypeid,w2.courseid FROM t_word w2,t_word_ex we,t_word_userselect wu2 WHERE wu2.userid=? and we.userid=? AND we.wordid=w2.wordid AND wu2.wordid=we.wordid AND w2.courseid=?; ";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),userid, DBTableField.WORD_COMETYPE_STANDAR,courseid,userid,userid,courseid);
		List<Word> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			WordLengType wl=new WordLengType();
			BeanUtils.populate(wl, map);
			WordComeType wc=new WordComeType();
			BeanUtils.populate(wc, map);
			Course course=new Course();
			BeanUtils.populate(course, map);
			
			Word word=new Word();
			BeanUtils.populate(word, map);
			word.setLengtype(wl);
			word.setCometype(wc);
			word.setCourse(course);
			
			list.add(word);
		}
		return list;
	}

	/**
	 * 查询用户单词筛选项是否存在
	 */
	@Override
	public int queryWordSelItem(String userid, String wordid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from t_word_userselect where userid=? and wordid=?";
		return qr.query(sql, new ScalarHandler<Long>(),userid,wordid).intValue();


	}

	/**
	 * 删除用户单词筛选项
	 * @return 
	 */
	@Override
	public int deleteWordSelItem(String userid, String wordid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_word_userselect where userid=? and wordid=?";
		return qr.update(sql,userid,wordid);
		
	}


	/**
	 * 添加用户单词筛选项
	 * @return 
	 */
	@Override
	public int addWordSelItem(String id,String userid, String wordid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_word_userselect values(?,?,?)";
		return qr.update(sql,id,userid,wordid);
	}

}
