
/**
 * A frog sprite which appears when a hole is reached. Will cause death when
 * player makes contact with it.
 * 
 * by Alan Guo, University of Melbourne
 */
public class FillHoleFrog extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";

	/**
	 * Create a frog based on its location.
	 * 
	 * @param x The x-coordinate of the sprite.
	 * @param y The y-coordinate of the sprite.
	 */
	public FillHoleFrog(float x, float y) {
		super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD });
	}
}
