package kr.ds.utils;

import java.util.List;
import java.util.Map;

import android.content.Context;

/**
 * NULL 유틸
 * 
 * @author Chodongsuk
 * @since 2015.02.02
 * public DsObjectUtils mDsDebugUtils = DsObjectUtils.getInstance(getActivity());
 */
public class DsObjectUtils {
	
	
	private static DsObjectUtils objectUtils = null;
	private Context mContext = null;

	public DsObjectUtils(Context context) {
		mContext = context;
	}

	public static DsObjectUtils getInstance(Context context) {
		if (objectUtils == null) {
			synchronized (DsObjectUtils.class) {
				if (objectUtils == null) {
					objectUtils = new DsObjectUtils(
							context.getApplicationContext());
				}
			}
		}
		return objectUtils;
	}
	
	public boolean isEmpty(Object s) {
		if (s == null) {
			return true;
		}
		if ((s instanceof String) && (((String) s).trim().length() == 0)) {
			return true;
		}
		if (s instanceof Map) {
			return ((Map<?, ?>) s).isEmpty();
		}
		if (s instanceof List) {
			return ((List<?>) s).isEmpty();
		}
		return false;
	}
}
