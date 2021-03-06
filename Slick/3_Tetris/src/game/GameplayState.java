package game;

import java.awt.Font;
import java.util.ArrayList;

import main.Tetris;
import menu.Highscores;

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
import game.Piece.Tuple;

public class GameplayState extends BasicGameState {

	private int stateID;

	TrueTypeFont ttf;
	Sound blockFX;
	
	private Entity gameHUD;
	private Entity transparentGameHUD;

	ArrayList<Entity> blockImages;

	private PieceFactory pieceFactory;

	private Tuple cursorPos;
	private Piece currentPiece = null;
	private Piece nextPiece = null;
	
	private Pit pit;

	private int score;

	private int deltaCounter = 500;
	private int inputDelta = 0;

	private static int PIT_X = 52;
	private static int PIT_Y = 18;

	private int blockSize = 0;

	private enum States {
		START, NEW_PIECE, MOVING_PIECE, LINE_DESTRUCTION, PAUSE, HIGHSCORE, GAME_OVER
	}

	private States currentState;
	public GameplayState(int stateID) {

		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		gameHUD = new Entity("img/hudblockgame.jpg");

		transparentGameHUD = new Entity("img/textures.png", 48, 15);
		transparentGameHUD.sub(29, 0, 287, 573);

		blockImages = new ArrayList<Entity>();

		for (int i = 0; i < 4; i++) {
			blockImages.add(new Entity("img/textures.png"));
			blockImages.get(i).sub(0, i * 28, 28, 28);
		}

		pieceFactory = new PieceFactory();

		pit = new Pit(10, 20);

		blockSize = 28;
		
		blockFX = new Sound("snd/over.wav");
		
		Font font = new Font("Verdana", Font.BOLD, 40);
        ttf = new TrueTypeFont(font, true);
        
        

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gfx)
			throws SlickException {
		gameHUD.draw();

		// draw pit
		for (int lineIdx = 0; lineIdx < pit.getNumberOfLines(); lineIdx++) {
			for (int columnIdx = 0; columnIdx < pit.getNumberOfColumns(); columnIdx++) {
				int blockType = pit.getBlockAt(columnIdx, lineIdx);

				if (blockType != -1) {
					blockImages.get(blockType).setX(columnIdx * blockSize + PIT_X);
					blockImages.get(blockType).setY(PIT_Y + (blockSize * (pit.getNumberOfLines() - lineIdx - 1)));
					blockImages.get(blockType).draw();
				}
			}
		}

		// draw currentPiece
		if (currentPiece != null) {
			for (int i = 0; i < 4; i++) {
				Tuple blockPos = currentPiece.getPosOfBlock(i);
				blockImages.get(i).setX(PIT_X + (blockPos.x + cursorPos.x) * blockSize);
				blockImages.get(i).setY(PIT_Y + (pit.getNumberOfLines() - 1 - (blockPos.y + cursorPos.y)) * blockSize);
				blockImages.get(i).draw();
			}
		}

		// draw nextPiece
		if (nextPiece != null) {
			for (int i = 0; i < 4; i++) {
				Tuple blockPos = nextPiece.getPosOfBlock(i);
				blockImages.get(i).setX(PIT_X + 336 + (blockPos.x) * blockSize);
				blockImages.get(i).setY(PIT_Y + 56 + ((blockPos.y)) * blockSize);
				blockImages.get(i).draw();
			}
		}

		ttf.drawString(600, 80, String.valueOf(score), Color.orange);

		transparentGameHUD.draw();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		switch (currentState) {
		case START:
			currentState = States.NEW_PIECE;
			deltaCounter = 500;
			break;
		case NEW_PIECE:
			generateNewPiece();
			break;
		case MOVING_PIECE:
			updatePiece(gc, sbg, delta);
			break;
		case LINE_DESTRUCTION:
			checkForFullLines(gc, sbg, delta);
			currentState = States.NEW_PIECE;
			break;
		case HIGHSCORE:
			break;
		case PAUSE:
			break;
		case GAME_OVER:

			Highscores.getInstance().addScore(score);

			sbg.enterState(Tetris.MAINMENUSTATE);
			break;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		super.enter(gc, sb);

		pit.makeCleanPit();
		currentState = States.START;

		score = 0;
	}

	/* *************** *
	 * Private Methods * ***************
	 */

	private void checkForFullLines(GameContainer gc, StateBasedGame sb,
			int delta) {
		int linesDestroyed = 0;

		for (int lineIdx = 0; lineIdx < pit.getNumberOfLines();) {
			if (pit.isLineFull(lineIdx)) {
				pit.eraseLine(lineIdx);
				linesDestroyed++;
			} else
				lineIdx++;
		}

		switch (linesDestroyed) {
		case 0:
			score += 10;
			break;
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 600;
			break;
		case 4:
			score += 1000;
			break;

		}
	}

	private void generateNewPiece() {
		if (currentPiece == null)
			nextPiece = pieceFactory.generateRandomPiece();

		currentPiece = nextPiece;

		cursorPos = new Tuple(5, 19);

		if (pit.isPieceInsertableIn(currentPiece, cursorPos)) {
			nextPiece = pieceFactory.generateRandomPiece();
			currentState = States.MOVING_PIECE;
		} else {
			currentState = States.GAME_OVER;
		}

	}

	private void updatePiece(GameContainer gc, StateBasedGame sb, int delta) {
		Tuple newCursorPos = new Tuple(cursorPos.x, cursorPos.y);

		// is it falling down time?
		deltaCounter -= delta;
		inputDelta -= delta;
		if (deltaCounter < 0) {
			newCursorPos.y -= 1;
			if (!pit.isPieceInsertableIn(currentPiece, newCursorPos)) {
				pit.insertPieceAt(currentPiece, (int) cursorPos.x,
						(int) cursorPos.y);
				blockFX.play();
				currentState = States.LINE_DESTRUCTION;
				return;
			}

			deltaCounter = 500;
		}
		// verificar o input
		Input input = gc.getInput();
		if (inputDelta < 0) {
			if (input.isKeyDown(Input.KEY_LEFT)) {
				newCursorPos.x -= 1;

				if (!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					newCursorPos.x += 1;
				else
					inputDelta = 100;
			}
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				newCursorPos.x += 1;
				if (!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					newCursorPos.x -= 1;
				else
					inputDelta = 100;
			}
			if (input.isKeyDown(Input.KEY_UP)) {
				currentPiece.rotateRight();
				if (!pit.isPieceInsertableIn(currentPiece, newCursorPos))
					currentPiece.rotateLeft();
				else
					inputDelta = 150;
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				newCursorPos.y -= 1;
				if (!pit.isPieceInsertableIn(currentPiece, newCursorPos)) {
					newCursorPos.y += 1;
				} else
					inputDelta = 10;
			}
		}

		cursorPos = new Tuple(newCursorPos.x, newCursorPos.y);
	}

}