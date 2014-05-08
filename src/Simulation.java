// Simulation.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to process the simulation of a string
// on the created automaton. This class provides a method to simulate
// the entire string or just the next or previous symbol.

package automataCreator;

import java.util.ArrayList;
import java.util.Stack;

public class Simulation 
{
	private ArrayList<State> _states;
	private ArrayList<Transition> _transitions;
	private String _tape;
	private String _errorString;
	private boolean _dirty;
	private boolean _validated; 
	
	private State _currentState;
	private Transition _currentTransition;
	private int _currentSymbolOnTape;
	
	private OutputPanel _output;
	private SimulationPanel _simulation;
	
	private Stack<Transition> _transitionsMade; 
	
	// PRE: The output panel and the simulation bar have been 
	// created
	// POST: The simulation object is created with appropriate
	// data set 
	public Simulation(OutputPanel output, SimulationPanel simulation)
	{
		// We want to be able to interact with the
		// output panel so that we can set the 
		// current symbol and processed symbols
		// accordingly 
		_output = output;
		
		// We want to be able to interact with the
		// simulation panel so that we can set the 
		// buttons accordingly 
		_simulation = simulation;
		
		_transitionsMade = new Stack<Transition>();
		
		_errorString = "";
		
		_dirty = true;
		_validated = false;
		
	}
	
	// Begin set methods
	
	public void setStates(ArrayList<DrawableObject> objects)
	{
		initializeStates(objects);
	}
	
	public void setTransitions(ArrayList<DrawableObject> objects)
	{
		initializeTransitions(objects);
	}
	
	public void setTape(String tape)
	{
		_tape = tape;
	}
	
	public void setCurrentSymbolOnTape(int symbol)
	{
		_currentSymbolOnTape = symbol;
	}
	
	// End set methods
	
	
	// Begin get methods
	
	public boolean getDirty()
	{
		return _dirty;
	}
	
	public boolean getValidated()
	{
		return _validated;
	}
	
	public String getErrorString()
	{
		return _errorString;
	}
	
	public String getTape()
	{
		return _tape;
	}
	
	
	public int getCurrentSymbolOnTape()
	{
		return _currentSymbolOnTape;
	}

	// End get methods
	
	// PRE:
	// POST:
	private void initializeStates(ArrayList<DrawableObject> objects)
	{
		_states = new ArrayList<State>();
		for (DrawableObject obj : objects)
		{
			if (obj instanceof State)
			{
				_states.add((State)obj);
			}
		}
	}
	
	// PRE:
	// POST:
	private void initializeTransitions(ArrayList<DrawableObject> objects)
	{
		_transitions = new ArrayList<Transition>();
		for (DrawableObject obj : objects)
		{
			if (obj instanceof Transition)
			{
				_transitions.add((Transition)obj);
			}
		}
	}
	
	// PRE: Validate has been called from main and 
	// objects exists on the canvas to be
	// validated
	// POST: States, transitions and the input
	// string will be validated. If one of the
	// three is not validated the simulation
	// will not begin and an error will be output
	// in the output window 
	public boolean Validate()
	{
		// We want the errors for the
		// states to be first, then transitions.
		// and then the tape
		if (validateStates())
		{
			if (validateTransitions())
			{
				if (validateTape())
				{
					_validated = true;
					_dirty = false;
				}
				
				else
				{
					_validated = false;
					_dirty = true;
				}
			}
			
			else
			{
				_validated = false;
				_dirty = true;				
			}
			
		}
		
		else
		{
			_validated = false;
			_dirty = true;
		}
		
		return _validated;
	}

	// PRE: An automaton has been created 
	// POST: It is checked to see whether there is a start
	// state and if there is only one
	// start state and at least one final state 
	public boolean validateStates()
	{
		boolean validStates = true; // are the collection of states valid?
		
		int startStates = 0; // total of start states in automaton
		int finalStates = 0; // total of final states in automaton
		
		for (State state : _states)
		{			
			if (state.getType() == Data.StateType.START || state.getType() == Data.StateType.STARTACCEPT)
			{
				startStates++; 
			}
			
			else if (state.getType() == Data.StateType.ACCEPT || state.getType() == Data.StateType.STARTACCEPT)
			{
				finalStates++;
			}			
		}
		
		
		if (startStates < 1)
		{
			_errorString = "Automaton must have a start state";
			validStates = false;
		}
		
		else if (startStates != 1)
		{
			_errorString = "Automaton must have only one start state";
			validStates = false;			
		}
		
		else if (finalStates < 1)
		{
			_errorString = "Automaton must have at least one accept state";
			validStates = false;
		}
		
		return validStates;
	}
	
