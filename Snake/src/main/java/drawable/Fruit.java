package drawable;

import helper.Position;

public class Fruit extends DrawableObject
{
	public boolean eaten;
	
	public Fruit(Position position) {
		pos = position;
		eaten = false;
	}

}
