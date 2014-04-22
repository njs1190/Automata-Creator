package automataCreator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StateObjectPanel extends JPanel
{
	
	// State object panel items
    private JPanel _stateObjectPanel;
    private JButton _start; 	
	private JButton _startA;
	private JButton _intermediate;
	private JButton _accept;
	
	public StateObjectPanel()
	{
		
	}
	
	public void initializeStateObjectPanel()
	{	
		this.setLayout(new GridLayout(4,1));
		
    	// Creates 4 buttons for the state panel
        _start = new JButton();
        // Start State icon
        ImageIcon startIcon = new ImageIcon("Images/start.gif");
        _start.setIcon(startIcon);
        _start.setFocusable(false); // Turn off highlighting after user click
        _start.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StateEvent event = new StateEvent(this, Data.StateType.START);
	        	StateEvents.sendStateEvent(event);
				
			}
        	
        });
        
    	_startA = new JButton();
    	// Start accept Icon
        ImageIcon startAIcon = new ImageIcon("Images/startA.gif");
        _startA.setIcon(startAIcon);
        _startA.setFocusable(false); // Turn off highlighting after user click
        _startA.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StateEvent event = new StateEvent(this, Data.StateType.STARTACCEPT);
	        	StateEvents.sendStateEvent(event);
				
			}
        	
        });
        
    	_intermediate = new JButton();
    	// Intermediate state Icon
        ImageIcon intermediateIcon = new ImageIcon("Images/intermediate.gif");
        _intermediate.setIcon(intermediateIcon);
        _intermediate.setFocusable(false); // Turn off highlighting after user click
        _intermediate.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StateEvent event = new StateEvent(this, Data.StateType.INTERMEDIATE);
	        	StateEvents.sendStateEvent(event);
				
			}
        	
        });
    	
    	_accept = new JButton();
    	// Accept Icon
        ImageIcon acceptIcon = new ImageIcon("Images/accept.gif");
        _accept.setIcon(acceptIcon);
        _accept.setFocusable(false); // Turn off highlighting after user click
        _accept.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				StateEvent event = new StateEvent(this, Data.StateType.ACCEPT);
	        	StateEvents.sendStateEvent(event);
				
			}
        	
        });
    	
    	this.add(_start);
    	this.add(_startA);
    	this.add(_intermediate);
    	this.add(_accept);
	
		
	}

}
