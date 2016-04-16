
public abstract class DrawableObject {
	private Position pos;
	private Level lvl;
	
	public DrawableObject(int x, int y){
		pos = new Position(x, y);
	}
	
	public Position GetPosition()
	{
		return pos;
	}
	
	public void UpdatePosition(Position p)
	{
		pos = p;
	}
}
