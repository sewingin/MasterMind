package MM;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.SystemColor;


/**
 * Main Gui class which contains the main method.
 * 
 * @author Ingo Sewing
 * @date 09.12.2022
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class GuiMain extends JFrame implements ActionListener{

	
	/**
	 * The menubar of the game
	 */
	private JMenuBar menuBar;
	
	/**
	 * The layout of the main contentPanel
	 */
	private CardLayout cardLayout;
	
	/**
	 * The JPanel which contains the card layout
	 */
	private ContentPanel contentPanel;
	
	/**
	 * JPanel for the play field in which the game take place
	 */
	private Playground playFieldPanel;
	
	/**
	 * JPanel in which the saved players scores are displayed
	 */
	private StatisticPanel scorePanel;
	
	/**
	 * JPanel which contains the main menu
	 */
	private StartPanel startPanel;
	
	/**
	 * JPanel in which saved players scores can be deleted  
	 */
	private PlayersPanel playersPanel;
	
	/**
	 * JPanel for to display game info
	 */
	private InfoPanel infoPanel;
	
	/**
	 * The position in x-direction relative to the display at which the game is displayed 
	 */
	private int xPosition;
	
	/**
	 * The height of the display resolution
	 */
	private int displayHeight;
	
	/**
	 * The width of the display resolution
	 */
	private int displayWidth;
	
	/**
	 * Create the frame.
	 */
	public GuiMain() {
		super("MasterMind");
		setDefaultLookAndFeelDecorated(true);
		setBackground(SystemColor.controlDkShadow);
		
		///////////////////////////////////////////////////////////////////////////////
		//		Some unused code for personal mind only ;-)
		//
		//		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		//		this.width = gd.getDisplayMode().getWidth();
		//		this.height = gd.getDisplayMode().getHeight();
		//
		//		setUndecorated(true);
		//
		////////////////////////////////////////////////////////////////////////////////
		//		Returns only display height and width   TESTED IN LINUX 
		//
		//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//		width = (int)screenSize.getWidth();
		//		height = (int)screenSize.getHeight();
		//		height = height - 50; 
		//
		//		but it is not capable to determine the task bar height
		//		
		//      Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets( gc);
		//		int taskBarHeight = scnMax.bottom; // = 0 !
		//
		//		Instead use:
		//
		//		GraphicsConfiguration gc = getGraphicsConfiguration();
		//		Rectangle winSize = gc.getBounds();
		//		
		//		int taskBarHeight = winSize.y;
		//
		////////////////////////////////////////////////////////////////////////////////

		// return the graphic configuration of the component, but in this case
		// because the frame isn't created yet, the parent compnent's configuration
		// which is a maximized window in system display 
		GraphicsConfiguration gc = getGraphicsConfiguration();
		
		// Contains the maximum window size of the display
		// in which the y value is the y-position from this on content can be positioned.
		// That means that the y value is the height of the system window's task bar.
		// Calling getY() deliver the same but getWidth() and getHeight() returns "0"
		// until the component is painted
		//
		// I use it to position the JFrame on the display with suitable sizes
		Rectangle screenSize = gc.getBounds();
		int taskBarHeight = screenSize.y;
		this.displayHeight = screenSize.height;
		this.displayWidth = screenSize.width;
		
		cardLayout = new CardLayout();
		this.xPosition = this.displayWidth/2 - this.displayHeight/4;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//The window should be in the middle of the screen
		//This set the bounds for the drawing area not for the whole window
		//Therefore it is necessary to modify height and/or width
		setBounds(xPosition, taskBarHeight, this.displayHeight/2, this.displayHeight);
		
		//Set menu
		menuBar = new JMenuBar();
		menuBar.add(createMenu("Game", "Start Game", "Restart Game","Main Menu", "Quit"));
		//menuBar.add(createMenu("Player","CreatePlayer","DeletePlayer"));
		menuBar.add(createMenu("Overview","Score","Player Overview"));
		menuBar.add(createMenu("Help", "Manual", "Info"));
		int menuHeight = displayHeight/30;
		menuBar.setPreferredSize(new Dimension(displayWidth,menuHeight));
		setJMenuBar(menuBar);
		
		//////////////////////////////////////////////////////////////////////////////////7
		//
		// Some infos for the author
		//
		// This determines the border sizes
		// Insets insets = this.getInsets();
		//
		////////////////////////////////////////////////////////////////////////////////////
		
		contentPanel =  new ContentPanel(cardLayout);//-menuHeight);
		startPanel = new StartPanel();
		playFieldPanel = new Playground(displayHeight/2,displayHeight-menuHeight);//-menuHeight);
		scorePanel = StatisticPanel.getInstance();
		playersPanel = PlayersPanel.getInstance();
		infoPanel = new InfoPanel();
		//playGround.setBounds(0, 0+s, width, height-s);
		contentPanel.add(playFieldPanel,"PLAYGROUND");
		contentPanel.add(scorePanel,"SCORE");
		contentPanel.add(startPanel,"START");
		contentPanel.add(playersPanel,"PLAYERS");
		contentPanel.add(infoPanel, "INFO");
		
		setContentPane(contentPanel);
		cardLayout.show(contentPanel,"START");
		setMinimumSize(new Dimension(450, 840));
		setVisible(true);
		//repaint();
	}
	
	/**
	 * Create a menu with sub menu entries
	 * 
	 * @param menuLabel The menu name
	 * @param subMenuLabels A variable amount of sub menu names
	 * @return A complete menu entry with sub menu entries
	 */
	private JMenu createMenu(String menuLabel, String... subMenuLabels) {
		JMenu menu = new JMenu(menuLabel);
		Font menuFont = new Font("Arial",Font.BOLD , 16);
		menu.setFont(menuFont);
		for (String subMenuLabel : subMenuLabels) {
			JMenuItem menuItem = new JMenuItem(subMenuLabel); 
			menuItem.setFont(menuFont);
			menuItem.addActionListener(this);
			menu.add(menuItem);
		}
		return menu;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Start")){
			cardLayout.show(contentPanel, "PLAYGROUND");
		}
		if(e.getActionCommand().equals("Start Game")){
			cardLayout.show(contentPanel, "PLAYGROUND");
		}
		if(e.getActionCommand().equals("Restart Game")){
			playFieldPanel.stopGame();
			//playGround.setGameTry(1);
			cardLayout.show(contentPanel, "PLAYGROUND");
		}
		if(e.getActionCommand().equals("Main Menu")){
			playFieldPanel.stopGame();
			//playGround.setGameTry(1);
			cardLayout.show(contentPanel, "START");			
		}
		if(e.getActionCommand().equals("Score")){
			playFieldPanel.stopGame();
			cardLayout.show(contentPanel, "SCORE");
		}
		if(e.getActionCommand().equals("Quit")){
			playFieldPanel.stopGame();
			System.exit(0);
		}
		if(e.getActionCommand().equals("Player Overview")) {
			cardLayout.show(contentPanel, "PLAYERS");
		}		
		if(e.getActionCommand().equals( "Info")) {
			cardLayout.show(contentPanel, "INFO");
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiMain frame = new GuiMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
