package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Homework;
import indi.cyken.domain.Word;

public interface HomeworkDao {

	int addHomework(String hwid, String hwname, String classid, Boolean hwstate) throws Exception;

	int addHomeworkItem(String hwid, String wordid) throws Exception;

	List<Homework> getAllHomeworkByUid(String classid) throws Exception;

	List<Word> getAllWordByHWid(String hwid) throws Exception;

	int delOneHomeworkByHwid(String hwid) throws Exception;

}
