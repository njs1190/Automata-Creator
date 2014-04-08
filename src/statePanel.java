package automataCreator;

import javax.swing.JButton;
import javax.swing.JPanel;

public class statePanel 
{	
	public statePanel()
	{
	
	}
	
	public void BuildObjectPanel (JPanel objectPanel)
	{
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
	
	public static  JButton makeObjectPanel(String imageName, String actionCommand, String toolTipText, String altText ) 
	{
							//Look for the image.
							String imgLocation = "images/"
							+ imageName
							+ ".gif";
//							URL imageURL = GridBagEx1.class.getResource(imgLocation);
							
							//Create and initialize the button.
							JButton button = new JButton();
							button.setActionCommand(actionCommand);
							
							button.setToolTipText(toolTipText);
							//button.addActionListener(this);
							button.setText(altText);
//							if (imageURL != null) 
//								{                      //image found
//								button.setIcon(new ImageIcon(imageURL, altText));
//								} 
//							else {                                     //no image found
//							button.setText(altText);
//							System.err.println("Resource not found: "
//							+ imgLocation);
//							}

							return button;
	}	
	
	

}
