// Canvas.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to handle the creation of an automaton.

package automataCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
   
public class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
    private ArrayList<DrawableObject> _drawableObjects;
    private UndoManagement _undoManagement;
    private int _states = 0;			     	  // This is the number of states on the canvas
    private static final int STATE_DIAMETER = 50; // This is the diameter for the state object 
    private int _dragFromX = 0;   				  // pressed this far inside ball's
    private int _dragFromY = 0;    				  // bounding box.
    private int _stateX = 50;      				  // x coordinate - set from drag
    private int _stateY = 50;      				  // y coordinate - set from drag
    private int _validX;					      // valid state insertion x-point	
    private int _validY;				    	  // valid state insertion y-point
    private boolean _validCheck;		  		  // validity check of state insertion
    private State _currentState;
    private Point _currentPoint;
  
    // Context menu items
    private JPopupMenu _contextMenu; 
    private JMenu _insert;
    private JMenu _state;
    private JMenuItem _start;
    private JMenuItem _startA;
    private JMenuItem _intermediate;
    private JMenuItem _accept;
    private JMenu _to;
    private JMenuItem _clearCanvas;
    private JMenuItem _copyCanvas;
    private JMenu _delete;
    private JMenuItem _deleteState;
    private JMenu _deleteTransition;
    
    // PRE: myCanvas object is instantiated with no parameters
    // POST: myCanvas object is initialized
    public Canvas()
    {  
    	_drawableObjects = new ArrayList<DrawableObject>();
    	_undoManagement = new UndoManagement();
 
    }
    
    // PRE: A test is opened from the main window
    // POST: The states and transitions saved in to the
    // opened project will be displayed on the canvas
    public void openProject(ArrayList<DrawableObject> objects)
    {
    	if (_drawableObjects != null)
    	{    		
    		// make sure that if the objects were
    		// saved during simulation that 
    		// that no objects are set to current 
    		// when they are opened from a saved test
    		for (DrawableObject o : objects)
    		{
    			if (o instanceof State)
    			{
    				State state = (State)o;
    				_states++;
    				state.setCurrent(false);
    			}
    			
    			else if (o instanceof Transition)
    			{
    				((Transition)o).setCurrent(false);
    			}
    		}
    		
    		// after updating the objects
    		// set them to local _drawableobjects
    		// array list 
	    	_drawableObjects = objects;	    	
	    	
	    	if (_undoManagement != null)
	    	{
	    		_undoManagement.Empty();
	    	}
	    	
	    	repaint();	    	
			
			// Send canvas event
			sendCanvasEvent();
    	}
    }
    
    // PRE: A new test is selected from the main window
    // POST: All drawable objects from the canvas are removed
    public void newProject()
    {
    	if (_drawableObjects != null)
    	{
    		_drawableObjects.clear();
    		_states = 0;
    		repaint();
    	}
    	
    	if (_undoManagement != null)
    	{
    		_undoManagement.Empty();
    	}

    	sendCanvasEvent();
    }
    
    // Set methods
    public void setDrawableObjects(ArrayList<DrawableObject> objects)
    {
    	_drawableObjects = objects;
    }
    
    public void setUndoManagement(UndoManagement undoManagement)
    {
    	_undoManagement = undoManagement;
    }
    
    public void setStates(int states)
    {
    	_states = states;
    }
    
    // Get methods
    public ArrayList<DrawableObject> getDrawableObjects()
    {
    	return _drawableObjects;
    }
    
    public UndoManagement getUndoManagement()
    {
    	return _undoManagement;
    }
    
    public int getStates()
    {
    	return _states;
    }
    
    // PRE: AddCanvas is called from constructor
    // POST: myCanvas JFrame is initialized
    public void initializeCanvas()
    {    
    	setBackground(Color.WHITE);
    	setLayout(new BorderLayout());
    	
    	setPreferredSize(new Dimension(1800, 1350));
       	    	
    	// Add mouse listeners
    	addMouseListener(this); 
    	addMouseMotionListener(this);
     
        
        // Create right-click context menu
        createContextMenu();
        
    }
          
     public void createState(Data.StateType type)
     {
    	 int _checkSum;
			_validX = 50;
			_validY = 50;
			_validCheck = false; 
			
			while(_validCheck == false && _drawableObjects.isEmpty() != true)
			{
				_checkSum = 0;
				for (DrawableObject obj : _drawableObjects)
	  			{     		
	  				if (obj instanceof State)
	  				{	
	  					State s = (State) obj;   
	  					if ((s.getXPosition() > _validX + s.getDiameter()
	  							|| s.getXPosition() < _validX - s.getDiameter())
	  							|| (s.getYPosition() > _validY + s.getDiameter()
	  							|| s.getYPosition() < _validY - s.getDiameter()))
	  					{
	  						_validCheck = true;   
	  					}        					
	  					else
	  					{
	  						_validCheck = false;
	  						_checkSum++;
	  					}         						
	  				}
	  			}
				
				if(_checkSum > 0)
				{
					_validCheck = false;
					if(_validX < 500)
					{
						_validX += 150;
					}
					else
					{
						_validX = 50;
						_validY += 100;
					}
				}		
				
			}
			
			addState(type, ("q" + _states), _states, _validX, _validY);		
     }
      
	// PRE: Mouse is pressed
    // POST: If the mouse is pressed over a state object, that object will be created 
    // and can be dragged across the canvas
	public void mousePressed(MouseEvent e) 
	{
		// checks to see if it is the left click
		if (SwingUtilities.isLeftMouseButton(e))
		{
			_currentState = null;
			int xInitial = e.getX();  // x-position of mouse
			int yInitial = e.getY();  // y-position of mouse
			
			// check for state at this x and y position
			State state = isState(xInitial, yInitial);
			if (state != null)
			{      
	            _currentState = state;
				
				_dragFromX = xInitial - state.getXPosition();  // how far from left
	            _dragFromY = yInitial - state.getYPosition();  // how far from top
	            
	            // begin the undo block for a state move
				_undoManagement.BlockBegin();
		        _undoManagement.Add(new UndoStateMove(this, true, state, state.getXPosition(), state.getYPosition()));		        		
	            
			}

		}
		
	}
	
	// PRE: Mouse dragged
	// POST: The state is moved wherever the mouse is
	public void mouseDragged(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		if (SwingUtilities.isLeftMouseButton(e) && _currentState != null)
		{
			if (x > 0 && x < 1750 && y > 35 && y < 1300)
			{				
				_currentState.setXPosition(x - _dragFromX);
	            _currentState.setYPosition(y - _dragFromY);
	            
	            repaint();
			}
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
		if (SwingUtilities.isLeftMouseButton(e) && _currentState != null) 
		{
			int xInitial = e.getX();
			int yInitial = e.getY();
			
			if (xInitial > 0 && xInitial < 1750 && yInitial > 35 && yInitial < 1300)
			{	
				int x = xInitial - _dragFromX;
				int y = yInitial - _dragFromY;
				
				_currentState.setXPosition(x);
			    _currentState.setYPosition(y);
			    
			    // end redo block for a state move when the mouse is released 
			    _undoManagement.Add(new UndoStateMove(this, false, _currentState, x, y));
			    _undoManagement.BlockEnd();	
			    
			    _currentState = null;		        
			    repaint();		    
				
				// Send canvas event
				sendCanvasEvent();	
			}
		}
		
		else
		{
			_currentPoint = e.getPoint();
			maybeShowContextMenu(e);
		}
	}
	
	// PRE: Mouse button has been clicked
	// POST: A context menu specific to the location 
	// where a right mouse click event occurs will be created
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		_currentPoint = e.getPoint();
		maybeShowContextMenu(e);
	}
	
	//these mouse actions are ignored
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	private void maybeShowContextMenu(MouseEvent e)
	{
		if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1)
		{
			int x = e.getX();
			int y = e.getY();
			finishCreatingContextMenu(x, y);
			_contextMenu.show(e.getComponent(), x, y);			
		}
	}
	
	// PRE: The user has right-clicked on the canvas
	// POST: The context menu is created  
	private void createContextMenu()
	{
		_contextMenu = new JPopupMenu();
		
		_insert = new JMenu("Insert");	
		
		_state = new JMenu("State");
		_start = new JMenuItem("Start");
		_start.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {		    	  
		    	 createState(Data.StateType.START);
		      }
		        	
		});
		_startA = new JMenuItem("Start Accept");
		_startA.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {		    	  
		    	 createState(Data.StateType.STARTACCEPT);
		      }
		        	
		});
		_intermediate = new JMenuItem("Intermediate");
		_intermediate.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {		    	  
		    	 createState(Data.StateType.INTERMEDIATE);
		      }
		        	
		});
		_accept = new JMenuItem("Accept");
		_accept.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {		    	  
		    	 createState(Data.StateType.ACCEPT);
		      }
		        	
		});
		
		
		_state.add(_start);
		_state.add(_startA);
		_state.add(_intermediate);
		_state.add(_accept);

		_to = new JMenu("Transition to...");
		
		// Add insert sub menu items to the insert menu
		_insert.add(_state);
		_insert.add(_to);

		_copyCanvas = new JMenuItem("Copy Canvas");	
		_copyCanvas.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {		    	  
		    	 copyCanvas(); 
		    	 _contextMenu.setVisible(false);
		      }
		        	
		});
		
		_clearCanvas = new JMenuItem("Clear Canvas");			
		_clearCanvas.addActionListener(new ActionListener()
	    {
		      public void actionPerformed(ActionEvent e) 
		      {
		    	 clearCanvas(); 
		    	 _contextMenu.setVisible(false);
		      }
		        	
		});	

		_delete = new JMenu("Delete");
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource("delete.png"));            
        _delete.setIcon(deleteIcon);
       
        _deleteState = new JMenuItem("State");
        _deleteState.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				 State state = isState((int)_currentPoint.getX(), (int)_currentPoint.getY());
				 if (state != null)
				 {
					 deleteState(state); 	
				 }
			}
        	
        });
        
        _deleteTransition = new JMenu("Transition to...");
        
        _delete.add(_deleteState);
        _delete.add(_deleteTransition);
		
		// Add all sub menu items to the context menu
		_contextMenu.add(_insert);
		_contextMenu.addSeparator();
		_contextMenu.add(_delete);
		_contextMenu.addSeparator();				
		_contextMenu.add(_copyCanvas);
		_contextMenu.addSeparator();
		_contextMenu.add(_clearCanvas);
			
	}
		
	// PRE: Context menu has been created
	// POST: The rest of the context menu items and listeners
	// are added at run time since there is a possibility that they might
	// change
	private void finishCreatingContextMenu(int x, int y)
	{
		// check if the mouse click event is generated
		// from a right click
		if (_contextMenu != null)
		{			
			final State state = isState(x, y);
			if (state != null)
			{
				_to.setEnabled(true);
				_delete.setEnabled(true);
				
				addItemsToInsertTransitionContextMenu(state);				
				addItemsToDeleteTransitionMenu(state);
				
				if (_deleteTransition.getMenuComponents().length > 0)
				{
					_deleteTransition.setEnabled(true);
				}
				
				else
				{
					_deleteTransition.setEnabled(false);
				}
						
			}
			
			else
			{
				_to.setEnabled(false);
				_delete.setEnabled(false);
			}					
		}				
	}
	
	// PRE: The user has right-clicked the canvas and there 
	// are states added to the canvas
	// POST: A transition will be added from a state
	// to itself or another state. The transition will
	// be labeled with a 0, 1, or a 0 and a 1
	private void addItemsToInsertTransitionContextMenu(State from)
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
				JMenu stateItem = new JMenu(toState.getName());
				JMenu symbol = new JMenu("On Symbol...");
				JMenuItem _zero = new JMenuItem("0");
				_zero.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						 // Add event handler at run time
				    	 drawTransition(fromState, toState, Data.Symbol.ZERO);
				    	 _contextMenu.setVisible(false);						
					}
					
				});
				JMenuItem _one = new JMenuItem("1");
				_one.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						 // Add event handler at run time
				    	 drawTransition(fromState, toState, Data.Symbol.ONE);
				    	 _contextMenu.setVisible(false);
					}
					
				});
				JMenuItem _both = new JMenuItem("0, 1");
				_both.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						 // Add event handler at run time
				    	 drawTransition(fromState, toState, Data.Symbol.BOTH);
				    	 _contextMenu.setVisible(false);
						
					}
					
				});
				
				symbol.add(_zero);
				symbol.add(_one);
				symbol.add(_both);
				
				stateItem.add(symbol);
				
				_to.add(stateItem);
			}
		
		}
	}
	
	// PRE: The user has right-clicked the canvas and the state 
	// selected has transitions going to other states or itself
	// POST: The transition selected from the context menu will
	// be deleted from the list of drawable objects as well as the canvas
	private void addItemsToDeleteTransitionMenu(State state)
	{
		// Remove all of the old to menu items 
		// since the menu could possible change
		// each time the context menu is loaded
		for (Component item : _deleteTransition.getMenuComponents())
		{
			_deleteTransition.remove(item);
		}
		
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof Transition)
			{
				final Transition t = (Transition) obj;
				if (t.getFrom() == state)
				{
					JMenuItem transitionItem = new JMenuItem(t.getTo().getName());					
					transitionItem.addActionListener(new ActionListener()
				    {
				      public void actionPerformed(ActionEvent e) 
				      {
				    	  // Add event handler at run time
				    	  deleteTransition(t);
				    	  _contextMenu.setVisible(false);
				      }
				        	
				    });	
					
					_deleteTransition.add(transitionItem);
				}				
			}
		}
	}
		
	
	// PRE:
	// POST: A state will be drawn onto the canvas
	private void addState(Data.StateType type, String name, int number, int x, int y)
	{
		beginUndoCreateDelete();
    
		DrawableObject obj = new State(type, name, number, x, y, STATE_DIAMETER, false);
		_drawableObjects.add(obj);
		_states++;
		
		endUndoCreateDelete();
		
		repaint();
		
		// Create and send canvas event		
		sendCanvasEvent();
	}
	
	// PRE: A valid state has been selected to delete
	// POST: The state will be removed from the list of drawable objects and 
	// will be removed from the canvas. Additionally, the transitions that 
	// are drawn to or from this state will be removed
	private void deleteState(State state)	
	{	
		beginUndoCreateDelete();
		
		// Remove transitions that the deleted state is the from state
		ArrayList<Transition> removeList = new ArrayList<Transition>();
		for (DrawableObject obj : _drawableObjects)
		{			 			
			if (obj instanceof Transition)
			{
				Transition t = (Transition) obj;
				if (t.getFrom() == state || t.getTo() == state)
				{
					removeList.add(t);
				}				
			}			
		}
		
		for (Transition t : removeList)
		{
			_drawableObjects.remove(t);
		}		
		
		// Update labels 
		int num = state.getNumber();
		for (DrawableObject obj : _drawableObjects)
		{			 			
			if (obj instanceof State)
			{
				State s = (State) obj;
				if (s != state && s.getNumber() > num)
				{
					s.setNumber(s.getNumber() - 1);
					s.setName("q" + s.getNumber());
				}			
			}
		}
		
		_states--;
		
		// Remove the state from list of all states on canvas
		_drawableObjects.remove(state);
	
		endUndoCreateDelete();
		
		repaint();
		
		// Send canvas event
		sendCanvasEvent();		    
	}
	
	// PRE: Both a from and to state have been selected 
	// POST: A transition will be drawn between the selected states
	private void drawTransition(State from, State to, Data.Symbol symbol)
	{
		// check to see if a transition already occurs and if
		// it does then just modify the symbol
		// otherwise, if it does not exist then create the transition
		boolean exists = false;
		int _transitionType = 1;
		for (int i = 0; i < _drawableObjects.size() && !exists; i++)
		{			
			if (_drawableObjects.get(i) instanceof Transition)
			{
				Transition t = (Transition) _drawableObjects.get(i);
				if ((t.getFrom() == from && t.getTo() == to)||
						(from == t.getFrom() && to == t.getTo()))
					_transitionType = 2;
				if (t.getFrom() == from && t.getTo() == to)
				{
					exists = true;
					
					// Make sure that the modification of the new symbol
					// is able to be undone and redone
					_undoManagement.BlockBegin();
			        _undoManagement.Add(new UndoModifyTransition(this, true, t, t.getSymbol()));
			        
					t.setSymbol(symbol);
					
			        _undoManagement.Add(new UndoModifyTransition(this, false, t, symbol));
					_undoManagement.BlockEnd();
				}				
			}
		}
		
		if (!exists)
		{
			beginUndoCreateDelete();			

			DrawableObject obj = new Transition(from, to, symbol, false, _transitionType);
			_drawableObjects.add(obj);
			
			endUndoCreateDelete();
		}
		
		repaint();
		
		// Send canvas event
		sendCanvasEvent();		
	}
	
	// PRE: A transition is selected to be deleted
	// POST: The selected transition is deleted from the canvas
	// along with its labeled symbol
	private void deleteTransition(Transition delete)
	{		
		Transition match = null;		
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof Transition)
			{
				Transition t = (Transition) obj;
				if (delete == t)
				{
					match = t;
				}
			}
		}
		
		if (match != null)
		{
			beginUndoCreateDelete();
			
			_drawableObjects.remove(match);	
			
			endUndoCreateDelete();
			
			repaint();
		}
		
		// Send canvas event
		sendCanvasEvent();
	}
		
	// PRE: Given an x and y point on canvas
	// POST: Determines if the point is a state and returns that state
	private State isState(int x, int y)
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
	
	// PRE: A change is made to the canvas
	// POST: The insert start state options are
	// enabled or disabled 
	public void enableStartStates(boolean enable)
	{
		_start.setEnabled(enable);
		_startA.setEnabled(enable);
	}
	
	public void undo()
	{
		_undoManagement.Undo();
	}
	
	public void redo()
	{
		_undoManagement.Redo();
	}
	
	// PRE: The copy canvas menu option has been chosen
	// POST: The image of the canvas is saved to the system clip board
	public void copyCanvas()
	{	
		int max_x = getMaxX(); // get the greatest x position so the entire canvas doesn't have to be copied
		int max_y = getMaxY(); // get the greatest y position so the entire canvas doesn't have to be copied 

		max_x = max_x + 100;
		max_y = max_y + 100;		
		
		//BufferedImage canvasCopy = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB); 
		BufferedImage canvasCopy = new BufferedImage(max_x, max_y, BufferedImage.TYPE_INT_RGB); 
		// paint the canvas onto the canvas copy that will
		// copied to the clip board 
		paint(canvasCopy.getGraphics());  
	
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();		
		ClipboardImage clipboardImage = new ClipboardImage(canvasCopy);		
		clipboard.setContents(clipboardImage, clipboardImage);	
	}
	
	// PRE: 
	// POST: the state that is the farthest along the canvas x position
	// is returned 
	public int getMaxX()
	{
		int max = 0;
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof State)
			{
				State state = (State)obj;
				if (state.getXPosition() > max)
				{
					max = state.getXPosition();
				}
			}
		}
		
		return max;
		
	}
	
	// PRE: 
	// POST: the state that is the farthest along the canvas y position
	// is returned 
	public int getMaxY()
	{
		int max = 0;
		for (DrawableObject obj : _drawableObjects)
		{
			if (obj instanceof State)
			{
				State state = (State)obj;
				if (state.getYPosition() > max)
				{
					max = state.getYPosition();
				}
			}
		}
		
		return max;
		
	}

	// PRE: The clear canvas menu option has been chosen
	// POST: All states and transitions are cleared from the canvas
	public void clearCanvas()
	{
		beginUndoCreateDelete();
		
		_drawableObjects.clear();
		_states = 0;
		
		endUndoCreateDelete();
		
		repaint();
		
		// Create and send canvas event
		sendCanvasEvent();
	}
	
	
	// PRE: Before an action is performed
	// POST: The previous state of the object before an
	// action is done that can be undone is performed is
	// put on the stack
	private void beginUndoCreateDelete()
	{
		if (_undoManagement != null)
		{
			ArrayList<DrawableObject> obj = new ArrayList<DrawableObject>();
			for (DrawableObject o : _drawableObjects)
				obj.add(o);
			
			_undoManagement.BlockBegin();
	        _undoManagement.Add(new UndoCreateDelete(this, true, obj));
		}
	}
	
	// PRE: After an action is performed
	// POST: The new state of the object after the
	// action is done is put on the stack 
	private void endUndoCreateDelete()
	{
		if (_undoManagement != null)
		{
			ArrayList<DrawableObject> obj = new ArrayList<DrawableObject>();
			for (DrawableObject o : _drawableObjects)
				obj.add(o);
			
			_undoManagement.Add(new UndoCreateDelete(this, false, obj)); 
			_undoManagement.BlockEnd();
		}
	}
	
	// Begin Canvas Events Methods
	
	public void sendCanvasEvent()
	{
		CanvasEvent event = new CanvasEvent(this); 
		CanvasEvents.sendCanvasEvent(event);
	}	
	
	// End Canvas Events Methods
	
	
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
	
	// PRE: repaint is called from another class
	// POST: Drawable objects on the canvas are repainted
	public void repaintCanvas()
	{
		repaint();
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

