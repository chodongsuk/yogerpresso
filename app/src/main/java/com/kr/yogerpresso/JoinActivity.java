package com.kr.yogerpresso;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ds.data.BaseResultListener;
import kr.ds.data.Data;
import kr.ds.data.DataHandler;
import kr.ds.data.IdCheckData;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;
import kr.ds.utils.StringUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends BaseActivity implements OnClickListener{
	private Button mButton1, mButton2, mButtonId;
	private EditText mEditTextId, mEditTextPw, mEditTextName, mEditTextTell;
	private DsObjectUtils mDsObjectUtils;
	private boolean is_idcheck = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDsObjectUtils = DsObjectUtils.getInstance(getApplicationContext());
		setContentView(R.layout.join);
		(mButtonId = (Button)findViewById(R.id.button_id)).setOnClickListener(this);
		(mButton1 = (Button)findViewById(R.id.button1)).setOnClickListener(this);
		(mButton2 = (Button)findViewById(R.id.button2)).setOnClickListener(this);
		
		mEditTextId = (EditText)findViewById(R.id.editText_id);
		mEditTextPw = (EditText)findViewById(R.id.editText_pw);
		mEditTextName = (EditText)findViewById(R.id.editText_name);
		mEditTextTell = (EditText)findViewById(R.id.editText_tell);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_id:
			HashMap<String, String> mHashMap1 = new HashMap<String, String>();
			mHashMap1.put("md_id", mEditTextId.getText().toString());
			if(mHashMap1 != null){
				new IdCheckData(getApplicationContext()).clear().setCallBack(new BaseResultListener() {
					@Override
					public <T> void OnComplete(T data) {
						// TODO Auto-generated method stub
						String mData = (String)data;
						if(mData.matches("success")){
							is_idcheck = true;
						}else{
							is_idcheck = false;
							mEditTextId.setText("");
						}
					}
					@Override
					public void OnMessage(String str) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
					}
					
				}).setUrl(Config.ID_CHECK_URL).getViewPost(mHashMap1);
			}	
			
			
			break;
		case R.id.button1:
			if(!mDsObjectUtils.isEmpty(mEditTextId.getText().toString()) && is_idcheck && !mDsObjectUtils.isEmpty(mEditTextPw.getText().toString())&& !mDsObjectUtils.isEmpty(mEditTextName.getText().toString())&& !mDsObjectUtils.isEmpty(mEditTextTell.getText().toString())){
				HashMap<String, String> mHashMap2 = new HashMap<String, String>();
				mHashMap2.put("md_id", mEditTextId.getText().toString());
				mHashMap2.put("md_pw", mEditTextPw.getText().toString());
				mHashMap2.put("md_name", mEditTextName.getText().toString());
				mHashMap2.put("md_tell", mEditTextTell.getText().toString());
				if(mHashMap2 != null){
					new Data(getApplicationContext()).clear().setCallBack(new BaseResultListener() {
						@Override
						public <T> void OnComplete(T data) {
							// TODO Auto-generated method stub
							ArrayList<DataHandler> mData = (ArrayList<DataHandler>)data;
							new SharedPreference().putSharedPreference(getApplicationContext(), "md_uid", mData.get(mData.size()-1).getMd_uid());
							
							Intent intent = new Intent(JoinActivity.this, CuponActivity.class);
							intent.putExtra("data", mData.get(mData.size()-1));
							startActivity(intent);
							overridePendingTransition(0,0);
							finish();
						}
						@Override
						public void OnMessage(String str) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
						}
						
					}).setUrl(Config.JOIN_URL).getViewPost(mHashMap2);
				}	
			}else{
				if(mDsObjectUtils.isEmpty(mEditTextId.getText().toString())){
					Toast.makeText(getApplicationContext(), "아이디를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}else if(is_idcheck == false){
					Toast.makeText(getApplicationContext(), "Id 중복확인 체크 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}else if(mDsObjectUtils.isEmpty(mEditTextPw.getText().toString())){
					Toast.makeText(getApplicationContext(), "비밀번호를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}else if(mDsObjectUtils.isEmpty(mEditTextName.getText().toString())){
					Toast.makeText(getApplicationContext(), "이름를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}else if(mDsObjectUtils.isEmpty(mEditTextTell.getText().toString())){
					Toast.makeText(getApplicationContext(), "전화번호를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.button2:
			Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
			startActivity(intent);
			overridePendingTransition(0,0);
			finish();
			break;		
		}
	}
	

}
