package jpac.remaster.gtc.logic;

import java.util.ArrayList;

import jpac.remaster.gtc.R;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ButtonManager {

	private ChoiceButton[] layer1;
	private ChoiceButton[] layer2;

	private ArrayList<AnswerButton> answers;

	private int choiceClicked = 0;

	private UserActionListener answerDoneListener;
	private ArrayList<Integer> indices;
	private ArrayList<Integer> filteredIndex;

	private String answerCharacter;

	private static final ButtonManager manager = new ButtonManager();

	private Typeface fontBold;

	public static ButtonManager getInstance() {
		return manager;
	}

	private ButtonManager() {
		layer1 = new ChoiceButton[7];
		layer2 = new ChoiceButton[7];
	}

	public void init(Activity activity, Puzzle puzzle) {
		fontBold = ResourceManager.getFont("roboto_bold.ttf");

		final ViewGroup choiceLayer1 = (ViewGroup) activity
				.findViewById(R.id.choiceLayer1);
		int layer1Count = choiceLayer1.getChildCount();
		for (int i = 0; i < layer1Count; i++) {
			layer1[i] = new ChoiceButton((Button) choiceLayer1.getChildAt(i));
			layer1[i].setOnClickListener(new OnChoiceClickListener(1, i));
			layer1[i].setTypeface(fontBold);
		}

		final ViewGroup choiceLayer2 = (ViewGroup) activity
				.findViewById(R.id.choiceLayer2);
		int layer2Count = choiceLayer2.getChildCount();
		for (int i = 0; i < layer2Count; i++) {
			layer2[i] = new ChoiceButton((Button) choiceLayer2.getChildAt(i));
			layer2[i].setOnClickListener(new OnChoiceClickListener(2, i));
			layer2[i].setTypeface(fontBold);
		}

		if (answers == null) {
			answers = new ArrayList<AnswerButton>(20);
		}
		answers.clear();

		if (indices == null) {
			indices = new ArrayList<Integer>(20);
		}

		indices.clear();
		for (int i = 0; i < 14; i++) {
			indices.add(i);
		}

		if (filteredIndex == null) {
			filteredIndex = new ArrayList<Integer>(20);
		}
		filteredIndex.clear();

		answerCharacter = puzzle.getAnswer();

		choiceClicked = 0;

		DataManager.updatePuzzleInfo(puzzle.getId(), puzzle.getAnswer());
	}

	public void setData(String puzzleInfo, String removed, String locked) {
		String curr = PuzzleManager.currentPuzzle.getId() + "@"
				+ PuzzleManager.currentPuzzle.getAnswer();

		if (curr.compareTo(puzzleInfo) == 0) {
			// has removed
			if (removed != null && removed.length() > 0) {

				if (removed.length() > 1) {
					String[] removedLetters = removed.split(":");
					int n = removedLetters.length;
					ChoiceButton btn;
					for (int i = 0; i < n; i++) {
						for (int j = 0; j < 14; j++) {
							if (j < 7) {
								btn = layer1[j];
							} else {
								btn = layer2[j - 7];
							}
							if (btn.getText().compareTo(removedLetters[i]) == 0
									&& btn.isIdle()) {
								btn.remove();
								indices.remove(Integer.valueOf(j));
								break;
							}
						}
					}
				} else {
					ChoiceButton btn;
					for (int i = 0; i < 14; i++) {
						if (i < 7) {
							btn = layer1[i];
						} else {
							btn = layer2[i - 7];
						}
						if (btn.getText().compareTo(removed) == 0) {
							btn.remove();
							indices.remove(Integer.valueOf(i));
							break;
						}
					}
				}
			}

			// has answered
			if (locked != null) {
				String[] answeredLetters = locked.split("@");
				int n = answeredLetters.length;

				for (int i = 0; i < n; i++) {
					String current = answeredLetters[i];
					if (current.compareTo("#") != 0) {
						AnswerButton ans = answers.get(i);
						ChoiceButton btn;
						int layer, index;
						for (int j = 0; j < 14; j++) {
							if (j < 7) {
								btn = layer1[j];
								layer = 1;
								index = j;
							} else {
								btn = layer2[j - 7];
								layer = 2;
								index = j - 7;
							}
							if (btn.isIdle()
									&& btn.getText().compareTo(current) == 0) {
								btn.select(i);
								ans.changeContent(layer, index, current);
								ans.lockButton();
								choiceClicked++;
								indices.remove(Integer.valueOf(j));
								break;
							}
						}
					}
				}
			}
		}
	}

	class OnChoiceClickListener implements OnClickListener {

		private int layer;

		private int index;

		public OnChoiceClickListener(int layer, int index) {
			this.layer = layer;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			choiceSelected(layer, index);
		}
	}

	public void choiceSelected(int layer, int index) {
		if (choiceClicked != answers.size()) {
			if (layer == 1) {
				int idx = addAnswer(layer, index, layer1[index].getText()
						.toString());
				layer1[index].select(idx);
			} else {
				int idx = addAnswer(layer, index, layer2[index].getText()
						.toString());
				layer2[index].select(idx);
			}
			choiceClicked++;
			checkForDone();
		}
	}

	public void setAnswerDoneListener(UserActionListener listener) {
		answerDoneListener = listener;
	}

	private void checkForDone() {
		if (choiceClicked == answers.size()) {
			String sequence = "";
			int n = answers.size();
			for (int i = 0; i < n; i++) {
				sequence += answers.get(i).getText();
			}
			answerDoneListener.onAnswerComplete(sequence);
		}
	}

	private int addAnswer(int layer, int index, String text) {
		int n = answers.size();
		for (int i = 0; i < n; i++) {
			AnswerButton btn = answers.get(i);
			if (btn.isEqualTo("")) {
				btn.changeContent(layer, index, text);
				return i;
			}
		}
		return -1;
	}

	public void initAnswerField(Activity activity, String answer) {
		String[] layers = answer.split(":");
		int n = layers.length;

		final ViewGroup answerField = (ViewGroup) activity
				.findViewById(R.id.answers);

		for (int i = 0; i < n; i++) {
			LinearLayout sublayout = new LinearLayout(
					activity.getApplicationContext());
			sublayout.setOrientation(LinearLayout.HORIZONTAL);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 0,
					ResourceManager.loadDimension(R.dimen.margin_small));
			sublayout.setLayoutParams(params);
			sublayout.setGravity(Gravity.CENTER_HORIZONTAL);

			String layer = layers[i];
			int m = layer.length();
			for (int j = 0; j < m; j++) {
				Button btn = initAnswerButton(activity.getApplicationContext(),
						layer.substring(j, j + 1));
				String text = answer.substring(j, j + 1);
				if (text.compareTo("-") != 0 && text.compareTo(" ") != 0) {
					addAnswerButton(btn);
				}
				sublayout.addView(btn);
			}

			answerField.addView(sublayout);
		}
	}

	private final int ANSWER_WIDTH = ResourceManager
			.loadDimension(R.dimen.answer_box_button);
	private final int ANSWER_HEIGHT = ResourceManager
			.loadDimension(R.dimen.box_button);
	private final int ANSWER_MARGIN = ResourceManager
			.loadDimension(R.dimen.margin_xsmall);
	private final int ANSWER_TEXT_SIZE = ResourceManager
			.loadDimension(R.dimen.font_normal);

	private Button initAnswerButton(Context context, String text) {
		Button button = new Button(context);
		button.setText("");
		if (text.compareTo(" ") == 0) {
			button.setBackgroundDrawable(null);
		} else if (text.compareTo("-") == 0) {
			button.setText("-");
			button.setBackgroundResource(R.drawable.choice_box_locked);
		} else {
			button.setBackgroundResource(R.drawable.letter_variant_btn);
		}
		button.setTypeface(fontBold);
		button.setTextColor(Color.WHITE);
		button.setTextSize(ANSWER_TEXT_SIZE);
		button.setClickable(false);
		button.setEnabled(false);

		final LinearLayout.LayoutParams viewMargin = new LinearLayout.LayoutParams(
				ANSWER_WIDTH, ANSWER_HEIGHT);
		viewMargin.setMargins(ANSWER_MARGIN, 0, ANSWER_MARGIN, 0);
		button.setLayoutParams(viewMargin);

		return button;
	}

	private void addAnswerButton(Button btn) {
		btn.setOnClickListener(new OnAnswerClickListener());
		answers.add(new AnswerButton(btn));
	}

	class OnAnswerClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			answerSelected((Button) v);
		}

	}

	private void choiceReset(int layer, int index) {
		if (layer == 1) {
			layer1[index].makeVisible();
		} else {
			layer2[index].makeVisible();
		}
		choiceClicked--;
	}

	public void answerSelected(Button btn) {
		int n = answers.size();

		for (int i = 0; i < n; i++) {
			AnswerButton ans = answers.get(i);
			if (ans.getText() == btn.getText()) {
				ans.reset();
				choiceReset(ans.getLayer(), ans.getIndex());
				break;
			}
		}
	}

	public void setChoices(String[] choices) {
		int n = choices.length;

		if (n != 14) {
			return;
		}

		for (int i = 0; i < n; i++) {
			ChoiceButton btn;
			if (i < 7) {
				btn = layer1[i];
			} else {
				btn = layer2[i - 7];
			}
			btn.setText(choices[i]);
		}
	}

	class ChoiceButton {

		private final int STATE_IDLE = 0;
		private final int STATE_SELECTED = 1;
		private final int STATE_REMOVED = 2;

		private int state;

		private Button btn;

		private int answerIndex = -1;

		public ChoiceButton(Button btn) {
			this.btn = btn;
			this.state = STATE_IDLE;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getState() {
			return this.state;
		}

		public void makeVisible() {
			this.btn.setVisibility(View.VISIBLE);
			this.setState(STATE_IDLE);
		}

		public void makeInvisible() {
			this.btn.setVisibility(View.INVISIBLE);
		}

		public String getText() {
			return this.btn.getText().toString();
		}

		public void setText(String text) {
			this.btn.setText(text);
		}

		public void setTypeface(Typeface typeface) {
			this.btn.setTypeface(typeface);
		}

		public void setOnClickListener(OnClickListener listener) {
			this.btn.setOnClickListener(listener);
		}

		public int getAnswerIndex() {
			return answerIndex;
		}

		public void setAnswerIndex(int answerIndex) {
			this.answerIndex = answerIndex;
		}

		public void select(int answerIndex) {
			this.makeInvisible();
			this.setAnswerIndex(answerIndex);
			this.setState(STATE_SELECTED);
		}

		public void remove() {
			this.makeInvisible();
			this.setState(STATE_REMOVED);
		}

		public boolean isIdle() {
			return state == STATE_IDLE;
		}

		public boolean isRemoved() {
			return state == STATE_REMOVED;
		}

		public boolean isSelected() {
			return state == STATE_SELECTED;
		}

		public boolean isEqualTo(String text) {
			return this.getText().compareTo(text) == 0;
		}

		public boolean isEqualTo(char text) {
			return this.isEqualTo(String.valueOf(text));
		}

		public boolean isVisible() {
			return this.btn.getVisibility() == View.VISIBLE;
		}
	}

	class AnswerButton {

		private Button btn;

		private int idx;

		private int layer;

		private int index;

		private boolean locked = false;

		public AnswerButton(Button btn) {
			this.btn = btn;
			this.idx = ButtonManager.this.answers.size();
		}

		public boolean isLocked() {
			return locked;
		}

		public int getLayer() {
			return layer;
		}

		public int getIndex() {
			return index;
		}

		public void changeContent(int layer, int index, String text) {
			this.layer = layer;
			this.index = index;
			this.btn.setText(text);
			this.enable();
		}

		public void disable() {
			this.btn.setEnabled(false);
		}

		public void enable() {
			this.btn.setEnabled(true);
		}

		public String getText() {
			return this.btn.getText().toString();
		}

		public void setText(String text) {
			this.btn.setText(text);
		}

		public void lockButton() {
			this.btn.setBackgroundResource(R.drawable.choice_box_locked);
			this.locked = true;
			// disable the button
			this.disable();
			// DataManager.lock(idx);
		}

		public void reset() {
			this.disable();
			this.setText("");
		}

		public boolean isEqualTo(String text) {
			return this.getText().compareTo(text) == 0;
		}

		public boolean isEqualTo(char text) {
			return this.isEqualTo(String.valueOf(text));
		}

		public int getIdx() {
			return idx;
		}

		public void setIdx(int idx) {
			this.idx = idx;
		}
	}

	public boolean removeLetter(String answer) {
		int n;
		int idx; // index in the indices array list
		int index; // index of the button
		ChoiceButton btn;

		while (indices.size() > 0) {
			n = indices.size();
			idx = Util.randInt(n);
			index = indices.get(idx);

			if (index < 7) {
				btn = layer1[index];
			} else {
				btn = layer2[index - 7];
			}

			// already removed, fail safe only
			if (btn.isRemoved()) {
				indices.remove(Integer.valueOf(index));
			} else if (btn.isSelected()) {
				if (!answer.contains(btn.getText())
						&& btn.getAnswerIndex() != -1) {
					AnswerButton ans = answers.get(btn.getAnswerIndex());
					if (!ans.isLocked()) {
						ans.reset();
						choiceClicked--;
						indices.remove(Integer.valueOf(index));
						btn.remove();
						DataManager.addRemovedButton(btn.getText());
						return true;
					}
				} else {
					filteredIndex.add(index);
					indices.remove(Integer.valueOf(index));
				}
			} else if (btn.isIdle()) {
				if (!answer.contains(btn.getText())) {
					indices.remove(Integer.valueOf(index));
					btn.remove();
					DataManager.addRemovedButton(btn.getText());
					return true;
				} else {
					filteredIndex.add(index);
					indices.remove(Integer.valueOf(index));
				}
			} else {
				break;
			}
		}

		if (indices.size() == 0) {
			if (answerCharacter.length() != filteredIndex.size()) {
				String text;
				// remove possible correct letters
				// filter out duplicates
				while (answerCharacter.length() > 0) {
					text = answerCharacter.substring(0, 1);
					n = filteredIndex.size();
					for (int j = 0; j < n; j++) {
						index = filteredIndex.get(j);
						if (index < 7) {
							btn = layer1[index];
						} else {
							btn = layer2[index - 7];
						}
						if (btn.isEqualTo(text)) {
							filteredIndex.remove(Integer.valueOf(index));
							break;
						}
					}
					answerCharacter = answerCharacter.substring(1);
				}
				// for the remaining letters
				// check if its IDLE, if yes then REMOVED
				//
				if (answerCharacter.length() == 0 && filteredIndex.size() > 0) {
					n = filteredIndex.size();
					for (int i = 0; i < n; i++) {
						idx = filteredIndex.get(i);
						if (idx < 7) {
							btn = layer1[idx];
						} else {
							btn = layer2[idx - 7];
						}
						if (btn.isIdle()) {
							btn.remove();
							DataManager.addRemovedButton(btn.getText());
							return true;
						} else if (btn.isSelected()) {
							String txt = btn.getText();
							// look for other button in choices
							ChoiceButton temp;
							for (int j = 0; j < 14; j++) {
								if (j < 7) {
									temp = layer1[j];
								} else {
									temp = layer2[j - 7];
								}
								if (temp.isIdle() && temp.isEqualTo(txt)) {
									temp.remove();
									DataManager
											.addRemovedButton(temp.getText());
									return true;
								}
							}

							// look for first button in answers
							int len = answers.size();
							AnswerButton ans;
							for (int j = 0; j < len; j++) {
								ans = answers.get(j);
								if (!ans.isLocked() && ans.isEqualTo(txt)
										&& ans.isEqualTo(answer.charAt(j))) {
									ans.reset();
									int x = ans.getLayer();
									int y = ans.getIndex();
									choiceReset(x, y);
									if (x == 1) {
										layer1[y].remove();
										DataManager.addRemovedButton(layer1[y]
												.getText());
									} else {
										layer2[y].remove();
										DataManager.addRemovedButton(layer2[y]
												.getText());
									}
									return true;
								}
							}
						}
					}
				}
			}

		}

		return false;
	}

	public void revealLetter(String answer) {
		int n = answer.length();

		while (choiceClicked != n) {
			int index = Util.randInt(n);
			AnswerButton ans = answers.get(index);
			if (ans.isEqualTo("")) {
				ChoiceButton btn;
				for (int i = 0; i < 14; i++) {
					if (i < 7) {
						btn = layer1[i];
					} else {
						btn = layer2[i - 7];
					}
					if (btn.isIdle() && btn.isEqualTo(answer.charAt(index))) {
						ans.setText(answer.charAt(index) + "");
						ans.lockButton();
						DataManager.updateLockState(createLockState());
						choiceClicked++;
						lockChoiceButton(ans);
						if (choiceClicked == n) {
							String sequence = "";
							for (int j = 0; j < n; j++) {
								sequence += answers.get(j).getText();
							}
							answerDoneListener.onAnswerComplete(sequence);
						}
						return;
					} else if (btn.isSelected()
							&& btn.isEqualTo(answer.charAt(index))) {
						int aidx = btn.getAnswerIndex();
						if (aidx >= 0) {
							AnswerButton temp = answers.get(aidx);
							if (!temp.isLocked()) {
								temp.reset();
								ans.changeContent(temp.getLayer(),
										temp.getIndex(), answer.charAt(index)
												+ "");
								ans.lockButton();
								DataManager.updateLockState(createLockState());
								btn.setAnswerIndex(index);
								if (choiceClicked == n) {
									String sequence = "";
									for (int j = 0; j < n; j++) {
										sequence += answers.get(j).getText();
									}
									answerDoneListener
											.onAnswerComplete(sequence);
								}
								return;
							}
						}
					}
				}
			}
		}

		if (choiceClicked == n) {
			for (int i = 0; i < n; i++) {
				AnswerButton ans = answers.get(i);
				String letter = String.valueOf(answer.charAt(i));

				if (!(ans.isEqualTo(letter))) {
					// look in the choice buttons
					ChoiceButton btn;
					for (int j = 0; j < 14; j++) {
						int x1, y1;
						if (j < 7) {
							btn = layer1[j];
							x1 = 1;
							y1 = j;
						} else {
							btn = layer2[j - 7];
							x1 = 2;
							y1 = j - 7;
						}

						if (btn.isIdle() && btn.isEqualTo(letter)) {
							btn.select(i);
							choiceClicked++;
							int x2 = ans.getLayer();
							int y2 = ans.getIndex();
							choiceReset(x2, y2);
							ans.changeContent(x1, y1, btn.getText());
							ans.lockButton();
							DataManager.updateLockState(createLockState());
							if (choiceClicked == n) {
								String sequence = "";
								for (int l = 0; l < n; l++) {
									sequence += answers.get(l).getText();
								}
								answerDoneListener.onAnswerComplete(sequence);
							}
							return;
						}
					}
					// look in the other answer buttons
					AnswerButton temp;
					for (int j = 0; j < n; j++) {
						if (j != i) {
							temp = answers.get(j);
							if (!temp.isLocked() && temp.isEqualTo(letter)) {
								int x1 = temp.getLayer();
								int y1 = temp.getIndex();
								int x2 = ans.getLayer();
								int y2 = ans.getIndex();
								choiceReset(x2, y2);
								temp.reset();
								if (x1 == 1) {
									btn = layer1[y1];
								} else {
									btn = layer2[y1];
								}
								btn.setAnswerIndex(i);
								ans.changeContent(x1, y1, letter);
								ans.lockButton();
								DataManager.updateLockState(createLockState());
								if (choiceClicked == n) {
									String sequence = "";
									for (int l = 0; l < n; l++) {
										sequence += answers.get(l).getText();
									}
									answerDoneListener
											.onAnswerComplete(sequence);
								}
								return;
							}
						}
					}
				}
			}
		}
	}

	private String createLockState() {
		AnswerButton ans;
		String lockState = "";
		int n = answers.size();
		for (int i = 0; i < n; i++) {
			ans = answers.get(i);
			if (ans.isLocked()) {
				lockState += ans.getText() + "@";
			} else {
				lockState += "#@";
			}
		}
		return lockState;
	}

	private void lockChoiceButton(AnswerButton btn) {
		for (int i = 0; i < 14; i++) {
			if (i < 7) {
				if (btn.isEqualTo(layer1[i].getText()) && layer1[i].isVisible()) {
					layer1[i].select(layer1[i].getAnswerIndex());
					break;
				}
			} else {
				if (btn.isEqualTo(layer2[i - 7].getText())
						&& layer2[i - 7].isVisible()) {
					layer2[i - 7].select(layer2[i - 7].getAnswerIndex());
					break;
				}
			}
		}
	}
}
