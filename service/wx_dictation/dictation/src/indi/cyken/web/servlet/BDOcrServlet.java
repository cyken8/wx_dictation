package indi.cyken.web.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.dto.ParamDto;
import indi.cyken.service.BdOcrService;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.FileUtil;
import indi.cyken.utils.OSResourcePathUtil;
import indi.cyken.utils.RequestUtil;
import indi.cyken.utils.UploadUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;



/**
 * 百度Ocr文字识别
 */
public class BDOcrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	
	/**
	 * 接收图片进行解析
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXUploadWordPic3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("调用BDOcrServlet中的 WXUploadWordPic2");
		if(ServletFileUpload.isMultipartContent(request)) {	//判断是否未multipart/form-data
			//1.将所有的解析结果封装到ParamDto对象中
			ParamDto dto=RequestUtil.parseParam(request);
			//2.获取表单参数
			String userid=dto.getParamMap().get("userid");
			String fileIndex=dto.getParamMap().get("fileIndex");
			System.out.println("用户名"+userid+"fileIndex: "+fileIndex);
			//3.获取提交的文件
			FileItem item=dto.getFileMap().get("file");
			//4.获取上传文件原始名称
			String fileName=item.getName();
			System.out.println("文件的原始文件名称"+fileName);
			//5.生成一个随机名称文件名称
			String randomName=UploadUtils.getUUIDName(userid,fileName);
			System.out.println("随机文件名称"+randomName);
			//6.获取文件保存在服务器的路径
			//String path=ResourceBundle.getBundle("resourcepath").getString("PicPathHandWrite");
			String path=OSResourcePathUtil.getPicPathHandWrite();
			String fullFilePath=path+System.getProperty("file.separator")+randomName;
			System.out.println("完整路径名："+fullFilePath);
			//7.保存图片
			if(!FileUtil.createFile(fullFilePath)) {
				System.out.println("文件不存在");
				return null;
			}
			item.write(new File(fullFilePath));
			System.out.println("图片完整路径"+fullFilePath);
			//8.上传识别
			BdOcrService ocrServ=(BdOcrService) BeanFactory.getBean("BdOcrService");
			String ocrResult=ocrServ.getOcrRessult(fullFilePath);
			//String ocrResult=BdOcrUtil.getOcrRessult(fullFilePath);
			if(ocrResult!=null) {
				System.out.println("打印识别结果："+ocrResult);
				//9.写回到微信小程序
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("state", 1);
				jsonObj.put("code", FEIPCodeEnum.OK.getCode());
				jsonObj.put("msg", FEIPCodeEnum.OK.getMsg());
				JSONObject data=new JSONObject();
				data.put("retIndex", fileIndex);
				data.put("ocrResult", ocrResult);
				jsonObj.put("data", data);
				WriteBackUtil.WriteBackJsonStr(jsonObj, response);	
			}else {
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("state", 0);
				jsonObj.put("code", FEIPCodeEnum.FAIL.getCode());
				jsonObj.put("msg", FEIPCodeEnum.FAIL.getMsg());
				WriteBackUtil.WriteBackJsonStr(jsonObj, response);	

			}
						
		}		
		return null;

	}
	
	
	

}
