package com.kr.yogerpresso;

import kr.ds.utils.DsScreenUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code128;
import com.onbarcode.barcode.android.IBarcode;

public class AndroidBarcodeView extends View {

    private String mData;
    private Context mContext;
    private DsScreenUtils mDsScreenUtils = DsScreenUtils.getInstance();
    public AndroidBarcodeView(Context context, String data) {
        super(context);
        mData = data;
        mContext = context;
        mDsScreenUtils.initScreenSize(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            androidUPC(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void androidUPC(Canvas canvas) throws Exception {
    	Code128 barcode = new Code128();
    	/*
    	   Code 128 Valid data char set:
    	        all 128 ASCII characters (Char from 0 to 127)
    	*/
    	barcode.setData(mData);
    	//  Set the processTilde property to true, if you want use the tilde character "~"
    	//  to specify special characters in the input data. Default is false.
    	//  1) All 128 ISO/IEC 646 characters, i.e. characters 0 to 127 inclusive, in accordance with ISO/IEC 646.
    	//       NOTE This version consists of the G0 set of ISO/IEC 646 and the C0 set of ISO/IEC 6429 with values 28 - 31
    	//       modified to FS, GS, RS and US respectively.
    	//  2) Characters with byte values 128 to 255 may also be encoded.
    	//  3) 4 non-data function characters.
    	//  4) 4 code set selection characters.
    	//  5) 3 Start characters.
    	//  6) 1 Stop character.
    	barcode.setProcessTilde(true);
    	// Unit of Measure, pixel, cm, or inch
    	barcode.setUom(IBarcode.UOM_PIXEL);
    	// barcode bar module width (X) in pixel
    	barcode.setX(15);
    	// barcode bar module height (Y) in pixel
    	barcode.setY((mDsScreenUtils.getScreenHeight()-mDsScreenUtils.getStatusBarHeight(mContext)-mDsScreenUtils.dipConvertPx(50))/2);

    	barcode.setBarcodeWidth(mDsScreenUtils.getScreenWidth());
    	barcode.setBarcodeHeight(mDsScreenUtils.getScreenHeight()-mDsScreenUtils.getStatusBarHeight(mContext)-mDsScreenUtils.dipConvertPx(50));
    	barcode.setBarAlignment(1);
    	
    	barcode.setLeftMargin(10f);
    	barcode.setRightMargin(10f);
    	barcode.setTopMargin(10f);
    	barcode.setBottomMargin(10f);
    	
    	// barcode image resolution in dpi
    	barcode.setResolution(72);
    	
    	// disply barcode encoding data below the barcode
    	barcode.setShowText(true);
    	// barcode encoding data font style
    	barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, mDsScreenUtils.dipConvertPx(16)));
    	// space between barcode and barcode encoding data
    	barcode.setTextMargin(6);
    	barcode.setTextColor(AndroidColor.black);
    	
    	// barcode bar color and background color in Android device
    	barcode.setForeColor(AndroidColor.black);
    	barcode.setBackColor(AndroidColor.white);
    	/*
    	specify your barcode drawing area
    	    */
    	RectF bounds = new RectF(0, 0, 0, 0);
    	barcode.drawBarcode(canvas, bounds);
     
    }
}
