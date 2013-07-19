package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConfirmationPopup extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_confirm);

		String title = getIntent().getStringExtra("title");
		String message = getIntent().getStringExtra("message");

		((TextView) findViewById(R.id.title)).setText(title);
		((TextView) findViewById(R.id.message)).setText(message);

		findViewById(R.id.okButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});

		findViewById(R.id.cancelButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						setResult(RESULT_CANCELED);
						finish();
					}
				});
	}

}
