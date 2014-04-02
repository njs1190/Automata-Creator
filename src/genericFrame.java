

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.MenuListener;

import basicFrame.GridBagEx1;
 
public class genericFrame implements ActionListener {
 
	  protected JTextArea outputTextArea;
	    protected String newline = "\n";
	    
	
	public JPanel contentPane (){

        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel(new BorderLayout());
        
        
        //Create the simBar.
        JPanel simBar = new JPanel();
        buildSimBar(simBar);
        simBar.setSize(100, 10);
        simBar.setBackground(Color.red);
      
        JPanel objectPanel = new JPanel(new GridLayout(4,1));
        objectPanel.setBackground(Color.GRAY);
        buildObjectPanel(objectPanel);
        
        JPanel outputPanel = new JPanel();
        outputPanel.setSize(300, 150);
        buildOutputPanel(outputPanel);
        
        JPanel canvasPanel = new JPanel();
        canvasPanel.setBackground(Color.white);
        canvasPanel.setSize(300, 260);
        canvasPanel.add(new Canvas());
        
       
        
        totalGUI.add(simBar, BorderLayout.NORTH);
        totalGUI.add(objectPanel, BorderLayout.WEST);
        totalGUI.add(outputPanel, BorderLayout.SOUTH);
        totalGUI.add(canvasPanel, BorderLayout.CENTER);
        

        totalGUI.setOpaque(true);
        return totalGUI;
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

        
     //Build File Menu
        JMenu save = new JMenu("Save");  
        JMenuItem newAutomata = new JMenuItem("New");
        JMenuItem fileSave = new JMenuItem("as Automota Creator File");
        JMenuItem imageSave = new JMenuItem("as Image");
        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem close = new JMenuItem("Close");
        
        
      
        
        file.add(newAutomata);
        file.add(importFile);
        file.add(save);
        save.add(fileSave);
        save.add(imageSave);
        file.add(close);
        
     //Build Edit Menu
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");
        JMenuItem deleteState = new JMenuItem("Delete State");
        JMenuItem copyCanvas = new JMenuItem("Copy Canvas");
        JMenuItem clearCanvas = new JMenuItem("Clear Canvas");
        
        edit.add(undo);
        edit.add(redo);
        edit.add(deleteState);
        edit.add(copyCanvas);
        edit.add(clearCanvas);
        
        
      //Build Insert Menu  
        JMenu state = new JMenu("State");
        JMenuItem transition = new JMenuItem("Transition");
        JMenuItem string = new JMenuItem("String");

        //Build State Menu
        JMenuItem start = new JMenuItem("Start");
        JMenuItem startAccept = new JMenuItem("Start Accept");
        JMenuItem intermediate = new JMenuItem("Intermediate");
        JMenuItem accept = new JMenuItem("Accept");
        
        insert.add(state);
        state.add(start);
        state.add(startAccept);
        state.add(intermediate);
        state.add(accept);
  
       insert.add(transition);
       insert.add(string);
        
        
      
        
        
        
     
      //Build Help Menu
      JMenuItem about = new JMenuItem("About");   
      JMenuItem quickHelp = new JMenuItem("Quick Help");
      
      help.add(about);
//      about.addActionListener(new ActionListener() 
//      {
//          public void actionPerformed(ActionEvent e) 
//          {
//        	  JFrame popUpFrame = new JFrame();
//        	 
//        	  JOptionPane.showMessageDialog( "Eggs are not supposed to be green.");
//          }
//
//      });
     
     


      
      help.add(quickHelp);
        

       return menuBar;
    
    
    
    }
	
