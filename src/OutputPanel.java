// OutputPanel.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to created the output panel that is added to bottom of MainFrame. 

package automataCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.EventListenerList;

public class OutputPanel extends JPanel 
{	
	private JPanel _outTextAreaPanel;
	private JTextArea _outputTextArea;
	private JScrollPane _outputScroll;
	private JPanel _stringProcessPanel;
	private JTextArea _processedSymbols;
	private JScrollPane _processedScroll;
    private JTextArea _tapeSymbols;
    private JScrollPane _tapeScroll;
    private JTextArea _currentSymbol;
    private JLabel _processedLabel;
    private JLabel _currentLabel;
    private JLabel _tapeLabel;
    private JPanel _currentSymbolPanel;
    private JPanel _processedSymbolsPanel;
    private JPanel _tapeSymbolsPanel;

	public OutputPanel()
	{

	}	
	
	// PRE: Method has been called from the mainframe class
	// POST: All components for the output panel have been created and
	// are visible
	public void initializeOutputPanel()
	{
		// Initiate Feedback/Error Window
		
		_outTextAreaPanel = new JPanel(new GridLayout(1,1));
		
         _outputTextArea = new JTextArea(5, 19);
         _outputTextArea.setEditable(false);
         _outputTextArea.setToolTipText("Feedback/Error Window");
         _outputTextArea.setText("Not Simulated");  
         _outputTextArea.setLineWrap(true);
         _outputTextArea.setWrapStyleWord(true);
         _outputScroll = new JScrollPane(_outputTextArea);
         _outputScroll.setVerticalScrollBarPolicy(_outputScroll.VERTICAL_SCROLLBAR_AS_NEEDED);
         _outputScroll.setPreferredSize(new Dimension(230, 100));
         _outputScroll.setBorder(BorderFactory.createLoweredBevelBorder());      
                 
        _outTextAreaPanel.add(_outputScroll);
        _outTextAreaPanel.setBorder(BorderFactory.createEmptyBorder(0, 90, 0, 40));       
	    
	     //Initiate text area for already processed symbols,the current symbol, and the symbols still on the tape
         _stringProcessPanel = new JPanel (new GridLayout(1, 3, 20, 0));
        
         _processedSymbols = new JTextArea();
         _processedSymbols.setEditable(false); // make them not editable
         _processedSymbols.setToolTipText("Symbols in the string that have been accepted by the automaton");
         _processedScroll = new JScrollPane(_processedSymbols);
         _processedScroll.setHorizontalScrollBarPolicy(_processedScroll.HORIZONTAL_SCROLLBAR_NEVER);
         _processedScroll.setPreferredSize(new Dimension(20, 10));
         _processedScroll.setBorder(BorderFactory.createLoweredBevelBorder());
         
         _currentSymbol = new JTextArea();
	     _currentSymbol.setEditable(false); // make them not editable
	     _currentSymbol.setToolTipText("Symbol that is currently being simulated by the automaton");
	     _currentSymbol.setBorder(BorderFactory.createLoweredBevelBorder());
         
	     _tapeSymbols = new JTextArea();
	     _tapeSymbols.setForeground(Color.GRAY);
	     _tapeSymbols.setText("Enter String...");
	     _tapeSymbols.setToolTipText("Symbols remaining to be simulated by the autamaton");
	     _tapeSymbols.addKeyListener(new KeyListener()
	     {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (_tapeSymbols.getText().contains("Enter String..."))
				{
					_tapeSymbols.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) 
			{
				if (_tapeSymbols.getText().isEmpty())
				{
					_tapeSymbols.setForeground(Color.GRAY);
					_tapeSymbols.setText("Enter String...");
				}
				
				if (e.getKeyChar() == '0' || e.getKeyChar() == '1')
				{
					_tapeSymbols.setForeground(Color.BLACK);
					_tapeSymbols.setCaretPosition(_tapeSymbols.getText().length());
				}
				
				// if the character is anywhere in the ascii table between
				// these values which includes, but is not limited to
				// a-z, A-Z, 2-9, space, and additional characters that
				// are not part of the binary alphabet 
				else if (((int)e.getKeyChar()) > 31 && ((int)e.getKeyChar()) < 127)
				{
					String currentText = _tapeSymbols.getText();
					String newText = currentText.substring(0, currentText.length() - 1);
					
					if (newText.isEmpty())
					{
						_tapeSymbols.setForeground(Color.GRAY);
						_tapeSymbols.setText("Enter String...");
					}
					
					else
					{
						_tapeSymbols.setForeground(Color.BLACK);
						_tapeSymbols.setText(newText);
					}					
				}

			}

			@Override
			public void keyTyped(KeyEvent e) 
			{				
				 TypingEvent event = new TypingEvent(this); 
	        	 TypingEvents.sendTypingEvent(event);
			}
	    	 
	     });     
	     _tapeScroll = new JScrollPane(_tapeSymbols);
	     _tapeScroll.setHorizontalScrollBarPolicy(_tapeScroll.HORIZONTAL_SCROLLBAR_NEVER);
	     _tapeScroll.setPreferredSize(new Dimension(20, 10));
	     _tapeScroll.setBorder(BorderFactory.createLoweredBevelBorder());
	   	     
	     // add labels for text areas
	     _processedLabel = new JLabel("Processed Symbols");
	     _currentLabel = new JLabel("Current Symbol");
	     _tapeLabel = new JLabel("Symbols on Tape");
	  	    
	     _currentSymbolPanel = new JPanel(new GridLayout(2,1));
	     _currentSymbolPanel.add(_currentSymbol);
	     _currentSymbolPanel.add(_currentLabel);
	     
	     _processedSymbolsPanel = new JPanel(new GridLayout(2,1));
	     _processedSymbolsPanel.add(_processedScroll);
	     _processedSymbolsPanel.add(_processedLabel);
	     
	     _tapeSymbolsPanel = new JPanel(new GridLayout(2,1));
	     _tapeSymbolsPanel.add(_tapeScroll);
	     _tapeSymbolsPanel.add(_tapeLabel);

	     // add labels and text boxes to stringProcessPanel	    
	     _stringProcessPanel.add(_processedSymbolsPanel);
	     _stringProcessPanel.add(_currentSymbolPanel);
	     _stringProcessPanel.add(_tapeSymbolsPanel); 
	     _stringProcessPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
	    
	    this.add(_outTextAreaPanel, BorderLayout.WEST);
	    this.add(_stringProcessPanel, BorderLayout.EAST);

	    initializeListeners();
	    
    }
	
