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

import jpac.remaster.gtc.logic.Puzzle.Category;

/******************************************************************************
 * This factory class is responsible for creating puzzle objects given an
 * integer id.
 * 
 * @author JP Carabuena
 * @since 1.0
 *****************************************************************************/
public class PuzzleFactory {

	// ========================================================================
	// The current total puzzles in the game.
	// ========================================================================
	public static final int TOTAL_PUZZLE = 250;

	/**************************************************************************
	 * Returns a puzzle given an integer id.
	 *************************************************************************/
	public static Puzzle getPuzzleById(int id) {

		switch (id) {
		/* Version 1.0 Puzzles */
		case 0:
			return new Puzzle("JIMBEI", Category.CLOTHING, 237846237, "125415",
					id, 2);
		case 1:
			return new Puzzle("LUFFY", Category.ACCESSORY, 37432492, "126338",
					id, 1);
		case 2:
			return new Puzzle("ZORO", Category.ACCESSORY, 328746237, "128966",
					id, 1);
		case 3:
			return new Puzzle("UROUGE", Category.MARKS, 723482374, "146983",
					id, 3);
		case 4:
			return new Puzzle("LOLA", Category.FACE, 983475923, "151581", id, 2);
		case 5:
			return new Puzzle("MOMONGA", Category.CLOTHING, 523146123,
					"183767", id, 3);
		case 6:
			return new Puzzle("SENGOKU", Category.FACE, 63547223, "187675", id,
					1);
		case 7:
			return new Puzzle("DOMA", Category.FACE, 843759, "201439", id, 2);
		case 8:
			return new Puzzle("SANJI", Category.FACE, 327468237, "203440", id,
					1);
		case 9:
			return new Puzzle("INAZUMA", Category.CLOTHING, 7435327, "216618",
					id, 2);
		case 10:
			return new Puzzle("TASHIGI", Category.CLOTHING, 948648, "220427",
					id, 2);
		case 11:
			return new Puzzle("CAPONE:BEGE", Category.CLOTHING, 84365783,
					"221029", id, 3);
		case 12:
			return new Puzzle("DOFLA-:MINGO", Category.FACE, 32745273,
					"232033", id, 1);
		case 13:
			return new Puzzle("BEPO", Category.CLOTHING, 562372, "233439", id,
					1);
		case 14:
			return new Puzzle("IGARAM", Category.FACE, 435683465, "238678", id,
					2);
		case 15:
			return new Puzzle("FRANKY", Category.ABILITY, 2345274, "247451",
					id, 2);
		case 16:
			return new Puzzle("MIHAWK", Category.FACE, 374682, "250122", id, 1);
		case 17:
			return new Puzzle("HINA", Category.ACCESSORY, 32742834, "269777",
					id, 2);
		case 18:
			return new Puzzle("AIN", Category.CLOTHING, 326472345, "275632",
					id, 2);
		case 19:
			return new Puzzle("FRANKY", Category.BODY, 237622, "276118", id, 1);
		case 20:
			return new Puzzle("BOA:MARIGOLD", Category.ACCESSORY, 2163571,
					"287249", id, 2);
		case 21:
			return new Puzzle("CHOPPER", Category.ACCESSORY, 328642, "293120",
					id, 1);
		case 22:
			return new Puzzle("PERONA", Category.ABILITY, 327482, "312654", id,
					1);
		case 23:
			return new Puzzle("MAGELLAN", Category.ABILITY, 538465834,
					"315541", id, 1);
		case 24:
			return new Puzzle("LAW", Category.CLOTHING, 4283462, "315681", id,
					1);
		case 25:
			return new Puzzle("SANJI", Category.ABILITY, 734682, "328509", id,
					3);
		case 26:
			return new Puzzle("SMOKER", Category.FACE, 37846582, "343623", id,
					1);
		case 27:
			return new Puzzle("BINZ", Category.CLOTHING, 4234628, "344228", id,
					3);
		case 28:
			return new Puzzle("BOA:HANCOCK", Category.CLOTHING, 237846782,
					"357129", id, 2);
		case 29:
			return new Puzzle("BROWN-:BEARD", Category.CLOTHING, 3568374,
					"369794", id, 2);
		case 30:
			return new Puzzle("CHOPPER", Category.CLOTHING, 674389753,
					"383530", id, 2);
		case 31:
			return new Puzzle("ZETTO", Category.WEAPON, 874829, "383776", id, 1);
		case 32:
			return new Puzzle("VISTA", Category.BODY, 7826428, "384901", id, 2);
		case 33:
			return new Puzzle("KUZAN", Category.BODY, 26157371, "389013", id, 1);
		case 34:
			return new Puzzle("BROOK", Category.MARKS, 2378462, "392620", id, 1);
		case 35:
			return new Puzzle("LULU", Category.FACE, 437452734, "400927", id, 1);
		case 36:
			return new Puzzle("ABSALOM", Category.CLOTHING, 12310987, "425812",
					id, 3);
		case 37:
			return new Puzzle("WHITEY:BAY", Category.CLOTHING, 1236816,
					"431577", id, 3);
		case 38:
			return new Puzzle("ZORO", Category.WEAPON, 76813618, "433711", id,
					1);
		case 39:
			return new Puzzle("X-DRAKE", Category.MARKS, 12361278, "436115",
					id, 2);
		case 40:
			return new Puzzle("LUFFY", Category.CLOTHING, 4365835, "439364",
					id, 1);
		case 41:
			return new Puzzle("KILLER", Category.CLOTHING, 8345234, "441018",
					id, 2);
		case 42:
			return new Puzzle("ZORO", Category.CLOTHING, 374856837, "441211",
					id, 2);
		case 43:
			return new Puzzle("BLACK-:BEARD", Category.FACE, 17265371,
					"466519", id, 1);
		case 44:
			return new Puzzle("SOGEKING", Category.ACCESSORY, 73487538,
					"479634", id, 1);
		case 45:
			return new Puzzle("DAZ:BONES", Category.MARKS, 324872364, "488904",
					id, 2);
		case 46:
			return new Puzzle("FISHER:TIGER", Category.MARKS, 7732472,
					"498464", id, 2);
		case 47:
			return new Puzzle("SMILEY", Category.FACE, 6113657, "505448", id, 1);
		case 48:
			return new Puzzle("JOZU", Category.FACE, 34875682, "513571", id, 1);
		case 49:
			return new Puzzle("SANJI", Category.CLOTHING, 643574231, "520728",
					id, 2);
		case 50:
			return new Puzzle("TOM", Category.FACE, 372442374, "522045", id, 1);
		case 51:
			return new Puzzle("BROOK", Category.WEAPON, 437856387, "528030",
					id, 2);
		case 52:
			return new Puzzle("SADI", Category.BODY, 165712111, "535283", id, 2);
		case 53:
			return new Puzzle("SQUARDO", Category.FACE, 456789573, "561365",
					id, 1);
		case 54:
			return new Puzzle("BONNEY", Category.FACE, 36547212, "563009", id,
					2);
		case 55:
			return new Puzzle("KUMA", Category.CLOTHING, 7344283, "594285", id,
					1);
		case 56:
			return new Puzzle("CAESAR:CLOWN", Category.CLOTHING, 487567234,
					"597224", id, 1);
		case 57:
			return new Puzzle("GECKO:MORIA", Category.CLOTHING, 53874,
					"605371", id, 1);
		case 58:
			return new Puzzle("HANNYA-:BAL", Category.CLOTHING, 436212735,
					"606294", id, 3);
		case 59:
			return new Puzzle("PORTGAS:D:ACE", Category.MARKS, 5634786,
					"609593", id, 1);
		case 60:
			return new Puzzle("KINEMON", Category.CLOTHING, 31832784, "620208",
					id, 2);
		case 61:
			return new Puzzle("HOGBACK", Category.CLOTHING, 1123461, "621690",
					id, 3);
		case 62:
			return new Puzzle("WHITE-:BEARD", Category.MARKS, 12537845,
					"626397", id, 2);
		case 63:
			return new Puzzle("ZORO", Category.CLOTHING, 89892364, "644640",
					id, 2);
		case 64:
			return new Puzzle("NAMI", Category.CLOTHING, 4672384, "647524", id,
					2);
		case 65:
			return new Puzzle("CINDRY", Category.MARKS, 435651231, "655909",
					id, 2);
		case 66:
			return new Puzzle("MARCO", Category.ABILITY, 54353324, "668636",
					id, 2);
		case 67:
			return new Puzzle("USOPP", Category.CLOTHING, 7435683, "675284",
					id, 1);
		case 68:
			return new Puzzle("SANDER-:SONIA", Category.ABILITY, 7435683,
					"680454", id, 3);
		case 69:
			return new Puzzle("MARGUE-:RITE", Category.FACE, 676532, "694087",
					id, 2);
		case 70:
			return new Puzzle("OARS JR", Category.ACCESSORY, 73648111,
					"707753", id, 3);
		case 71:
			return new Puzzle("VERGO", Category.FACE, 12671126, "713929", id, 1);
		case 72:
			return new Puzzle("ROB:LUCCI", Category.FACE, 7864843, "724925",
					id, 1);
		case 73:
			return new Puzzle("NICO:ROBIN", Category.CLOTHING, 11112367,
					"736919", id, 3);
		case 74:
			return new Puzzle("LAW", Category.MARKS, 999342874, "751556", id, 1);
		case 75:
			return new Puzzle("DRAGON", Category.MARKS, 763583, "783004", id, 2);
		case 76:
			return new Puzzle("SHILLIEW", Category.ACCESSORY, 831235, "783720",
					id, 1);
		case 77:
			return new Puzzle("MONET", Category.CLOTHING, 2221735, "785584",
					id, 2);
		case 78:
			return new Puzzle("BON:KUREI", Category.CLOTHING, 3487583,
					"813627", id, 3);
		case 79:
			return new Puzzle("DOBERMAN", Category.MARKS, 4537213, "826309",
					id, 2);
		case 80:
			return new Puzzle("KAKU", Category.CLOTHING, 78345683, "841345",
					id, 3);
		case 81:
			return new Puzzle("KIZARU", Category.ABILITY, 498739, "845596", id,
					1);
		case 82:
			return new Puzzle("BASIL:HAWKINS", Category.MARKS, 2137284,
					"852328", id, 3);
		case 83:
			return new Puzzle("IVANKOV", Category.BODY, 3187236, "865687", id,
					1);
		case 84:
			return new Puzzle("KOHZA", Category.CLOTHING, 2378462, "868675",
					id, 3);
		case 85:
			return new Puzzle("SANJI", Category.CLOTHING, 897239, "880630", id,
					2);
		case 86:
			return new Puzzle("NAMI", Category.MARKS, 1245435, "884098", id, 1);
		case 87:
			return new Puzzle("GALDINO", Category.BODY, 7643248, "899076", id,
					1);
		case 88:
			return new Puzzle("PENGUIN", Category.ACCESSORY, 87346, "902988",
					id, 2);
		case 89:
			return new Puzzle("AKAINU", Category.ABILITY, 347856378, "919374",
					id, 2);
		case 90:
			return new Puzzle("GARP", Category.MARKS, 327846, "921889", id, 1);
		case 91:
			return new Puzzle("VIVI", Category.FACE, 234634, "930831", id, 2);
		case 92:
			return new Puzzle("ROBIN", Category.CLOTHING, 9817239, "940201",
					id, 3);
		case 93:
			return new Puzzle("LUFFY", Category.CLOTHING, 287422131, "959646",
					id, 2);
		case 94:
			return new Puzzle("LUFFY", Category.BODY, 3278462, "961674", id, 1);
		case 95:
			return new Puzzle("PELL", Category.MARKS, 87342652, "961769", id, 3);
		case 96:
			return new Puzzle("EUSTASS:KID", Category.FACE, 7345234, "981823",
					id, 1);
		case 97:
			return new Puzzle("LUCY", Category.MARKS, 324233, "981896", id, 2);
		case 98:
			return new Puzzle("MR ZERO", Category.MARKS, 864783101, "993980",
					id, 2);
		case 99:
			return new Puzzle("APOO", Category.FACE, 9991236, "998761", id, 2);
		/* Version 2.0 */
		case 100:
			return new Puzzle("VANDER:DECKEN", Category.CLOTHING, 113287,
					"000113", id, 2);
		case 101:
			return new Puzzle("VASCO:SHOT", Category.ACCESSORY, 434019,
					"004913", id, 3);
		case 102:
			return new Puzzle("JERRY", Category.ABILITY, 993653, "011921", id,
					2);
		case 103:
			return new Puzzle("LIP:DOUGHTY", Category.FACE, 881059, "012520",
					id, 2);
		case 104:
			return new Puzzle("VERY:GOOD", Category.FACE, 846792, "013213", id,
					2);
		case 105:
			return new Puzzle("NOJIKO", Category.ACCESSORY, 371332, "020720",
					id, 2);
		case 106:
			return new Puzzle("JESUS:BURGESS", Category.CLOTHING, 893706,
					"021421", id, 4);
		case 107:
			return new Puzzle("BORSA-:LINO", Category.BODY, 951779, "023313",
					id, 2);
		case 108:
			return new Puzzle("KAMAKIRI", Category.FACE, 939323, "025721", id,
					2);
		case 109:
			return new Puzzle("ENEL", Category.ABILITY, 406108, "030420", id, 1);
		case 110:
			return new Puzzle("WANZE", Category.ACCESSORY, 614739, "031113",
					id, 1);
		case 111:
			return new Puzzle("JYABURA", Category.FACE, 812488, "034520", id, 1);
		case 112:
			return new Puzzle("SCOTCH", Category.MARKS, 513429, "035113", id, 2);
		case 113:
			return new Puzzle("KID", Category.ABILITY, 990782, "040421", id, 2);
		case 114:
			return new Puzzle("YASOPP", Category.CLOTHING, 563862, "042813",
					id, 3);
		case 115:
			return new Puzzle("NICO:OLVIA", Category.FACE, 298569, "045020",
					id, 1);
		case 116:
			return new Puzzle("KOKORO", Category.FACE, 848813, "045421", id, 1);
		case 117:
			return new Puzzle("BELLE-:MERE", Category.FACE, 273802, "050413",
					id, 1);
		case 118:
			return new Puzzle("KONG", Category.FACE, 823933, "053621", id, 2);
		case 119:
			return new Puzzle("ZEFF", Category.FACE, 990789, "053713", id, 1);
		case 120:
			return new Puzzle("ZEO", Category.BODY, 896241, "060213", id, 2);
		case 121:
			return new Puzzle("KUINA", Category.FACE, 302086, "061621", id, 2);
		case 122:
			return new Puzzle("LAKI", Category.ACCESSORY, 324334, "071621", id,
					2);
		case 123:
			return new Puzzle("CC", Category.WEAPON, 277555, "081913", id, 1);
		case 124:
			return new Puzzle("LABOON", Category.FACE, 490194, "083221", id, 1);
		case 125:
			return new Puzzle("MAKINO", Category.ACCESSORY, 202675, "091321",
					id, 2);
		case 126:
			return new Puzzle("DOUBLE-:FINGER", Category.FACE, 975352,
					"095321", id, 3);
		case 127:
			return new Puzzle("EDWARD:NEWGATE", Category.ABILITY, 898757,
					"101913", id, 1);
		case 128:
			return new Puzzle("VALEN-:TINE", Category.CLOTHING, 427796,
					"103921", id, 3);
		case 129:
			return new Puzzle("BELLAMY", Category.FACE, 601079, "105420", id, 1);
		case 130:
			return new Puzzle("NORLAND", Category.FACE, 237050, "112521", id, 2);
		case 131:
			return new Puzzle("OARS", Category.MARKS, 567830, "115821", id, 2);
		case 132:
			return new Puzzle("OHM", Category.MARKS, 962550, "122621", id, 4);
		case 133:
			return new Puzzle("CROCO-:DILE", Category.WEAPON, 862488, "130613",
					id, 1);
		case 134:
			return new Puzzle("PAULIE", Category.CLOTHING, 200317, "131620",
					id, 2);
		case 135:
			return new Puzzle("JIMBEI", Category.ABILITY, 946834, "135813", id,
					1);
		case 136:
			return new Puzzle("BECKMAN", Category.CLOTHING, 628818, "142621",
					id, 3);
		case 137:
			return new Puzzle("HYOUZOU", Category.FACE, 432042, "145320", id, 1);
		case 138:
			return new Puzzle("FUJITORA", Category.FACE, 847895, "153421", id,
					1);
		case 139:
			return new Puzzle("SHANKS", Category.MARKS, 454728, "155312", id, 1);
		case 140:
			return new Puzzle("BOA", Category.ABILITY, 881513, "155413", id, 1);
		case 141:
			return new Puzzle("CHINJAO", Category.MARKS, 553661, "161921", id,
					1);
		case 142:
			return new Puzzle("CARIBOU", Category.FACE, 653814, "163720", id, 2);
		case 143:
			return new Puzzle("NAMI", Category.ABILITY, 831528, "172413", id, 1);
		case 144:
			return new Puzzle("ACE", Category.CLOTHING, 277723, "172620", id, 1);
		case 145:
			return new Puzzle("HATCHAN", Category.BODY, 878292, "173912", id, 2);
		case 146:
			return new Puzzle("LUCCI", Category.BODY, 200621, "183313", id, 1);
		case 147:
			return new Puzzle("BROGY", Category.FACE, 638600, "183912", id, 2);
		case 148:
			return new Puzzle("ALVIDA", Category.FACE, 954462, "191520", id, 1);
		case 149:
			return new Puzzle("DALTON", Category.CLOTHING, 322581, "192612",
					id, 2);
		case 150:
			return new Puzzle("BUGGY", Category.FACE, 306901, "202312", id, 1);
		case 151:
			return new Puzzle("DECKEN", Category.WEAPON, 698723, "205013", id,
					3);
		case 152:
			return new Puzzle("CALIFA", Category.ABILITY, 360756, "212412", id,
					3);
		case 153:
			return new Puzzle("ARLONG", Category.FACE, 435019, "215220", id, 1);
		case 154:
			return new Puzzle("OTOHIME", Category.CLOTHING, 473753, "222112",
					id, 3);
		case 155:
			return new Puzzle("KURO", Category.WEAPON, 255022, "224713", id, 1);
		case 156:
			return new Puzzle("AVALO:PIZARRO", Category.CLOTHING, 578714,
					"224720", id, 3);
		case 157:
			return new Puzzle("VAN:AUGER", Category.ACCESSORY, 487516,
					"232013", id, 2);
		case 158:
			return new Puzzle("PEKOMS", Category.FACE, 254732, "232912", id, 1);
		case 159:
			return new Puzzle("BABY:FIVE", Category.ABILITY, 815645, "233220",
					id, 1);
		case 160:
			return new Puzzle("WAPOL", Category.ABILITY, 258098, "242520", id,
					1);
		case 161:
			return new Puzzle("LAFITTE", Category.FACE, 752212, "243513", id, 2);
		case 162:
			return new Puzzle("PERONA", Category.CLOTHING, 139699, "244712",
					id, 1);
		case 163:
			return new Puzzle("ROUGE", Category.ACCESSORY, 687013, "253312",
					id, 1);
		case 164:
			return new Puzzle("BELLAMY", Category.MARKS, 883719, "262220", id,
					1);
		case 165:
			return new Puzzle("RAYLEIGH", Category.ACCESSORY, 532890, "262712",
					id, 4);
		case 166:
			return new Puzzle("PEARL", Category.WEAPON, 236652, "263313", id, 3);
		case 167:
			return new Puzzle("REBECCA", Category.CLOTHING, 364373, "271712",
					id, 2);
		case 168:
			return new Puzzle("BLUENO", Category.BODY, 932807, "272720", id, 1);
		case 169:
			return new Puzzle("DON:KRIEG", Category.WEAPON, 603381, "274013",
					id, 1);
		case 170:
			return new Puzzle("GIN", Category.CLOTHING, 244830, "275513", id, 4);
		case 171:
			return new Puzzle("RYUMA", Category.CLOTHING, 650113, "281212", id,
					3);
		case 172:
			return new Puzzle("BOA", Category.ACCESSORY, 213801, "283320", id,
					1);
		case 173:
			return new Puzzle("SABO", Category.FACE, 895382, "284712", id, 2);
		case 174:
			return new Puzzle("SALOME", Category.BODY, 581042, "292112", id, 2);
		case 175:
			return new Puzzle("BOGART", Category.FACE, 135927, "294020", id, 2);
		case 176:
			return new Puzzle("SCOPPER:GABAN", Category.FACE, 928529, "300112",
					id, 2);
		case 177:
			return new Puzzle("STRAW-:BERRY", Category.FACE, 224667, "302620",
					id, 2);
		case 178:
			return new Puzzle("SHALULIA", Category.FACE, 847104, "304512", id,
					2);
		case 179:
			return new Puzzle("WIPER", Category.MARKS, 463523, "312612", id, 3);
		case 180:
			return new Puzzle("YAMAKIJI", Category.FACE, 941927, "312820", id,
					2);
		case 181:
			return new Puzzle("KUREHA", Category.CLOTHING, 803629, "313619",
					id, 2);
		case 182:
			return new Puzzle("SHANKS", Category.ACCESSORY, 624465, "320712",
					id, 1);
		case 183:
			return new Puzzle("DOBERMAN", Category.FACE, 903706, "323720", id,
					2);
		case 184:
			return new Puzzle("SHIKI", Category.CLOTHING, 665908, "324712", id,
					2);
		case 185:
			return new Puzzle("SHIRA-:HOSHI", Category.BODY, 668281, "332412",
					id, 1);
		case 186:
			return new Puzzle("ONIGUMO", Category.ACCESSORY, 471120, "333720",
					id, 2);
		case 187:
			return new Puzzle("KASHI", Category.FACE, 167232, "341319", id, 1);
		case 188:
			return new Puzzle("SHU", Category.FACE, 247647, "341412", id, 2);
		case 189:
			return new Puzzle("MOMONGA", Category.FACE, 514590, "343520", id, 2);
		case 190:
			return new Puzzle("SHURA", Category.FACE, 373802, "350512", id, 3);
		case 191:
			return new Puzzle("CALGARA", Category.MARKS, 627040, "353520", id,
					3);
		case 192:
			return new Puzzle("OIMO", Category.FACE, 481362, "354019", id, 1);
		case 193:
			return new Puzzle("SHYARLY", Category.BODY, 998692, "363212", id, 2);
		case 194:
			return new Puzzle("KEIMI", Category.CLOTHING, 580065, "363520", id,
					2);
		case 195:
			return new Puzzle("SMOKER", Category.ACCESSORY, 942925, "372612",
					id, 2);
		case 196:
			return new Puzzle("CARUE", Category.FACE, 865343, "372920", id, 1);
		case 197:
			return new Puzzle("SENTO-:MARU", Category.CLOTHING, 326310,
					"375719", id, 3);
		case 198:
			return new Puzzle("SURUME", Category.FACE, 728212, "381312", id, 1);
		case 199:
			return new Puzzle("CHAKA", Category.CLOTHING, 655916, "382220", id,
					4);
		case 200:
			return new Puzzle("BARON:TAMAGO", Category.FACE, 806823, "384712",
					id, 2);
		case 201:
			return new Puzzle("SANJUAN:WOLF", Category.FACE, 737584, "390619",
					id, 1);
		case 202:
			return new Puzzle("SARQUISS", Category.FACE, 156085, "391020", id,
					3);
		case 203:
			return new Puzzle("T BONE", Category.CLOTHING, 253649, "394212",
					id, 2);
		case 204:
			return new Puzzle("BIG MOM", Category.FACE, 913566, "394420", id, 1);
		case 205:
			return new Puzzle("TILE-:STONE", Category.MARKS, 602131, "401712",
					id, 3);
		case 206:
			return new Puzzle("CHIMNEY", Category.CLOTHING, 509402, "402320",
					id, 4);
		case 207:
			return new Puzzle("BROOK", Category.ABILITY, 850298, "404619", id,
					2);
		case 208:
			return new Puzzle("TOTO", Category.CLOTHING, 279346, "405412", id,
					4);
		case 209:
			return new Puzzle("COBY", Category.CLOTHING, 214568, "411820", id,
					1);
		case 210:
			return new Puzzle("GAN FALL", Category.WEAPON, 388706, "414412",
					id, 3);
		case 211:
			return new Puzzle("DARUMA", Category.FACE, 519594, "420819", id, 2);
		case 212:
			return new Puzzle("CONIS", Category.BODY, 333134, "421720", id, 2);
		case 213:
			return new Puzzle("HILULUK", Category.ACCESSORY, 735965, "423312",
					id, 2);
		case 214:
			return new Puzzle("DADAN", Category.FACE, 533962, "430820", id, 2);
		case 215:
			return new Puzzle("JIGORO", Category.CLOTHING, 228666, "433519",
					id, 4);
		case 216:
			return new Puzzle("DOC Q", Category.FACE, 211924, "435820", id, 2);
		case 217:
			return new Puzzle("CROCUS", Category.CLOTHING, 751646, "443919",
					id, 3);
		case 218:
			return new Puzzle("DOMINO", Category.FACE, 100078, "444220", id, 1);
		case 219:
			return new Puzzle("DORRY", Category.FACE, 316654, "454220", id, 1);
		case 220:
			return new Puzzle("KING:NEPTUNE", Category.FACE, 631304, "461919",
					id, 1);
		case 221:
			return new Puzzle("DOSUN", Category.FACE, 458741, "463620", id, 2);
		case 222:
			return new Puzzle("GENZO", Category.FACE, 248334, "472319", id, 2);
		case 223:
			return new Puzzle("MORIA", Category.FACE, 444020, "472420", id, 2);
		case 224:
			return new Puzzle("YORKI", Category.MARKS, 911572, "485519", id, 4);
		case 225:
			return new Puzzle("FOXY", Category.FACE, 878519, "492420", id, 2);
		case 226:
			return new Puzzle("FUKABO-:SHI", Category.FACE, 886733, "500820",
					id, 1);
		case 227:
			return new Puzzle("SALDEATH", Category.FACE, 891135, "501219", id,
					2);
		case 228:
			return new Puzzle("BECKMAN", Category.FACE, 628695, "511019", id, 2);
		case 229:
			return new Puzzle("FUKURO", Category.FACE, 673140, "511920", id, 2);
		case 230:
			return new Puzzle("GENBO", Category.ACCESSORY, 344010, "521820",
					id, 3);
		case 231:
			return new Puzzle("BRAHAM", Category.WEAPON, 161906, "534120", id,
					4);
		case 232:
			return new Puzzle("SPANDAM", Category.ACCESSORY, 937045, "540112",
					id, 2);
		case 233:
			return new Puzzle("HELMEPPO", Category.ACCESSORY, 743664, "544220",
					id, 2);
		case 234:
			return new Puzzle("HERACLES", Category.BODY, 987594, "551720", id,
					3);
		case 235:
			return new Puzzle("GOMO-:RRAH", Category.BODY, 104033, "552112",
					id, 2);
		case 236:
			return new Puzzle("TONJIT", Category.FACE, 861262, "555519", id, 2);
		case 237:
			return new Puzzle("SODOM", Category.BODY, 119368, "555712", id, 2);
		case 238:
			return new Puzzle("HODY:JONES", Category.CLOTHING, 703148,
					"555920", id, 2);
		case 239:
			return new Puzzle("BARTO-:LOMEO", Category.MARKS, 146361, "565119",
					id, 2);
		case 240:
			return new Puzzle("ICEBURG", Category.CLOTHING, 959104, "565720",
					id, 2);
		case 241:
			return new Puzzle("AISA", Category.FACE, 935353, "565812", id, 2);
		case 242:
			return new Puzzle("IKAROS", Category.ACCESSORY, 812686, "573620",
					id, 2);
		case 243:
			return new Puzzle("CHARLOSS", Category.FACE, 608435, "574519", id,
					1);
		case 244:
			return new Puzzle("KUMADORI", Category.BODY, 914280, "583612", id,
					3);
		case 245:
			return new Puzzle("DUVAL", Category.FACE, 582019, "583920", id, 1);
		case 246:
			return new Puzzle("CATARINA", Category.FACE, 875554, "585919", id,
					1);
		case 247:
			return new Puzzle("USOLAND", Category.FACE, 139824, "591812", id, 1);
		case 248:
			return new Puzzle("JEAN:BART", Category.MARKS, 955255, "592520",
					id, 3);
		case 249:
			return new Puzzle("RAYLEIGH", Category.FACE, 210032, "595519", id,
					2);
		default:
			return null;
		}
	}
}
