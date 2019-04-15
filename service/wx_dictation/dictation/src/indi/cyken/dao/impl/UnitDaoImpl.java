package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;


import indi.cyken.dao.UnitDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.Course;
import indi.cyken.domain.Unit;
import indi.cyken.utils.DataSourceUtils;

public class UnitDaoImpl implements UnitDao {

	
	/**
	 * 根据bookid获取单元列表
	 */
	@Override
	public List<Unit> getByBid(String bookid) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//获取课本下的所有单元
		String sql="select * from t_unit where bookid=?";
		List<Unit> list=qr.query(sql, new BeanListHandler<>(Unit.class),bookid);
		List<Unit> retlist=new LinkedList<>();
		for(Unit item:list) {
			sql="select distinct  * from t_course c,t_unit u,t_book b where c.unitid=? and c.unitid=u.unitid and u.bookid=b.bookid";
			List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), item.getUnitid());
			Unit unit=new Unit();
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
				
				
				BeanUtils.populate(unit, map);
				unit.setBook(book);

				Course course=new Course();
				BeanUtils.populate(course, map);
				course.setUnit(unit);
				
				unit.getCourselist().add(course);

			}
			retlist.add(unit);
			
		}
		return retlist;

		
	}

}
