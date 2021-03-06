package menu;
import java.awt.Font;

import main.MyGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entity.Entity;
import entity.MovingEntity;
 
public class MainMenuState extends BasicGameState {
 
	private int stateID;
	
	private float startGameScale;
	private float exitGameScale;
	
	private Entity background;
	private MovingEntity startGameOption;
	private MovingEntity exitGameOption;
	private TrueTypeFont ttf;
	private Sound enter;
	private Music musique;
	
    public MainMenuState(int stateID) {
    	this.stateID = stateID;
    
    	// Pour augmenter/diminuer la taille des menus
    	startGameScale = 1;
		exitGameScale = 1;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		// Background
		background = new Entity("../Slick/img/background.jpg");
		
		// Menus
		int menuX = 410;
		int menuY = 160;
		
		startGameOption = new MovingEntity("../Slick/img/menuoptions.png", menuX, menuY,
				startGameScale, 1.0f, 1.05f, 0.0001f);
		startGameOption.sub(0, 0, 377, 71); // Recupere seulement une partie de l'image (crop)

		exitGameOption = new MovingEntity("../Slick/img/menuoptions.png", menuX,
				menuY + 80, exitGameScale, 1.0f, 1.05f, 0.0001f);
		exitGameOption.sub(0, 71, 377, 71);
		
		// Font
		Font font = new Font("Verdana", Font.BOLD, 40);
		ttf = new TrueTypeFont(font, true);
		
		// Sound
		enter = new Sound("../Slick/snd/enter.wav");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx)
			throws SlickException {
		
		background.draw();
		startGameOption.draw();
		exitGameOption.draw();
		
		// Affichage d'une chaine de caracteres
		ttf.drawString(350, 20, "Menu", Color.orange);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		// Menu Jouer
		
		if (startGameOption.isInside(mouseX, mouseY)) { // Au survol du curseur
			startGameOption.enlarge(delta); // Augmente la taille du menu

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				enter.play(); // Joue un son
				sbg.enterState(MyGame.GAMEPLAYSTATE); // Lance le jeu
			}
		} else {
			startGameOption.reduce(delta); // Souris en dehors -> retour a la taille normale
		}

		// Menu Quitter
		if (exitGameOption.isInside(mouseX, mouseY)) {
			exitGameOption.enlarge(delta);

			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				gc.exit(); // Quitte le jeu
		} else {
			exitGameOption.reduce(delta);
		}
		
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	// Appelee lors de l'entree dans l'etat
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		super.enter(gc, sbg);

		musique = new Music("../Slick/snd/hope.ogg"); // A revoir
		musique.play();
	}
	
	// Appelee lors de la sortie de l'etat
	@Override
	public void leave(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		super.leave(gc, sb);

		musique.stop();
	}

}