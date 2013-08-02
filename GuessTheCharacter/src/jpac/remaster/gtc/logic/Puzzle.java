package jpac.remaster.gtc.logic;

public class Puzzle {

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

	private String cleanAnswer;

	private int difficulty;

	private int id;
	
	private String imageId;

	private long randomSeed;

	private String rawAnswer;

	private Category category;

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

	private void cleanAnswer() {
		cleanAnswer = rawAnswer.replace(" ", "").replace("-", "")
				.replace(":", "");
	}

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
