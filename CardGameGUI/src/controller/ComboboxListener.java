package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.Player;
import view.AppFrame;

public class ComboboxListener implements ActionListener 
{

	private AppFrame frame;
	
	public ComboboxListener(AppFrame frame)
	{
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Player player = (Player) this.frame.gettoolbar().getCombobox().getSelectedItem();
		if(player !=null)
		{
			if(!this.frame.gettoolbar().getdealListener().isDealing())
				this.frame.gettoolbar().changestates(player);
			this.frame.getcardpanel().paintagain();
		}
	}
	
}
