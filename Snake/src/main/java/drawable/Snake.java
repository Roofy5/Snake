package drawable;

import config.SettingsControl;
import config.SnakeConfiguration;
import config.TailConfiguration;
import helper.Direction;
import helper.Position;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Snake
{
	private List <DrawableObject> levelMap;
	private Direction direction;
	private SettingsControl snakeControl;
	private TailConfiguration tailConfig;
	private Direction lastDirection = null;
	private List<Tail> tails;
	private Head head;
	private Position lastTail; //poprzednia pozycja konca ogona(mozliwosc wycofania ruchu)
	public boolean isDead;

	public Snake(SnakeConfiguration config, TailConfiguration tailConfig, List<DrawableObject> levelMap, int blockSize)
	{
		this.tailConfig = tailConfig;
		this.head = new Head(config.GetSettings());
		this.snakeControl = config.GetControl();
		tails = new ArrayList<Tail>();
		this.levelMap = levelMap;
		isDead = false;
	}

	public Head getHead() {
		return head;
	}

	public SettingsControl getSnakeControl() {
		return snakeControl;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction)
	{
		if(this.direction == null && direction == tailConfig.GetStartDirection() || //Stany zabronione np poruszanie sie
			lastDirection != null && direction == lastDirection.getOppositeDirection())
			return;
		this.direction = direction;
	}

	public void setDead(){
		tailConfig.SetColor(Color.LIGHT_GRAY);
		isDead = true;
	}

	private void addTailByPosition(Position pos)
	{
		Tail tail = new Tail(pos, tailConfig);
		tails.add(tail);
		levelMap.add(tail);
	}

	public void appendTail(){
		addTailByPosition(lastTail);
	}

	private void addTailDefault(){
		Direction dir = tailConfig.GetStartDirection();
		Position oldPosition;
		Position newPosition;
		if(tails.size() > 0)
			oldPosition = tails.get(tails.size() - 1).getPosition();
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
		for(i = 0; i < tails.size() - 1; i++){//cofamy elementy ogona
			tails.get(i).updatePosition(tails.get(i + 1).getPosition());
		}
		if(tails.size() > 0)
			tails.get(i).updatePosition(lastTail);
	}
	
	private void undoHead(){
		switch(this.direction){
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
	}

	private void moveHead(){//Ruszamy glowa drawable.Snake'a
		if(direction == Direction.UP)
			head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()-1));
		else if(direction == Direction.DOWN)
			head.updatePosition(new Position(head.getPosition().getX(), head.getPosition().getY()+1));
		else if(direction == Direction.LEFT)
			head.updatePosition(new Position(head.getPosition().getX()-1, head.getPosition().getY()));
		else if(direction == Direction.RIGHT)
			head.updatePosition(new Position(head.getPosition().getX()+1, head.getPosition().getY()));
	}

	private void moveTail(){
		int i;
		for(i = tails.size() - 1; i >= 1; i--){//Ruszamy elementami ogona
			tails.get(i).updatePosition(tails.get(i - 1).getPosition());
		}

		if(i == 0)//1. element ogona w miejsce glowy jesli istnieje
			tails.get(i).updatePosition(head.getPosition());
	}

	public void move(){
		if(this.direction == null)
			return;

		if(tails.size() > 0)//Zapisujemy pozycje ostatniego elementu ogona w razie potrzeby
			lastTail = tails.get(tails.size() - 1).getPosition();
		else
			lastTail = head.getPosition();

		moveTail();
		moveHead();
		lastDirection = this.direction;
	}
}
