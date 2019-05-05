package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Homework;

public interface HomeworkService {

	int addHomework(String hwid,String hwname,String userid,  List<String> list) throws Exception;

	List<Homework> getAllHomeworkByUid(String userid) throws Exception;

}
