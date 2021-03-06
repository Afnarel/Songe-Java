package main;
import game.GameplayState;
import menu.MainMenuState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 
public class Tetris extends StateBasedGame {

	//enum States {MAINMENUSTATE,GAMEPLAYSTATE};
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	
	public Tetris() {
		super("Tetris-like");
		
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.enterState(MAINMENUSTATE);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MAINMENUSTATE).init(gc, this);
		this.getState(GAMEPLAYSTATE).init(gc, this);
	}

}