package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.CollectionDao;
import indi.cyken.dao.WordDao;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.Word;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;
import indi.cyken.utils.UUIDUtils;
import net.sf.json.JSONObject;

public class WordServiceImpl implements WordService {

	/**
	 * 根据用户id和课时id获取课时下所有单词
	 */
	@Override
	public List<Word> getWordsByUidCid(String userid, String courseid) throws Exception {
		WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
		return wd.getWordsByUidCid(userid, courseid);

	}

	/**
	 * 获取该课时下用户筛选的单词
	 */
//	@Override
//	public List<Word> getSelectedWordsByUidCid(String userid, String courseid) throws Exception {
//		WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
//		return wd.getSelectedWordsByUidCid(userid, courseid);
//
//	}

	/**
	 * 更新用户单词筛选状态
	 */
//	@Override
//	public int updateWordSelState(String userid, List<JSONObject> list) throws Exception {
//		try {
//			//1.开启事务
//			DataSourceUtils.startTransaction();
//			
//			WordDao wd=(WordDao) BeanFactory.getBean("WordDao");
//			int ret=0;
//			//3.向CollectionItem中添加n条数据
//			for (JSONObject item : list) {
//				String wordid=item.getString("wordid");
//				Boolean selected=item.getBoolean("selected");
//				//1.判断是否存在该单词的筛选态
//				int ret2=wd.queryWordSelItem(userid,wordid);
//				System.out.println("查询是否存在筛选态返回的值：" +wordid+": "+ret2);
//				if(ret2!=0) {
//					if(selected==false) {
//						ret2=wd.deleteWordSelItem(userid,wordid);
//						ret++;
//					}
//				}else {
//					if(selected==true) {
//						String id="WUS"+UUIDUtils.getCode();
//						ret2=wd.addWordSelItem(id,userid,wordid);
//						ret++;
//					}
//				}
//				
//			}
//			
//			//4.事务处理
//			DataSourceUtils.commitAndClose();
//			return ret;
//		} catch (Exception e) {
//			e.printStackTrace();
//			DataSourceUtils.rollbackAndClose();
//			throw e;
//		}
//	}

}
