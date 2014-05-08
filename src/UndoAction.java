// UndoAction.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is a superclass that all undo events inherit from and
// override the do method. The superclass is intended to make it easier to add
// all different kinds of events to one undo or redo stack.

package automataCreator;

/// Base class for objects that represent actions that can be managed by 
/// the Undo Manager
public abstract class UndoAction
{
    /// Performs the necessary operations to undo the action which this 
    /// object represents
    public abstract void Do();
}
