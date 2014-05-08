// Menu.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to create all of the menu items for the frames menu bar
// created in MainFrame and also handle the events generated when an menu item is selected. 

package automataCreator;

import java.awt.event.*;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.EventListenerList;


public class Menu 
{	
	private MainFrame _mainFrame;
	private StringInput _input;
	private JOptionPane _aboutBox;
	
	private JMenuBar _menuBar;
	private JMenu _file;
	private JMenu _edit;
	private JMenu _insert;
	private JMenu _help;
	
	// file
	private JMenuItem _newAutomata;
	private JMenu _save;
	private JMenuItem _fileSave;
	private JMenuItem _imageSave;
	private JMenuItem _importFile;
	private JMenuItem _close;
	
	// edit
	private JMenuItem _undo;
	private JMenuItem _redo;
	private JMenuItem _copyCanvas;
	private JMenuItem _clearCanvas;
	
	// insert
	private JMenu _state;
	private JMenuItem _start;
	private JMenuItem _startAccept;
	private JMenuItem _intermediate;
	private JMenuItem _accept;
	private JMenuItem _string;

	
	// help
	private JMenuItem _about;
	private JMenuItem _quickHelp;

	public Menu(MainFrame mainFrame)
	{
		_mainFrame = mainFrame;
	}

	public JMenuBar initializeMenuBar()
    {
        //Create the menu bar.
        _menuBar = new JMenuBar();

        //Add a JMenu
        _file = new JMenu("File");
        _edit = new JMenu("Edit");
        _insert = new JMenu("Insert");
        _help = new JMenu("Help"); 
        
        _menuBar.add(_file);
        _menuBar.add(_edit);
        _menuBar.add(_insert);
        _menuBar.add(_help);
        
        // UI manager to get icons for menu items
        UIDefaults defaults = UIManager.getDefaults( );
        
        // Start build File menu
        
        // Add new menu item and action listener    
        _newAutomata = new JMenuItem("New");
        Icon newIcon = defaults.getIcon( "FileView.fileIcon" );             
        _newAutomata.setIcon(newIcon);
        _newAutomata.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.newProject();
          }
        	
        });
        
        // Parent save menu 
        _save = new JMenu("Save");
        ImageIcon saveIcon = new ImageIcon("save.png");
        _save.setIcon(saveIcon); 
        
        // Add save file menu items and their corresponding action listeners
        _fileSave = new JMenuItem("Automota Creator File");
        _fileSave.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.save();
          }
        	
        });
        
        _imageSave = new JMenuItem("Image");
        _imageSave.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.saveImage();
          }
        	
        });
        
        // Add import file menu item and its corresponding action listener
        _importFile = new JMenuItem("Import");
        Icon importIcon = defaults.getIcon( "FileView.directoryIcon");  
        _importFile.setIcon(importIcon);
        _importFile.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.openExisting();
          }
        	
        });
        
        // Add close file menu item and its corresponding action listener
        _close = new JMenuItem("Close");   
        _close.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.close();
          }
        	
        });
             
        // Add all created sub menu items to the File menu
        _file.add(_newAutomata);
        _file.addSeparator();
        _file.add(_importFile);
        _file.addSeparator();
        _file.add(_save);
        _save.add(_fileSave);
        _save.add(_imageSave);
        _file.addSeparator();
        _file.add(_close); 
        
        // end build File menu
        
        //Build Edit Menu
        
        // Add undo edit menu item and action listener
        _undo = new JMenuItem("Undo");
        _undo.setAccelerator(KeyStroke.getKeyStroke('Z', KeyEvent.CTRL_DOWN_MASK));
        ImageIcon undoIcon = new ImageIcon("undo.png");            
        _undo.setIcon(undoIcon);
        // begin with undo disabled
        _undo.setEnabled(false);
        _undo.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.undo(); 	          	  
          }
          
        });
        
        // Add re-do edit menu item and action listener
        _redo = new JMenuItem("Redo");
        _redo.setAccelerator(KeyStroke.getKeyStroke('Y', KeyEvent.CTRL_DOWN_MASK));
        ImageIcon redoIcon = new ImageIcon("redo.png");            
        _redo.setIcon(redoIcon);
        // begin with redo disabled
        _redo.setEnabled(false);
        _redo.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.redo(); 	          	  
          }
          
        });
        
        // Add copy edit menu item and action listener
        _copyCanvas = new JMenuItem("Copy Canvas");
        _copyCanvas.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_DOWN_MASK));
        _copyCanvas.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.copyCanvas();	          	  
          }
          
        });
        
        // Add clear edit menu item and action listener
        _clearCanvas = new JMenuItem("Clear Canvas");
        _clearCanvas.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.clearCanvas();	          	  
          }
          
        });
        
        // Add all created sub menu items to edit menu
        _edit.add(_undo);
        _edit.add(_redo);
        _edit.addSeparator();
        _edit.add(_copyCanvas);
        _edit.addSeparator();
        _edit.add(_clearCanvas); 
        
        // End build Edit menu
        
        // Build Insert menu 
        
        // Add state insert menu item and action listener
        _state = new JMenu("State");
        
        // Build State sub menu options
        
        // Add start state sub menu item and action listener 
        _start = new JMenuItem("Start");
        _start.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {       	  
        	  _mainFrame.createState(Data.StateType.START);
          }   
          
        });
        
        // Add start/accept state sub menu item and action listener 
        _startAccept = new JMenuItem("Start Accept");
        _startAccept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(Data.StateType.STARTACCEPT);
          }        	
        });
        
        // Add intermediate state sub menu item and action listener 
        _intermediate = new JMenuItem("Intermediate");
        _intermediate.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(Data.StateType.INTERMEDIATE);
          }
          
        });
        
        // Add accept state sub menu item and action listener 
        _accept = new JMenuItem("Accept");
        _accept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(Data.StateType.ACCEPT);
          }        	
        });
      
        
        // Add string insert menu item and action listener
        _string = new JMenuItem("String");
        _string.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _input = new StringInput();
        	  _input.create();  	          	  
          }
          
        });
        
        // Add all created sub menu items to insert menu
        _insert.add(_state);
        _state.add(_start);
        _state.add(_startAccept);
        _state.add(_intermediate);
        _state.add(_accept);
        _insert.addSeparator();
        _insert.add(_string);
        
        // End build Insert menu
        
        // Build Help Menu
        
        // Add about help menu item and action listener
        _about = new JMenuItem("About"); 
        ImageIcon aboutIcon = new ImageIcon("information.png");            
        _about.setIcon(aboutIcon);
        _about.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  
        	  JOptionPane.showMessageDialog(
        			  
        			  
        		_aboutBox, "The Automata Creator is a standalone desktop application that can be \n "
        				 +"installed across multiple platforms such as Windows and Linux.The \n"
        				 +"Automata Creator will allow both educators and students to create, edit,\n"
        				 +"simulate, and store a DFA. Additionally, the application will allow users\n "
        				 +"to import an existing DFA and then perform any normal operations upon that\n "
        				 + "DFA.", "About Automata Creator", _aboutBox.INFORMATION_MESSAGE, null);
          }        	
        });
        
        // Add quick help menu item and action listener
        _quickHelp = new JMenuItem("Quick Help");   
        ImageIcon helpIcon = new ImageIcon("help.png");            
        _quickHelp.setIcon(helpIcon);
        
        // Add all created sub menu items to help menu
        _help.add(_about);
        _help.add(_quickHelp);
        
        // End build Help menu
        
        
        InitializeListeners();
       
        return _menuBar;      
    }

	private void InitializeListeners()
	{
		// Listen for events generated by the undo management class
     	UndoEvents.addUndoEventListener(new UndoEventListener()
     	{
			@Override
			public void undoEvent(UndoEvent e) 
			{
				if (e._isEmpty)
				{
					_undo.setEnabled(false);
				}
				
				else
				{
					_undo.setEnabled(true);
				}
				
			}
     	});
     	
     	RedoEvents.addRedoEventListener(new RedoEventListener()
     	{
			@Override
			public void redoEvent(RedoEvent e) 
			{
				if (e._isEmpty)
				{
					_redo.setEnabled(false);
				}
				
				else
				{
					_redo.setEnabled(true);
				}
				
			}
     	});
		
	}
}

