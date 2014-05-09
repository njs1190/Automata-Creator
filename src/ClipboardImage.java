// ClipboardImage.java
// Author: Jidaeno
// Course: CSC4910
// Description: This class is responsible for transferring the canvas as an image to the system clipboard. 
// This class is based off of a tutorial on http://omtlab.com/java-store-image-in-clipboard/ on copying and 
// pasting images to the system clipboard.
// A tutorial was needed for this functionality because as a group we were all unfamiliar with this task. 

package automataCreator;

import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
 
// PRE: Copy has been selected
// POST: Canvas is copied as an image to the system clipboard 
public class ClipboardImage implements Transferable, ClipboardOwner 
{
 
    private Image _canvasCopy;
 
    public ClipboardImage(Image canvasCopy) 
    {
        this._canvasCopy = canvasCopy;
    }
 
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException 
    {
 
        if (flavor.equals(DataFlavor.imageFlavor) && _canvasCopy != null) 
        {
            return _canvasCopy;
        } 
        
        else 
        {
            throw new UnsupportedFlavorException(flavor);
        }
    }
 
    @Override
    public DataFlavor[] getTransferDataFlavors() 
    {
        DataFlavor[] flavors = new DataFlavor[1];
        flavors[0] = DataFlavor.imageFlavor;
        return flavors;
    }
 
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) 
    {
        DataFlavor[] flavors = getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++) {
            if (flavor.equals(flavors[i])) 
            {
            	return true;                
            }
        }
        
        return false;
    }
 
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) 
    {
    }
 
}
