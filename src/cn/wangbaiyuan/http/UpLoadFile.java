package cn.wangbaiyuan.http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpLoadFile {
 // 要上传的文件路径，理论上可以传输任何文件，实际使用时根据需要处理
 private String uploadFile = "/sdcard/testimg.jpg";
 private String srcPath = "/sdcard/testimg.jpg";
 // 服务器上接收文件的处理页面，这里根据需要换成自己的
 private String actionUrl = "http://weixin.wangbaiyuan.cn/api/receiveFile.php";

 /* 上传文件至Server，uploadUrl：接收文件的处理页面 */
 private void uploadFile(String uploadUrl)
 {
   String end = "\r\n";
   String twoHyphens = "--";
   String boundary = "******";
   try
   {
     URL url = new URL(uploadUrl);
     HttpURLConnection httpURLConnection = (HttpURLConnection) url
         .openConnection();
     // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
     // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
     httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
     // 允许输入输出流
     httpURLConnection.setDoInput(true);
     httpURLConnection.setDoOutput(true);
     httpURLConnection.setUseCaches(false);
     // 使用POST方法
     httpURLConnection.setRequestMethod("POST");
     httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
     httpURLConnection.setRequestProperty("Charset", "UTF-8");
     httpURLConnection.setRequestProperty("Content-Type",
         "multipart/form-data;boundary=" + boundary);

     DataOutputStream dos = new DataOutputStream(
         httpURLConnection.getOutputStream());
     dos.writeBytes(twoHyphens + boundary + end);
     dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
         + srcPath.substring(srcPath.lastIndexOf("/") + 1)
         + "\""
         + end);
     dos.writeBytes(end);

     FileInputStream fis = new FileInputStream(srcPath);
     byte[] buffer = new byte[8192]; // 8k
     int count = 0;
     // 读取文件
     while ((count = fis.read(buffer)) != -1)
     {
       dos.write(buffer, 0, count);
     }
     fis.close();

     dos.writeBytes(end);
     dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
     dos.flush();

     InputStream is = httpURLConnection.getInputStream();
     InputStreamReader isr = new InputStreamReader(is, "utf-8");
     BufferedReader br = new BufferedReader(isr);
     String result = br.readLine();

    
     dos.close();
     is.close();

   } catch (Exception e)
   {
     e.printStackTrace();
   }
 }
}