package jpac.remaster.gtc.util;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.WindowManager;

public class SysInfo {

	public static final int DEBUG = 1;
	public static final int RELEASE = 2;

	private static int mode = RELEASE;

	public static boolean isDebug() {
		return mode == DEBUG;
	}

	public static boolean isRelease() {
		return mode == RELEASE;
	}

	public static double SCREEN_SIZE = -1;
	public static double SCREEN_DENSITY = -1;
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;

	public static void loadScreenInfo(Context context) {
		getScreenSize(context);
		getScreenDensity(context);
		getScreenDimensions(context);
	}

	@SuppressWarnings("deprecation")
	private static void getScreenDimensions(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		SCREEN_WIDTH = display.getWidth();
		SCREEN_HEIGHT = display.getHeight();
	}

	/**************************************************************************
	 * Retrieve the screen density.
	 * 
	 * 0.75 - ldpi
	 * 1.0	- mdpi
	 * 1.5	- hdpi
	 * 2.0	- xhdpi
	 * 3.0	- xxhdpi
	 * 4.0	- xxxhdpi
	 *************************************************************************/
	private static void getScreenDensity(Context context) {
		SCREEN_DENSITY = context.getResources().getDisplayMetrics().density;
	}

	/**************************************************************************
	 * Retrieve the screen size classification.
	 * 
	 * 1.0	- small
	 * 2.0	- normal
	 * 3.0	- large
	 * 4.0	- xlarge
	 *************************************************************************/
	private static void getScreenSize(Context context) {
		SCREEN_SIZE = context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;
	}
}
