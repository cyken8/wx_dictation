package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;

public interface UserDao {

	

	User getByUsernameAndPwd(String username, String password) throws Exception;

	User getByUid(String userid) throws Exception;

	User getByOpenId(String openid) throws Exception;

	int add(User user) throws Exception;

	User getUserBySessionId(String sessionId) throws Exception;

	User queryByUidAndPass(String userid, String password) throws Exception;

	UserTwo getUserInfoByUid(String userid) throws Exception;

	List<User> getAllUser()throws Exception;

	int delOneUserByUid(String userid) throws Exception;

	List<User> getAllStudentByClid(String classid) throws Exception;

	int addTeacherClass(User user) throws Exception;


}
