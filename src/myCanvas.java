package automataCreator;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
   
public class myCanvas extends JPanel implements MouseListener, MouseMotionListener
{
    private ArrayList<DrawableObject> _drawableObjects;
    private int _states = 0;			      // This is the number of states on the canvas
    private static final int X_BORDER = 73;       // This is the x location where the canvas is able to be drawn on
    private static final int Y_BORDER = 26; 	  // This is the y location where the canvas is able to be drawn on
    private static final int STATE_DIAMETER = 50; // This is the diameter for the state object 
    private int _dragFromX = 0;   				  // pressed this far inside ball's
    private int _dragFromY = 0;    				  // bounding box.
    private int _stateX = 50;      				  // x coordinate - set from drag
    private int _stateY = 50;      				  // y coordinate - set from drag
    private State _currentState;				  // Current state being selected
    private boolean _canDrag  = false;
    
    // State object panel items
    JPanel _stateObjectPanel;
    JButton _start; 	
	JButton _startA;
	JButton _intermediate;
	JButton _accept;
    
    // Context menu items
    private JPopupMenu _contextMenu; 
    private JMenu _insert;
    private JMenu _to;
    private JMenuItem _clearCanvas;
    private JMenuItem _copyCanvas;
    private JMenuItem _deleteState;
    
    // PRE: myCanvas object is instantiated with no parameters
    // POST: myCanvas object is initialized
    public myCanvas()
    {  
    	_drawableObjects = new ArrayList<DrawableObject>();
    	
    }
    
    // PRE: myCanvas object is instantiated with parameters
    // POST: myCanvas object is initialized
    public myCanvas(ArrayList<DrawableObject> objects)
    {
    	_drawableObjects = objects; 
    }
    
    // Set methods
    public void setDrawableObjects(ArrayList<DrawableObject> objects)
    {
    	_drawableObjects = objects;
    }
    
    // Get methods
    public ArrayList<DrawableObject> getDrawableObjects()
    {
    	return _drawableObjects;
    }

    // PRE: AddCanvas is called from constructor
    // POST: myCanvas JFrame is initialized
    public void initializeCanvas()
    {   	
    	this.setBackground(Color.WHITE);
    	this.setLayout(new BorderLayout());
    	
    	// Create panel for 4 states on left side
        _stateObjectPanel = new JPanel(new GridLayout(4,1));
    	
    	// Creates 4 buttons for the state panel
        _start = new JButton();
        // Start State icon
        ImageIcon startIcon = new ImageIcon("Images/start.gif");
        _start.setIcon(startIcon);
        _start.setFocusable(false); // Turn off highlighting after user click
        _start.addActionListener(new ActionListener()
        {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				DrawableObject s = new State(Data.StateType.START, "q0", 0, 200 - 25, 200 - 25, STATE_DIAMETER);
				_drawableObjects.add(s);
				repaint();
			}
        	
        });
        
    	_startA = new JButton();
    	// Start accept Icon
        ImageIcon startAIcon = new ImageIcon("Images/startA.gif");
        _startA.setIcon(startAIcon);
        _startA.setFocusable(false); // Turn off highlighting after user click
        
    	_intermediate = new JButton();
    	// Intermediate state Icon
        ImageIcon intermediateIcon = new ImageIcon("Images/intermediate.gif");
        _intermediate.setIcon(intermediateIcon);
        _intermediate.setFocusable(false); // Turn off highlighting after user click
    	
    	_accept = new JButton();
    	// Accept Icon
        ImageIcon acceptIcon = new ImageIcon("Images/accept.gif");
        _accept.setIcon(acceptIcon);
        _accept.setFocusable(false); // Turn off highlighting after user click
    	
    	_stateObjectPanel.add(_start);
    	_stateObjectPanel.add(_startA);
    	_stateObjectPanel.add(_intermediate);
    	_stateObjectPanel.add(_accept);
	
    	// Adds the state panel to the left side
    	this.add(_stateObjectPanel, BorderLayout.WEST);
		
    	// Add mouse listeners
    	this.addMouseListener(this); 
    	this.addMouseMotionListener(this);
        
        // Add listeners to listen for events generated
        // by the main menu bar, or simulation bar
        addPublicEvents(); 
        
