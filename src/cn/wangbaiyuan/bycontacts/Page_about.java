package cn.wangbaiyuan.bycontacts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import cn.wangbaiyuan.contacts.R;
import cn.wangbaiyuan.http.HttpUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ʵ�������Ϣ��չʾ��������¹���
 * @author ����Ԫ
 *
 */
public class Page_about extends Activity {

	String sw_version;
	String current_version;
	TextView version_label;
	String version_des;
	String download_url;
	Handler hander=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0x123){
				//version_label.setText("���°汾��"+sw_version);
				Double latest_Version=Double.parseDouble(sw_version);
				if(Double.parseDouble(current_version)<latest_Version)
				{

					version_label.setText("���°汾��"+sw_version);
				AlertDialog.Builder builder=new AlertDialog.Builder(Page_about.this)
				.setTitle("�汾����")
				.setMessage(version_des);
				builder.setPositiveButton("���ظ���", download_listener);
				builder.setNegativeButton("�Ժ���˵", null);
				builder.create();
				builder.show();
				}
				else
					Toast.makeText(Page_about.this, "��ǰ�����°汾", 5).show();
			}
			else if(msg.what==012)
			Toast.makeText(Page_about.this, "��װ��������ɣ����밲װ����", Toast.LENGTH_LONG).show();
            //���toast���ļ�������Ϊ��������ǵ��̵߳ģ�����Ҫ�������ļ��Ժ�Ż�ִ����һ�䣬�м��ʱ���������������������̻߳�û��ѧ��
			
			
		}
	};
	OnClickListener download_listener =new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) { 
			new Thread(download).start();

			Toast.makeText(Page_about.this, "��ʼ���ء���", Toast.LENGTH_LONG).show();
		}
	};
	Runnable download = new Runnable(){ 
		  
			@Override  
			public void run() {  
				String sdcard=Environment.getExternalStorageDirectory()+"/";
				String filepath=sdcard+"BYContacts/";
				
				download_url=(download_url.startsWith("http://"))?download_url:"http://"+download_url;
			    try {
				  URL url = new URL(download_url);
	               //�򿪵�url������
	             HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	              //����Ϊjava IO���֣�������˵�����ȼ���ļ����Ƿ���ڣ��������򴴽�,Ȼ����ļ����ظ����⣬û�п���
	             InputStream istream=connection.getInputStream();
	               String filename=download_url.substring(download_url.lastIndexOf("/")+1);
	         
	               File dir=new File(filepath);
	                if (!dir.exists()) {
	                    dir.mkdir();
	               }
	                File file=new File(filepath+filename);
	               file.createNewFile();	
	               
	               OutputStream output=new FileOutputStream(file);
	           	byte[] buff = new byte[1024];
				int hasRead = 0;
				// ��URL��Ӧ����Դ���ص�����
				while((hasRead = istream.read(buff)) > 0)
				{
					output.write(buff, 0 , hasRead);
				}
	                output.flush();
	                output.close();
	                istream.close();
	                hander.sendEmptyMessage(012);
	                Log.e("log_tag",file.toString());

	                openFile(file);
	            } catch (Exception e) {
	           	 Log.e("log_tag","���ش���"+e.toString());
	               e.printStackTrace();
	           }

				
				
				
       } 
	};
	Runnable checkupdate = new Runnable(){  
		  
		@Override  
		public void run() {  
			String url_path = "http://weixin.wangbaiyuan.cn/api/update.php?id=1";
	    	
	        String jsonString = HttpUtils.getJsonContent(url_path);
	       
	        try {
				JSONObject Json=new JSONObject(jsonString);
				sw_version=Json.getString("sw_version");
				 version_des=Json.getString("sw_description");
				 download_url=Json.getString("sw_url");
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 Log.e("log_tag",e.toString());
			}
	        hander.sendEmptyMessage(0x123);	
		}  
		
		  }; 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		version_label=(TextView)findViewById(R.id.textView1);
		
		try {
		       PackageManager packageManager = getPackageManager();
		        // getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0); 
		        current_version = packInfo.versionName;
		        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
		        version_label.setText(applicationName+current_version);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        	
	}
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }
		  @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		       
		        int id = item.getItemId();
		        if (id == R.id.action_checkupdate) {
		        	new Thread(checkupdate).start();
		        	Toast.makeText(Page_about.this, "���ڼ����¡���", 2).show();
		        }
		        return super.onOptionsItemSelected(item);
		    }
		    private void openFile(File file) {
		        Log.e("OpenFile", file.getName());
		        Intent intent = new Intent();
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        intent.setAction(android.content.Intent.ACTION_VIEW);
		        intent.setDataAndType(Uri.fromFile(file),
		                "application/vnd.android.package-archive");
		        startActivity(intent);
		    }
}
