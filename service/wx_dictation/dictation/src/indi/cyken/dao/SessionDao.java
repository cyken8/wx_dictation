package indi.cyken.dao;

import indi.cyken.domain.User;
import indi.cyken.domain.UserSession;

public interface SessionDao {

	int add(UserSession us) throws Exception;

	int update(UserSession us)throws Exception;

	UserSession getBySessionId(String sessionId) throws Exception;

	

}
