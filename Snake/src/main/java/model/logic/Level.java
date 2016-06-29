package model.logic;

import model.factories.AbstractControlFactory;
import model.factories.PositionFactory;
import model.factories.SnakeControl;
import model.drawable.*;
import helper.Direction;
import helper.Position;
import helper.SnakeState;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Level implements model.observator.Observer{

	private int countX;
	private int countY;
	private int maxFruitCount;
	private int eatenFruitsCount;
	private List <Snake> movableSnakes;
	private List <Snake> deadSnakes;
	private List <DrawableObject> map;
	private List <Fruit> fruits;
	private List <Fruit> eatenFruits;
	private Random randomGenerator;

	public Level(int countX, int countY)
	{
		this.countX = countX;
		this.countY = countY;
		this.maxFruitCount = 5;
		this.eatenFruitsCount = 0;
		map = new ArrayList<>();
		movableSnakes = new LinkedList<>();
		fruits = new ArrayList<>();
		eatenFruits = new ArrayList<>();
		randomGenerator = new Random();
		deadSnakes = new ArrayList<>();
	}

	public int getEatenFruitsCount() {
		return eatenFruitsCount;
	}

	public List<DrawableObject> getMap()
	{
		return map;
	}

	public List<Snake> getSnakes()
	{
		return Stream.of(movableSnakes, deadSnakes).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public List<Snake> getAliveSnakes()
	{
		return movableSnakes;
	}

	public Snake getSnakeByID(int id){
		for(Snake snake : getSnakes())
			if(snake.getSnakeID() == id)
				return snake;
		return null;
	}

	public void setMaxFruitCount(int count){
		maxFruitCount = count;
	}

	public void decreaseMaxFruitCount(){
		maxFruitCount -= 1;
	}

	public void addToObjectList(DrawableObject object){
		map.add(object);
	}

	public void addSnake(Snake snake){
		movableSnakes.add(snake);
		addToObjectList(snake.getHead());
	}

	private void addFruit(){
		Fruit fruit = generateFruit();
		generateFruitPosition(fruit);
		addToObjectList(fruit);
		fruits.add(fruit);
	}

	private Fruit generateFruit(){
		int maxRand = 50;
		int randNum = randomGenerator.nextInt(maxRand);
		Fruit fruit;
		if(randNum < 10)
			fruit = new Invis();
		else if(randNum > 44)
			fruit = new Pear();
		else
			fruit = new Apple();
		return fruit;
	}

	public void addFruits(int n){
		for(int i = 0; i < n; i++)
			addFruit();
	}

	private void addPlayer(Position pos, int startLength, Direction startDir, SnakeControl control){
		Snake snake1 = new Snake(new Head(pos),control, startDir, this);
		snake1.addStartingTails(startLength);
		addSnake(snake1);
	}

	public void addPlayers(int num, int startLength, AbstractControlFactory factory){
		switch(num){
			case 1:
				addPlayer(PositionFactory.getPosition(countX/2, countY/2), startLength, Direction.LEFT,
						factory.getPlayer1Control());
				break;
			case 2:
				addPlayer(PositionFactory.getPosition(countX/4, countY/4), startLength, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(PositionFactory.getPosition(countX - countX/4, countY - countY/4), startLength, Direction.RIGHT,
						factory.getPlayer2Control());

			break;
			case 3:
				addPlayer(PositionFactory.getPosition(countX/4, countY/4), startLength, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(PositionFactory.getPosition(countX - countX/4, countY - countY/4), startLength, Direction.RIGHT,
						factory.getPlayer2Control());

				addPlayer(PositionFactory.getPosition(countX/4, countY - countY/4), startLength, Direction.LEFT,
						factory.getPlayer3Control());

				break;
			case 4:
				addPlayer(PositionFactory.getPosition(countX/4, countY/4), startLength, Direction.LEFT,
						factory.getPlayer1Control());

				addPlayer(PositionFactory.getPosition(countX - countX/4, countY - countY/4), startLength, Direction.RIGHT,
						factory.getPlayer2Control());

				addPlayer(PositionFactory.getPosition(countX/4, countY - countY/4), startLength, Direction.LEFT,
						factory.getPlayer3Control());

				addPlayer(PositionFactory.getPosition(countX - countX/4, countY/4), startLength, Direction.RIGHT,
						factory.getPlayer4Control());
				break;

			default:
				break;
		}
	}

	private void removeFromFruitList(Fruit fruit)
	{
		fruits.remove(fruit);
	}
	
	private void removeFromObjectList(DrawableObject object)
	{
		map.remove(object);
	}

	private boolean collide(DrawableObject ob1, DrawableObject ob2){
		if(ob1 == ob2)
			return false;
		return ob1.getPosition().equals(ob2.getPosition());
	}

	private Snake findSnakeByHead(Head head){
		for(Snake snake : movableSnakes)
			if(snake.getHead() == head)
				return snake;
		return null;
	}

	private void generateFruits(){
		eatenFruits.clear();
		while(map.size() >= (countX * countY) || maxFruitCount < fruits.size()) {
			if (!decreaseFruitCount())
				return;
		}
			 // pelna map - usuwamy czesc jablek aby zrobic miejsce

		if(map.size() >= (countX * countY) / 2) // pelna map nawet z usunietymi jablkami - wychodzimy
			return;

		for(Fruit fruit : fruits){
			if(fruit.eaten){
				eatenFruits.add(fruit);
			}
		}

		fruits.removeAll(eatenFruits);
		map.removeAll(eatenFruits);
		addFruits(eatenFruits.size());
		eatenFruitsCount += eatenFruits.size();
	}

	private void generateFruitPosition(Fruit fruit){
		boolean emptyPosition = false;
		while(!emptyPosition) {
			emptyPosition = true;
			int x = randomGenerator.nextInt(countX);
			int y = randomGenerator.nextInt(countY);
			fruit.updatePosition(PositionFactory.getPosition(x, y));

			for(DrawableObject obj : map)
				if (collide(obj, fruit))
					emptyPosition = false;
		}
	}

	private boolean decreaseFruitCount(){
		for(Fruit fruit : fruits)
			if(fruit.eaten){
				removeFromObjectList(fruit);
				removeFromFruitList(fruit);
				return true;
			}
		return false;
	}

	private void growSnakes(){
		for(Fruit fruit : fruits){
			for(Snake snake : movableSnakes)
				if(collide(snake.getHead(), fruit)){
					if(fruit instanceof Pear)
						snake.setSnakeState(SnakeState.INVERSED);
					else if(fruit instanceof Invis)
						snake.setSnakeState(SnakeState.INVISIBLE);
					else if(fruit instanceof Apple)
						snake.setSnakeState(SnakeState.NORMAL);
					snake.appendTail();
					fruit.eaten = true;
				}
		}
	}

	private void checkSnakeCollisions(){
		Snake snake;
		Snake tempSnake;
		for(Iterator<Snake> it = movableSnakes.iterator(); it.hasNext();){
			snake = it.next();

			for(DrawableObject ob : map){
				if(collide(snake.getHead(), ob)){
					if(ob instanceof Head){
						Head head = (Head)ob;
						tempSnake = findSnakeByHead(head);
						if(tempSnake == null || (tempSnake.getCurrentDirection() == Direction.NONE) || tempSnake.isDead()){
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
		for(Snake snake : movableSnakes){
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
		for(Snake snake : movableSnakes)
			if(snake.isDead())
				dead = true;

		if(dead) {
			List<Snake> copyList = new LinkedList<Snake>(movableSnakes);
			for(Snake snake : copyList)
				if(snake.isDead()) {
					deadSnakes.add(snake);
					movableSnakes.remove(snake);
				}
		}
	}

	public void checkBoardState() {
		checkSnakesInBoard();
		growSnakes();
		checkSnakeCollisions();
		deleteDeadSnakes();
		generateFruits();
	}

	@Override
	public void update() {
		checkBoardState();
	}

	@Override
	public void update(DrawableObject ob) {
		addToObjectList(ob);
	}
}
