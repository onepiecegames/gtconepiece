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
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

	/* View Related Methods */
	
	/**************************************************************************
	 * Set the visibility of a specified view.
	 *************************************************************************/
	public void setVisibility(int id, int visibility) {
		View view = findViewById(id);
		
		if (view != null) {
			view.setVisibility(visibility);
		}
	}
	
	/**************************************************************************
	 * Set the text of a specified TextView or Button.
	 *************************************************************************/
	public void setText(int id, String text) {
		View view = findViewById(id);
		
		if (view != null) {
			if (view instanceof TextView) {
				((TextView) view).setText(text);
			} else if (view instanceof Button) {
				((Button) view).setText(text);
			}
		}
	}

	/**************************************************************************
	 * Set the font for a specified TextView or Button.
	 *************************************************************************/
	public void setTypeface(int id, Typeface font) {
		View view = findViewById(id);
		
		if (view != null) {
			if (view instanceof TextView) {
				((TextView) view).setTypeface(font);
			} else if (view instanceof Button) {
				((Button) view).setTypeface(font);
			}
		}
	}
	
	/**************************************************************************
	 * Set an onClickListener for a specified view.
	 *************************************************************************/
	public void setOnClickListener(int id, OnClickListener listener) {
		findViewById(id).setOnClickListener(listener);
	}
	
	/**************************************************************************
	 * Set the background drawable of a specified view.
	 *************************************************************************/
	public void setBackground(int id, int bg) {
		findViewById(id).setBackgroundResource(bg);
	}
	
	/**************************************************************************
	 * Set the image source of the specified ImageView or ImageButton.
	 *************************************************************************/
	public void setImage(int id, Bitmap image) {
		View view = findViewById(id);
		
		if (view != null) {
			if (view instanceof ImageView) {
				((ImageView) view).setImageBitmap(image);
			} else if (view instanceof ImageButton) {
				((ImageButton) view).setImageBitmap(image);
			}
		}
	}

	/**************************************************************************
	 * Set the image drawable of the specified ImageView or ImageButton.
	 *************************************************************************/
	public void setImageResource(int id, int resId) {
		View view = findViewById(id);
		
		if (view != null) {
			if (view instanceof ImageView) {
				((ImageView) view).setImageResource(resId);
			} else if (view instanceof ImageButton) {
				((ImageButton) view).setImageResource(resId);
			}
		}
	}
	
	/**************************************************************************
	 * Start an animation for the specified view.
	 *************************************************************************/
	public void startAnimation(int id, Animation anim) {
		findViewById(id).startAnimation(anim);
	}
	
	/**************************************************************************
	 * Returns a TextView given a resource id. Make sure that the id supplied
	 * is of a TextView to prevent ClassCastException.
	 *************************************************************************/
	public TextView getTextView(int id) {
		return (TextView) findViewById(id);
	}

	/**************************************************************************
	 * Returns a Button given a resource id. Make sure that the id supplied is
	 * of a Button to prevent ClassCastException.
	 *************************************************************************/	
	public Button getButton(int id) {
		return (Button) findViewById(id);
	}

	/**************************************************************************
	 * Returns a ImageButton given a resource id. Make sure that the id
	 * supplied is of an ImageButton to prevent ClassCastException.
	 *************************************************************************/
	public ImageButton getImageButton(int id) {
		return (ImageButton) findViewById(id);
	}

	/**************************************************************************
	 * Returns a ImageView given a resource id. Make sure that the id supplied
	 * is of a ImageView to prevent ClassCastException.
	 *************************************************************************/
	public ImageView getImageView(int id) {
		return (ImageView) findViewById(id);
	}

	/**************************************************************************
	 * Returns a WebView given a resource id. Make sure that the id supplied
	 * is of a WebView to prevent ClassCastException.
	 *************************************************************************/
	public WebView getWebView(int id) {
		return (WebView) findViewById(id);
	}

	/**************************************************************************
	 * Returns a ViewFlipper given a resource id. Make sure that the id
	 * supplied is of a ViewFlipper to prevent ClassCastException.
	 *************************************************************************/
	public ViewFlipper getViewFlipper(int id) {
		return (ViewFlipper) findViewById(id);
	}

	/**************************************************************************
	 * Returns a ProgressBar given a resource id. Make sure that the id supplied
	 * is of a ProgressBar to prevent ClassCastException.
	 *************************************************************************/
	public ProgressBar getProgressBar(int id) {
		return (ProgressBar) findViewById(id);
	}

	/**************************************************************************
	 * Returns a View given a resource id.
	 *************************************************************************/
	public View getView(int id) {
		return findViewById(id);
	}
	
	/**************************************************************************
	 * Returns a string extra given the name.
	 *************************************************************************/
	public String getStringExtra(String name) {
		return getIntent().getStringExtra(name);
	}
	
	/**************************************************************************
	 * Returns an integer extra given the name.
	 *************************************************************************/
	public int getIntExtra(String name) {
		return getIntent().getIntExtra(name, 0);
	}
}
