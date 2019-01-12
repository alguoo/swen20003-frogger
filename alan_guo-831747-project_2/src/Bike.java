import org.newdawn.slick.Input;

/**
 * A bike which reverses direction when it reaches set boundaries.
 * 
 * by Alan Guo, University of Melbourne
 */
public class Bike extends MovingSprite {
	private static final String ASSET_PATH = "assets/bike.png";
	private static final float SPEED = 0.2f;
	private static final float BOUNDARY_LEFT = 24;
	private static final float BOUNDARY_RIGHT = 1000;

	/** Creates a bike vehicle via its initial position and direction of traversal.
	 * @param x The initial x-coordinate of the vehicle.
	 * @param y The initial y-coordinate of the vehicle.
	 * @param dirRight True if moving right.
	 */
	public Bike(float x, float y, boolean dirRight) {
		super(ASSET_PATH, x, y, dirRight, SPEED, new String[] {Sprite.HAZARD});
	}

	/** Reverse the bike's direction of movement if it reaches certain boundaries. */
	public void reverseDir() {
		if (getX() < BOUNDARY_LEFT) {
			setDir(true);
		}
		if (getX() > BOUNDARY_RIGHT) {
			setDir(false);
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		reverseDir();
		super.update(input, delta);
	}
}
