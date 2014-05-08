// StateObjectPanel.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to create the panel that is docked to the
// left-hand side of the MainFrame. The panel provides buttons that can be clicked
// to add states to the canvas. 

package automataCreator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StateObjectPanel extends JPanel
{		
	private MainFrame _mainFrame;
	
	// State object panel items
    private JPanel _stateObjectPanel;
    private JButton _start; 	
	private JButton _startA;
	private JButton _intermediate;
	private JButton _accept;
	
	public StateObjectPanel(MainFrame mainFrame)
	{
		_mainFrame = mainFrame;
	}
	
	public void initializeStateObjectPanel()
	{	
		this.setLayout(new GridLayout(4,1));
		
    	// Creates 4 buttons for the state panel
        _start = new JButton();
        // Start State icon
        ImageIcon startIcon = new ImageIcon("start.gif");
        _start.setIcon(startIcon);
        _start.setToolTipText("Add start state to the canvas");
        _start.setFocusable(false); // Turn off highlighting after user click
        _start.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_mainFrame.createState(Data.StateType.START);				
			}
        	
        });
        
    	_startA = new JButton();
    	// Start accept Icon
        ImageIcon startAIcon = new ImageIcon("startA.gif");
        _startA.setIcon(startAIcon);
        _startA.setToolTipText("Add start accept state to the canvas");
        _startA.setFocusable(false); // Turn off highlighting after user click
        _startA.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_mainFrame.createState(Data.StateType.STARTACCEPT);
				
			}
        	
        });
        
    	_intermediate = new JButton();
    	// Intermediate state Icon
        ImageIcon intermediateIcon = new ImageIcon("intermediate.gif");
        _intermediate.setIcon(intermediateIcon);
        _intermediate.setToolTipText("Add intermediate state to the canvas");
        _intermediate.setFocusable(false); // Turn off highlighting after user click
        _intermediate.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_mainFrame.createState(Data.StateType.INTERMEDIATE);				
			}
        	
        });
    	
    	_accept = new JButton();
    	// Accept Icon
        ImageIcon acceptIcon = new ImageIcon("accept.gif");
        _accept.setIcon(acceptIcon);
        _accept.setToolTipText("Add final state to the canvas");
        _accept.setFocusable(false); // Turn off highlighting after user click
        _accept.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_mainFrame.createState(Data.StateType.ACCEPT);
				
			}
        	
        });
    	
    	this.add(_start);
    	this.add(_startA);
    	this.add(_intermediate);
    	this.add(_accept);
		
	}
	
}
