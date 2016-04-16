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
	}
	
	//public void Draw()
	//{	}
	
	public void AddToObjectList(DrawableObject object){
		Position pos = object.GetPosition();
		int x = pos.GetX();
		int y = pos.GetY();
		if(mapa[y][x] == null)
			mapa[y][x] = object;
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

	public void move(Direction direction) {
		int i, j;
		if(direction == Direction.RIGHT){
			for(i = sizeX - 1; i > 0; i--)
				for(j = 0; j < sizeY; j++){
					mapa[j][i] = mapa[j][i - 1];
					if(mapa[j][i] != null)
						mapa[j][i].UpdatePosition(new Position(i, j));
				}
			for(j = 0; j < sizeY; j++)	
				mapa[j][i] = null;
		}
	}
	
}
