package snakegame.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pathfinding.astar.core.AStar;
import pathfinding.core.*;


public class AISnake extends Snake
{
	
	private Board _board;

	public AISnake(Position startPos, Direction direction, Color color,
			String name, Board board) 
	{		
		super(startPos, direction, color, name);
		
		_board = board;
	}
	
	@Override
	protected Position getNewPosition(Position currentPos, Direction direction) {
		
		//All AI Logic goes here
		ArrayList<BoardEntity> boardState = _board.getBoardState();
		ArrayList<Position> food = new ArrayList<Position>();
		
		Grid grid = new Grid(_board.getBoardSizeX(), _board.getBoardSizeY());
		grid.fillGridWithWalkableNodes();
		
		
		for(BoardEntity entity : boardState)
		{
			if(entity.getEntityType() == BoardEntityType.PLAYER 
					&& ((Snake)entity).getName() != getName())
			{
				for(Position oppPos : ((Snake)entity).getBody())
				{
					grid.setNonWalkableNode(oppPos);
				}
			}
			if(entity.getEntityType() == BoardEntityType.FOOD)
				food.add(entity.getPosition().get(0));
		}
		
		
		for(Position mypos : this.getBody())
		{
			grid.setNonWalkableNode(mypos);
		}
		
		
		
	
		AStar astar = new AStar(grid);
		Node myHead = new Node(null, this.getBody().get(0), true);
		Node myTarget = new Node(null, food.get(0), true);
		List<Node> myPath = astar.findPath(myHead, myTarget, false);

		Position nextPosition;
		if(myPath.size() == 0)
		{
			List<Node> nodes = grid.getAdjacentNodes(myHead);
			if(!nodes.isEmpty())
				nextPosition = nodes.get(0).position;
			else
				//Fix this!!!!
				nextPosition = new Position(-1,-1);
		}
		else
			nextPosition = myPath.get(0).position; 
		
		return nextPosition;
	}

	
}
