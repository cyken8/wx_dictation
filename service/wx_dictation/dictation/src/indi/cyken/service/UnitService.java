package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Unit;

public interface UnitService {

	List<Unit> getByBid(String bookid) throws Exception;

}
