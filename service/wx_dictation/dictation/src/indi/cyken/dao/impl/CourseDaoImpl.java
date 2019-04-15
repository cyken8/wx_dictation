package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.dao.CourseDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.Course;
import indi.cyken.domain.Unit;
import indi.cyken.utils.DataSourceUtils;

public class CourseDaoImpl implements CourseDao {

	/**
	 * 根据unitid查询所有的课时
	 */
	@Override
	public List<Course> getByUid(String unitid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_course c,t_unit u where unitid=? and c.unitid=u.unitid; ";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), unitid);

		List<Course> list=new LinkedList<>();
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
			
			Unit unit=new Unit();
			BeanUtils.populate(unit, map);
			unit.setBook(book);
			
			Course course=new Course();
			BeanUtils.populate(course, map);
			course.setUnit(unit);

			list.add(course);

		}
		return list;
	}

}
