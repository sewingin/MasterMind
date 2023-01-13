package MM;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * This panel is showing the player's score 
 * 
 * @author Ingo Sewing
 * @Date 12.12.2022
 *
 */
@SuppressWarnings("serial")
public class StatisticPanel extends JPanel implements ActionListener{

	/**
	 * The table model 
	 */
	private DefaultTableModel model;
	
	/**
	 * Table in which the player's are listed
	 */
	private JTable table;
	
	/**
	 * The column names of the table
	 */
	private String[] columnNames = {"Rank", "Name", "Date", "Duration", "Rounds"};
	
	/**
	 * The table data for the table
	 */
	private String[][] tableData;
	
	/**
	 * The button for return to the start panel
	 */
	private JButton backButton;	
	
	/**
	 * The score Panel
	 */
	private static StatisticPanel instanceStatPanel;
	
	/**
	 * Constructs the Score Panel
	 */
	private StatisticPanel(){
		super();
		setLayout(new BorderLayout(10,10));
		
		// The north Panel for the high score label
		JPanel northPanel = new JPanel();
		JLabel highScoreJLabel = new JLabel("High Score");
		highScoreJLabel.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 55));
		northPanel.add(highScoreJLabel);
		add(northPanel,BorderLayout.NORTH);
		
		// The south panel for the back button
		JPanel southPanel =new JPanel();
		southPanel.setLayout(new GridLayout(3,3,10,10));
		
		backButton = new JButton("Back");
		Font font = new Font("Monospaced", Font.BOLD, 30);		
		backButton.addActionListener(this);
		backButton.setPreferredSize(new Dimension(100, 40));
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		backButton.setFont(font);
		backButton.setBackground(Color.WHITE);
		backButton.setFocusPainted(false);
				
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
		
		createTableDataInput();
		model = new DefaultTableModel(tableData, columnNames);
		
		// The table for the players score.
		table = new JTable( model ) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		JLabel renderer = ((JLabel)table.getDefaultRenderer(Object.class));
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setRowHeight(30);
		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane,BorderLayout.CENTER);		
	}
	
	/**
	 * This method creates the table data input.
	 * <p>
	 * Get the player's score data, which is loaded from a file. 
	 */
	public void createTableDataInput(){
		
		Players players = new Players();
		// Get player's list
		ArrayList<Player> playersList = players.getPlayers();
		
		//sort list in respect to number of tries and used time with preference to number of tries
		playersList.sort(Comparator.comparing(Player::getNumOfTrys).thenComparing(Player::getDuration));
		
		int listSize = playersList.size();
		tableData = new String[listSize][5];
			
		for(int i=0;i<listSize;i++) {		
			tableData[i][0] = Integer.toString(i+1)+".";
			tableData[i][1] = playersList.get(i).getName();
			tableData[i][2] = playersList.get(i).getDate().toString();
			tableData[i][3] = playersList.get(i).getDuration().toString();
			tableData[i][4] = Integer.toString(playersList.get(i).getNumOfTrys());
		}
		
		if(table!=null) {
			model.setDataVector(tableData, columnNames);
		}
	}

	/**
	 * Get score panel instance.
	 * 
	 * @return The score panel instance.
	 */
	public static StatisticPanel getInstance() {
		if(instanceStatPanel==null) {
			instanceStatPanel = new StatisticPanel();
		}
		return instanceStatPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component parent = getParent();
		CardLayout cl = (CardLayout)(((JPanel)parent).getLayout());
		cl.show((JPanel)parent, "START");
	}

}
