package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Word;

public interface WordDao {

	List<Word> getWordsByUidCid(String userid, String courseid) throws Exception;

	int addWord(String wordid, String wordtext, String voiceurl, String lengtypeid, String cometypeid, String courseid) throws Exception;

	int addWordExUserMap(String weid, String wordid, String userid) throws Exception;

	List<Word> getWordEx(String userid, String courseid) throws Exception;

	void delWordEx(String wordid, String userid) throws Exception;

	List<Word> getWordsByCid(String courseid) throws Exception;

	int addWordStandard(Word word) throws Exception;

	int delWordStandard(String wordid) throws Exception;

	List<Word> getWordStandardByCid(String courseid)throws Exception;


}
