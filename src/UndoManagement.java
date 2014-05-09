// UndoMangement.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is responsible for adding undo actions to the
// undo and redo stacks. Also, the class is responsible for generating 
// events to notify listeners whether the stacks have undo or redo events, 
// or whether they become empty. 

package automataCreator;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Stack;

import javax.swing.event.EventListenerList;

/// UndoManager manages the undo and re-do stacks
public class UndoManagement
{
    // Need 3 stacks:
    // Undo Stack - keeps track of operations to undo
    // Re-do Stack - keeps track of operations to re-do
    // Active Stack - keeps track of the stack to which undo actions get posted. One undo action may
	// consist of multiple actions, so we have to keep track of which actions are part of 1 undo action
    Stack<Object> _activeStack;
    Stack<Object> _undoStack;
    Stack<Object> _redoStack;

    // Need to keep track of whether the add/begin/add is coming from an Undo/Re-do
    // or a new action.
    boolean _isUndoRedo;

    // Public constructor
    public UndoManagement()
    {
        // Create the stacks
        _undoStack = new Stack<Object>();
        _redoStack = new Stack<Object>();
        _activeStack = new Stack<Object>();

        // The undo stack is the active stack
        _activeStack.push(_undoStack);

        // The first action will not be an Undo/Re-do
        _isUndoRedo = false;
    }

    // PRE: An action has been performed 
    // POST: Adds an action or block to the active stack.  The active stack is either 
    // the undo stack, the re-do stack (if we are performing an undo operation) or 
    // a helper stack used to group operations.
    public void Add(UndoAction action)
    {
        AddItemToActiveStack(action);
    }

    // PRE: The series of steps within one action is added to the active stack 
    // POST: Helper function for adding items to the active stack.  The public function 
    // allows only UndoAction to be added to the stack.  
    private void AddItemToActiveStack(Object item)
    {
        // If this action is not coming from Undo/Re-do and there are items in the 
        // Re-do stack, empty the re-do stack
        if ((!_isUndoRedo) && (_redoStack.size() != 0))
            _redoStack.clear();

        if (_activeStack.size() > 0)
        {
            // Get the stack to which we are adding the action and add this action
            Stack<Object> theStack = (Stack<Object>) _activeStack.peek();
            theStack.push(item);
        }
        
        // Generate events, if needed
        NotifyListeners();
    }

    // PRE: The stacks are created 
    // POST: Clears all stacks, so that all of the actions of block of actions
    // from the undo, re-do and active stacks are gone. The user should
    // not be able to undo or re-do any of the actions that were previously
    // on these stacks. The undo and re-do menu options should become
    // disabled. 
    public void Empty()
    {
        if (_undoStack.size() > 0)
        {
            _undoStack.clear();
        }

        if (_redoStack.size() > 0)
        {
            _redoStack.clear();
        }

        if (_activeStack.size() > 0)
        {
            _activeStack.clear();
            _activeStack.push(_undoStack);
        }

        NotifyListeners();
    }

    // PRE: 
    // POST: Removes an action or block of actions from the Undo stack and asks
    // the action to do what is necessary to undo the action.
    public void Undo()
    {
        // Continue only if there are items in the undo stack
        if (_undoStack.size() > 0)
        {
            // Set the Undo/Re-do flag
            _isUndoRedo = true;

            // Make the re-do stack the active action - that way any operations performed
            // during the undo operation will automatically added to the re-do stack
            _activeStack.push(_redoStack);
            try
            {
                // Remove the action on top of the undo stack and process the action
                Object undoAction = _undoStack.pop();
                ProcessAction(undoAction);
            }
            finally
            {
                // Re-do stack is no longer the active stack - undo operation is done
                _activeStack.pop();
            }

            // Clear the Undo/Re-do flag
            _isUndoRedo = false;

            // Generate events, if needed
            NotifyListeners();
        }
    }

    // PRE: 
    // POST: Removes an action or block of actions from the re-do stack and asks
    // the action to do what is necessary to re-do the action
    public void Redo()
    {
        // Continue only if there are items in the re-do stack
        if (_redoStack.size() > 0)
        {
            // Set the Undo/Re-do flag
            _isUndoRedo = true;

            // Remove the action on top of the re-do stack and process the action
            Object redoAction = _redoStack.pop();
            ProcessAction(redoAction);

            // Clear the Undo/Re-do flag
            _isUndoRedo = false;

            // Generate events, if needed
            NotifyListeners();
        }
    }

