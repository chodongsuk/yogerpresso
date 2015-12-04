package com.kr.yogerpresso;


import java.util.ArrayList;
import java.util.HashMap;

import kr.ds.data.BaseResultListener;
import kr.ds.data.Data;
import kr.ds.data.DataHandler;
import kr.ds.utils.DsObjectUtils;
import kr.ds.utils.SharedPreference;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private Button mButton1, mButton2;
	private EditText mEditTextId, mEditTextPw;
	private DsObjectUtils mDsObjectUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDsObjectUtils = DsObjectUtils.getInstance(getApplicationContext());
		setContentView(R.layout.login);
		mEditTextId = (EditText)findViewById(R.id.editText_id);
		mEditTextPw = (EditText)findViewById(R.id.editText_pw);
				
		(mButton1 = (Button)findViewById(R.id.button1)).setOnClickListener(this);
		(mButton2 = (Button)findViewById(R.id.button2)).setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			if(!mDsObjectUtils.isEmpty(mEditTextId.getText().toString()) && !mDsObjectUtils.isEmpty(mEditTextPw.getText().toString())){
				HashMap<String, String> mHashMap = new HashMap<String, String>();
				mHashMap.put("md_id", mEditTextId.getText().toString());
				mHashMap.put("md_pw", mEditTextPw.getText().toString());
				if(mHashMap != null){
					new Data(getApplicationContext()).clear().setCallBack(new BaseResultListener() {
						@Override
						public <T> void OnComplete(T data) {
							// TODO Auto-generated method stub
							ArrayList<DataHandler> mData = (ArrayList<DataHandler>)data;
							new SharedPreference().putSharedPreference(getApplicationContext(), "md_uid", mData.get(mData.size()-1).getMd_uid());
							
							Intent intent = new Intent(LoginActivity.this, CuponActivity.class);
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
					}).setUrl(Config.LOIN_URL).getViewPost(mHashMap);
				}	
			}else{
				if(mDsObjectUtils.isEmpty(mEditTextId.getText().toString())){
					Toast.makeText(getApplicationContext(), "아이디를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}else if(mDsObjectUtils.isEmpty(mEditTextPw.getText().toString())){
					Toast.makeText(getApplicationContext(), "비밀번호를 입력 해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.button2:
			Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
			startActivity(intent);
			overridePendingTransition(0,0);
			finish();
			break;		
		}
	}
}
