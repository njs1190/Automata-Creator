// Author: Jidaeno
// Class: Menu.java
// Purpose: Creates the menu bar that is displayed in the main frame 
// including menu items such as file, edit, insert, help

package automataCreator;

import java.awt.event.*;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.EventListenerList;


public class menu 
{	
	private MainFrame _mainFrame;
	private StringInput _input;
	private JOptionPane _aboutBox;

	public menu(MainFrame mainFrame)
	{
		_mainFrame = mainFrame;
	}

	public JMenuBar initializeMenuBar()
    {
        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //Add a JMenu
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu insert = new JMenu("Insert");
        JMenu help = new JMenu("Help"); 
        
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(insert);
        menuBar.add(help);
        
        // UI manager to get icons for menu items
        UIDefaults defaults = UIManager.getDefaults( );
        
        // Start build File menu
        
        // Add new menu item and action listener    
        JMenuItem newAutomata = new JMenuItem("New");
        Icon newIcon = defaults.getIcon( "FileView.fileIcon" );             
        newAutomata.setIcon(newIcon);
        
        // Parent save menu 
        JMenu save = new JMenu("Save"); 
        ImageIcon saveIcon = new ImageIcon("Images/save.png");
        save.setIcon(saveIcon); 
        
        // Add save file menu items and their corresponding action listeners
        JMenuItem fileSave = new JMenuItem("Automota Creator File");
        fileSave.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.save();
          }
        	
        });
        
        JMenuItem imageSave = new JMenuItem("Image");
        
        // Add import file menu item and its corresponding action listener
        JMenuItem importFile = new JMenuItem("Import");
        Icon importIcon = defaults.getIcon( "FileView.directoryIcon");  
        importFile.setIcon(importIcon);
        importFile.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.openExisting();
          }
        	
        });
        
        // Add close file menu item and its corresponding action listener
        JMenuItem close = new JMenuItem("Close");   
        close.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.close();
          }
        	
        });
             
        // Add all created sub menu items to the File menu
        file.add(newAutomata);
        file.addSeparator();
        file.add(importFile);
        file.addSeparator();
        file.add(save);
        save.add(fileSave);
        save.add(imageSave);
        file.addSeparator();
        file.add(close); 
        
        // end build File menu
        
        //Build Edit Menu
        
        // Add undo edit menu item and action listener
        JMenuItem undo = new JMenuItem("Undo");
        ImageIcon undoIcon = new ImageIcon("Images/undo.png");            
        undo.setIcon(undoIcon);
        
        // Add re-do edit menu item and action listener
        JMenuItem redo = new JMenuItem("Redo");
        ImageIcon redoIcon = new ImageIcon("Images/redo.png");            
        redo.setIcon(redoIcon);
        
        // Add copy edit menu item and action listener
        JMenuItem copyCanvas = new JMenuItem("Copy Canvas");
        
        // Add clear edit menu item and action listener
        JMenuItem clearCanvas = new JMenuItem("Clear Canvas");
        clearCanvas.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  ClearCanvasEvent event = new ClearCanvasEvent(this);
        	  ClearCanvasEvents.sendClearCanvasEvent(event);  	          	  
          }
          
        });
        
        // Add all created sub menu items to edit menu
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(copyCanvas);
        edit.addSeparator();
        edit.add(clearCanvas); 
        
        // End build Edit menu
        
        // Build Insert menu 
        
        // Add state insert menu item and action listener
        JMenu state = new JMenu("State");
        
        // Build State sub menu options
        
        // Add start state sub menu item and action listener 
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {       	  
        	  StateEvent event = new StateEvent(this, Data.StateType.START);
        	  StateEvents.sendStateEvent(event);
          }   
          
        });
        
        // Add start/accept state sub menu item and action listener 
        JMenuItem startAccept = new JMenuItem("Start Accept");
        startAccept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  StateEvent event = new StateEvent(this, Data.StateType.STARTACCEPT); 
        	  StateEvents.sendStateEvent(event);
          }        	
        });
        
        // Add intermediate state sub menu item and action listener 
        JMenuItem intermediate = new JMenuItem("Intermediate");
        intermediate.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	 StateEvent event = new StateEvent(this, Data.StateType.INTERMEDIATE);  
        	 StateEvents.sendStateEvent(event);
          }
          
        });
        
        // Add accept state sub menu item and action listener 
        JMenuItem accept = new JMenuItem("Accept");
        accept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  StateEvent event = new StateEvent(this, Data.StateType.ACCEPT);  
        	  StateEvents.sendStateEvent(event);
          }        	
        });
        
        // Add transition insert menu item and action listener
        JMenuItem transition = new JMenuItem("Transition");
        
        // Add string insert menu item and action listener
        JMenuItem string = new JMenuItem("String");
        string.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _input = new StringInput();
        	  _input.create();  	          	  
          }
          
        });
        
        // Add all created sub menu items to insert menu
        insert.add(state);
        state.add(start);
        state.add(startAccept);
        state.add(intermediate);
        state.add(accept);
        insert.addSeparator();  
        insert.add(transition);
        insert.addSeparator();
        insert.add(string);
        
        // End build Insert menu
        
        // Build Help Menu
        
        // Add about help menu item and action listener
        JMenuItem about = new JMenuItem("About"); 
        ImageIcon aboutIcon = new ImageIcon("Images/information.png");            
        about.setIcon(aboutIcon);
        about.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  
        	  
        	  JOptionPane.showMessageDialog(
        			  
        			  
        		_aboutBox, "The Automata Creator is a standalone desktop application that can be \n "
        				 +"installed across multiple platforms such as Windows and Linux.The \n"
        				 +"Automata Creator will allow both educators and students to create, edit,\n"
        				 +"simulate, and store a DFA. Additionally, the application will allow users\n "
        				 +"to import an existing DFA and then perform any normal operations upon that\n "
        				 + "DFA.");
          }        	
        });
        
        // Add quick help menu item and action listener
        JMenuItem quickHelp = new JMenuItem("Quick Help");   
        ImageIcon helpIcon = new ImageIcon("Images/help.png");            
        quickHelp.setIcon(helpIcon);
        
        // Add all created sub menu items to help menu
        help.add(about);
        help.add(quickHelp);
        
        // End build Help menu
       
        return menuBar;      
    }
}