	protected void buildOutputPanel (JPanel outputPanel) {
        
 
        outputTextArea = new JTextArea(5, 15);
        outputTextArea.setEditable(false);
        outputTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
    
     outputPanel.add(outputTextArea, BorderLayout.LINE_START);
     
     JPanel stringProcessPanel = new JPanel (new GridLayout(2,3));
     
     JTextArea processedSymbols = new JTextArea(1,6);
     JTextArea currentSymbol = new JTextArea(1,1);
     JTextArea tapeSymbols = new JTextArea(1,6);
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
	
	protected void buildObjectPanel (JPanel objectPanel) {
        JButton button = null;
 
        
    
        //first button
        button = makeObjectPanel("start", "previousSymbol",
                "click and drag to add start state",
                "Start");
        objectPanel.add(button);
 
        //second button
        button = makeObjectPanel("start", "previousSymbol",
        		"click and drag to add a start accept state",
                "Start Accept");
       objectPanel.add(button);
 
        //third button
        button = makeObjectPanel("start", "previousSymbol",
        		"click and drag to add an Intermediate state",
                "Intermediate ");
        objectPanel.add(button);
        
        //fourth button
        button = makeObjectPanel("start", "previousSymbol",
        		"click and drag to add an accept state",
                "Accept");
        objectPanel.add(button);
 
      
    
       
    }
	
	  protected JButton makeObjectPanel(String imageName,
	            String actionCommand,
	            String toolTipText,
	          String altText ) 
	    				{
							//Look for the image.
							String imgLocation = "images/"
							+ imageName
							+ ".gif";
							URL imageURL = GridBagEx1.class.getResource(imgLocation);
							
							//Create and initialize the button.
							JButton button = new JButton();
							button.setActionCommand(actionCommand);
							button.setToolTipText(toolTipText);
							button.addActionListener(this);

							if (imageURL != null) 
								{                      //image found
								button.setIcon(new ImageIcon(imageURL, altText));
								} 
							else {                                     //no image found
							button.setText(altText);
							System.err.println("Resource not found: "
							+ imgLocation);
							}

	return button;
	}	
	
	

	protected void buildSimBar(JPanel simBar) {
        JButton button = null;
 
        
        JPanel simControl = new JPanel();
        simControl.setSize(150, 50);
        simControl.setBackground(Color.black);
     
        //first button
        button = makeSimulationButton("previousSymbol", "previousSymbol",
                                      "Go to previous symbol on tape Input",
                                      "Previous");
        simControl.add(button);
 
        //second button
        button = makeSimulationButton("autoSim", "startAutoSim",
                                      "Start auto simulation",
                                      "Start");
        simControl.add(button);
 
        //third button
        button = makeSimulationButton("nextSymbol", "nextSymbol",
                                      "Go to next symbol on tape input",
                                      "Next");
        simControl.add(button);
 
       simBar.add(simControl,BorderLayout.WEST);
        //separator
        
       JPanel tapePanel = new JPanel();

        JLabel tapeLabel = new JLabel("Tape Input");
        JTextField tapeInput = new JTextField("input binary string");
        tapeInput.setColumns(10);
        
        tapeInput.addActionListener(this);
        //tapeInput.setActionCommand(TEXT_ENTERED);
        tapePanel.add(tapeLabel);
        tapePanel.add(tapeInput);
        
        simBar.add(tapePanel,BorderLayout.EAST);
 
       
    }
	

    protected JButton makeSimulationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) 
    				{
						//Look for the image.
						String imgLocation = "images/"
						+ imageName
						+ ".gif";
						URL imageURL = GridBagEx1.class.getResource(imgLocation);
						
						//Create and initialize the button.
						JButton button = new JButton();
						button.setActionCommand(actionCommand);
						button.setToolTipText(toolTipText);
						button.addActionListener(this);

						if (imageURL != null) 
							{                      //image found
							button.setIcon(new ImageIcon(imageURL, altText));
							} 
						else {                                     //no image found
						button.setText(altText);
						System.err.println("Resource not found: "
						+ imgLocation);
						}

return button;
}	
	
	
    public void actionPerformed(ActionEvent e) 
    {
        
        
    }
	
	

 
 private static void createAndShowGUI() 
 {

	 //Create and set up the frame. 
     JFrame frame = new JFrame("Automata Creator");
 
     
   //Create and set up the content pane.
     genericFrame frameTemplate = new genericFrame();
     frame.setContentPane(frameTemplate.contentPane());
    
   //add menu to frame
     frame.setJMenuBar(frameTemplate.createMenuBar());
 
     
   // other frame parts
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(800, 600);
     frame.setVisible(true);
     frame.setResizable(true);
 
     
 
 
 }  
 
 
 
 
 public static void main(String[] args)
 {
     //Schedule a job for the event-dispatching thread:
     //creating and showing this application's GUI.
     SwingUtilities.invokeLater(new Runnable() 
     {
         public void run() 
         {
             createAndShowGUI();
         }
     });
 }




 
 
}
 
