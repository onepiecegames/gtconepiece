package jpac.remaster.gtc.util;

import java.util.Random;

import jpac.remaster.gtc.R;
import jpac.remaster.gtc.core.GTCPopupActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Util {

	private static final Random globalRand = new Random(
			System.currentTimeMillis());

	private static final Random specialRand = new Random();

	public static int randInt(int base, int limit) {
		return base + globalRand.nextInt(limit);
	}

	public static int randInt(int limit) {
		return randInt(0, limit);
	}

	public static void displayToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void setSeed(long seed) {
		specialRand.setSeed(seed);
	}

	public static int randIntSpecial(int limit) {
		return randIntSpecial(0, limit);
	}

	public static int randIntSpecial(int base, int limit) {
		return base + specialRand.nextInt(limit);
	}

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

	public static String[] shuffleContents(String[] source, int n) {
		String[] temp = shuffleContents(source);

		if (n == 1) {
			return temp;
		} else {
			return shuffleContents(temp, n - 1);
		}
	}

	public static void log(String message) {
		if (SysInfo.isDebug()) {
			Log.v("LOG", message);
		}
	}

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

	public static Intent createConfirmPopup(Context context, String title,
			String message) {
		return createPopup(context, title, message);
	}

	public static Intent createAcknowledgePopup(Context context, String title,
			String message) {
		Intent intent = createPopup(context, title, message);

		intent.putExtra(Constants.POP_SINGLE_BUTTON, true);
		intent.putExtra(Constants.POP_BUTTON1,
				ResourceManager.loadString(R.string.label_close));

		return intent;
	}

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
