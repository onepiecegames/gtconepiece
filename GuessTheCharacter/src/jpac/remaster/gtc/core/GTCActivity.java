package jpac.remaster.gtc.core;

import jpac.remaster.gtc.GTCSplash;
import jpac.remaster.gtc.data.DataManager;
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

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public abstract class GTCActivity extends Activity {

	protected Tracker mGaTracker;
	protected GoogleAnalytics mGaInstance;

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
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
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

		// Get the GoogleAnalytics singleton. Note that the SDK uses
	    // the application context to avoid leaking the current context.
	    mGaInstance = GoogleAnalytics.getInstance(this);

	    // Use the GoogleAnalytics singleton to get a Tracker.
	    mGaTracker = mGaInstance.getTracker("UA-42918296-1"); // Placeholder tracking ID.

		ResourceManager.setContextReference(this);
		DataManager.setContextReference(this);
		Util.log(this.getClass().getName() + "[onCreate]");
	}

	private boolean isLaunchedFromHistory() {
		int flags = getIntent().getFlags();
		return (flags & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;
	}

	private void setFullscreenWindow() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private String getClassName() {
		return this.getClass().getName();
	}

	public void setVisibility(int id, int visibility) {
		View view = findViewById(id);
		
		if (view != null) {
			view.setVisibility(visibility);
		}
	}
	
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
	
	public void setOnClickListener(int id, OnClickListener listener) {
		findViewById(id).setOnClickListener(listener);
	}
	
	public void setBackground(int id, int bg) {
		findViewById(id).setBackgroundResource(bg);
	}
	
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
	
	public void startAnimation(int id, Animation anim) {
		findViewById(id).startAnimation(anim);
	}
	
	public TextView getTextView(int id) {
		return (TextView) findViewById(id);
	}

	public Button getButton(int id) {
		return (Button) findViewById(id);
	}

	public ImageButton getImageButton(int id) {
		return (ImageButton) findViewById(id);
	}

	public ImageView getImageView(int id) {
		return (ImageView) findViewById(id);
	}

	public WebView getWebView(int id) {
		return (WebView) findViewById(id);
	}

	public ViewFlipper getViewFlipper(int id) {
		return (ViewFlipper) findViewById(id);
	}

	public ProgressBar getProgressBar(int id) {
		return (ProgressBar) findViewById(id);
	}

	public View getView(int id) {
		return findViewById(id);
	}
	
	public String getStringExtra(String name) {
		return getIntent().getStringExtra(name);
	}
	
	public int getIntExtra(String name) {
		return getIntent().getIntExtra(name, 0);
	}

	public boolean getBooleanExtra(String name) {
		return getIntent().getBooleanExtra(name, false);
	}
}
