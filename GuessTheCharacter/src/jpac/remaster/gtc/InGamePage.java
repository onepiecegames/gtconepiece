/******************************************************************************
 * Copyright 2013
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jpac.remaster.gtc;

import jpac.remaster.gtc.core.GTCActivity;
import jpac.remaster.gtc.data.DataManager;
import jpac.remaster.gtc.logic.ButtonManager;
import jpac.remaster.gtc.logic.Puzzle;
import jpac.remaster.gtc.logic.PuzzleManager;
import jpac.remaster.gtc.logic.UserActionListener;
import jpac.remaster.gtc.util.Constants;
import jpac.remaster.gtc.util.ResourceManager;
import jpac.remaster.gtc.util.Util;
import jpac.remaster.gtc.util.social.GTCAuthAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/******************************************************************************
 * This is the main game activity. This is where the puzzle images will be
 * displayed to the user.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class InGamePage extends GTCActivity implements UserActionListener {

	// ========================================================================
	// Request codes.
	// ========================================================================
	private static final int REQUEST_SHOW_CONFIRM_BACK = 0;
	private static final int REQUEST_SHOW_ACKNOWLEDGE = 1;
	private static final int REQUEST_CONFIRM_REVEAL = 2;
	private static final int REQUEST_CONFIRM_REMOVE = 3;
	private static final int REQUEST_CONFIRM_SOLVE = 4;
	private static final int REQUEST_SHARE_FACEBOOK = 5;
	private static final int REQUEST_PUBLISH_FEED = 6;

	// ========================================================================
	// The current puzzle.
	// ========================================================================
	private Puzzle puzzle;

	// ========================================================================
	// This is the button manager for the current level.
	// ========================================================================
	private ButtonManager buttonManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_page);

		setOnClickListener(R.id.backButton, new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		buttonManager = ButtonManager.getInstance();

		// loads the puzzle
		if (PuzzleManager.isFinished()) {
			puzzle = null;
		} else if (DataManager.checkCurrentPuzzleId() == -1) {
			puzzle = PuzzleManager.getNextPuzzle();
		} else {
			puzzle = PuzzleManager.getPuzzleById(DataManager
					.checkCurrentPuzzleId());
		}
		PuzzleManager.currentPuzzle = puzzle;

		updateLevel();
		updateGold();

		if (puzzle == null) {
			startActivity(new Intent(this, GameFinishedPage.class));
			finish();
		} else {
			DataManager.updatePuzzle(puzzle.getId());

			String puzzleInfo = DataManager.getPuzzleInfo();
			String removedButtonMeta = DataManager.getRemovedButtons();
			String lockStateMeta = DataManager.getLockedButtons();

			// initialize choice buttons and answer layout
			buttonManager.init(this, puzzle);
			buttonManager.setChoices(PuzzleManager.createChoiceSet(
					puzzle.getAnswer(), puzzle.getRandomSeed()));
			buttonManager.initAnswerField(this, puzzle.getRawAnswer());
			buttonManager.setAnswerDoneListener(this);

			buttonManager.setData(puzzleInfo, removedButtonMeta, lockStateMeta);
			
			try {
				setImage(
						R.id.puzzleImage1,
						ResourceManager.loadBitmapFromAsset(puzzle.getImageId()));
			} catch (RuntimeException e) {
				finish();
			}
			updatePuzzleDifficulty();

			setTypeface(R.id.categoryLabel,
					ResourceManager.getFont("digitalstrip.ttf"));
			setText(R.id.categoryLabel, puzzle.getCategory());
		}
	}

	/**************************************************************************
	 * Update the current level from the shared preferences.
	 *************************************************************************/
	private void updateLevel() {
		setText(R.id.currLevelLabel, "" + DataManager.checkLevel());
		setTypeface(R.id.currLevelLabel,
				ResourceManager.getFont("digitalstrip.ttf"));
		setTypeface(R.id.levelLabel,
				ResourceManager.getFont("digitalstrip.ttf"));
	}

	/**************************************************************************
	 * Update the current gold from the shared preferences.
	 *************************************************************************/
	private void updateGold() {
		setText(R.id.amountLabel, "" + DataManager.checkGold());
		setTypeface(R.id.amountLabel,
				ResourceManager.getFont("digitalstrip.ttf"));
		setTypeface(R.id.goldLabel, ResourceManager.getFont("digitalstrip.ttf"));
	}

	/**************************************************************************
	 * Update the current puzzle difficulty from the shared preferences.
	 *************************************************************************/
	private void updatePuzzleDifficulty() {
		int level = puzzle.getDifficulty();
		int drawable = R.drawable.level_full;

		switch (level) {
		case 5:
			setImageResource(R.id.level5, drawable);
		case 4:
			setImageResource(R.id.level4, drawable);
		case 3:
			setImageResource(R.id.level3, drawable);
		case 2:
			setImageResource(R.id.level2, drawable);
		case 1:
			setImageResource(R.id.level1, drawable);
		}
	}

	/**************************************************************************
	 * Use the remove letter hint.
	 *************************************************************************/
	public void removeLetter(View v) {
		if (DataManager.isGoldEnough(Constants.REMOVE_COST)) {
			showConfirmUseHint("Pay " + Constants.REMOVE_COST
					+ " gold to remove an incorrect letter from the choices.",
					REQUEST_CONFIRM_REMOVE);
		} else {
			showInsufficientGold(Constants.REMOVE_COST);
		}
	}

	/**************************************************************************
	 * Use the reveal letter hint.
	 *************************************************************************/
	public void revealLetter(View v) {
		if (DataManager.isGoldEnough(Constants.REVEAL_COST)) {
			showConfirmUseHint("Pay " + Constants.REVEAL_COST
					+ " gold to reveal a correct letter.",
					REQUEST_CONFIRM_REVEAL);
		} else {
			showInsufficientGold(Constants.REVEAL_COST);
		}
	}

	/**************************************************************************
	 * Solve the puzzle and advance to the next level.
	 *************************************************************************/
	public void solvePuzzle(View v) {
		if (DataManager.isGoldEnough(Constants.SOLVE_COST)) {
			showConfirmUseHint("Pay " + Constants.SOLVE_COST
					+ " gold to solve the puzzle.", REQUEST_CONFIRM_SOLVE);
		} else {
			showInsufficientGold(Constants.SOLVE_COST);
		}
	}

	/**************************************************************************
	 * Ask facebook friends for help.
	 *************************************************************************/
	public void askFacebook(View v) {
		if (DataManager.checkIfPosted(puzzle.getId())) {
			startActivityForResult(
					Util.createAcknowledgePopup(
							this,
							"Facebook",
							"You've already posted this image.\n\nCheck the comments"
									+ " section to know what your friends think."),
					REQUEST_SHOW_ACKNOWLEDGE);
		} else {
			startActivityForResult(Util.createCustomConfirmPopup(this,
					ResourceManager.loadString(R.string.label_social),
					ResourceManager.loadString(R.string.label_social_message),
					"facebook.otf", R.color.chambray,
					ResourceManager.loadString(R.string.label_share),
					Constants.CANCEL_BUTTON), REQUEST_SHARE_FACEBOOK);
		}
	}

	/**************************************************************************
	 * Display confirmation popup for using hints.
	 *************************************************************************/
	private void showConfirmUseHint(String message, int requestCode) {
		startActivityForResult(
				Util.createConfirmPopup(this, "Confirm Use Hint", message),
				requestCode);
	}

	/**************************************************************************
	 * Display notification that user have insufficient gold.
	 *************************************************************************/
	private void showInsufficientGold(int cost) {
		startActivityForResult(Util.createAcknowledgePopup(this,
				"Insufficient Gold", "You need at least " + cost
						+ " Gold to ues this hint."), REQUEST_SHOW_ACKNOWLEDGE);
	}

	@Override
	public void onBackPressed() {
		startActivityForResult(Util.createConfirmPopup(this, "Confirm Action",
				"Return to Main Menu?"), REQUEST_SHOW_CONFIRM_BACK);
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
					DataManager.spendGold(Constants.REMOVE_COST);
				} else {
					Util.displayToast(this, "No more letter to remove");
				}
				break;
			case REQUEST_CONFIRM_REVEAL:
				DataManager.spendGold(Constants.REVEAL_COST);
				buttonManager.revealLetter(puzzle.getAnswer());
				break;
			case REQUEST_CONFIRM_SOLVE:
				DataManager.spendGold(Constants.SOLVE_COST);
				showLevelComplete();
				break;
			case REQUEST_SHARE_FACEBOOK:
				if (GTCAuthAdapter.isNetworkAvailable(getApplicationContext())) {
					ResourceManager.captureView(findViewById(R.id.gamepage));
					Intent intent = new Intent(this, SocialPostingPage.class);
					intent.putExtra("action", SocialPostingPage.ACTION_SHARE);
					startActivityForResult(intent, REQUEST_PUBLISH_FEED);
				} else {
					Util.displayToast(getApplicationContext(),
							"Check your Internet connection.");
				}
				break;
			case REQUEST_PUBLISH_FEED:
				DataManager.updatePosted(puzzle.getId());
				break;
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
			startAnimation(R.id.answers, shake);
		}
	}

	/**************************************************************************
	 * Display the level completed page.
	 *************************************************************************/
	private void showLevelComplete() {
		PuzzleManager.markAsSolved(puzzle.getId());
		int prize = puzzle.getDifficulty() * Constants.PUZZLE_PRIZE;
		DataManager.earnGold(prize);
		DataManager.levelUp();
		
		DataManager.clearButtonMetadata();
		
		Intent intent = new Intent(this, LevelFinishedPage.class);
		intent.putExtra("prize", prize);
		intent.putExtra("image", puzzle.getImageId());
		intent.putExtra("answer", puzzle.getFormattedAnswer());
		startActivity(intent);

		ResourceManager.queueBitmapForRecycle(puzzle.getImageId());

		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
