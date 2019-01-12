
/**
 * A racecar, which travels at constant speed and direction.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Racecar extends MovingSprite {
	private static final String ASSET_PATH = "assets/racecar.png";
	private static final float SPEED = 0.5f;

	/**
	 * Creates a racecar vehicle via its initial position and direction of
	 * traversal.
	 * 
	 * @param x        The initial x-coordinate of the vehicle.
	 * @param y        The initial y-coordinate of the vehicle.
	 * @param dirRight True if moving right.
	 */
	public Racecar(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED, new String[] { Sprite.HAZARD });
	}
}
