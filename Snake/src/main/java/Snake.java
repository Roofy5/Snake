import java.awt.Color;


public class Snake extends DrawableObject
{
	public SnakeConfiguration config;
	
	public static int noSnake = 0; 
	private int id;
	private Direction direction;
	
	/*public Snake(int x, int y, SnakeConfiguration config) {
		super(x, y);
		id = noSnake;
		noSnake++;
		this.config = config;
	}	*/
	
	public Snake(SnakeConfiguration config) 
	{
		id = noSnake;
		noSnake++;
		this.config = config;
		pos = config.GetSettings().GetStartPosition();
	}	
	
	public void SetDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	public Direction GetDirection()
	{
		return direction;
	}
	
	public Color GetColor()
	{
		return config.GetSettings().GetColor();
	}
}
