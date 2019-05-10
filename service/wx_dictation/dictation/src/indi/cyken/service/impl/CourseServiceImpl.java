package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.CourseDao;
import indi.cyken.domain.BookLanguage;
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

	/**
	 * 通过课时id获取课时语言
	 */
	@Override
	public BookLanguage getBookLangByCourseid(String courseId) throws Exception {
		CourseDao cdao=(CourseDao) BeanFactory.getBean("CourseDao");
		return cdao.getBookLangByCourseid(courseId);
	}

	/**
	 * 添加一个课时
	 */
	@Override
	public int addOneCourse(Course course) throws Exception {
		CourseDao cdao=(CourseDao) BeanFactory.getBean("CourseDao");
		return cdao.addOneCourse(course);
	}

	/**
	 * 删除一个课时
	 */
	@Override
	public int delOneCourse(String courseid) throws Exception {
		CourseDao cdao=(CourseDao) BeanFactory.getBean("CourseDao");
		return cdao.delOneCourse(courseid);
	}

}
