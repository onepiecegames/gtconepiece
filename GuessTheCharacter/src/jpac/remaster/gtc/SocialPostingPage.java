package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCPopupActivity;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.DialogListener;
import jpac.remaster.gtc.util.social.SocialAuthAdapter;
import jpac.remaster.gtc.util.social.SocialAuthAdapter.Provider;
import jpac.remaster.gtc.util.social.SocialAuthError;
import jpac.remaster.gtc.util.social.SocialAuthListener;
import android.os.Bundle;

public class SocialPostingPage extends GTCPopupActivity implements
		DialogListener, SocialAuthListener<Integer> {

	public static final String ACTION_SHARE = "Share";
	public static final String ACTION_SIGN_IN = "Sign In";
	public static final String ACTION_SIGN_OUT = "Sign Out";

	private SocialAuthAdapter adapter;

	private String action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		action = getIntent().getStringExtra("action");

		adapter = new SocialAuthAdapter(this);
		adapter.connect(this);
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onComplete(Bundle values) {
		try {
			if (action.compareTo(ACTION_SHARE) == 0) {
				// adapter.uploadImageAsync("Help! Who is this character?",
				// "guess_the_character.png",
				// Util.getCapturedImage(), 1, this);
			} else if (action.compareTo(ACTION_SIGN_OUT) == 0) {
				if (adapter.signOut(Provider.FACEBOOK.toString())) {
					setResult(RESULT_OK);
				} else {
					setResult(RESULT_CANCELED);
				}
				finish();
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
