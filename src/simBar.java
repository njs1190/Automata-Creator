package automataCreator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class simBar implements ActionListener {
	
	
	public static void buildSimBar(JPanel simBarPanel) {
        JButton button = null;
 
        
        JPanel simControl = new JPanel();
        simControl.setSize(150, 50);
//        simControl.setBackground(Color.GRAY);
     
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
 
       simBarPanel.add(simControl,BorderLayout.WEST);
        //separator
        
       JPanel tapePanel = new JPanel();

        JLabel tapeLabel = new JLabel("Tape Input");
        JTextField tapeInput = new JTextField("input binary string");
        tapeInput.setColumns(10);
        
     
        //tapeInput.setActionCommand(TEXT_ENTERED);
        tapePanel.add(tapeLabel);
        tapePanel.add(tapeInput);
        
        simBarPanel.add(tapePanel,BorderLayout.AFTER_LINE_ENDS);
 
       
    }
	

public static JButton makeSimulationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) 
    				{
						//Look for the image.
						String imgLocation = "images/"
						+ imageName
						+ ".gif";
						//URL imageURL = GridBagEx1.class.getResource(imgLocation);
						
						//Create and initialize the button.
						JButton button = new JButton();
						button.setActionCommand(actionCommand);
						button.setToolTipText(toolTipText);
						
//
//						if (imageURL != null) 
//							{                      //image found
//							button.setIcon(new ImageIcon(imageURL, altText));
//							} 
//						else {                                     //no image found
//						button.setText(altText);
//						System.err.println("Resource not found: "
//						+ imgLocation);
//						}

return button;
}	
	
	
    public void actionPerformed(ActionEvent e) 
    {
        
        
    }


	
	
	
	
	
	
	

}
