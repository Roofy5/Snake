
public class Snake extends DrawableObject
{
	public SnakeConfiguration config;
	
	public static int noSnake = 0; 
	private int id;
	private Direction direction;
	
	public Snake(int x, int y, SnakeConfiguration config) {
		super(x, y);
		id = noSnake;
		noSnake++;
		this.config = config;
	}	
	
	public void SetDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	public Direction GetDirection()
	{
		return direction;
	}
}
