// MainFrame.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to control all of the panels that are added to the frame
// and dealing with actions from the components of these panels such as menu item selections. 
// MainFrame is responsible for closing the software in a reasonable manner. 

package automataCreator;

// Java classes
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
// Project classes

public class MainFrame 
{ 
	 private String _directory;
	 private static MainFrame _mainFrame;	
	 private JPanel _totalGUI;
	 private JFrame _frame; 
	 private Menu _menu; 	 
	 private SimulationPanel _simulationBar;	 
	 private OutputPanel _outputPanel;	 
	 private StateObjectPanel _stateObjectPanel;
	 private Canvas _myCanvas;
	 private JScrollPane _scrollCanvas;
	 private Simulation _simulator;
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
		 initializeListeners();

		 _simulator = new Simulation(_outputPanel, _simulationBar);
		 
		 // Begin the automatic save timer
		 _timer = new SaveTimer();
		 _timer.StartTimer();
		 
		 _directory = getPublicDirectory();

	 }
	 
	 // PRE: An event has been fired
	 // POST: The event is handled
	 private void initializeListeners()
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
	 				setTitle("AutomataCreator*");
	 			
	 			if (_outputPanel != null)
	 				_outputPanel.setOutputContents("Not Simulated");
	 			
	 			_simulator.reset(false);
	 			
	 			// if the canvas has changed and a start state exists disable the start state options
	 			// otherwise, set them all to true
	 			boolean start = false;
	 			for (DrawableObject obj : _myCanvas.getDrawableObjects())
	 			{
	 				if (obj instanceof State)
	 				{
	 					State state = (State)obj;
	 					if (state.getType() == Data.StateType.START || state.getType() == Data.StateType.STARTACCEPT)
	 					{
	 						start = true;
	 					}
	 				}
	 			}
	 			
	 			if (start)
	 			{
	 				_stateObjectPanel.enableStartStates(false);
	 				_myCanvas.enableStartStates(false);
	 				_menu.enableStartStates(false);
	 			}
	 			
	 			else
	 			{
	 				_stateObjectPanel.enableStartStates(true);
	 				_myCanvas.enableStartStates(true);
	 				_menu.enableStartStates(true);
	 			}
	 			
	 			
	 			// repaint in case that some of the states or
	 			// transitions are highlighted 
	 			_myCanvas.repaint();
		
	 		}
	 	});
		
		TypingEvents.addTypingEventListener(new TypingEventListener()
		{
			public void typing(TypingEvent e)
			{
				if (_outputPanel != null)
	 				_outputPanel.setOutputContents("Not Simulated");
				
				_simulator.reset(true);
				// repaint in case that some of the states or 
				// transitions are highlighted 
				_myCanvas.repaint();
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
	     _menu = new Menu(this);
	     _frame.setJMenuBar(_menu.initializeMenuBar());       	     
	     _frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	     _frame.setSize(800, 600);
	     _frame.setVisible(true);
	     _frame.setResizable(false);
	     _frame.addWindowListener(new WindowListener()
	     {
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) 
			{				
				close();	
			}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowOpened(WindowEvent e) {}
	    	 
	     });
	 }  

	// PRE:
	// POST: 
	public JPanel CreateContentPane()
	{
        // We create a bottom JPanel to place everything on.
        _totalGUI = new JPanel(new BorderLayout());        
        
        // Create the simBar.
        _simulationBar = new SimulationPanel(this); 
        _simulationBar.initializeSimBar();
        
        // Create the Output Window
        _outputPanel = new OutputPanel();
        _outputPanel.initializeOutputPanel();
        
        // Create the state object panel
        _stateObjectPanel = new StateObjectPanel(this);
        _stateObjectPanel.initializeStateObjectPanel();

        // Create scrollPane for canvas, 
        _myCanvas = new Canvas();
        _myCanvas.initializeCanvas();        
    	
        _scrollCanvas = new JScrollPane(_myCanvas);        
        _scrollCanvas.setHorizontalScrollBarPolicy(_scrollCanvas.HORIZONTAL_SCROLLBAR_ALWAYS);
        _scrollCanvas.setVerticalScrollBarPolicy(_scrollCanvas.VERTICAL_SCROLLBAR_ALWAYS);

       
        // Add all components to the totalGUI JPanel
        _totalGUI.add(_simulationBar, BorderLayout.NORTH);
        _totalGUI.add(_outputPanel, BorderLayout.SOUTH);
        _totalGUI.add(_stateObjectPanel, BorderLayout.WEST);
        _totalGUI.add(_scrollCanvas, BorderLayout.CENTER); 
        _totalGUI.setOpaque(true);
        
        return _totalGUI;
    }
	
	// PRE: _frame is not null
	// POST: The title of _frame changes to
	// the value of newTitle
	private void setTitle(String newTitle)
	{
		if (_frame != null)
		{
			_frame.setTitle(newTitle);
		}
	}
	
	// PRE:
	// POST: Returns the public directory as determined by the system
	public static String getPublicDirectory()
	{
		return System.getProperty("user.home") + "/Desktop/";				
	}
	
	// PRE: The new option is selected from the menu
	// POST: A new project is opened and all old 
	// information is cleared. The user will be asked
	// if they would like to save their test if there
	// is anything to save
	public void newProject()
	{
		if (_dirty)
		{
			int save = JOptionPane.showConfirmDialog(null, "Would you like to save your automaton before starting a new project?", "Save Before Starting New Project", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (save == JOptionPane.YES_OPTION)
			{
				save();
			}
		}
		
		// Clear the canvas of all drawable objects
		_myCanvas.newProject();
		
		// Next clear all of the contents of the output
		_outputPanel.clearContents();
		
		// clear simulation
		_simulator.reset(false);
		
		_dirty = false;
		setTitle("Automata Creator");
		
	}

	// PRE: The save menu option is selected from the menu
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

				FileOutputStream os = new FileOutputStream(filename);
				XMLEncoder encoder = new XMLEncoder(os);
				encoder.writeObject(_myCanvas);
				encoder.close();
			}
		}

		catch(Exception ex)
		{
		}
		
		finally
		{
			_dirty = false;
			setTitle("AutomataCreator");	
		}
	}
	
	// PRE: The timer expires
	// POST: The current work is saved in case of unexpected
	// termination of automata creator
	public void saveAutomatically()
	{
		String filename = _directory + "/automatacreator.xml";	

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
		
		finally
		{
			_dirty = false;
			setTitle("AutomataCreator");
		}
	}
	
	
	public void saveImage() 
	{
		try
		{	
			String filename = "";	
			
			// file explorer
			JFileChooser file = new JFileChooser();
			
			// set restraints 
		    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("*.jpg", "jpg");
			file.setFileFilter(imageFilter);	
			
			int max_x = _myCanvas.getMaxX(); // get the greatest x position so the entire canvas doesn't have to be copied
			int max_y = _myCanvas.getMaxY(); // get the greatest y position so the entire canvas doesn't have to be copied 

			max_x = max_x + 100;
			max_y = max_y + 100;				
			
			BufferedImage canvasCopy = new BufferedImage(max_x, max_y, BufferedImage.TYPE_INT_RGB); 
			// paint whatever is on the canvas onto the new copy of the canvas to be saved 
			_myCanvas.paint(canvasCopy.getGraphics());
			
			// set the file chooser to have default desktop directory
			File dir = new File(_directory);
			file.setCurrentDirectory(dir);
			
			if (file.showSaveDialog(file.getParent()) == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getAbsoluteFile().toString();
				if (!filename.contains("jpg"))
				{								
					filename = filename + ".jpg";
				}
				
				ImageIO.write(canvasCopy,"jpg", new File(filename));	
			}
		}
		

		catch(Exception ex)
		{
		}
	}

		
	// PRE: An existing file must be chosen to open
	// POST: All objects saved in the XML file will be 
	// re-instantiated so that the workspace looks as it did before the
	// project was closed
	public void openExisting()
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

			if (file.showOpenDialog(file.getParent()) == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getAbsoluteFile().toString();
				
				FileInputStream is = new FileInputStream(filename);
		        XMLDecoder decoder = new XMLDecoder(is);
		        Object obj = decoder.readObject();
		        decoder.close();
		        
		        ArrayList<DrawableObject> objects = ((Canvas)obj).getDrawableObjects();
		        _myCanvas.openProject(objects);
			}

		}

		catch (Exception ex)
		{
		}		
	}	

	// PRE:
	// POST: If dirty we will ask user if they want to
	// save before closing, otherwise just close
	public void close()
	{ 
		int close = -1;
		if (_dirty)
		{
			close = JOptionPane.showConfirmDialog(null, "Would you like to save your automaton before closing?", "Save Before Closing", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (close == JOptionPane.YES_OPTION)
			{
				save();
				// Close Automata Creator 
				System.exit(0);	
			}
			
			else if (close == JOptionPane.NO_OPTION)
			{
				// Close Automata Creator 
				System.exit(0);	
			}			
		}
		
		else
		{
			close = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit Automata Creator", "Exit Automata Creator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (close == JOptionPane.OK_OPTION)
			{
				// Close Automata Creator 
				System.exit(0);	
			}
		}

	}
	
	// PRE:
	// POST:
	public void undo()
	{
		_myCanvas.undo();
	}
	
	// PRE:
	// POST:
	public void redo()
	{
		_myCanvas.redo();
	}
	
	// PRE:
	// POST:
	public void clearCanvas()
	{
		_myCanvas.clearCanvas();
	}
	
	// PRE:
	// POST:
	public void copyCanvas()
	{
		_myCanvas.copyCanvas();
	}
	
	// PRE:
	// POST:
	public void createState(Data.StateType type)
	{
		_myCanvas.createState(type);
	}
	
	// PRE: The play button in the simulaton panel was clicked
	// POST: The automaton will attempt to process the string
	// entered into the tape text field in the output panel 
	// on another thread since there is a lot of processing
	// with updating the different text boxes 
	public void simulate()
	{
		// Create a new swing worker thread 
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
		{
			@Override
			protected Void doInBackground() throws Exception 
			{
				performSimulation();
				return null;
			}	
		};
		
		worker.execute();
		
	}
	
	// PRE: The worker thread has been created and is executed
	// POST: First, we check if there is even a string to process
	// in the tape text area in the output window. If there isn't
	// we set valid to false and output the message to the output window.
	// Otherwise, if there are states and transitions on the canvas we want to 
	// disable everything in the main window so that the user cannot
	// click anything while we are running. Next, we check to see if the automaton
	// has already been validated. If not, we validate in order to see if 
	// we have a valid DFA that can process a string. If it is valid, we simulate.
	// Otherwise, we output the error to the output window. Lastly, we want to 
	// re-enable to window. 
	private void performSimulation()
	{
		boolean validated = true;
		
		ArrayList<DrawableObject> objects = _myCanvas.getDrawableObjects();
		String tape = _outputPanel.getTape();
		
		if (tape == null)
		{
			_outputPanel.setOutputContents("No symbols on tape");
			return;
		}
		
		if (objects != null && tape != null)
		{
			// disable the main window
			_frame.setEnabled(false);
			
			// if the automata is not validated yet
			if (!_simulator.getValidated())
			{			
				_simulator.setStates(objects);
				_simulator.setTransitions(objects);
				_simulator.setTape(_outputPanel.getTape()); 
				
				// Attempt to validate the automaton 
				validated = _simulator.Validate();
				if (!validated)
				{
					// if it is not validated we want to output the error 
					// message to the output window 
					_outputPanel.setOutputContents(_simulator.getErrorString());
				}
			}
			
			// If the automaton was validated, we simulate
			if (validated)
			{	
				_outputPanel.setOutputContents("");
				
				try
				{
					_simulator.beginSimulation();
										
					// for each symbol in the tape we want to simulate 
					for (int i = _simulator.getCurrentSymbolOnTape(); i < _simulator.getTape().length(); i++)
					{
						_simulator.beginStep(true);
						_simulator.nextSymbol();
						_myCanvas.repaintCanvas();
						Thread.sleep(_simulationBar.getDelayTime() * 1000);	
						_simulator.endStep(true);
					}
				}
				
				catch (Exception e)
				{
					
				}
			}
			
			// enable the main window
			_frame.setEnabled(true);				
		}	
	}
	
	
	// PRE: The automaton must have been validated
	// and it must have already been through simulation
	// or the next symbol button must have been pressed
	// POST: The very last symbol that was processed will
	// be re-processed 
	public void previousSymbol()
	{
		// enable the main window
		_frame.setEnabled(true);
		
		_outputPanel.setOutputContents("");
		_simulator.beginStep(false);
		_simulator.previousSymbol();
		_simulator.endStep(false);
	
		// enable the main window
		_frame.setEnabled(true);
		
		_myCanvas.repaintCanvas();
	
	}

	// PRE: The automaton must have been validated
	// and it must have at least 1 symbol to process
	// or the next symbol button must have been pressed
	// POST: The next symbol in the tape that has not
	// yet been processed will be processed 
	public void nextSymbol()
	{
		boolean validated = true;
		
		ArrayList<DrawableObject> objects = _myCanvas.getDrawableObjects();
		String tape = _outputPanel.getTape();
		
		if (tape == null)
		{
			_outputPanel.setOutputContents("No symbols on tape");
			return;
		}
		
		if (objects != null && tape != null)
		{
			// disable the main window
			_frame.setEnabled(false);
			
			if (!_simulator.getValidated())
			{			
				_simulator.setStates(objects);
				_simulator.setTransitions(objects);
				_simulator.setTape(_outputPanel.getTape()); 
				
				validated = _simulator.Validate();
				if (!validated)
				{
					_outputPanel.setOutputContents(_simulator.getErrorString());
				}
			}
			
			if (validated)
			{
				_outputPanel.setOutputContents("");
				_simulator.beginStep(true);
				_simulator.nextSymbol();
				_simulator.endStep(true);
			}
			
			// enable the main window
			_frame.setEnabled(true);
		}

		_myCanvas.repaintCanvas();
	}

}
