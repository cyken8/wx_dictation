package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.HomeworkDao;
import indi.cyken.domain.Category;
import indi.cyken.domain.Course;
import indi.cyken.domain.Homework;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.utils.DataSourceUtils;

public class HomeworkDaoImpl implements HomeworkDao {

	/**
	 * 添加作业本
	 */
	@Override
	public int addHomework(String hwid, String hwname, String classid, Boolean hwstate) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_homework values(?,?,?,?);";
		int ret = qr.update(sql,hwid,hwname,classid,hwstate);
		return ret;
	}

	/**
	 * 添加作业本项
	 */
	@Override
	public int addHomeworkItem(String hwid, String wordid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_homework_item values(?,?);";
		int ret = qr.update(sql,hwid,wordid);
		return ret;
	}

	/**
	 * 获取用户的所有的作业本
	 */
	@Override
	public List<Homework> getAllHomeworkByUid(String classid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_homework where classid=?;";
		return qr.query(sql, new BeanListHandler<>(Homework.class),classid);
	}

	/**
	 * 获取作业下所有单词
	 */
	@Override
	public List<Word> getAllWordByHWid(String hwid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT * FROM t_word w,t_homework_item hi  WHERE hi.hwid=? AND hi.wordid=w.wordid";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), hwid);
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
	 * 删除一项作业
	 */
	@Override
	public int delOneHomeworkByHwid(String hwid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_homework where hwid=?";
		return qr.update(sql,hwid);
	}

}
