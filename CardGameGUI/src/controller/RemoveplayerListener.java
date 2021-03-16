package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.interfaces.Player;
import view.AppFrame;

public class RemoveplayerListener implements ActionListener
{

	private AppFrame frame;
	public RemoveplayerListener(AppFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if (this.frame.gettoolbar().getCombobox().getSelectedItem() == null)
			new WarningDialog("There are no players selected at the moment for removal.");
		else
		{
			Player player = (Player) this.frame.gettoolbar().getCombobox().getSelectedItem();
			if(!player.getPlayerId().equals(this.frame.gettoolbar().gethouseplayer().getPlayerId()))
			{
				this.frame.getgameEngine().removePlayer(player);
				this.frame.removePlayer(player);
				this.frame.getsummarypanel().removePlayer(player);
				this.frame.gettoolbar().RemoveFromComboBox(player);
				this.frame.getStatusbar().updateStatusBar(String.format("%s has been removed from this Game.",player.getPlayerName()));
				this.frame.gettoolbar().getdealListener().checkforHouse();
			}
		}
		
	}

}
