package automataCreator;

import java.util.ArrayList;

public class Validator 
{
	private ArrayList<State> _states;
	
	public Validator(ArrayList<State> states)
	{
		_states = states;
	}
	
	public boolean ValidateStates()
	{
		boolean validStates = true; 
		
		int start = 0;
		
		for (State state : _states)
		{
			if (state.getType() == Data.StateType.START || state.getType() == Data.StateType.STARTACCEPT)
			{
				start++; 
			}
		}
		
		if (start > 1)
		{
			validStates = false;
		}
		
		return validStates;
	}

}
