package com.kr.yogerpresso;

import kr.ds.data.DataHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class CuponActivity extends BaseActivity {
	private DataHandler mSavedata;
	private int cupon_use = 0; 
	private LinearLayout mLinearLayoutMain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null){
			mSavedata = savedInstanceState.getParcelable("data");
		}else{
			mSavedata = (DataHandler) getIntent().getParcelableExtra("data");
		}
		cupon_use = Integer.parseInt(mSavedata.getCupon_use());
		setContentView(R.layout.cupon);
		mLinearLayoutMain = (LinearLayout) findViewById(R.id.linearLayout);
		setCupon();
	}
	
	public int dipToInt(int number) {
		int num = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				number, getResources().getDisplayMetrics());
		return num;
	}
	public void setCupon() {
		int total = 10;
		int col = 5;
		int row = 2;

		LinearLayout linearLayout = new LinearLayout(getApplicationContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		TableLayout tableLayout = new TableLayout(getApplicationContext());
		tableLayout.setStretchAllColumns(true);
		
		tableLayout.setLayoutParams(new TableLayout.LayoutParams(
				TableLayout.LayoutParams.MATCH_PARENT,
				TableLayout.LayoutParams.MATCH_PARENT));
		tableLayout.setWeightSum(col);
		final LinearLayout mLinearLayout[] = new LinearLayout[total];

		for (int i = 0; i < row; i++) {
			TableRow tableRow = new TableRow(getApplicationContext());
			tableRow.setGravity(Gravity.LEFT);
			tableRow.setPadding(0, dipToInt(10), 0, dipToInt(10));
			tableRow.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
			for (int j = 0; j < col; j++) {
				int position = j + i * col;
				mLinearLayout[position] = new LinearLayout(getApplicationContext());
	        	mLinearLayout[position].setGravity(Gravity.CENTER);
	        	if(cupon_use > position){
	        		mLinearLayout[position].setBackgroundResource(R.drawable.on);
	        	}else{
	        		mLinearLayout[position].setBackgroundResource(R.drawable.off);
	        	}
	        	mLinearLayout[position].setTag(position);
	        	mLinearLayout[position].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int position = (Integer) v.getTag();
						if(cupon_use == position){
							Intent intent = new Intent(CuponActivity.this, BarCodeActivity.class);
							intent.putExtra("data", mSavedata);
							startActivityForResult(intent, 0);
						}else{
							Toast.makeText(getApplicationContext(), "잘못된 접근 방식입니다.", Toast.LENGTH_SHORT).show();
						}
					}
	        	});
	        	tableRow.addView(mLinearLayout[j + i * col]);
			}
			tableLayout.addView(tableRow);
		}
		linearLayout.addView(tableLayout);
		mLinearLayoutMain.addView(linearLayout);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("data",mSavedata);
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 1:
			cupon_use = data.getIntExtra("use_total", 0);
			mLinearLayoutMain.removeAllViews();
			setCupon();
			break;

		default:
			break;
		}
	}
}
