import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Snake extends DrawableObject
{
	public boolean alive = true;
	private List <DrawableObject> levelMap;
	public SnakeConfiguration snakeConfig;
	public TailConfiguration tailConfig;
	public static int noSnake = 0; 
	private int id;
	private Direction direction = null;
	private Direction lastDirection = null;
	private List<Tail> tails;
	private Position lastTail; //poprzednia pozycja konca ogona(mozliwosc wycofania ruchu)
			
	/*public Snake(int x, int y, SnakeConfiguration config) {
		super(x, y);
		id = noSnake;
		noSnake++;
		this.config = config;
	}	*/
	
	public Snake(SnakeConfiguration config, TailConfiguration tailConfig, List<DrawableObject> levelMap) 
	{
		id = noSnake;
		noSnake++;
		this.snakeConfig = config;
		this.tailConfig = tailConfig;
		pos = config.GetSettings().GetStartPosition();
		tails = new ArrayList<Tail>();
		this.levelMap = levelMap;
		lastDirection = tailConfig.GetStartDirection().getOppositeDirection();
	}	
	
	public void SetDirection(Direction direction)
	{
		if(GetDirection() == null && direction == tailConfig.GetStartDirection() || //Stany zabronione np poruszanie sie 
			direction == lastDirection.getOppositeDirection())
			return;
		this.direction = direction;
	}
	
	public Direction GetDirection()
	{
		return direction;
	}
	
	public Color GetColor()
	{
		return snakeConfig.GetSettings().GetColor();
	}
		
	public List<Tail> GetTails()
	{
		return tails;
	}
	
	public void ChangeTailColor(Color color){
		tailConfig.SetColor(color);
	}
	
	public void AddTail(Position pos)
	{
		Tail tail = new Tail(pos, tailConfig);
		tails.add(tail);
		levelMap.add(tail);
	}
	
	public void AddTail(){
		Direction direction = tailConfig.GetStartDirection();
		Position oldPosition;
		Position newPosition;
		if(tails.size() > 0)
			oldPosition = tails.get(tails.size() - 1).GetPosition();
		else
			oldPosition = GetPosition();
		
		if(direction == Direction.UP)
			newPosition = new Position(oldPosition.GetX(), oldPosition.GetY() - 1);
		else if(direction == Direction.DOWN)
			newPosition = new Position(oldPosition.GetX(), oldPosition.GetY() + 1);
		else if(direction == Direction.LEFT)
			newPosition = new Position(oldPosition.GetX() - 1, oldPosition.GetY());
		else if(direction == Direction.RIGHT)
			newPosition = new Position(oldPosition.GetX() + 1, oldPosition.GetY());
		else //Domyslnie ustawiamy po lewej w razie klopotow
			newPosition = new Position(oldPosition.GetX() - 1, oldPosition.GetY()); 
		
		AddTail(newPosition);
	}
	
	public void AddTail(int n){
		for(;n > 0; n--)
			AddTail();
	}
	
	public void Grow()
	{
		AddTail(lastTail);
	}
	
	private void UndoTail(){
		int i;
		for(i = 0; i < tails.size() - 1; i++){//cofamy elementy ogona
			tails.get(i).UpdatePosition(tails.get(i + 1).GetPosition()); 
		}
		if(tails.size() > 0)
			tails.get(i).UpdatePosition(lastTail);
	}
	
	private void UndoHead(){
		if(GetDirection() == Direction.UP) 
			UpdatePosition(new Position(GetPosition().GetX(), GetPosition().GetY()+1));
		else if(GetDirection() == Direction.DOWN)
			UpdatePosition(new Position(GetPosition().GetX(), GetPosition().GetY()-1));
		else if(GetDirection() == Direction.LEFT)
			UpdatePosition(new Position(GetPosition().GetX()+1, GetPosition().GetY()));
		else if(GetDirection() == Direction.RIGHT)
			UpdatePosition(new Position(GetPosition().GetX()-1, GetPosition().GetY()));
	}
	
	public void UndoMove()
	{
		UndoHead();
		UndoTail();
	}
	
	private void MoveHead(){//Ruszamy glowa Snake'a
		if(GetDirection() == Direction.UP) 
			UpdatePosition(new Position(GetPosition().GetX(), GetPosition().GetY()-1));
		else if(GetDirection() == Direction.DOWN)
			UpdatePosition(new Position(GetPosition().GetX(), GetPosition().GetY()+1));
		else if(GetDirection() == Direction.LEFT)
			UpdatePosition(new Position(GetPosition().GetX()-1, GetPosition().GetY()));
		else if(GetDirection() == Direction.RIGHT)
			UpdatePosition(new Position(GetPosition().GetX()+1, GetPosition().GetY()));
	}
	
	private void MoveTail(){
		int i;
		for(i = tails.size() - 1; i >= 1; i--){//Ruszamy elementami ogona
			tails.get(i).UpdatePosition(tails.get(i - 1).GetPosition()); 
		}
		
		if(i == 0)//1. element ogona w miejsce glowy jesli istnieje
			tails.get(i).UpdatePosition(GetPosition());
	}
	
	public void Move(){
		if(GetDirection() == null)
			return;
		
		if(tails.size() > 0)//Zapisujemy pozycje ostatniego elementu ogona w razie potrzeby
			lastTail = tails.get(tails.size() - 1).GetPosition();
		else
			lastTail = GetPosition();
		
		MoveTail();
		MoveHead();
		lastDirection = GetDirection();
	}
}
