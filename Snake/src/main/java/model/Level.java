package model;

import drawable.*;
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

	public List<DrawableObject> GetMap()
	{
		return map;
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

	private void RemoveFromAppleList(Apple apple)
	{
		apples.remove(apple);
	}
	
	private void RemoveFromObjectList(DrawableObject object)
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

	private void GenerateApples(){
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
				RemoveFromObjectList(apple);
				RemoveFromAppleList(apple);
				return true;
			}
		return false;
	}

	private void GrowSnakes(){
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

	private void CheckSnakeCollisions(){
		Snake snake;
		Snake tempSnake;
		for(Iterator<Snake> it = snakeList.iterator(); it.hasNext();){
			snake = it.next();

			for(DrawableObject ob : map){
				if(collide(snake.getHead(), ob)){
					if(ob instanceof Head){
						Head head = (Head)ob;
						tempSnake = findSnakeByHead(head);
						if(tempSnake == null || (tempSnake.getDirection() == null) || tempSnake.isDead){
							snake.undoMove();
						}
						snake.setDead();
					}
					if(ob instanceof Tail){
						snake.undoMove();
						snake.setDead();
					}
				}	
			}
		}
	}
	
	private void CheckSnakesInBoard(){
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
		CheckSnakesInBoard();
		GrowSnakes();
		CheckSnakeCollisions();
		deleteDeadSnakes();
		GenerateApples();
	}
	
}
