package jpac.remaster.gtc.logic;

import java.util.ArrayList;
import java.util.List;


public class ButtonMetadata {

	private String puzzleAnswer;

	private int puzzleID;

	private ArrayList<String> removedButtons;
	
	private boolean[] lockedState; 
	
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
		
		for (int i=0; i<n; i++) {
			removedButtons.add(data[i]);
		}
	}

	public void setLockedMetadata(String locked) {
		int n = locked.length();
		initLockState(n);
		
		for (int i=0; i<n; i++) {
			String letter = locked.substring(i, i+1);
			if (letter.compareTo("O") == 0) {
				lockedState[i] = true;
			}
		}
	}
	
	public List<String> getRemovedButtons() {
		return removedButtons;
	}
	
	public void initLockState(int n) {
		lockedState = new boolean[n];
		
		for(int i=0; i<n; i++) {
			lockedState[i] = false;
		}
	}
	
	public void lock(int id) {
		if (id < 0 || id >= lockedState.length) {
			return;
		}
		
		lockedState[id] = true;
	}
	
	public boolean[] getLockState() {
		return lockedState;
	}
}
