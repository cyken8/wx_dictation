package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.CategoryDao;
import indi.cyken.domain.Category;
import indi.cyken.service.CategoryService;
import indi.cyken.utils.BeanFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {

	/**
	 *  查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		//1.创建缓存管理器
		CacheManager cm=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		//3.通过缓存获取数据  将cache看成一个map即可
		Element element = cache.get("clist");
		
		List<Category> list=null;
		
		//4.判断数据
		if(element==null){
			//从数据库中获取
			CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
			list=cd.findAll();
			
			//将list放入缓存
			cache.put(new Element("clist", list));
			
			System.out.println("缓存中没有数据,已去数据库中获取");
		}else{
			//直接返回
			list=(List<Category>) element.getObjectValue();
			
			System.out.println("缓存中有数据");
		}
		
		return list;
	}
	


}
