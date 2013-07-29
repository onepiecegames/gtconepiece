package jpac.remaster.gtc.util.social;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import jpac.remaster.gtc.util.social.SocialAuthAdapter.Provider;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public final class SocialUtil {

	public static int UI_DENSITY;
	public static int UI_SIZE;
	public static int UI_RESOLUTION;

	public static boolean isConnected(final Context context,
			final Provider provider) {

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);

		return pref.contains(provider.toString() + " key");
	}

	@SuppressWarnings("deprecation")
	public static String encodeUrl(Bundle parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String key : parameters.keySet()) {
			if (first)
				first = false;
			else
				sb.append("&");
			sb.append(URLEncoder.encode(key) + "="
					+ URLEncoder.encode(parameters.getString(key)));
		}
		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static Map<String, String> decodeUrl(String s) {
		Map<String, String> params = new HashMap<String, String>();
		if (s != null) {
			String array[] = s.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				if (v.length > 1) {
					params.put(URLDecoder.decode(v[0]),
							v.length > 1 ? URLDecoder.decode(v[1]) : null);
				}
			}
		}
		return params;
	}

	public static Map<String, String> parseUrl(String url) {
		// hack to prevent MalformedURLException
		url = url.replace("fbconnect", "http");
		try {
			URL u = new URL(url);
			Map<String, String> params = decodeUrl(u.getQuery());
			params.putAll(decodeUrl(u.getRef()));
			return params;
		} catch (MalformedURLException e) {
			return new HashMap<String, String>();
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}

		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

	@SuppressWarnings("deprecation")
	public static void getDisplayDpi(Context ctx) {

		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
		double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		double screenInches = Math.sqrt(x + y);
		int screenInch = (int) Math.round(screenInches);
		int dapi = dm.densityDpi;

		Log.d("Resolution X", String.valueOf(width));
		Log.d("Resolution Y", String.valueOf(height));
		Log.d("screeninch", String.valueOf(screenInch));
		Log.d("dapi", String.valueOf(dapi));

		try {
			switch (dm.densityDpi) {

			case DisplayMetrics.DENSITY_LOW:

				UI_DENSITY = 120;

				if (screenInch <= 7) {
					UI_SIZE = 4;

				} else {
					UI_SIZE = 10;
				}

				break;
			case DisplayMetrics.DENSITY_MEDIUM:

				UI_DENSITY = 160;

				if (screenInch <= 7) {

					// For devices having width 320
					if (width == 320) {
						UI_SIZE = 3;
					} else if (width == 480) {
						UI_SIZE = 4;
					} else {
						UI_SIZE = 7;
					}
				} else {
					UI_SIZE = 10;
				}

				break;

			case DisplayMetrics.DENSITY_HIGH:

				UI_DENSITY = 240;

				break;
			case DisplayMetrics.DENSITY_XHIGH:
				UI_DENSITY = 320;
				if (width >= 720 && width < 1280) {
					UI_SIZE = 7;
				} else if (width >= 1280) {
					UI_SIZE = 10;
				} else {
				}

				break;

			case 213:
				UI_DENSITY = 213;

			default:
				break;
			}
		} catch (Exception e) {
			// Caught exception here
		}
	}
}
