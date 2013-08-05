package jpac.remaster.gtc.logic;

import java.util.ArrayList;

import jpac.remaster.gtc.util.Util;

public class PuzzleManager {

	private static ArrayList<Integer> puzzleList;
	
	static {
		puzzleList = new ArrayList<Integer>();
		
		for (int i=0; i<PuzzleFactory.TOTAL_PUZZLE; i++) {
			puzzleList.add(i);
		}
	}
	
	private static ArrayList<Integer> solvedPuzzles = new ArrayList<Integer>(50);

	public static Puzzle currentPuzzle = null;

	public static void populateList(String[] data) {
		int n = data.length;

		for (int i = 1; i < n; i++) {
			if (data[i] != null) {
				solvedPuzzles.add(Integer.valueOf(data[i]));
				puzzleList.remove(Integer.valueOf(data[i]));
			}
		}
	}

	public static void markAsSolved(int id) {
		solvedPuzzles.add(id);
	}

	public static Puzzle getNextPuzzle() {
		if (puzzleList.size() <= 0) {
			return null;
		}
		
		Util.log("Puzzles: " + puzzleList.size());
		
		int idxFromList = Util.randInt(puzzleList.size());
		int puzzleIndex = puzzleList.remove(idxFromList);
		
		return PuzzleFactory.getPuzzleById(puzzleIndex);
	}

	public static boolean isFinished() {
		return solvedPuzzles.size() == PuzzleFactory.TOTAL_PUZZLE;
	}

	public static String[] createChoiceSet(String answer, long randomSeed) {
		Util.setSeed(randomSeed);

		String[] choicesRaw = Util.generateRandomCharacters(
				14 - answer.length(), answer);
		String[] choicesFinal = Util.shuffleContents(choicesRaw, 5);

		return choicesFinal;
	}

	public static Puzzle getPuzzleById(int id) {
		Puzzle puzzle = PuzzleFactory.getPuzzleById(id);

		if (solvedPuzzles.contains(puzzle.getId())) {
			puzzle = getNextPuzzle();
		}

		return puzzle;
	}
	
	public static void reset() {
		solvedPuzzles.clear();
		puzzleList.clear();
		
		for (int i=0; i<PuzzleFactory.TOTAL_PUZZLE; i++) {
			puzzleList.add(i);
		}
	}
}
