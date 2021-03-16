package view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel
{
	private JLabel statusLabel = new JLabel();

	public StatusBar(AppFrame frame)
	{
		this.statusLabel = new JLabel();
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setPreferredSize(new Dimension(frame.getWidth(), 18));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.updateStatusBar("Hi, Welcome to the Card Game");
		
	}
	
	public void updateStatusBar(String status)
	{
		this.statusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		this.statusLabel.setText(status);	
		this.add(this.statusLabel);
	}
}
