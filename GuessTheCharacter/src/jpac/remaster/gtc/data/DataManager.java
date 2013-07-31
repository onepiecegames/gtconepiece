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

import jpac.remaster.gtc.util.Constants;
import jpac.remaster.gtc.util.ResourceManager;
import android.content.Context;

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
	// Internal file compatibility
	// ========================================================================
	public static final int USER_DATA = 1;
	public static final int BUTTON_STATE = 2;
	public static final int SOLVED_PUZZLES = 3;
	
	public static void init(Context context) {
		for (int i=1; i<=SOLVED_PUZZLES; i++) {
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
			
		}
	}

}
