package jpac.remaster.gtc.logic;

public class Puzzle {

	public static enum Category {
		
		WEAPON,
		
		ABILITY,
		
		CLOTHING,
		
		MARKS,
		
		ACCESSORY,
		
		BODY,
		
		FACE;
	}
	
	private String cleanAnswer;

	private int difficulty;

	private int id;

	private String imageId;

	private long randomSeed;

	private String rawAnswer;
	
	private Category category;
	
	public Puzzle(String rawAnswer, Category category, long randomSeed, String imageId1, int id,
			int difficulty) {
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
