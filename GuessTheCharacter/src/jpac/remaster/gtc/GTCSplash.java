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

/******************************************************************************
 * This activity is the splash screen of this application. It displays a
 * silhouette of the current version mascot and the developer logo. It is
 * responsible for loading and initializing managers in the background.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
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

	/**************************************************************************
	 * A handler for initializing the resource loading.
	 * 
	 * @author JP Carabuena
	 * @since 1.0
	 *************************************************************************/
	static class LoadResourceHandler extends Handler {

		// ====================================================================
		// An instance of a resource loader.
		// ====================================================================
		private final ResourceLoader loader;

		/**********************************************************************
		 * Construct a new load resource handler.
		 *********************************************************************/
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

	/**************************************************************************
	 * An asynchronous task for initializing and loading resources.
	 * 
	 * @author JP Carabuena
	 * @since 1.0
	 *************************************************************************/
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