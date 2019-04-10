package indi.cyken.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import indi.cyken.dao.BookDao;
import indi.cyken.domain.Book;
import indi.cyken.utils.DataSourceUtils;

public class BookDaoImpl implements BookDao {

	/**
	 * 查询所有课本
	 */
	@Override
	public List<Book> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_book";
		return qr.query(sql, new BeanListHandler<>(Book.class));
	}

	/**
	 * 查询单个课本
	 */
	@Override
	public Book getByPid(String bid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql="select * from t_book where BOOKID = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Book.class), bid);
	}
	
	/**
	 * 查询当前类别的总条数
	 */
	@Override
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from t_book where BOOKCATEGORYID = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}

	@Override
	public List<Book> findByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_book where BOOKCATEGORYID = ? limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Book.class), cid,(currPage-1)*pageSize,pageSize);
	}




	

}