	// PRE: One or more transitions have been added to the canvas
	// POST: Goes through all of the states and checks to see
	// whether each state has a transition on both 0 and 1
	private boolean validateTransitions()
	{
		boolean validTransitions = true; // are the collection of transitions valid or not?		

		for (State state : _states)
		{
			boolean oneTransition = false; // does the state have a transition on a one?
			boolean zeroTransition = false; // does the state have a transition on a zero?
			
			for (Transition transition : _transitions)
			{				
				if (transition.getFrom() == state && transition.getSymbol() == Data.Symbol.ZERO)
				{
					zeroTransition = true;
				}
				
				if (transition.getFrom() == state && transition.getSymbol() == Data.Symbol.ONE)
				{
					oneTransition = true;
				}
				
				if (transition.getFrom() == state && transition.getSymbol() == Data.Symbol.BOTH)
				{
					zeroTransition = true;
					oneTransition = true;
				}

			}
			
			if (!zeroTransition || !oneTransition)
			{
				validTransitions = false;
			}
			
		}
		
		if (!validTransitions)
		{
			_errorString = "One or more states do not have a transition "+
			"on symbol 0 and on symbol 1";
		}
		
		return validTransitions;
		
	}

	// PRE: The tape has one or more symbols
	// POST: Goes through the tape and verifies it
	// is only made up of ones and zeroes
	private boolean validateTape()
	{
		boolean validTape = true; // is the input string valid?
		
		for (int i = 0; i < _output.getTape().length() && validTape; i++)
		{
			char c = _output.getTape().charAt(i);
			if (c != '0' && c != '1')
			{
				_errorString = "Tape must consist of only symbols from the binary alphabet {0, 1}";
				validTape = false;
			}
		}
		
		return validTape;		
	}
	
	// PRE: A stack is created and the transitions already
	// made have been added to the stack
	// POST: The previous symbol that was processed is 
	// popped off the stack re-processed
	public void previousSymbol()
	{
		_currentTransition.setCurrent(false);
		_currentTransition = _transitionsMade.pop();
		_currentTransition.setCurrent(true);
		
		_currentState.setCurrent(false);
		_currentState = _currentTransition.getFrom();
		_currentState.setCurrent(true);

	}
	
	// PRE: The tape, states and transitions have been initialized
	// POST: The next symbol on the tape is processed
	public void nextSymbol()
	{		
		// if the current state has not been set
		// set it to the start state
		if (_currentState == null)
		{
			_currentState = getStartState();
			_currentState.setCurrent(true);
		}
		
		// set the current transition to null so that we find the first 
		// transition that meets the criteria 
		if (_currentTransition != null)
		{
			_currentTransition.setCurrent(false);
			_currentTransition = null;
		}
		
		// stop if we go through all of the transition or we have found the first
		// transition that meets the criteria 
		for (int i = 0; i < _transitions.size() && _currentTransition == null; i++)
		{
			Transition t = _transitions.get(i);
			
			// If the transition from the current state back to itself
			// has the symbol that matches the current symbol on the tape, the current 
			// state remains the same
			if ((t.getFrom() == _currentState && t.getTo() == _currentState) && validSymbol(t.getSymbol()))
			{
				// set it to current so that it will be highlighted
				_currentState.setCurrent(true);
				
				// set new current transition
				_currentTransition = t;
				// set it to current so that it will be highlighted
				_currentTransition.setCurrent(true);				
			}
			
			// If the transition from the current state to another state has
			// the symbol that matches the current symbol on the tape, the current 
			// symbol becomes the state the transition transitions to
			else if ((t.getFrom() == _currentState && t.getTo() != _currentState) && validSymbol(t.getSymbol()))
			{
				// set old state to false so that it wont be highlighted
				_currentState.setCurrent(false);
				// set new current state
				_currentState = t.getTo();
				// set it to current so that it will be highlighted
				_currentState.setCurrent(true);

				// set new current transition
				_currentTransition = t;
				// set it to current so that it will be highlighted
				_currentTransition.setCurrent(true);
			}
		}
		
		// add the transition to the stack 
		_transitionsMade.add(_currentTransition);

	}
	
