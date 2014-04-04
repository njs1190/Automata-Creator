package automataCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class outPanel {
	 
	
	 static JTextArea outputTextArea;
	
	
	public static void buildOutputPanel (JPanel outputPanel) 
	{
        
 
        outputTextArea = new JTextArea(5, 15);
        outputTextArea.setEditable(false);
//        outputTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
    
     outputPanel.add(outputTextArea, BorderLayout.LINE_START);
     
     JPanel stringProcessPanel = new JPanel (new GridLayout(2,3));
     
     JTextArea processedSymbols = new JTextArea(1,6);
     JTextArea currentSymbol = new JTextArea(1,1);
     JTextArea tapeSymbols = new JTextArea(1,6);
    
     processedSymbols.setEditable(false);
     currentSymbol.setEditable(false);
     tapeSymbols.setEditable(false);
     
     
     
     processedSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
     currentSymbol.setBorder(BorderFactory.createLineBorder(Color.black));
     tapeSymbols.setBorder(BorderFactory.createLineBorder(Color.black));
     
     JLabel processedLabel = new JLabel("Processed Symbols");
     JLabel currentLabel = new JLabel("  Current\n Symbol");
     JLabel tapeLabel = new JLabel("  Symbols\n on Tape");
     
     
     stringProcessPanel.add(processedSymbols);
     stringProcessPanel.add(currentSymbol);
     stringProcessPanel.add(tapeSymbols);
     stringProcessPanel.add(processedLabel);
     stringProcessPanel.add(currentLabel);
     stringProcessPanel.add(tapeLabel);
     
     
    outputPanel.add(stringProcessPanel, BorderLayout.EAST);
    

     
     	
     
 
       
    }
	




}
