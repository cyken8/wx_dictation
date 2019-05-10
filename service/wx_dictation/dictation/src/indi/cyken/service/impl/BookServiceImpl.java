package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.BookDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.PageBean;
import indi.cyken.service.BookService;
import indi.cyken.utils.BeanFactory;

public class BookServiceImpl implements BookService {

	
	/**
	 *  查询所有课本
	 */
	@Override
	public List<Book> findAll() throws Exception {
		BookDao bdao=(BookDao) BeanFactory.getBean("BookDao");
		return bdao.findAll();
	}

	@Override
	public Book getByBid(String bid) throws Exception {
		BookDao bdao=(BookDao) BeanFactory.getBean("BookDao");
		return bdao.getByPid(bid);
	}

	/**
	 * 按类别分页查询课本
	 */
	@Override
	public PageBean<Book> findByPage(int currPage, int pageSize, String cid) throws Exception {
		BookDao pdao=(BookDao) BeanFactory.getBean("BookDao");
		//当前页数据
		List<Book> list=pdao.findByPage(currPage,pageSize,cid);
		
		//总条数
		int totalCount = pdao.getTotalCount(cid);
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

	
	/**
	 * 根据课本分类CategoryId来获取分类下的所有我看吧
	 */
	@Override
	public List<Book> getByCategoryId(String categoryid) throws Exception {
		BookDao bdao=(BookDao) BeanFactory.getBean("BookDao");
		return bdao.getByCategoryId(categoryid);
	}

	/**
	 * 管理员添加一本课本
	 */
	@Override
	public int addOneBook(Book book) throws Exception {
		BookDao bdao=(BookDao) BeanFactory.getBean("BookDao");
		return bdao.addOneBook(book);
	}

	/**
	 * 管理员删除一本课本
	 */
	@Override
	public int delOneBook(String bookid) throws Exception {
		BookDao bdao=(BookDao) BeanFactory.getBean("BookDao");
		return bdao.delOneBook(bookid);
	}

	
	

}
