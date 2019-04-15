package indi.cyken.dao;

import java.util.List;

import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;

public interface CollectionDao {

	Collection findByUserid(String userid) throws Exception;

	void add(Collection collection) throws Exception;

	void addItem(CollectionItem oi) throws Exception;

	void delete(CollectionItem ci) throws Exception;

	CollectionItem findByBidAndUid(String bookid, String userid)throws Exception;

	

}
