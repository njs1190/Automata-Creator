// UndoModifyTransition.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is used to handle the undo or redo of the change of a symbol 
// for a transition. Once the action is undone or redone, the new
// action will be added to the stack so that it can later be undone or redone.

package automataCreator;

// UndoAction for undoing a transition symbol change
class UndoModifyTransition extends UndoAction
{
	  Canvas _parent;
	  boolean _beginAction;
	  Transition _item;
	  Data.Symbol _symbol;

	  // Public constructor for creating the undo action for a modification of a transition symbol
	  public UndoModifyTransition(Canvas parent, boolean beginAction, Transition item, Data.Symbol symbol) 
	  {
		  _parent = parent;
		  _beginAction = beginAction;
		  _item = item;
	      _symbol = symbol;
	  }
	
	  // Performs the undo action
	  public void Do()
	  {
		  if (_beginAction)
		  {
			  _item.setSymbol(_symbol);
			  _parent.repaint();
			  _parent.sendCanvasEvent();
		  }
		  
		 // Add the same action back to the undo/re-do stack with _beginAction toggled
		  _parent.getUndoManagement().Add(new UndoModifyTransition(_parent, !_beginAction, _item, _symbol));  				  
	  }
}

