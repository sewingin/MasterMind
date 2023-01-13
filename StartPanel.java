package MM;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * This class create's the game's start panel.
 * <p>
 * This panel is shown on start up of the game. There are buttons
 * to start a new game, to quit the game or to go to the score panel.
 * 
 * @author Ingo Sewing
 * @date 10.12.2022
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel implements ActionListener{

	/**
	 * The button to start the game.
	 */
	private JButton startButton;
	
	/**
	 * The button to show the score panel.
	 */
	private JButton scoreButton;
	
	/**
	 * The button to quit the game.
	 */
	private JButton quitButton;
	
	/**
	 * Construct's the start panel.
	 */
	public StartPanel() {
		super();		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JLabel lblMastermind = new JLabel("Mastermind");
		lblMastermind.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblMastermind.setMinimumSize(new Dimension(400, 100));
		lblMastermind.setMaximumSize(new Dimension(400, 100));
		lblMastermind.setPreferredSize(new Dimension(400, 100));
		lblMastermind.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 55));
		lblMastermind.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblMastermind);
		add(Box.createVerticalGlue());
		setButton(startButton, "Start");
		setButton(scoreButton,"Score");
		setButton(quitButton, "Quit");
		add(Box.createVerticalGlue());
	}
	
	/**
	 * Set's up a button in the start panel.
	 * 
	 * @param jButton The button to set up.
	 * @param text The text to display in the button.
	 */
	public void setButton(JButton jButton, String text ) {
		
		Font font = new Font("Monospaced", Font.BOLD, 40);
		add(Box.createRigidArea(new Dimension(5,5)));
		jButton = new JButton(text);
		jButton.setAlignmentX(CENTER_ALIGNMENT);
		jButton.setMinimumSize(new Dimension(200, 60));
		jButton.setMaximumSize(new Dimension(200, 60));
		jButton.setPreferredSize(new Dimension(200, 60));
		//jButton.setBounds(10, 46, 41, 30);
		jButton.setFont(font);
		jButton.setBackground(Color.WHITE);
		jButton.setFocusPainted(false);
		jButton.addActionListener(this);
		add(jButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Start"))
        {
			Component parent =  getParent();
			CardLayout cl = (CardLayout)(((JPanel)parent).getLayout());
			cl.show((JPanel)parent, "PLAYGROUND");
			
			GameLogic.calculateGame();
        }
        else if(e.getActionCommand().equals("Score"))
        {
        	Component parent =  getParent();
			CardLayout cl = (CardLayout)(((JPanel)parent).getLayout());
			cl.show((JPanel)parent, "SCORE");
			
        }
        else if(e.getActionCommand().equals("Quit"))
        {
        	System.exit(0);
        }	
	}

}
