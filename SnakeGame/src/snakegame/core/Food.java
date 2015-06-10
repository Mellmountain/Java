package snakegame.core;

import java.awt.Color;
import java.util.ArrayList;

import pathfinding.core.Position;

public class Food extends BoardEntity
{
	private Position _position;
	private int _growFactor;
	
	public Food(Position pos, int growFactor)
	{
		_position = pos;
		_growFactor = growFactor;
	}
	
	@Override
	public ArrayList<Position> getPosition() 
	{
		// This is not nice... we want to have BoardEntity, but we don't want to return a list when we
		// only always have one Position
		ArrayList<Position> positions = new ArrayList<Position>();
		positions.add(_position);
		return positions;
	}

	public int getGrowFactor() 
	{
		return _growFactor;
	}

	@Override
	public BoardEntityType getEntityType() {
		return BoardEntityType.FOOD;
	}

	@Override
	public Color getEntityColor() {
		return Color.YELLOW;
	}
}
