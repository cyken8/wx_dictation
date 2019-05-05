package indi.cyken.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的servlet
 */
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("调用了baseServlet");
			// 1.获取子类  创建子类或者调用子类的时候 this代表的是子类对象
			@SuppressWarnings("rawtypes")
			Class clazz = this.getClass();
			//System.out.println(this);

			// 2.获取请求的方法
			String m = request.getParameter("method");
			if(m==null){
				m="index";
			}
			//System.out.println(m);

			// 3.获取方法对象
			Method method = clazz.getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
			
			System.out.println("调用 "+ m+"模块中的 "+method+"方法");
			
			// 4.让方法执行 返回值为请求转发的路径
			String s=(String) method.invoke(this, request,response);//相当于 userservlet.add(request,response)
			
			// 5.判断s是否为空
//			if(s!=null){
//				request.getRequestDispatcher(s).forward(request, response);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 

	}
	
	
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return null;
	}
	
}
