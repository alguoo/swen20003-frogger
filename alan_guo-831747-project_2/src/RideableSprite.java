
/** 
 * Class for rideable sprites which the player can ride on.
 * 
 * by Alan Guo, University of Melbourne
 */
public abstract class RideableSprite extends MovingSprite {
	/**
	 * Create a rideable sprite based on image file, location, direction and speed.
	 * 
	 * @param imgSrc   The location of image file to load.
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 * @param dirRight True if sprite is moving right.
	 * @param speed    The speed in pixels per millisecond.
	 */
	public RideableSprite(String imgSrc, float x, float y, boolean dirRight, float speed) {
		super(imgSrc, x, y, dirRight, speed, new String[] { Sprite.RIDEABLE });
	}

	/**
	 * Check if the sprite is floating, and therefore rideable by the player.
	 * 
	 * @return True if sprite is floating.
	 */
	public boolean isFloating() {
		return true;
	}

}
