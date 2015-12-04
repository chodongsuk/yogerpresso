package kr.ds.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

public class DsScreenUtils {

	public static int screenWidthPx = 0;

	public static int screenHeightPx = 0;

	public static float densityPx = 0;

	private static DsScreenUtils screenUtils = null;

	public DsScreenUtils() {
	}

	public static DsScreenUtils getInstance() {
		if (screenUtils == null) {
			synchronized (DsScreenUtils.class) {
				if (screenUtils == null) {
					screenUtils = new DsScreenUtils();
				}
			}
		}

		return screenUtils;
	}

	@SuppressLint("NewApi")
	public static void initScreenSize(Context mContext) {
		final DisplayMetrics dm = new DisplayMetrics();
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			((Activity) mContext).getWindowManager().getDefaultDisplay()
					.getRealMetrics(dm);
			screenWidthPx = dm.widthPixels;
			screenHeightPx = dm.heightPixels;
			densityPx = dm.density;
		} else {
			((Activity) mContext).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			screenWidthPx = dm.widthPixels;
			screenHeightPx = dm.heightPixels;
			densityPx = dm.density;
		}
	}

	public int getScreenWidth() {
		return screenWidthPx;
	}
	public int getScreenHeight() {
		return screenHeightPx;
	}

	public static int dipConvertPx(int size) {
		return (int) (size * densityPx);
	}
	public static int getStatusBarHeight(Context mContext){ 
	    int statusHeight = 0;
	    int screenSizeType = (mContext.getResources().getConfiguration().screenLayout &
	            Configuration.SCREENLAYOUT_SIZE_MASK);
	    if(screenSizeType != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
	        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
	        if (resourceId > 0) {
	            statusHeight = mContext.getResources().getDimensionPixelSize(resourceId);
	        }
	    }
	     
	    return statusHeight;
	}
	public static int VersionCode(Context context) {
		int VersionCode = 1;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(
					context.getPackageName(), 0);
			VersionCode = packageInfo.versionCode; 
			return VersionCode;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static String VersionName(Context context) {
		String VersionName = null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(
					context.getPackageName(), 0);
			VersionName = packageInfo.versionName; 
			return VersionName;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
