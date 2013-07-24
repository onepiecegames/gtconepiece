package jpac.remaster.gtc.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class UserDataManager {

	private static final UserData USER = new UserData();

	private static final String FILENAME = "default_user.sav";

	public static void init(Context context) {
		loadData(context);
	}

	private static void loadData(Context context) {
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
			String[] data = content.split("\n");
			assignData(data);
		}
	}

	private static void assignData(String[] data) {
		USER.setUsername(data[0]);
		USER.setLevel(data[1]);
		USER.setGold(data[2]);
		USER.setCurrentPuzzle(data[3]);
	}

	public static void saveData(Context context) {
		String content = null;

		content = getDataAsRaw();

		if (content == null) {
			return;
		}

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

	private static String getDataAsRaw() {
		String content = null;

		content += USER.getUsername() + "\n";
		content += USER.getLevel() + "\n";
		content += USER.getGold() + "\n";
		content += USER.getCurrentPuzzle();

		return content;
	}

	public static void levelUp() {
		USER.setLevel(USER.getLevel() + 1);
	}

	public static void earnGold(int amount) {
		USER.setGold(USER.getGold() + amount);
	}

	public static void spendGold(int cost) {
		USER.setGold(USER.getGold() - cost);
	}

	public static boolean isGoldEnough(int cost) {
		return USER.getGold() - cost >= 0;
	}

	public static void updatePuzzle(String puzzle) {
		USER.setCurrentPuzzle(puzzle);
	}

	public static int checkGold() {
		return USER.getGold();
	}

	public static int checkLevel() {
		return USER.getLevel();
	}

	public static int checkCurrentPuzzleId() {
		return Integer.valueOf(USER.getCurrentPuzzle());
	}

	public static void resetData(Context context) {
		try {
			context.deleteFile(FILENAME);
		} catch (Exception e) {
			
		}
	}
}
