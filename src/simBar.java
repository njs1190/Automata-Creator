package automataCreator;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class simBar extends JPanel
{
	JPanel _simControl;
	JButton _previousSymbol;
	JButton _startStopSymbol;
	JButton _nextSymbol;
	SpinnerModel _timeSpinner;
	JSpinner _spinner;
	JLabel _timeLabel;
	JPanel _tapePanel;
    JLabel _tapeLabel;
	JTextField _tapeInput;
	
	public simBar()
	{
		
	}	
	
	public void initializeSimBar() 
	{
        _simControl = new JPanel();
        _simControl.setSize(150, 50);
        
        //Creates 3 buttons for the state panel
        _previousSymbol = new JButton("<html><center>Previous<br/>Symbol</center></html>");
        _previousSymbol.setToolTipText("Go to previous symbol on tape Input");
        _previousSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon previousSymbolIcon = new ImageIcon("Images/back.png");
        _previousSymbol.setIcon(previousSymbolIcon);
        
        _startStopSymbol = new JButton("<html><center>Start<br/>Simulation</center></html>");
        _startStopSymbol.setToolTipText("Start auto simulation");
        _startStopSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon start = new ImageIcon("Images/play.png");
        ImageIcon stop = new ImageIcon ("Images/pause.png");
        _startStopSymbol.setIcon(start);
        
        _nextSymbol = new JButton("<html><center>Next<br/>Symbol</center></html>");
        _nextSymbol.setToolTipText("Go to next symbol on tape input");
        _nextSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon nextSymbolIcon = new ImageIcon("Images/next.png");
        _nextSymbol.setIcon(nextSymbolIcon);
        
        _simControl.setBorder(BorderFactory.createEmptyBorder(0,110,0,0));
        
        // Field to specify amount of time transition Graphics are displayed during auto-simulation
        _timeSpinner = new SpinnerNumberModel(1, 1, 10, 1);
        _spinner = new JSpinner(_timeSpinner);
        _spinner.setToolTipText("Set time each symbols transition graphic will be displayed");
        _timeLabel = new JLabel("<html><center>Display<br/>Time</center></html> ");
                
        // Add all created buttons to the simulation control JPanel
        _simControl.add(_previousSymbol);
        _simControl.add(_startStopSymbol);
        _simControl.add(_nextSymbol);
        _simControl.add(_timeLabel);
        _simControl.add(_spinner);
        
        //add simControl panel to simBarPanel
        this.add(_simControl, BorderLayout.LINE_START);
        
        // spacing
        JPanel p = new JPanel();
        p.setBorder(new EmptyBorder(10, 10, 10, 80) );
        this.add(p);
        
        // Field for Tape input Field and Label
        _tapePanel = new JPanel();
        _tapeLabel = new JLabel("Tape Input");
        _tapeInput = new JTextField();
        _tapeInput.setColumns(10);
              
        //tapeInput.setActionCommand(TEXT_ENTERED);
        _tapePanel.add(_tapeLabel);
        _tapePanel.add(_tapeInput);   
        
        //add tape panel to simBar Panel
        //simBarPanel.add(_tapePanel, BorderLayout.AFTER_LINE_ENDS);

        // Add event listeners 
        initializeEvents();
       
    }
	
	// PRE: An object of simBar has been instantiated
	// POST: The string entered in the dialog through insert > string 
	// menu option will be inserted into the tapeInput text field
	// if tapeInput is not null 
	private void initializeEvents()
	{
		// Add custom close event listener for when the string is input
		// through the insert > string menu option. When the 
		// dialog is closed we want to insert the string into
		// the tape input text field.
	 	CloseEvents.addCloseEventListener(new CloseEventListener()
	 	{
	 		public void close(CloseEvent e)
	 		{
	 			if (_tapeInput != null)
	 			{
	 				_tapeInput.setText(e.getText());
	 			}
	 		}
	 	});
	}
	
	
	

}

