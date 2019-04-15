package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Word;
import net.sf.json.JSONObject;

public interface WordService {

	List<Word> getWordsByUidCid(String userid, String courseid) throws Exception;

//	List<Word> getSelectedWordsByUidCid(String userid, String courseid) throws Exception;
//
//	int updateWordSelState(String userid, List<JSONObject> list) throws Exception;

}
