package MM;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Draw's a game component-
 * <p>
 * One Instance of this class draws the content for one
 * game attempt and handle's the mouse events and also 
 * the action event.
 * 
 * @author Ingo Sewing
 * @date 16.12.2022
 */
@SuppressWarnings("serial")
public class GameComponent extends JComponent implements ActionListener,MouseListener,MouseMotionListener{
	
	/**
	 * X-position of the game component
	 */
	private int posX;
	
	/**
	 * Y-position of the game component
	 */
	private int posY;
	
	/**
	 * Size in x-direction of the game component
	 */
	private int sizeX;
	
	/**
	 * Size in y-direction of the game component
	 */
	private int sizeY;
	
	/**
	 * This array contains the result of an attempt.
	 */
	private char[] result;
	
	/**
	 * Array for to save the selected color's.
	 */
	private char[] selected;
	
	/**
	 * Array for to save if a color is selected.
	 */
	private boolean[] isSelected;
	
	/**
	 * Y-position of the 4 game circle's relative to the game component.
	 */
	private int yPosGame;
	
	/**
	 * The distance in x-direction between the circle's. 
	 */
	private int sdist;
	
	/**
	 * The diameter of a selection circle.
	 */
	private int smaxSize;

	/**
	 * X-position's of the selection circle's.
	 */
	private int[] xPosGame;
	
	/**
	 * Save's the current selected game oval.
	 */
	private int currentGameOval;
	
	/**
	 * The Button to check the game attempt.
	 */
	private JButton playButton;
	
	/**
	 * The color dialog to select a color for an attempt.
	 */
	private ColorDialog colorDialog;

	/**
	 * Construct's a game component
	 * 
	 * @param posX The x-position where the game component should be created.
	 * @param posY The y-position where the game component should be created.
	 * @param sizeX The size in x-direction the component should have.
	 * @param sizeY The size in y-direction the component should have.
	 */
	public GameComponent(int posX,int posY, int sizeX,int sizeY) {
		super();
		this.setLayout(null);
		result=new char[]{'n','n','n','n'};
		selected = new char[]{'n','n','n','n'};
		isSelected = new boolean[] {false,false,false,false};
		xPosGame = new int[4];	
		playButton=new JButton("Try");
		playButton.addActionListener(this);
		add(playButton);
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setBounds(posX, posY, sizeX, sizeY);	
		this.setVisible(true);
	}
	
	@Override
	public void setBounds(int posX,int posY,int sizeX,int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.posX = posX;
		this.posY = posY;
		super.setBounds(posX, posY, sizeX, sizeY);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   
		
		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        rh.put(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
        rh.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
						
		//calculate result sizes
		int rmaxSize = sizeY/3;
		int rdist = rmaxSize/3;
		int xpos;
		int ypos;
		
		//draw result field
		for(int j=0; j<4; j++){
			if(j==1 || j==3) {
				xpos=2*rdist+rmaxSize;
			}
			else{
				xpos=rdist;
			}
			if(j==2 || j==3) {
				ypos=rdist;
			}
			else {
				ypos=2*rdist+rmaxSize;
			}
			switch(result[j]) {
				case 'n': 					
					g2d.setColor(Color.GRAY);
					g2d.fillOval(xpos, ypos, rmaxSize, rmaxSize);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(xpos, ypos, rmaxSize, rmaxSize);
					break;
				case 'b': 
					g2d.setColor(Color.BLACK);
					g2d.fillOval(xpos, ypos, rmaxSize, rmaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xpos, ypos, rmaxSize, rmaxSize);
					break;
				case 'w': 
					g2d.setColor(Color.WHITE);
					g2d.fillOval(xpos, ypos, rmaxSize, rmaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xpos, ypos, rmaxSize, rmaxSize);		
					break;
			}
		}
		
		//calculate selection position
		sdist = (sizeX-2*sizeY)/5;
		smaxSize = sizeY/2;
		yPosGame = sizeY/2-smaxSize/2;		
		xpos = sizeY+sdist-smaxSize/2;
		
		//draw selection field
		for(int j=0; j<4; j++){
			//Save points for mouse moved event
			xPosGame[j] = xpos+j*sdist;

			switch(selected[j]) {
				case 'n': 	
					g2d.setColor(Color.GRAY);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.BLACK);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'r': 
					g2d.setColor(Color.RED);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'y': 
					g2d.setColor(Color.YELLOW);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'g': 		
					g2d.setColor(Color.GREEN);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'b': 
					g2d.setColor(Color.BLUE);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'm': 
					g2d.setColor(Color.MAGENTA);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
				case 'c': 
					g2d.setColor(Color.CYAN);
					g2d.fillOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					g2d.setColor(Color.GRAY);
					g2d.drawOval(xPosGame[j] , yPosGame, smaxSize, smaxSize);
					break;
			}
		}
		
		//add play button
		Font font =new Font("Arial", Font.BOLD, sizeY/5);
		playButton.setFont(font);
		playButton.setBounds(sizeX-sizeY, yPosGame, sizeY-rdist, sizeY/2);
	}
	
