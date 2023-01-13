package MM;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * The JPanel which contains the graphic content displayed in the ColorDialog.
 * <p>
 * This panel show all 6 color's that can selected for a game try.
 * 
 * @author Ingo Sewing
 * @date 18.12.2022
 */
@SuppressWarnings("serial")
public class DialogPanel extends JPanel{

	/**
	 * Array contains the x-positions of the color circles
	 */
	private int[] xPositions;
	
	/**
	 * The y-position is for all circles the same
	 */
	private int yPosition;
	
	/**
	 * The radius of the circles.
	 */
	private int radius;
	
	/**
	 * Color array of all available color's
	 */
	private char[] co = {'r','y','b','g','m','c'};
	
	/**
	 * Get x-positions of the color circle's
	 * 
	 * @return array of x-positions
	 */
	public int[] getxPositions() {
		return xPositions;
	}

	/**
	 * Get color at specified index.
	 * 
	 * @param i Index for the color char to return.
	 * @return The color at index i.
	 */
	public char getColor(int i) {
		return co[i];
	}

	/**
	 * Get the y-position of the color circle's.
	 * 
	 * @return The y-position.
	 */
	public int getyPosition() {
		return yPosition;
	}

	/**
	 * Get the radius of the circle's.
	 * 
	 * @return Radius of the selection circle's.
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Construct's the dialog panel.
	 */
	public DialogPanel() {
		super();
		xPositions = new int[6];
	}
	
	@Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d =(Graphics2D)g;
        
        //calculate positions
        int width = getWidth();
        int height = getHeight();
        int diameter = height/2;
        
        radius = diameter/2;
        yPosition = radius;
        int dist = width/7;
        xPositions[0] = dist - radius;        
        
        // Draw the circle's
        g2d.setColor(Color.RED);
        g2d.fillOval(xPositions[0], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[0], yPosition, diameter, diameter);
        
        xPositions[1]=xPositions[0]+dist;
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(xPositions[1], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[1], yPosition, diameter, diameter);
        
        xPositions[2]=xPositions[1]+dist;
        g2d.setColor(Color.BLUE);
        g2d.fillOval(xPositions[2], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[2], yPosition, diameter, diameter);
        
        xPositions[3]=xPositions[2]+dist;
        g2d.setColor(Color.GREEN);
        g2d.fillOval(xPositions[3], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[3], yPosition, diameter, diameter);
        
        xPositions[4]=xPositions[3]+dist;
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(xPositions[4], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[4], yPosition, diameter, diameter);
        
        xPositions[5]=xPositions[4]+dist;
        g2d.setColor(Color.CYAN);
        g2d.fillOval(xPositions[5], yPosition, diameter, diameter);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(xPositions[5], yPosition, diameter, diameter);    
	}
}