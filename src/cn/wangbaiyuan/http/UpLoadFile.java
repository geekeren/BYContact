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
 // Ҫ�ϴ����ļ�·���������Ͽ��Դ����κ��ļ���ʵ��ʹ��ʱ������Ҫ����
 private String uploadFile = "/sdcard/testimg.jpg";
 private String srcPath = "/sdcard/testimg.jpg";
 // �������Ͻ����ļ��Ĵ���ҳ�棬���������Ҫ�����Լ���
 private String actionUrl = "http://weixin.wangbaiyuan.cn/api/receiveFile.php";

 /* �ϴ��ļ���Server��uploadUrl�������ļ��Ĵ���ҳ�� */
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
     // ����ÿ�δ��������С��������Ч��ֹ�ֻ���Ϊ�ڴ治�����
     // �˷���������Ԥ�Ȳ�֪�����ݳ���ʱ����û�н����ڲ������ HTTP �������ĵ�����
     httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
     // �������������
     httpURLConnection.setDoInput(true);
     httpURLConnection.setDoOutput(true);
     httpURLConnection.setUseCaches(false);
     // ʹ��POST����
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
     // ��ȡ�ļ�
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