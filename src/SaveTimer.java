// Author: Jidaeno
// Class: SaveTimer.java
// Purpose: Creates a timer that will go off every minute in order to
// automatically save the test in case of a unexpected crash

package automataCreator;

import java.util.Timer;
import java.util.TimerTask;

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
	    timer.schedule(new RunTask(), 60000, 60000);							
	}
	
	class RunTask extends TimerTask
	{
		public void run()
		{
			// Here we will save the test automatically for the user
			// in the case of an unexpected crash we do not want the 
			// user to lose their work
		}
	}
}




