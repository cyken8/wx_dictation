package indi.cyken.utils;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 本工程专用，Windows,Linux不同系统下路径获取，和resourcepath.properties配置文件搭配使用
 * 
 * @author Administrator
 *
 */
public class OSResourcePathUtil {

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	// public static final String FILE_SEPARATOR = File.separator;

	public static String getRealFilePath(String path) {
		return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
	}

	public static String getHttpURLPath(String path) {
		return path.replace("\\", "/");
	}



	/**
	 * 获取用户上传图片保存路径
	 * @return
	 */
	public static String getPicPathHandWrite() {
		if(isOSLinux()) {
			return ResourceBundle.getBundle("resourcepath").getString("PicPathHandWrite_Linux");
		}else {
			return ResourceBundle.getBundle("resourcepath").getString("PicPathHandWrite");
		}

	}

	
	
	/**
	 * 获取用户上传的录音保存路径
	 * @return
	 */
	public static String getVoicePath() {
		if(isOSLinux()) {
			return ResourceBundle.getBundle("resourcepath").getString("VoicePath_Linux");
		}else {
			return ResourceBundle.getBundle("resourcepath").getString("VoicePath");
		}

	}
	
	/**
	 * 获取单词音频保存路径
	 * @return
	 */
	public static String getWordPath() {
		if(isOSLinux()) {
			return ResourceBundle.getBundle("resourcepath").getString("WordPath_Linux");
		}else {
			return ResourceBundle.getBundle("resourcepath").getString("WordPath");
		}

	}
	
	/**
	 * 获取单词音频Tomcat虚拟路径
	 * @return
	 */
	public static String getWordPathVirtual() {
		return ResourceBundle.getBundle("resourcepath").getString("WordPath_Virtual");

	}

	/**
	 * 判断系统是否是Linux系统
	 * @return
	 */
	public static boolean isOSLinux() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1) {
			return true;
		} else {
			return false;
		}
	}

}
