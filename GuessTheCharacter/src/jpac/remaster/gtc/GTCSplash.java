package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.util.Constants;
import jpac.remaster.gtc.util.ResourceLoader;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.SysInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GTCSplash extends GTCActivity implements ResourceLoader {

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		SysInfo.loadScreenInfo(this);

		LoadResourceHandler handler = new LoadResourceHandler(this);
		// a delay before executing load
		handler.sendEmptyMessageDelayed(0, Constants.SPLASH_DELAY);
	}

	static class LoadResourceHandler extends Handler {

		private final ResourceLoader loader;

		public LoadResourceHandler(ResourceLoader loader) {
			this.loader = loader;
		}

		@Override
		public void handleMessage(Message message) {
			loader.doLoading();
		}
	}

	@Override
	public void doLoading() {
		new ResourcesLoaderTask().execute();
	}

	class ResourcesLoaderTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ResourceManager.loadSystemFonts();
			PuzzleManager.init(getApplicationContext());
			DataManager.init();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			SharedPreferences prefs = GTCSplash.this.getSharedPreferences("splash", MODE_PRIVATE);
			prefs.edit().putBoolean("loaded", true).commit();
			
			startActivity(new Intent(getApplicationContext(),
					MainMenuPage.class));
		}

	}
}