package drawable;

import config.AppleConfiguration;
import helper.Position;

import java.awt.Color;

public class Apple extends DrawableObject 
{
	public AppleConfiguration config;
	public boolean eaten = false;
	
	public Apple(Position position, AppleConfiguration config) {
		pos = position;
		this.config = config;
	}

	@Override
	public Color getColor()
	{
		return config.GetColor();
	}

}
