
import utilities.BoundingBox;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Basic class of which all game objects are based on. Handles sprite update,
 * rendering, movement, and collision mechanics.
 * 
 * based on Sample Project by Eleanor McMurtry, University of Melbourne
 * modified by Alan Guo, University of Melbourne
 */
public abstract class Sprite {

	/** tag for hazard object, which causes death upon contact */
	public final static String HAZARD = "hazard";
	/** tag for rideable object, which Player objects can ride on */
	public final static String RIDEABLE = "rideable";
	/** tag for pushing object, which pushes Player objects */
	public final static String PUSHING = "pushing";
	/** tag for solid object, which Player objects cannot move into */
	public final static String SOLID = "solid";

	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;

	private String[] tags;

	/**
	 * Create a sprite based on image file, and its location.
	 * 
	 * @param imageSrc The location of image file to load.
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 */
	public Sprite(String imageSrc, float x, float y) {
		setupSprite(imageSrc, x, y);
	}

	/**
	 * Create a sprite based on image file, location, and tags.
	 * 
	 * @param imageSrc The location of image file to load.
	 * @param x        The x-coordinate of the sprite.
	 * @param y        The y-coordinate of the sprite.
	 * @param tags     The tags of the sprite.
	 */
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
	}

	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;

		bounds = new BoundingBox(image, (int) x, (int) y);

		tags = new String[0];
	}

	/**
	 * Sets the x position of the sprite.
	 * 
	 * @param x the target x position
	 */
	public final void setX(float x) {
		this.x = x;
		bounds.setX((int) x);
	}

	/**
	 * Sets the y position of the sprite.
	 * 
	 * @param y the target y position
	 */
	public final void setY(float y) {
		this.y = y;
		bounds.setY((int) y);
	}

	/**
	 * Accesses the x position of the sprite.
	 * 
	 * @return the x position of the sprite
	 */
	public final float getX() {
		return x;
	}

	/**
	 * Accesses the y position of the sprite.
	 * 
	 * @return the y position of the sprite
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Accesses the image width of the sprite.
	 * 
	 * @return the image width of the sprite
	 */
	public final float getWidth() {
		return image.getWidth();
	}

	/**
	 * Accesses the image height of the sprite.
	 * 
	 * @return the image height of the sprite
	 */
	public final float getHeight() {
		return image.getHeight();
	}

	/**
	 * Moves the sprite by the change in pixels in x and y direction.
	 * 
	 * @param dx Change in pixels in the x direction.
	 * @param dy Change in pixels in the y direction.
	 */
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}

	/**
	 * Check if a tile of a given location is on-screen.
	 * 
	 * @param x The x-coordinate of the given location.
	 * @param y The y-coordinate of the given location.
	 * @return True if tile is on-screen.
	 */
	public final boolean onScreen(float x, float y) {
		return !(x + World.TILE_SIZE / 2 > App.SCREEN_WIDTH || x - World.TILE_SIZE / 2 < 0
				|| y + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT || y - World.TILE_SIZE / 2 < 0);
	}

	/**
	 * Check if the sprite is on-screen.
	 * 
	 * @return True if sprite is on-screen.
	 */
	public final boolean onScreen() {
		return onScreen(getX(), getY());
	}

	/**
	 * Check if sprite collides with another sprite.
	 * 
	 * @param other The other Sprite object to check for collision.
	 * @return True if the sprite collides with the other sprite.
	 */
	public final boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}

	/**
	 * Check if sprite collides with a BoundingBox object.
	 * 
	 * @param other The BoundingBox object to check for collision.
	 * @return True if the sprite collides with the BoundingBox.
	 */
	public final boolean collides(BoundingBox other) {
		return bounds.intersects(other);
	}

	/**
	 * Update the sprite since the last frame.
	 * 
	 * @param input The Input object responsible for key inputs.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
	}

	/**
	 * Update the sprite since the last frame.
	 * 
	 * @param input   The Input object responsible for key inputs.
	 * @param delta   Time passed since last frame (milliseconds).
	 * @param sprites An ArrayList of sprites to be interacted with.
	 */
	public void update(Input input, int delta, ArrayList<Sprite> sprites) {
	}

	/**
	 * Called when sprite collides with another given sprite.
	 * 
	 * @param other The other sprite which collided with this sprite.
	 */
	public void onCollision(Sprite other) {
	}

	/** Renders the image of the sprite at its central location. */
	public void render() {
		image.drawCentered(x, y);
	}

	/**
	 * Check if sprite has a given tag.
	 * 
	 * @param tag The string to check for.
	 * @return True if sprite has the tag.
	 */
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
}
