package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Course;

public interface CourseService {

	List<Course> getByUid(String unitid) throws Exception;

}
