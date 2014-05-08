// SaveTimer.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to generate an event every 2 minutes.
// Every two minutes the project will automatically be saved to the user
// desktop.

package automataCreator;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.EventListenerList;

public class SaveTimer 
{
	// Private members
	private Timer timer = null; // Timer object 	
	
	// Constructor
	public SaveTimer()
	{
	    timer = new Timer();
	}
	
	// PRE:
	// POST: A timer will start and will save the test every minute in the
	// case of an unexpected failure
	public void StartTimer()
	{
	    timer.schedule(new RunTask(), 1200000, 1200000);							
	}
	
	class RunTask extends TimerTask
	{
		public void run()
		{
			TimerEvent event = new TimerEvent(this); 
        	TimerEvents.sendTimerEvent(event);
		}
	}
}

//Create a close event 
@SuppressWarnings("serial")
class TimerEvent extends EventObject
{
	public TimerEvent(Object source)
	{
		super(source);
	}
	
}

interface TimerEventListener extends EventListener
{
	public void timerEvent(TimerEvent e);
}

class TimerEvents
{
    static EventListenerList listenerList = new EventListenerList();
	
    static void addTimerEventListener(TimerEventListener l)
	{
		listenerList.add(TimerEventListener.class, l);
	}
	
	static void removeTimerEventListener(TimerEventListener l)
	{
		listenerList.remove(TimerEventListener.class, l);
	}
	
	static void sendTimerEvent(TimerEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == TimerEventListener.class)
			{
				((TimerEventListener) listeners[i+1]).timerEvent(e);
			}
		}
	}
}




