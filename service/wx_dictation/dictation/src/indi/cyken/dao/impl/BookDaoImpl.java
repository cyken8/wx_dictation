package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import indi.cyken.dao.BookDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.CollectionItem;
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

	/**
	 * 根据课本分类CategoryId来获取分类下的所有我看吧
	 */
	@Override
	public List<Book> getByCategoryId(String categoryid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_book_version v,t_book_lang l,t_book_type t,t_book_category c,t_book b where b.versionid=v.versionid and b.langid=l.langid and b.typeid=t.typeid and b.cid=c.cid and  b.cid=?";
		
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), categoryid);
		List<Book> list=new LinkedList<>();
		for (Map<String, Object> map : query) {
			//封装Book
			BookVersion version=new BookVersion();
			BeanUtils.populate(version, map);
			BookLanguage language=new BookLanguage();
			BeanUtils.populate(language, map);
			Category category=new Category();
			BeanUtils.populate(category, map);
			BookType booktype=new BookType();
			BeanUtils.populate(booktype, map);

			Book book=new Book();
			BeanUtils.populate(book, map);
			book.setVersion(version);
			book.setLanguage(language);
			book.setBookCategory(category);
			book.setBooktype(booktype);
			
			list.add(book);
		}
		return list;
	}




	

}
