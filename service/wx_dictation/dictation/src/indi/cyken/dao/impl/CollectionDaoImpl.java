package indi.cyken.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import indi.cyken.dao.CollectionDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.User;
import indi.cyken.utils.DataSourceUtils;

public class CollectionDaoImpl implements CollectionDao {

	/**
	 * 根据用户ID获取收藏
	 */
	@Override
	public Collection findByUserid(String userid) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_collection where userid = ?";
		Collection collection = qr.query(sql, new BeanHandler<>(Collection.class), userid);
		
		
		//查询Book
		sql="select distinct  * from t_collection_item ci,t_book b,t_book_category bc,t_book_lang bl,t_book_type bt,t_book_version bv where ci.bookid = b.bookid and ci.userid = ? and b.cid=bc.cid and b.langid=bl.langid and b.typeid=bt.typeid and b.versionid=bv.versionid;";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), userid);
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
			
			//封装CollectionItem
			CollectionItem ci=new CollectionItem();
			BeanUtils.populate(ci, map);
			ci.setBook(book);
			//将collectionitem介入order的items中
			collection.getItems().add(ci);
		}
		return collection;
	}

	/**
	 * 向Collection中插入一条记录
	 */
	@Override
	public void add(Collection collection) throws Exception {
		QueryRunner qr = new QueryRunner();
	
		//String sql="insert into t_collection values(?,?,?,?,?);";
//		qr.update(DataSourceUtils.getConnection(),sql,collection.getUser().getUserid(),
//				collection.getItem1(),
//				collection.getItem2(),
//				collection.getItem3(),
//				collection.getItem4());
		String sql="insert into t_collection(userid,item1,item2,item3,item4) select ?,?,?,?,? from DUAL where not exists(select * from t_collection where userid=?);";
		qr.update(DataSourceUtils.getConnection(),sql,collection.getUser().getUserid(),
				collection.getItem1(),
				collection.getItem2(),
				collection.getItem3(),
				collection.getItem4(),
				collection.getUser().getUserid());
		
	}

	/**
	 * 向CollectionItem中插入多条记录
	 */
	@Override
	public void addItem(CollectionItem ci) throws Exception {
		QueryRunner qr = new QueryRunner();
//		String sql="insert into t_collection_item values(?,?,?,?);";
//		qr.update(DataSourceUtils.getConnection(),sql, ci.getItemid(),
//				ci.getBook().getBookid(),
//				ci.getCollection().getUser().getUserid(),
//				ci.getState());
		
		String sql="insert into t_collection_item(itemid,bookid,userid,state) select ?,?,?,? from DUAL where not exists(select * from t_collection_item where userid=? and bookid=?);";
		qr.update(DataSourceUtils.getConnection(),sql, ci.getItemid(),
				ci.getBook().getBookid(),
				ci.getCollection().getUser().getUserid(),
				ci.getState(),
				ci.getCollection().getUser().getUserid(),
				ci.getBook().getBookid());
		
	}

	/**
	 * 删除用户的收藏项
	 */
	@Override
	public void delete(CollectionItem ci) throws Exception {
		QueryRunner qr = new QueryRunner();
		//String sql="update orders set state=?,address=?,name=?,telephone=? where oid=?";
		//qr.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());

		String sql="delete from t_collection_item where userid=? and bookid=?;";
		int line=qr.update(DataSourceUtils.getConnection(),sql,ci.getCollection().getUser().getUserid(),
				ci.getBook().getBookid());
		System.out.println("删除了t_collection_item第几行："+line);
		
	}

	/**
	 * 通过bookid和sessionid来获取收藏项
	 */
	@Override
	public CollectionItem findByBidAndUid(String bookid, String userid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from t_collection_item where bookid=? and userid=?;";
		return qr.query(sql, new BeanHandler<>(CollectionItem.class), bookid,userid);


	}


	

}
