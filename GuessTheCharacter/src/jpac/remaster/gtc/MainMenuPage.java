/******************************************************************************
 * Copyright 2013
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.GTCAuthAdapter;

import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/******************************************************************************
 * This activity is the main menu of this application, the main starting point
 * of the game. From this page,  you can do any of the following:
 * 
 * 1. Start the Game
 * 2. Sign in or Sign out from Facebook
 * 3. Check game info
 * 4. Reset data
 * 5. Close the Game
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class MainMenuPage extends GTCActivity {

	// ========================================================================
	// Request Codes
	// ========================================================================
	private static final int REQUEST_RESETCONFIRM = 1;
	private static final int REQUEST_ACKNOWLEDGE_RESET = 2;
	private static final int REQUEST_FACEBOOK_ACTION = 3;
	private static final int REQUEST_FACEBOOK_SIGN = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_main_menu);

		// this is to fix multiple instance of main menu
		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		if (!prefs.getBoolean("loaded", false)) {
			finish();
		}
		
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

	@Override
	protected void onDestroy() {
		super.onDestroy();

		SharedPreferences prefs = getSharedPreferences("splash", MODE_PRIVATE);
		prefs.edit().putBoolean("loaded", false).commit();
	}

	/**************************************************************************
	 * Perform facebook action depending on the token state.
	 *************************************************************************/
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_RESETCONFIRM && resultCode == RESULT_OK) {
			DataManager.reset();
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
}
