package automataCreator;


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
import automataCreator.simBar;
import automataCreator.menu;
import automataCreator.outPanel;
import automataCreator.statePanel;
import automataCreator.myCanvas;


public class MainFrame {
 
	 
	   
	    
	  
	public JPanel contentPane (){

        // We create a bottom JPanel to place everything on.
        JPanel totalGUI = new JPanel(new BorderLayout());
        
        
        //Create the simBar.
        JPanel simBarPanel = new JPanel();
        simBar.buildSimBar(simBarPanel);
        
       
        
      //leftHand side panel for state Object buttons
        JPanel objectPanel = new JPanel(new GridLayout(4,1));
        objectPanel.setBackground(Color.GRAY);
        statePanel.buildObjectPanel(objectPanel);
        
        
       //Bottom outputPanel for user feedback and string process tracking 
        JPanel outputPanel = new JPanel();
        outPanel.buildOutputPanel(outputPanel);
        
        
        
        //Create scrollPane for canvas, 
        JScrollPane canvasPane = new JScrollPane();
        myCanvas.addCanvas(canvasPane);
       
      
        
       
        
        totalGUI.add(simBarPanel, BorderLayout.NORTH);
        totalGUI.add(objectPanel, BorderLayout.WEST);
        totalGUI.add(outputPanel, BorderLayout.SOUTH);
        totalGUI.add(canvasPane, BorderLayout.CENTER);
        

        totalGUI.setOpaque(true);
        return totalGUI;
    }

	


	

	
 
 private static void createAndShowGUI() 
 {

	 //Create and set up the frame. 
     JFrame frame = new JFrame("Automata Creator");
 
     
   //Create and set up the content pane.
     MainFrame frameTemplate = new MainFrame();
     frame.setContentPane(frameTemplate.contentPane());
    
   //add menu to frame
     frame.setJMenuBar(menu.createMenuBar());
 
     
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
 
