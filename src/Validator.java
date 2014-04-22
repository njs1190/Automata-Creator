// Author: Jidaeno
// Class: Validator.java
// Purpose: Responsible for going through the states, transitions and string
// and making sure that there is only one start state, at least one final state,
// each transition has at least one symbol, and that the string contains only
// letters from the binary alphabet {0, 1}

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
