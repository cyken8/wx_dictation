package indi.cyken.web.servlet;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import indi.cyken.constant.Constant;
import indi.cyken.constant.DBTableField;
import indi.cyken.domain.Book;
import indi.cyken.domain.BookLanguage;
import indi.cyken.domain.BookType;
import indi.cyken.domain.BookVersion;
import indi.cyken.domain.Category;
import indi.cyken.domain.City;
import indi.cyken.domain.Course;
import indi.cyken.domain.Grade;
import indi.cyken.domain.Organization;
import indi.cyken.domain.Province;
import indi.cyken.domain.Role;
import indi.cyken.domain.SClass;
import indi.cyken.domain.Unit;
import indi.cyken.domain.User;
import indi.cyken.domain.Word;
import indi.cyken.domain.WordComeType;
import indi.cyken.domain.WordLengType;
import indi.cyken.dto.Result;
import indi.cyken.dto.Book.BookTreeNodeDto;
import indi.cyken.dto.User.UserDto;
import indi.cyken.service.BookService;
import indi.cyken.service.CategoryService;
import indi.cyken.service.CourseService;
import indi.cyken.service.UnitService;
import indi.cyken.service.UserService;
import indi.cyken.service.WordService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.HTMLConvertTextUtil;
import indi.cyken.utils.OSResourcePathUtil;
import indi.cyken.utils.SpeechUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class test
 */

