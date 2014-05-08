// Data.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is stores the enums used throughout the entire solution 

package automataCreator;

public class Data
{
	// The symbols that the transitions are labeled with 
	public enum Symbol
	{
		ZERO,
		ONE,
		BOTH
	}
	
	// The type of state each state added to the canvas is 
	public enum StateType
	{
		START,
		STARTACCEPT,
		ACCEPT,
		INTERMEDIATE
	}
}


