import java.awt.Color;


public class Apple extends DrawableObject 
{
	public AppleConfiguration config;
	
	public Apple(AppleConfiguration config) {
		pos = config.GetStartPosition();
		this.config = config;
	}
	
	public Color GetColor()
	{
		return config.GetColor();
	}

}
