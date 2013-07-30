package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.logic.ButtonDataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserDataManager;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.GTCAuthAdapter;
import jpac.remaster.gtc.util.social.SocialDataManager;

import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenuPage extends GTCActivity {

	private static final int REQUEST_RESETCONFIRM = 1;
	private static final int REQUEST_ACKNOWLEDGE_RESET = 2;
	private static final int REQUEST_FACEBOOK_ACTION = 3;
	private static final int REQUEST_FACEBOOK_SIGN = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main_menu);

		setOnClickListener(R.id.playButton, new OnClickListener() {

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

		setOnClickListener(R.id.aboutButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainMenuPage.this, AboutUsPage.class));
			}
		});

		setOnClickListener(R.id.resetButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenuPage.this,
						ConfirmationPopup.class);
				intent.putExtra("title", "Confirm Action");
				intent.putExtra(
						"message",
						"This will clear all your progress as of now. Are you sure you want to reset data?");
				startActivityForResult(intent, REQUEST_RESETCONFIRM);
			}
		});

		setOnClickListener(R.id.facebookButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenuPage.this,
						ConfirmationPopup.class);
				intent.putExtra("title", "Confirm Action");
				if (GTCAuthAdapter.isConnected(MainMenuPage.this, Provider.FACEBOOK)) {
					intent.putExtra("message",
							"Are you sure you want to sign out from Facebook?");
				} else {
					intent.putExtra("message",
							"This will connect to your Facebook account.");
				}
				startActivityForResult(intent, REQUEST_FACEBOOK_ACTION);
			}
		});

		Typeface roboto = ResourceManager.getFont("roboto_bold.ttf");
		setTypeface(R.id.playButton, roboto);
		setTypeface(R.id.facebookButton, roboto);
		setTypeface(R.id.aboutButton, roboto);
		setTypeface(R.id.resetButton, roboto);
	}

	private void doFacebookAction() {
		Intent intent = new Intent(this, SocialPostingPage.class);
		if (GTCAuthAdapter.isConnected(this, Provider.FACEBOOK)) {
			intent.putExtra("action", SocialPostingPage.ACTION_SIGN_OUT);
		} else {
			intent.putExtra("action", SocialPostingPage.ACTION_SIGN_IN);
		}
		startActivityForResult(intent, REQUEST_FACEBOOK_SIGN);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setTypeface(R.id.currLevelLabel, ResourceManager.getFont("roboto_black.ttf"));
		setText(R.id.currLevelLabel, "" + UserDataManager.checkLevel());

		setTypeface(R.id.banner, ResourceManager.getFont("digitalstrip.ttf"));

		if (GTCAuthAdapter.isConnected(this, Provider.FACEBOOK)) {
			setText(R.id.facebookButton, ResourceManager.loadString(R.string.label_signout));
		} else {
			setText(R.id.facebookButton, ResourceManager.loadString(R.string.label_signin));
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
		} else if (requestCode == REQUEST_FACEBOOK_ACTION
				&& resultCode == RESULT_OK) {
			doFacebookAction();
		} else if (requestCode == REQUEST_FACEBOOK_SIGN) {
			if (resultCode == RESULT_OK) {
				Util.displayToast(this, "Logged Out");
			}
		}
	}

	private void resetData() {
		UserDataManager.resetData(this);
		PuzzleManager.resetData(this);
		SocialDataManager.resetData(this);
		ButtonDataManager.resetData(this);
	}
}
