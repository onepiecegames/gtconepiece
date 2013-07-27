package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.logic.ButtonDataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserDataManager;
import jpac.remaster.gtc.util.FontUtil;
import jpac.remaster.gtc.util.SysInfo;
import jpac.remaster.gtc.util.social.SocialAuthAdapter.Provider;
import jpac.remaster.gtc.util.social.SocialDataManager;
import jpac.remaster.gtc.util.social.SocialUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuPage extends GTCActivity {

	private static final int REQUEST_RESETCONFIRM = 1;
	private static final int REQUEST_ACKNOWLEDGE_RESET = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main_menu);

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
		
		findViewById(R.id.resetButton).setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainMenuPage.this, ConfirmationPopup.class);
						intent.putExtra("title", "Confirm Action");
						intent.putExtra("message", "This will clear all your progress as of now. Are you sure you want to reset data?");
						startActivityForResult(intent, REQUEST_RESETCONFIRM);
					}
				});
		
		findViewById(R.id.facebookButton).setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						doFacebookAction();
					}
				});

		((Button) findViewById(R.id.playButton)).setTypeface(FontUtil.getFont(
				getAssets(), "font/roboto_bold.ttf"));
		((Button) findViewById(R.id.facebookButton)).setTypeface(FontUtil.getFont(
				getAssets(), "font/roboto_bold.ttf"));
		((Button) findViewById(R.id.aboutButton)).setTypeface(FontUtil.getFont(
				getAssets(), "font/roboto_bold.ttf"));
		((Button) findViewById(R.id.resetButton)).setTypeface(FontUtil.getFont(
				getAssets(), "font/roboto_bold.ttf"));
//
//		try {
//			ImageView imageBg = (ImageView) findViewById(R.id.imageBG);
//			imageBg.setImageBitmap(ResourceUtil
//					.loadImage(ResourceUtil.MAIN_MASCOT_BG));
//	
//			ImageView logo = (ImageView) findViewById(R.id.gameLogo);
//			logo.setImageBitmap(ResourceUtil.loadImage(ResourceUtil.GAME_LOGO));
//	
//			ImageView mascot = (ImageView) findViewById(R.id.gameMascot);
//			mascot.setImageBitmap(ResourceUtil.loadImage(ResourceUtil.GAME_MASCOT));
//		} catch (RuntimeException e) {
//			finish();
//		}
	}


	private void doFacebookAction() {
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TextView level = ((TextView) findViewById(R.id.currLevelLabel));
		level.setTypeface(FontUtil
				.getFont(getAssets(), "font/roboto_black.ttf"));
		level.setText("" + UserDataManager.checkLevel());
		
		TextView banner = ((TextView) findViewById(R.id.banner));
		banner.setTypeface(FontUtil
				.getFont(getAssets(), "font/digitalstrip.ttf"));

		if (SocialUtil.isConnected(this, Provider.FACEBOOK)) {
			((Button) findViewById(R.id.facebookButton)).setText("Sign Out");
		} else {
			((Button) findViewById(R.id.facebookButton)).setText("Sign In");
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_RESETCONFIRM && resultCode == RESULT_OK) {
			resetData();
			
			Intent intent = new Intent(this, AcknowledgementPopup.class);
			intent.putExtra("title", "Data Reset");
			intent.putExtra("message", "Your user data has been deleted.");
			startActivityForResult(intent, REQUEST_ACKNOWLEDGE_RESET);
		} else if (requestCode == REQUEST_ACKNOWLEDGE_RESET) {
			startActivity(new Intent(this, GTCSplash.class));
			finish();
		}
	}

	private void resetData() {
		UserDataManager.resetData(this);
		PuzzleManager.resetData(this);
		SocialDataManager.resetData(this);
		ButtonDataManager.resetData(this);
	}
}
