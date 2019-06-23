package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.SClassDao;
import indi.cyken.domain.SClass;
import indi.cyken.service.SClassService;
import indi.cyken.utils.BeanFactory;
/**
 * 班级Service
 * @author Administrator
 *
 */
public class SClassServiceImpl implements SClassService {

	/**
	 * 根据用户（教师）编号获取用户拥有的所有班级
	 */
	@Override
	public List<SClass> getAllClassByUid(String userid) throws Exception {
		// TODO Auto-generated method stub
		SClassDao sd = (SClassDao) BeanFactory.getBean("SClassDao");
		return sd.getAllClassByUid(userid);
	}

}
