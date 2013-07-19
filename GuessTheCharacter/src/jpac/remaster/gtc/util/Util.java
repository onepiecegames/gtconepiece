package jpac.remaster.gtc.util;

import java.util.Random;

import android.content.Context;
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

	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String[] generateRandomCharacters(int n, String offset) {
		String[] generatedStrings = new String[n + offset.length()];
		int len = ALPHABET.length();

		for (int i = 0; i < n; i++) {
			int index = specialRand.nextInt(len);
			generatedStrings[i] = ALPHABET.substring(index, index + 1);
		}

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
		Log.v("LOG", message);
	}
}
