package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.User;
import indi.cyken.domain.UserTwo;

public interface UserService {

	

	User login(String username, String password)  throws Exception;

	User getByUid(String userid) throws Exception;

	User getByOpenId(String openid) throws Exception;

	int add(User user) throws Exception;

	User getUserBySessionId(String sessionId) throws Exception;

	UserTwo queryByUidAndPass(String userid, String password) throws Exception;

	UserTwo getUserInfoByUid(String userid) throws Exception;

	List<User> getAllUser()throws Exception;

	int delOneUserByUid(String userid)throws Exception;

	

}
