
/**
 * A bus, which travels at constant speed and direction.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Bus extends MovingSprite {
	private static final String ASSET_PATH = "assets/bus.png";
	private static final float SPEED = 0.15f;

	/**
	 * Creates a bus vehicle via its initial position and direction of traversal.
	 * 
	 * @param x        The initial x-coordinate of the vehicle.
	 * @param y        The initial y-coordinate of the vehicle.
	 * @param dirRight True if moving right.
	 */
	public Bus(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED, new String[] { Sprite.HAZARD });
	}
}
