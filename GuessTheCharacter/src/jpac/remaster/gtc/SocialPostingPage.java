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
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.GTCAuthAdapter;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.os.Bundle;

/******************************************************************************
 * This class is used as a buffer when facebook request is loading.
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public class SocialPostingPage extends GTCActivity implements
		DialogListener, SocialAuthListener<Integer> {

	// ========================================================================
	// Facebook Request Action.
	// ========================================================================
	public static final String ACTION_SHARE = "Share";
	public static final String ACTION_SIGN_IN = "Sign In";
	public static final String ACTION_SIGN_OUT = "Sign Out";

	// ========================================================================
	// The social adapter for this class.
	// ========================================================================
	private GTCAuthAdapter adapter;

	// ========================================================================
	// The action specified by the user.
	// ========================================================================
	private String action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		action = getStringExtra("action");

		adapter = new GTCAuthAdapter(this);
		adapter.connectToFacebook(this);
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onComplete(Bundle values) {
		try {
			// share capture image to facebook
			if (action.compareTo(ACTION_SHARE) == 0) {
				 adapter.uploadImageAsync("Help! Who is this character?",
				 "guess_the_character.png",
				 ResourceManager.getCapturedImage(), 1, this);
			// signs out from facebook
			} else if (action.compareTo(ACTION_SIGN_OUT) == 0) {
				if (adapter.signOut(Provider.FACEBOOK.toString())) {
					setResult(RESULT_OK);
				} else {
					setResult(RESULT_CANCELED);
				}
				finish();
			// sign in to facebook
			} else {
				setResult(RESULT_OK);
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(SocialAuthError e) {
		Util.displayToast(getApplicationContext(),
				"Error posting on your timeline.");
		finish();
	}

	@Override
	public void onCancel() {
		finish();
	}

	@Override
	public void onBack() {
		finish();
	}

	@Override
	public void onExecute(String provider, Integer t) {
		Util.displayToast(getApplicationContext(),
				"Successfully posted on your timeline.");
		setResult(RESULT_OK);
		finish();
	}

}
