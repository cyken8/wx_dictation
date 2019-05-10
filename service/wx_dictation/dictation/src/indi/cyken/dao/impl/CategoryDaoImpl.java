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

	
	@Override
	public int addOneCategory(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_book_category values(?,?);";
		int ret = qr.update(sql,category.getCid(),category.getCname());
		return ret;
	}


	@Override
	public int delOneCategory(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_book_category where cid=?;";
		int ret = qr.update(sql,cid);
		return ret;
	}

}