        // Create right-click context menu
        createContextMenu();

    }
     
     // PRE:
     // POST: This class will now listen for events
     // generated by items other than those created
     // in this class
     public void addPublicEvents()
     {
    	// Listen for events generated by the insert > state menu items
     	StateEvents.addStateEventListener(new StateEventListener()
     	{
     		public void stateSelected(StateEvent e)
     		{
     			if (e.getType() == Data.StateType.START)
     			{
     				createStates(1);
     			}
     			
     			else if (e.getType() == Data.StateType.STARTACCEPT)
     			{
     				createStates(2);
     			}
     			
     			else if (e.getType() == Data.StateType.INTERMEDIATE)
     			{
     				createStates(3);
     			}
     			
     			else if (e.getType() == Data.StateType.ACCEPT)
     			{
     				createStates(4);
     			}
     		}
     	}); 
     	
     	// Listen for events generated by the edit > clear canvas menu item
     	ClearCanvasEvents.addClearCanvasEventListener(new ClearCanvasEventListener()
     	{
     		public void clearCanvasSelected(ClearCanvasEvent e)
     		{
     			clearCanvas();
     		}     	
     	});
     }
     
    // PRE: One of the state insert options chosen from menu
    // POST: Creates the appropriate state in predetermined areas
    public void createStates(int stateChoice)
    {   	
    	switch(stateChoice)
    	{
    		case 1:     			
    			break;			
    		case 2: 
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
				_dragFromX = xInitial - _stateX;  // how far from left
	            _dragFromY = yInitial - _stateY;  // how far from top
	            _currentState = state;
			}
		}

			
	}
	
	// PRE: Mouse s dragged
	// POST: The state is moved wherever the mouse is
	public void mouseDragged(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e)  && _canDrag == true)
		{			
			int x = e.getX() - 25;
			int y = e.getY() - 25;
			
			_currentState.setXPosition(x);
            _currentState.setYPosition(y); 
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
			int x = e.getX() - 25;
			int y = e.getY() - 25;
			
			_currentState.setXPosition(x);
	        _currentState.setYPosition(y);  
	        
	        repaint();
			
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
				(e.getX() > X_BORDER && e.getX() < this.getWidth()) &&
				e.getY() > Y_BORDER && e.getY() < this.getHeight())
		{	
			
			final State state = isState(e.getX(), e.getY());
			if (state != null)
			{
				_insert.setEnabled(true);
				addToStatesToContextMenu(state);	
				_deleteState.setEnabled(true);
				_deleteState.addActionListener(new ActionListener()
			    {
				      public void actionPerformed(ActionEvent e) 
				      {
				    	 deleteState(state); 
				    	 _contextMenu.setVisible(false);
				      }
				        	
				});			
			}
			
			else
			{
				_insert.setEnabled(false);
				_deleteState.setEnabled(false);
			}
			
			_copyCanvas.addActionListener(new ActionListener()
		    {
			      public void actionPerformed(ActionEvent e) 
			      {
			    	 copyCanvas(); 
			    	 _contextMenu.setVisible(false);
			      }
			        	
			});
			
			_clearCanvas.addActionListener(new ActionListener()
		    {
			      public void actionPerformed(ActionEvent e) 
			      {
			    	 clearCanvas(); 
			    	 _contextMenu.setVisible(false);
			      }
			        	
			});
			
			
			// Lastly, make the context menu visible
			this.setComponentPopupMenu(_contextMenu);
			_contextMenu.setLocation(e.getLocationOnScreen());
			_contextMenu.setVisible(true);
		
		}
		
		else if (_contextMenu != null && _contextMenu.isVisible())
		{
			_contextMenu.setVisible(false);
		}
	}
	
	public void addToStatesToContextMenu(State from)
	{
		// Remove all of the old to menu items 
		// since the menu could possible change
		// each time the context menu is loaded
		for (Component item : _to.getMenuComponents())
		{
			_to.remove(item);
		}
		
		// For every state on the canvas add the option to
		// add a transition to that state, including the selected state	
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof State)
			{
				final State fromState = from;
				final State toState = (State)obj;
				JMenuItem stateItem = new JMenuItem(toState.getName());
				_to.add(stateItem);
				stateItem.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e) 
			      {
			    	  // Add event handler at run time
			    	  drawTransition(fromState, toState);
			    	  _contextMenu.setVisible(false);
			      }
			        	
			    });
			}
		
		}
	}
		
	// PRE: The user has right-clicked on the canvas
	// POST: The context menu is created  
	public void createContextMenu()
	{
		_contextMenu = new JPopupMenu();
		
		_insert = new JMenu("Insert");	
		
		_to = new JMenu("Transition to...");
		// Add insert sub menu items to the insert menu
		_insert.add(_to);
		
		_copyCanvas = new JMenuItem("Copy Canvas");		
		
		_clearCanvas = new JMenuItem("Clear Canvas");

		_deleteState = new JMenuItem("Delete");
		ImageIcon _deleteIcon = new ImageIcon("Images/delete.png");
		_deleteState.setIcon(_deleteIcon);
		
		// Add all sub menu items to the context menu
		_contextMenu.add(_insert);
		_contextMenu.addSeparator();
		_contextMenu.add(_deleteState);
		_contextMenu.addSeparator();				
		_contextMenu.add(_copyCanvas);
		_contextMenu.addSeparator();
		_contextMenu.add(_clearCanvas);
			
	}
	
	// PRE:
	// POST: A state will be drawn onto the canvas
	public void AddState(int x, int y)
	{
		DrawableObject obj = new State();
		_drawableObjects.add(obj);
		_states++;
		
		repaint();
		
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);		
	}
	
	// PRE: A valid state has been selected to delete
	// POST: The state will be removed from the list of states and 
	// will be removed from the canvas 
	public void deleteState(State state)	
	{
		// Remove the state from list of all states on canvas
		_drawableObjects.remove(state);
		repaint();
		
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);		
	}
	
	// PRE: Both a from and to state have been selected 
	// POST: A transition will be drawn between the selected states
	public void drawTransition(State from, State to)
	{
		Graphics g = this.getGraphics();
		
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
		
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);		
	}
	
	// PRE: A transition is selected to be deleted
	// POST: The selected transition is deleted from the canvas
	// along with its labeled symbol
	public void deleteTransition()
	{
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);
	}
	
	// PRE: Given an x and y point on canvas
	// POST: Determines if the point is a state and returns that state
	public State isState(int x, int y)
	{
		State state = null;
		
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof State)
			{	
				State s = (State) obj;				
				if ((x >= s.getXPosition() && x <= s.getXPosition() + s.getDiameter())
						&& y >= s.getYPosition() && y <= s.getYPosition() + s.getDiameter())
				{
					state = s;
				}
			}
		}
		
		return state;		
	}
	
	// PRE: The copy canvas menu option has been chosen
	// POST: The image of the canvas is saved to the clip board
	public void copyCanvas()
	{
		
		
	}
	
	// PRE: The clear canvas menu option has been chosen
	// POST: All states and transitions are cleared from the canvas
	public void clearCanvas()
	{	
		_drawableObjects.clear();
		repaint();
		
		// Create and send canvas event
		CanvasEvent event = new CanvasEvent(this); 
    	CanvasEvents.sendCanvasEvent(event);
	}
	
	// PRE: Repaint canvas has been called by another method
	// POST: All existing states and transitions will be repainted onto the canvas
	@Override
	public void paintComponent(Graphics g) 
	{
        super.paintComponent(g);       

		//Loop thru a list of drawable objects
		for (DrawableObject d : _drawableObjects)
		{
			d.Draw(g);
		}

	}
}

// CANVAS CHANGED EVENT
// This event will be fired when the canvas is modified
// during adding or deleting a state, adding or deleting 
// a transition, clearing the canvas
@SuppressWarnings("serial")
class CanvasEvent extends EventObject
{
	public CanvasEvent(Object source)
	{
		super(source);
	}

}

interface CanvasEventListener extends EventListener
{
	public void canvasModified(CanvasEvent e);
}

class CanvasEvents
{
    static EventListenerList listenerList = new EventListenerList();
	
    static void addCanvasEventListener(CanvasEventListener l)
	{
		listenerList.add(CanvasEventListener.class, l);
	}
	
	static void removeCanvasEventListener(CanvasEventListener l)
	{
		listenerList.remove(CanvasEventListener.class, l);
	}
	
	static void sendCanvasEvent(CanvasEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == CanvasEventListener.class)
			{
				((CanvasEventListener) listeners[i+1]).canvasModified(e);
			}
		}
	}
}

