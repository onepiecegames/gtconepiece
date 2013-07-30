package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCPopupActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AcknowledgementPopup extends GTCPopupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_acknowledge);

		String title = getIntent().getStringExtra("title");
		String message = getIntent().getStringExtra("message");

		setText(R.id.title, title);
		setText(R.id.message, message);

		setOnClickListener(R.id.okButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});

		setTypeface(R.id.title, ResourceManager.getFont("digitalstrip.ttf"));
	}

}
