package cn.wangbaiyuan.bycontacts;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.wangbaiyuan.http.HttpUtils;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 王柏�? on 2015/4/4.
 */
public class myapi extends Activity {

    public String getVersionName() throws Exception
    {
        // 获取packagemanager的实�?
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名�?0代表是获取版本信�?
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
       
        String version = packInfo.versionName;
        return version;
    }

}
