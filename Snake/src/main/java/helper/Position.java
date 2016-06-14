package helper;

public class Position {
	private int posX;
	private int posY;
	
	public Position(int x, int y){
		posX = x;
		posY = y;
	}
	
	public void setX(int x)
	{
		posX =x;
	}
	
	public void setY(int y)
	{
		posY =y;
	}
	
	public int getX()
	{
		return posX;
	}
	
	public int getY()
	{
		return posY;
	}

	@Override
	public int hashCode() {
		int result = posX;
		result = 31 * result + posY;
		return result;
	}

	@Override
	public boolean equals(Object ob){
		if(ob instanceof Position){
			Position pos = (Position) ob;
			if(pos.getX() == getX() && pos.getY() == getY())
				return true;
		}
		return false;
	}
}
