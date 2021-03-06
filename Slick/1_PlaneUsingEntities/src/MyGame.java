import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MyGame extends BasicGame {

	private final String BACKGROUND = "img/land.jpg";

	private Image land;
	private Plane plane;

	public MyGame() {
		super("Mon Super Jeu");
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		land = new Image(BACKGROUND);
		plane = new Plane();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		// Rotate left
		if (input.isKeyDown(Input.KEY_Q)) {
			plane.rotate(-delta);
		}

		// Rotate right
		if (input.isKeyDown(Input.KEY_D)) {
			plane.rotate(delta);
		}

		// Move forward
		if (input.isKeyDown(Input.KEY_Z)) {
			plane.move(delta);
		}
		
		// Move backward
		if (input.isKeyDown(Input.KEY_S)) {
			plane.move(-delta);
		}

		// Reduce
		if (input.isKeyDown(Input.KEY_1)) {
			plane.reduce();
		}
		
		// Enlarge
		if (input.isKeyDown(Input.KEY_2)) {
			plane.enlarge();
		}
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		land.draw(0, 0);
		plane.draw();
	}

}