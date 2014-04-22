// Author: Jidaeno
// Class: MainFrame.java
// Purpose: Responsible for setting up the user interface and also implementing
// the save and open methods 

package automataCreator;

// Java classes
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// Project classes
import automataCreator.simBar;
import automataCreator.menu;
import automataCreator.outPanel;
import automataCreator.myCanvas;

public class MainFrame 
{ 
	 private String _directory;
	 private static MainFrame _mainFrame;	 
	 private  JFrame _frame; 
	 private menu _menu; 	 
	 private JPanel simBarPanel;
	 private simBar _simulationBar;	 
	 private JPanel outputPanel;
	 private outPanel _outputPanel;	 
	 private JPanel canvasPanel;
	 private myCanvas _myCanvas;	 
	 private SaveTimer _timer;	 
	 private boolean _dirty;
	 
	 public static void main(String[] args)
	 {
	     //Schedule a job for the event-dispatching thread:
	     //creating and showing this application's GUI.
	     SwingUtilities.invokeLater(new Runnable() 
	     {
	         public void run() 
	         {
	        	 _mainFrame = new MainFrame();
	             _mainFrame.initalize();
	         }
	     });
	 }
	 
	 // PRE:
	 // POST: The user interface is created and shown, variables
	 // are initialized, and event listeners are created for events
	 // that need to be handled in the main window.
	 private void initalize()
	 {
		 // First, create and show the GUI
		 createAndShowGUI();
		 
		 // Set dirty bit
		 _dirty = false;	
		 
		 // Create event listeners for the main window
		 createListeners();

		 // Begin the automatic save timer
		 _timer = new SaveTimer();
		 _timer.StartTimer();
		 
		 _directory = Helper.getPublicDirectory();

	 }
	 
	 // PRE: An event has been fired
	 // POST: The event is handled
	 private void createListeners()
	 {
		// Add timer event listener so when it goes off
		// we save the test automatically
		TimerEvents.addTimerEventListener(new TimerEventListener()
	 	{
	 		public void timerEvent(TimerEvent e)
	 		{
	 			saveAutomatically();
	 		}
	 	});
		 
		// Add canvas event listener so when it goes off
		// we save the test dirty  bit to true
		CanvasEvents.addCanvasEventListener(new CanvasEventListener()
	 	{
	 		public void canvasModified(CanvasEvent e)
	 		{
	 			if (!_dirty)
	 				_dirty = true;	 			
	 		}
	 	});		 
	 }

	 // PRE:
	 // POST: The GUI is created and shown
	 private void createAndShowGUI() 
	 {		    
	     // Create and set up the frame. 
	     _frame = new JFrame("Automata Creator");
	     _frame.setContentPane(CreateContentPane());

	     // Add menu to frame
	     _menu = new menu(this);
	     _frame.setJMenuBar(_menu.initializeMenuBar());	         
	     
	     _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     _frame.setSize(800, 600);
	     _frame.setVisible(true);
	     _frame.setResizable(false);
	 }  

	// PRE:
	// POST: 
	public JPanel CreateContentPane()
	{
        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel(new BorderLayout());        
        
        // Create the simBar.
        _simulationBar = new simBar(); 
        _simulationBar.initializeSimBar();
        
        // Create the Output Window
        _outputPanel = new outPanel();
        _outputPanel.initializeOutputPanel();

        // Create scrollPane for canvas, 
        _myCanvas = new myCanvas();
        _myCanvas.initializeCanvas();
       
        // Add all components to the totalGUI JPanel
        totalGUI.add(_simulationBar, BorderLayout.NORTH);
        totalGUI.add(_outputPanel, BorderLayout.SOUTH);
        totalGUI.add(_myCanvas, BorderLayout.CENTER);    

        totalGUI.setOpaque(true);
        return totalGUI;
    }	

	// PRE: The filename chosen for this project must be chosen
	// POST: All of the project details will be saved into an XML 
	// file so that the project may be opened and worked on at another 
	// time
	public void save()
	{
		String filename = "";		

		try
		{	
			// file explorer
			JFileChooser file = new JFileChooser();
			
			// set restraints 
		    FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("*.xml", "xml");
			file.setFileFilter(xmlfilter);
			
			// set the file chooser to have default desktop directory
			File dir = new File(_directory);
			file.setCurrentDirectory(dir);
			
			if (file.showSaveDialog(file.getParent()) == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getAbsoluteFile().toString();
				if (!filename.contains(".xml"))
				{
					filename = filename + ".xml";
				}
			}

			FileOutputStream os = new FileOutputStream(filename);
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(_myCanvas);
			encoder.close();
		}

		catch(Exception ex)
		{
		}
	}
	
	// PRE: The timer expires
	// POST: The current work is saved in case of unexpected
	// termination of automata creator
	public void saveAutomatically()
	{
		String filename = _directory + "/test.xml";	

		try
		{	
			FileOutputStream os = new FileOutputStream(filename);
			XMLEncoder encoder = new XMLEncoder(os);
			encoder.writeObject(_myCanvas);
			encoder.close();
		}

		catch(Exception ex)
		{
		}
	}

	// PRE: An existing file must be chosen to open
	// POST: All objects saved in the XML file will be 
	// re-instantiated so that the workspace looks as it did before the
	// project was closed
	public Object openExisting()
	{
		String filename = "";

		try
		{
			// file explorer
			JFileChooser file = new JFileChooser();
			
			// set restraints 
		    FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("*.xml", "xml");
			file.setFileFilter(xmlfilter);

			if (file.showOpenDialog(file.getParent()) == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getAbsoluteFile().toString();
			}

			FileInputStream is = new FileInputStream(filename);
	        XMLDecoder decoder = new XMLDecoder(is);
	        Object obj = decoder.readObject();
	        decoder.close();
	        
	        //_myCanvas = new myCanvas(canvasPanel, ((myCanvas)obj).getStates(), ((myCanvas)obj).getTransitions());
	        //_myCanvas.RepaintCanvas();

	        return obj;
		}

		catch (Exception ex)
		{
			return null;	
		}		
	}	

	// PRE:
	// POST: If dirty we will ask user if they want to
	// save before closing, otherwise just close
	public void close()
	{ 
		if (_dirty)
		{
			int save = JOptionPane.showConfirmDialog(null, "Would you like to save your automaton before closing?", "Save Before Closing", JOptionPane.YES_NO_OPTION);
			if (save == JOptionPane.YES_OPTION)
			{
				save();
			}
		}

		// Close Automata Creator 
		System.exit(0);	
	}
}
