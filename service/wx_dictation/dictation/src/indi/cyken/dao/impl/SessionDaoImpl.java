package indi.cyken.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import indi.cyken.dao.SessionDao;
import indi.cyken.domain.User;
import indi.cyken.domain.UserSession;
import indi.cyken.utils.DataSourceUtils;

public class SessionDaoImpl implements SessionDao {

	
	/**
	 * 添加登录态,存在openid则更新，不存在则插入
	 */
	@Override
	public int add(UserSession us) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_user_session(session_key,open_id,expire_date,client_key) values(?,?,?,?);";
		System.out.println("添加进数据库的client_key: "+ us.getClient_key());
		int ret = qr.update(sql,us.getSeesion_key(),
				us.getOpen_id(),
				us.getExpire_date(),
				us.getClient_key());
		return ret;
		
	}

	/**
	 * 更新登录态
	 */
	@Override
	public int update(UserSession us) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql="update t_user_session set session_key=?,expire_date=?,client_key=? where open_id=?;";
		System.out.println("更新进数据库的client_key: "+ us.getClient_key());
		System.out.println("更新进数据库的session_key: "+ us.getSeesion_key());
		System.out.println("更新进数据库的expire_date: "+ us.getExpire_date());

		int ret = qr.update(DataSourceUtils.getConnection(),sql,us.getSeesion_key(),
				us.getExpire_date(),
				us.getClient_key(),
				us.getOpen_id());
		return ret;
	}

	/**
	 * 根据session_key获取登录态
	 */
	@Override
	public UserSession getBySessionId(String sessionId) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_user_session where session_key = ? limit 1";
		return qr.query(sql, new BeanHandler<>(UserSession.class), sessionId);
	}

}
