// UndoCreateDelete.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to handle the undo or redo of the creation or
// deletion of a drawable object. Once the action is undone or redone, the new
// action will be added to the stack so that it can later be undone or redone.

package automataCreator;

import java.util.ArrayList;

// UndoAction for undoing a transition or state item create
class UndoCreateDelete extends UndoAction
{
   Canvas _parent;
   boolean _beginAction;
   ArrayList<DrawableObject> _collection;

  // Public constructor for creating the undo action for a Create of a transition or state
  public UndoCreateDelete(Canvas parent, boolean beginAction, ArrayList<DrawableObject> collection) 
  {
	  _parent = parent;
	  _beginAction = beginAction;
      _collection = collection;
  }

  // Performs the undo action
  public void Do()
  {
	  if (_beginAction)
	  {
		  _parent.setDrawableObjects(_collection);		  
		  int states = 0;
		  for (DrawableObject o : _collection)
		  {
			  if (o instanceof State)
				  states++;
			  
			  else if (o instanceof Transition)
			  {
				  Transition t = (Transition) o;
			  	  t.setCurrent(false);
			  }
		  }		  
		  _parent.setStates(states);		  
		  _parent.repaint();
		  _parent.sendCanvasEvent();
	  }
	  
	  // Add the same action back to the undo/re-do stack with _beginAction toggled
      _parent.getUndoManagement().Add(new UndoCreateDelete(_parent, !_beginAction, _collection));
  }
}
  
  

