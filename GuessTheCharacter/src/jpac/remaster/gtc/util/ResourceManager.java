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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/******************************************************************************
 * This class manages all the resources that are needed in the game. These are
 * the functions which will be provided by this manager:
 * 
 * - Image Loading and Caching
 * - Font Loading and Caching
 * - Loading resources from res folder via id
 * - Loading and saving internal files
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public class ResourceManager {

	// ========================================================================
	// A static context reference used for resource operations.
	// ========================================================================
	private static Context contextRef;

	// ========================================================================
	// The cache for image bitmaps and fonts.
	// ========================================================================
	private static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>(
			20);
	private static HashMap<String, Typeface> fontCache = new HashMap<String, Typeface>(
			20);

	// ========================================================================
	// List of bitmap keys ready for recycling.
	// ========================================================================
	private static ArrayList<String> forRecycle = new ArrayList<String>(20);

	/**************************************************************************
	 * Set the reference context. This must be the context of the current active
	 * activity.
	 *************************************************************************/
	public static void setContextReference(Context context) {
		ResourceManager.contextRef = context;
	}

	/**************************************************************************
	 * Load a string from the values.xml given the resource id.
	 *************************************************************************/
	public static String loadString(int id) {
		return ResourceManager.contextRef.getResources().getString(id);
	}

	/**************************************************************************
	 * Load a color (integer) from the colors.xml given the resource id.
	 *************************************************************************/
	public static int loadColor(int id) {
		return ResourceManager.contextRef.getResources().getColor(id);
	}

	/**************************************************************************
	 * Load the integer value of a dimension from dimensions.xml given the
	 * resource id.
	 *************************************************************************/
	public static int loadDimension(int id) {
		return ResourceManager.contextRef.getResources().getDimensionPixelSize(
				id);
	}

	/**************************************************************************
	 * Load an array of string from array.xml given the resource id.
	 *************************************************************************/
	public static String[] loadStringArray(int id) {
		return ResourceManager.contextRef.getResources().getStringArray(id);
	}

	/**************************************************************************
	 * Load an animation file from the res/anim folder given the resource id.
	 *************************************************************************/
	public static Animation loadAnimation(int id) {
		return AnimationUtils.loadAnimation(contextRef, id);
	}

	/**************************************************************************
	 * Load an image from res/drawable folder and resize it accordingly to the
	 * provided width and height.
	 *************************************************************************/
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

	/**************************************************************************
	 * Calculate the in sample size of a bitmap.
	 *************************************************************************/
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

	/**************************************************************************
	 * Load a bitmap from the res/drawable folder given the resource id and the
	 * specified width and height.
	 *************************************************************************/
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

	/**************************************************************************
	 * Load a bitmap from the assets folder given the name of the file.
	 *************************************************************************/
	public static Bitmap loadBitmapFromAsset(String name) {
		if (!ResourceManager.imageCache.containsKey(name)) {
			try {
				InputStream is = (InputStream) contextRef.getAssets().open(
						Constants.IMAGE_LOCATION + name + ".png");
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				ResourceManager.imageCache.put(name, bitmap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return ResourceManager.imageCache.get(name);
	}

	/**************************************************************************
	 * Mark the bitmap ready for recycling.
	 *************************************************************************/
	public static void queueBitmapForRecycle(String name) {
		if (!forRecycle.contains(name)) {
			forRecycle.add(name);
			if (isReadyForRecycling()) {
				recycle();
			}
		}
	}

	/**************************************************************************
	 * You can force the recycling of bitmaps at any point in the application
	 * lifecycle. This will not check if list already hits threshold.
	 *************************************************************************/
	public static void forceRecycle() {
		recycle();
	}

	/**************************************************************************
	 * Performs bitmap recycling for all images included in the for recycle
	 * list. This will clear the list afterwards.
	 *************************************************************************/
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

	/**************************************************************************
	 * Checks if the recycle list is ready for cleanup.
	 *************************************************************************/
	private static boolean isReadyForRecycling() {
		return forRecycle.size() > Constants.RECYCLE_THRESHOLD;
	}

	/**************************************************************************
	 * Dispose all resources used by this manager. Make sure to call this method
	 * only when the application is finishing.
	 *************************************************************************/
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

	/**************************************************************************
	 * Retrieve a font from the cache.
	 *************************************************************************/
	public static Typeface getFont(String font) {
		if (fontCache.containsKey(Constants.FONT_LOCATION + font)) {
			return fontCache.get(Constants.FONT_LOCATION + font);
		}
		return loadFont(Constants.FONT_LOCATION + font);
	}

	/**************************************************************************
	 * Load font from asset/font folder and add it to cache.
	 *************************************************************************/
	private static Typeface loadFont(String font) {
		Typeface tf = Typeface.createFromAsset(contextRef.getAssets(), font);
		fontCache.put(font, tf);
		return tf;
	}

	/**************************************************************************
	 * Load system delegated fonts.
	 *************************************************************************/
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

	/**************************************************************************
	 * Save a content to an internal file.
	 *************************************************************************/
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

	/**************************************************************************
	 * Load data content from an internal file and return it as a string.
	 *************************************************************************/
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

	/**************************************************************************
	 * Checks if the specified file exist in internal storage.
	 *************************************************************************/
	public static boolean isFileExist(String filename) {
		return contextRef.getFileStreamPath(filename).exists();
	}
}
