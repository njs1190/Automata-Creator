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
         
        JPanel outTextAreaPanel = new JPanel(new GridLayout(1,1));
         
         
        outTextAreaPanel.add(outputTextArea);
        outputPanel.add(outTextAreaPanel, BorderLayout.WEST);
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10,110,10,0));
	     
	     
	     
	     
	     
	     JPanel stringProcessPanel = new JPanel (new GridLayout(1,3,20,0));

	     //Initiate text area for already processed symbols,the current symbol, and the symbols still on the tape
	     JTextArea processedSymbols = new JTextArea(1,6);
	     JTextArea tapeSymbols = new JTextArea(1,6);
	     JTextArea currentSymbol = new JTextArea();
	     currentSymbol.setSize(5, 6);
	     
	     
	     
	     
	     
	     // make them not editable 
	     processedSymbols.setEditable(false);
	     tapeSymbols.setEditable(false);
	     currentSymbol.setEditable(false);
	   
	     
	     //add labels for text areas
	     JLabel processedLabel = new JLabel("Processed Symbols");
	     JLabel currentLabel = new JLabel("<html>   Current<br/>Symbol</html> ");
	     JLabel tapeLabel = new JLabel(" Symbols on Tape");
	 
	     
	     
	     
	     //add Borders to string tracker text areas
	     processedSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
	     currentSymbol.setBorder(BorderFactory.createLineBorder(Color.black));
	     tapeSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
	    
	     
	    
	    
	     JPanel currentSymbolPanel = new JPanel(new GridLayout(2,1));
	     currentSymbolPanel.add(currentSymbol);
	     currentSymbolPanel.add(currentLabel);
	     currentSymbolPanel.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
	     
	    
	     
	     JPanel processedSymbolsPanel = new JPanel(new GridLayout(2,1));
	     processedSymbolsPanel.add(processedSymbols);
	     processedSymbolsPanel.add(processedLabel);
	     
	     JPanel tapeSymbolsPanel = new JPanel(new GridLayout(2,1));
	     tapeSymbolsPanel.add(tapeSymbols);
	     tapeSymbolsPanel.add(tapeLabel);
	     
	     
	     
	     // add labels and textboxes to stringProcessPanel
	    
	     stringProcessPanel.add(processedSymbolsPanel);
	     stringProcessPanel.add(currentSymbolPanel);
	     stringProcessPanel.add(tapeSymbolsPanel);
	     
	     
	     
	     stringProcessPanel.setBorder(BorderFactory.createEmptyBorder(25,20,0,30));

	    
	    outputPanel.add(stringProcessPanel, BorderLayout.EAST);

    }

}
