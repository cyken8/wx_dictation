package indi.cyken.service;

import indi.cyken.domain.User;

public interface UserService {

	void registTeacher(User user) throws Exception;

	User login(String username, String password)  throws Exception;

	User getByUid(String userid) throws Exception;

}
