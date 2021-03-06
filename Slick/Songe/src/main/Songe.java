package main;

import game.DialogState;
import game.GameplayState;
import game.QuestionState;
import menu.ChoiceMiniGameState;
import menu.ChoicePersoState;
import menu.InitialMenuState;
import menu.LoadingState;
import menu.MainMenuState;
import menu.SaveHighScore;
import minigame.HoverCave;
import utils.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



public class Songe extends StateBasedGame {

	// Attributes
	private GameContainer container;
	private boolean vsync = true;
	
	// States
	public static final int LOADINGSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int CHOICEPERSOSTATE = 2;
	public static final int GAMEPLAYSTATE = 3;
	public static final int HOVERCAVESTATE = 4;
	public static final int SAVEHIGHSCORE = 5;
	public static final int QUESTIONSTATE = 6;
	public static final int CHOICEMINIGAMESTATE = 7;
	public static final int INITIALMENUSTATE = 8;
	public static final int DIALOGSTATE = 9;
	
	public Songe() {
		super("Songe");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.container = gc;
		Globals.gcHeight = gc.getHeight();
		Globals.gcWidth = gc.getWidth();
		
		/*
		try {
			container.setFullscreen(true);
		} catch (SlickException e) {
			System.err.println("Erreur lors de la mise en plein écran.");
		}
		*/
		
		// True if vertical sync is turned on
		container.setTargetFrameRate(100);
		container.setVSync(vsync);
		//To render even if the window is not focused
		container.setAlwaysRender(true);
		//container.setVerbose(false);
		
		this.addState(new LoadingState(LOADINGSTATE));
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new ChoicePersoState(CHOICEPERSOSTATE));
		this.addState(new ChoiceMiniGameState(CHOICEMINIGAMESTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.addState(new HoverCave(HOVERCAVESTATE));
		this.addState(new SaveHighScore(SAVEHIGHSCORE));
		this.addState(new QuestionState(QUESTIONSTATE));
		this.addState(new InitialMenuState(INITIALMENUSTATE));
		this.addState(new DialogState(DIALOGSTATE));
		
		this.enterState(LOADINGSTATE);
	}
	

	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		
		if (key == Input.KEY_F7) {
			vsync = !vsync;
			container.setVSync(vsync);
		}
	}
}
