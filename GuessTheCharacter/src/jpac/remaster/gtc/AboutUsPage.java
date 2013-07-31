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
 *****************************************************************************/
package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;


/******************************************************************************
 * The activity for the About Us page. Loads the html file to the webview and
 * display it to the user.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class AboutUsPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);

		setOnClickListener(R.id.backButton, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		WebView webView = getWebView(R.id.aboutHTML);
		webView.loadUrl("file:///android_asset/data/about.html");

		setTypeface(R.id.pageTitle, ResourceManager.getFont("digitalstrip.ttf"));
	}
}
