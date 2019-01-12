import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import utilities.BoundingBox;
import java.util.Random;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Initialises, updates and renders the sprites of Shadow Leap from level files.
 * Unites their behaviours and interactions, and also includes game exit cues.
 * 
 * based on Sample Project by Eleanor McMurtry, University of Melbourne
 * modified by Alan Guo, University of Melbourne
 *
 */
public class World {

	/** tile width and height, in pixels */
	public static final int TILE_SIZE = 48;

	private static final int N_LEVELS = 2;
	private static final String[] LEVEL_PATHS = new String[] { "assets/levels/0.lvl", "assets/levels/1.lvl" };
	private static int currentLevel = 0;

	// index format of the .lvl files to load sprites from
	private static final int TYPE_INDEX = 0;
	private static final int X_INDEX = 1;
	private static final int Y_INDEX = 2;
	private static final int DIR_RIGHT_INDEX = 3;

	// tile tags to be used in levelBuilder() method.
	private static final String WATER_TILE = "water";
	private static final String GRASS_TILE = "grass";
	private static final String TREE_TILE = "tree";
	private static final String BUS_TILE = "bus";
	private static final String BULLDOZER_TILE = "bulldozer";
	private static final String RACECAR_TILE = "racecar";
	private static final String BIKE_TILE = "bike";
	private static final String TURTLE_TILE = "turtle";
	private static final String LOG_TILE = "log";
	private static final String LONG_LOG_TILE = "longLog";

	// sprites contains all sprites except logs and longlogs, player, and extra life.
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private ArrayList<LogType> logs = new ArrayList<>();
	private Player frog;

	/** Conversion from seconds to milliseconds. */
	public static final int SEC_TO_MILLISEC = 1000;
	private static final Integer[] SPAWN_INTERVAL = new Integer[] { 25, 35 };
	// timer for spawning ExtraLife object.
	private int timer = 0;
	private int extraLifeSpawnTime;
	private ExtraLife extraLife = null;

	private static final Integer[] HOLE_LOC_X = new Integer[] { (96 + 144) / 2, (288 + 336) / 2, (480 + 528) / 2,
																(672 + 720) / 2, (864 + 912) / 2 };
	private static final int HOLE_LOC_Y = 48;
	private static final int HOLE_WIDTH = 2 * TILE_SIZE;
	private static final int HOLE_HEIGHT = TILE_SIZE;
	private static final int N_HOLES = HOLE_LOC_X.length;
	// Array to check if a hole has been filled
	private static Boolean[] holesFilled = new Boolean[N_HOLES];
	// Array of BoundingBox objects for the player to reach
	private BoundingBox[] holes = new BoundingBox[N_HOLES];

	/**
	 * Initialises World object by building sprites of the first level, the holes to
	 * be filled, player object, and a spawn time for ExtraLife object.
	 */
	public World() {

		// create tiles based off level file
		levelLoader(LEVEL_PATHS[currentLevel]);

		// create holes to be filled
		holesBuilder();

		// create player
		frog = new Player(Player.X_INIT, Player.Y_INIT);

		// initialise random spawn time for ExtraLife object
		setExtraLifeSpawnTime();

	}

