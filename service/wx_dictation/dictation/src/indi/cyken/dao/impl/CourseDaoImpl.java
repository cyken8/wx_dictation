package indi.cyken.dao.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
		String sql="select * from t_course c,t_unit u where c.unitid=? and c.unitid=u.unitid; ";
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

	/**
	 * 通过课时id获取课时语言
	 */
	@Override
	public BookLanguage getBookLangByCourseid(String courseId) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="SELECT b.langid,bl.langname FROM t_course c,t_unit u,t_book b,t_book_lang bl WHERE c.courseid=? AND c.unitid=u.unitid AND u.bookid=b.bookid AND b.langid=bl.langid;" ;
		BookLanguage language = qr.query(sql, new BeanHandler<>(BookLanguage.class), courseId);
		return language;

	}

	/**
	 * 添加一个课时
	 */
	@Override
	public int addOneCourse(Course course) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into t_course values(?,?,?);";
		int ret = qr.update(sql,course.getCourseid(),
				course.getCoursename(),
				course.getUnit().getUnitid());
		return ret;
	}

	/**
	 * 删除一个课时
	 */
	@Override
	public int delOneCourse(String courseid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from t_course where courseid=?";
		return qr.update(sql, courseid);
	}

}
