// SimulationPanel.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to create the simulation panel that is docked to the top
// of the MainFrame underneath the main menu bar. 

package automataCreator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class SimulationPanel extends JPanel 
{
	MainFrame _mainFrame;
	
	// Simulation bar controls
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
	
	public SimulationPanel(MainFrame mainFrame)
	{
		_mainFrame = mainFrame;
	}	
	
	public void initializeSimBar() 
	{
        _simControl = new JPanel();
        _simControl.setSize(150, 50);
        
        //Creates 3 buttons for the state panel
        _previousSymbol = new JButton("<html><center>Previous<br/>Symbol</center></html>");
        _previousSymbol.setToolTipText("Go to previous symbol on tape Input");
        // Initially set to disabled because users can't click previous unless
        // the play or next symbol button has been selected because at least one symbol must be processed
        // before previous symbol can be used
        _previousSymbol.setEnabled(false);         
        _previousSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon previousSymbolIcon = new ImageIcon("back.png");
        _previousSymbol.setIcon(previousSymbolIcon);
        _previousSymbol.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_mainFrame.previousSymbol();				
			}
        	
        });
        
        _startStopSymbol = new JButton("<html><center>Start<br/>Simulation</center></html>");
        _startStopSymbol.setToolTipText("Start auto simulation");
        _startStopSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon start = new ImageIcon("play.png");
        _startStopSymbol.setIcon(start);
        _startStopSymbol.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_mainFrame.simulate();				
			}
        	
        });
        
        _nextSymbol = new JButton("<html><center>Next<br/>Symbol</center></html>");
        _nextSymbol.setToolTipText("Go to next symbol on tape input");
        _nextSymbol.setFocusable(false); // Turn off highlighting after user click
        ImageIcon nextSymbolIcon = new ImageIcon("next.png");
        _nextSymbol.setIcon(nextSymbolIcon);
        _nextSymbol.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				_mainFrame.nextSymbol();				
			}
        	
        });
        
        _simControl.setBorder(BorderFactory.createEmptyBorder(0,110,0,0));
        
        // Field to specify amount of time transition Graphics are displayed during auto-simulation
        _timeSpinner = new SpinnerNumberModel(1, 1, 10, 1);
        _spinner = new JSpinner(_timeSpinner);
        _spinner.setToolTipText("Set the time (1- 10 seconds) each symbol transition will be displayed");
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
              
        _tapePanel.add(_tapeLabel);
        _tapePanel.add(_tapeInput);   

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
	 				_tapeInput.setText(e._text);
	 			}
	 		}
	 	});
	}
	
	public int getDelayTime()
	{
		return (Integer) _spinner.getValue();
	}
	
	// PRE: _previousSymbol is not null
	// POST: _previousSymbol is enabled or disabled 
	public void enablePreviousButton(boolean enable)
	{
		if (_previousSymbol != null)
		{
			_previousSymbol.setEnabled(enable);
		}
	}
	
	// PRE: _nextSymbol is not null
	// POST: _nextSymbol is enabled or disabled 
	public void enableNextButton(boolean enable)
	{
		if (_nextSymbol != null)
		{
			_nextSymbol.setEnabled(enable);
		}
	}
	

}

