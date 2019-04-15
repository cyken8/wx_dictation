package indi.cyken.dao;

import indi.cyken.domain.User;

public interface UserDao {

	int registTeacher(User user) throws Exception;

	User getByUsernameAndPwd(String username, String password) throws Exception;

	User getByUid(String userid) throws Exception;

	User getByOpenId(String openid) throws Exception;

	int add(User user) throws Exception;

	User getUserBySessionId(String sessionId) throws Exception;


}
