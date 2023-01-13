package MM;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class creates the playground and handle players input.
 * 
 * @author Ingo Sewing 
 * @Date 10.12.2022
 * @version 1.0
 */
@SuppressWarnings("serial")
public final class Playground extends JPanel implements LayoutManager, ActionListener{
	
	/**
	 * Holds the width of the playground
	 */
	private int width;
	
	/**
	 * Holds the height of the playground
	 */
	private int height;
		
	/**
	 * Holds the height of a game component
	 */
	private int gameComponentheight;
	
	/**
	 * Holds the game components for the single game attempt
	 */
	private GameComponent[] gameComponents;
	
	/**
	 * Holds the number of attempts
	 */
	private int attempt;

	/**
	 * Label for the game name "Mastermind"
	 */
	private JLabel lblMastermind;
	
	/**
	 * Button to end the game
	 */
	private JButton cancelButton;
	
	/**
	 * The background color of the playground
	 */
	private Color backGroundColor;

	/**
	 * Constructs the playground
	 * 
	 * @param width Sets the width of the playground
	 * @param height Sets the height of the playground (without menu height from MainGui)
	 */
	public Playground(int width, int height) {
		super();
		this.width=width;
		this.height=height;
		attempt=1;
		this.setLayout(this);
		
		lblMastermind = new JLabel("Mastermind",SwingConstants.CENTER);
		add(lblMastermind);
		this.setSize(this.width, this.height);
		gameComponentheight = height/14;		
		cancelButton = new JButton("Cancel");		
		cancelButton.addActionListener(this);	
		cancelButton.setAlignmentX(CENTER_ALIGNMENT);
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setFocusPainted(false);
		add(cancelButton);
		
		backGroundColor = new Color(139, 101, 8);
		setBackground(backGroundColor);
		gameComponents = new GameComponent[12];
		setVisible(true);
	}
	
	@Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d =(Graphics2D)g;
        
        //calculate game component size
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int labelPartsX = width/10;
        int labelWidth = labelPartsX*8;
        int labelX = labelPartsX;
        int labelY = gameComponentheight/9;
        double xyRelation = (double)width/(double)height;
        int labelHeight = (int)(labelY*16*xyRelation);
        int maxlabelHeight = labelY*8;
        if(labelHeight>labelY*8) {
        	labelHeight = maxlabelHeight;
        }
	
		lblMastermind.setHorizontalAlignment(SwingConstants.CENTER);
		lblMastermind.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, labelHeight));
		lblMastermind.setBounds(labelX, labelY, labelWidth, labelHeight);
		
		// compute position and size for cancel Button
		int cancelButtonWidth = width/4;
		int cancelButtonHeight = gameComponentheight/2;
		int cancelButtonXpos = (width-cancelButtonWidth)/2;
		int cancelButtonYpos = height - gameComponentheight + (gameComponentheight-cancelButtonHeight)/2;
		
		cancelButton.setBounds(cancelButtonXpos, cancelButtonYpos, cancelButtonWidth, cancelButtonHeight);
		
		// draw game components
        for(int i=0; i<attempt; i++) {
        	if(gameComponents[i] == null) {
        		gameComponents[i] = new GameComponent(20,(height-(i+2)*gameComponentheight),width-40, gameComponentheight);
        		add(gameComponents[i]);
        	}
        	g2d.drawLine(0, height-(i+1)*gameComponentheight-1, width, height-(i+1)*gameComponentheight-1);
        	gameComponents[i].setVisible(true);
        	gameComponents[i].setBackground(backGroundColor);
        }
    }
		
	/**
	 * Increases number of attempts until the maximum amount of attempts is reached. 
	 * If maximum of attempts is reached the game will end
	 */
	public void increaseGameTry() {
		if(attempt==12) {
			JOptionPane.showMessageDialog(this,"Only 12 tries! You lost.");
			stopGame();				
			return;
		}
		attempt++;
	}
	
	/**
	 * Ends the game and return to start panel
	 */
	public void stopGame() {
		for(int i=0; i<attempt; i++) {
			//destroy and remove game components from playground
        	if(gameComponents[i] != null) {
        		//if colorDialog is still displayed close it
        		if(gameComponents[i].getColorDialog() != null) {
        			gameComponents[i].destroyColorDialog();
        		}
        		remove(gameComponents[i]);	
        		gameComponents[i] = null;
        	}
        }
		// reset game
		attempt=1;
		//back to StartPanel
		Component parent = getParent();
		CardLayout cl = (CardLayout)(((JPanel)parent).getLayout());
		cl.show((JPanel)parent, "START");
	}
	
	/**
	 * Get the current number of game tries
	 * 
	 * @return number of game attempts
	 */
	public int getAttempt() {
		return attempt;
	}
		
	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		this.height = getHeight();
		this.width = getWidth();
		
		//compute position and size for cancel Button
		int cancelButtonWidth = width/4;
		int cancelButtonHeight = gameComponentheight/2;
		int cancelButtonXpos = (width-cancelButtonWidth)/2;
		int cancelButtonYpos = height - gameComponentheight + (gameComponentheight-cancelButtonHeight)/2;
	
		cancelButton.setBounds(cancelButtonXpos, cancelButtonYpos, cancelButtonWidth, cancelButtonHeight);
		
		for(int i=0; i<attempt; i++) {
			if(gameComponents[i] != null) {
				gameComponents[i].setBounds(20, (height-(i+2)*gameComponentheight), width-40, gameComponentheight);
			}
    	}	
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cancel")) {
			stopGame();
		}
	}
}
	
