package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.WordWrongDao;
import indi.cyken.domain.Course;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.utils.DataSourceUtils;

public class WordWrongDaoImpl implements WordWrongDao{

	/**
	 * 添加错字集项
	 */
	@Override
	public int addWordWrong(String wwid,String wordid, String bookid, String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_word_wrong values(?,?,?,?);";
		int ret = qr.update(sql,wwid,userid,bookid,wordid);
		return ret;
	}

	/**
	 * 获取错字集
	 */
	@Override
	public List<Word> getWrongWordByUidBid(String userid, String bookid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT w.wordid,w.wordtext,w.voiceurl,w.lengtypeid,w.cometypeid,w.courseid FROM t_word_wrong ww,t_word w\r\n" + 
				"WHERE ww.userid=? AND ww.bookid=? AND ww.wordid=w.wordid";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),userid,bookid);
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
