/******************************************************************************
 * Copyright 2013
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jpac.remaster.gtc.core;

import jpac.remaster.gtc.GTCSplash;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/******************************************************************************
 * Superclass for all activity in this application. All activities must extend
 * this class to ensure fullscreen feature all throughout.
 * 
 * In debug mode, this class will log in four different stages of an activity
 * lifecycle: onCreate, onPause, onDestroy and onStop.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
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
		Util.log(getClassName() + "[onStop]");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isLaunchedFromHistory()) {
			// The activity was launched from history
			startActivity(new Intent(this, GTCSplash.class));
			this.finish();
		}

		setFullscreenWindow();

		ResourceManager.setContextReference(this);
		Util.log(this.getClass().getName() + "[onCreate]");
	}

	/**************************************************************************
	 * Checks if this activity is launched from history, probably when user
	 * opens the game from Recent Application.
	 *************************************************************************/
	private boolean isLaunchedFromHistory() {
		int flags = getIntent().getFlags();
		return (flags & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;
	}

	/**************************************************************************
	 * Sets the display window to fullscreen. This will remove the title bar at
	 * the top of the application as well as the notification bar for lower
	 * Android SDK level.
	 *************************************************************************/
	private void setFullscreenWindow() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**************************************************************************
	 * Returns the name of this activity.
	 *************************************************************************/
	private String getClassName() {
		return this.getClass().getName();
	}

}
