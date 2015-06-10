package snakegame.core;

import java.awt.Color;
import java.util.ArrayList;

import pathfinding.core.Position;

public abstract class BoardEntity 
{
	
	public enum BoardEntityType
	{
		PLAYER, FOOD
	}
	
	public BoardEntity()
	{	
	}

	public abstract ArrayList<Position> getPosition();
	public abstract BoardEntityType getEntityType();
	public abstract Color getEntityColor();
}
