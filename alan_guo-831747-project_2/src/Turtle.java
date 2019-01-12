import org.newdawn.slick.Input;

/**
 * A rideable turtle, which travels at constant speed and direction. Dives
 * underwater and becomes unrideable and is not rendered for a certain time.
 *
 * by Alan Guo, University of Melbourne
 */
public class Turtle extends RideableSprite {
	private static final String ASSET_PATH = "assets/turtles.png";
	private static final float SPEED = 0.085f;
	private static final float TIME_LOOP = (7+2)*World.SEC_TO_MILLISEC;
	private static final float TIME_UNDERWATER = 7*World.SEC_TO_MILLISEC;
	private float timer = 0;

	/**
	 * Create a turtle based on initial location and direction.
	 * 
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 * @param dirRight True if sprite is moving right.
	 */
	public Turtle(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED);
	}

	@Override
	public void update(Input input, int delta) {
		// update looping timer since last frame
		timer += delta;
		timer %= TIME_LOOP;

		super.update(input, delta);
	}

	@Override
	public boolean isFloating() {
		// If sufficient time has passed, the turtle will be not floating
		if (timer > TIME_UNDERWATER) {
			return false;
		}
		return super.isFloating();
	}

	@Override
	public void render() {
		if (isFloating()) {
			super.render();
		}
	}

}
