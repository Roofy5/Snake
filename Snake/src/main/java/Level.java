import java.util.List;


public class Level {
	private int sizeX;
	private int sizeY;
	//private List<DrawableObject> listaObiektow;
	private List<Snake> listaSnake;
	private DrawableObject [][] mapa;
	
	Level(int x, int y)
	{
		sizeX = x;
		sizeY = y;
		mapa = new DrawableObject[sizeY][sizeX];
	}
	
	public void Draw()
	{	}
	
	public void UpdatePosition() {}
	public void CheckControlers() {}
	public void RemoveFromObjectList(DrawableObject object) {}
	public void RemoveFromSnakeList(Snake s) {}
	public void ClearMap() {}
	
}
