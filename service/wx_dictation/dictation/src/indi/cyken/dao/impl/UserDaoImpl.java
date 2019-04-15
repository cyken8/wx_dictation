package indi.cyken.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.dao.UserDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.User;
import indi.cyken.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * 注册教师省份
	 * @return 
	 * @throws SQLException 
	 */
	@Override
	public int registTeacher(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		System.out.println("QueryRuner: "+qr);
		String sql="insert into t_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ret = qr.update(sql,user.getUserid(),
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
				user.getCityid(),
				user.getOpen_id());
		
		return ret;

		
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

	/**
	 * 通过openid来查询用户
	 */
	@Override
	public User getByOpenId(String openid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user where open_id = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), openid);
	}

	/**
	 * 注册用户
	 */
	@Override
	public int add(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ret = qr.update(sql,user.getUserid(),
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
				user.getCityid(),
				user.getOpen_id());
		
		return ret;
	}

	/**
	 * 根据session_key获取用户
	 */
	@Override
	public User getUserBySessionId(String sessionId) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user u,t_user_session s where s.client_key = ? and s.open_id=u.open_id;";
		Map<String, Object> query = qr.query(sql, new MapHandler(), sessionId);
		User user=new User();
		
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		BeanUtils.populate(user, query);
		System.out.println("根据client_key查询出的查询出的用户:"+user.getUserid());
		return user;
	}

	
	
}
