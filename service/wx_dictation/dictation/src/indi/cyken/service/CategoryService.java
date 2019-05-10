package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Category;



public interface CategoryService {

	List<Category> findAll () throws Exception;

	int AddOneCateogry(Category category) throws Exception;

	int delOneCategory(String cid)throws Exception;

	int delManyCategory(String[] cidList) throws Exception;


}
