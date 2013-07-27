package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.util.ResourceUtil;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.DialogListener;
import jpac.remaster.gtc.util.social.SocialAuthAdapter;
import jpac.remaster.gtc.util.social.SocialAuthError;
import jpac.remaster.gtc.util.social.SocialAuthListener;
import android.os.Bundle;

public class SocialPostingPage extends GTCActivity implements DialogListener, SocialAuthListener<Integer> {

	private SocialAuthAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		adapter = new SocialAuthAdapter(this);
		adapter.share(getApplicationContext());
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onComplete(Bundle values) {
		try {
			adapter.uploadImageAsync(
					"Help! Who is this character?",
					"guess_the_character.png",
					ResourceUtil.getCapturedImage(), 1, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(SocialAuthError e) {
		Util.displayToast(getApplicationContext(), "Error posting on your timeline.");
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
		Util.displayToast(getApplicationContext(), "Successfully posted on your timeline.");
		setResult(RESULT_OK);
		finish();
	}
	
	
}
