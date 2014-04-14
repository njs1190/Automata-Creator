package automataCreator;

// Java classes
import javax.swing.*;

import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;


// Project classes
import automataCreator.simBar;
import automataCreator.menu;
import automataCreator.outPanel;
import automataCreator.statePanel;
import automataCreator.myCanvas;

public class MainFrame 
{ 
	 private static MainFrame _mainFrame;
	 private menu _menu; 
	 private statePanel _statePanel;
	 private simBar _simulationBar;
	 private outPanel _outputPanel;
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
	             _mainFrame.createAndShowGUI();
	             _mainFrame.Initalize();
	         }
	     });
	 }

	 private void Initalize()
	 {
		 // Set dirty bit
		 _dirty = true;		 

		 // Begin the automatic save timer
		 //_timer = new SaveTimer();
		 //_timer.StartTimer();	


	 }

	 private void createAndShowGUI() 
	 {		    
	     // Create and set up the content pane.
	     MainFrame frameTemplate = new MainFrame();

	     // Create and set up the frame. 
	     JFrame frame = new JFrame("Automata Creator");
	     frame.setContentPane(frameTemplate.CreateContentPane());

	     // Add menu to frame
	     _menu = new menu(this);
	     frame.setJMenuBar(_menu.createMenuBar());

	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(800, 600);
	     frame.setVisible(true);
	     frame.setResizable(true);
	 }  

	public JPanel CreateContentPane()
	{
        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel(new BorderLayout());        
        
        // Create the simBar.
        JPanel simBarPanel = new JPanel();
        _simulationBar = new simBar();
        _simulationBar.BuildSimBar(simBarPanel);      
       
        /*  Dae - I commented this out to add it from canvas instead
        // Create the State Object Window
        JPanel stateObjectPanel = new JPanel(new GridLayout(4,1));
        stateObjectPanel.setBackground(Color.GRAY);
        _statePanel = new statePanel();
        _statePanel.BuildObjectPanel(stateObjectPanel);
        */
        
        // Create the Output Window
        JPanel outputPanel = new JPanel();
        _outputPanel = new outPanel();
        _outputPanel.BuildOutputPanel(outputPanel);

       // Create scrollPane for canvas, 
        JPanel canvasPanel = new JPanel(new BorderLayout());
        canvasPanel.setBackground(Color.WHITE);
        _myCanvas = new myCanvas();
        _myCanvas.AddCanvas(canvasPanel);
       
        totalGUI.add(simBarPanel, BorderLayout.NORTH);
        //totalGUI.add(stateObjectPanel, BorderLayout.WEST);  //commented out the statePanel
        totalGUI.add(outputPanel, BorderLayout.SOUTH);
        totalGUI.add(canvasPanel, BorderLayout.CENTER);    

        totalGUI.setOpaque(true);
        return totalGUI;
    }	

	// PRE: One of the insert state options is chosen
	// POST: Sends command to myCanvas to create desired state object
	public void createState(int stateChoice ){
		_myCanvas.createStates(stateChoice);
	}
	
	// PRE: The filename chosen for this project must be chosen
	// POST: All of the project details will be saved into an XML 
	// file so that the project may be opened and worked on at another 
	// time
	public void Save()
	{
		String filename = "";		

		try
		{	
			// file explorer
			JFileChooser file = new JFileChooser();
			int option = file.showSaveDialog(file.getParent());
			if(option == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getName();
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

	// PRE: An existing file must be chosen to open
	// POST: All objects saved in the XML file will be 
	// re-instantiated so that the workspace looks as it did before the
	// project was closed
	public Object OpenExisting()
	{
		String filename = "";

		try
		{
			// file explorer
			JFileChooser file = new JFileChooser();
			int option = file.showOpenDialog(file.getParent());
			if(option == JFileChooser.APPROVE_OPTION) 
			{
				filename = file.getSelectedFile().getName();
			}

			FileInputStream is = new FileInputStream(filename);
	        XMLDecoder decoder = new XMLDecoder(is);
	        Object obj = decoder.readObject();
	        decoder.close();
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
	public void Close()
	{ 
		if (_dirty)
		{
			int save = JOptionPane.showConfirmDialog(null, "Would you like to save your automaton before closing?", "Save Before Closing", JOptionPane.YES_NO_OPTION);
			if (save == JOptionPane.YES_OPTION)
			{
				_mainFrame.Save();
			}
		}

		// Close Automata Creator 
		System.exit(0);		
	}
}
