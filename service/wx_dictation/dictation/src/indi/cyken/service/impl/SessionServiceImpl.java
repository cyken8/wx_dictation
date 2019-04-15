package indi.cyken.service.impl;

import indi.cyken.dao.SessionDao;
import indi.cyken.domain.User;
import indi.cyken.domain.UserSession;
import indi.cyken.service.SessionService;
import indi.cyken.utils.BeanFactory;

public class SessionServiceImpl implements SessionService {

	
	/**
	 * 添加登录态
	 */
	@Override
	public int add(UserSession us) throws Exception {
		
        SessionDao sd=(SessionDao) BeanFactory.getBean("SessionDao");
        int ret = sd.add(us);
        return ret;
		
	}

	@Override
	public int update(UserSession us) throws Exception {
		
        SessionDao sd=(SessionDao) BeanFactory.getBean("SessionDao");
        int ret = sd.update(us);
        return ret;
	}

	

}
