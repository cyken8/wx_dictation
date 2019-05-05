package indi.cyken.utils;

import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 读取Src目录下某个配置文件
 * @author Administrator
 *
 */
public class ReadPropertesUtil {

	/**
	 * 类加载器方式读取配置文件中中的某个项目值
	 * @param configName 完整配置文件名
	 * @param PropertyName 配置项
	 * @return 配置项的值
	 * @throws Exception
	 */
	public static String loaderReadConfig(String configName,String PropertyName)throws Exception{
		InputStream in = ReadPropertesUtil.class.getClassLoader().getResourceAsStream(configName);
		Properties pro = new Properties();
		pro.load(in);
		String ItemValue=pro.getProperty(PropertyName);
		return ItemValue;	
	}
	
	/**
	 * ResourceBundle方式读取配置文件中中的某个项目值
	 * @param configName 不带后缀的配置文件名
	 * @param PropertyName 配置项
	 * @return 配置项的值
	 * @throws Exception
	 */
	public static String resourceBundleReadConfig(String configName,String PropertyName)throws Exception{
		return ResourceBundle.getBundle(configName).getString(PropertyName);
	}
	
	
}
