package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.UnitDao;
import indi.cyken.domain.Unit;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;

public class UnitServiceImpl implements UnitService {

	
	/**
	 * 根据bookid获取单元列表
	 */
	@Override
	public List<Unit> getByBid(String bookid) throws Exception {
		UnitDao bdao=(UnitDao) BeanFactory.getBean("UnitDao");
		return bdao.getByBid(bookid);
	}

}
