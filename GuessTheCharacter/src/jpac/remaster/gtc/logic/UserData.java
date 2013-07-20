package jpac.remaster.gtc.logic;

public class UserData {

	private String username = "default_user";

	private int level = 1;

	private int gold = 50;

	private String currentPuzzle = "X";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(String data) {
		this.level = Integer.valueOf(data);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(String data) {
		this.gold = Integer.valueOf(data);
	}

	public int getCurrentPuzzle() {
		if (currentPuzzle.compareTo("X") == 0) {
			return -1;
		}
		return Integer.valueOf(currentPuzzle);
	}

	public void setCurrentPuzzle(String currentPuzzle) {
		this.currentPuzzle = currentPuzzle;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
}
