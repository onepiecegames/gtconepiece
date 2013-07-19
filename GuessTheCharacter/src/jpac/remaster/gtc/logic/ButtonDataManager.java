package jpac.remaster.gtc.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jpac.remaster.gtc.logic.ButtonManager.AnswerButton;
import jpac.remaster.gtc.logic.ButtonManager.ChoiceButton;
import android.content.Context;

public class ButtonDataManager {

	private static final String filename = "btn.sav";

	public static void loadData(Context context, ButtonManager manager) {
		String content = "";

		try {
			FileInputStream fis = context.openFileInput(filename);

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

		if (content.length() > 0) {
			String[] metadata = content.split(":");
			manager.setData(metadata);
		}
	}

	public static void saveData(Context context, Puzzle puzzle,
			ChoiceButton[] layer1, ChoiceButton[] layer2,
			ArrayList<AnswerButton> answers) {
		String data = "";

		data += puzzle.getAnswer();
		data += "@" + puzzle.getId();

		ChoiceButton btn;
		String meta1 = ":@";
		for (int i = 0; i < 7; i++) {
			btn = layer1[i];
			if (btn.isRemoved()) {
				meta1 += btn.getText() + "@";
			}
		}
		for (int i = 0; i < 7; i++) {
			btn = layer2[i];
			if (btn.isRemoved()) {
				meta1 += btn.getText() + "@";
			}
		}
		data += meta1.substring(0, meta1.length() - 1);

		AnswerButton ans;
		String meta2 = ":";
		int n = answers.size();
		for (int i = 0; i < n; i++) {
			ans = answers.get(i);
			if (ans.isLocked()) {
				meta2 += ans.getText() + "@";
			} else {
				meta2 += "#@";
			}
		}
		data += meta2.substring(0, meta2.length() - 1);

		try {
			FileOutputStream fos = context.openFileOutput(filename,
					Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