	/**
	 * Update the game state for a frame.
	 * 
	 * @param input The object responsible for all input.
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
		// If ExtraLife object yet to spawn, keep updating the timer
		if (extraLife == null) {
			if (timer < extraLifeSpawnTime) {
				timer += delta;
			} else {
				// If enough time has elapsed, create extra life object.
				Random rand = new Random();
				extraLife = new ExtraLife(logs.get(rand.nextInt(logs.size())));
				// Reset timer, and a new random spawn time.
				timer = 0;
				setExtraLifeSpawnTime();
			}
		} else {

			extraLife.update(input, delta);

			// When player makes contact with ExtraLife object, destroy it and add one to existing lives.
			if (frog.collides(extraLife)) {
				extraLife = null;
				frog.extraLife();
			}

			// When sufficient time runs out, ExtraLife object is destroyed.
			if (extraLife != null && extraLife.toDisappear()) {
				extraLife = null;
			}
		}

		// loop over all sprites to test for intersection with player.
		for (LogType log : logs) {
			if (frog.collides(log)) {
				frog.onCollision(log);
			}
		}

		for (Sprite sprite2 : sprites) {
			if (frog.collides(sprite2)) {
				frog.onCollision(sprite2);
			}
		}

		frog.update(input, delta, sprites);

		// loop over all holes to test for intersection with player.
		for (int i = 0; i < N_HOLES; i++) {
			if (frog.collides(holes[i])) {
				// if a hole was not previously filled, return player to starting position and fill the hole.
				if (!holesFilled[i]) {
					frog.setX(Player.X_INIT);
					frog.setY(Player.Y_INIT);
					sprites.add(new FillHoleFrog(HOLE_LOC_X[i], HOLE_LOC_Y));
					holesFilled[i] = true;
				}
			}
		}

		for (LogType log : logs) {
			log.update(input, delta);
		}

		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
		}
		
		levelUpdate();
	}

	/**
	 * Render all sprites on the screen.
	 * 
	 * @param g The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) {

		for (Sprite sprite : sprites) {
			sprite.render();
		}

		for (LogType log : logs) {
			log.render();
		}
		frog.render();

		if (extraLife != null) {
			extraLife.render();
		}
	}

	/**
	 * Loads the sprites for a level, described in a CSV format file.
	 * 
	 * @param stringName The location of the file to load.
	 */
	public void levelLoader(String stringName) {
		try (BufferedReader br = new BufferedReader(new FileReader(stringName))) {

			String text;
			// builds sprite from each line
			while ((text = br.readLine()) != null) {
				String[] columns = text.split(",");
				spriteBuilder(columns);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a sprite based off a String array.
	 * 
	 * @param item A String array containing a tile tag, location, and direction (if applicable)
	 */
	public void spriteBuilder(String[] item) {
		// parse the strings into the intended format
		int x = Integer.parseInt(item[X_INDEX]);
		int y = Integer.parseInt(item[Y_INDEX]);
		boolean dirRight = true;
		if (item.length > DIR_RIGHT_INDEX) {
			dirRight = Boolean.parseBoolean(item[DIR_RIGHT_INDEX]);
		}

		// build the sprite depending on the tile tag, location, and direction of movement
		switch (item[TYPE_INDEX]) {
		case WATER_TILE:
			sprites.add(Tile.createWaterTile(x, y));
			break;
		case GRASS_TILE:
			sprites.add(Tile.createGrassTile(x, y));
			break;
		case TREE_TILE:
			sprites.add(Tile.createTreeTile(x, y));
			break;
		case BUS_TILE:
			sprites.add(new Bus(x, y, dirRight));
			break;
		case BULLDOZER_TILE:
			sprites.add(new Bulldozer(x, y, dirRight));
			break;
		case BIKE_TILE:
			sprites.add(new Bike(x, y, dirRight));
			break;
		case RACECAR_TILE:
			sprites.add(new Racecar(x, y, dirRight));
			break;
		case TURTLE_TILE:
			sprites.add(new Turtle(x, y, dirRight));
			break;
		case LOG_TILE:
			logs.add(new Log(x, y, dirRight));
			break;
		case LONG_LOG_TILE:
			logs.add(new LongLog(x, y, dirRight));
			break;

		}
	}

	/**
	 * Build BoundingBoxes for the holes to be filled. Initialises Boolean array
	 * checking if a hole has been filled.
	 * 
	 */
	public void holesBuilder() {
		for (int i = 0; i < N_HOLES; i++) {
			holes[i] = new BoundingBox(HOLE_LOC_X[i], HOLE_LOC_Y, HOLE_WIDTH, HOLE_HEIGHT);
			holesFilled[i] = false;
		}
	}

	/**
	 * Generate and set a random spawn time for the ExtraLife object.
	 */
	public void setExtraLifeSpawnTime() {
		Random rand = new Random();
		extraLifeSpawnTime = SEC_TO_MILLISEC * (SPAWN_INTERVAL[0] + rand.nextInt(SPAWN_INTERVAL[1] - SPAWN_INTERVAL[0]));
	}
	
	/**
	 * Check if the level needs updating. If all levels completed, exit the fame.
	 */
	public void levelUpdate() {
		// checks if the level is complete, i.e. all holes are filled.
		boolean levelComplete = true;
		for (Boolean b : holesFilled) {
			levelComplete = levelComplete && b;
		}

		// if level is complete, clear all sprites except the player, and load new level.
		if (levelComplete) {
			extraLife = null;
			sprites.clear();
			logs.clear();

			currentLevel += 1;
			// if levels are all complete, exit the game.
			if (currentLevel >= N_LEVELS) {
				System.exit(0);
			}

			levelLoader(LEVEL_PATHS[currentLevel]);
			holesBuilder();

		}
	}
}