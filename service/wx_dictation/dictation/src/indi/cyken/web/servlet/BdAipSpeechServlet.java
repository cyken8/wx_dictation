package indi.cyken.web.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import indi.cyken.constant.Constant;
import indi.cyken.constant.DBTableField;
import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.domain.BookLanguage;
import indi.cyken.dto.ParamDto;
import indi.cyken.service.BdOcrService;
import indi.cyken.service.CourseService;
import indi.cyken.service.WordService;
import indi.cyken.utils.AudioConverterUtil;
import indi.cyken.utils.BeanFactory;
import indi.cyken.utils.FFmpegUtil;
import indi.cyken.utils.FileUtil;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.RequestUtil;
import indi.cyken.utils.SpeechUtil;
import indi.cyken.utils.UUIDUtils;
import indi.cyken.utils.UploadUtils;
import indi.cyken.utils.WriteBackUtil;
import net.sf.json.JSONObject;


/**
 * 百度语音Servlet
 */

public class BdAipSpeechServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * 微信端解析录音文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String WXParseRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("调用BdAipSpeechServlet中的 WXParseRecord");
		String filePath=null;
		if(ServletFileUpload.isMultipartContent(request)) {	//判断是否未multipart/form-data
			//1.将所有的解析结果封装到ParamDto对象中
			ParamDto dto=RequestUtil.parseParam(request);
			//2.获取表单参数
			String userid=dto.getParamMap().get("userid");
			System.out.println("用户名"+userid);
			//3.获取提交的文件
			FileItem item=dto.getFileMap().get("file");
			//4.获取上传文件原始名称
			String fileName=item.getName();
			System.out.println("文件的原始文件名称"+fileName);
			//5.生成一个随机名称文件名称
			String randomName=UploadUtils.getUUIDName(userid,fileName);
			System.out.println("随机文件名称"+randomName);
			//6.获取文件保存在服务器的路径
			//String path=this.getServletContext().getRealPath("/images_upload");
			String path="E:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\dictationResourse\\uservoice";
			String fullFilePath=path+"\\"+randomName;
			System.out.println("完整录音路径名："+fullFilePath);
			//7.创建文件
			if(!FileUtil.createFile(path, randomName)) {
				System.out.println("录音文件不存在");
				return null;
			}
			//8.保存录音文件
			item.write(new File(fullFilePath));
			String destFileName=FileUtil.getShortName(randomName)+".pcm";
			System.out.println("目标文件名："+destFileName);
			String destFilePath=path+"\\"+destFileName;
			System.out.println("完整转换目标路径名："+destFilePath);
			if(!FileUtil.createFile(path, destFileName)) {
				System.out.println("目标文件不存在");
				return null;
			}
			System.out.println("原录音文件名："+fullFilePath);
			System.out.println("转换目标文件名："+destFilePath);
			//7.解析录音
			String ffmpegUrl="G:\\code\\ffmpeg\\ffmpeg-20190119-32fb83e-win64-static\\ffmpeg-20190119-32fb83e-win64-static\\bin\\ffmpeg.exe";
			FFmpegUtil.vedioToPcm(fullFilePath, destFilePath, ffmpegUrl);
	        //SpeechUtil.convertMP32Pcm(fullFilePath,destFilePath);
			//AudioConverterUtil.mp3Convertpcm(fullFilePath, destFilePath);
			//FFmpegUtil.changeAmrToMp3(fullFilePath, destFilePath);
	        String wordText = SpeechUtil.SpeechRecognition2(destFilePath,"pcm");
	        if(wordText!=null) {
		        wordText=wordText.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
		        System.out.println("录音文件解析得到的单词："+wordText);
				//9.写回到微信小程序
		        JSONObject jsonObj=new JSONObject();
		        jsonObj.put("state", 1);
		        jsonObj.put("code", FEIPCodeEnum.OK.getCode());
		        jsonObj.put("msg", FEIPCodeEnum.OK.getMsg());
		        jsonObj.put("data", wordText);
		        String jsonstr = JsonUtil.object2json(jsonObj);
		        System.out.println("录音识别返回前端的数据："+jsonstr);
		        response.setContentType("application/json;charset=utf-8");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(jsonstr);
				//WriteBackUtil.WriteBackJsonStr(jsonstr, response);
				return "success:录音识别结果返回成功";
		        
	        }
					
		}		
		return "fail:录音识别失败";

	}
  

}
