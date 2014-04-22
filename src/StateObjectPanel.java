// Author: Jidaeno
// Class: StateObjectPanel.java
// Purpose: This class is in its own file because multiple
// classes use these events. An event will be fired when
// either the insert > state  menu options are chosen
// or if the buttons in the state object panel are chosen

package automataCreator;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

//State clicked event
@SuppressWarnings("serial")
class StateEvent extends EventObject
{
	private Data.StateType _type;
	
	public StateEvent(Object source, Data.StateType type)
	{
		super(source);
		_type = type;
	}
	
	public Data.StateType getType()
	{
		return _type;
	}
}

interface StateEventListener extends EventListener
{
	public void stateSelected(StateEvent e);
}

class StateEvents
{
 static EventListenerList listenerList = new EventListenerList();
	
 static void addStateEventListener(StateEventListener l)
	{
		listenerList.add(StateEventListener.class, l);
	}
	
	static void removeStateEventListener(StateEventListener l)
	{
		listenerList.remove(StateEventListener.class, l);
	}
	
	static void sendStateEvent(StateEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == StateEventListener.class)
			{
				((StateEventListener) listeners[i+1]).stateSelected(e);
			}
		}
	}
}
