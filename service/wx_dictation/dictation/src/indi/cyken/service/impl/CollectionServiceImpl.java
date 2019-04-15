package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.BookDao;
import indi.cyken.dao.CollectionDao;
import indi.cyken.dao.UserDao;
import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.User;
import indi.cyken.service.CollectionService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;

public class CollectionServiceImpl implements CollectionService {

	/**
	 * 根据用户ID获取收藏
	 */
	@Override
	public Collection findByUserid(String userid) throws Exception {
		CollectionDao cd=(CollectionDao) BeanFactory.getBean("CollectionDao");
		return cd.findByUserid(userid);
	}

	/**
	 * 添加收藏
	 */
	@Override
	public void add(Collection collection) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			CollectionDao cd=(CollectionDao) BeanFactory.getBean("CollectionDao");
			//2.向Collection表中添加一个数据
			cd.add(collection);			
			//3.向CollectionItem中添加n条数据
			for (CollectionItem ci : collection.getItems()) {
				cd.addItem(ci);
			}
			
			//4.事务处理
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}

	/**
	 * 删除收藏项
	 */
	@Override
	public void delete(Collection collection) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			CollectionDao cd=(CollectionDao) BeanFactory.getBean("CollectionDao");
			//TODO:更新Collection表的数据，保留
					
			//3.从CollectionItem中删除n条数据
			for (CollectionItem ci : collection.getItems()) {
				cd.delete(ci);
			}
			
			//4.事务处理
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}

	/**
	 * 通过bookid和sessionid来获取收藏项
	 */
	@Override
	public CollectionItem findByBidAndSid(String bookid, String sessionid) throws Exception {
		//1.根据sessionid获取userid
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		User user = ud.getUserBySessionId(sessionid);
		if(user!=null&& user.getUserid().length()>0) {
			CollectionDao cd=(CollectionDao) BeanFactory.getBean("CollectionDao");
			return cd.findByBidAndUid(bookid,user.getUserid());
		}else {
			return null;
		}
		
	}

	/**
	 * 通过bookid和userid来获取收藏项
	 */
	@Override
	public CollectionItem findByBidAndUid(String userid, String bookid) throws Exception {
		CollectionDao cd=(CollectionDao) BeanFactory.getBean("CollectionDao");
		return cd.findByBidAndUid(bookid,userid);
	}
	
	


	

}
