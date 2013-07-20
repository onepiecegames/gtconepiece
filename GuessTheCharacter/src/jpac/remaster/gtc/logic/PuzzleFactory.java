package jpac.remaster.gtc.logic;

public class PuzzleFactory {

	public static final int TOTAL_PUZZLE = 100;

	public static Puzzle getPuzzleById(int id) {

		switch (id) {
		case 0:
			return new Puzzle("JIMBEI", 237846237, "125415", id, 2);
		case 1:
			return new Puzzle("LUFFY", 37432492, "126338", id, 1);
		case 2:
			return new Puzzle("ZORO", 328746237, "128966", id, 1);
		case 3:
			return new Puzzle("UROUGE", 723482374, "146983", id, 3);
		case 4:
			return new Puzzle("LOLA", 983475923, "151581", id, 2);
		case 5:
			return new Puzzle("MOMONGA", 523146123, "183767", id, 3);
		case 6:
			return new Puzzle("SENGOKU", 63547223, "187675", id, 1);
		case 7:
			return new Puzzle("DOMA", 843759, "201439", id, 2);
		case 8:
			return new Puzzle("SANJI", 327468237, "203440", id, 1);
		case 9:
			return new Puzzle("INAZUMA", 7435327, "216618", id, 2);
		case 10:
			return new Puzzle("TASHIGI", 948648, "220427", id, 2);
		case 11:
			return new Puzzle("CAPONE:BEGE", 84365783, "221029", id, 3);
		case 12:
			return new Puzzle("DOFLA-:MINGO", 32745273, "232033", id, 1);
		case 13:
			return new Puzzle("BEPO", 562372, "233439", id, 1);
		case 14:
			return new Puzzle("IGARAM", 435683465, "238678", id, 2);
		case 15:
			return new Puzzle("FRANKY", 2345274, "247451", id, 2);
		case 16:
			return new Puzzle("MIHAWK", 374682, "250122", id, 1);
		case 17:
			return new Puzzle("HINA", 32742834, "269777", id, 2);
		case 18:
			return new Puzzle("AIN", 326472345, "275632", id, 2);
		case 19:
			return new Puzzle("FRANKY", 237622, "276118", id, 1);
		case 20:
			return new Puzzle("BOA:MARIGOLD", 2163571, "287249", id, 2);
		case 21:
			return new Puzzle("CHOPPER", 328642, "293120", id, 1);
		case 22:
			return new Puzzle("PERONA", 327482, "312654", id, 1);
		case 23:
			return new Puzzle("MAGELLAN", 538465834, "315541", id, 1);
		case 24:
			return new Puzzle("LAW", 4283462, "315681", id, 1);
		case 25:
			return new Puzzle("SANJI", 734682, "328509", id, 3);
		case 26:
			return new Puzzle("SMOKER", 37846582, "343623", id, 1);
		case 27:
			return new Puzzle("BINZ", 4234628, "344228", id, 3);
		case 28:
			return new Puzzle("BOA:HANCOCK", 237846782, "357129", id, 2);
		case 29:
			return new Puzzle("BROWN-:BEARD", 3568374, "369794", id, 2);
		case 30:
			return new Puzzle("CHOPPER", 674389753, "383530", id, 2);
		case 31:
			return new Puzzle("ZETTO", 874829, "383776", id, 1);
		case 32:
			return new Puzzle("VISTA", 7826428, "384901", id, 2);
		case 33:
			return new Puzzle("KUZAN", 26157371, "389013", id, 1);
		case 34:
			return new Puzzle("BROOK", 2378462, "392620", id, 1);
		case 35:
			return new Puzzle("LULU", 437452734, "400927", id, 1);
		case 36:
			return new Puzzle("ABSALOM", 12310987, "425812", id, 3);
		case 37:
			return new Puzzle("WHITEY:BAY", 1236816, "431577", id, 3);
		case 38:
			return new Puzzle("ZORO", 76813618, "433711", id, 1);
		case 39:
			return new Puzzle("X-DRAKE", 12361278, "436115", id, 2);
		case 40:
			return new Puzzle("LUFFY", 4365835, "439364", id, 1);
		case 41:
			return new Puzzle("KILLER", 8345234, "441018", id, 2);
		case 42:
			return new Puzzle("ZORO", 374856837, "441211", id, 2);
		case 43:
			return new Puzzle("BLACK-:BEARD", 17265371, "466519", id, 1);
		case 44:
			return new Puzzle("SOGEKING", 73487538, "479634", id, 1);
		case 45:
			return new Puzzle("DAZ:BONES", 324872364, "488904", id, 2);
		case 46:
			return new Puzzle("FISHER:TIGER", 7732472, "498464", id, 2);
		case 47:
			return new Puzzle("SMILEY", 6113657, "505448", id, 1);
		case 48:
			return new Puzzle("JOZU", 34875682, "513571", id, 1);
		case 49:
			return new Puzzle("SANJI", 643574231, "520728", id, 2);
		case 50:
			return new Puzzle("TOM", 372442374, "522045", id, 1);
		case 51:
			return new Puzzle("BROOK", 437856387, "528030", id, 2);
		case 52:
			return new Puzzle("SADI", 165712111, "535283", id, 2);
		case 53:
			return new Puzzle("SQUARDO", 456789573, "561365", id, 1);
		case 54:
			return new Puzzle("BONNEY", 36547212, "563009", id, 2);
		case 55:
			return new Puzzle("KUMA", 7344283, "594285", id, 1);
		case 56:
			return new Puzzle("CAESAR:CLOWN", 487567234, "597224", id, 1);
		case 57:
			return new Puzzle("GECKO:MORIA", 53874, "605371", id, 1);
		case 58:
			return new Puzzle("HANNYA-:BAL", 436212735, "606294", id, 3);
		case 59:
			return new Puzzle("PORTGAS:D:ACE", 5634786, "609593", id, 1);
		case 60:
			return new Puzzle("KINEMON", 31832784, "620208", id, 2);
		case 61:
			return new Puzzle("HOGBACK", 1123461, "621690", id, 3);
		case 62:
			return new Puzzle("WHITE-:BEARD", 12537845, "626397", id, 2);
		case 63:
			return new Puzzle("ZORO", 89892364, "644640", id, 2);
		case 64:
			return new Puzzle("NAMI", 4672384, "647524", id, 2);
		case 65:
			return new Puzzle("CINDRY", 435651231, "655909", id, 2);
		case 66:
			return new Puzzle("MARCO", 54353324, "668636", id, 2);
		case 67:
			return new Puzzle("USOPP", 7435683, "675284", id, 1);
		case 68:
			return new Puzzle("SANDER-:SONIA", 7435683, "680454", id, 3);
		case 69:
			return new Puzzle("MARGUE-:RITE", 676532, "694087", id, 2);
		case 70:
			return new Puzzle("OARS JR", 73648111, "707753", id, 3);
		case 71:
			return new Puzzle("VERGO", 12671126, "713929", id, 1);
		case 72:
			return new Puzzle("ROB:LUCCI", 7864843, "724925", id, 1);
		case 73:
			return new Puzzle("NICO:ROBIN", 11112367, "736919", id, 3);
		case 74:
			return new Puzzle("LAW", 999342874, "751556", id, 1);
		case 75:
			return new Puzzle("DRAGON", 763583, "783004", id, 2);
		case 76:
			return new Puzzle("SHILLIEW", 831235, "783720", id, 1);
		case 77:
			return new Puzzle("MONET", 2221735, "785584", id, 2);
		case 78:
			return new Puzzle("BON:KUREI", 3487583, "813627", id, 3);
		case 79:
			return new Puzzle("DOBERMAN", 4537213, "826309", id, 2);
		case 80:
			return new Puzzle("KAKU", 78345683, "841345", id, 3);
		case 81:
			return new Puzzle("KIZARU", 498739, "845596", id, 1);
		case 82:
			return new Puzzle("BASIL:HAWKINS", 2137284, "852328", id, 3);
		case 83:
			return new Puzzle("IVANKOV", 3187236, "865687", id, 1);
		case 84:
			return new Puzzle("KOHZA", 2378462, "868675", id, 3);
		case 85:
			return new Puzzle("SANJI", 897239, "880630", id, 2);
		case 86:
			return new Puzzle("NAMI", 1245435, "884098", id, 1);
		case 87:
			return new Puzzle("GALDINO", 7643248, "899076", id, 1);
		case 88:
			return new Puzzle("PENGUIN", 87346, "902988", id, 2);
		case 89:
			return new Puzzle("AKAINU", 347856378, "919374", id, 2);
		case 90:
			return new Puzzle("GARP", 327846, "921889", id, 1);
		case 91:
			return new Puzzle("VIVI", 234634, "930831", id, 2);
		case 92:
			return new Puzzle("ROBIN", 9817239, "940201", id, 3);
		case 93:
			return new Puzzle("LUFFY", 287422131, "959646", id, 2);
		case 94:
			return new Puzzle("LUFFY", 3278462, "961674", id, 1);
		case 95:
			return new Puzzle("PELL", 87342652, "961769", id, 3);
		case 96:
			return new Puzzle("EUSTASS:KID", 7345234, "981823", id, 1);
		case 97:
			return new Puzzle("LUCY", 324233, "981896", id, 2);
		case 98:
			return new Puzzle("MR ZERO", 864783101, "993980", id, 2);
		case 99:
			return new Puzzle("APOO", 9991236, "998761", id, 2);

		default:
			return null;
			/*
			 * case 0: return new Puzzle("LUFFY", 34857934, "000001", id, 1,
			 * "This is Monkey D. Luffy's trademark strawhat given to him by Red Haired Shanks. It earned him his epithet \"Strawhat Luffy\"."
			 * ); case 9: return new Puzzle("BARTO-:LOMEO", 32234682, "551941",
			 * id, 3,
			 * "Bartolomeo has a mark just below his right eye similar to this symbol."
			 * ); case 17: return new Puzzle("NICO:ROBIN", 1231572, "910044",
			 * id, 3,
			 * "This is the design of Nico Robin's casual dress as seen in the Film Strong World."
			 * ); default: return null;
			 */
		}
	}
}
