package jpac.remaster.gtc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import jpac.remaster.gtc.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ResourceUtil {

	private static Context contextRef;

	public static void setContextRef(Context context) {
		ResourceUtil.contextRef = context;
	}

	public static void cleanup() {
		ResourceUtil.contextRef = null;
	}

	public static String[] loadStringArray(int id) {
		return ResourceUtil.contextRef.getResources().getStringArray(id);
	}

	public static int loadDimension(int id) {
		return ResourceUtil.contextRef.getResources().getDimensionPixelSize(id);
	}

	private static HashMap<String, Bitmap> IMAGE_CACHE = new HashMap<String, Bitmap>(
			20);

	private static ArrayList<String> FOR_RECYCLE = new ArrayList<String>(10);

	public static final String GAME_LOGO = "game_logo";
	public static final String GAME_DONE_MASCOT = "luffy_congrats";
	public static final String GAME_CONTINUE = "to_be_continued";

	public static void loadMainImages() {
		if (!IMAGE_CACHE.containsKey(GAME_LOGO)) {
			IMAGE_CACHE.put(
					GAME_LOGO,
					decodeSampledBitmapFromResource(contextRef.getResources(),
							R.drawable.game_logo, DeviceInfo.SCREEN_WIDTH,
							loadDimension(R.dimen.logo_height)));
		}

		if (!IMAGE_CACHE.containsKey(GAME_DONE_MASCOT)) {
			IMAGE_CACHE.put(
					GAME_DONE_MASCOT,
					decodeSampledBitmapFromResource(contextRef.getResources(),
							R.drawable.luffy_congrats, DeviceInfo.SCREEN_WIDTH,
							loadDimension(R.dimen.badge_height)));
		}

		if (!IMAGE_CACHE.containsKey(GAME_CONTINUE)) {
			IMAGE_CACHE.put(
					GAME_CONTINUE,
					decodeSampledBitmapFromResource(contextRef.getResources(),
							R.drawable.to_be_continued,
							DeviceInfo.SCREEN_WIDTH,
							loadDimension(R.dimen.badge_height)));
		}
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		if (reqWidth == -1) {
			reqWidth = options.outWidth;
		}

		if (reqHeight == -1) {
			reqHeight = options.outHeight;
		}

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap loadImage(String name) {
		if (IMAGE_CACHE.containsKey(name)) {
			return IMAGE_CACHE.get(name);
		}
		return null;
	}

	public static Bitmap loadPuzzleImage(String name) {

		if (IMAGE_CACHE.containsKey(name)) {
			return IMAGE_CACHE.get(name);
		}

		try {
			InputStream is = (InputStream) contextRef.getAssets().open(
					"puzzle/" + name + ".png");
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			IMAGE_CACHE.put(name, bitmap);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void clearImageCache() {
		IMAGE_CACHE.clear();
	}

	public static Animation loadAnimation(int id) {
		return AnimationUtils.loadAnimation(contextRef, id);
	}

	public static void forRecycle(String imageId) {
		if (!FOR_RECYCLE.contains(imageId)) {
			FOR_RECYCLE.add(imageId);
		}
	}

	public static void forceRecycleImages() {
		int n = FOR_RECYCLE.size();

		for (int i = 0; i < n; i++) {
			String id = FOR_RECYCLE.get(i);
			if (IMAGE_CACHE.containsKey(id)) {
				Bitmap b = IMAGE_CACHE.remove(id);
				b.recycle();
				b = null;
			}
		}

		FOR_RECYCLE.clear();
	}

	private static Bitmap capturedImage;

	public static void captureView(View v) {
		if (capturedImage != null) {
			capturedImage.recycle();
			capturedImage = null;
		}

		v.setDrawingCacheEnabled(true);
		capturedImage = v.getDrawingCache();
	}

	public static Bitmap getCapturedImage() {
		return capturedImage;
	}
}
