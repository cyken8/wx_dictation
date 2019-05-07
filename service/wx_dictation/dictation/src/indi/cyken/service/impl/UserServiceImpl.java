package indi.cyken.service.impl;

import java.util.List;


import indi.cyken.dao.UserDao;
import indi.cyken.dao.impl.UserDaoImpl;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;

public class UserServiceImpl implements UserService {



	
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

	/**
	 * 根据用户id和密码查询用户是否存在
	 */
	@Override
	public UserTwo queryByUidAndPass(String userid, String password) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.queryByUidAndPass(userid,password);
	}

	/**
	 * 根据用户id查询用户具体信息
	 */
	@Override
	public UserTwo getUserInfoByUid(String userid) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.getUserInfoByUid(userid);
	}

	/**
	 * 获取所有用户信息
	 */
	@Override
	public List<User> getAllUser() throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.getAllUser();
	}


	@Override
	public User login(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 根据用户id 删除一个用户
	 */
	@Override
	public int delOneUserByUid(String userid) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.delOneUserByUid(userid);
	}

	
}
