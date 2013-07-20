package jpac.remaster.gtc.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jpac.remaster.gtc.util.Util;
import android.content.Context;

public class PuzzleManager {

	private static ArrayList<Integer> solvedPuzzles;

	private static final String FILENAME = "solved_puzzle.raw";

	public static Puzzle currentPuzzle = null;

	public static void init(Context context) {
		solvedPuzzles = new ArrayList<Integer>(50);

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
			populateList(data);
		}
	}

	private static void populateList(String[] data) {
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

		while (index == -1 || solvedPuzzles.contains(index)) {
			index = Util.randInt(PuzzleFactory.TOTAL_PUZZLE);
		}

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
}
