package automataCreator;

import java.awt.Graphics;

public class State extends DrawableObject
{
	protected Data.StateType _type;
	protected String _name;
	protected int _number;
	protected int _xPosition;
	protected int _yPosition;
	protected int _diameter;
	
	// Constructors
	public State()
	{
		_type = null;
		_name = "";
		_number = -1;
		_xPosition = -1;
		_yPosition = -1;
		_diameter = -1;
	}
	
	public State(Data.StateType type, String name, int number, int xPosition, int yPosition, int diameter)
	{
		_type = type;
		_name = name;
		_number = number;
		_xPosition = xPosition;
		_yPosition = yPosition;
		_diameter = diameter;
		
	}
	
	// Set methods
	public void setType(Data.StateType type)
	{
		_type = type;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public void setNumber(int number)
	{
		_number = number;
	}
	
	public void setXPosition(int xPosition)
	{
		_xPosition = xPosition;
	}
	
	public void setYPosition(int yPosition)
	{
		_yPosition = yPosition;
	}
	
	public void setDiameter(int diameter)
	{
		_diameter = diameter;
	}
	
	
	// Get methods
	public Data.StateType getType()
	{
		return _type;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public int getNumber()
	{
		return _number;
	}
	
	public int getXPosition()
	{
		return _xPosition;
	}
	
	public int getYPosition()
	{
		return _yPosition;
	}
	
	public int getDiameter()
	{
		return _diameter;
	}
	
	// Inherited methods
	public void Draw(Graphics g)
	{
		g.drawOval(_xPosition, _yPosition, 50, 50);
		g.drawString(_name, _xPosition + 19, _yPosition + 29); // center of circle	
		
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);
		
	}

}
