/**
 * Class for background tiles, which are stationary.
 * Handles creating grass tiles, water tiles (a hazard) and tree tiles (a solid object).
 */
public class Tile extends Sprite {
	private static final String GRASS_PATH = "assets/grass.png";
	private static final String WATER_PATH = "assets/water.png";
	private static final String TREE_PATH = "assets/tree.png";
	
	
	/** Create a new grass tile, at the given location.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @return A new grass tile.
	 */
	public static Tile createGrassTile(float x, float y) {
		return new Tile(GRASS_PATH, x, y);
	}
	/** Create a new water tile, at the given location.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @return A new grass tile.
	 */
	public static Tile createWaterTile(float x, float y) {
		return new Tile(WATER_PATH, x, y, new String[] { Sprite.HAZARD });
	}
	/** Create a new tree tile, at the given location.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @return A new grass tile.
	 */
	public static Tile createTreeTile(float x, float y) {
		return new Tile(TREE_PATH, x, y, new String[] { Sprite.SOLID });
	}
	
	/** Create a tile based on image file, and its location.
	 * @param imageSrc The location of image file to load.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 */
	private Tile(String imageSrc, float x, float y) {		
		super(imageSrc, x, y);
	}
	
	/** Create a tile based on image file, location, and tags.
	 * @param imageSrc The location of image file to load.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @param tags The tags of the tile.
	 */
	private Tile(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}
}