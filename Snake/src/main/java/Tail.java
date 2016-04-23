import java.awt.Color;


public class Tail extends DrawableObject {

	public TailConfiguration config;
	
	public Tail(TailConfiguration config) {
		pos = config.GetStartPosition();
		this.config = config;
	}
	
	public Color GetColor()
	{
		return config.GetColor();
	}

}
