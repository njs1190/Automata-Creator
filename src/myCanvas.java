package automataCreator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;


public class myCanvas extends JPanel implements MouseListener, MouseMotionListener
{
    ArrayList<State> _states; // The list of states that have been added to the canvas
    ArrayList<Transition> _transitions; // The list of transitions that have been added to the canvas
    JPanel canvas;
    JPanel stateObjectPanel;
    JPopupMenu popupMenu; 
    private int stateInserts = 0;
    private static final int LEFT_BORDER = 72;
    private static final int STATE_DIAMETER = 50;
    private int _dragFromX = 0;    // pressed this far inside ball's
    private int _dragFromY = 0;    // bounding box.
    private int _stateX = 50;      // x coord - set from drag
    private int _stateY = 50;      // y coord - set from drag

    private boolean _canDrag  = false;
    
    public myCanvas()
    {    	
    	_states = new ArrayList<State>();
    	_transitions = new ArrayList<Transition>(); 
    	setBackground(Color.white);
        setForeground(Color.black);
    	
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

    
      public void AddCanvas(JPanel canvasPanel)
    {   
    	 canvas = canvasPanel; 
    	  
    	//Create panel for 4 states on left side
        stateObjectPanel = new JPanel(new GridLayout(4,1));
    	//stateObjectPanel.setBackground(Color.LIGHT_GRAY);
    	
    	//Creates 4 buttons for the state panel
    	JLabel start = new JLabel("Start");
    	JLabel startA = new JLabel("Start Accept");
    	JLabel intermediate = new JLabel("Intermediate");
    	JLabel accept = new JLabel("Accept");
    	
    	stateObjectPanel.add(start);
    	stateObjectPanel.add(startA);
    	stateObjectPanel.add(intermediate);
    	stateObjectPanel.add(accept);
    	/*  this part is if you want to use buttons instead, but I couldn't find a way to 
    	 * handle button pressed/released, just button clicked
    	JButton startButton = new JButton("Start");
    	JButton startAcceptButton = new JButton("Start Accept");
    	JButton intermediate = new JButton("Intermediate");
    	JButton acceptButton = new JButton("Accept");
    		
    	//adds action listeners to the buttons
    	startButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			stateEvent(1);
    			}
    		});
    	startAcceptButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			stateEvent(2);
    			}
    		});
    	intermediate.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			stateEvent(3);
    			}
    		});
    	acceptButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			stateEvent(4);
    			}
    		});
    	
    	//adds the buttons to the state panel
    	stateObjectPanel.add(startButton);
    	stateObjectPanel.add(startAcceptButton);
    	stateObjectPanel.add(intermediate);
    	stateObjectPanel.add(acceptButton);
    	
    	*/   	
    	
    	
    	//adds the state panel to the left side
    	canvasPanel.add(stateObjectPanel, BorderLayout.WEST);
		
		canvasPanel.addMouseListener(this); 
        canvasPanel.addMouseMotionListener(this);
        
    }  
      
    //PRE: One of the state insert options chosen from menu
    //POST: Creates the appropriate state in predetermined areas
    public void createStates(int stateChoice)
    {   	
    	Graphics g = canvas.getGraphics();
    	
    	switch(stateChoice)
    	{
    		case 1: 
    			g.drawOval((125 + stateInserts*100), 50, 50, 50);
    			g.drawString("Q" + stateInserts, 50, 50);
    			stateInserts++;
    			break;			
    		case 2: 
    			g.drawOval((125 + stateInserts*100), 50, 50, 50);
    			g.drawOval((125 + stateInserts*100), 50, 53, 53);
    			g.drawString("Q" + stateInserts, 50, 50);
    			stateInserts++;
    			break;
    		case 3:
    			break;
    		case 4:
    			break;    			
			default:
				break;   	
    	
    	}
    	
    }
      
	// Mouse Listener Events
	public void mousePressed(MouseEvent e) 
	{
		// gets the source of button click  1 = left click, 3 = right click
		int xInitial = e.getX();  // x-position of mouse
		int yInitial = e.getY();  // y-position of mouse
		
		//checks to see if it is the left click
		if(SwingUtilities.isLeftMouseButton(e))
		{
			//within a bound area of the state panel
			if(xInitial > 25 && xInitial < 72 && yInitial > 25 && yInitial < 375)
			{
				DrawState(xInitial - 25, yInitial - 25);
				
				_canDrag = true;
				_dragFromX = xInitial - _stateX;  // how far from left
	            _dragFromY = yInitial - _stateY;  // how far from top
			}
			
			else
				_canDrag = false;			
		}		
		
		//I just use this part to comment and uncomment to obtain x, y coords when needed
		/*
		if(mouseButton == 1)
			JOptionPane.showMessageDialog(null, e.getX());
		if(mouseButton == 3)
			JOptionPane.showMessageDialog(null, e.getY());
			*/
	}
	
	
	// Listens to the mouse being dragged while the left mouse button is held down over a state
	public void mouseDragged(MouseEvent e) 
	{
		if (_canDrag == true)
		{
			_stateX = e.getX() - _dragFromX;
			_stateY = e.getY() - _dragFromY;			
			
			//Prevent the state moving off the screen sides
			_stateX = Math.max(_stateX, 0);
			_stateX = Math.min(_stateX, getWidth() - STATE_DIAMETER);
            
            //Prevent the state moving off the top or bottom
			_stateY = Math.max(_stateY, 0);
			_stateY = Math.min(_stateY, getHeight() - STATE_DIAMETER);
            
            this.repaint(); // Repaint because position changed.
		}
		
	}	
	

	public void mouseReleased(MouseEvent e) 
	{
		//when mouse is released, it should check to see if it's within a valid area and 
		//create the state and label object and send information to canvas to hold
		//otherwise delete
		if (SwingUtilities.isLeftMouseButton(e))
		{
			State s = new State(Data.StateType.STARTFINAL, "q0", 0, e.getX(), e.getY(), 50);
			_states.add(s);
		}
	}
	
	//if the mouse exits the canvas, stop the canvas change
	public void mouseExited(MouseEvent e) 
	{
		_canDrag = false;
	}
	
	//these mouse actions are ignored
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

	// PRE: Mouse button has been clicked
	// POST: A context menu specific to the location 
	// where a right mouse click event occurs will be created
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (SwingUtilities.isRightMouseButton(e))
		{			
			DrawTransition(_states.get(0), _states.get(1));
		}
	/*
	 	NOT FINISHED IMPLEMENTING COMMENT OUT FOR NOW
	 
	    // Create the context menu if it is null
		if (popupMenu == null)
		{
			popupMenu = new JPopupMenu();	
		}
					
		// check if the mouse click event is generated
		// from a right click
		if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e))
		{		
		
			JMenuItem copyCanvas = new JMenuItem("Copy Canvas");
			popupMenu.add(copyCanvas);
			
			
			// Lastly, make the context menu visible
			popupMenu.setLocation(e.getLocationOnScreen());
			popupMenu.setVisible(true);	        
		}
		
		else
		{
			if (popupMenu.isVisible())
			{
				popupMenu.setVisible(false);
			}
		}*/		
	}
	
