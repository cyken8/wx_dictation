package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Word;
import net.sf.json.JSONObject;

public interface WordSelectedService {
	
	List<Word> getSelectedWordsByUidCid(String userid, String courseid) throws Exception;

	int updateWordSelState(String userid, List<JSONObject> list) throws Exception;
}
