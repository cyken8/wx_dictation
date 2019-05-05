package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Homework;

public interface HomeworkDao {

	int addHomework(String hwid, String hwname, String userid, Boolean hwstate) throws Exception;

	int addHomeworkItem(String hwid, String wordid) throws Exception;

	List<Homework> getAllHomeworkByUid(String userid) throws Exception;

}
