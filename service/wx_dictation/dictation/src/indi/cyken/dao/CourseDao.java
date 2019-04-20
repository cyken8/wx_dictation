package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.Course;

public interface CourseDao {

	List<Course> getByUid(String unitid) throws Exception;

	BookLanguage getBookLangByCourseid(String courseId) throws Exception;

}
