package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.logic.ButtonDataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserDataManager;
import jpac.remaster.gtc.util.FontUtil;
import jpac.remaster.gtc.util.ResourceUtil;
import jpac.remaster.gtc.util.SysInfo;
import jpac.remaster.gtc.util.social.SocialDataManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuPage extends GTCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_beta);

		if (!SysInfo.splash) {
			startActivity(new Intent(getApplicationContext(), GTCSplash.class));
			return;
		}

		findViewById(R.id.playButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PuzzleManager.isFinished()) {
					startActivity(new Intent(MainMenuPage.this,
							GameFinishedPage.class));
				} else {
					startActivity(new Intent(MainMenuPage.this,
							InGamePage.class));
				}
			}
		});

		findViewById(R.id.aboutButton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainMenuPage.this,
								AboutUsPage.class));
					}
				});

		((Button) findViewById(R.id.playButton)).setTypeface(FontUtil.getFont(
				getAssets(), "font/roboto_bold.ttf"));

		try {
			ImageView imageBg = (ImageView) findViewById(R.id.imageBG);
			imageBg.setImageBitmap(ResourceUtil
					.loadImage(ResourceUtil.MAIN_MASCOT_BG));
	
			ImageView logo = (ImageView) findViewById(R.id.gameLogo);
			logo.setImageBitmap(ResourceUtil.loadImage(ResourceUtil.GAME_LOGO));
	
			ImageView mascot = (ImageView) findViewById(R.id.gameMascot);
			mascot.setImageBitmap(ResourceUtil.loadImage(ResourceUtil.GAME_MASCOT));
		} catch (RuntimeException e) {
			finish();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TextView level = ((TextView) findViewById(R.id.currLevelLabel));
		level.setTypeface(FontUtil
				.getFont(getAssets(), "font/roboto_black.ttf"));
		level.setText("" + UserDataManager.checkLevel());
	}

	public void resetData() {
		UserDataManager.resetData(this);
		PuzzleManager.resetData(this);
		SocialDataManager.resetData(this);
		ButtonDataManager.resetData(this);
		
		Intent intent = new Intent(this, AcknowledgementPopup.class);
		intent.putExtra("title", "Data Reset");
		intent.putExtra("message", "All user data has been cleared. You can play the game from the start again.");
		startActivity(intent);
	}
}
