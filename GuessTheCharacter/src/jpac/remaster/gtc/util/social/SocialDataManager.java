package jpac.remaster.gtc.util.social;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class SocialDataManager {

	private static final String FILENAME = "facebook_posted.raw";

	private static int idOfLastPosted = -1;
	
	public static void loadData(Context context) {
		String content = null;

		try {
			FileInputStream fis = context.openFileInput(FILENAME);

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

		if (content != null) {
			try {
				idOfLastPosted = Integer.valueOf(content);
			} catch (Exception e) {
				
			}
		}
	}

	public static void saveData(Context context) {
		String content = null;

		content = idOfLastPosted + "";

		try {
			FileOutputStream fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkIfPosted(int id) {
		return idOfLastPosted == id;
	}
	
	public static void updatePosted(int id) {
		idOfLastPosted = id;
	}
}
