package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.logic.ButtonManager;
import jpac.remaster.gtc.logic.Puzzle;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserActionListener;
import jpac.remaster.gtc.logic.UserDataManager;
import jpac.remaster.gtc.util.ResourceUtil;
import jpac.remaster.gtc.util.SysInfo.Constants;
import jpac.remaster.gtc.util.Util;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class InGamePage extends GTCActivity implements UserActionListener {

	private static final int REQUEST_SHOW_CONFIRM_BACK = 0;
	private static final int REQUEST_SHOW_ACKNOWLEDGE = 1;
	private static final int REQUEST_CONFIRM_REVEAL = 2;
	private static final int REQUEST_CONFIRM_REMOVE = 3;
	private static final int REQUEST_CONFIRM_SOLVE = 4;

	private Puzzle puzzle;

	private ButtonManager buttonManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_page);

		findViewById(R.id.backButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		buttonManager = ButtonManager.getInstance();

		if (PuzzleManager.isFinished()) {
			puzzle = null;
		} else if (UserDataManager.checkCurrentPuzzleId() == -1) {
			puzzle = PuzzleManager.getNextPuzzle();
		} else {
			puzzle = PuzzleManager.getPuzzleById(UserDataManager
					.checkCurrentPuzzleId());
		}
		PuzzleManager.currentPuzzle = puzzle;

		updateLevel();
		updateGold();

		if (puzzle == null) {
			startActivity(new Intent(this, GameFinishedPage.class));
			finish();
		} else {
			UserDataManager.updatePuzzle("" + puzzle.getId());

			buttonManager.init(this, puzzle.getAnswer());
			buttonManager.setChoices(PuzzleManager.createChoiceSet(
					puzzle.getAnswer(), puzzle.getRandomSeed()));
			buttonManager.initAnswerField(this, puzzle.getRawAnswer());
			buttonManager.setAnswerDoneListener(this);
			buttonManager.loadData(this);

			final ImageView puzzle1 = (ImageView) findViewById(R.id.puzzleImage1);

			try {
				puzzle1.setImageBitmap(ResourceUtil.loadPuzzleImage(puzzle
						.getImageId()));
			} catch (RuntimeException e) {
				finish();
			}
			updatePuzzleDifficulty();
		}
	}

	private void updateLevel() {
		final TextView level = (TextView) findViewById(R.id.currLevelLabel);
		level.setText("" + UserDataManager.checkLevel());
	}

	private void updateGold() {
		final TextView gold = (TextView) findViewById(R.id.amountLabel);
		gold.setText("" + UserDataManager.checkGold());
	}

	private void updatePuzzleDifficulty() {
		int level = puzzle.getDifficulty();

		final ImageView level1 = (ImageView) findViewById(R.id.level1);
		final ImageView level2 = (ImageView) findViewById(R.id.level2);
		final ImageView level3 = (ImageView) findViewById(R.id.level3);
		final ImageView level4 = (ImageView) findViewById(R.id.level4);
		final ImageView level5 = (ImageView) findViewById(R.id.level5);

		switch (level) {
		case 5:
			level5.setImageResource(R.drawable.level_full);
		case 4:
			level4.setImageResource(R.drawable.level_full);
		case 3:
			level3.setImageResource(R.drawable.level_full);
		case 2:
			level2.setImageResource(R.drawable.level_full);
		case 1:
			level1.setImageResource(R.drawable.level_full);
		}
	}

	public void removeLetter(View v) {
		if (UserDataManager.isGoldEnough(Constants.REMOVE_COST)) {
			showConfirmUseHint("Pay " + Constants.REMOVE_COST
					+ " gold to remove an incorrect letter from the choices.",
					REQUEST_CONFIRM_REMOVE);
		} else {
			showInsufficientGold(Constants.REMOVE_COST);
		}
	}

	public void revealLetter(View v) {
		if (UserDataManager.isGoldEnough(Constants.REVEAL_COST)) {
			showConfirmUseHint("Pay " + Constants.REVEAL_COST
					+ " gold to reveal a correct letter.",
					REQUEST_CONFIRM_REVEAL);
		} else {
			showInsufficientGold(Constants.REVEAL_COST);
		}
	}

	public void solvePuzzle(View v) {
		if (UserDataManager.isGoldEnough(Constants.SOLVE_COST)) {
			showConfirmUseHint("Pay " + Constants.SOLVE_COST
					+ " gold to solve the puzzle.", REQUEST_CONFIRM_SOLVE);
		} else {
			showInsufficientGold(Constants.SOLVE_COST);
		}
	}
	
	public void askFacebook(View v) {
		
	}

	private void showConfirmUseHint(String message, int requestCode) {
		Intent intent = new Intent(this, ConfirmationPopup.class);
		intent.putExtra("title", "Confirm Use Hint");
		intent.putExtra("message", message);
		startActivityForResult(intent, requestCode);
	}

	private void showInsufficientGold(int cost) {
		Intent intent = new Intent(this, AcknowledgementPopup.class);
		intent.putExtra("title", "Insufficient Gold");
		intent.putExtra("message", "You need at least " + cost
				+ " Gold to ues this hint.");
		startActivityForResult(intent, REQUEST_SHOW_ACKNOWLEDGE);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ConfirmationPopup.class);
		intent.putExtra("title", "Confirm Action");
		intent.putExtra("message", "Return to Main Menu?");
		startActivityForResult(intent, REQUEST_SHOW_CONFIRM_BACK);
		return;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_SHOW_CONFIRM_BACK:
				finish();
				break;
			case REQUEST_CONFIRM_REMOVE:
				if (buttonManager.removeLetter(puzzle.getAnswer())) {
					UserDataManager.spendGold(Constants.REMOVE_COST);
				} else {
					Util.displayToast(this, "No more letter to remove");
				}
				break;
			case REQUEST_CONFIRM_REVEAL:
				UserDataManager.spendGold(Constants.REVEAL_COST);
				buttonManager.revealLetter(puzzle.getAnswer());
				break;
			case REQUEST_CONFIRM_SOLVE:
				UserDataManager.spendGold(Constants.SOLVE_COST);
				showLevelComplete();
			default:
				break;
			}

			updateGold();
		}
	}

	@Override
	public void onAnswerComplete(String sequence) {
		if (puzzle.getAnswer().compareTo(sequence) == 0) {
			showLevelComplete();
		} else {
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			findViewById(R.id.answers).startAnimation(shake);
		}
	}

	private void showLevelComplete() {
		PuzzleManager.markAsSolved(puzzle.getId());
		int prize = puzzle.getDifficulty() * Constants.PUZZLE_PRIZE;
		UserDataManager.earnGold(prize);
		UserDataManager.levelUp();
		Intent intent = new Intent(this, LevelFinishedPage.class);
		intent.putExtra("prize", prize);
		intent.putExtra("image", puzzle.getImageId());
		intent.putExtra("answer", puzzle.getFormattedAnswer());
		startActivity(intent);

		ResourceUtil.forRecycle(puzzle.getImageId());

		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		buttonManager.saveData(this, puzzle);
		UserDataManager.saveData(this);
		PuzzleManager.saveData(this);
	}

}