public class TestServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取全部用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserService us = (UserService) BeanFactory.getBean("UserService");
		List<User> ul = us.getAllUser();
		List<UserDto> list = new LinkedList<>();
		Result result = new Result(0, "获取所有用户成功");
		JSONObject data = new JSONObject();
		if (ul != null) {
			for (int i = 0; i < ul.size(); i++) {
				// JSONObject userDto=new JSONObject();
				UserDto userDto = new UserDto();
				User userDao = ul.get(i);
				// BeanCopyUtil.copyPropertiesObj2Item(userDto, userDao);
				BeanUtils.copyProperties(userDto, userDao);
				userDto.setRole(userDao.getRole().getRolename());
				userDto.setProvince(userDao.getProvince().getProvincename());
				userDto.setCity(userDao.getCity().getCityname());
				userDto.setOrg(userDao.getOrg().getOrgname());
				userDto.setGrade(userDao.getGrade().getGradename());
				userDto.setSclass(userDao.getSclass().getClassname());
				list.add(userDto);
			}

			data.put("list", list);
			result.setResult("data", data);
			// 4.写回网页端
			WriteBackUtil.WriteBackJsonStr(result, response);
		} else {
			result.setErrorNo(1);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;

	}

	/**
	 * 新增用户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String roleid = request.getParameter("role");
		String sex = request.getParameter("sex");
		String state = request.getParameter("state");
		String provinceid = request.getParameter("province");
		String cityid = request.getParameter("city");
		String orgid = request.getParameter("org");
		String gradeid = request.getParameter("grade");
		String sclassid = request.getParameter("sclass");

		// 2.添加用户
		User user = new User(UUIDUtils.getCode(), username, password, "avatar", sex, new Date(), "email",
				new Role(roleid), new Organization(orgid), new Grade(gradeid), new SClass(sclassid),
				new Province(provinceid), new City(cityid), "open_id", state);
		UserService us = (UserService) BeanFactory.getBean("UserService");
		int ret = us.add(user);
		Result result = new Result(0, "添加用户成功");
		JSONObject data = new JSONObject();
		if (ret > 0) { // 添加用户成功
			// 写回前端添加用户成功提示
			result.setResult(1); // 按例子来
			WriteBackUtil.WriteBackJsonStr(result, response);
			System.out.println("添加用户成功");

			// 3.读取所有用户
			List<User> ul = us.getAllUser();
			List<Object> list = new LinkedList<>();
			for (int i = 0; i < ul.size(); i++) {
				JSONObject userinfo = new JSONObject();
				User tmpuser = ul.get(i);
				userinfo.put("userid", tmpuser.getUserid());
				userinfo.put("username", tmpuser.getUsername());
				userinfo.put("password", tmpuser.getPassword());
				userinfo.put("role", tmpuser.getRole().getRolename());
				list.add(userinfo);
			}

			data.put("list", list);
			result.setResult("data", data);
			// 4.写回网页端
			WriteBackUtil.WriteBackJsonStr(result, response);
		} else {
			// 添加用户失败
			result.setErrorNo(1); // 失败errorNo:1代表成功
			result.setResult(0); // 按例子来
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;

	}

	/**
	 * 新增用户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebdelOneUserByUid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取参数
		String userid=request.getParameter("userid");
	
		//2.删除用户
		UserService us = (UserService) BeanFactory.getBean("UserService");
		int ret=us.delOneUserByUid(userid);
		Result result=new Result(0,"删除用户成功");
		if(ret>0) {
			//删除成功
			result.setResult(1);
			WriteBackUtil.WriteBackJsonStr(result, response);
			
		}else {
			result.setErrorNo(1);
			WriteBackUtil.WriteBackJsonStr(result, response);

		}
		
	

	return null;
	
	}
	
	
	
	/////////////////////////////BookNode/////////////////////////////////

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
		String bookname=request.getParameter("bookname");
		String bookversion=request.getParameter("bookversion");
		String booklang=request.getParameter("booklang");
		String bookcatagory=request.getParameter("bookcatagory");
		String cover=request.getParameter("cover");
		
		Book book=new Book(UUIDUtils.getCode(),bookname,new BookVersion(bookversion),new BookLanguage(booklang),new BookType("BT100"),new Category(bookcatagory),cover,"1");
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
	 * 为一本课本添加一个单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid=request.getParameter("bookid");
		String unitname=request.getParameter("unitname");

		Unit unit=new Unit(UUIDUtils.getCode(),unitname,new Book(bookid));
		//2.添加单元
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		int ret=us.addOneUnit(unit);
		Result result = new Result(0, "添加单元成功");
		JSONObject data = new JSONObject();
		if(ret>0) {
			//添加成功
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("添加单元失败");
			WriteBackUtil.WriteBackJsonStr(result, response);	
		}
	
		return null;

	}
	
	/**
	 * 为一个单元添加一个课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddOneCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid=request.getParameter("unitid");
		String coursename=request.getParameter("coursename");

		Course course=new Course(UUIDUtils.getCode(),coursename,new Unit(unitid));
		//2.添加课时
		CourseService us = (CourseService) BeanFactory.getBean("CourseService");
		int ret=us.addOneCourse(course);
		Result result = new Result(0, "添加课时成功");
		JSONObject data = new JSONObject();
		if(ret>0) {
			//添加成功
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("添加课时失败");
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
	 * 获取某一课本下的所有单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetAllUnitByBid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String bookid=request.getParameter("bookid");
		System.out.println("获取到的课本id: "+bookid);
		
		UnitService us = (UnitService) BeanFactory.getBean("UnitService");
		List<Unit> blist =us.getByBid(bookid);
		Result result = new Result(0, "获取单元成功");
		if(blist!=null) {
			result.setResult(blist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取单元失败");
		}
		return null;
	}
	
	/**
	 * 获取某一课时下的所有标准单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebGetWordStandByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.节点编号
		String nodeId = request.getParameter("menuId");	
		//2.判断节点类型，此处无需判断也可完成业务逻辑，留待改善代码使用。

		//2.调用service 通过oid 返回值:order
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		List<Word> wlist=ws.getWordsByCid(nodeId);
		Result result=new Result(0,"获取标准单词成功");
		if(wlist!=null) {
			result.setResult(wlist);
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("获取标准单词失败");
		}
		return null;
	}
	
	/**
	 * 管理员网页上添加标准单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebAddWordStandard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.节点编号
		String courseid = request.getParameter("courseid");	//课时编号
		String description=request.getParameter("description");
		
		description = HTMLConvertTextUtil.convertOneText(description);
		List<String> list = Arrays.asList(description.split(";"));
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));
		}
		List<Word> wlist=new LinkedList<>();
		for(int i=0;i<list.size();i++) {
			String wordtext=list.get(i);
			//2.获取录音文件保存路径
			String path=OSResourcePathUtil.getWordPath();
			String fileName=wordtext+".mp3";
			String fullPath=path+System.getProperty("file.separator")+fileName;

			//3.文字合成录音
			boolean ret = SpeechUtil.SpeechSynthesizer(wordtext, fullPath);
			System.out.println("文字合成录音返回的成功与否结果"+ret);
			if(!ret) {
				return null;
			}

			//组装Word
			String wordPathVirtual=OSResourcePathUtil.getWordPathVirtual();
			String voiceurl=wordPathVirtual+fileName;
			Word word=new Word(Constant.PREFIX_WORD+UUIDUtils.getCode(),
					wordtext,
					voiceurl,
					new Course(courseid),
					new WordLengType(DBTableField.WORD_LENGTYPE_LONGWORD),
					new WordComeType(DBTableField.WORD_COMETYPE_STANDARD));
			
			wlist.add(word);
			
		}
		
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		int ret=ws.addWordStandard(wlist);
		Result result=new Result(0,"添加标准单词成功");
		if(ret==wlist.size()) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}
		else {
			result.setErrorNo(1);
			result.setErrorInfo("添加标准单词失败");
			WriteBackUtil.WriteBackJsonStr(result, response);

		}
		return null;
	}
	
	
	/**
	 * 管理员网页删除单词
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelWordStandard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String wordid = request.getParameter("wordid");	//课时编号
		
		WordService ws=(WordService) BeanFactory.getBean("WordService");
		int ret=ws.delWordStandard(wordid);
		Result result=new Result(0,"删除标准单词成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除标准单词失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	/**
	 * 管理员网页删除课时
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneCourse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String courseid = request.getParameter("courseid");	//课时编号
		
		CourseService cs=(CourseService) BeanFactory.getBean("CourseService");
		int ret=cs.delOneCourse(courseid);
		Result result=new Result(0,"删除课时成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除课时失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
		}

		return null;
	}
	
	
	/**
	 * 管理员网页删除单元
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WebDelOneUnit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取参数
		String unitid = request.getParameter("unitid");	//课时编号
		
		UnitService us=(UnitService) BeanFactory.getBean("UnitService");
		int ret=us.delOneUnit(unitid);
		Result result=new Result(0,"删除单元成功");
		if(ret>0) {
			WriteBackUtil.WriteBackJsonStr(result, response);
		}else {
			result.setErrorNo(1);
			result.setErrorInfo("删除单元失败");
			WriteBackUtil.WriteBackJsonStr(result, response);
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
