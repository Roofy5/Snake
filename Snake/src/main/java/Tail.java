import java.awt.Color;


public class Tail extends DrawableObject {

	public TailConfiguration config;
	
	public Tail(Position position, TailConfiguration config) {
		pos = position;
		this.config = config;
	}
	
	public Color GetColor()
	{
		return config.GetColor();
	}
	
}
