package view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import model.interfaces.Player;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer<Player> 
{
	@Override
	public Component getListCellRendererComponent(JList<? extends Player> list, Player player, int index, boolean isSelected, boolean cellHasFocus) 
	{
		if (player != null)
		{
			
			this.setText(player.getPlayerName());
		}
		else
		{
			this.setText("");
		}
		return this;
	}

}
