package MM;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

/**
 * This class creates a dialog to select color's.
 * <p>
 * Creates a JDialog with a JPanel which shows all 6 possible
 * Color's that can selected, handles the mouse drag event and 
 * the mouse click event to select a color.
 * 
 * @author Ingo Sewing
 * @date 17.12.2022
 */
@SuppressWarnings("serial")
public class ColorDialog extends JDialog implements MouseListener, MouseMotionListener{
	
	/**
	 * Contains the graphic content
	 */
	private DialogPanel dialogPanel;
	
	/**
	 * Contains the parent game component
	 */
	private GameComponent parentComponent;
	
	/**
	 * Creates the JDialog to select one color for a game attempt
	 * 
	 * @param col The background color from the parent component (GameComponent)
	 * @param parentComponent 
	 */
	public ColorDialog(Color col, GameComponent parentComponent) {
		super();
		setUndecorated(true);
		getRootPane().setBorder( BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.GRAY) );
		this.parentComponent=parentComponent;
		dialogPanel = new DialogPanel();
		dialogPanel.addMouseListener(this);
		dialogPanel.addMouseMotionListener(this);
		dialogPanel.setBackground(col);
		setVisible(true);	
		add(dialogPanel);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// get position of mouse pointer
		int mousePosX =  e.getX();
		int mousePosY =  e.getY();
		int ypos = dialogPanel.getyPosition();
		int radius = dialogPanel.getRadius();
		int diameter =2*radius;
		
		// set the selected color in the parent game component
		if(ypos < mousePosY && mousePosY < ypos +diameter){			
			for(int i = 0; i<6; ++i){				
				if(dialogPanel.getxPositions()[i] < mousePosX && mousePosX < dialogPanel.getxPositions()[i] + diameter){					
					dialogPanel.getColor(i);
					parentComponent.setSingleResult(dialogPanel.getColor(i));
					break;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//setBounds(mousePosX, ypos, width, height);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//change Cursor and set new bounds 
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		setBounds(getX()+e.getX(), getY()+e.getY(), getWidth(), getHeight());
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		// get position of mouse pointer
		int mousePosX =  e.getX();
		int mousePosY =  e.getY();
		int ypos = dialogPanel.getyPosition();
		int radius = dialogPanel.getRadius();
		int diameter =2*radius;
		
		// Change Cursor if the mouse entered a color selection field
		if(ypos < mousePosY && mousePosY < ypos +diameter){			
			for(int i = 0; i<6; ++i){				
				if(dialogPanel.getxPositions()[i] < mousePosX && mousePosX < dialogPanel.getxPositions()[i] + diameter){					
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					break;
				}
				else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}	
			}
		}		
	}
}