package automataCreator;

import java.util.Timer;
import java.util.TimerTask;

public class SaveTimer 
{
	private Timer timer = null;
	
	public SaveTimer()
	{
	    timer = new Timer();
	}
	
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




