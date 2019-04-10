package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Category;



public interface CategoryService {

	List<Category> findAll () throws Exception;

}
