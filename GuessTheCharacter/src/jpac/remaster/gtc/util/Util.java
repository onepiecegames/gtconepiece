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
package jpac.remaster.gtc.util;

import java.util.Random;

import jpac.remaster.gtc.R;
import jpac.remaster.gtc.core.GTCPopupActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/******************************************************************************
 * This class contains all the utility or helper methods which will be used by
 * several components in the application.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class Util {

	// ========================================================================
	// This is the default random generator of the application.
	// ========================================================================
	private static final Random globalRand = new Random(
			System.currentTimeMillis());

	// ========================================================================
	// This is a specialized random generator for processes with specific
	// random seed value.
	// ========================================================================
	private static final Random specialRand = new Random();

	/**************************************************************************
	 * Get a random integer between the specified base number and the limit.
	 *************************************************************************/
	public static int randInt(int base, int limit) {
		return base + globalRand.nextInt(limit);
	}

	/**************************************************************************
	 * Get a random integer between zero and the specified limit.
	 *************************************************************************/
	public static int randInt(int limit) {
		return randInt(0, limit);
	}

	/**************************************************************************
	 * Display the specified message as a short toast notification.
	 *************************************************************************/
	public static void displayToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**************************************************************************
	 * Set the random seed for the special random generator.
	 *************************************************************************/
	public static void setSeed(long seed) {
		specialRand.setSeed(seed);
	}

	/**************************************************************************
	 * Get a random integer between 0 and the specified limit using the special
	 * random generator.
	 *************************************************************************/
	public static int randIntSpecial(int limit) {
		return randIntSpecial(0, limit);
	}

	/**************************************************************************
	 * Get a random integer between the specified base number and the limit
	 * using the special random generator.
	 *************************************************************************/
	public static int randIntSpecial(int base, int limit) {
		return base + specialRand.nextInt(limit);
	}

	/**************************************************************************
	 * Generate random set of n strings. The characters in the specified offset
	 * will be split into separate string and will be added to the set.
	 * 
	 * e.g. generateRandomCharacters(5, "XT");
	 * 		this function will return a set containing 5 random characters plus
	 * 		the character X and T.
	 *************************************************************************/
	public static String[] generateRandomCharacters(int n, String offset) {
		String[] generatedStrings = new String[n + offset.length()];
		int len = Constants.ALPHABET.length();

		// generate random characters
		for (int i = 0; i < n; i++) {
			int index = specialRand.nextInt(len);
			generatedStrings[i] = Constants.ALPHABET
					.substring(index, index + 1);
		}

		// add the offset
		int limit = n + offset.length();
		for (int i = n, j = 0; i < limit; i++, j++) {
			generatedStrings[i] = offset.substring(j, j + 1);
		}

		return generatedStrings;
	}

	/**************************************************************************
	 * Shuffle the contents of a string array.
	 *************************************************************************/
	public static String[] shuffleContents(String[] source) {
		int n = source.length;

		for (int i = 0; i < n; i++) {
			int x = globalRand.nextInt(n);

			String temp = source[i];
			source[i] = source[x];
			source[x] = temp;
		}

		return source;
	}

	/**************************************************************************
	 * Shuffle the contents of a string array for n times.
	 *************************************************************************/
	public static String[] shuffleContents(String[] source, int n) {
		String[] temp = shuffleContents(source);

		if (n == 1) {
			return temp;
		} else {
			return shuffleContents(temp, n - 1);
		}
	}

	/**************************************************************************
	 * Log the message to the logcat for debugging purposes. This method only
	 * works if the application is in Debug mode.
	 *************************************************************************/
	public static void log(String message) {
		if (SysInfo.isDebug()) {
			Log.v("LOG", message);
		}
	}

	/**************************************************************************
	 * Creates the default popup intent.
	 *************************************************************************/
	private static Intent createPopup(Context context, String title,
			String message) {
		Intent intent = new Intent(context, GTCPopupActivity.class);

		intent.putExtra(Constants.POP_TITLE, title);
		intent.putExtra(Constants.POP_MESSAGE, message);
		intent.putExtra(Constants.POP_BUTTON1, Constants.OK_BUTTON);
		intent.putExtra(Constants.POP_BUTTON2, Constants.CANCEL_BUTTON);
		intent.putExtra(Constants.POP_TITLE_FONT, "digitalstrip.ttf");

		return intent;
	}

	/**************************************************************************
	 * Creates a confirmation popup. This is also the default popup.
	 *************************************************************************/
	public static Intent createConfirmPopup(Context context, String title,
			String message) {
		return createPopup(context, title, message);
	}

	/**************************************************************************
	 * Creates an acknowledge popup.
	 *************************************************************************/
	public static Intent createAcknowledgePopup(Context context, String title,
			String message) {
		Intent intent = createPopup(context, title, message);

		intent.putExtra(Constants.POP_SINGLE_BUTTON, true);
		intent.putExtra(Constants.POP_BUTTON1,
				ResourceManager.loadString(R.string.label_close));

		return intent;
	}

	/**************************************************************************
	 * Creates a customized confirmation popup.
	 *************************************************************************/
	public static Intent createCustomConfirmPopup(Context context,
			String title, String message, String titleFont, int bannerResource,
			String buttonText1, String buttonText2) {
		Intent intent = createConfirmPopup(context, title, message);

		intent.putExtra(Constants.POP_TITLE_FONT, titleFont);
		intent.putExtra(Constants.POP_BANNER_RES, bannerResource);
		intent.putExtra(Constants.POP_BUTTON1, buttonText1);
		intent.putExtra(Constants.POP_BUTTON2, buttonText2);

		return intent;
	}

	/**************************************************************************
	 * Creates a customized acknowledgement popup.
	 *************************************************************************/
	public static Intent createCustomAcknowledgePopup(Context context,
			String title, String message, String titleFont, int bannerResource,
			String buttonText) {
		Intent intent = createAcknowledgePopup(context, title, message);

		intent.putExtra(Constants.POP_TITLE_FONT, titleFont);
		intent.putExtra(Constants.POP_BANNER_RES, bannerResource);
		intent.putExtra(Constants.POP_BUTTON1, buttonText);

		return intent;
	}
}
