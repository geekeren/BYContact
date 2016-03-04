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
 * 实现软件信息的展示和软件更新功能
 * @author 王柏元
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
				//version_label.setText("最新版本："+sw_version);
				Double latest_Version=Double.parseDouble(sw_version);
				if(Double.parseDouble(current_version)<latest_Version)
				{

					version_label.setText("最新版本："+sw_version);
				AlertDialog.Builder builder=new AlertDialog.Builder(Page_about.this)
				.setTitle("版本更新")
				.setMessage(version_des);
				builder.setPositiveButton("下载更新", download_listener);
				builder.setNegativeButton("以后再说", null);
				builder.create();
				builder.show();
				}
				else
					Toast.makeText(Page_about.this, "当前是最新版本", 5).show();
			}
			else if(msg.what==012)
			Toast.makeText(Page_about.this, "安装包下载完成，进入安装……", Toast.LENGTH_LONG).show();
            //最后toast出文件名，因为这个程序是单线程的，所以要下载完文件以后才会执行这一句，中间的时间类似于死机，不过多线程还没有学到
			
			
		}
	};
	OnClickListener download_listener =new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) { 
			new Thread(download).start();

			Toast.makeText(Page_about.this, "开始下载……", Toast.LENGTH_LONG).show();
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
	               //打开到url的连接
	             HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	              //以下为java IO部分，大体来说就是先检查文件夹是否存在，不存在则创建,然后的文件名重复问题，没有考虑
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
				// 将URL对应的资源下载到本地
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
	           	 Log.e("log_tag","下载错误"+e.toString());
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
		        // getPackageName()是你当前类的包名，0代表是获取版本信息
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
		        	Toast.makeText(Page_about.this, "正在检查更新……", 2).show();
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
