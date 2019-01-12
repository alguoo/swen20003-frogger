import org.newdawn.slick.Input;
import java.lang.Math;

/**
 * Class for extra life object. Player will gain a life upon contact.
 * 
 * by Alan Guo, University of Melbourne
 */
public class ExtraLife extends Sprite {
	private static final String ASSET_PATH = "assets/extralife.png";
	public static final int TIME_LOOP = 14*World.SEC_TO_MILLISEC;
	private static final int TIME_BETWEEN_MOVEMENT = 2*World.SEC_TO_MILLISEC;

	private int timer = 0;
	private boolean dirRight = true;
	private float xDistFromLog = 0;
	private LogType logChoice;

	/**
	 * Creates an ExtraLife object, based on the log it rides on.
	 * 
	 * @param log
	 */
	public ExtraLife(LogType log) {
		super(ASSET_PATH, log.getX(), log.getY());
		logChoice = log;
	}
	
	@Override
	public void update(Input input, int delta) {
		// moves when sufficient time has passed
		if (timer % TIME_BETWEEN_MOVEMENT > (timer += delta) % TIME_BETWEEN_MOVEMENT) {
			int dx = (dirRight ? 1 : -1) * World.TILE_SIZE;
			// moves opposite way if otherwise extra life will fall off log
			if ((Math.abs(xDistFromLog + dx)) > logChoice.getWidth() / 2) {
				xDistFromLog -= dx;
				dirRight = !dirRight;
			} else {
				xDistFromLog += dx;
			}
		}
		// move extra life object based on relative distance from its log
		setX(logChoice.getX() + xDistFromLog);
	}
	
	/** Check if it is time for extra life to disappear.
	 * @return True if it is time to disappear.
	 */
	public boolean toDisappear() {
		return (timer > TIME_LOOP);
	}
	
}
