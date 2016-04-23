import java.awt.Color;


public class AppleConfiguration 
{
	private Position startPosition;
	private Color color;
	
	AppleConfiguration(Position startPosition, Color color)
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
