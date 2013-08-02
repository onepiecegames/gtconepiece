package jpac.remaster.gtc.util.social;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GTCAuthAdapter extends SocialAuthAdapter {

	public GTCAuthAdapter(DialogListener listener) {
		super(listener);
	}

	public void connectToFacebook(Context context) {
		authorize(context, Provider.FACEBOOK);
	}

	public static boolean isConnected(final Context context,
			final Provider provider) {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);

		return pref.contains(provider.toString() + " key");
	}

	public static boolean isNetworkAvailable(Context applicationContext) {
		return Util.isNetworkAvailable(applicationContext);
	}
}