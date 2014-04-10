package automataCreator;

public class State 
{
	protected Data.StateType _type;
	protected String _name;
	protected int _number;
	protected int _xPosition;
	protected int _yPosition;
	
	// Constructors
	public State()
	{
		_type = null;
		_name = "";
		_number = -1;
		_xPosition = -1;
		_yPosition = -1;
	}
	
	public State(Data.StateType type, String name, int number, int xPosition, int yPosition)
	{
		_type = type;
		_name = name;
		_number = number;
		_xPosition = xPosition;
		_yPosition = yPosition;
		
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

}
