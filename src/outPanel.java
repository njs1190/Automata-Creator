// Author: Jidaeno
// Class: OutPanel.java
// Purpose: Creates the output panel that is displayed on the bottom of the 
// main window. The output panel consists of the output window which is responsible
// for providing the user feedback, and the the text fields that will display the
// the current symbol being simulated, the symbols that have been simulated, and the
// remaining symbols to be simulated

package automataCreator;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class outPanel extends JPanel
{	
	private JPanel _outTextAreaPanel;
	private JTextArea _outputTextArea;
	private JPanel _stringProcessPanel;
	private JTextArea _processedSymbols;
    private JTextArea _tapeSymbols;
    private JTextArea _currentSymbol;
    private JLabel _processedLabel;
    private JLabel _currentLabel;
    private JLabel _tapeLabel;
    private JPanel _currentSymbolPanel;
    private JPanel _processedSymbolsPanel;
    private JPanel _tapeSymbolsPanel;
	
	public outPanel()
	{

	}	
	
	// PRE: Method has been called from the mainframe class
	// POST: All components for the output panel have been created and
	// are visible
	public void initializeOutputPanel () 
	{
		// Initiate Feedback/Error Window
		
		_outTextAreaPanel = new JPanel(new GridLayout(1,1));
		
         _outputTextArea = new JTextArea(5, 20);
         _outputTextArea.setEditable(false);
         _outputTextArea.setToolTipText("Feedback/Error Window");
         _outputTextArea.setText("Not Simulated");
         _outputTextArea.setBorder(BorderFactory.createLoweredBevelBorder());                 
         
        _outTextAreaPanel.add(_outputTextArea); 
        _outTextAreaPanel.setBorder(BorderFactory.createEmptyBorder(0, 95, 0, 40));
       
	    
	     //Initiate text area for already processed symbols,the current symbol, and the symbols still on the tape
         _stringProcessPanel = new JPanel (new GridLayout(1, 3, 20, 0));
        
         _processedSymbols = new JTextArea();
         _processedSymbols.setEditable(false); // make them not editable
         _processedSymbols.setToolTipText("Symbols in the string that have been accepted by the automaton");
         _processedSymbols.setBorder(BorderFactory.createLoweredBevelBorder());
         
	     _tapeSymbols = new JTextArea();
	     //_tapeSymbols.setEditable(false); // make them not editable
	     _tapeSymbols.setToolTipText("Symbols remaining to be simulated by the autamaton");
	     _tapeSymbols.setBorder(BorderFactory.createLoweredBevelBorder());
	     	     
	     _currentSymbol = new JTextArea();
	     _currentSymbol.setEditable(false); // make them not editable
	     _currentSymbol.setToolTipText("Symbol that is currently being simulated by the automaton");
	     _currentSymbol.setBorder(BorderFactory.createLoweredBevelBorder());
	     
	     // add labels for text areas
	     _processedLabel = new JLabel("Processed Symbols");
	     _currentLabel = new JLabel("Current Symbol");
	     _tapeLabel = new JLabel("Symbols on Tape");
	  	    
	     _currentSymbolPanel = new JPanel(new GridLayout(2,1));
	     _currentSymbolPanel.add(_currentSymbol);
	     _currentSymbolPanel.add(_currentLabel);
	     
	     _processedSymbolsPanel = new JPanel(new GridLayout(2,1));
	     _processedSymbolsPanel.add(_processedSymbols);
	     _processedSymbolsPanel.add(_processedLabel);
	     
	     _tapeSymbolsPanel = new JPanel(new GridLayout(2,1));
	     _tapeSymbolsPanel.add(_tapeSymbols);
	     _tapeSymbolsPanel.add(_tapeLabel);

	     // add labels and text boxes to stringProcessPanel	    
	     _stringProcessPanel.add(_processedSymbolsPanel);
	     _stringProcessPanel.add(_currentSymbolPanel);
	     _stringProcessPanel.add(_tapeSymbolsPanel); 
	     _stringProcessPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
	    
	    this.add(_outTextAreaPanel, BorderLayout.WEST);
	    this.add(_stringProcessPanel, BorderLayout.EAST);

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
	 			if (_tapeSymbols != null)
	 			{
	 				_tapeSymbols.setText(e.getText());
	 			}
	 		}
	 	});
	}
	
}


