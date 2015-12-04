package com.kr.yogerpresso;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

	public class BackPressCloseHandler {

		private long backKeyPressedTime = 0;
		private Toast toast;
		private Activity activity;

		public BackPressCloseHandler(Activity context) {
			this.activity = context;
		}

		public void onBackPressed() {
			if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
				backKeyPressedTime = System.currentTimeMillis();
				showGuide();
				return;
			}

			if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
				activity.finish();
				toast.cancel();
			}
		}
		private void showGuide() {
			toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
			Toast.LENGTH_SHORT);
			toast.show();

		}
	}
	
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_BACK) {
				backPressCloseHandler.onBackPressed();
				return true;
			}
		}
		return super.onKeyDown(KeyCode, event);
	}

}
