package cn.wangbaiyuan.bycontacts;

import cn.wangbaiyuan.contacts.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Description:
 * <br/>website: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class Tab_dailer extends Activity
{
	GridLayout gridLayout;
	TextView phone_number;
	Button clear_number;
	Button call;
	// ����16����ť���ı�
	String[] chars = new String[]
		{
			"7" , "8" , "9",
			"4" , "5" , "6",
			"1" , "2" , "3",
			"*" , "0" , "#"
		};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bohao_layout);
		gridLayout = (GridLayout) findViewById(R.id.root);
		phone_number=(TextView)findViewById(R.id.phone_number);
		clear_number=(Button)findViewById(R.id.clear_number);
		call=(Button)findViewById(R.id.call);
		clear_number.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				phone_number.setText("");
			}
		});
call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
				String number=phone_number.getText().toString();
		          
		           if(number != null && !number.equals(""))
		           {
		               //����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
		               //��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
		               Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+number));
		               startActivity(intent);//�ڲ���
		           }
			}
		});
		for(int i = 0 ; i < chars.length ; i++)
		{
			Button bn = new Button(this);
			bn.setText(chars[i]);
			bn.setTag(chars[i]);
			// ���øð�ť�������С
			bn.setTextSize(30);
			
			bn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					String number=phone_number.getText().toString();
					number+=v.getTag().toString();
					phone_number.setText(number);
				}
			});
			// ָ����������ڵ���
			GridLayout.Spec rowSpec = GridLayout.spec(i / 3 );
			// ָ�������������
			GridLayout.Spec columnSpec = GridLayout.spec(i % 3);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(
					rowSpec , columnSpec);
			// ָ�������ռ��������
			
			gridLayout.addView(bn , params);
		}
	}
}
