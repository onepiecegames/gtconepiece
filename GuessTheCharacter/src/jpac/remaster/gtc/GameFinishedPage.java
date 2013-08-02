package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class GameFinishedPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_finished_page);

		setOnClickListener(R.id.continueButton, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		final ViewFlipper flipper = getViewFlipper(R.id.descFlipper);
		if (flipper.getChildCount() > 1) {
			flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.fade_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.fade_out));
			flipper.setFlipInterval(5000);
			flipper.startFlipping();
		}

		Typeface ds = ResourceManager.getFont("digitalstrip.ttf");

		setTypeface(R.id.banner, ds);
		setTypeface(R.id.titleLabel, ds);
		setTypeface(R.id.finishLabel, ds);
		setTypeface(R.id.continueButton, ResourceManager.getFont("roboto_bold.ttf"));
	}
}
