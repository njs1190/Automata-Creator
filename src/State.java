// State.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to create an instance of a state object.
// This class inherits from its superclass DrawableObject and overrides its
// draw method.

package automataCreator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class State extends DrawableObject
{
	protected Data.StateType _type;
	protected String _name;
	protected int _number;
	protected int _xPosition;
	protected int _yPosition;
	protected int _diameter;
	protected boolean _current;
	
	// Constructors
	public State()
	{
		_type = null;
		_name = "";
		_number = -1;
		_xPosition = -1;
		_yPosition = -1;
		_diameter = -1;
		_current = false;
	}
	
	public State(Data.StateType type, String name, int number, int xPosition, int yPosition, int diameter, boolean current)
	{
		_type = type;
		_name = name;
		_number = number;
		_xPosition = xPosition;
		_yPosition = yPosition;
		_diameter = diameter;
		_current = current;
		
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
	
	public void setCurrent(boolean current)
	{
		_current = current;
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
	
	public boolean getCurrent()
	{
		return _current;
	}
	
	// Inherited methods
	public void Draw(Graphics g)
	{
		//File file = null;
		URL url = null;
		BufferedImage image = null;
			
		try 
		{
			if (_type == Data.StateType.START)
			{
				if (_current)
				{
					url = getClass().getResource("canvasStartSim.gif");
				}
				
				else
				{
					url = getClass().getResource("canvasStart.gif");
				}
			}
			
			else if (_type == Data.StateType.STARTACCEPT)
			{
				if (_current)
				{
					url = getClass().getResource("canvasStartASim.gif");
				}
				
				else
				{
					url = getClass().getResource("canvasStartA.gif");
				}
			}
			
			else if (_type == Data.StateType.INTERMEDIATE)
			{
				if (_current)
				{
					url = getClass().getResource("canvasIntermediateSim.gif");
				}
				
				else
				{
					url = getClass().getResource("canvasIntermediate.gif");
				}
			}
			
			else if (_type == Data.StateType.ACCEPT)
			{
				if (_current)
				{
					url = getClass().getResource("canvasAcceptSim.gif");
					

				}
				
				else
				{
					url = getClass().getResource("canvasAccept.gif");
				}
			}
			
			image = ImageIO.read(url.openStream());
			if (_type == Data.StateType.START || _type == Data.StateType.STARTACCEPT)
			{
				g.drawImage(image, _xPosition, _yPosition, 67, 50, null);	
			}
			else
			{
				g.drawImage(image, _xPosition, _yPosition, 50, 50, null);
			}
			
			// Center of circle for names with numbers under 10
			if (_number < 10)
			{
				if (_type == Data.StateType.START || _type == Data.StateType.STARTACCEPT)
				{
					g.drawString(_name, _xPosition + 36, _yPosition + 29); // center of circle	
				}
				else
				{
					g.drawString(_name, _xPosition + 19, _yPosition + 29); // center of circle
				}
			}
			
			// Center of circle for names with numbers greater than 10
			if (_number >= 10)
			{
				if (_type == Data.StateType.START || _type == Data.StateType.STARTACCEPT)
				{
					g.drawString(_name, _xPosition + 31, _yPosition + 29); // center of circle	
				}
				else 
				{
					g.drawString(_name, _xPosition + 14, _yPosition + 29); // center of circle
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

					
	}

}
