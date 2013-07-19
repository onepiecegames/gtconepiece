package jpac.remaster.gtc.logic;

public class Puzzle {

	private String cleanAnswer;

	private int difficulty;

	private int id;

	private String imageId;

	private long randomSeed;

	private String rawAnswer;

	public Puzzle(String rawAnswer, long randomSeed, String imageId1, int id,
			int difficulty) {
		super();
		this.rawAnswer = rawAnswer;
		this.randomSeed = randomSeed;
		this.imageId = imageId1;
		this.id = id;
		this.difficulty = difficulty;
		cleanAnswer();
	}

	private void cleanAnswer() {
		cleanAnswer = rawAnswer.replace(" ", "").replace("-", "")
				.replace(":", "");
	}

	public String getFormattedAnswer() {
		return rawAnswer.replace("-", "").replace(":", "");
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
}
