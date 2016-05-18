import java.awt.Color;

public abstract class SettingsFactory 
{
	public static SnakeConfiguration GetSnakeConfiguration(Position startPosition, Color color, char u, char d, char l, char r)
	{
		SettingsSnake conf1 = new SettingsSnake(startPosition, color);
		SettingsControl conf2 = new SettingsControl(u, d, l, r);
		return new SnakeConfiguration(conf1, conf2);
	}
	
	public static TailConfiguration GetTailConfiguration(Direction startDirection, Color color)
	{
		return new TailConfiguration(startDirection, color);
	}
	
	public static AppleConfiguration GetAppleConfigration(Color color)
	{
		return new AppleConfiguration(color);
	}
}
