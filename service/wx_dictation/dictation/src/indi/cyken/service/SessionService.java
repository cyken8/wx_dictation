package indi.cyken.service;


import indi.cyken.domain.UserSession;

public interface SessionService {

	int add(UserSession us) throws Exception;

	int update(UserSession us) throws Exception;


}
