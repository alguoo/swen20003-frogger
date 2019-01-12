
/**
 * Class for LogType sprites, which are rideable.
 * 
 * by Alan Guo, University of Melbourne
 */
public abstract class LogType extends RideableSprite {

	/** Create a rideable sprite based on image file, location, direction and speed.
	 * @param imgSrc The location of image file to load.
	 * @param x The x-coordinate of the sprite.
	 * @param y The y-coordinate of the sprite.
	 * @param dirRight True if sprite is moving right.
	 * @param speed The speed in pixels per millisecond.
	 */
	public LogType(String imgSrc, float x, float y, boolean dirRight, float speed) {
		super(imgSrc, x, y, dirRight, speed);
	}
	

}
