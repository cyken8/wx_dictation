package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Word;

public interface WordWrongDao {

	int addWordWrong(String wwid,String wordid, String bookid, String userid) throws Exception;

	List<Word> getWrongWordByUidBid(String userid, String bookid) throws Exception;

}
