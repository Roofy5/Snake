package drawable;

import config.*;
import helper.Direction;
import helper.Position;

import java.util.ArrayList;
import java.util.List;


public class Snake
{
	private static int snakeCounter = 1;
	private int snakeID;
	private List <DrawableObject> levelMap;
	private Direction startDirection;
	private SnakeControl snakeControl;
	private Direction lastDirection = Direction.NONE;
	private List<Block> blocks;
	private Head head;
	private Position lastTail; //poprzednia pozycja konca ogona(mozliwosc wycofania ruchu)
	public boolean isDead;

	public Snake(Head head, SnakeControl snakeControl, Direction startDirection, List<DrawableObject> levelMap)
	{
		this.startDirection = startDirection;
		this.head = head;
		this.snakeControl = snakeControl;
		blocks = new ArrayList<>();
		this.levelMap = levelMap;
		isDead = false;
		snakeID = snakeCounter++;
	}

	public static void cleanCount(){snakeCounter = 1;}

	public Head getHead() {
		return head;
	}

	public SnakeControl getSnakeControl() {
		return snakeControl;
	}

	public Direction getCurrentDirection() {
		return head.getDirection();
	}

	public Direction getStartDirection() {
		return startDirection;
	}

	public int getSnakeID(){
		return snakeID;
	}

	public void setCurrentDirection(Direction currentDirection)
	{
		if(head.getDirection() == Direction.NONE && currentDirection == startDirection || //Stany zabronione
			lastDirection != Direction.NONE && currentDirection == lastDirection.getOppositeDirection())
			return;
		head.setDirection(currentDirection);
	}

	public boolean hasPart(DrawableObject part){
		if(blocks.contains(part) || getHead() == part)
			return true;
		return false;
	}

	public void setDead(){
		isDead = true;
	}

	private void addTailByPosition(Position pos)
	{
		Block block = new Block(pos);
		blocks.add(block);
		levelMap.add(block);
	}

	public void appendTail()
	{
		addTailByPosition(lastTail);
	}

	private void addTailDefault(){
		Direction dir = startDirection;
		Position oldPosition;
		Position newPosition;
		if(blocks.size() > 0)
			oldPosition = blocks.get(blocks.size() - 1).getPosition();
		else
			oldPosition = head.getPosition();

		switch(dir){
			case UP:
				newPosition = new Position(oldPosition.getX(), oldPosition.getY() - 1);
				break;
			case DOWN:
				newPosition = new Position(oldPosition.getX(), oldPosition.getY() + 1);
				break;
			case LEFT:
				newPosition = new Position(oldPosition.getX() - 1, oldPosition.getY());
				break;
			case RIGHT:
				newPosition = new Position(oldPosition.getX() + 1, oldPosition.getY());
				break;
			default:
				newPosition = new Position(oldPosition.getX() - 1, oldPosition.getY());
		}
		addTailByPosition(newPosition);
	}
	
	public void addStartingTails(int n){
		for(;n > 1; n--)
			addTailDefault();
		addTailDefault();
		updatePartDirections();
	}

	private void undoTail(){
		int i;
		for(i = 0; i < blocks.size() - 1; i++){//cofamy elementy ogona
			blocks.get(i).updatePosition(blocks.get(i + 1).getPosition());
		}
		if(blocks.size() > 0)
			blocks.get(i).updatePosition(lastTail);
	}
	
	private void undoHead(){
		switch(head.getDirection()){
			case UP:
				head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()+1));
				break;
			case DOWN:
				head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()-1));
				break;
			case LEFT:
				head.updatePosition(new Position(head.getPosition().getX()+1, head.getPosition().getY()));
				break;
			case RIGHT:
				head.updatePosition(new Position(head.getPosition().getX()-1, head.getPosition().getY()));
				break;
			default:
				break;
		}
	}
	
	public void undoMove()
	{
		undoHead();
		undoTail();
		updatePartDirections();
	}

	private void moveHead(){//Ruszamy glowa drawable.Snake'a
		if(head.getDirection() == Direction.UP)
			head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()-1));
		else if(head.getDirection() == Direction.DOWN)
			head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()+1));
		else if(head.getDirection() == Direction.LEFT)
			head.updatePosition(new Position(head.getPosition().getX()-1, head.getPosition().getY()));
		else if(head.getDirection() == Direction.RIGHT)
			head.updatePosition(new Position(head.getPosition().getX()+1, head.getPosition().getY()));
	}

	private void moveTail(){
		int i;
		for(i = blocks.size() - 1; i >= 1; i--){//Ruszamy elementami ogona
			blocks.get(i).updatePosition(blocks.get(i - 1).getPosition());
		}

		if(i == 0)//1. element ogona w miejsce glowy jesli istnieje
			blocks.get(i).updatePosition(head.getPosition());
	}

	public void move(){
		if(head.getDirection() == Direction.NONE)
			return;

		if(blocks.size() > 0)//Zapisujemy pozycje ostatniego elementu ogona w razie potrzeby
			lastTail = blocks.get(blocks.size() - 1).getPosition();
		else
			lastTail = head.getPosition();

		moveTail();
		moveHead();
		updatePartDirections();
		lastDirection = head.getDirection();
	}

	public void updatePartDirections(){
		int xDiff, yDiff, xPrevDiff, yPrevDiff;
		Direction currDirection, prevDirection;
		for(int i = 0; i < blocks.size(); i++){
			xPrevDiff = yPrevDiff = 0;
			if(i == 0){
				xDiff = head.getPosition().getX() - blocks.get(i).getPosition().getX();
				yDiff = head.getPosition().getY() - blocks.get(i).getPosition().getY();
			}
			else {
				xDiff = blocks.get(i - 1).getPosition().getX() - blocks.get(i).getPosition().getX();
				yDiff = blocks.get(i - 1).getPosition().getY() - blocks.get(i).getPosition().getY();
			}
			if(i < blocks.size() - 1) {
				xPrevDiff = blocks.get(i).getPosition().getX() - blocks.get(i + 1).getPosition().getX();
				yPrevDiff = blocks.get(i).getPosition().getY() - blocks.get(i + 1).getPosition().getY();
			}

			if(xDiff > 0 && yDiff == 0)
				currDirection = Direction.RIGHT;
			else if(xDiff < 0 && yDiff == 0)
				currDirection = Direction.LEFT;
			else if(xDiff == 0 && yDiff > 0)
				currDirection = Direction.DOWN;
			else if(xDiff == 0 && yDiff < 0)
				currDirection = Direction.UP;
			else
				currDirection = Direction.NONE;

			if(xPrevDiff > 0 && yPrevDiff == 0)
				prevDirection = Direction.RIGHT;
			else if(xPrevDiff < 0 && yPrevDiff == 0)
				prevDirection = Direction.LEFT;
			else if(xPrevDiff == 0 && yPrevDiff > 0)
				prevDirection = Direction.DOWN;
			else if(xPrevDiff == 0 && yPrevDiff < 0)
				prevDirection = Direction.UP;
			else
				prevDirection = Direction.NONE;

			blocks.get(i).setCurrDirection(currDirection);
			blocks.get(i).setPrevDirection(prevDirection);
		}
	}
}
