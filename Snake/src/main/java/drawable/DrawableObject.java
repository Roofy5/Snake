package drawable;

import helper.Position;


public abstract class DrawableObject {
	Position pos;

	public Position getPosition()
	{
		return pos;
	}
	
	public void updatePosition(Position p)
	{
		pos = p;
	}

}
