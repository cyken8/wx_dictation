package indi.cyken.web.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.cyken.domain.Book;
import indi.cyken.domain.Collection;
import indi.cyken.domain.CollectionItem;
import indi.cyken.domain.User;
import indi.cyken.service.BookService;
import indi.cyken.service.CollectionService;
import indi.cyken.service.UserService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.UUIDUtils;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CollectionServlet
 */
public class CollectionServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 添加到收藏
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取bookid和userid
		String bookid = request.getParameter("bookid");
		String userid = request.getParameter("userid");
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("msg", "fail");
		jsonObject.put("data", "{}");
		if(userid == null){
			response.getWriter().print(jsonObject);
			return "fail:用户没有登录";
		}
		//2.组成成Collection
		Collection collection=new Collection();
		UserService us=(UserService) BeanFactory.getBean("UserService");
		User user=us.getByUid(userid);
		collection.setUser(user);
		
		//3、组成CollectionItem
		CollectionItem item=new CollectionItem();
		item.setItemid("I"+UUIDUtils.getCode());
		item.setState("1");
		BookService bs=(BookService) BeanFactory.getBean("BookService");
		Book book = bs.getByBid(bookid);
		item.setBook(book);
		item.setCollection(collection);
		collection.getItems().add(item);
		
		//4、.调用Service
		CollectionService cs=(CollectionService) BeanFactory.getBean("CollectionService");
		cs.add(collection);
//		
//		
//		//5.重定向
//		//response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
//		
//		//写回
		jsonObject.put("state", 1);
		jsonObject.put("msg", "success");
		response.getWriter().print(jsonObject);
		return null;
	}
	
	/**
	 * 按用户名查询所有
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String findByUserid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取userid
		String userid = request.getParameter("userid");
		
		//2.调用service 通过oid 返回值:order
		CollectionService cs=(CollectionService) BeanFactory.getBean("CollectionService");
		Collection collection=cs.findByUserid(userid);
		
		
		// 2.将返回值转成json格式 返回到页面上
		//request.setAttribute("blist", blist);
		List<CollectionItem> items = collection.getItems();
		List<Book> blist=new LinkedList<>();
		for(int i=0;i<items.size();i++) {
			blist.add(items.get(i).getBook());
		}
		String json = JsonUtil.list2json(blist);
		
		//3.写回去
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
		
		return null;
		
	}
	
	/**
	 * 删除收藏项
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取bookid和userid
		String bookid = request.getParameter("bookid");
		String userid = request.getParameter("userid");
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("msg", "fail");
		jsonObject.put("data", "{}");
		if(userid == null){
			response.getWriter().print(jsonObject);
			return "fail:用户没有登录";
		}
		//2.组成成Collection
		Collection collection=new Collection();
		UserService us=(UserService) BeanFactory.getBean("UserService");
		User user=us.getByUid(userid);
		collection.setUser(user);
		
		//3、组成CollectionItem
		CollectionItem item=new CollectionItem();
		item.setItemid("I"+UUIDUtils.getCode());
		item.setState("1");
		BookService bs=(BookService) BeanFactory.getBean("BookService");
		Book book = bs.getByBid(bookid);
		item.setBook(book);
		item.setCollection(collection);
		collection.getItems().add(item);
		
		//4、.调用Service
		CollectionService cs=(CollectionService) BeanFactory.getBean("CollectionService");
		cs.delete(collection);
//		
//		
//		//5.重定向
//		//response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
//		
//		//写回
		jsonObject.put("state", 1);
		jsonObject.put("msg", "success");
		response.getWriter().print(jsonObject);
		return null;
	}
	

}
