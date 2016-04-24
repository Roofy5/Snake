import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Level{
	public int sizeX;
	public int sizeY;
	private List <Snake> listaSnake;
	private List <DrawableObject> mapa;	
	private List <Apple> apples;
	Level(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		mapa = new ArrayList<DrawableObject>();	
		listaSnake = new LinkedList<Snake>();// lepsza wydajnosc gdy usuwamy iterujac po liscie
		apples = new ArrayList<Apple>();
	}
	
	public List<DrawableObject> GetMap()
	{
		return mapa;
	}
	
	public void AddToObjectList(DrawableObject object){
		mapa.add(object);
		
		if(object instanceof Snake)
			listaSnake.add((Snake)object);
		if(object instanceof Apple)
			apples.add((Apple)object);
	}
	
	public void UpdatePosition() {}
	public void CheckControlers() {}
	
	public void RemoveFromAppleList(Apple apple)
	{
		apples.remove(apple);
	}
	
	public void RemoveFromObjectList(DrawableObject object)
	{
			mapa.remove(object);
	}
	
	public void RemoveFromSnakeList(Snake s) 
	{
		listaSnake.remove(s);
	}
	public void ClearMap() {}
	public List<Snake> GetListOfSnakes()
	{
		return listaSnake;
	}
	
	private void GenerateApples(){
		Random generator;
		boolean found = true;
		while(mapa.size() >= sizeX * sizeY && found) // pelna mapa - usuwamy czesc jablek aby zrobic miejsce
		{
			found = false;
			for(Apple apple : apples)
				if(apple.eaten){
					RemoveFromObjectList(apple);
					RemoveFromAppleList(apple);
					found = true;
					break;
				}
		}
		if(mapa.size() >= sizeX * sizeY) // pelna mapa nawet z usunietymi jablkami - wychodzimy
			return;
		
		generator = new Random();

		for(Apple apple : apples){
			if(apple.eaten){
				boolean used;
				do{
					used = false;
					apple.GetPosition().SetX(generator.nextInt(sizeX));
					apple.GetPosition().SetY(generator.nextInt(sizeY));
					
					for(DrawableObject obj : mapa)
						if(obj.GetPosition().equals(apple.GetPosition()) && apple != obj){
							used = true;		
							break;
						}
					
				}while(used);
				apple.eaten = false;
			}
		}
	}
	
	private void GrowSnakes(){
		for(Apple apple : apples){
			for(Snake snake : listaSnake)
				if(snake.GetPosition().equals(apple.GetPosition())){
					snake.Grow();
					apple.eaten = true;
					System.out.println("EATEN!");
				}
		}
	}
	
	private void CheckSnakeCollisions(){
		for(Iterator<Snake> it = listaSnake.iterator(); it.hasNext();){
			Snake snake = it.next();
			Position pos = snake.GetPosition();
			for(DrawableObject ob : mapa){
				if(pos.equals(ob.GetPosition()) && snake != ob){
					if(ob instanceof Snake){
						if(!listaSnake.contains((Snake)ob))
							snake.UndoMove();
						it.remove();
					}
					if(ob instanceof Tail){
						snake.UndoMove();
						it.remove();
						
					}
				}	
			}
		}
	}
	
	private void CheckSnakesInBoard(){
		for(Iterator<Snake> it = listaSnake.iterator(); it.hasNext();){
			Snake snake = it.next();
			Position position = snake.GetPosition();
			if(snake.GetDirection() == Direction.UP){
				if(position.GetY() <= 0)
					it.remove();
			}
			else if(snake.GetDirection() == Direction.DOWN){
				if(position.GetY() >= sizeY - 1)
					it.remove();
			}
			else if(snake.GetDirection() == Direction.LEFT){
				if(position.GetX() <= 0) 
					it.remove();
			}
			else if(snake.GetDirection() == Direction.RIGHT){
				if(position.GetX() >= sizeX - 1)
					it.remove();
			}
		}
	}
	
	public void Move() {
		CheckSnakesInBoard();
		for(Snake snake : listaSnake)
			snake.Move();
		GrowSnakes();
		CheckSnakeCollisions();
		GenerateApples();
	}
	
}
