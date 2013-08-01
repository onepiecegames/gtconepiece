package jpac.remaster.gtc.logic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jpac.remaster.gtc.util.Util;
import android.content.Context;

public class PuzzleManager {

	private static ArrayList<Integer> solvedPuzzles = new ArrayList<Integer>(50);

	private static final String FILENAME = "solved_puzzle.raw";

	public static Puzzle currentPuzzle = null;

	public static ArrayList<Integer> smartLoader;

	public static void init(Context context) {
		smartLoader = new ArrayList<Integer>();
		smartLoad();
	}

	private static void smartLoad() {
		while (smartLoader.size() < 10) {
			int index = -1;
			while (index == -1 || solvedPuzzles.contains(index)
					|| smartLoader.contains(index)) {
				index = Util.randInt(PuzzleFactory.TOTAL_PUZZLE);
			}
			smartLoader.add(index);
		}
	}

	public static void populateList(String[] data) {
		int n = data.length;

		for (int i = 1; i < n; i++) {
			if (data[i] != null) {
				solvedPuzzles.add(Integer.valueOf(data[i]));
			}
		}
	}

	public static void saveData(Context context) {
		String content = null;

		content = getDataAsRaw();

		if (content == "") {
			return;
		}

		content = "puzzle-id\n" + content;

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
		String content = "";
		int n = solvedPuzzles.size();

		for (int i = 0; i < n; i++) {
			content += "" + solvedPuzzles.get(i);
			if (i != n - 1) {
				content += "\n";
			}
		}

		return content;
	}

	public static void markAsSolved(int id) {
		solvedPuzzles.add(id);
	}

	public static Puzzle getNextPuzzle() {
		if (solvedPuzzles.size() == PuzzleFactory.TOTAL_PUZZLE) {
			return null;
		}

		int index = -1;

		if (smartLoader.size() < 3
				&& (PuzzleFactory.TOTAL_PUZZLE - solvedPuzzles.size()) > 1) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					smartLoad();
				}
			}).start();
		}

		index = smartLoader.remove(0);

		return PuzzleFactory.getPuzzleById(index);
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

	public static void resetData(Context context) {
		try {
			context.deleteFile(FILENAME);
			solvedPuzzles.clear();
		} catch (Exception e) {

		}
	}
}
