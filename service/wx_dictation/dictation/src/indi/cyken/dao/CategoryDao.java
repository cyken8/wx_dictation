package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Category;


public interface CategoryDao {

	List<Category> findAll () throws Exception;

}
