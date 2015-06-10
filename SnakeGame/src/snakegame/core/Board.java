package snakegame.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import pathfinding.core.Node;
import pathfinding.core.Position;
import snakegame.core.Board.BoardPlayState;
import snakegame.core.Snake.Direction;

public class Board 
{	
	private int _boardSizeX;
	private int _boardSizeY;
	
	private ArrayList<Food> _food;
	private ArrayList<Snake> _players;
	
	private BoardPlayState _boardPlayState;
	private Random _random;
	
	public enum BoardPlayState
	{
		PLAYING, GAME_OVER
	}
	
	public Board(int sizeX, int sizeY, int numberOfPlayers)
	{
		System.out.println("Created board x:" + sizeX + "  y:" + sizeY);
		_boardSizeX = sizeX;
		_boardSizeY = sizeY;
		
		AISnake player2 = new AISnake(new Position(2, 3), Direction.RIGHT, Color.BLUE, "BlueSnake", this);
		AISnake player1 = new AISnake(new Position(8, 8), Direction.LEFT, Color.GREEN, "GreenSnake", this);
		_players = new ArrayList<Snake>();
		_players.add(player1);
		_players.add(player2);
				
		_food = new ArrayList<Food>();
		
		_boardPlayState = BoardPlayState.PLAYING;
		
		_random = new Random(System.currentTimeMillis());
		generateNewFood();
	}
	
	public void update() 
	{
		if(hasAllPlayersDied())
			_boardPlayState = BoardPlayState.GAME_OVER;
		
		if(_boardPlayState != BoardPlayState.GAME_OVER)
		{
			for(Snake player : _players)
			{
				// Don't update dead players
				if(player.isAlive())
				{
					player.update();
					if(hasCollidedWithWall(player) || hasCollidedWithOpponent(player) || hasCollidedWithSelf(player))
						player.setAlive(false);
					
					if(hasArrivedAtFood(player))
					{
						player.grow(getFoodAt(player.getBody().get(0)));
						generateNewFood();
					}
				}
			}			
		}
	}

	private void generateNewFood() 
	{
		//this is bad if we want to have more than one food on board
		//we should probably check so we dont generate food on top of a player (will be bad for AI)
		if(_food.size() > 0)
			_food.remove(0);
		_food.add(new Food(new Position(_random.nextInt(getBoardSizeX() - 1), _random.nextInt(getBoardSizeY() - 1)), _random.nextInt(4) + 1));
	}

	public ArrayList<Snake> getPlayers()
	{
		return _players;
	}

	public ArrayList<BoardEntity> getBoardState()
	{
		ArrayList<BoardEntity> boardState = new ArrayList<BoardEntity>();
		boardState.addAll(_players);
		boardState.addAll(_food);
		return boardState;
	}
	
	private boolean hasAllPlayersDied() 
	{
		boolean allPlayersAreDead = true;
		for(Snake player : _players)
		{
			if(player.isAlive())
			{
				allPlayersAreDead = false;
				break;
			}
		}
		return allPlayersAreDead;
	}
	
	private boolean hasCollidedWithWall(Snake player)
	{
		Position headPosition = player.getBody().get(0);
		if(headPosition.x < 0 
				|| headPosition.x > getBoardSizeX()
				|| headPosition.y < 0
				|| headPosition.y > getBoardSizeY())
		{
			System.out.println(player.getName() + "has collided with wall");
			return true;
		}
		return false;
	}

	private boolean hasCollidedWithOpponent(Snake player) {
		Position headPosition = player.getBody().get(0);
		for(Snake opponent : _players)
		{
			//bad check!? This will trigger if two snakes collide head on! 
			//We don't want to compare against out own head
			if(headPosition.equals(opponent.getBody().get(0)))
					continue;
			for(Position opponentBodyPosition : opponent.getBody())
			{
				if(headPosition.equals(opponentBodyPosition))
					return true;
			}
		}
		return false;
	}
	private boolean hasCollidedWithSelf(Snake player) {
		Position headPosition = player.getBody().get(0);
		for(Position bodyPosition : player.getBodyWithoutHead())
		{
			if(headPosition.equals(bodyPosition))
				return true;
		}
		return false;
	}
	
	private boolean hasArrivedAtFood(Snake player)
	{
		Position headPosition = player.getBody().get(0);
		for(Food food : _food)
		{
			if(headPosition.x == food.getPosition().get(0).x  && headPosition.y == food.getPosition().get(0).y)
				{
					System.out.println(player.getName() + "found food @" + player.getPosition().get(0).print() + " score:" + food.getGrowFactor());
					return true;
				}
		}
		return false;
	}
	
	private Food getFoodAt(Position position) {
		Food foodAtPosition = null;
		for(Food food : _food)
		{
			if(position.equals(food.getPosition().get(0)))
			{
				foodAtPosition = food;
				break;
			}
		}
		return foodAtPosition;
	}

	public BoardPlayState getBoardPlayState() {
		return _boardPlayState;
	}

	public int getBoardSizeX() {
		return _boardSizeX;
	}

	public int getBoardSizeY() {
		return _boardSizeY;
	}

}
