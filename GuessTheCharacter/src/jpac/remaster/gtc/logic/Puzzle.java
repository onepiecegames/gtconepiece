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
package jpac.remaster.gtc.logic;

/******************************************************************************
 * This class is an abstraction of a Puzzle model. It is the object that
 * represents a puzzle or question for the current level.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class Puzzle {

	/**************************************************************************
	 * A list of valid puzzle image category.
	 * 
	 * @author JP Carabuena
	 * @since 2.0
	 *************************************************************************/
	public static enum Category {

		// ====================================================================
		// This category refers to images that shows any weapon, parts or
		// whole, representing a particular character. This weapon should be
		// anything the character uses during combat not classified as ability.
		// ====================================================================
		WEAPON,

		// ====================================================================
		// This category refers to images that shows any ability, parts or
		// whole, representing a particular character. Forms, stances, and
		// attacks (non-weapon) belongs to this category.
		// ====================================================================
		ABILITY,

		// ====================================================================
		// This category refers to images that shows a part of a character's
		// overall clothing. This can range from pants, shorts, blouse, shirts,
		// skirts, bikinis, etc.
		// ====================================================================
		CLOTHING,

		// ====================================================================
		// This category refers to images that shows a markings unique to a
		// particular character. This can range from tattoos, emblems and/or
		// scars.
		// ====================================================================
		MARKS,

		// ====================================================================
		// This category refers to images that shows a part or whole of an item
		// related or being used by a particular character. This includes all
		// non-weapon items owned by the character.
		// ====================================================================
		ACCESSORY,

		// ====================================================================
		// This category refers to images that shows a part of the character's
		// body. This ranges from body parts below the head except in some
		// cases, hair is included in this category.
		// ====================================================================
		BODY,

		// ====================================================================
		// This category refers to images that shows parts of the character's
		// head: ears, eyes, mouth, beards, mustache, brows, hair, nose, etc.
		// ====================================================================
		FACE;
	}

	// ========================================================================
	// The cleaned answer derived from the raw answer. The extra characters
	// (' ', '-', ':') are removed. Only the letter remains.
	// ========================================================================
	private String cleanAnswer;

	// ========================================================================
	// The degree of difficulty of this puzzle. This will also be used as a
	// multiplier for the prize earned after solving the puzzle.
	// ========================================================================
	private int difficulty;

	// ========================================================================
	// The integer id representing this puzzle.
	// ========================================================================
	private int id;
	
	// ========================================================================
	// The name of the image bitmap. This is a six digit number combination
	// representing a PNG file in the assets/puzzle folder.
	// ========================================================================
	private String imageId;

	// ========================================================================
	// This is the random seed which will be used by the special random
	// generator.
	// ========================================================================
	private long randomSeed;

	// ========================================================================
	// The answer for this puzzle in raw unclean format.
	// ========================================================================
	private String rawAnswer;

	// ========================================================================
	// The category of this puzzle.
	// ========================================================================
	private Category category;

	/**************************************************************************
	 * Creates a new puzzle object.
	 *************************************************************************/
	public Puzzle(String rawAnswer, Category category, long randomSeed,
			String imageId1, int id, int difficulty) {
		super();
		this.rawAnswer = rawAnswer;
		this.randomSeed = randomSeed;
		this.imageId = imageId1;
		this.id = id;
		this.difficulty = difficulty;
		this.category = category;
		cleanAnswer();
	}

	/**************************************************************************
	 * Clean the raw answer. Removes non-letter characters.
	 *************************************************************************/
	private void cleanAnswer() {
		cleanAnswer = rawAnswer.replace(" ", "").replace("-", "")
				.replace(":", "");
	}

	/**************************************************************************
	 * Gets a formatted answer. The formatted answer removes the '-' character
	 * and replace ':' with a whitespace.
	 *************************************************************************/
	public String getFormattedAnswer() {
		String formattedAnswer = "";

		if (rawAnswer.contains("-:")) {
			formattedAnswer = rawAnswer.replace("-:", "");
		} else if (rawAnswer.contains(":")) {
			formattedAnswer = rawAnswer.replace(":", " ");
		} else {
			formattedAnswer = rawAnswer;
		}

		return formattedAnswer;
	}

	// ========================================================================
	// Getters
	// ========================================================================
	
	public String getAnswer() {
		return cleanAnswer;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getId() {
		return id;
	}

	public String getImageId() {
		return imageId;
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public String getRawAnswer() {
		return rawAnswer;
	}

	public String getCategory() {
		return category.name();
	}
}
