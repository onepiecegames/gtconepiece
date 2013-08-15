package jpac.remaster.gtc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import jpac.remaster.gtc.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ResourceManager {

	private static Context contextRef;

	private static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>(
			20);
	private static HashMap<String, Typeface> fontCache = new HashMap<String, Typeface>(
			20);
	private static ArrayList<String> forRecycle = new ArrayList<String>(20);

	private static Bitmap capturedImage;

	public static void setContextReference(Context context) {
		ResourceManager.contextRef = context;
	}

	public static String loadString(int id) {
		return ResourceManager.contextRef.getResources().getString(id);
	}

	public static int loadColor(int id) {
		return ResourceManager.contextRef.getResources().getColor(id);
	}

	public static int loadDimension(int id) {
		return ResourceManager.contextRef.getResources().getDimensionPixelSize(
				id);
	}

	public static String[] loadStringArray(int id) {
		return ResourceManager.contextRef.getResources().getStringArray(id);
	}

	public static Animation loadAnimation(int id) {
		return AnimationUtils.loadAnimation(contextRef, id);
	}

	private static Bitmap decodeSampledBitmapFromResource(Resources res,
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

	public static Bitmap loadBitmapFromDrawable(int id, int width, int height) {
		String idAsText = String.valueOf(id);
		if (!ResourceManager.imageCache.containsKey(idAsText)) {
			try {
				Bitmap bitmap = decodeSampledBitmapFromResource(
						ResourceManager.contextRef.getResources(), id, width,
						height);
				ResourceManager.imageCache.put(idAsText, bitmap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return ResourceManager.imageCache.get(idAsText);
	}

	public static Bitmap loadBitmapFromAsset(String name) {
		if (!ResourceManager.imageCache.containsKey(name)) {
			try {
				InputStream is = (InputStream) contextRef.getAssets().open(
						Constants.IMAGE_LOCATION + name + ".png");
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				ResourceManager.imageCache.put(name, bitmap);
				ResourceManager.forRecycle.add(name);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return ResourceManager.imageCache.get(name);
	}

	public static void queueBitmapForRecycle(String name) {
		if (!forRecycle.contains(name)) {
			forRecycle.add(name);
			if (isReadyForRecycling()) {
				recycle();
			}
		}
	}

	public static void forceRecycle() {
		recycle();
	}

	private static void recycle() {
		int n = forRecycle.size();

		for (int i = 0; i < n; i++) {
			String id = forRecycle.get(i);
			if (imageCache.containsKey(id)) {
				Bitmap b = imageCache.remove(id);
				b.recycle();
				b = null;
			}
		}

		forRecycle.clear();
	}

	private static boolean isReadyForRecycling() {
		return forRecycle.size() > Constants.RECYCLE_THRESHOLD;
	}

	public static void cleanup() {

		if (imageCache != null) {
			// recycle all images in the cache
			Iterator<String> keys = imageCache.keySet().iterator();
			while (keys.hasNext()) {
				forRecycle.add(keys.next());
			}
			recycle();
			imageCache.clear();
		}

		if (fontCache != null) {
			// clear fonts
			fontCache.clear();
		}

		// make context reference null
		contextRef = null;
	}

	public static Typeface getFont(String font) {
		if (fontCache.containsKey(Constants.FONT_LOCATION + font)) {
			return fontCache.get(Constants.FONT_LOCATION + font);
		}
		return loadFont(Constants.FONT_LOCATION + font);
	}

	private static Typeface loadFont(String font) {
		Typeface tf = Typeface.createFromAsset(contextRef.getAssets(), font);
		fontCache.put(font, tf);
		return tf;
	}

	public static void loadSystemFonts() {
		String[] fonts = loadStringArray(R.array.fonts);
		int n = fonts.length;

		for (int i = 0; i < n; i++) {
			try {
				loadFont(Constants.FONT_LOCATION + fonts[i]);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean saveData(String filename, String content) {
		if (content == null || filename == null) {
			return false;
		}

		try {
			FileOutputStream fos = contextRef.openFileOutput(filename,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String loadDataFromAsset(String filename) {
		String content = null;
		
		try {
            InputStream input = contextRef.getResources().getAssets().open(filename);
             
             int size = input.available();
             byte[] buffer = new byte[size];
             input.read(buffer);
             input.close();
 
             // byte buffer into a string
             content = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return content;
	}

	public static String loadData(String filename) {
		String content = null;

		try {
			FileInputStream fis = contextRef.openFileInput(filename);

			if (fis != null) {
				byte[] input = new byte[fis.available()];
				while (fis.read(input) != -1) {
					content += new String(input);
				}
				fis.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public static boolean isFileExist(String filename) {
		return contextRef.getFileStreamPath(filename).exists();
	}

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
