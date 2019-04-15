package indi.cyken.service.impl;

import indi.cyken.dao.SessionDao;
import indi.cyken.dao.UserDao;
import indi.cyken.dao.impl.UserDaoImpl;
import indi.cyken.domain.User;

import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;

public class UserServiceImpl implements UserService {

	/**
	 * 注册教师身份
	 */
	@Override
	public void registTeacher(User user)  throws Exception{
		UserDao dao=new UserDaoImpl();
		int ret=dao.registTeacher(user);

	}

	/**
	 * 教师网页版登录
	 */
	@Override
	public User login(String username, String password) throws Exception {
		UserDao dao=new UserDaoImpl();
		return dao.getByUsernameAndPwd(username,password);
	}
	
	/**
	 * 通过用户id查询用户信息
	 */
	@Override
	public User getByUid(String userid) throws Exception {
		UserDao dao=new UserDaoImpl();
		return dao.getByUid(userid);
	}

	
	/**
	 * 通过openid查询用户
	 */
	@Override
	public User getByOpenId(String openid) throws Exception {
		UserDao dao=new UserDaoImpl();
		return dao.getByOpenId(openid);
	}

	/**
	 * 注册用户
	 */
	@Override
	public int add(User user) throws Exception {
		UserDao dao=new UserDaoImpl();
		return dao.add(user);
	}

	/**
	 * 根据session_key获取用户
	 */
	@Override
	public User getUserBySessionId(String sessionId) throws Exception {
		
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.getUserBySessionId(sessionId);
	}

	
}
