package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.CategoryDao;
import indi.cyken.dao.WordDao;
import indi.cyken.domain.Category;
import indi.cyken.domain.Word;
import indi.cyken.service.CategoryService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;


public class CategoryServiceImpl implements CategoryService {

	/**
	 *  查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {

		CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findAll();

	}

	/**
	 * 添加一个课本分类
	 */
	@Override
	public int AddOneCateogry(Category category) throws Exception {
		CategoryDao wd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return wd.addOneCategory(category);
	}

	/**
	 * 删除一个课本分类
	 */
	@Override
	public int delOneCategory(String cid) throws Exception {
		CategoryDao wd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return wd.delOneCategory(cid);
	}

	/**
	 * 删除多个课本分类
	 */
	@Override
	public int delManyCategory(String[] cidList) throws Exception {
		try {
			
			CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
			
			//1.开启事务
			DataSourceUtils.startTransaction();
		int ret=0;
		for(int i=0;i<cidList.length;i++) {
			String cid=cidList[i];
			cd.delOneCategory(cid);
			ret++;
		}
		return ret;
		}catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	


}
