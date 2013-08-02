package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

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
