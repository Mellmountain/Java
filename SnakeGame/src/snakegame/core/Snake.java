package snakegame.core;

import java.awt.Color;
import java.util.ArrayList;

import pathfinding.core.*;

public class Snake extends BoardEntity 
{

	private Direction _direction;
	private ArrayList<Position> _body;
	private Color _color;
	private int _score;
	private int _growFactor = 0;
	private boolean _isAlive;
	private String _name;
	
	public enum Direction
	{
		UP, DOWN, LEFT , RIGHT
	}
	
	public Snake(Position startPos, Direction direction, Color color, String name)
	{
		_direction = direction;
		_color = color;
		_body = new ArrayList<Position>();
		_body.add(0, startPos);		
		_growFactor = 2;
		_isAlive = true;
		_name = name;
	}
	
	public void update()
	{
		if(_growFactor == 0)
			_body.remove(_body.size() - 1);
		else
			_growFactor--;
		
		_body.add(0, getNewPosition(_body.get(0), _direction));
		System.out.println(printSnakeBody());
	}
	
	

	public void grow(Food food)
	{
		if(food != null)
			{
				_growFactor = food.getGrowFactor();
				_score = food.getGrowFactor();
			}
	}
	
	private Position getNewPosition(Position currentPos, Direction direction)
	{
		Position newPosition;
		switch (direction)
		{
			case UP:			
				newPosition = new Position(currentPos.x, currentPos.y - 1);
				break;
			case DOWN:
				newPosition = new Position(currentPos.x, currentPos.y + 1);
				break;
			case LEFT:
				newPosition = new Position(currentPos.x - 1, currentPos.y);
				break;
			case RIGHT:
				newPosition = new Position(currentPos.x + 1, currentPos.y);
				break;
			default:
				// will never go here ;)
				newPosition = new Position(0, 0);
		}
		return newPosition;
	}

	public ArrayList<Position> getBody() {
		return _body;
	}

	public void setDirection(Direction direction) {
		// Make sure we cant make an illegal move maybe Board should do this?
		if(_direction == Direction.UP && direction == Direction.DOWN)
			return;
		if(_direction == Direction.DOWN && direction == Direction.UP)
			return;
		if(_direction == Direction.LEFT && direction == Direction.RIGHT)
			return;
		if(_direction == Direction.RIGHT && direction == Direction.LEFT)
			return;
		_direction = direction;
	}

	public int getScore() {
		return _score;
	}

	public void setScore(int _score) {
		this._score = _score;
	}

	public boolean isAlive() {
		return _isAlive;
	}

	public void setAlive(boolean isAlive) {
		this._isAlive = isAlive;
	}

	
	@Override
	public BoardEntityType getEntityType() {
		return BoardEntityType.PLAYER;
	}

	@Override
	public ArrayList<Position> getPosition() {
		return _body;
	}

	@Override
	public Color getEntityColor() {		
		return _color;
	}
	
	public String getName()
	{
		return _name;
	}
	
	private char[] printSnakeBody() {
		String strBody = "[H]";
		for(Position pos : _body)
		{
			strBody += "[" + pos.x + "," + pos.y + "]";
		}
		strBody += "[T]-" + _name;
		return strBody.toCharArray();
	}

	public boolean growing() {
		return _growFactor > 0;
	}

	public ArrayList<Position> getBodyWithoutHead() {
		ArrayList<Position> bodyNoHead = (ArrayList<Position>) _body.clone();;
		bodyNoHead.remove(0);
		return bodyNoHead;
	}
	
	
}
