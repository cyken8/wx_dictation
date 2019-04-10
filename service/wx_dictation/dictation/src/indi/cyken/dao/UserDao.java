package indi.cyken.dao;

import indi.cyken.domain.User;

public interface UserDao {

	void add(User user) throws Exception;

	User getByUsernameAndPwd(String username, String password) throws Exception;

	User getByUid(String userid) throws Exception;
}
