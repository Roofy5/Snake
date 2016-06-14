package drawable;

import helper.Position;
import java.awt.Color;



public abstract class DrawableObject {
	protected Position pos;
	protected Color color;
	public Position getPosition()
	{
		return pos;
	}
	
	public void updatePosition(Position p)
	{
		pos = p;
	}
	
	public abstract Color getColor();
}
