package indi.cyken.web.servlet.admin;


import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import indi.cyken.constant.DBTableField;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.Course;
import indi.cyken.domain.Unit;
import indi.cyken.dto.Result;
import indi.cyken.dto.Book.BookInfoDto;
import indi.cyken.dto.Book.BookTreeNodeDto;
import indi.cyken.service.BookService;
import indi.cyken.service.CategoryService;
import indi.cyken.service.CourseService;
import indi.cyken.service.UnitService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import indi.cyken.web.servlet.BaseServlet;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class BookManageSerlvet
 */

public class BookManageSerlvet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 获取全部课本，单元，课时节点
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetBookTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

	
		Result result = new Result(0, "获取树列表成功");
		List<BookTreeNodeDto> list = new LinkedList<>();
		//1.获取课本总分类
		CategoryService cas = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = cas.findAll();
		//2.获取总分类下的所有课本
		BookService bs = (BookService) BeanFactory.getBean("BookService");
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		CourseService cos = (CourseService) BeanFactory.getBean("CourseService");

		for(int i=0;i<clist.size();i++) {
			String categoryid=clist.get(i).getCid();
			String categoryname=clist.get(i).getCname();
			BookTreeNodeDto tnDto=new BookTreeNodeDto(categoryid, false, categoryname, false, "0");
			list.add(tnDto);
			List<Book> blist = bs.getByCategoryId(categoryid);
			//2.获取课本下的所有单元
			for(int j=0;j<blist.size();j++) {
				String bookid=blist.get(j).getBookid();
				String bookname=blist.get(j).getBookname();
				tnDto=new BookTreeNodeDto(bookid, false, bookname, false, categoryid);
				list.add(tnDto);
				List<Unit> ulist = us.getUnitIdAndNameByBid(bookid);
				for(int k=0;k<ulist.size();k++) {
					String unitid=ulist.get(k).getUnitid();
					String unitname=ulist.get(k).getUnitname();
					tnDto=new BookTreeNodeDto(unitid, false, unitname, false, bookid);
					list.add(tnDto);
					List<Course> coslist = cos.getByUid(unitid);
					for(int m=0;m<coslist.size();m++) {
						String couseid=coslist.get(m).getCourseid();
						String cousename=coslist.get(m).getCoursename();
						tnDto=new BookTreeNodeDto(couseid, false, cousename, false, unitid);
						list.add(tnDto);
					}
					
				}
			}	
		}
		
		result.setResult("data", list);
		// 4.写回网页端
		WriteBackUtil.WriteBackJsonStr(result, response);
	
		return null;

	}
	
	/**
	 * 管理员添加一本课本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
//		String bookname=request.getParameter("bookname");
//		String bookversion=request.getParameter("bookversion");
//		String booklang=request.getParameter("booklang");
//		String bookcatagory=request.getParameter("bookcatagory");
//		String cover=request.getParameter("cover");
		BookInfoDto bookInfoDto=new BookInfoDto();
		BeanUtils.populate(bookInfoDto, request.getParameterMap());
		
//		Book book=new Book(UUIDUtils.getCode(),bookname,new BookVersion(bookversion),new BookLanguage(booklang),new BookType(DBTableField.BOOK_COMETYPE_STANDAR),new Category(bookcatagory),cover,"1");
		Book book=new Book(UUIDUtils.getCode(),bookInfoDto.getBookname(),new BookVersion(bookInfoDto.getBookversion()),new BookLanguage(bookInfoDto.getBooklang()),new BookType(DBTableField.BOOK_COMETYPE_STANDAR),new Category(bookInfoDto.getBookcategory()),bookInfoDto.getCover(),"1");

		//2.添加课本
		BookService bs = (BookService) BeanFactory.getBean("BookService");
		int ret=bs.addOneBook(book);
		Result result = new Result(0, "添加课本成功");
		JSONObject data = new JSONObject();
		if(ret>0) {
			//添加成功
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("添加课本失败");
			WriteBackUtil.WriteBackJsonStr(result, response);	
		}
	
		return null;

	}
	
	/**
	 * 获取所有的课本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BookService bs = (BookService) BeanFactory.getBean("BookService");
		List<Book> blist =bs.findAll();
		Result result = new Result(0, "获取课本成功");
		JSONObject data=new JSONObject();
		List<BookInfoDto> dtoList=new LinkedList<>();
		if(blist!=null) {
			for(int i=0;i<blist.size();i++) {
				BookInfoDto bookDto=new BookInfoDto();
				Book bookDao=blist.get(i);
				BeanUtils.copyProperties(bookDto,bookDao );
				bookDto.setBookversion(bookDao.getVersion().getVersionname());
				bookDto.setBooklang(bookDao.getLanguage().getLangname());
				bookDto.setBookcategory(bookDao.getBookCategory().getCname());
				dtoList.add(bookDto);
			}
			data.put("list", dtoList);
			result.setResult(data);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取课本失败");
		}
		return null;
	}
	
	/**
	 * 获取所有的课本(为前端下拉框）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllBookForDropList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BookService bs = (BookService) BeanFactory.getBean("BookService");
		List<Book> blist =bs.findAll();
		Result result = new Result(0, "获取课本成功");
		if(blist!=null) {
			result.setResult(blist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取课本失败");
		}
		return null;
	}
	
	/**
	 * 管理员网页删除课本
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid = request.getParameter("bookid");	//课时编号
		
		BookService bs=(BookService) BeanFactory.getBean("BookService");
		int ret=bs.delOneBook(bookid);
		Result result=new Result(0,"删除课本成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除课本失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	

}
