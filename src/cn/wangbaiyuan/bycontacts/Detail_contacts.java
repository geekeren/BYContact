package cn.wangbaiyuan.bycontacts;

import cn.wangbaiyuan.contacts.R;
import cn.wangbaiyuan.contacts.R.id;
import cn.wangbaiyuan.contacts.R.layout;
import cn.wangbaiyuan.contacts.R.menu;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Detail_contacts extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_contacts);
	ListView detailListview=(ListView)findViewById(R.id.detailListview);
	String contacts_id=getIntent().getStringExtra("contacts_id");
	Log.e("cont",contacts_id+"_Detail_contacts");
		Contacts detailContatcts=new Contacts(this, contacts_id);
		String name=detailContatcts.getName();
		String email=detailContatcts.getEmail();
		String phoneNumber=detailContatcts.getPhoneNumber().get(0);
		EditText contact_name=(EditText)findViewById(R.id.contact_name);
		contact_name.setText(name);
		
		BaseAdapter adapter=new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_contacts, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
