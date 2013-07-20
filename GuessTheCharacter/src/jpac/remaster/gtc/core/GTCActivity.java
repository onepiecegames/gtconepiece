package jpac.remaster.gtc.core;

import jpac.remaster.gtc.GTCSplash;
import jpac.remaster.gtc.util.ResourceUtil;
import jpac.remaster.gtc.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class GTCActivity extends Activity {

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Util.log(this.getClass().getName() + "[onDestroy]");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Util.log(this.getClass().getName() + "[onPause]");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Util.log(this.getClass().getName() + "[onStop]");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int flags = getIntent().getFlags();
		if ((flags & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
			// The activity was launched from history
			startActivity(new Intent(this, GTCSplash.class));
			this.finish();
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		ResourceUtil.setContextRef(this);
		Util.log(this.getClass().getName() + "[onCreate]");
	}
}
