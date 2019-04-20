package indi.cyken.utils;

import java.io.*;

import org.apache.commons.fileupload.FileItem;

/**
 * 文件读取工具类（百度ocr使用）
 */
public class FileUtil {
	
    /**
     * 上传文件的保存路径
     */
    public static final String IMAGE_SAVE_PATH = "c:/dictation/upload/images/";
    

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } 

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        } 

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        FileInputStream fis = new FileInputStream(filePath);  
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];  
        // 用于保存实际读取的字节数  
        int hasRead = 0;  
        while ( (hasRead = fis.read(bbuf)) > 0 ) {  
            sb.append(new String(bbuf, 0, hasRead));  
        }  
        fis.close();  
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }
    
    
    /**
     * 保存上传的文件
     * @param fileItem
     * @param path
     * @return
     * @throws Exception
     */
    public static String save(FileItem fileItem,String path) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + fileItem.getName();
        fileItem.write(new File(path + fileName));
        return fileName;
    }
    
    
    /**
     * 文件的创建
     * @param filePath:后面不带\
     * @return
     */
    public static boolean createFile(String filePath,String fileName)
    {
    	File dir = new File(filePath);
        if(!dir.exists()){
              dir.mkdirs();
        }
        File f=new File(filePath+"\\"+fileName);
        try {
            return f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    /**
     * 文件的创建
     * @param filePath：完整路径名
     * @return
     */
    public static boolean createFile(String filePath)
    {
       	File dir = new File(filePath);
        if(!dir.exists()){
              dir.mkdirs();
        }
        File f=new File(filePath);
        try {
            return f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    //文件的重命名
    public static boolean renameFile(String oldPath,String newPath)
    {
        File oldFile=new File(oldPath);
        return oldFile.renameTo(new File(newPath));
    }
    
    //文件的删除
    /**
     * 删除文件
     * @param filePath：带文件名的完整路径
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        File f=new File(filePath);
        //判断是否是文件 并且存在
        if(f.isFile()&&f.exists())
        {
            return f.delete();
        }
        return false;
    }
    
    /** 
     * 获取短文件名,不带扩展名 
     * @param fileName 
     * @return 
     */  
    public static String getShortName(String fileName){  
        if(fileName != null && fileName.length()>0 && fileName.lastIndexOf(".")>-1){  
            return fileName.substring(0, fileName.lastIndexOf("."));  
        }   
        return fileName;  
    } 
    
    /** 
     * 获取扩展名,带点 
     * @param fileName 
     * @return 
     */  
    public static String getExtention(String fileName){  
        if(fileName!=null && fileName.length()>0 && fileName.lastIndexOf(".")>-1){  
            return fileName.substring(fileName.lastIndexOf("."));  
        }  
        return "";  
    }  
}
