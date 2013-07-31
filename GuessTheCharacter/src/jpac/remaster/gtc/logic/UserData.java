package jpac.remaster.gtc.logic;

import jpac.remaster.gtc.data.Data;
import jpac.remaster.gtc.util.Constants;
import android.os.Bundle;

public class UserData implements Data {

	public static final long IDENTIFIER = 7465682526L; 
	
	private String username = Constants.DEFAULT_USER;

	private int level = Constants.START_LEVEL;

	private int gold = Constants.START_GOLD;

	private int currentPuzzle = Constants.NA;

	@Override
	public long identify() {
		return IDENTIFIER;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public Bundle readContent() {
		Bundle bundle = new Bundle(5);
		
		bundle.putLong("id", IDENTIFIER);
		bundle.putString("username", username);
		bundle.putInt("level", level);
		bundle.putInt("gold", gold);
		bundle.putInt("current_puzzle", currentPuzzle);
		
		return bundle;
	}

	@Override
	public void updateContent(Bundle bundle) {
		if (bundle.getInt("id") == IDENTIFIER) {
			username = bundle.getString("username");
			level = bundle.getInt("level");
			gold = bundle.getInt("gold");
			currentPuzzle = bundle.getInt("current_puzzle");
		}
	}
}
