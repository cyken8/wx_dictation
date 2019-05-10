package indi.cyken.service.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import indi.cyken.dao.UnitDao;
import indi.cyken.domain.Unit;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;

public class UnitServiceImpl implements UnitService {

	
	/**
	 * 根据bookid获取单元列表（包含课时信息）
	 */
	@Override
	public List<Unit> getByBid(String bookid) throws Exception {
		UnitDao bdao=(UnitDao) BeanFactory.getBean("UnitDao");
		return bdao.getByBid(bookid);
	}

	/**
	 * 添加一个单元
	 */
	@Override
	public int addOneUnit(Unit unit) throws Exception {
		UnitDao bdao=(UnitDao) BeanFactory.getBean("UnitDao");
		return bdao.addOneUnit(unit);
	}

	/**
	 * 获取课本下的单元ID和单元名称
	 */
	@Override
	public List<Unit> getUnitIdAndNameByBid(String bookid) throws Exception {
		UnitDao bdao=(UnitDao) BeanFactory.getBean("UnitDao");
		return bdao.getUnitIdAndNameByBid(bookid);
	}

	/**
	 * 删除一个单元
	 */
	@Override
	public int delOneUnit(String unitid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_unit where unitid=?";
		return qr.update(sql, unitid);
	}

	/**
	 * 网页端获取课本下的单元信息（仅仅包含单元信息，不包含课时信息）
	 */
	@Override
	public List<Unit> getByBidForWeb(String bookid) throws Exception {
		UnitDao bdao=(UnitDao) BeanFactory.getBean("UnitDao");
		return bdao.getByBidForWeb(bookid);
	}

}
