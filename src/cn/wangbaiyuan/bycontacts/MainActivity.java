package cn.wangbaiyuan.bycontacts;
import cn.wangbaiyuan.contacts.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class MainActivity extends TabActivity
{
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		    Window window = getWindow();
		    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
		    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_main);
		// 获取该Activity里面的TabHost组件
		final TabHost tabHost = getTabHost();
		
		// 创建第一个Tab页
		TabSpec tab1 = tabHost.newTabSpec(getResources().getString(R.string.dialer))
			.setIndicator(getResources().getString(R.string.dialer)) // 设置标题
			.setContent(new Intent(this,Tab_dailer.class)); //设置内容
		// 添加第一个标签页
		tabHost.addTab(tab1);
		TabSpec tab2 = tabHost.newTabSpec(getResources().getString(R.string.contacts))
			// 在标签标题上放置图标
			.setIndicator(getResources().getString(R.string.contacts), getResources()
					.getDrawable(R.drawable.ic_launcher))
			.setContent(new Intent(this,Tab_contacts.class)); //设置内容
		// 添加第二个标签页
		tabHost.addTab(tab2);
		TabSpec tab3 = tabHost.newTabSpec(getResources().getString(R.string.messages))
				.setIndicator(getResources().getString(R.string.messages)) // 设置标题
				.setContent(new Intent(this,Tab_message.class)); //设置内容
			// 添加第三个标签页
			tabHost.addTab(tab3);
			tabHost.setOnTabChangedListener(new OnTabChangeListener(){ 
				@Override public void onTabChanged(String tabId) {
					//setTitle(""+tabId); 显示tabid //
					setTitle(""+tabHost.getCurrentTabTag());
				}
			});
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
        case R.id.action_exit:
        	finish();
        	break;
        case R.id.action_about:
        	Intent intent=new Intent(MainActivity.this,Page_about.class);
        	startActivity(intent);
        }
      
        return super.onOptionsItemSelected(item);
    }

}
