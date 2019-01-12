import org.newdawn.slick.Input;

/**
 * Class for sprites which move based off speed and left/right direction.
 * Handles initialisation and reappearance when object moves off screen.
 * 
 * based on Sample Project by Eleanor McMurtry, University of Melbourne
 * modified by Alan Guo, University of Melbourne
 */
public abstract class MovingSprite extends Sprite {
	private boolean dirRight;
	private float speed;

	// returns the beginning off-screen position
	private final float getInitialX() {
		return dirRight ? -super.getWidth() / 2 : App.SCREEN_WIDTH + super.getWidth() / 2;
	}

	/**
	 * Create a moving sprite based on image file, location, direction, speed and
	 * tags.
	 * 
	 * @param imgSrc   The location of image file to load.
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 * @param dirRight True if sprite is moving right.
	 * @param speed    The speed in pixels per millisecond.
	 * @param tags     The tags of the sprite.
	 */
	public MovingSprite(String imgSrc, float x, float y, boolean dirRight, float speed, String[] tags) {
		super(imgSrc, x, y, tags);

		this.dirRight = dirRight;
		this.speed = speed;
	}
	
	/**
	 * Accesses the speed of the moving sprite.
	 * @return	the speed of the moving sprite
	 */	
	public final float getSpeed() {
		return speed;
	}
	/**
	 * Accesses the horizontal direction of the moving sprite.
	 * @return	the horizontal direction of the moving sprite
	 */	
	public final boolean getDir() {
		return dirRight;
	}
	/**
	 * Sets the horizontal direction of the moving sprite.
	 * @param newDir The target horizontal direction
	 */
	public final void setDir(boolean newDir) {
		this.dirRight = newDir;
	}
	
	@Override
	public void update(Input input, int delta) {
		move(speed * delta * (dirRight ? 1 : -1), 0);

		// check if the vehicle has moved off the screen
		if (getX() > App.SCREEN_WIDTH + super.getWidth() / 2 || getX() < -super.getWidth() / 2
				|| getY() > App.SCREEN_HEIGHT + super.getWidth() / 2 || getY() < -super.getWidth() / 2) {
			setX(getInitialX());
		}
	}
}
