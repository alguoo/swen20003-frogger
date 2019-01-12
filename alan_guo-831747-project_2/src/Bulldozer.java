
/**
 * A bulldozer which cannot be moved into, and pushes the player if it is in its path.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Bulldozer extends MovingSprite {
	private static final String ASSET_PATH = "assets/bulldozer.png";
	private static final float SPEED = 0.05f;

	/**
	 * Creates a bulldozer vehicle via its initial position and direction of
	 * traversal.
	 * 
	 * @param x        The initial x-coordinate of the vehicle.
	 * @param y        The initial y-coordinate of the vehicle.
	 * @param dirRight True if moving right.
	 */
	public Bulldozer(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED, new String[] { Sprite.SOLID, Sprite.PUSHING });
	}

}
