package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class LevelFinishedPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle_done_page);

		setOnClickListener(R.id.continueButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),
						InGamePage.class);
				startActivity(intent);
				finish();
			}
		});

		String id = getStringExtra("image");
		setImage(R.id.puzzleImage, ResourceManager.loadBitmapFromAsset(id));

		Typeface ds = ResourceManager.getFont("digitalstrip.ttf");

		int amount = getIntExtra("prize");
		setText(R.id.amountLabel, "" + amount);
		setTypeface(R.id.amountLabel, ds);

		String desc = getStringExtra("answer");
		setText(R.id.answer, desc);
		setTypeface(R.id.answer, ds);

		setTypeface(R.id.banner, ds);
		setTypeface(R.id.titleLabel, ds);
		setTypeface(R.id.receiveLabel, ds);
		setTypeface(R.id.amountLabel, ds);
		setTypeface(R.id.goldLabel, ds);

		setTypeface(R.id.continueButton,
				ResourceManager.getFont("roboto_bold.ttf"));
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), InGamePage.class);
		startActivity(intent);
		finish();
	}
}
