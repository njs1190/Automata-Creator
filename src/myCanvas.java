package automataCreator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Color;
import java.util.ArrayList;

public class myCanvas extends JPanel implements MouseListener
{
    ArrayList<State> _states; // The list of states that have been added to the canvas
    ArrayList<Transition> _transitions; // The list of transitions that have been added to the canvas
    
    public myCanvas()
    {    	
    	_states = new ArrayList<State>();
    	_transitions = new ArrayList<Transition>();    	
    }
    
    public myCanvas(ArrayList<State> states, ArrayList<Transition> transitions)
    {
    	_states = states;
    	_transitions = transitions;
    }
    
    public void setStates(ArrayList<State> states)
    {
    	_states = states;
    }
    
    public void setTransitions(ArrayList<Transition> transitions)
    {
    	_transitions = transitions;
    }
    
    public ArrayList<State> getStates()
    {
    	return _states;
    }
    
    public ArrayList<Transition> getTransitions()
    {
    	return _transitions;
    	
    }
    
      public static void AddCanvas(JPanel canvasPanel)
    {
    	
    	Canvas automataCanvas = new Canvas();
    	
    	
    	
    		automataCanvas.setSize(800, 510);
    		automataCanvas.setBackground(Color.WHITE);
		automataCanvas.setVisible(true);
  

		
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
