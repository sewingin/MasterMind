package MM;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
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
 * This class creates a panel to display the score of the player's in a table.
 * In this table selected player's can be deleted finally.
 * 
 * @author Ingo Sewing
 * @date 22.12.2022
 *
 */
@SuppressWarnings("serial")
public class PlayersPanel extends JPanel implements ActionListener, TableModelListener, MouseListener{

	/**
	 * The model for the tabel.  
	 */
	private DefaultTableModel model;
	
	/**
	 * The table for to display the player score.
	 */
	private JTable table;
	
	/**
	 * The column names of the table.
	 */
	private String[] columnNames = {"Name", "Rank", "Rounds", "Duration", "Date"};
	
	/**
	 * Array contains the table data.
	 */
	private String[][] tableData;
	
	/**
	 * The top label of the panel.
	 */
	private JLabel playersJLabel;
	
	/**
	 * Button to delete the selected player's.
	 */
	private JButton deleteTableDataButton;
	
	/**
	 * Button for to return to the start panel.
	 */
	private JButton backButton;
	
	/**
	 * Instance of the player's panel.
	 */
	private static PlayersPanel playersPanelInstance;
	
	/**
	 * Construct's the player panel.
	 */
	private PlayersPanel() {
		super();
		setLayout(new BorderLayout(10,10));
		
		// The north panel for borderlayout north.
		JPanel northPanel = new JPanel(new FlowLayout());		
		
		playersJLabel = new JLabel("Player Overview");
		playersJLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
		playersJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		northPanel.add(playersJLabel);
		add(northPanel,BorderLayout.NORTH);
		
		
		createTableDataInput();
		model = new DefaultTableModel(tableData, columnNames);
		model.addTableModelListener(this);		
		table = new JTable( model ) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.addMouseListener(this);
		table.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		// Center the content in table.
		JLabel renderer = ((JLabel)table.getDefaultRenderer(Object.class));
		renderer.setHorizontalAlignment(SwingConstants.CENTER);		
		table.setRowHeight(28);
		
		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane,BorderLayout.CENTER);
		
		// South panel
		JPanel southJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 40));
		
		deleteTableDataButton = new JButton("Delete Players");
		deleteTableDataButton.setBackground(Color.WHITE);
		deleteTableDataButton.setFocusPainted(false);
		deleteTableDataButton.setAlignmentX(CENTER_ALIGNMENT);
		deleteTableDataButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		deleteTableDataButton.setEnabled(false);
		deleteTableDataButton.addActionListener(this);
		southJPanel.add(deleteTableDataButton);
		
		backButton = new JButton("Back");
		backButton.setBackground(Color.WHITE);
		backButton.setFocusPainted(false);
		backButton.setAlignmentX(CENTER_ALIGNMENT);
		backButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		backButton.addActionListener(this);
		southJPanel.add(backButton);	
		add(southJPanel,BorderLayout.SOUTH);
	}

	/**
	 * Get the only player's panel.
	 * 
	 * @return The player panel instance. 
	 */
	public static PlayersPanel getInstance() {
		if(playersPanelInstance==null) {
			playersPanelInstance = new PlayersPanel();
		}
		return playersPanelInstance;
	}
	
	/**
	 * Create's the table data input.
	 */
	public void createTableDataInput(){
		
		Players players = new Players();
		ArrayList<Player> playersList = players.getPlayers();
		
		//sort list in respect to number of tries and used time with preference to number of tries
		playersList.sort(Comparator.comparing(Player::getNumOfTrys).thenComparing(Player::getDuration));
		int listSize = playersList.size();
		tableData = new String[listSize][5];
			
		for(int i=0;i<listSize;i++) {		
			tableData[i][0] = playersList.get(i).getName();
			tableData[i][1] = Integer.toString(i+1)+"."; 
			tableData[i][2] = Integer.toString(playersList.get(i).getNumOfTrys()); 
			tableData[i][3] = playersList.get(i).getDuration().toString();
			tableData[i][4] = playersList.get(i).getDate().toString();
		}
		
		if(table!=null) {
			model.setDataVector(tableData, columnNames);
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("Hallo du socke oben");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Back")) {
			//deselect 
			Component parentComponent = getParent();
			CardLayout cardLayout =  (CardLayout) ((JPanel)parentComponent).getLayout();
			cardLayout.show((JPanel)parentComponent, "START");
		}	
		if(e.getActionCommand().equals("Delete Players")) {
			int rows[] = table.getSelectedRows();
			Players players = new Players();
			ArrayList<Player> playersList = players.getPlayers();
			
			//remove selected players
			for(int i=0; i<rows.length; i++) {
				for(int j=0; j<playersList.size(); j++) {
					if(playersList.get(j).getName().equals(tableData[rows[i]][0]) && 
					   Integer.toString(playersList.get(j).getNumOfTrys()).equals(tableData[rows[i]][2]) && 
					   playersList.get(j).getDuration().toString().equals(tableData[rows[i]][3]) && 
					   playersList.get(j).getDate().toString().equals(tableData[rows[i]][4])) {
						
					   playersList.remove(j);
					}
				}				
			}
			
			//update and save new players list
			players.setPlayers(playersList);
			players.savePlayers();
			createTableDataInput();
			deleteTableDataButton.setEnabled(false);
			table.clearSelection();
			//update Score Panel
			StatisticPanel.getInstance().createTableDataInput();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//this guarantees that a rows is selected
		if(e.getSource().equals(table)) {
			deleteTableDataButton.setEnabled(true);
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
}
