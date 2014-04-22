package automataCreator;

public class Helper 
{	
	public Helper()
	{
	}
	
	// PRE:
	// POST: Returns the public directory as determined by the system
	public static String getPublicDirectory()
	{
		String os = getOS();
		if (os.contains("Mac OS"))
		{
			return System.getProperty("user.home") + "/Desktop/";
		}
		
		else if (os.contains("Windows"))
		{
			return "";
		}
		
		else
		{
			return null;
		}
				
	}
	
	// PRE:
	// POST: Returns the operating system as determined by the system
	public static String getOS()
	{
		return System.getProperty("os.name");
	}

}

