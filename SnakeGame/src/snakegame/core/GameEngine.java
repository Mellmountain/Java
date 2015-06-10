package snakegame.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import snakegame.core.Board.BoardPlayState;
import snakegame.core.Snake.Direction;
import snakegame.screens.*;
import pathfinding.core.*;


public class GameEngine implements KeyListener{

	public static int SCREEN_WIDTH = 500;
	public static int SCREEN_HEIGHT = 500;
	public static int TILE_SIZE = 10;
	
	public enum GameEngineState
	{
		NOT_STARTED, 
		RUNNING,
		STOPPED
	}
	
	Board _board;
	GameEngineState _gameEngineState;
	ScreenHandler _screenHandler = new ScreenHandler(SCREEN_WIDTH, SCREEN_HEIGHT);
	GameScreen _gameScreen = new GameScreen();
	StartScreen _startScreen = new StartScreen();
	GameOverScreen _gameOverScreen = new GameOverScreen();
	
	public GameEngine()
	{
		_gameScreen.addKeyListener(this);
		
		_screenHandler.addScreen(_gameScreen, "GameScreen");
		_screenHandler.addScreen(_startScreen, "StartScreen");
		_screenHandler.addScreen(_gameOverScreen, "GameOverScreen");
	
		_screenHandler.showScreen("GameScreen");	
		_screenHandler.showWindow();	
	}
	
	private int calculateYTiles() {
		return (int)(SCREEN_HEIGHT - GameEngine.TILE_SIZE) / GameEngine.TILE_SIZE;
	}

	private int calculateXTiles() {
		return (int)(SCREEN_WIDTH - GameEngine.TILE_SIZE) / GameEngine.TILE_SIZE;
	}

	public void Start()
	{
		System.out.println("GameEngine started...");
		_board = new Board(48, 46, 2);		
		_gameEngineState = GameEngineState.RUNNING;
		while(true)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(_board.getBoardPlayState() == BoardPlayState.PLAYING)
			{
				_board.update(); // Update player positions
				_gameScreen.update(_board.getBoardState());
				_gameScreen.repaint();
			}
			else
			{
				_screenHandler.showScreen("GameOverScreen");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		//This is debug code
		Snake player1 = _board.getPlayers().get(0);
		Direction direction = Direction.UP;
		if(e.getKeyChar() == 'w' || e.getKeyCode() == 'W')
			direction = Direction.UP;
		if(e.getKeyChar() == 'a' || e.getKeyCode() == 'A')
			direction = Direction.LEFT;
		if(e.getKeyChar() == 's' || e.getKeyCode() == 'S')
			direction = Direction.DOWN;
		if(e.getKeyChar() == 'd' || e.getKeyCode() == 'D')
			direction = Direction.RIGHT;
		player1.setDirection(direction);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
