package indi.cyken.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * mp3转pcm工具类
 * @author Yong
 *
 */
public class FFmpegUtil {
	
	public static void vedioToPcm(String vedioUrl,String pcmUrl,String ffmpegUrl) throws InterruptedException, IOException {
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegUrl);
		commend.add("-y");
		commend.add("-i");
		commend.add(vedioUrl);
		commend.add("-acodec");
		commend.add("pcm_s16le");
		commend.add("-f");
		commend.add("s16le");
		commend.add("-ac");
		commend.add("1");
		commend.add("-ar");
		commend.add("16000");
		commend.add(pcmUrl);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(commend);
		builder.redirectErrorStream(true);
		Process process = builder.start();
		process.waitFor();// 等待进程执行结束
		
	}	
	
	
	   public static void changeAmrToMp3(String sourcePath, String targetPath) throws Exception {
	        //String webroot = "c:\\ffmpeg\\bin";
		   String webroot = "G:\\code\\ffmpeg\\ffmpeg\\bin";
	        Runtime run = null;
	        try {
	            run = Runtime.getRuntime();
	            long start=System.currentTimeMillis();
	            System.out.println(new File(webroot).getAbsolutePath());
	            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
	            //wav转pcm
	            //Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
	            //mp3转pcm
	            Process p=run.exec(new File(webroot).getAbsolutePath()+"/ffmpeg -y -i "+sourcePath+" -acodec pcm_s16le -f s16le -ac 1 -ar 16000 "+targetPath);
	            //释放进程
	            p.getOutputStream().close();
	            p.getInputStream().close();
	            p.getErrorStream().close();
	            p.waitFor();
	            long end=System.currentTimeMillis();
	            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            //run调用lame解码器最后释放内存
	            run.freeMemory();
	        }
	    }

}
