
public class Position {
	private int pozX;
	private int pozY;
	
	public Position(int x, int y){
		pozX = x;
		pozY = y;
	}
	
	public void SetX(int x)
	{
		pozX=x;
	}
	
	public void SetY(int y)
	{
		pozY=y;
	}
	
	public int GetX()
	{
		return pozX;
	}
	
	public int GetY()
	{
		return pozY;
	}
}
