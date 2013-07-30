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
package jpac.remaster.gtc.util.social;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/******************************************************************************
 * This class is a subclass of the default {@link SocialAuthAdapter} in the
 * SocialAuth library for Android. The only changes made is the addition of a
 * method to directly authorize connection to Facebook without displaying the
 * social provider list.
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public class GTCAuthAdapter extends SocialAuthAdapter {

	/**************************************************************************
	 * Creates a new {@link GTCAuthAdapter} object.
	 *************************************************************************/
	public GTCAuthAdapter(DialogListener listener) {
		super(listener);
	}

	/**************************************************************************
	 * Authorize connection to Facebook.
	 *************************************************************************/
	public void connectToFacebook(Context context) {
		authorize(context, Provider.FACEBOOK);
	}

	/**************************************************************************
	 * Checks if this adapter is connected to the specified provider.
	 *************************************************************************/
	public static boolean isConnected(final Context context,
			final Provider provider) {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);

		return pref.contains(provider.toString() + " key");
	}

	/**************************************************************************
	 * Checks if device is connected to the Internet.
	 *************************************************************************/
	public static boolean isNetworkAvailable(Context applicationContext) {
		return Util.isNetworkAvailable(applicationContext);
	}
}