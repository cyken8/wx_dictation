package indi.cyken.utils;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameUtil {

	/**
	 * 修改指定文件的扩展名
	 * @param fileName
	 * @param newExt
	 * @return
	 */
    public static String changeFileExt(String fileName, String newExt){
        return fileName.replaceAll("\\.[^\\.\\\\/]*$", "") + "." + newExt.replaceAll("^\\.", "");
    }
    /**
     * 去掉文件的扩展名
     * @param fileName
     * @return
     */
    public static String removeFileExt(String fileName){
        return fileName.replaceAll("\\.[^\\\\/\\.]*$", "");
    }
    
    /**
     * 修正文件名错误，主要包括出现/的、双\\的 
     * @param fileName
     * @return
     */
    public static String correctFileName(String fileName){
        return fileName.replaceAll("(?!^)[\\\\/]+", "\\\\");
    }
    /**
     * 修正文件名错误，主要包括出现/的、双\\的 成为linux 
     * @param fileName
     * @return
     */
    public static String correctFileName4Linux(String fileName){
        return fileName.replaceAll("(?!^)[\\\\/]+", "/");
    }
    
    /**
     * 判断文件是否存在 
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName){
        //把一个或多个\或/替换成1个
        File f = new File(correctFileName(fileName));
        try{
            return f.exists();
        }finally{
            f = null;
        }
    }
    
    /**
     * 连接两个文件名
     * @param base
     * @param addition
     * @return
     */
    public static String fileNameAdd(String base, String addition){
        return base.replaceAll("[\\\\/]+$", "") + "\\" + addition.replaceAll("^[\\\\/]+", "");
    }
    
    /**
     * 是不是UNC路径 
     * @param fileName
     * @return
     */
    public static boolean isUNC(String fileName){
        return fileName.matches("^\\\\{2}[^\\\\/]+\\\\[\\s\\S]*$");
    }
    
    /**
     * 获取文件名的扩展名 
     * @param fileName
     * @return
     */
    public static String extractFileExt(String fileName){
        Pattern p = Pattern.compile("\\.[^\\\\/.]+$");
        Matcher m = p.matcher(fileName);
        return m.find()? m.group() : "";
    }
    
    /**
     * 获取文件的路径（最后的\会被去掉） 
     * @param fileName
     * @return
     */
    public static String extractFilePath(String fileName){
        return fileName.replaceAll("[\\\\/][^\\\\/]*$", "");
    }
    
    /**
     * 获取文件绝对路径的文件名部分 
     * @param fileName
     * @return
     */
    public static String extractFileName(String fileName){
        return fileName.replaceAll("^[\\s\\S]*[\\\\/]", "");
    }
    
    /**
     * 获取相对路径（去掉盘符或UNC的主机）
     * @param fileName
     * @return
     */
    public static String extractRelativePath(String fileName){
        if(isUNC(fileName)){
            return fileName.replaceAll("^\\\\{2}[^\\\\/]+[\\\\/]+", "");
        }else{
            return fileName.replaceAll("^.*\\:\\\\+", "");
        }
    }
    
    /**
     * 把盘符 和 文件路径拼接起来 得到完整的文件地址，自动判断拼接的时候前面是不是有  斜杠 
     * @param driverOrLpath windows系统下的盘符，或者是linux系统下的路径
     * @param filename 文件的路径 如： 二次合成\2011\IPTV\上海文广\电影\123456_变形金刚.ts
     */
    public static String joinPath(String driverOrLpath,String filename ){
        String d = driverOrLpath.replaceAll("[\\\\/]*$", "") ;
        filename = filename.replaceAll("^[\\\\/]*", ""); // 把开头的 斜杠都去掉，后面统一加
        
        return d + File.separator + filename;
    }
    
    /**
     * 功能：替换掉文件名字中的特殊字符
     * 时间：2016-01-21
     * @param filename
     * @return
     */
    public static String removeSpecialcharacter(String filename){
         Pattern pattern=Pattern.compile("[\u4e00-\u9fa5]");//中文汉字编码区间  
           Matcher matcher;
           char[] array = filename.toCharArray();
           for (int i = 0; i < array.length; i++) {
                if((char)(byte)array[i]!=array[i]){//取出双字节字符
                    matcher=pattern.matcher(String.valueOf(array[i]));
                    if(!matcher.matches()){//中文汉字无需替换
                        filename=filename.replaceAll(String.valueOf(array[i]), "");//特殊字符用空字符串替换
                    }
                }
            }
           return filename;
    }
    
    public static void main(String[] args) {
        
    }
}