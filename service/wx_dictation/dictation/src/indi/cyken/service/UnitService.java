package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Unit;

public interface UnitService {

	List<Unit> getByBid(String bookid) throws Exception;

	int addOneUnit(Unit unit) throws Exception;

	List<Unit> getUnitIdAndNameByBid(String bookid) throws Exception;

	int delOneUnit(String unitid)throws Exception;

	List<Unit> getByBidForWeb(String bookid) throws Exception;

} 
