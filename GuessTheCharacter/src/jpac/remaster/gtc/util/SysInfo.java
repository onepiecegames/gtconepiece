package jpac.remaster.gtc.util;

public class SysInfo {

	public static class AppMode {

		public static final int DEBUG = 1;

		public static final int RELEASE = 2;

		// change the value for this before releasing
		private static int mode = DEBUG;

		public static boolean isDebug() {
			return mode == DEBUG;
		}

		public static boolean isRelease() {
			return mode == RELEASE;
		}

		public static int getMode() {
			return mode;
		}
	}

	public static class Constants {

		public static final int REVEAL_COST = 12;
		public static final int REMOVE_COST = 8;
		public static final int SOLVE_COST = 30;
		public static final int UNLOCK_COST = 15;
		public static final int COST_FREE = 0; // for debugging only

		public static final int START_GOLD = 50;

		public static final int PUZZLE_PRIZE = 2;
	}

	public static boolean splash = false;
}