	// PRE:
	// POST: A state will be drawn onto the canvas
	public void DrawState(int x, int y)
	{
		Graphics g = canvas.getGraphics();
		
		g.drawOval(x - 25, y - 25, 50, 50);
		g.drawString("Q1", x - 6, y + 4);			
	}
	
	// PRE: A valid state has been selected to delete
	// POST: The state will be removed from the list of states and 
	// will be removed from the canvas 
	public void DeleteState(State state)
	{
		// Remove the state from list of all states on canvas
		_states.remove(state); 
		
		// Clear state from canvas
		Graphics g = canvas.getGraphics();
		
		// Delete the height and width of 51 just to make sure that we get all of the 
		// state image removed from the canvas
		g.clearRect(state.getXPosition() - 50, state.getYPosition() - 50, 51, 51); 
	}
	
	// PRE: Both a from and to state have been selected 
	// POST: A transition will be drawn between the selected states
	public void DrawTransition(State from, State to)
	{
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1; 
		
		if (from.getYPosition() < to.getYPosition())
		{
			y1 = from.getYPosition(); 
			y2 = to.getYPosition();	
			
			x1 = from.getXPosition();
			x2 = to.getXPosition();
		}
					
		Graphics g = canvas.getGraphics();
		g.drawLine(x1, y1, x2, y2);
	}
	
	// PRE:
	// POST: All saved states and transitions will be repainted onto the canvas
	public void RepaintCanvas()
	{	
		// Go through all of the states and re-add them to the canvas
		for (int i = 0; i < _states.size(); i++)
		{
			State s = _states.get(i);
			DrawState(s.getXPosition(), s.getYPosition());					
		}
		
		// Go through all of the transitions and re-add them onto the canvas
		for (int i = 0; i < _transitions.size(); i++)
		{
			Transition t = _transitions.get(i);
			DrawTransition(t.getFrom(), t.getTo());			
		}
	}
}
