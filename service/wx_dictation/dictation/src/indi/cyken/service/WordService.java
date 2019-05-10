package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Word;
import net.sf.json.JSONObject;

public interface WordService {

	List<Word> getWordsByUidCid(String userid, String courseid) throws Exception;

	int add(String wordid, String weid,String wordtext, String voiceurl, String lengtypeid, String cometypeid, String courseid,
			String userid) throws Exception;

	List<Word> getWordEx(String userid, String courseid) throws Exception;

	int addWordEx(String userid, List<Word> wordList) throws Exception;

	int delWordEx(List<String> wordIdList, String userid) throws Exception;

	List<Word> getWordsByCid(String courseid) throws Exception;

	int addWordStandard(List<Word> wlist) throws Exception;

	int delWordStandard(String wordid) throws Exception;

	List<Word> getWordStandardByCid(String courseid)throws Exception;





}
