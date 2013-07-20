package jpac.remaster.gtc.util;

import java.util.HashMap;

import jpac.remaster.gtc.R;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontUtil {

	private static final HashMap<String, Typeface> FONT_CACHE = new HashMap<String, Typeface>(
			10);

	public static Typeface getFont(AssetManager manager, String font) {
		if (FONT_CACHE.containsKey(font)) {
			return FONT_CACHE.get(font);
		}
		return loadFont(manager, font);
	}

	private static Typeface loadFont(AssetManager mgr, String font) {
		Typeface tf = Typeface.createFromAsset(mgr, font);
		FONT_CACHE.put(font, tf);
		return tf;
	}

	public static void loadSystemFonts(AssetManager mgr) {
		String[] fonts = ResourceUtil.loadStringArray(R.array.fonts);
		int n = fonts.length;

		for (int i = 0; i < n; i++) {
			FontUtil.loadFont(mgr, "font/" + fonts[i] + ".ttf");
		}
	}

	public static void cleanup() {
		FONT_CACHE.clear();
	}
}
