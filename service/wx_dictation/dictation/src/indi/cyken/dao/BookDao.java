package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Book;

/**
 * 课本实体Dao
 * @author Yong
 *
 */
public interface BookDao  {

	List<Book> findAll()  throws Exception;

	Book getByPid(String bid) throws Exception;
	
	List<Book> findByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;




}
