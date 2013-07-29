package jpac.remaster.gtc.util;

public class SysInfo {

	public static class AppMode {

		public static final int DEBUG = 1;

		public static final int RELEASE = 2;

		// change the value for this before releasing
		private static int mode = RELEASE;

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

	public static boolean splash = false;
}
