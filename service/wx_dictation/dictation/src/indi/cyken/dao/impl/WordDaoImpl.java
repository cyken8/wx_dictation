package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.WordDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.Course;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.utils.DataSourceUtils;

public class WordDaoImpl implements WordDao {

	/**
	 * 根据用户id和课时id获取课时下所有单词
	 */
	@Override
	public List<Word> getWordsByUidCid(String userid, String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_word w1  WHERE w1.cometypeid= ? AND w1.courseid=? \r\n" + 
				"UNION \r\n" + 
				"SELECT w2.wordid,w2.wordtext,w2.voiceurl,w2.lengtypeid,w2.cometypeid,w2.courseid FROM t_word w2,t_word_ex we WHERE  we.userid=? AND we.wordid=w2.wordid AND w2.courseid=?; ";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), DBTableField.WORD_COMETYPE_STANDARD,courseid,userid,courseid);
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
	 * 添加用户自定义单词
	 */
	@Override
	public int addWord(String wordid, String wordtext, String voiceurl, String lengtypeid, String cometypeid,
			String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_word values(?,?,?,?,?,?);";
		int ret = qr.update(sql,wordid,wordtext,voiceurl,lengtypeid,cometypeid,courseid);
		return ret;
		
	}

	/**
	 * 添加自定义单词，用户映射
	 */
	@Override
	public int addWordExUserMap(String weid, String wordid, String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_word_ex values(?,?,?);";
		int ret = qr.update(sql,weid,userid,wordid);
		return ret;
	}

	/**
	 * 根据用户id和课时id获取课时下所有的自定义单词
	 */
	@Override
	public List<Word> getWordEx(String userid, String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT w2.wordid,w2.wordtext,w2.voiceurl,w2.lengtypeid,w2.cometypeid,w2.courseid FROM t_word w2,t_word_ex we WHERE  we.userid=? AND w2.courseid=? AND we.wordid=w2.wordid ; ";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),userid,courseid);
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
	 * 删除用户自定义单词
	 */
	@Override
	public void delWordEx(String wordid, String userid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_word_ex where wordid=? and userid=?";
		qr.update(sql, wordid,userid);
		
	}

	
	/**
	 * 根据课时获取所有标准单词
	 */
	@Override
	public List<Word> getWordsByCid(String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_word  WHERE cometypeid= ? AND courseid=?";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), DBTableField.WORD_COMETYPE_STANDARD,courseid);
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
	 * 添加标准单词
	 */
	@Override
	public int addWordStandard(Word word) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_word values(?,?,?,?,?,?);";
		int ret = qr.update(sql,word.getWordid(),
				word.getWordtext(),
				word.getVoiceurl(),
				word.getLengtype().getLengtypeid(),
				word.getCometype().getCometypeid(),
				word.getCourse().getCourseid());
		return ret;
	}

	/**
	 * 删除标准单词
	 */
	@Override
	public int delWordStandard(String wordid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_word where wordid=?";
		return qr.update(sql, wordid);
	}

	/**
	 * 根据课时编号获取标准单词
	 */
	@Override
	public List<Word> getWordStandardByCid(String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_word w1  WHERE w1.cometypeid= ? AND w1.courseid=?" ;
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), DBTableField.WORD_COMETYPE_STANDARD,courseid);
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
	



	


	



}
