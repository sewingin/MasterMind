package MM;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * The info panel to display informations about the author and
 * informations about the game
 * 
 * @author Ingo Sewing
 * @date 30.12.2022
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements ActionListener,HyperlinkListener{
	
	/**
	 * Some possible browsers which can be found on unixoid OS
	 */
	private static final String[] browsers = {"firefox", "google-chrome", "mozilla", "epiphany",
	            "konqueror", "netscape", "opera", "links", "lynx", "chromium", "brave-browser"};
		
	/**
	 * Button for return to the start panel
	 */
	private JButton backButton;	
	
	/**
	 * Label to display the game name 
	 */
	private JLabel infoLabel;
	
	/**
	 * Info text which will be displayed in JEditorPane
	 */
	public static String labelText = "<html><br><h1>Information:</h1><br><br>This is a computer game version of the board game Mastermind."
			+"<a href=\"https://en.wikipedia.org/wiki/Mastermind_(board_game)\"> More information about the game.</a> "
			+"The author of the game -- and also the writer of this text -- is me, Ingo Sewing. For me this was a \"refreshing "
			+"of my Java programming skills\". Therefore this game is not made for commercial use. Also this game is not made "
			+ " for sharing with other people, maybe there are copyright issues. "
			+ "It is prohibited to use this game commercially and also it is prohibited to share this game."
			+" I'm not a lawyer and I won't have contact to a warning lawyer.<br><br> 03.01.2023</html>";
	
	/**
	 * Constructor which build the info panel
	 */
	public InfoPanel() {
		super();
		setLayout(new BorderLayout(10,10));
	
		// Create north panel
		JPanel northPanel = new JPanel(new BorderLayout(10,10));
		
		//JLabel infoLabel
		infoLabel = new JLabel("Mastermind", SwingConstants.CENTER);
		
		//////////////////////////////////////////////////////////////////////
		//
		// For personal use, helps the author to remember some things
		//
		// sets the components alignment
		// infoJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		// infoJLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		//
		// set the content alignment
		// infoJLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// lblMastermind.setMinimumSize(new Dimension(400, 100));
		// lblMastermind.setMaximumSize(new Dimension(400, 100));
		// infoJLabel.setPreferredSize(new Dimension(400, 0));
		///////////////////////////////////////////////////////////////////////
		
		
		infoLabel.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 50));
		
		northPanel.add(Box.createVerticalStrut(30),BorderLayout.NORTH);
		northPanel.add(infoLabel,BorderLayout.CENTER);
		northPanel.add(Box.createVerticalStrut(30),BorderLayout.SOUTH);
		
		add(northPanel ,BorderLayout.NORTH);
		
		// Create center panel
		JPanel centerPanel =new JPanel(new BorderLayout(10,10));
		centerPanel.add(Box.createHorizontalStrut(30),BorderLayout.WEST);
		centerPanel.add(Box.createHorizontalStrut(30),BorderLayout.EAST);
		JEditorPane editorPane = new JEditorPane("text/html", labelText);
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(this);
		
		centerPanel.add(Box.createVerticalStrut(30),BorderLayout.SOUTH);
		centerPanel.add(editorPane, BorderLayout.CENTER);
		add(centerPanel,BorderLayout.CENTER);
		
		// Create south panel
		backButton = new JButton("Back");
		Font font = new Font("Monospaced", Font.BOLD, 30);
		backButton.setFont(font);
		backButton.addActionListener(this);
		backButton.setPreferredSize(new Dimension(100, 40));
		backButton.setAlignmentX(CENTER_ALIGNMENT);	
		backButton.setBackground(Color.WHITE);
		backButton.setFocusPainted(false);
		
		JPanel southPanel = new JPanel(new GridLayout(3,3,10,10));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(backButton);
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		southPanel.add(Box.createVerticalStrut(30));
		
		add(southPanel , BorderLayout.SOUTH);	
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			Component component = getParent();
			CardLayout cardLayout = (CardLayout)(((JPanel)component).getLayout());		
			cardLayout.show((JPanel)component, "START");
		}
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		
		// Check if link is clicked
		if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
			
			String osName = System.getProperty("os.name").toLowerCase();
			//Set uri
			URI uri = null;
			try {
				uri = new URI("https://en.wikipedia.org/wiki/Mastermind_(board_game)");
			// If exception occurs: do nothing  
			} catch (URISyntaxException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			// If OS is Windows
			if(osName.contains("win")) {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(uri);
					// If exception occurs: do nothing  
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else {
				Runtime runtime = Runtime.getRuntime();
				// If OS is UNIXOID
				if(osName.contains("nix") || osName.contains("nux") || osName.contains("sunnos")){
					try {
						String browser = null;
				        for (String b : browsers) {
				        	// Check which browser is installed an take the first one of the list in browsers
				            if (browser == null && runtime.exec(new String[]{"which", b}).getInputStream().read() != -1) {
				            	runtime.exec(new String[]{browser = b, uri.toString()});
				            	// leave loop if browser is found
				            	break;
				            }
				        }
				    // If exception occurs: do nothing    
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// If OS is mac
				else if(osName.contains("mac")){
					try {
						runtime.exec("open " + uri);
					// If exception occurs: do nothing
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {}
			}
		}
	}

}
