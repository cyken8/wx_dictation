package indi.cyken.service.impl;

import java.util.List;

import indi.cyken.dao.WordDao;
import indi.cyken.dao.WordSelectedDao;
import indi.cyken.domain.Word;
import indi.cyken.service.WordSelectedService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.DataSourceUtils;
import indi.cyken.utils.UUIDUtils;
import net.sf.json.JSONObject;

public class WordSelectedServiceImpl implements WordSelectedService{
	/**
	 * 获取该课时下用户筛选的单词
	 */
	@Override
	public List<Word> getSelectedWordsByUidCid(String userid, String courseid) throws Exception {
		WordSelectedDao wd=(WordSelectedDao) BeanFactory.getBean("WordSelectedDao");
		return wd.getSelectedWordsByUidCid(userid, courseid);

	}

	/**
	 * 更新用户单词筛选状态
	 */
	@Override
	public int updateWordSelState(String userid, List<JSONObject> list) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			WordSelectedDao wd=(WordSelectedDao) BeanFactory.getBean("WordSelectedDao");
			int ret=0;
			//3.向CollectionItem中添加n条数据
			for (JSONObject item : list) {
				String wordid=item.getString("wordid");
				String wordname=item.getString("wordtext");
				Boolean selected=item.getBoolean("selected");
				//1.判断是否存在该单词的筛选态
				int ret2=wd.queryWordSelItem(userid,wordid);
				System.out.println("查询是否存在筛选态返回的值：" +wordid+": "+wordname+": "+ret2);
				if(ret2!=0) {
					if(selected==false) {
						ret2=wd.deleteWordSelItem(userid,wordid);
						System.out.println("删除筛选项："+wordname);
						ret++;
					}
				}else {
					if(selected==true) {
						String id="WUS"+UUIDUtils.getCode();
						ret2=wd.addWordSelItem(id,userid,wordid);
						System.out.println("添加筛选项："+wordname);
						ret++;
					}
				}
				
			}
			
			//4.事务处理
			DataSourceUtils.commitAndClose();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
}
