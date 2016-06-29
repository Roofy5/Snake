package drawable;

import config.*;
import helper.Direction;
import helper.Position;
import helper.SnakeState;
import observator.Observable;
import observator.Observer;

import java.util.ArrayList;
import java.util.List;


public class Snake implements Observable
{
	private static int snakeCounter = 1;
	private int snakeID;
	private SnakeState snakeState;
	private List <Observer> observers;
	private Direction startDirection;
	private SnakeControl snakeControl;
	private Direction lastDirection = Direction.NONE;
	private List<Block> blocks;
	private Head head;
	private Position lastTail; //poprzednia pozycja konca ogona(mozliwosc wycofania ruchu)

	public Snake(Head head, SnakeControl snakeControl, Direction startDirection, Observer obs)
	{
		this.startDirection = startDirection;
		this.head = head;
		this.snakeControl = snakeControl;
		this.observers = new ArrayList<>();
		this.snakeState = SnakeState.NORMAL;
		blocks = new ArrayList<>();
		head.setDead(false);
		snakeID = snakeCounter++;
		addObserver(obs);
	}

	public static void cleanCount(){snakeCounter = 1;}

	@Override
	public void addObserver(Observer obs) {
		observers.add(obs);
	}

	@Override
	public void deleteObserver(Observer obs) {
		observers.remove(obs);
	}

	@Override
	public void notifyObservers() {
		for(Observer obs : observers)
			obs.update();
	}

	@Override
	public void notifyObservers(DrawableObject ob) {
		for(Observer obs : observers)
			obs.update(ob);
	}

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

	public SnakeState getSnakeState(){return snakeState;}

	public int getSnakeID(){
		return snakeID;
	}

	public int getSize() { return blocks.size() + 1;}

	public boolean isRunning() {
		if(getCurrentDirection() == Direction.NONE)
			return false;
		return true;
	}

	public boolean isDead(){return head.isDead();}

	public void setSnakeState(SnakeState state){
		if(snakeState == SnakeState.INVERSED) {
			getSnakeControl().inverseControl();
			if (state == SnakeState.INVERSED)
				snakeState = SnakeState.NORMAL;
			else
				snakeState = state;
		}
		else {
			if(state == SnakeState.INVERSED)
				getSnakeControl().inverseControl();
			snakeState = state;
		}
	}

	public void setDefaultDirection(){
		setCurrentDirection(startDirection.getOppositeDirection());
	}

	public boolean setCurrentDirection(Direction currentDirection)
	{
		if(lastDirection == Direction.NONE && currentDirection == startDirection || //Stany zabronione
			lastDirection != Direction.NONE && currentDirection == lastDirection.getOppositeDirection())
			return false;
		head.setDirection(currentDirection);
		return true;
	}

	public boolean hasPart(DrawableObject part){
		if(blocks.contains(part) || getHead() == part)
			return true;
		return false;
	}

	public void setDead(){
		head.setDead(true);
	}

	private void addTailByPosition(Position pos)
	{
		Block block = new Block(pos);
		blocks.add(block);
		notifyObservers(block);
		updatePartDirections();
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
		for(;n > 0; n--)
			addTailDefault();
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
		notifyObservers();
	}

	private Direction getDirectionByOffset(int xOffset, int yOffset){
		if(xOffset > 0 && yOffset == 0)
			return Direction.RIGHT;
		else if(xOffset < 0 && yOffset == 0)
			return Direction.LEFT;
		else if(xOffset == 0 && yOffset > 0)
			return Direction.DOWN;
		else if(xOffset == 0 && yOffset < 0)
			return Direction.UP;
		else
			return Direction.NONE;
	}

	private Direction getPartCurrDirection(DrawableObject ob){
		Block tempBlock;
		int blockIndex;
		int xDiff, yDiff;
		if(!hasPart(ob))
			return null;
		if(ob instanceof Block){
			tempBlock = (Block)ob;
			blockIndex = blocks.lastIndexOf(tempBlock);

			if(blockIndex == 0){
				xDiff = head.getPosition().getX() - blocks.get(blockIndex).getPosition().getX();
				yDiff = head.getPosition().getY() - blocks.get(blockIndex).getPosition().getY();
			}
			else {
				xDiff = blocks.get(blockIndex - 1).getPosition().getX() - blocks.get(blockIndex).getPosition().getX();
				yDiff = blocks.get(blockIndex - 1).getPosition().getY() - blocks.get(blockIndex).getPosition().getY();
			}

			return getDirectionByOffset(xDiff, yDiff);
		}
		else
			return null;
	}

	private Direction getPartPrevDirection(DrawableObject ob){
		Block tempBlock;
		int blockIndex;
		int xPrevDiff, yPrevDiff;
		if(!hasPart(ob))
			return null;
		if(ob instanceof Block){
			tempBlock = (Block)ob;
			blockIndex = blocks.lastIndexOf(tempBlock);

			if(blockIndex < blocks.size() - 1) {
				xPrevDiff = blocks.get(blockIndex).getPosition().getX() - blocks.get(blockIndex + 1).getPosition().getX();
				yPrevDiff = blocks.get(blockIndex).getPosition().getY() - blocks.get(blockIndex + 1).getPosition().getY();
			}
			else
				return Direction.NONE;

			return getDirectionByOffset(xPrevDiff, yPrevDiff);
		}
		else
			return null;
	}

	public void updatePartDirections(){
		Direction prevDirection, currDirection;
		for(Block block : blocks){
			prevDirection = getPartPrevDirection(block);
			currDirection = getPartCurrDirection(block);
			block.setCurrDirection(currDirection);
			block.setPrevDirection(prevDirection);
		}
	}

}
