package indi.cyken.dao.impl;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import indi.cyken.dao.UserDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.User;
import indi.cyken.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		System.out.println("QueryRuner: "+qr);
		String sql="insert into t_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql,user.getUserid(),
				user.getUsername(),
				user.getPassword(),
				user.getAvatar(),
				user.getState(),
				user.getUsertypeid(),
				user.getOrgid(),
				user.getGradeid(),
				user.getClassid(),
				user.getBirthday(),
				user.getSex(),
				user.getEmail(),
				user.getProvinceid(),
				user.getCityid());
		

		
	}

	/**
	 * 教师网页登录
	 * 
	 */
	@Override
	public User getByUsernameAndPwd(String username, String password)  throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

	
	/**
	 * 通过用户id查询用户信息
	 */
	@Override
	public User getByUid(String userid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where userid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), userid);
	}
	
}
