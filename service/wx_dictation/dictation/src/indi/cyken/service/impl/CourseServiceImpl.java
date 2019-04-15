package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.CourseDao;
import indi.cyken.domain.Course;
import indi.cyken.service.CourseService;
import indi.cyken.utils.BeanFactory;

public class CourseServiceImpl implements CourseService {

	
	/**
	 * 根据unitid查询所有的课时
	 */
	@Override
	public List<Course> getByUid(String unitid) throws Exception {
		CourseDao cdao=(CourseDao) BeanFactory.getBean("CourseDao");
		return cdao.getByUid(unitid);
	}

}
