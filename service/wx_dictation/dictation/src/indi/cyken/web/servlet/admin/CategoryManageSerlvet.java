package indi.cyken.web.servlet.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Category;
import indi.cyken.dto.Result;
import indi.cyken.service.CategoryService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CategoryManageSerlvet
 */
public class CategoryManageSerlvet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 管理员网页获取所有课本分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist=cs.findAll();
		Result result=new Result(0,"获取所有课本分类成功");
		JSONObject data=new JSONObject();
		if(clist!=null) {
			data.put("list", clist);
			result.setResult(data);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取所有课本分类失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	
	/**
	 * 管理员网页获取所有课本分类(前端下拉框使用）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllCategoryForDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist=cs.findAll();
		Result result=new Result(0,"获取所有课本分类成功");
		if(clist!=null) {
			result.setResult(clist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取所有课本分类失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	/**
	 * 管理员网页获取所有课本分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String cname=request.getParameter("cname");
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		
		Category category=new Category(UUIDUtils.getCode(),cname);
		int ret=cs.AddOneCateogry(category);
		Result result=new Result(0,"获取所有课本分类成功");
		if(ret>0) {
			result.setResult(1);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取所有课本分类失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	/**
	 * 管理员网页删除一个课本分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String cid = request.getParameter("cid");	//课时编号
		
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		int ret=cs.delOneCategory(cid);
		Result result=new Result(0,"删除课本分类成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除课本分类失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	/**
	 * 管理员网页删除多个课本分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelManyCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String cid = request.getParameter("cid");	//课时编号
		String[] cidList = cid.split(",");
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		int ret=cs.delManyCategory(cidList);
		Result result=new Result(0,"删除课本分类成功");
		if(ret==cidList.length) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除课本分类失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	

}