	// PRE: An object of simBar has been instantiated
	// POST: The string entered in the dialog through insert > string 
	// menu option will be inserted into the tapeInput text field
	// if tapeInput is not null 
	private void initializeListeners()
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
	 				if (e._text.isEmpty())
	 				{
	 					_tapeSymbols.setForeground(Color.GRAY);
		 				_tapeSymbols.setText("Enter String...");
	 				}
	 				else
	 				{
		 				_tapeSymbols.setForeground(Color.BLACK);
		 				_tapeSymbols.setText(e._text);
		 				
		 				// Although, we are not actually typing anything
		 				// we are completely resetting the text field to be
		 				// what was entered in the string input window and
		 				// we want to send an event for this 
		 				TypingEvent event = new TypingEvent(this); 
		 	        	TypingEvents.sendTypingEvent(event);
	 				}
	 			}
	 		}
	 	});
	}
	
	// PRE: The text area _tapeSymbols should be initialized
	// POST: The text entered in this area will be returned
	// to the caller
	public String getTape()
	{
		if (_tapeSymbols != null)
		{
			String tape = _tapeSymbols.getText();
			
			if (!tape.equals("Enter String..."))
			{
				return _tapeSymbols.getText();
			}
			
			else
			{
				return null;
			}
		}
		
		else
		{
			return null;
		}
	}
	
	// PRE: The text area _tapeSymbols should be initialized
	// POST: The contents of this area will be set to the passed
	// string
	public void setTape(String tape)
	{
		if (_tapeSymbols != null)
		{
			if (tape == "")
			{
				_tapeSymbols.setForeground(Color.GRAY);
				_tapeSymbols.setText("Enter String...");
			}
			
			else
			{
				_tapeSymbols.setText(tape);
			}
		}
	}
	
	// PRE: The text area _currentSymbol should be initialized
	// POST: The contents of this area will be returned
	public String getCurrentSymbol()
	{
		if (_currentSymbol != null)
		{
			return _currentSymbol.getText();
		}
		
		else
		{		
			return null;
		}
	}
	
	// PRE: The text area _currentSymbol should be initialized
	// POST: The contents of this area will be set to the passed
	// string
	public void setCurrentSymbol(String symbol)
	{
		if (_currentSymbol != null)
		{
			_currentSymbol.setText(symbol);
		}
	}
	
	// PRE: The text area _tapeSymbols should be initialized
	// POST: The contents of this area will be returned
	public String getProcessedSymbols()
	{
		if (_processedSymbols != null)
		{
			return _processedSymbols.getText();
		}
		
		else
		{
			return null;
		}
	}
	
	// PRE: The text area _tapeSymbols should be initialized
	// POST: The contents of this area will be set to the passed
	// string
	public void setProcessedSymbols(String symbols)
	{
		if (_processedSymbols != null)
		{
			_processedSymbols.setText(symbols);
			_processedSymbols.setCaretPosition(_processedSymbols.getText().length());
		}
	}
	
	
	// PRE:
	// POST: All text fields are emptied
	public void clearContents()
	{
		_outputTextArea.setText("Not Simulated");
		
		_processedSymbols.setText("");
		_currentSymbol.setText("");
		
		_tapeSymbols.setForeground(Color.GRAY);
		_tapeSymbols.setText("Enter String...");
	
	}
	
	// PRE:
	// POST: The output text area is filled with the 
	// message
	public void setOutputContents(String message)
	{
		_outputTextArea.setText(message);
	}
	
	// PRE:
	// POST: The string in the output window will be returned 
	public String getOutputContents()
	{
		return _outputTextArea.getText();
    }
}
    

//Create a typing event 
@SuppressWarnings("serial")
class TypingEvent extends EventObject
{
	public TypingEvent(Object source)
	{
		super(source);
	}
}

interface TypingEventListener extends EventListener
{
	public void typing(TypingEvent e);
}

class TypingEvents
{
 static EventListenerList listenerList = new EventListenerList();
	
 static void addTypingEventListener(TypingEventListener l)
	{
		listenerList.add(TypingEventListener.class, l);
	}
	
	static void removeTypingEventListener(TypingEventListener l)
	{
		listenerList.remove(TypingEventListener.class, l);
	}
	
	static void sendTypingEvent(TypingEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == TypingEventListener.class)
			{
				((TypingEventListener) listeners[i+1]).typing(e);
			}
		}
	}
}


