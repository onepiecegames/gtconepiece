package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCPopupActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SocialPopup extends GTCPopupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_social);

		setOnClickListener(R.id.okButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});

		setOnClickListener(R.id.cancelButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		setTypeface(R.id.title, ResourceManager.getFont("facebook.otf"));
	}

}
