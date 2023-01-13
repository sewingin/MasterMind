package MM;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * 
 * The JPanel for to display the content using the CardLayout
 * 
 * @author Ingo Sewing
 * @date 10.12.2022
 *
 */
@SuppressWarnings("serial")
public class ContentPanel extends JPanel {

	/**
	 * Create the panel.
	 * 
	 * @param cardLayout 
	 */
	public ContentPanel(CardLayout cardLayout) {
		super(cardLayout);		
	}
}
