import java.awt.Color;


public class TailConfiguration {
	private Position startPosition;
	private Color color;
	
	TailConfiguration(Position startPosition, Color color)
	{
		this.startPosition = startPosition;
		this.color = color;
	}
	
	public Position GetStartPosition()
	{
		return startPosition;
	}
	
	public Color GetColor()
	{
		return color;
	}
}
