package com.carporange.cloudmusic.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.hannesdorfmann.swipeback.R;

/**
 * Created by liuhui on 2016/6/27.
 */
public class SwipeBackActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle saved){
		super.onCreate(saved);

		Log.d("SwipeBack", "onCreate");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.d("SwipeBack", "onDestroy");

	}


	/*@Override
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.swipeback_slide_left_in,
				R.anim.swipeback_slide_right_out);
	}*/

	/**
	 * ToolBar中的返回按钮对应事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if (item.getItemId() == android.R.id.home){
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
