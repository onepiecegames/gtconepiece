package jpac.remaster.gtc.core;

import jpac.remaster.gtc.R;
import jpac.remaster.gtc.util.ResourceManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GTCPopupActivity extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_template);

		String title = getStringExtra("title");
		String message = getStringExtra("message");

		setText(R.id.title, title);
		setText(R.id.message, message);

		String font = getStringExtra("title_font");
		setTypeface(R.id.title, ResourceManager.getFont(font));

		// modify ok button
		setText(R.id.okButton, getStringExtra("button1"));
		setOnClickListener(R.id.okButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});

		// modify cancel button
		setText(R.id.cancelButton, getStringExtra("button2"));
		setOnClickListener(R.id.cancelButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		// change the default banner background if specified
		int res = getIntExtra("banner_resource");
		if (res > 1) {
			setBackground(R.id.title, res);
		}
		
		// remove the second button for acknowledgment popups
		if (getBooleanExtra("single_button")) {
			setVisibility(R.id.cancelButton, View.GONE);
		}
	}
}
