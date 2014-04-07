package automataCreator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;



class myCanvas extends JPanel implements MouseListener
{
    Image img;      // Contains the image to draw on MyCanvas

    public static void addCanvas(JScrollPane canvasPane)
    {
    	Canvas automataCanvas = new Canvas();
    	automataCanvas.setSize(675, 600);
    	automataCanvas.setBackground(Color.WHITE);
		
    	canvasPane.add(automataCanvas);
		
	
	
	}
    
   
 

//    public void paintComponent(Graphics g)
//    {
//        // Draws the image to the canvas
//        g.drawImage(img, 0, 0, null);
//    }
//
//    public void mouseClicked(MouseEvent e)
//    {
//        int x = e.getX();
//        int y = e.getY();
//
//        Graphics g = img.getGraphics();
//        g.fillOval(x, y, 3, 3);
//        g.dispose();
//    }
//
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

////	
//
//    // ... other MouseListener methods ... //
}
