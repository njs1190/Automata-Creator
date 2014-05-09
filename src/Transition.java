// Transition.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to create an instance of a transition object.
// This class inherits from its superclass DrawableObject and overrides its
// draw method.

package automataCreator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Transition extends DrawableObject
{
	protected State _to;
	protected State _from;
	protected Data.Symbol _symbol;
	protected boolean _current;
	protected int _tType;
	
	// Constructors
	public Transition()
	{
		_to = null;
		_from = null;
		_symbol = null;
		_current = false;
	}
	
	public Transition(State from, State to, Data.Symbol symbol, boolean current, int type)
	{
		_from = from;
		_to = to;		
		_symbol = symbol;
		_current = current;
		_tType = type;
	}
	
	// Set methods
	
	public void setFrom(State from)
	{
		_from = from;
	}
	
	public void setTo(State to)
	{
		_to = to;
	}
		
	public void setSymbol(Data.Symbol symbol)
	{
		_symbol = symbol;
	}
	
	public void setCurrent(boolean current)
	{
		_current = current;
	}
	
	// Get methods
	
	public State getFrom()
	{
		return _from;
	}
	
	public State getTo()
	{
		return _to;
	}

	public Data.Symbol getSymbol()
	{
		return _symbol;
	}
	
	public boolean getCurrent()
	{
		return _current;
	}
	
	// Inherited Methods
	public void Draw(Graphics g)
	{
		String s = "";
		
		//  radius of state
		double radius = 25;
		
		// If a symbol has been added, draw the symbol
		if (_symbol != null)
		{
			if (_symbol == Data.Symbol.ONE)
			{
				s = "1";
			}
			
			else if (_symbol == Data.Symbol.ZERO)
			{
				s ="0";
			}
			
			else if (_symbol == Data.Symbol.BOTH)
			{
				s = "0, 1";
			}
			
			else
			{
				s = "";
			}
		}
					
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2)); // set width of line 
		if (_current)
		{
			Color color = new Color(0x0099FF);  
			g2D.setColor(color);
		}
		
		else
		{
			g2D.setColor(Color.BLACK);
		}

		if (_to != _from)
		{
			int x1, x2, y1, y2 = 0;
			
			// Get center points of states 
			if (_from.getType() == Data.StateType.START || _from.getType() == Data.StateType.STARTACCEPT){
				x1 = _from.getXPosition() + 42;
				y1 = _from.getYPosition() + 25;
			}
			
			else
			{
				x1 = _from.getXPosition() + 25;
				y1 = _from.getYPosition() + 25;
			}
				
			if (_to.getType() == Data.StateType.START || _to.getType() == Data.StateType.STARTACCEPT)
			{
				x2 = _to.getXPosition() + 42;
				y2 = _to.getYPosition() + 25;
			}
			
			else
			{
				x2 = _to.getXPosition() + 25;
				y2 = _to.getYPosition() + 25;
			}			
							
			Point2D p1 = new Point2D.Double(x1, y1);
			Point2D p2 = new Point2D.Double(x2, y2);
				
			// Adjusted Point 1 
			// Radius / distance = delta x' / delta x 		
			double distance = p1.distance(p2);		
			// Ratio -> radius(always 25) / distance
			double ratio = radius / distance;		
			double deltaX = x2 - x1;		
			double deltaXPrime = deltaX * ratio;
			double deltaY = y2 - y1;
			double deltaYPrime = deltaY * ratio;
			
			// Adjusted Point 1
			double adjustedX1 = deltaXPrime + x1;			
			double adjustedY1 = deltaYPrime + y1;
			
			// Adjusted Point 2
			double adjustedX2 = (deltaXPrime * -1) + x2;
			double adjustedY2 = (deltaYPrime * -1) + y2;
			
			Point2D newP1 = new Point2D.Double(adjustedX1, adjustedY1);
			Point2D newP2 = new Point2D.Double(adjustedX2, adjustedY2);							
			
			// Find the midpoint of the two states
			int symbolX = (int)(((adjustedX2 - adjustedX1) / 2) + adjustedX1);
			int symbolY = (int)(((adjustedY2 - adjustedY1) / 2) + adjustedY1);
			
			// variables for determining geometry calculations
			double phi, rho;
			double changeY = newP1.getY() - newP2.getY();
			double changeX = newP1.getX() - newP2.getX();
			double theta = Math.atan2(changeY,  changeX);
						
			QuadCurve2D curveLine = new QuadCurve2D.Double();
			// draw QuadCurve2D.Double with set points
			double xOffSet, yOffSet; 
			double symbolCoordX, symbolCoordY;
			int offSetLength = 30;
			// drawing upper curve
			if(_tType == 1){
				// First offset coordinate
				phi = Math.toRadians(270);
				rho = theta + phi;
				xOffSet = symbolX - offSetLength * Math.cos(rho);
				yOffSet = symbolY - offSetLength * Math.sin(rho);
				changeY = yOffSet - newP2.getY();
				changeX = xOffSet - newP2.getX();
				// First symbol offset coordinate
				symbolCoordX = symbolX - 25 * Math.cos(rho);
				symbolCoordY = symbolY - 25 * Math.sin(rho);	
				curveLine.setCurve(newP1.getX(), newP1.getY(), xOffSet, yOffSet, newP2.getX(), newP2.getY());
				g2D.drawString(s, (int)symbolCoordX, (int)symbolCoordY + 4);
			}
			// drawing lower curve
			else if(_tType == 2){
				// Second offset coordinate
				phi = Math.toRadians(90);
				rho = theta + phi;
				xOffSet = symbolX - offSetLength * Math.cos(rho);
				yOffSet = symbolY - offSetLength * Math.sin(rho);
				changeY = yOffSet - newP2.getY();
				changeX = xOffSet - newP2.getX();
				// Second symbol offset coordinate
				symbolCoordX = symbolX - 30 * Math.cos(rho);
				symbolCoordY = symbolY - 30 * Math.sin(rho);	
				curveLine.setCurve(newP1.getX(), newP1.getY(), xOffSet, yOffSet, newP2.getX(), newP2.getY());
				g2D.drawString(s, (int)symbolCoordX, (int)symbolCoordY - 3);
			}	
			g2D.draw(curveLine);	
			
			// Variables for determining arrow head
			phi = Math.toRadians(155);
			int arrowHead = 10;
			// Draw arrow head on line 
			theta = Math.atan2(changeY,  changeX);
			double x, y;
			rho = theta + phi;
			x = newP2.getX() - arrowHead * Math.cos(rho);
			y = newP2.getY() - arrowHead * Math.sin(rho);
			g2D.draw(new Line2D.Double(newP2.getX(), newP2.getY(), x, y));
			
			// Draw arrow head on opposite side of line
			rho = theta - phi;
			x = newP2.getX() - arrowHead * Math.cos(rho);
			y = newP2.getY() - arrowHead * Math.sin(rho);
			g2D.draw(new Line2D.Double(newP2.getX(), newP2.getY(), x, y));
		}
		
		// Draw a self-loop on the to/from state
		else
		{
			// Find state position
			int x = _from.getXPosition();
			int y = _from.getYPosition();
			
			URL url = null;
			BufferedImage image = null;
			
			// Using image for self loop transition
			if (_current)
			{
				url = getClass().getResource("selfLoopSim.gif");
			}			
			else
			{
				url = getClass().getResource("selfLoop.gif");
			}		
			// try catch on reading the image
			try 
			{
				image = ImageIO.read(url.openStream());
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Draw image and symbol
			if (_from.getType() == Data.StateType.START || _from.getType() == Data.StateType.STARTACCEPT)
			{
				g.drawImage(image, x + 29, y - 25, 25, 25, null);
				if(_symbol == Data.Symbol.BOTH)
					g2D.drawString(s, x + 33, y - 28);
				else
					g2D.drawString(s, x + 39, y - 28);
			}
			else
			{
				g.drawImage(image, x + 12, y - 25, 25, 25, null);
				if(_symbol == Data.Symbol.BOTH)
					g2D.drawString(s, x + 16, y - 28);
				else
					g2D.drawString(s, x + 22, y - 28);
			}
		}
	}
}

	

