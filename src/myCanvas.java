package automataCreator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
   
public class myCanvas extends JPanel implements MouseListener, MouseMotionListener
{
    private ArrayList<State> _states; // The list of states that have been added to the canvas
    private ArrayList<Transition> _transitions; // The list of transitions that have been added to the canvas
    private JPanel canvas;
    private JPanel stateObjectPanel;
    private JPopupMenu contextMenu; 			  // Context menu used for the canvas when right clicking
    private int stateInserts = 0;			      // This is the number of states on the canvas
    private static final int X_BORDER = 73;       // This is the x location where the canvas is able to be drawn on
    private static final int Y_BORDER = 26; 	  // This is the y location where the canvas is able to be drawn on
    private static final int STATE_DIAMETER = 50; // This is the diameter for the state object 
    private int _dragFromX = 0;   				  // pressed this far inside ball's
    private int _dragFromY = 0;    				  // bounding box.
    private int _stateX = 50;      				  // x coord - set from drag
    private int _stateY = 50;      				  // y coord - set from drag
    private State _currentState;				  // Current state being selected

    private boolean _canDrag  = false;
    
    // PRE: myCanvas object is instantiated with no parameters
    // POST: myCanvas object is initialized
    public myCanvas()
    {    	
    	_states = new ArrayList<State>();
    	_transitions = new ArrayList<Transition>(); 
    	setBackground(Color.white);
        setForeground(Color.black);
    	
    }
    
    // PRE: myCanvas object is instantiated with parameters
    // POST: myCanvas object is initialized
    public myCanvas(ArrayList<State> states, ArrayList<Transition> transitions)
    {
    	_states = states;
    	_transitions = transitions;
    	setBackground(Color.white);
        setForeground(Color.black);
    }
    
    // Set methods
    public void setStates(ArrayList<State> states)
    {
    	_states = states;
    }
    
    public void setTransitions(ArrayList<Transition> transitions)
    {
    	_transitions = transitions;
    }
    
    // Get methods
    public ArrayList<State> getStates()
    {
    	return _states;
    }
    
    public ArrayList<Transition> getTransitions()
    {
    	return _transitions;
    	
    }

    // PRE: AddCanvas is called from mainframe
    // POST: myCanvas JFrame is initialized
     public void AddCanvas(JPanel canvasPanel)
    {   
    	//Create panel for 4 states on left side
        stateObjectPanel = new JPanel(new GridLayout(4,1));
    	
    	//Creates 4 buttons for the state panel
        JLabel start = new JLabel("Start");    	
    	JLabel startA = new JLabel("Start Accept");
    	JLabel intermediate = new JLabel("Intermediate");
    	JLabel accept = new JLabel("Accept");
    	
    	stateObjectPanel.add(start);
    	stateObjectPanel.add(startA);
    	stateObjectPanel.add(intermediate);
    	stateObjectPanel.add(accept);

        /*JButton startButton = new JButton("Start");
    	JButton startAcceptButton = new JButton("Start Accept");
    	JButton intermediate = new JButton("Intermediate");
    	JButton acceptButton = new JButton("Accept");
    		
    	//adds action listeners to the buttons
    	startButton.addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent e) 
    		{
    			
    			
    		}
    	});
    	startAcceptButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			//stateEvent(2);
    			}
    		});
    	intermediate.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			//stateEvent(3);
    			}
    		});
    	acceptButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			//stateEvent(4);
    			}
    		});
    	
    	//adds the buttons to the state panel
    	stateObjectPanel.add(startButton);
    	stateObjectPanel.add(startAcceptButton);
    	stateObjectPanel.add(intermediate);
    	stateObjectPanel.add(acceptButton); */	
    	  	
        	
    	//adds the state panel to the left side
    	canvasPanel.add(stateObjectPanel, BorderLayout.WEST);
		
		canvasPanel.addMouseListener(this); 
        canvasPanel.addMouseMotionListener(this);
        
        canvas = canvasPanel;      
        
    }  
      
    // PRE: One of the state insert options chosen from menu
    // POST: Creates the appropriate state in predetermined areas
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
      
	// PRE: Mouse is pressed
    // POST: If the mouse is pressed over a state object, that object will be created 
    // and can be dragged across the canvas
	public void mousePressed(MouseEvent e) 
	{
		//checks to see if it is the left click
		if(SwingUtilities.isLeftMouseButton(e))
		{
			// gets the source of button click  1 = left click, 3 = right click
			int xInitial = e.getX();  // x-position of mouse
			int yInitial = e.getY();  // y-position of mouse
			
			State state = isState(xInitial, yInitial);
			if (state != null)
			{
				_canDrag = true;
				_dragFromX = xInitial - _stateX;  // how far from left
	            _dragFromY = yInitial - _stateY;  // how far from top
	            _currentState = state;
			}
			
			else
			{
				_canDrag = true;
				DrawState(xInitial - 25, yInitial - 25);
				State s = new State(Data.StateType.STARTFINAL, "q0", 0, xInitial - 25, yInitial - 25, STATE_DIAMETER);
				_states.add(s);
				_currentState = s;
			}
		}
		
		else
		{
			_canDrag = false;
		}
			
	}
	
