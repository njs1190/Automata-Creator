// Author: Jidaeno
// Class: Transition.java
// Purpose: Provides the attributes and methods of a transition object that
// will be added to the canvas between two state objects. This class extends
// from the drawable object super class

package automataCreator;

import java.awt.Graphics;

public class Transition extends DrawableObject
{
	protected State _to;
	protected State _from;
	protected Data.Symbol _symbol;
	
	// Constructors
	public Transition()
	{
		_to = null;
		_from = null;
		_symbol = null;
	}
	
	public Transition(State to, State from, Data.Symbol symbol)
	{
		_to = to;
		_from = from;
		_symbol = symbol;
	}
	
	// Set methods
	public void setTo(State to)
	{
		_to = to;
	}
	
	public void setFrom(State from)
	{
		_from = from;
	}
	
	public void setSymbol(Data.Symbol symbol)
	{
		_symbol = symbol;
	}
	
	// Get methods
	public State getTo()
	{
		return _to;
	}
	
	public State getFrom()
	{
		return _from;
	}
	
	public Data.Symbol getSymbol()
	{
		return _symbol;
	}
	
	// Inherited Methods
	public void Draw(Graphics g)
	{
		
	}
	
}
