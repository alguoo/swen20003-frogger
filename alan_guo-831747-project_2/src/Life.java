
/**
 * A Life sprite which appears on the lower right of the screen. Represents the
 * number of lives the player has remaining.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Life extends Sprite {
	private static final String ASSET_PATH = "assets/lives.png";
	private static final float X_BASE_LOC = 24;
	private static final float Y_BASE_LOC = 744;
	private static final float GAP_SIZE = 32;

	/**
	 * Create a Life object, located based on the number of lives.
	 * 
	 * @param n The number of lives.
	 */
	public Life(int n) {
		super(ASSET_PATH, X_BASE_LOC + n * GAP_SIZE, Y_BASE_LOC);
	}

}