	/**
	 * Set's the array's for the current selected color circle
	 * 
	 * @param c The color selected for the current circle
	 */
	public void setSingleResult(char c) {
		selected[currentGameOval]=c;
		isSelected[currentGameOval] = true;
		colorDialog.setVisible(false);
	}
	
	/**
	 * Get the color dialog
	 * 
	 * @return The current dialog, if destroyed null is returned
	 */
	public ColorDialog getColorDialog() {
		return colorDialog;
	}
	
	/**
	 * Destroy's the color dialog.
	 */
	public void destroyColorDialog() {
		colorDialog.setVisible(false);
		colorDialog.dispose();
		colorDialog = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//wait until all fields are selected
		for(int i=0; i<4; i++) {
			if(isSelected[i]==false) {
				return;
			}
		}
		playButton.setEnabled(false);
		result = GameLogic.checkGameTry(selected);
		
		// Check whether the guess was successful.
		int countBlack=0;
		for(int i=0; i<4; i++) {
			if(result[i]=='b'){
				countBlack++;
			}
		}
		
		Component parent = getParent();
		Playground pg = (Playground)parent;
		
		// If the guess was successful.
		if(countBlack==4){
			
			String name = (String)JOptionPane.showInputDialog(this, "Successful guess!\\n Save player?", "Got it!", JOptionPane.INFORMATION_MESSAGE);
			
			// Only save if there is a string
			if(!name.equals("")) {
				LocalDate date = LocalDate.now();
				int gameTries = pg.getAttempt();
				String duration = GameLogic.duration();
				Players players = new Players();
				players.addPlayer(name, date, duration, gameTries);
				players.savePlayers();
				StatisticPanel.getInstance().createTableDataInput();
				PlayersPanel.getInstance().createTableDataInput();
			}

			pg.stopGame();
			
			CardLayout cl = (CardLayout)(((JPanel)parent.getParent()).getLayout());
			cl.show((JPanel)parent.getParent(), "START");
		}
		else {
			pg.increaseGameTry();
		}	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int xMouse = e.getX();
		int yMouse = e.getY();
		
		// Opens the color dialog if the player clicked into a circle
		if( (yPosGame < yMouse) && yMouse < (yPosGame + smaxSize) ) {
			for (int i=0; i<4; ++i) {
				
				if( xPosGame[i] < xMouse && xMouse < xPosGame[i] + smaxSize ) {
					currentGameOval=i;
					Point location = getLocationOnScreen();		
					if(colorDialog==null) {
						colorDialog = new ColorDialog(getBackground(),this);
					}
					colorDialog.setVisible(true);
					colorDialog.setBounds(location.x + xPosGame[i] +  smaxSize,location.y +  yPosGame,sdist*5 , sizeY);
					colorDialog.setBackground(getBackground());
					colorDialog.setAlwaysOnTop(true);
					break;
				}
			}
		}
	}

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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int xMouse = e.getX();
		int yMouse = e.getY();
		
		if( (yPosGame < yMouse) && yMouse < (yPosGame + smaxSize) ) {
			for (int i=0; i<4; ++i) {
				if( xPosGame[i] < xMouse && xMouse < (xPosGame[i] + smaxSize) ) {
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
