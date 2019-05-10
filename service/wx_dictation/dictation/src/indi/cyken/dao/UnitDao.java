package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Unit;

public interface UnitDao {

	List<Unit> getByBid(String bookid) throws Exception;

	int addOneUnit(Unit unit) throws Exception;

	List<Unit> getUnitIdAndNameByBid(String bookid) throws Exception;

	List<Unit> getByBidForWeb(String bookid)throws Exception;

}
