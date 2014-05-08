// UndoStateMove.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to handle the undo or redo of the 
// relocation of a state. Once the action is undone or redone, the new
// action will be added to the stack so that it can later be undone or redone.

package automataCreator;

// UndoAction for undoing a state move
class UndoStateMove extends UndoAction
{
	  Canvas _parent;
	  boolean _beginAction;
	  State _item;	  
	  int _x;
	  int _y;

	  // Public constructor for creating the undo action for the move of a state
	  public UndoStateMove(Canvas parent, boolean beginAction, State item, int x, int y) 
	  {
		  _parent = parent;
		  _beginAction = beginAction;
	      _item = item;
	      _x = x;
	      _y = y;
	  }
	
	  // Performs the undo action
	  public void Do()
	  {
		  if (_beginAction)
		  {
			  _item.setXPosition(_x);
			  _item.setYPosition(_y);
			  _parent.repaint();
			  _parent.sendCanvasEvent();
		  }
		  
		 // Add the same action back to the undo/re-do stack with _beginAction toggled
		  _parent.getUndoManagement().Add(new UndoStateMove(_parent, !_beginAction, _item, _x, _y));  				  
	  }
}
