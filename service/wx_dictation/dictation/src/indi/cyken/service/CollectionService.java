package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;

public interface CollectionService {

	Collection findByUserid(String userid) throws Exception;

	void add(Collection collection) throws Exception;

	void delete(Collection collection) throws Exception;;





}
