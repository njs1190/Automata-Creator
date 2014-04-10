package automataCreator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.util.ArrayList;

class myCanvas extends JPanel implements MouseListener
{
    Image img;      // Contains the image to draw on MyCanvas
    ArrayList<State> _states; // The list of states that have been added to the canvas
    ArrayList<Transition> _transitions; // The list of transitions that have been added to the canvas
    
    public myCanvas()
    {    	
    	_states = new ArrayList<State>();
    	_transitions = new ArrayList<Transition>();    	
    }

    public void AddCanvas(JScrollPane canvasPanel)
    {  	
    
    	Canvas automataCanvas = new Canvas();
    	automataCanvas.setSize(700, 320);
    	automataCanvas.setBackground(Color.WHITE);
		
    	canvasPanel.add(automataCanvas);

	}    

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
