import java.awt.Color;
/*XX*/

public class Apple extends DrawableObject 
{
	public AppleConfiguration config;
	public boolean eaten = false;
	
	public Apple(Position position, AppleConfiguration config) {
		pos = position;
		this.config = config;
	}
	
	public Color GetColor()
	{
		return config.GetColor();
	}

}
