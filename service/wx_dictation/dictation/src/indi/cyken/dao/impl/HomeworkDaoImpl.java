package indi.cyken.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import indi.cyken.dao.HomeworkDao;
import indi.cyken.domain.Category;
import indi.cyken.domain.Homework;
import indi.cyken.utils.DataSourceUtils;

public class HomeworkDaoImpl implements HomeworkDao {

	/**
	 * 添加作业本
	 */
	@Override
	public int addHomework(String hwid, String hwname, String userid, Boolean hwstate) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_homework values(?,?,?,?);";
		int ret = qr.update(sql,hwid,hwname,userid,hwstate);
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
	public List<Homework> getAllHomeworkByUid(String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_homework where userid=?;";
		return qr.query(sql, new BeanListHandler<>(Homework.class),userid);
	}

}
