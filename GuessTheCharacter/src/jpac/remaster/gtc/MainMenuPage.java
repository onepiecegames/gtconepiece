package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.GTCAuthAdapter;

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
				startActivityForResult(
						Util.createConfirmPopup(
								MainMenuPage.this,
								"Confirm Action",
								"This will clear all your progress as of now. Are you sure you want to reset data?"),
						REQUEST_RESETCONFIRM);
			}
		});

		setOnClickListener(R.id.facebookButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message;
				if (GTCAuthAdapter.isConnected(MainMenuPage.this,
						Provider.FACEBOOK)) {
					message = "Are you sure you want to sign out from Facebook?";
				} else {
					message = "This will connect to your Facebook account.";
				}
				startActivityForResult(Util.createConfirmPopup(
						MainMenuPage.this, "Confirm Action", message),
						REQUEST_FACEBOOK_ACTION);
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
		setTypeface(R.id.currLevelLabel,
				ResourceManager.getFont("roboto_black.ttf"));
		setText(R.id.currLevelLabel, "" + DataManager.checkLevel());

		setTypeface(R.id.banner, ResourceManager.getFont("digitalstrip.ttf"));

		if (GTCAuthAdapter.isConnected(this, Provider.FACEBOOK)) {
			setText(R.id.facebookButton,
					ResourceManager.loadString(R.string.label_signout));
		} else {
			setText(R.id.facebookButton,
					ResourceManager.loadString(R.string.label_signin));
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_RESETCONFIRM && resultCode == RESULT_OK) {
			resetData();
			startActivityForResult(Util.createAcknowledgePopup(this,
					"Data Reset", "Your user data has been deleted."),
					REQUEST_ACKNOWLEDGE_RESET);
		} else if (requestCode == REQUEST_ACKNOWLEDGE_RESET) {
			onResume();
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
		DataManager.reset();
//		PuzzleManager.resetData(this);
//		SocialDataManager.resetData(this);
//		ButtonDataManager.resetData(this);
	}
}
