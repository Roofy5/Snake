import java.awt.Color;


public class TailConfiguration {
	private Direction startDirection; //Elementy ogona nie beda istniec samodzielnie
	private Color color;				//wiec nie potrzebujemy ich pozycji startowej
										//jedynie wzgledny kierunek poczatkowy od glowy
	
	TailConfiguration(Direction startDirection, Color color)
	{
		this.startDirection = startDirection;
		this.color = color;
	}
	
	public Direction GetStartDirection()
	{
		return startDirection;
	}
	
	public Color GetColor()
	{
		return color;
	}
	
	public void SetColor(Color color){
		this.color = color;
	}
}
