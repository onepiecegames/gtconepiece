package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelFinishedPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_done_page);

		findViewById(R.id.continueButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getApplicationContext(),
								InGamePage.class);
						startActivity(intent);
						finish();
					}
				});

		String id = getIntent().getStringExtra("image");
		ImageView image = (ImageView) findViewById(R.id.puzzleImage);
		image.setImageBitmap(ResourceManager.loadBitmapFromAsset(id));

		Typeface ds = ResourceManager.getFont("digitalstrip.ttf");

		int amount = getIntent().getIntExtra("prize", 0);
		((TextView) findViewById(R.id.amountLabel)).setText("" + amount);
		((TextView) findViewById(R.id.amountLabel)).setTypeface(ds);

		String desc = getIntent().getStringExtra("answer");
		((TextView) findViewById(R.id.answer)).setText(desc);
		((TextView) findViewById(R.id.answer)).setTypeface(ds);

		((TextView) findViewById(R.id.banner)).setTypeface(ds);
		((TextView) findViewById(R.id.titleLabel)).setTypeface(ds);
		((TextView) findViewById(R.id.receiveLabel)).setTypeface(ds);
		((TextView) findViewById(R.id.amountLabel)).setTypeface(ds);
		((TextView) findViewById(R.id.goldLabel)).setTypeface(ds);

		((Button) findViewById(R.id.continueButton))
				.setTypeface(ResourceManager.getFont("roboto_bold.ttf"));
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), InGamePage.class);
		startActivity(intent);
		finish();
	}
}
