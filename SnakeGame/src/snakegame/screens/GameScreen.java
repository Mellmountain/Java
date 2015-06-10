package snakegame.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.ArrayList;


import javax.swing.JPanel;
import pathfinding.core.*;
import snakegame.core.*;
import snakegame.core.BoardEntity.BoardEntityType;

public class GameScreen extends JPanel {

	ArrayList<BoardEntity> _elementsToPaint; 
	ArrayList<KeyListener> _keyListeners;
	
	private int _xBoxes;
	private int _yBoxes;
	
	
	public GameScreen()
	{
		super();
		setBackground(Color.WHITE);
		_elementsToPaint = new ArrayList<BoardEntity>();
		_keyListeners = new ArrayList<KeyListener>();
		setFocusable(true);		
	}
	
	public void paintComponent(Graphics g)
	{		
		int width = getWidth();
		int height = getHeight();
		super.paintComponent(g);
	
		int _xBoxes = (int)(width - GameEngine.TILE_SIZE) / GameEngine.TILE_SIZE;
		int _yBoxes = (int)(height - GameEngine.TILE_SIZE) / GameEngine.TILE_SIZE;
		
		int xPadding = (int)((width - (_xBoxes * GameEngine.TILE_SIZE)) / 2);
		int yPadding = (int)((height - (_yBoxes * GameEngine.TILE_SIZE)) / 2);
		
		for(int x = xPadding; x < (_xBoxes * GameEngine.TILE_SIZE); x+=GameEngine.TILE_SIZE)
			for(int y = yPadding; y <= (_yBoxes * GameEngine.TILE_SIZE); y+=GameEngine.TILE_SIZE)
			{
				g.setColor(Color.BLACK);
				g.drawRect(x, y, GameEngine.TILE_SIZE, GameEngine.TILE_SIZE);
			}
				
		for(BoardEntity boardEntity : _elementsToPaint)
		{
			g.setColor(boardEntity.getEntityColor());
			for(Position pos : boardEntity.getPosition())
			{
				if(boardEntity instanceof Snake)
					if(!((Snake)boardEntity).isAlive())
						continue;
					
				g.fillRect((pos.x * GameEngine.TILE_SIZE) + xPadding, 
						(pos.y * GameEngine.TILE_SIZE) + yPadding, 
						GameEngine.TILE_SIZE, GameEngine.TILE_SIZE);
			}
		}
	}
	
	public void update(ArrayList<BoardEntity> boardState) 
	{
		_elementsToPaint = boardState;
		update(getGraphics());
	}
	
	public int getXTiles()
	{
		return _xBoxes;
	}
	
	private static final long serialVersionUID = 1L;


	public int getYTiles() {
		return _yBoxes;
	}
	

}