// MENU EVENT CLASSES

// State clicked event
@SuppressWarnings("serial")
class StateEvent extends EventObject
{
	private Data.StateType _type;
	
	public StateEvent(Object source, Data.StateType type)
	{
		super(source);
		_type = type;
	}
	
	public Data.StateType getType()
	{
		return _type;
	}
}

interface StateEventListener extends EventListener
{
	public void stateSelected(StateEvent e);
}

class StateEvents
{
    static EventListenerList listenerList = new EventListenerList();
	
    static void addStateEventListener(StateEventListener l)
	{
		listenerList.add(StateEventListener.class, l);
	}
	
	static void removeStateEventListener(StateEventListener l)
	{
		listenerList.remove(StateEventListener.class, l);
	}
	
	static void sendStateEvent(StateEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == StateEventListener.class)
			{
				((StateEventListener) listeners[i+1]).stateSelected(e);
			}
		}
	}
}

// Clear canvas clicked event 
@SuppressWarnings("serial")
class ClearCanvasEvent extends EventObject
{
	public ClearCanvasEvent(Object source)
	{
		super(source);
	}
}

interface ClearCanvasEventListener extends EventListener
{
	public void clearCanvasSelected(ClearCanvasEvent e);
}

class ClearCanvasEvents
{
    static EventListenerList listenerList = new EventListenerList();
	
    static void addClearCanvasEventListener(ClearCanvasEventListener l)
	{
		listenerList.add(ClearCanvasEventListener.class, l);
	}
	
	static void removeClearCanvasEventListener(ClearCanvasEventListener l)
	{
		listenerList.remove(ClearCanvasEventListener.class, l);
	}
	
	static void sendClearCanvasEvent(ClearCanvasEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == ClearCanvasEventListener.class)
			{
				((ClearCanvasEventListener) listeners[i+1]).clearCanvasSelected(e);
			}
		}
	}
}

// Copy canvas clicked event 
@SuppressWarnings("serial")
class CopyCanvasEvent extends EventObject
{
	public CopyCanvasEvent(Object source)
	{
		super(source);
	}
}

interface CopyCanvasEventListener extends EventListener
{
	public void copyCanvasSelected(CopyCanvasEvent e);
}

class CopyCanvasEvents
{
 static EventListenerList listenerList = new EventListenerList();
	
 static void addCopyCanvasEventListener(CopyCanvasEventListener l)
	{
		listenerList.add(CopyCanvasEventListener.class, l);
	}
	
	static void removeCopyCanvasEventListener(CopyCanvasEventListener l)
	{
		listenerList.remove(CopyCanvasEventListener.class, l);
	}
	
	static void sendCopyCanvasEvent(CopyCanvasEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == CopyCanvasEventListener.class)
			{
				((CopyCanvasEventListener) listeners[i+1]).copyCanvasSelected(e);
			}
		}
	}
}

