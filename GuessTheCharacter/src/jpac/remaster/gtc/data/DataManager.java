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
package jpac.remaster.gtc.data;

import jpac.remaster.gtc.logic.UserData;
import jpac.remaster.gtc.util.Constants;
import jpac.remaster.gtc.util.ResourceManager;
import android.content.Context;
import android.content.SharedPreferences;

/******************************************************************************
 * This class manages the flow of data for this application. It hosts several
 * data handlers and navigate the outcoming and incoming data objects to their
 * respective destination.
 * 
 * @author JP Carabuena
 * @since 2.0
 *****************************************************************************/
public class DataManager {

	// ========================================================================
	// A static context reference used for resource operations.
	// ========================================================================
	private static Context contextRef;

	// ========================================================================
	// Version 1.0 Internal file compatibility
	// ========================================================================
	public static final int USER_DATA = 1;
	public static final int BUTTON_STATE = 2;
	public static final int SOLVED_PUZZLES = 3;

	private static UserData userData;
	private static String puzzleMetadata;
	private static String buttonMetadata;
	private static int socialData;

	/**************************************************************************
	 * Set the reference context. This must be the context of the current active
	 * activity.
	 *************************************************************************/
	public static void setContextReference(Context context) {
		DataManager.contextRef = context;
	}
	
	public static void init() {
		userData = new UserData();
		socialData = Constants.NA;
		
		for (int i = 1; i <= SOLVED_PUZZLES; i++) {
			checkInternalFile(i);
		}
	}

	private static void checkInternalFile(int file) {
		String name = null;
		switch (file) {
		case USER_DATA:
			name = Constants.FILE_USER_DATA;
			break;
		case BUTTON_STATE:
			name = Constants.FILE_BUTTON_DATA;
			break;
		case SOLVED_PUZZLES:
			name = Constants.FILE_USER_DATA;
			break;
		}

		if (ResourceManager.isFileExist(name)) {
			String content = ResourceManager.loadData(name);
			processContent(file, content);
		}
	}

	private static void processContent(int file, String content) {
		switch (file) {
		case USER_DATA:
			restoreUserData(content);
			break;
		case BUTTON_STATE:
			buttonMetadata = content;
			updateStringPrefs("button_metadata", buttonMetadata);
			break;
		case SOLVED_PUZZLES:
			puzzleMetadata = content;
			updateStringPrefs("puzzle_metadata", puzzleMetadata);
			break;
		}
	}

	private static void restoreUserData(String content) {
		if (content != null) {
			String[] data = content.split("\n");

			userData.setLevel(Integer.valueOf(data[0]));
			userData.setGold(Integer.valueOf(data[1]));
			userData.setCurrentPuzzle(Integer.valueOf(data[2]));

			updateStringPrefs("username", userData.getUsername());
			updateIntPrefs("level", userData.getLevel());
			updateIntPrefs("gold", userData.getGold());
			updateIntPrefs("current_puzzle", userData.getCurrentPuzzle());
		}
	}

	public static boolean checkIfPosted(int id) {
		return socialData == id;
	}

	public static void updatePosted(int id) {
		socialData = id;
		updateIntPrefs("posted_puzzle", id);
	}
	
	// ========================================================================
	// Metadata Operations
	// ========================================================================
	public static void updateSolvedPuzzle(int id) {
		if (puzzleMetadata.length() > 0) {
			puzzleMetadata += "\n" + id;
		} else {
			puzzleMetadata = String.valueOf(id);
		}
		updateStringPrefs("puzzle_metadata", puzzleMetadata);
	}
	
	public static void updateButtonMetadata(int id) {
		if (puzzleMetadata.length() > 0) {
			puzzleMetadata += "\n" + id;
		} else {
			puzzleMetadata = String.valueOf(id);
		}
		updateStringPrefs("puzzle_metadata", puzzleMetadata);
	}
	
	// ========================================================================
	// User data Operations
	// ========================================================================
	public static void levelUp() {
		userData.setLevel(userData.getLevel() + 1);
		updateIntPrefs("level", userData.getLevel());
	}

	public static void earnGold(int amount) {
		userData.setGold(userData.getGold() + amount);
		updateIntPrefs("gold", userData.getGold());
	}

	public static void spendGold(int cost) {
		userData.setGold(userData.getGold() - cost);
		updateIntPrefs("gold", userData.getGold());
	}

	public static boolean isGoldEnough(int cost) {
		return userData.getGold() - cost >= 0;
	}

	public static void updatePuzzle(int puzzle) {
		userData.setCurrentPuzzle(puzzle);
		updateIntPrefs("current_puzzle", userData.getCurrentPuzzle());
	}

	public static int checkGold() {
		return userData.getGold();
	}

	public static int checkLevel() {
		return userData.getLevel();
	}

	public static int checkCurrentPuzzleId() {
		return Integer.valueOf(userData.getCurrentPuzzle());
	}
	
	private static void updateIntPrefs(String key, int value) {
		SharedPreferences prefs = contextRef.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt(key, value);
		editor.commit();
	}
	
	private static void updateStringPrefs(String key, String value) {
		SharedPreferences prefs = contextRef.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(key, value);
		editor.commit();
	}

	public static void reset() {
		SharedPreferences prefs = contextRef.getSharedPreferences(
				Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();

		userData = new UserData();
		editor.putString("username", userData.getUsername());
		editor.putInt("level", userData.getLevel());
		editor.putInt("gold", userData.getGold());
		editor.putInt("current_puzzle", userData.getCurrentPuzzle());
		
		editor.commit();
	}
}
