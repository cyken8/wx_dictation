package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.SClass;

/**
 * 
 * @author 班级Dao
 *
 */
public interface SClassDao {

	List<SClass> getAllClassByUid(String userid) throws Exception;


}
