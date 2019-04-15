package indi.cyken.service;

import java.util.List;

import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;

public interface CollectionService {

	Collection findByUserid(String userid) throws Exception;

	void add(Collection collection) throws Exception;

	void delete(Collection collection) throws Exception;

	CollectionItem findByBidAndSid(String bookid, String sessionid) throws Exception;

	CollectionItem findByBidAndUid(String userid, String bookid) throws Exception;





}
