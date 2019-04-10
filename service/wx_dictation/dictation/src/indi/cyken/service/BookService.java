package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.PageBean;

public interface BookService {

	List<Book> findAll() throws Exception;

	Book getByBid(String bid) throws Exception;

	PageBean<Book> findByPage(int currPage, int pageSize, String cid) throws Exception;


}
