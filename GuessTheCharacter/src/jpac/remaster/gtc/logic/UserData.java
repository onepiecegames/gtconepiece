package jpac.remaster.gtc.logic;

import jpac.remaster.gtc.util.Constants;

public class UserData {

	private String username = Constants.DEFAULT_USER;

	private int level = Constants.START_LEVEL;

	private int gold = Constants.START_GOLD;

	private int currentPuzzle = Constants.NA;

	public void setLevel(int level) {
		this.level = level;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void setCurrentPuzzle(int currentPuzzle) {
		this.currentPuzzle = currentPuzzle;
	}

	public String getUsername() {
		return username;
	}

	public int getLevel() {
		return level;
	}

	public int getGold() {
		return gold;
	}

	public int getCurrentPuzzle() {
		return currentPuzzle;
	}
}
