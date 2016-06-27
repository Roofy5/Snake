package model;

import config.AbstractControlFactory;
import config.SnakeControl;
import drawable.*;
import helper.Direction;
import helper.Position;

import javax.swing.Timer;
import java.util.*;

public class Level{

	private final static int DELAY_DIVIDER = 20;
	private int countX;
	private int countY;
	private List <Snake> snakeList;
	private List <DrawableObject> map;
	private List <Apple> apples;
	private Map<Snake, Timer> snakeToTimer;
	private Random randomGenerator;
	public Level(int countX, int countY)
	{
		this.countX = countX;
		this.countY = countY;
		snakeToTimer = new HashMap<Snake, Timer>();
		map = new ArrayList<DrawableObject>();
		snakeList = new LinkedList<Snake>();
		apples = new ArrayList<Apple>();
		randomGenerator = new Random();
	}

	public int getCountY() {
		return countY;
	}

	public int getCountX() {
		return countX;
	}

	public List<DrawableObject> getMap()
	{
		return map;
	}

	public List<Snake> getSnakes()
	{
		return snakeList;
	}

	public void addToObjectList(DrawableObject object){
		map.add(object);

		if(object instanceof Apple)
			apples.add((Apple)object);
	}

	public void addSnake(Snake snake, int delay){
		snakeList.add(snake);

		final Timer timer = new Timer(delay, (event) ->
		{if(!snakeList.contains(snake)) {
			((Timer)event.getSource()).stop();
			return;
		}
			snake.move();
			checkBoardState();
		});
		snakeToTimer.put(snake, timer);
		timer.start();

		addToObjectList(snake.getHead());
	}

	public void addApple(Position pos){
		addToObjectList(new Apple(pos));
	}

	private void addPlayer(Position pos, int startLength, int startDelay, Direction startDir, SnakeControl control){
		Snake snake1 = new Snake(new Head(pos),control,
				startDir, getMap());
		snake1.addStartingTails(startLength);
		addSnake(snake1, startDelay);
	}

	public void addPlayers(int num, int startLength, int startDelay, AbstractControlFactory factory){
		switch(num){
			case 1:
				addPlayer(new Position(countX/2, countY/2), startLength, startDelay, Direction.LEFT,
						factory.getPlayer1Control());
				break;
			case 2:
				addPlayer(new Position(countX/4, countY/4), startLength, startDelay, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(new Position(countX - countX/4, countY - countY/4), startLength, startDelay, Direction.RIGHT,
						factory.getPlayer2Control());

			break;
			case 3:
				addPlayer(new Position(countX/4, countY/4), startLength, startDelay, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(new Position(countX - countX/4, countY - countY/4), startLength, startDelay, Direction.RIGHT,
						factory.getPlayer2Control());

				addPlayer(new Position(countX/4, countY - countY/4), startLength, startDelay, Direction.LEFT,
						factory.getPlayer3Control());

				break;
			case 4:
				addPlayer(new Position(countX/4, countY/4), startLength, startDelay, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(new Position(countX - countX/4, countY - countY/4), startLength, startDelay, Direction.RIGHT,
						factory.getPlayer2Control());

				addPlayer(new Position(countX/4, countY - countY/4), startLength, startDelay, Direction.LEFT,
						factory.getPlayer3Control());

				addPlayer(new Position(countX - countX/4, countY/4), startLength, startDelay, Direction.RIGHT,
						factory.getPlayer4Control());
				break;

			default:
				break;
		}
	}

	private void removeFromAppleList(Apple apple)
	{
		apples.remove(apple);
	}
	
	private void removeFromObjectList(DrawableObject object)
	{
		map.remove(object);
	}

	private boolean collide(DrawableObject ob1, DrawableObject ob2){
		if(ob1 == ob2)
			return false;
		if(ob1.getPosition().equals(ob2.getPosition()))
			return true;
		return false;
	}

	private Snake findSnakeByHead(Head head){
		for(Snake snake : snakeList)
			if(snake.getHead() == head)
				return snake;
		return null;
	}

	private void generateApples(){
		while(map.size() >= (countX * countY) && decreaseAppleCount()){}
			 // pelna map - usuwamy czesc jablek aby zrobic miejsce

		if(map.size() >= (countX * countY) / 2) // pelna map nawet z usunietymi jablkami - wychodzimy
			return;

		for(Apple apple : apples){
			if(apple.eaten){
				while(!generateNewApplePosition(apple)){}
				apple.eaten = false;
			}
		}
	}

	private boolean generateNewApplePosition(Apple apple){
			apple.getPosition().setX(randomGenerator.nextInt(countX));
			apple.getPosition().setY(randomGenerator.nextInt(countY));

			for(DrawableObject obj : map)
				if(!collide(obj, apple))
					return true;

			return false;
	}

	private boolean decreaseAppleCount(){
		for(Apple apple : apples)
			if(apple.eaten){
				removeFromObjectList(apple);
				removeFromAppleList(apple);
				return true;
			}
		return false;
	}

	private void growSnakes(){
		for(Apple apple : apples){
			for(Snake snake : snakeList)
				if(collide(snake.getHead(), apple)){
					snake.appendTail();
					apple.eaten = true;
					Timer snakeTimer = snakeToTimer.get(snake);
					snakeTimer.setDelay(snakeTimer.getDelay() - snakeTimer.getDelay()/DELAY_DIVIDER);
					System.out.println("Delay: " + snakeTimer.getDelay());
				}
		}
	}

	private void checkSnakeCollisions(){
		Snake snake;
		Snake tempSnake;
		for(Iterator<Snake> it = snakeList.iterator(); it.hasNext();){
			snake = it.next();

			for(DrawableObject ob : map){
				if(collide(snake.getHead(), ob)){
					if(ob instanceof Head){
						Head head = (Head)ob;
						tempSnake = findSnakeByHead(head);
						if(tempSnake == null || (tempSnake.getCurrentDirection() == Direction.NONE) || tempSnake.isDead){
							snake.undoMove();
						}
						snake.setDead();
					}
					if(ob instanceof Block){
						snake.undoMove();
						snake.setDead();
					}
				}	
			}
		}
	}
	
	private void checkSnakesInBoard(){
		Position snakePosition;
		for(Snake snake : snakeList){
			snakePosition = snake.getHead().getPosition();
			if(snakePosition.getX() < 0 || snakePosition.getX() >= countX ||
					snakePosition.getY() < 0 || snakePosition.getY() >= countY) {
				snake.undoMove();
				snake.setDead();
			}
		}
	}

	private void deleteDeadSnakes(){
		boolean dead = false;
		for(Snake snake : snakeList)
			if(snake.isDead)
				dead = true;

		if(dead) {
			List<Snake> copyList = new LinkedList<Snake>(snakeList);
			for(Snake snake : copyList)
				if(snake.isDead)
					snakeList.remove(snake);
		}
	}

	private void checkBoardState() {
		checkSnakesInBoard();
		growSnakes();
		checkSnakeCollisions();
		deleteDeadSnakes();
		generateApples();
	}
	
}
