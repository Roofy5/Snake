package drawable;

import config.TailConfiguration;
import helper.Position;

import java.awt.Color;


public class Tail extends DrawableObject {

	public TailConfiguration config;
	
	public Tail(Position position, TailConfiguration config) {
		pos = position;
		this.config = config;
	}
	
	public Color getColor()
	{
		return config.GetColor();
	}


	
}
