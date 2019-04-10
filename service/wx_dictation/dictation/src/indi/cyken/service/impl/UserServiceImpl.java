package indi.cyken.service.impl;

import indi.cyken.dao.UserDao;
import indi.cyken.dao.impl.UserDaoImpl;
import indi.cyken.domain.User;
import indi.cyken.service.UserService;

public class UserServiceImpl implements UserService {

	/**
	 * 用户注册
	 */
	@Override
	public void registTeacher(User user)  throws Exception{
		UserDao dao=new UserDaoImpl();
		dao.add(user);

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
}
