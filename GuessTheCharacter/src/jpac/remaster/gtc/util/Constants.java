package jpac.remaster.gtc.util;

public class Constants {

	// ========================================================================
	// These are the costs of the available hints in the game.
	// ========================================================================
	public static final int REVEAL_COST = 12;
	public static final int REMOVE_COST = 8;
	public static final int SOLVE_COST = 30;
	public static final int COST_FREE = 0; // for debugging only

	// ========================================================================
	// The starting gold for first time user.
	// ========================================================================
	public static final int START_GOLD = 50;

	// ========================================================================
	// This is the standard amount user receive after solving a puzzle.
	// ========================================================================
	public static final int PUZZLE_PRIZE = 2;

	// ========================================================================
	// This is the standard amount user receive after solving a puzzle.
	// ========================================================================
	public static final int RECYCLE_THRESHOLD = 5;

	// ========================================================================
	// The location of files in the asset folder
	// ========================================================================
	public static final String FONT_LOCATION = "font/";
	public static final String IMAGE_LOCATION = "puzzle/";
	
	// ========================================================================
	// A string consisting of the standard alphabet characters.
	// ========================================================================
	public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	// ========================================================================
	// An integer constant representing a not applicable value.
	// ========================================================================
	public static final int NA = -1;

	// ========================================================================
	// Standard starting level.
	// ========================================================================
	public static final int START_LEVEL = 1;

	// ========================================================================
	// Default username. Currently does not have any use.
	// ========================================================================
	public static final String DEFAULT_USER = "default_user";
	
	// ========================================================================
	// Default values for popup buttons.
	// ========================================================================
	public static final String OK_BUTTON = "OK";
	public static final String CANCEL_BUTTON = "Cancel";

	// ========================================================================
	// Intent key for popup activities.
	// ========================================================================
	public static final String POP_TITLE = "title";
	public static final String POP_MESSAGE = "message";
	public static final String POP_SINGLE_BUTTON = "single_button";
	public static final String POP_BUTTON1 = "button1";
	public static final String POP_BUTTON2 = "button2";
	public static final String POP_BANNER_RES = "banner_resource";
	public static final String POP_TITLE_FONT = "title_font";

	// ========================================================================
	// Internal files
	// ========================================================================
	public static final String FILE_USER_DATA = "default_user.sav";
	public static final String FILE_PUZZLE_DATA = "solved_puzzle.raw";
	public static final String FILE_BUTTON_DATA = "btn.sav";

	// ========================================================================
	// SharedPreferences
	// ========================================================================
	public static final String SHARED_PREFERENCES = "gtc_prefs";
	
	// ========================================================================
	// Splash Delay
	// ========================================================================
	public static final int SPLASH_DELAY = 2000;
	
	public static final int SKIP_COUNT = 5;
}
