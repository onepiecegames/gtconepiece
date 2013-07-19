package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
						ResourceUtil.forceRecycleImages();
						finish();
					}
				});

		String id = getIntent().getStringExtra("image");
		ImageView image = (ImageView) findViewById(R.id.puzzleImage);
		image.setImageBitmap(ResourceUtil.loadPuzzleImage(id));

		int amount = getIntent().getIntExtra("prize", 0);
		((TextView) findViewById(R.id.amountLabel)).setText("" + amount);

		String desc = getIntent().getStringExtra("answer");
		((TextView) findViewById(R.id.answer)).setText(desc);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), InGamePage.class);
		startActivity(intent);
		ResourceUtil.forceRecycleImages();
		finish();
	}
}