	// PRE: Mouse s dragged
	// POST: The state is moved wherever the mouse is
	public void mouseDragged(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e)  && _canDrag == true)
		{			
			int xInitial = e.getX();
			int yInitial = e.getY();
			int x = e.getX() - 25;
			int y = e.getY() - 25;
			
			_stateX = xInitial - _dragFromX;
			_stateY = yInitial - _dragFromY;			
			
			_currentState.setXPosition(x);
            _currentState.setYPosition(y);
            DrawState(x, y);
            RepaintCanvas();    
		}
		
	}	
	
	// PRE: Mouse is released 
	// POST: State is dropped where the mouse is released and the state is
	// added to the collection
	public void mouseReleased(MouseEvent e) 
	{		
		//when mouse is released, it should check to see if it's within a valid area and 
		//create the state and label object and send information to canvas to hold
		//otherwise delete
		if (SwingUtilities.isLeftMouseButton(e)) 
		{
			int xInitial = e.getX();
			int yInitial = e.getY();
			int x = xInitial - 25;
			int y = yInitial - 25;
			
			if (!(xInitial > X_BORDER))
			{
				_states.remove(_currentState);
				stateObjectPanel.repaint();
			}
			
			else
			{
				 DrawState(x, y);
	            _currentState.setXPosition(x);
	            _currentState.setYPosition(y);           
			}
		}
	}
	
	// PRE: The mouse exits the canvas
	// POST: Stop the canvas change
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
		// check if the mouse click event is generated
		// from a right click
		if (e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e) &&
				(e.getX() > X_BORDER && e.getX() < canvas.getWidth()) &&
				e.getY() > Y_BORDER && e.getY() < canvas.getHeight())
		{	
		    // Create the context menu if it is null
			if (contextMenu == null)
			{
				contextMenu = new JPopupMenu();	
			}
			
			else
			{
				// Clear all of the components from the menu item
				// so that menu options may be recreated
				for (Component c : contextMenu.getComponents())
				{
					contextMenu.remove(c);
				}
			}			
			
			contextMenu = CreateMenu(e.getX(), e.getY());			
			
			// Lastly, make the context menu visible
			contextMenu.setLocation(e.getLocationOnScreen());
			contextMenu.setVisible(true);
			
		}
		
		else if (contextMenu != null && contextMenu.isVisible())
		{
			contextMenu.setVisible(false);
		}
	}	
	
	// PRE: The user has right-clicked on the canvas
	// POST: The correct pop up menu is returned 
	public JPopupMenu CreateMenu(int x, int y)
	{
		JMenu insert = new JMenu("Insert");			
		JMenuItem deleteState = new JMenuItem("Delete");
		ImageIcon deleteIcon = new ImageIcon("Images/delete.png");
		deleteState.setIcon(deleteIcon);
		
		JMenuItem copyCanvas = new JMenuItem("Copy Canvas");
		copyCanvas.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {
		    	 CopyCanvas(); 
		    	 contextMenu.setVisible(false);
		      }
		        	
		});
		
		JMenuItem clearCanvas = new JMenuItem("Clear Canvas");
		clearCanvas.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {
		    	ClearCanvas(); 
		    	contextMenu.setVisible(false);
		      }
		        	
		});
		
		final State state = isState(x, y);
		if (state != null)
		{
			// For every state on the canvas add the option to
			// add a transition to that state, including the selected state
			JMenu to = new JMenu("Transition to...");	
			for (State s : _states)
			{
				final State toState = s;
				JMenuItem states = new JMenuItem(toState.getName());
				to.add(states);
				states.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e) 
			      {
			    	  DrawTransition(state, toState);
			    	  contextMenu.setVisible(false);
			      }
			        	
			    });
			}	
					
			// Add insert sub menu items to the insert menu
			insert.add(to);	;	
			
			// If the point is on a state, then add the event in the case
			// that the user chooses the delete state menu option.
			deleteState.addActionListener(new ActionListener()
		    {
			      public void actionPerformed(ActionEvent e) 
			      {			    	  
			    	 DeleteState(state); 
			    	 contextMenu.setVisible(false);
			      }
			        	
			});	
			
		}
		
		// If the point is not on a state disable the insert and delete state menu
		// options.
		else
		{
			insert.setEnabled(false);
			deleteState.setEnabled(false);
		}
		
		// Add all sub menu items to the context menu
		contextMenu.add(insert);
		contextMenu.addSeparator();
		contextMenu.add(deleteState);
		contextMenu.addSeparator();				
		contextMenu.add(copyCanvas);
		contextMenu.addSeparator();
		contextMenu.add(clearCanvas);
		
		return contextMenu;		
	}
	
	// PRE:
	// POST: A state will be drawn onto the canvas
	public void DrawState(int x, int y)
	{
		Graphics g = canvas.getGraphics();		
		g.drawOval(x, y, 50, 50);
		g.drawString("Q1", x + 19, y + 29);	// center of circle		
	}
	
	// PRE: A valid state has been selected to delete
	// POST: The state will be removed from the list of states and 
	// will be removed from the canvas 
	public void DeleteState(State state)	
	{
		// Remove the state from list of all states on canvas
		_states.remove(state);		
		RepaintCanvas();
	}
	
	// PRE: Both a from and to state have been selected 
	// POST: A transition will be drawn between the selected states
	public void DrawTransition(State from, State to)
	{
		Graphics g = canvas.getGraphics();
		
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1; 
		
		if (from.getYPosition() < to.getYPosition())
		{			
			y1 = from.getYPosition() + 50;					
			y2 = to.getYPosition();			
			
			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;
			
			g.drawLine(x1, y1, x2, y2);
			
		}
		
		else
		{
			y1 = from.getYPosition();					
			y2 = to.getYPosition() + 50;			
			
			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;
			
			g.drawLine(x1, y1, x2, y2);
		}
		
	}
	
	// PRE: Given an x and y point on canvas
	// POST: Determines if the point is a state and returns that state
	public State isState(int x, int y)
	{
		State state = null;
		
		for (State s : _states)
		{
			if ((x >= s.getXPosition() && x <= s.getXPosition() + s.getDiameter())
					&& y >= s.getYPosition() && y <= s.getYPosition() + s.getDiameter())
			{
				state = s;
			}
		}
		
		return state;		
	}
	
	// PRE: The copy canvas menu option has been chosen
	// POST: The image of the canvas is saved to the clip board
	public void CopyCanvas()
	{
		
		
	}
	
	// PRE: The clear canvas menu option has been chosen
	// POST: All states and transitions are cleared from the canvas
	public void ClearCanvas()
	{	
		if (_states != null)
		{
			_states.clear();
		}
		
		if (_transitions != null)
		{
			_transitions.clear();
		}

		// Repaint the area of the canvas just where states and transitions can be drawn
		canvas.repaint(X_BORDER, Y_BORDER, canvas.getWidth(), canvas.getHeight());
	}
	
	// PRE: Repaint canvas has been called by another method
	// POST: All existing states and transitions will be repainted onto the canvas
	public void RepaintCanvas()
	{
		//canvas.repaint(X_BORDER, Y_BORDER, canvas.getWidth(), canvas.getHeight());
		canvas.repaint();
		
		// Go through all of the states and re-add them to the canvas
		for (State s : _states)
		{
			DrawState(s.getXPosition(), s.getYPosition());					
		}
		
		// Go through all of the transitions and re-add them onto the canvas
		for (Transition t : _transitions)
		{
			DrawTransition(t.getFrom(), t.getTo());			
		}
	}

