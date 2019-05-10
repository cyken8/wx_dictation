package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.Course;

public interface CourseService {

	List<Course> getByUid(String unitid) throws Exception;

	BookLanguage getBookLangByCourseid(String courseId) throws Exception;

	int addOneCourse(Course course) throws Exception;

	int delOneCourse(String courseid)throws Exception;
}
