package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Homework;
import indi.cyken.domain.Word;

public interface HomeworkService {

	int addHomework(String hwid,String hwname,String classid,  List<String> list) throws Exception;

	List<Homework> getAllHomeworkByUid(String classid) throws Exception;

	List<Word> getAllWordByHWid(String hwid) throws Exception;

	int delOneHomeworkByHwid(String hwid) throws Exception;
}
