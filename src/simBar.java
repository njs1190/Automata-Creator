package automataCreator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class simBar implements ActionListener 
{
	public simBar()
	{

	}	

	public void BuildSimBar(JPanel simBarPanel) 
	{
       
 
        // Panel for string Simulation control componenets
        JPanel simControl = new JPanel();
        simControl.setSize(150, 50);


    	//Creates 3 buttons for the state panel
        JButton previousSymbol = new JButton("<html><center>Previous<br/>Symbol</center></html>");    	
        JButton startStop = new JButton("<html><center>Start<br/>Simulation</center></html>");
        JButton nextSymbol = new JButton("<html><center>Next<br/>Symbol</center></html>");
        
        
        
        //turn off highlighting after user click
        previousSymbol.setFocusable(false);
        startStop.setFocusable(false);
        nextSymbol.setFocusable(false);
        
        //Add stateIcons to stateObject Jlabels
    	//back icon
        ImageIcon previousSymbolIcon = new ImageIcon("Images/back.png");
        previousSymbol.setIcon(previousSymbolIcon); 														
        
        simControl.setBorder(BorderFactory.createEmptyBorder(0,110,0,0));
        
        
        
        
        // playIcon
        ImageIcon start = new ImageIcon("Images/play.png");						
        ImageIcon stop = new ImageIcon ("Images/pause.png");   
        startStop.setIcon(start); 						        
       
        // next Icon
        ImageIcon nextSymbolIcon = new ImageIcon("Images/next.png");
        nextSymbol.setIcon(nextSymbolIcon); 
       
        
        
        previousSymbol.setToolTipText("Go to previous symbol on tape Input");
        startStop.setToolTipText("Start auto simulation");
        nextSymbol.setToolTipText("Go to next symbol on tape input");
        
        
        // Field to specify amount of time transition Graphics are displayed during auto-simulation
        SpinnerModel timeSpinner = new SpinnerNumberModel(1, 1, 10, 1);     
        JSpinner spinner = new JSpinner(timeSpinner);
        JLabel timeLabel = new JLabel("<html><center>Display<br/>Time</center></html> ");  
        timeLabel.setToolTipText("Set time each symbols transition graphic will be displayed");
        spinner.setToolTipText("Set time each symbols transition graphic will be displayed");    
        

        simControl.add(previousSymbol);
    	simControl.add(startStop);
    	simControl.add(nextSymbol);
    	simControl.add(timeLabel);
        simControl.add(spinner);
    	
   
        
        
        //add simControl panel to simBarPanel
        simBarPanel.add(simControl,BorderLayout.LINE_START);
    	
        
      
     
       
       
       
      
       
       // spacing
       JPanel p = new JPanel();
       p.setBorder(new EmptyBorder(10, 10, 10, 80) );
       simBarPanel.add(p);
       
       
    
       
       // Field for Tape input Field and Label
       JPanel tapePanel = new JPanel();
        JLabel tapeLabel = new JLabel("Tape Input");
        JTextField tapeInput = new JTextField();
        tapeInput.setColumns(10);
       
     
        //tapeInput.setActionCommand(TEXT_ENTERED);
        tapePanel.add(tapeLabel);
        tapePanel.add(tapeInput);
        
        
        //add tape panel to simBar Panel
        simBarPanel.add(tapePanel,BorderLayout.AFTER_LINE_ENDS);
 
       
    }

		
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	   

	
	


}