// PRE: a valid DFA has been created and a string has been entered for simulation
	// POST: displays Relevant transition graphic
	public void simTransition(State from, State to, boolean symbolMatch, int displayLength )
	{	


		
		Graphics g = canvas.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1; 
		
	if (symbolMatch = true )
	{
	
		 
		if (from.getYPosition() < to.getYPosition())
		{			
			y1 = from.getYPosition() + 50;					
			y2 = to.getYPosition();			

			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;

			
			
			
			
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.YELLOW);
			g2.draw(new Line2D.Float(x1, y1, x2, y2));
			
			
			

		}

		else
		{
			y1 = from.getYPosition();					
			y2 = to.getYPosition() + 50;			

			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;

			
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.red);
			g2.draw(new Line2D.Float(x1, y1, x2, y2));
		}

	}	
	else if(symbolMatch = false)
	{
		if (from.getYPosition() < to.getYPosition())
		{			
			y1 = from.getYPosition() + 50;					
			y2 = to.getYPosition();			

			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;

			
			
			
			
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.red);
			g2.draw(new Line2D.Float(x1, y1, x2, y2));
			
			
			

		}

		else
		{
			y1 = from.getYPosition();					
			y2 = to.getYPosition() + 50;			

			x1 = from.getXPosition() + 25;
			x2 = to.getXPosition() + 25;

			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.red);
			g2.draw(new Line2D.Float(x1, y1, x2, y2));
		}

		
		
		
		
	}
	
	
	
	
	}

}
