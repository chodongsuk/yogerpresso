package kr.ds.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import kr.ds.asynctask.DsAsyncTask;
import kr.ds.asynctask.DsAsyncTaskCallback;
import kr.ds.httpclient.DsHttpClient;
import kr.ds.utils.DsDebugUtils;
import kr.ds.utils.DsObjectUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import com.kr.yogerpresso.Config;

import android.content.Context;
import android.util.Log;


public class Data extends BaseData{
	private DsDebugUtils mDsDebugUtils;
	private Context mContext;
	private BaseResultListener mResultListener;
	private String URL = "";
	private String PARAM = "";
	private HashMap<String, String> mDataPost;
	private ArrayList<DataHandler> mData;
	private DataHandler mDataHandler;
	private String mMsg = "";
	public Data(Context context){
		mContext = context;
		mDsDebugUtils = DsDebugUtils.getInstance(mContext);
	}
	@Override
	public BaseData clear() {
		if (mData != null) {
			mData = null;
		}
		mData = new ArrayList<DataHandler>();
		if (mDataHandler != null) {
			mDataHandler = null;
		}
		mDataHandler = new DataHandler();
		return this;
	}

	@Override
	public BaseData setUrl(String url) {
		// TODO Auto-generated method stub
		if(DsObjectUtils.getInstance(mContext).isEmpty(URL)){
			URL = url;
		}
		return this;
	}

	@Override
	public BaseData setParam(String param) {
		// TODO Auto-generated method stub
		PARAM = param;
		return this;
	}

	@Override
	public BaseData getView() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public <T> BaseData setCallBack(T resultListener) {
		// TODO Auto-generated method stub
		mResultListener = (BaseResultListener) resultListener;
		return this;
	}

	@Override
	public <T> BaseData getViewPost(final T post) {
		// TODO Auto-generated method stub
		new DsAsyncTask<String>().setCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
            	String content = new DsHttpClient().HttpPost((HashMap<String, String>) post, URL + PARAM,"utf-8");
            	JSONObject jsonObject = new JSONObject(content);
            	JSONObject summeryjsonObject = jsonObject.getJSONObject("summery");
            	String result = summeryjsonObject.getString("result");
            	mMsg = summeryjsonObject.getString("msg");
            	if (result.matches("success")) {
            		JSONArray jsonArray1 = jsonObject.getJSONArray("data");
            		for(int i = 0; i < jsonArray1.length(); i++){
        				mData.add(new DataHandler());
        				if (mData.size() > 0) {
        					mDataHandler = mData.get(mData.size() - 1);
        					mDataHandler.setMd_uid(jsonArray1.getJSONObject(i).getString("md_uid"));
        					mDataHandler.setMd_id(jsonArray1.getJSONObject(i).getString("md_id"));
        					mDataHandler.setMd_pw(jsonArray1.getJSONObject(i).getString("md_pw"));
        					mDataHandler.setMd_name(jsonArray1.getJSONObject(i).getString("md_name"));
        					mDataHandler.setMd_tell(jsonArray1.getJSONObject(i).getString("md_tell"));
        					mDataHandler.setMd_state(jsonArray1.getJSONObject(i).getString("md_state"));
        					mDataHandler.setMd_regdate(jsonArray1.getJSONObject(i).getString("md_regdate"));
        					mDataHandler.setCupon_total(jsonArray1.getJSONObject(i).getString("cupon_total"));
        					mDataHandler.setCupon_use(jsonArray1.getJSONObject(i).getString("cupon_use"));
        				}
            		}
            	}
				return result;
            }
        }).setCallback(new DsAsyncTaskCallback<String>() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void onPostExecute(String result) {

				if (result.matches("success") ) {
					if (mResultListener != null) {
						if(mData != null && mData.size() > 0){
							mResultListener.OnComplete(mData);
						}
						mResultListener.OnMessage(mMsg);
						
					}
				}else if (result.matches("error") ) {
					if (mResultListener != null) {
						mResultListener.OnMessage(mMsg);
						
					}
				} 
            }

            @Override
            public void onCancelled() {
            }

            @Override
            public void Exception(Exception e) {
            	if (mResultListener != null) {
            		mResultListener.OnMessage(e.getMessage()+"");
                }
            }
        }).execute();
		return this;
	}



}
