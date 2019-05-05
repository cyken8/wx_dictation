package indi.cyken.web.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import indi.cyken.constant.FEIPCodeEnum;
import indi.cyken.dto.ParamDto;
import indi.cyken.utils.FileUtil;
import indi.cyken.utils.JsonUtil;
import indi.cyken.utils.OSResourcePathUtil;
import indi.cyken.utils.RequestUtil;
import indi.cyken.utils.SpeechUtil;
import indi.cyken.utils.UploadUtils;
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
		if(ServletFileUpload.isMultipartContent(request)) {	//判断是否未multipart/form-data
			//1.将所有的解析结果封装到ParamDto对象中
			ParamDto dto=RequestUtil.parseParam(request);
			//2.获取表单参数
			String userid=dto.getParamMap().get("userid");
			//3.获取提交的文件
			FileItem item=dto.getFileMap().get("file");
			//4.获取上传文件原始名称
			String fileName=item.getName();
			System.out.println("文件的原始文件名称"+fileName);
			//5.生成一个随机名称文件名称
			String randomName=UploadUtils.getUUIDName(userid,fileName);
			//6.获取文件保存在服务器的路径
			//String path=ResourceBundle.getBundle("resourcepath").getString("VoicePath");
			String path=OSResourcePathUtil.getVoicePath();
			//String fullFilePath=path+"/"+randomName;
			String fullFilePath=path+System.getProperty("file.separator")+randomName;
			System.out.println("原录音文件名："+fullFilePath);
			//7.创建文件
			if(!FileUtil.createFile(fullFilePath)) {
				System.out.println("录音文件不存在");
				return null;
			}
			//8.保存录音文件
			item.write(new File(fullFilePath));
			String destFileName=FileUtil.getShortName(randomName)+".pcm";
			String destFilePath=path+System.getProperty("file.separator")+destFileName;
			if(!FileUtil.createFile(destFilePath)) {
				System.out.println("目标文件不存在");
				return null;
			}
			System.out.println("转换目标文件名："+destFilePath);
			//7.解析录音
			//String ffmpegUrl=ResourceBundle.getBundle("resourcepath").getString("FFmpegPath");

			//FFmpegUtil.vedioToPcm(fullFilePath, destFilePath, ffmpegUrl);
			System.out.println("开始转换音频格式");
	        SpeechUtil.convertMP32Pcm(fullFilePath,destFilePath);
			//AudioConverterUtil.mp3Convertpcm(fullFilePath, destFilePath);
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
