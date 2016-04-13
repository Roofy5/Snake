
public abstract class DrawableObject {
	private Position pos;
	private Level lvl;
	
	public Position GetPosition()
	{
		return pos;
	}
	
	public void UpdatePosition(Position p)
	{
		pos = p;
	}
}
