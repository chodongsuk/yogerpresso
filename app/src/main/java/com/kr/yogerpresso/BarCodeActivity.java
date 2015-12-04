package com.kr.yogerpresso;

import java.util.HashMap;

import kr.ds.data.BaseResultListener;
import kr.ds.data.CuponData;
import kr.ds.data.DataHandler;
import kr.ds.data.IdCheckData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


public class BarCodeActivity extends Activity implements OnClickListener{
	private String md_uid = "0";
	private DataHandler mSavedata;
	private LinearLayout mLinearLayoutMain;
	private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
			mSavedata = savedInstanceState.getParcelable("data");
		}else{
			mSavedata = (DataHandler) getIntent().getParcelableExtra("data");
		}
        md_uid = mSavedata.getMd_uid();
        AndroidBarcodeView androidBarcodeView = new AndroidBarcodeView(BarCodeActivity.this, md_uid);
        setContentView(R.layout.barcode);
        mLinearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout);
        mLinearLayoutMain.addView(androidBarcodeView);
        (mButton = (Button)findViewById(R.id.button)).setOnClickListener(this);
    }
    @Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("data",mSavedata);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button:
			HashMap<String, String> mHashMap1 = new HashMap<String, String>();
			mHashMap1.put("md_uid", md_uid);
			if(mHashMap1 != null){
				new CuponData(getApplicationContext()).clear().setCallBack(new BaseResultListener() {
					@Override
					public <T> void OnComplete(T data) {
						// TODO Auto-generated method stub
						String mData = (String)data;
						if(!mData.matches("")){
							Intent intent = new Intent();
							intent.putExtra("use_total", Integer.parseInt(mData));
							setResult(1, intent);
							finish();
						}
					}
					@Override
					public void OnMessage(String str) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
					}
					
				}).setUrl(Config.CUPON_URL).getViewPost(mHashMap1);
			}	
			break;
		}
	}

    
}
