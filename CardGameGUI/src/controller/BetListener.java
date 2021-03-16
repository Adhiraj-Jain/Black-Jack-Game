package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.interfaces.Player;
import view.AppFrame;

public class BetListener implements ActionListener
{
	private AppFrame frame;
	
	public BetListener(AppFrame frame)
	{
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	    try
	    {
			JTextField bet = new JTextField(5);
			JPanel myPanel = new JPanel();
			myPanel.add(new JLabel("Please Enter bet points:"));
			myPanel.add(bet);
			int result = JOptionPane.showConfirmDialog(null,myPanel,"!! Bet Points Update !!",JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION)
			{
				Player player = (Player) this.frame.gettoolbar().getCombobox().getSelectedItem();
				Boolean check = this.frame.getgameEngine().placeBet(player,Integer.parseInt(bet.getText()));
				if(!check)
					throw new Exception();
				this.frame.getPlayerinfo(player).setbet(true);
				this.frame.gettoolbar().changeDealState(true);
				this.frame.getsummarypanel().updateBetDetail(player);
				this.frame.getStatusbar().updateStatusBar(String.format("Bet has been placed for %s",player.getPlayerName()));
			}
		}
	    catch(Exception E)
	    {
	    	new WarningDialog("Please Enter a Valid Bet.");
	    }

		
	}

}
