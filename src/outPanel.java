package automataCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class outPanel 
{	
	static JTextArea outputTextArea;
	
	public outPanel()
	{
		
	}	
	
	public void BuildOutputPanel (JPanel outputPanel) 
	{
        
		 // Initiate Feedback/Error Window
         outputTextArea = new JTextArea(5, 15);
         outputTextArea.setEditable(false);
         outputTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
    
	     outputPanel.add(outputTextArea, BorderLayout.LINE_START);
	     
	     JPanel stringProcessPanel = new JPanel (new GridLayout(2,3,20,0));
	     
	     //Initiate text area for already processed symbols,the current symbol, and the symbols still on the tape
	     JTextArea processedSymbols = new JTextArea(1,6);
	     JTextArea tapeSymbols = new JTextArea(1,6);
	     // make them not editable 
	     processedSymbols.setEditable(false);
	     
	     tapeSymbols.setEditable(false);
	     
	     JPanel currentPanel = new JPanel(new GridLayout(1,3));
	     JTextArea currentSymbol = new JTextArea(1,1);
	     currentSymbol.setEditable(false);
	     
	     JPanel currentSpacer = new JPanel();
	     currentSpacer.setBorder(new EmptyBorder(10, 10, 10, 7) );
	     JPanel currentSpacer2 = new JPanel();
	     currentSpacer.setBorder(new EmptyBorder(10, 10, 10, 7) );
	    
	     currentPanel.add(currentSpacer);
	     currentPanel.add(currentSymbol);
	     currentPanel.add(currentSpacer2);
	     
	    
	     currentSymbol.setSize(6,5);
	     //add Borders to string tracker text areas
	     processedSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
	     currentSymbol.setBorder(BorderFactory.createLineBorder(Color.black));
	     tapeSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
	     //add labels for text areas
	     JLabel processedLabel = new JLabel("Processed Symbols");
	     JLabel currentLabel = new JLabel(" Current Symbol");
	     JLabel tapeLabel = new JLabel(" Symbols on Tape");
	     
	     
	     //  padding space between text boxes
	     JPanel spacer = new JPanel();
	     spacer.setBorder(new EmptyBorder(10, 10, 10, 75) );
	 
	     // add labels and textboxes to stringProcessPanel
	     stringProcessPanel.add(processedSymbols);
	     stringProcessPanel.add(currentPanel);
	     stringProcessPanel.add(tapeSymbols);
	     stringProcessPanel.add(processedLabel);
	     stringProcessPanel.add(currentLabel);
	     stringProcessPanel.add(tapeLabel);
	     
	
	    outputPanel.add(spacer);
	    outputPanel.add(stringProcessPanel, BorderLayout.EAST);
	    
    }
	
}
