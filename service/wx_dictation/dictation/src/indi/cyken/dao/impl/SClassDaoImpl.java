package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.dao.SClassDao;
import indi.cyken.domain.SClass;
import indi.cyken.utils.DataSourceUtils;

public class SClassDaoImpl implements SClassDao {

	/**
	 * 根据用户ID获取用户拥有的所有班级
	 */
	@Override
	public List<SClass> getAllClassByUid(String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_class c,t_user_class uc where uc.userid=? and uc.classid=c.classid";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), userid);
		List<SClass> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			//封装Book
			SClass sclass=new SClass();
			BeanUtils.populate(sclass, map);
			
			list.add(sclass);
		}
		return list;
	}

}