    // PRE: This is called before the action is actually performed 
    // POST: Marks the beginning of a block of operations to add to the undo or
    // re-do stacks
    public void BlockBegin()
    {
        // Create a stack to keep track of the block of actions
        Stack<Object> blockStack = new Stack<Object>();
        // Make this stack the active stack
        _activeStack.push(blockStack);
    }

    // PRE: This is called after the action has been performed 
    // POST: Marks the end of a block of operations to add to the undo or re-do
    // stacks
    public void BlockEnd()
    {
        // The block of operations we've been working on is currently the
        // active stack - remove it from the active stack and add it to the
        // active stack.
        Object blockStack = _activeStack.pop();
        AddItemToActiveStack(blockStack);
    }

    // PRE: 
    // POST: Helper function for processing an item removed from the undo or re-do
    // stack.
    private void ProcessAction(Object item)
    {
        if (item instanceof UndoAction)
        {
            // The item is an UndoAction - ask the action to do whatever is necessary
            // to undo the operation that it represents
            UndoAction action = (UndoAction) item;
            action.Do();
        }
        else if (item instanceof Stack)
        {
            // The item is a Stack (representing a block of operations that need to be
            // undone. Begin a new block (so that we can keep all the operations together,
            // and process each item in the stack.
			Stack<Object> blockStack = (Stack<Object>)item;
            
            BlockBegin();
            
            for (Object o : blockStack)
            {
                ProcessAction(o);
            }
            
            BlockEnd();            
        }       
        
    }
    
    // PRE: An event has been sent 
    // POST: Generates an event notifying listeners
    // the status of the stack
    private void NotifyListeners()
    {
        int undoCount = _undoStack.size();
        int redoCount = _redoStack.size();

        // If the undo stack is empty 
        if (undoCount == 0)
        {
        	UndoEvent event = new UndoEvent(this, true); 
    		UndoEvents.sendUndoEvent(event);
            
        }

        // If the count is 1 - then there is undo information available 
        if (undoCount == 1)
        {
        	UndoEvent event = new UndoEvent(this, false); 
    		UndoEvents.sendUndoEvent(event);
            
        }

        // If the re-do stack is empty 
        if (redoCount == 0)
        {
        	RedoEvent event = new RedoEvent(this, true); 
    		RedoEvents.sendRedoEvent(event);
            
        }

        // If the count is 1 - then there is re-do information available 
        if (redoCount == 1)
        {
        	RedoEvent event = new RedoEvent(this, false); 
    		RedoEvents.sendRedoEvent(event);
        }
    }
}

// Undo Event
@SuppressWarnings("serial")
class UndoEvent extends EventObject
{
	boolean _isEmpty;
	public UndoEvent(Object source, boolean isEmpty)
	{
		super(source);
		_isEmpty = isEmpty;
	}
}

interface UndoEventListener extends EventListener
{
	public void undoEvent(UndoEvent e);
}

class UndoEvents
{
    static EventListenerList listenerList = new EventListenerList();
		
	static void addUndoEventListener(UndoEventListener l)
	{
		listenerList.add(UndoEventListener.class, l);
	}
	
	static void removeUndoEventListener(UndoEventListener l)
	{
		listenerList.remove(UndoEventListener.class, l);
	}
	
	static void sendUndoEvent(UndoEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == UndoEventListener.class)
			{
				((UndoEventListener) listeners[i+1]).undoEvent(e);
			}
		}
	}
}

@SuppressWarnings("serial")
class RedoEvent extends EventObject
{
	boolean _isEmpty;
	public RedoEvent(Object source, boolean isEmpty)
	{
		super(source);
		_isEmpty = isEmpty;
	}
}

interface RedoEventListener extends EventListener
{
	public void redoEvent(RedoEvent e);
}

class RedoEvents
{
    static EventListenerList listenerList = new EventListenerList();
		
	static void addRedoEventListener(RedoEventListener l)
	{
		listenerList.add(RedoEventListener.class, l);
	}
	
	static void removeRedoEventListener(RedoEventListener l)
	{
		listenerList.remove(RedoEventListener.class, l);
	}
	
	static void sendRedoEvent(RedoEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++)
		{
			if (listeners[i] == RedoEventListener.class)
			{
				((RedoEventListener) listeners[i+1]).redoEvent(e);
			}
		}
	}
}





