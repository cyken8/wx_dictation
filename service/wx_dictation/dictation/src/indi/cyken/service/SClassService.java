package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.SClass;

public interface SClassService {

	List<SClass> getAllClassByUid(String userid) throws Exception;

}
