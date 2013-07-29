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

import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.WindowManager;

/******************************************************************************
 * This class contains all the system information which will be used by several
 * components in the application.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class SysInfo {

	// ========================================================================
	// These are the application modes.
	// 
	// DEBUG - this will enable debug specific codes in the application
	// RELEASE - the application is ready for release
	// ========================================================================
	public static final int DEBUG = 1;
	public static final int RELEASE = 2;

	// ========================================================================
	// The application's current mode.
	//
	// Be sure to change the value to RELEASE before publishing the apk.
	// ========================================================================
	private static int mode = RELEASE;

	/**************************************************************************
	 * Check if this application is in debug mode.
	 *************************************************************************/
	public static boolean isDebug() {
		return mode == DEBUG;
	}

	/**************************************************************************
	 * Check if this application is in release mode.
	 *************************************************************************/
	public static boolean isRelease() {
		return mode == RELEASE;
	}

	// ========================================================================
	// These are device screen information.
	// ========================================================================
	public static double SCREEN_SIZE = -1;
	public static double SCREEN_DENSITY = -1;
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;

	/**************************************************************************
	 * Initialize the device screen information.
	 *************************************************************************/
	public static void loadScreenInfo(Context context) {
		getScreenSize(context);
		getScreenDensity(context);
		getScreenDimensions(context);
	}

	/**************************************************************************
	 * Retrieve the screen dimension from the <code>WindowManager</code>.
	 *************************************************************************/
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
