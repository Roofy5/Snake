import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Level {
	private int sizeX;
	private int sizeY;
	private List <Snake> listaSnake;
	private List <DrawableObject> mapa;	
	private int applesPending = 0;
	Level(int sizeX, int sizeY, List<DrawableObject> objects)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		mapa = objects;	
		listaSnake = new ArrayList<Snake>();
	}
	
	public void AddToObjectList(DrawableObject object){
		mapa.add(object);
		
		if(object instanceof Snake)
			listaSnake.add((Snake)object);
	}
	
	public void UpdatePosition() {}
	public void CheckControlers() {}
	
	public void RemoveFromObjectList(DrawableObject object){
		for(DrawableObject ob : mapa)
			if(ob == object)
				mapa.remove(object);
	}
	
	public void RemoveFromSnakeList(Snake s) {}
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
			for(DrawableObject obj : mapa)
				if(obj instanceof Apple && ((Apple)obj).eaten){
					RemoveFromObjectList(obj);
					found = true;
					break;
				}
		}
		if(mapa.size() >= sizeX * sizeY) // pelna mapa nawet z usunietymi jablkami - wychodzimy
			return;
		
		generator = new Random();

		for(DrawableObject ob : mapa){
			if(ob instanceof Apple && ((Apple)ob).eaten){
				Apple apple = (Apple) ob;
				boolean used = false;
				do{
					apple.GetPosition().SetX(generator.nextInt(sizeX));
					apple.GetPosition().SetY(generator.nextInt(sizeY));
					
					for(DrawableObject obj : mapa)
						if(obj.GetPosition().equals(apple.GetPosition()) && apple != obj)
							used = true;		
					
				}while(used);
				apple.eaten = false;
			}
		}
	}
	
	private void GrowSnakes(){
		for(DrawableObject obj : mapa){
			if(obj instanceof Apple){
				for(Snake snake : listaSnake)
					if(snake.GetPosition().equals(obj.GetPosition())){
						snake.Grow();
						((Apple)obj).eaten = true;
						System.out.println("EATEN!");
					}
			}
		}
	}
	
	private void CheckSnakeCollisions(){
		for(Snake snake : listaSnake){
			for(DrawableObject ob : mapa){
				if(snake.GetPosition().equals(ob.GetPosition()) && snake != ob){
					if(ob instanceof Snake){
						snake.alive = false;
						((Snake) ob).alive = false;
					}
					else if(ob instanceof Tail){
						snake.alive = false;
						snake.UndoMove();
					}
				}	
			}
		}
	}
	
	private void CheckSnakesInBoard(){
		for(Snake snake : listaSnake){
		Position position = snake.GetPosition();
		if(snake.GetDirection() == Direction.UP){
			if(position.GetY() <= 0)
				snake.alive = false;
		}
		else if(snake.GetDirection() == Direction.DOWN){
			if(position.GetY() >= sizeY - 1)
				snake.alive = false;
		}
		else if(snake.GetDirection() == Direction.LEFT){
			if(position.GetX() <= 0) 
				snake.alive = false;
		}
		else if(snake.GetDirection() == Direction.RIGHT){
			if(position.GetX() >= 0)
				snake.alive = false;
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
