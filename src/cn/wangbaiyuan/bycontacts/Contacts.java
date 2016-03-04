package cn.wangbaiyuan.bycontacts;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class Contacts {
private String contacts_id;
private String name;
private ArrayList<String> phoneNumber;
private String email;
private String address;
private ContentResolver contentResolver;
public Contacts(){
}

/**
 * ��ϵ�˹��캯��
 * @param number ����id��ѯ��ϵ��
 */
public Contacts(Context context,String id){
	contacts_id=id;
	contentResolver=context.getContentResolver();
	Cursor phoneCursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
			ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"='"+contacts_id+"'", null, null);
	Log.e("cont",ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"='"+contacts_id+"'"+phoneCursor.getColumnCount());
	Log.e("cont",contacts_id+"_Contacts");
	while (phoneCursor.moveToNext())
	{
		// ��ȡ��ѯ����е绰�����������ݡ�
		String Number = phoneCursor.getString(phoneCursor
			.getColumnIndex(ContactsContract
			.CommonDataKinds.Phone.NUMBER));
		phoneNumber.add("�绰���룺" + phoneNumber);
	}

	Log.e("cont","cont");
	name=phoneCursor.getString(phoneCursor.getColumnIndex(Phone.DISPLAY_NAME));
	phoneCursor.close();
	Cursor emailCursor=contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
			ContactsContract.CommonDataKinds.Email.CONTACT_ID+"="+contacts_id,null, null);
	email=emailCursor.getString(phoneCursor.getColumnIndex(Email.DATA));
	emailCursor.close();
}
public String getName(){
	return name;
}
public ArrayList<String> getPhoneNumber(){
	return phoneNumber;
}
public String getEmail(){
	return email;
}
}
