package automataCreator;

class Helper 
{
	
	private static String _publicDirectory;
	private static String _os;
	
	public Helper()
	{
		_publicDirectory = System.getProperty("PUBLIC");
		_os = System.getProperty("os.name");
	}
	
	// PRE:
	// POST: Returns the public directory as determined by the system
	public static String GetPublicDirectory()
	{
		return _publicDirectory;
	}
	
	// PRE:
	// POST: Returns the operating system as determined by the system
	public static String GetOS()
	{
		return _os;
	}

}