	// if not next, its previous
	public void beginStep(boolean next)
	{
		if (next)
		{
			// current symbol from the tape being processed 
			String currentSymbol = String.valueOf(_tape.charAt(_currentSymbolOnTape));	
			_output.setCurrentSymbol(currentSymbol);
			// take the symbol being processed off of the tape 
			_output.setTape(_output.getTape().substring(1)); 					
		}
		
		else
		{
			_currentSymbolOnTape--;	
			
			if (_currentSymbolOnTape < _tape.length())
			{
				_simulation.enableNextButton(true);
			}

			// current symbol from the processed symbols being processed 
			String currentSymbol = String.valueOf(_output.getProcessedSymbols().charAt((_output.getProcessedSymbols().length() - 1)));
			_output.setCurrentSymbol(currentSymbol);
			// take the last symbol that was processed off of the processed symbols string
			_output.setProcessedSymbols(_output.getProcessedSymbols().substring(0, (_output.getProcessedSymbols().length() - 1)));
	
		}
		
	}
	
	public void endStep(boolean next)
	{
		if (next)
		{
			String currentSymbol = _output.getCurrentSymbol();
			_output.setCurrentSymbol("");
			_output.setProcessedSymbols(_output.getProcessedSymbols() + currentSymbol);
			
			// Now that the current symbol on the tape has
			// been processed, increment the symbol on the tape.
			// If the current symbol is greater than 0, then
			// enable the previous symbol button
			_currentSymbolOnTape++;
			
			if (_currentSymbolOnTape > 0)
			{
				_simulation.enablePreviousButton(true);
			}
			
			// Have you reached the end of the tape?
			if (_currentSymbolOnTape >= _tape.length())
			{
				// If so, disable the next button
				_simulation.enableNextButton(false);
				
				// And check to see if the last symbol
				// on the tape was processed in a final
				// state
				if (_currentState.getType() == Data.StateType.STARTACCEPT || _currentState.getType() == Data.StateType.ACCEPT)
				{
					_output.setOutputContents("String accepted");
				}
				
				else
				{
					_output.setOutputContents("String rejected");	
				}
				

			}		
		}
		
		else
		{
			String currentSymbol = _output.getCurrentSymbol();
			_output.setCurrentSymbol("");
			_output.setTape(currentSymbol + _output.getTape());
			
			if (_currentSymbolOnTape == 0)
			{
				_simulation.enablePreviousButton(false);
				_output.setCurrentSymbol("");
			}
		}
	}
	
	
	public void beginSimulation()
	{
		if (_currentSymbolOnTape >= _tape.length() - 1)
		{
			_transitionsMade.clear();
			
			if (_currentState!= null)
			{
				_currentState.setCurrent(false);
				_currentState = null;
			}
			
			if (_currentTransition != null)
			{
				_currentTransition.setCurrent(false);
				_currentTransition = null;
			}	
			
			_currentSymbolOnTape = 0;
			
			if (!_output.getProcessedSymbols().isEmpty())
			{
				_output.setTape(_tape);
			}
			
			_output.setProcessedSymbols("");
			_output.setCurrentSymbol("");
		}		
	}

	// PRE: _states is not null
	// POST: The start state is returned
	private State getStartState()
	{
		if (_states != null)
		{
			State start = null; 
			// initialize current state to start state
			for (State s : _states)
			{
				if (s.getType() == Data.StateType.START || s.getType() == Data.StateType.STARTACCEPT)
				{
					start = s;
				}
			}
			
			return start;	
		}
		
		return null;
	}

	// PRE: The transition has a symbol 
	// POST: It is determined whether the current symbol on the tape
	// matches the symbol of the transition 
	private boolean validSymbol(Data.Symbol s)
	{
		boolean validState = false;
		
		char symbol = _tape.charAt(_currentSymbolOnTape);
		if ((symbol == '0') && (s == Data.Symbol.ZERO || s == Data.Symbol.BOTH))
		{
			validState = true;
		}
		
		else if ((symbol == '1') && (s == Data.Symbol.ONE || s == Data.Symbol.BOTH))
		{
			validState = true;
		}
		
		return validState;
	}
	
	// PRE: A change has been made to the canvas
	// or the current tape text field
	// POST: The variables that need to be reset
	// in order to create a fresh start for a simulation
	// are changed
	public void testChanged()
	{
		_dirty = true;
		_validated = false;
		
		_transitionsMade.clear();
		
		if (_currentState!= null)
		{
			_currentState.setCurrent(false);
			_currentState = null;
		}
		
		if (_currentTransition != null)
		{
			_currentTransition.setCurrent(false);
			_currentTransition = null;
		}	
		
		_currentSymbolOnTape = 0;
		
		_output.setProcessedSymbols("");
		_output.setCurrentSymbol("");
		
		_simulation.enablePreviousButton(false);
		_simulation.enableNextButton(true);
	}

}
