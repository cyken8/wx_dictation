package indi.cyken.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import indi.cyken.dao.CategoryDao;
import indi.cyken.domain.Category;
import indi.cyken.utils.DataSourceUtils;



public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_book_category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

}
