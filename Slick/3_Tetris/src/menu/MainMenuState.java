package menu;

import java.awt.Font;
import java.util.ArrayList;

import main.Tetris;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entity.Entity;
import entity.MovingEntity;

public class MainMenuState extends BasicGameState {

	private int stateID;

	private Entity background;
	private MovingEntity startGameOption;
	private MovingEntity exitGameOption;

	private static int menuX = 410;
	private static int menuY = 160;

	private float startGameScale;
	private float exitGameScale;

	private Sound enter;

	private Highscores highscores;
	private TrueTypeFont ttf;

	public MainMenuState(int stateID) {
		this.stateID = stateID;

		startGameScale = 1;
		exitGameScale = 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		Font font = new Font("Verdana", Font.BOLD, 20);
		ttf = new TrueTypeFont(font, true);

		highscores = Highscores.getInstance();

		enter = new Sound("snd/enter.wav");

		background = new Entity("img/menu.jpg");

		// Image menuOptions = new Image("img/menuoptions.png");

		startGameOption = new MovingEntity("img/menuoptions.png", menuX, menuY,
				startGameScale, 1.0f, 1.05f, 0.0001f);
		startGameOption.sub(0, 0, 377, 71);

		exitGameOption = new MovingEntity("img/menuoptions.png", menuX,
				menuY + 80, exitGameScale, 1.0f, 1.05f, 0.0001f);
		exitGameOption.sub(0, 71, 377, 71);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx)
			throws SlickException {
		background.draw();
		startGameOption.draw();
		exitGameOption.draw();

		// Draw Highscores
		int index = 1;
		int posY = 300;

		ArrayList<Integer> highScoreList = highscores.getScores();

		for (Integer score : highScoreList) {
			ttf.drawString(20, posY, " "
					+ (index < highScoreList.size() ? "0" + index : "" + index)
					+ "  ." + score, Color.orange);
			index++;
			posY += 20;
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		if (startGameOption.isInside(mouseX, mouseY)) {
			startGameOption.enlarge(delta);

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				enter.play();
				sbg.enterState(Tetris.GAMEPLAYSTATE);
			}
		} else {
			startGameOption.reduce(delta);
		}

		if (exitGameOption.isInside(mouseX, mouseY)) {
			exitGameOption.enlarge(delta);

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				gc.exit();
		} else {
			exitGameOption.reduce(delta);
		}

	}

	@Override
	public int getID() {
		return stateID;
	}

}
