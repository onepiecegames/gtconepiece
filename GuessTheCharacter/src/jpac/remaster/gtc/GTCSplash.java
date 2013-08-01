package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.util.ResourceLoader;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.SysInfo;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class GTCSplash extends GTCActivity implements ResourceLoader {

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		SysInfo.loadScreenInfo(this);

		setTypeface(R.id.loadingLabel, ResourceManager.getFont("roboto_thin.ttf"));

		LoadResourceHandler handler = new LoadResourceHandler(this);
		handler.sendEmptyMessageDelayed(0, 1000);
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
		setVisibility(R.id.progressBar, View.VISIBLE);
		setVisibility(R.id.loadingLabel, View.VISIBLE);
		new ResourcesLoaderTask().execute();
	}

	class ResourcesLoaderTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			getProgressBar(R.id.progressBar).incrementProgressBy(100);
			ResourceManager.loadSystemFonts();
			PuzzleManager.init(getApplicationContext());
			DataManager.init();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			startActivity(new Intent(getApplicationContext(),
					MainMenuPage.class));
			finish();
		}

	}
}