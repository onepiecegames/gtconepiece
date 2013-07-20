package jpac.remaster.gtc.util;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.WindowManager;

public class DeviceInfo {

	public static double SCREEN_SIZE = -1;

	public static double SCREEN_DENSITY = -1;

	public static int SCREEN_WIDTH, SCREEN_HEIGHT;

	@SuppressWarnings("deprecation")
	public static void init(Context context) {

		SCREEN_SIZE = context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK;
		SCREEN_DENSITY = context.getResources().getDisplayMetrics().density;

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		SCREEN_WIDTH = display.getWidth();
		SCREEN_HEIGHT = display.getHeight();
	}
}
