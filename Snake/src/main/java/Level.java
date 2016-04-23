import java.util.ArrayList;
import java.util.List;


public class Level {
	private int sizeX;
	private int sizeY;
	//private List<DrawableObject> listaObiektow;
	private List<Snake> listaSnake;
	private DrawableObject [][] mapa;	
	Level(int sizeX, int sizeY, DrawableObject[][] objects)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		mapa = objects;	
		listaSnake = new ArrayList<Snake>();
	}
	
	//public void Draw()
	//{	}
	
	public void AddToObjectList(DrawableObject object){
		Position pos = object.GetPosition();
		int x = pos.GetX();
		int y = pos.GetY();
		if(mapa[y][x] == null)
			mapa[y][x] = object;
		
		if(object instanceof Snake)
			listaSnake.add((Snake)object);
	}
	
	public void UpdatePosition() {}
	public void CheckControlers() {}
	
	public void RemoveFromObjectList(DrawableObject object){
		Position pos = object.GetPosition();
		int x = pos.GetX();
		int y = pos.GetY();
		if(mapa[y][x] != null)
			mapa[y][x] = null;
	}
	
	public void RemoveFromSnakeList(Snake s) {}
	public void ClearMap() {}
	public List<Snake> GetListOfSnakes()
	{
		return listaSnake;
	}

	public void move() {
		for(int i = 0; i < Snake.noSnake; i++)
		{
			
			Snake snake = listaSnake.get(i);
			Position position = snake.GetPosition();
			
			mapa[position.GetY()][position.GetX()] = null;
			
			if(snake.GetDirection() == Direction.UP)
				snake.UpdatePosition(new Position(position.GetX(), position.GetY()-1));
			else if(snake.GetDirection() == Direction.DOWN)
				snake.UpdatePosition(new Position(position.GetX(), position.GetY()+1));
			else if(snake.GetDirection() == Direction.LEFT)
				snake.UpdatePosition(new Position(position.GetX()-1, position.GetY()));
			else if(snake.GetDirection() == Direction.RIGHT)
				snake.UpdatePosition(new Position(position.GetX()+1, position.GetY()));
			
			//position = snake.GetPosition();
			mapa[position.GetY()][position.GetX()] = snake;
		}
		
	}
	
}
