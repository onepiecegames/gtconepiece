package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserDataManager;
import jpac.remaster.gtc.util.FontUtil;
import jpac.remaster.gtc.util.ResourceLoader;
import jpac.remaster.gtc.util.ResourceUtil;
import jpac.remaster.gtc.util.SysInfo;
import jpac.remaster.gtc.util.social.SocialDataManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
		
		final TextView loadingLabel = (TextView) findViewById(R.id.loadingLabel);
		loadingLabel.setTypeface(FontUtil.getFont(getAssets(),
				"font/roboto_thin.ttf"));

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
		findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		findViewById(R.id.loadingLabel).setVisibility(View.VISIBLE);
		new ResourcesLoaderTask().execute(getAssets());
	}

	class ResourcesLoaderTask extends AsyncTask<AssetManager, Void, Void> {

		@Override
		protected Void doInBackground(AssetManager... params) {
			((ProgressBar) findViewById(R.id.progressBar))
					.incrementProgressBy(100);
			FontUtil.loadSystemFonts(params[0]);
			ResourceUtil.loadMainImages();
			PuzzleManager.init(getApplicationContext());
			UserDataManager.init(getApplicationContext());
			SocialDataManager.loadData(getApplicationContext());
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