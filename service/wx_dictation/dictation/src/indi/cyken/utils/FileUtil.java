package indi.cyken.utils;

import java.io.*;

import org.apache.commons.fileupload.FileItem;

/**
 * 文件读取工具类（百度ocr使用）
 */
public class FileUtil {
	
	 /**
     * 验证字符串是否为正确路径名的正则表达式
     */
    private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";

    /**
     * 通过 sPath.matches(matches) 方法的返回值判断是否正确
     * sPath 为上传的文件路径字符串
     */
    static boolean flag = false;
	
   
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
     * @param filePath:
     * @return
     */
    public static boolean createFile(String filePath,String fileName)
    {
    	File dir = new File(filePath);
        if(!dir.exists()){
              dir.mkdirs();
        }
        File f=new File(filePath+System.getProperty("file.separator")+fileName);
        try {
            return f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    

    
    
    /**
     * 创建单个文件
     *
     * @param filePath 文件所存放的路径
     * @return
     */
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {// 判断文件是否存在
            System.out.println("目标文件已存在" + filePath);
            return false;
        }
        if (filePath.endsWith(File.separator)) {// 判断文件是否为目录
            System.out.println("目标文件不能为目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
            // 如果目标文件所在的文件夹不存在，则创建父文件夹
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
                System.out.println("创建目标文件所在的目录失败！");
                return false;
            }
        }
        try {
            if (file.createNewFile()) {// 创建目标文件
                System.out.println("创建文件成功:" + filePath);
                return true;
            } else {
                System.out.println("创建文件失败！");
                return false;
            }
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
            System.out.println("创建文件失败！" + e.getMessage());
            return false;
        }
    }
    

    
 
    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param deletePath
     * @return
     */
    public static boolean deleteFolder(String deletePath) {
        flag = false;
        if (deletePath.matches(matches)) {
            File file = new File(deletePath);
            // 判断目录或文件是否存在
            if (!file.exists()) {
                // 不存在返回 false
                return flag;
            } else {
                // 判断是否为文件
                if (file.isFile()) {
                    // 为文件时调用删除文件方法
                    return deleteFile(deletePath);
                } else {
                    // 为目录时调用删除目录方法
                    return deleteDirectory(deletePath);
                }
            }
        } else {
            System.out.println("要传入正确路径！");
            return false;
        }
    }
    
   
    
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
    
    
    //文件的重命名
    public static boolean renameFile(String oldPath,String newPath)
    {
        File oldFile=new File(oldPath);
        return oldFile.renameTo(new File(newPath));
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
    
    /**
     * 判断目录是否存在
     * @param strDir
     * @return
     */
    public static boolean existsDirectory(String strDir) {
        File file = new File(strDir);
        return file.exists() && file.isDirectory();
    }

    /**
     * 判断文件是否存在
     * @param strDir
     * @return
     */
    public static boolean existsFile(String strDir) {
        File file = new File(strDir);
        return file.exists();
    }
    
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dirPath
     * @return
     */
    public static boolean deleteDirectory(String dirPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!dirPath.endsWith(File.separator)) {
            dirPath = dirPath + File.separator;
        }
        File dirFile = new File(dirPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 获得传入路径下的所有文件
        File[] files = dirFile.listFiles();
        // 循环遍历删除文件夹下的所有文件(包括子目录)
        if (files != null) {
            for (File file1 : files) {
                if (file1.isFile()) {
                    // 删除子文件
                    flag = deleteFile(file1.getAbsolutePath());
                    System.out.println(file1.getAbsolutePath() + " 删除成功");
                    if (!flag) {
                        break;// 如果删除失败，则跳出
                    }
                } else {// 运用递归，删除子目录
                    flag = deleteDirectory(file1.getAbsolutePath());
                    if (!flag) {
                        break;// 如果删除失败，则跳出
                    }
                }
            }
        }

        if (!flag) {
            return false;
        }
        // 删除当前目录
        return dirFile.delete();
    }
    
    
    
    /**
     * 强制创建目录
     * @param strDir
     * @return
     */
    public static boolean forceDirectory(String strDir) {
        File file = new File(strDir);
        file.mkdirs();
        return existsDirectory(strDir);
    }
    
    
    /**
     * 创建目录(如果目录存在就删掉目录)
     *
     * @param destDirName 目标目录路径
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("目标目录已存在!");
            //return false;
            return FileUtil.deleteDirectory(destDirName);
        }
        System.out.println("已删除原目录并重新创建!");
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }
    
    /**
     * 得到文件的大小
     * @param fileName
     * @return
     */
    public static int getFileSize(String fileName){

        File file = new File(fileName);
        FileInputStream fis = null;
        int size = 0 ;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return size ;
    }
    
    /**
     * 得到文件的全路径
     * @param filePath
     * @return
     */
    public static String getFileNameWithFullPath(String filePath) {
        int i = filePath.lastIndexOf('/');
        int j = filePath.lastIndexOf("\\");
        int k;
        if (i >= j) {
            k = i;
        } else {
            k = j;
        }
        int n = filePath.lastIndexOf('.');
        if (n > 0) {
            return filePath.substring(k + 1, n);
        } else {
            return filePath.substring(k + 1);
        }
    }
    
    /**
     * 改变文件的扩展名
     * @param fileNM
     * @param ext
     * @return
     */
    public static String changeFileExt(String fileNM, String ext) {
        int i = fileNM.lastIndexOf('.');
        if (i >= 0)
            return (fileNM.substring(0, i) + ext);
        else
            return fileNM;
    }
    
    /**
     * 得到文件名(排除文件扩展名)
     * @param f
     * @return
     */
    public static String getFileNameWithoutExt(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(0, i);
            }
        }
        return null;
    }
    
    

}
