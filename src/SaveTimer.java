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




