package jpac.remaster.gtc.logic;

import java.util.ArrayList;
import java.util.List;

public class ButtonMetadata {

	private String puzzleAnswer;

	private int puzzleID;

	private ArrayList<String> removedButtons;
	
	private String lockedButton = "";

	public ButtonMetadata() {
		removedButtons = new ArrayList<String>();
	}

	public String getPuzzleAnswer() {
		return puzzleAnswer;
	}

	public int getPuzzleID() {
		return puzzleID;
	}

	public void updatePuzzle(int id, String answer) {
		this.puzzleID = id;
		this.puzzleAnswer = answer;
	}

	public void addRemovedButton(String text) {
		if (text != null) {
			removedButtons.add(text);
		}
	}

	public void setRemovedMetadata(String[] data) {
		removedButtons = new ArrayList<String>();
		int n = data.length;

		for (int i = 0; i < n; i++) {
			removedButtons.add(data[i]);
		}
	}

	public List<String> getRemovedButtons() {
		return removedButtons;
	}

	public String getLockedButton() {
		return lockedButton;
	}

	public void setLockedButton(String lockedButton) {
		this.lockedButton = lockedButton;
	}
}
