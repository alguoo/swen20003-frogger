import java.util.ArrayList;
import org.newdawn.slick.Input;
import utilities.BoundingBox;

/**
 * The playable sprite of the game. Handles initialisation, input, rendering,
 * and movement, death, lives mechanics.
 * 
 * based on Sample Project by Eleanor McMurtry, University of Melbourne
 * modified by Alan Guo, University of Melbourne
 */
public class Player extends Sprite {

	/** Initial x-coordinate where the player should begin and return to, in pixels */
	public static final float X_INIT = 512;
	/** Initial y-coordinate where the player should begin and return to, in pixels */
	public static final float Y_INIT = 720;
	private static final String ASSET_PATH = "assets/frog.png";
	private static final float FUZZ = 0.05f*World.TILE_SIZE;
	
	// the speed the Player object moves when being carried or pushed
	private float pushSpeedX = 0;
	// checks whether the Player object is riding another sprite
	private boolean isRiding = false;

	private static final int N_LIVES_INIT = 3;
	// contains the Life sprites which represent the number of lives the Player object has remaining.
	private ArrayList<Life> lives = new ArrayList<>();
	// check if the Player object has lost a life
	private boolean deathFlag = false;

	/**
	 * Create player based on its location, and its lives.
	 * 
	 * @param x The x-coordinate of the player.
	 * @param y The y-coordinate of the player.
	 */
	public Player(float x, float y) {
		super(ASSET_PATH, x, y);

		// Initialises Life objects the player has.
		for (int i = 0; i < N_LIVES_INIT; i++) {
			lives.add(new Life(i));
		}
	}

	@Override
	public void update(Input input, int delta, ArrayList<Sprite> sprites) {
		// calculate the change in position when a key is pressed
		int dx = 0, dy = 0;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
		}

		// make sure the player stays on screen
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 > App.SCREEN_WIDTH) {
			dx = 0;
			pushSpeedX = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}

		// BoundingBox object which represents the player after moving
		BoundingBox tempBounds = new BoundingBox(getX() + dx, getY() + dy, getWidth(), getHeight());
		for (Sprite sprite : sprites) {
			// prevents movement if it results in intersecting a solid object
			if (sprite.hasTag(SOLID) && sprite.collides(tempBounds)) {
				dx = 0;
				dy = 0;
			}
			// update pushing factor if sprite is in contact with a pushing sprite
			if (collides(sprite) && sprite.hasTag(PUSHING)) {
				pushSpeedX = ((MovingSprite) sprite).getSpeed() * (((MovingSprite) sprite).getDir() ? 1 : -1);
			}

		}

		// perform movement based on key-presses and riding/pushing effects
		move(dx, dy);
		move(pushSpeedX * delta, 0);

		// re-initialise attributes to be checked for in the next update
		pushSpeedX = 0;
		isRiding = false;

		// if pushed off screen by pushing sprite, player loses a life
		if (getX() - getWidth() / 2 < FUZZ || getX() + getWidth() / 2 > App.SCREEN_WIDTH + FUZZ) {
			deathFlag = true;
		}

		// if player lost a life, player dies
		if (deathFlag) {
			playerDeath();
		}

		// update the state of the lives
		for (Life life : lives) {
			life.update(input, delta);
		}
	}

	@Override
	public void render() {
		super.render();
		// renders Life objects in ArrayList
		for (Life life : lives) {
			life.render();
		}
	}

	@Override
	public void onCollision(Sprite other) {
		/*
		 * if in contact with a rideable object, update movement, and ensures player is
		 * safe from hazards
		 */
		if (other instanceof RideableSprite && ((RideableSprite) other).isFloating()) {
			pushSpeedX = ((MovingSprite) other).getSpeed() * (((MovingSprite) other).getDir() ? 1 : -1);
			isRiding = true;
			deathFlag = false;
		}

		/*
		 * if in contact with a hazard object, and is not riding a rideable object,
		 * player loses a life
		 */
		if (other.hasTag(Sprite.HAZARD)) {
			if (!isRiding) {
				deathFlag = true;
			}
		}
	}

	/**
	 * Player loses a life, and returns to initial position. If no more remaining
	 * lives, the game exits.
	 */
	public void playerDeath() {
		// exit if the no more lives remaining
		if (lives.size() == 0) {
			System.exit(0);
		}
		// re-initialise the position of the player
		this.setX(X_INIT);
		this.setY(Y_INIT);
		// remove a life and the player is alive again
		lives.remove(lives.size() - 1);
		deathFlag = false;
	}

	/** Player gains a life. */
	public void extraLife() {
		lives.add(new Life(lives.size()));
	}

}
