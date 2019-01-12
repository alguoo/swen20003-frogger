
/**
 * A rideable log, which travels at constant speed and direction.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Log extends LogType {
	private static final String ASSET_PATH = "assets/log.png";
	private static final float SPEED = 0.1f;

	/**
	 * Create a log based on initial location and direction.
	 * 
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 * @param dirRight True if sprite is moving right.
	 */
	public Log(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED);
	}

}
