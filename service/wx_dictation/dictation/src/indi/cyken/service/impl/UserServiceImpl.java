package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.constant.DBTableField;
import indi.cyken.dao.UserDao;
import indi.cyken.dao.impl.UserDaoImpl;
import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;

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
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			UserDao dao=new UserDaoImpl();
			int ret=dao.add(user);
			//如果是教师身份并且添加用户成功，则给教师添加一个班级信息
			if(user.getRole().getRoleid().equals(DBTableField.USER_ROLE_TEACHER)&&ret>0) {
				ret = addTeacherClass(user);
			}
	
			//4.事务处理
			DataSourceUtils.commitAndClose();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}		
		
		
		
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
	public User queryByUidAndPass(String userid, String password) throws Exception {
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


	/**
	 * 根据班级id获取班级下所有学生身份的用户
	 */
	@Override
	public List<User> getAllStudentByClid(String classid) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.getAllStudentByClid(classid);
	}


	/**
	 * 添加教师拥有的班级
	 */
	@Override
	public int addTeacherClass(User user) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		// TODO Auto-generated method stub
		return ud.addTeacherClass(user);
	}

	
}
