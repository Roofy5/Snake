package config;

import helper.Position;

import java.awt.Color;


public class SettingsSnake 
{
	private Position startPosition;
	private Color color;
	
	SettingsSnake(Position startPosition, Color color)
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
