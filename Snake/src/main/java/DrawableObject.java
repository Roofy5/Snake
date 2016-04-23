import java.awt.Color;


public abstract class DrawableObject {
	protected Position pos;
	private Level lvl;
	
	/*Niepotrzebny bo startowa pozycje pobieramy z config
	 * public DrawableObject(int x, int y){
		pos = new Position(x, y);
	}*/
	
	public Position GetPosition()
	{
		return pos;
	}
	
	public void UpdatePosition(Position p)
	{
		pos = p;
	}
	
	public abstract Color GetColor(); // Funkcja do przeladowania w kazdej podklasie
}
