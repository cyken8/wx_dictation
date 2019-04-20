package indi.cyken.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import indi.cyken.dto.ParamDto;

/**
 * 请求参数解析工具类
 * @author Yong
 *
 */
public class RequestUtil {
	  /**
     * 从request流中解析参数与上传的文件
     * @param request
     */
    public static ParamDto parseParam(HttpServletRequest request) {
 
        ParamDto result = new ParamDto();
 
        //创建一个FileItem工厂 通过DiskFileItemFactory对象创建文件上传核心组件
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        upload.setHeaderEncoding("UTF-8");
 
        try {
        	//通过文件上传核心组件解析request请求，获取到所有的FileItem对象
            List<FileItem> fileItemList = upload.parseRequest(request);
 
            //遍历表单的所有表单项（FileItem） 并对其进行相关操作
            for(FileItem fileItem : fileItemList) {
            	//判断这个表单项如果是一个普通的表单项
                if(fileItem.isFormField()) {
                	System.out.println("表单参数普通参数："+fileItem.getFieldName()+" "+fileItem.getString("UTF-8"));
                    result.getParamMap().put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
                    //如果不是表单的普通文本域，就是
                } else {
                	System.out.println("表单文件参数："+fileItem.getFieldName());
                    result.getFileMap().put(fileItem.getFieldName(),fileItem);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
