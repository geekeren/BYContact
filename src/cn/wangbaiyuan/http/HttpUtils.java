package cn.wangbaiyuan.http;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.widget.Toast;
 
 
/**
 * @author ����Ԫ
 *
 */

public class HttpUtils {
  
    public HttpUtils() {
        // TODO Auto-generated constructor stub
    }
      
    /**
     * @param url_path json��URL
     * @return
     */
    public static String getJsonContent(String url_path){
         
        String jsonString = "";
        try {
            URL url = new URL(url_path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);  //�ӷ������������
            connection.connect();
            int responseCode = connection.getResponseCode();            
            Log.d("log_tag",responseCode+"");
            if (200 == responseCode) {
                jsonString = changeInputStream(connection.getInputStream());
                
            }
//            else{
//            	 jsonString = responseCode+"";
//            }
         
             
        } catch (Exception e) {
        	Log.d("log_tag",e.toString());
        	jsonString ="��ȡ��������Ϣ����";
            // TODO: handle exception
        }
         
    //
        return jsonString;
    }
 
    private static String changeInputStream(InputStream inputStream){
        // TODO Auto-generated method stub
        String  jsonString ="";
         
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
			while((len=inputStream.read(data))!=-1){
			    outputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        jsonString = new String(outputStream.toByteArray());
        return jsonString;
    }
     
}
