package automataCreator;

import java.awt.event.*;

import javax.swing.*;

public class menu 
{	
	MainFrame _mainFrame;
	private JOptionPane aboutBox;

	public menu(MainFrame mainFrame)
	{
		_mainFrame = mainFrame;
	}

	public JMenuBar createMenuBar()
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
        	  _mainFrame.Save();
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
        	  _mainFrame.OpenExisting();
          }
        	
        });
        
        // Add close file menu item and its corresponding action listener
        JMenuItem close = new JMenuItem("Close");   
        close.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.Close();
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
        
        // Add delete edit menu item and action listener
        JMenuItem deleteState = new JMenuItem("Delete State");
        ImageIcon deleteIcon = new ImageIcon("Images/delete.png");             
        deleteState.setIcon(deleteIcon);
        
        // Add copy edit menu item and action listener
        JMenuItem copyCanvas = new JMenuItem("Copy Canvas");
        
        // Add clear edit menu item and action listener
        JMenuItem clearCanvas = new JMenuItem("Clear Canvas");
        
        // Add all created sub menu items to edit menu
        edit.add(undo);
        edit.add(redo);
        edit.addSeparator();
        edit.add(deleteState);
        edit.addSeparator();
        edit.add(copyCanvas);
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
        	  _mainFrame.createState(1);
          }        	
        });
        
        // Add start/accept state sub menu item and action listener 
        JMenuItem startAccept = new JMenuItem("Start Accept");
        startAccept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(2);
          }        	
        });
        
        // Add intermediate state sub menu item and action listener 
        JMenuItem intermediate = new JMenuItem("Intermediate");
        intermediate.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(3);
          }        	
        });
        
        // Add accept state sub menu item and action listener 
        JMenuItem accept = new JMenuItem("Accept");
        accept.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e) 
          {
        	  _mainFrame.createState(4);
          }        	
        });
        
        // Add transition insert menu item and action listener
        JMenuItem transition = new JMenuItem("Transition");
        
        // Add string insert menu item and action listener
        JMenuItem string = new JMenuItem("String");
        
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
        	  
        	  
        	  JOptionPane.showMessageDialog(aboutBox, "The Automata Creator is a standalone desktop application that can be \n installed across multiple platforms such as Windows and Linux.\n The Automata Creator will allow both educators and students to create, edit, simulate, and store a \n DFA. Additionally, the application will allow users to import an existing\n DFA and then perform any normal operations upon that DFA.");
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
