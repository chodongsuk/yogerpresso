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


public class IdCheckData extends BaseData{
	private DsDebugUtils mDsDebugUtils;
	private Context mContext;
	private BaseResultListener mResultListener;
	private String URL = "";
	private String PARAM = "";
	private HashMap<String, String> mDataPost;
	private String mMsg = "";
	public IdCheckData(Context context){
		mContext = context;
		mDsDebugUtils = DsDebugUtils.getInstance(mContext);
	}
	@Override
	public BaseData clear() {
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
						mResultListener.OnComplete(result);
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
