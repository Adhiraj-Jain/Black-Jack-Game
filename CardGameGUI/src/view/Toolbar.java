package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

//import controller.AddPlayerListener;
import controller.BetListener;
import controller.ComboboxListener;
//import controller.RestartGameListener;
import controller.dealListener;
import model.SimplePlayer;
import model.interfaces.Player;

public class Toolbar extends JToolBar
{
	private AppFrame frame;
	private JComboBox<Player> playerSelector;
	private JButton deal;
	private JButton bet;
	private String houseID = "House";
	private dealListener deallistener;
	private static Player house = new SimplePlayer("House","House",0);
	
	public Toolbar(AppFrame appframe)
	{	
		this.frame = appframe;
		this.playerSelector = new JComboBox<Player>();

		this.deal = new JButton("Deal");
		this.bet = new JButton("Bet");
		
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		this.playerSelector.setRenderer(renderer);
		this.add(deal);
		this.add(bet);
		this.add(this.playerSelector);

		
		this.frame.addPlayer(house);
		this.frame.getPlayerinfo(house).setdealt(true);
		
		ComboboxListener comboboxlistener = new ComboboxListener(this.frame);
		this.playerSelector.addActionListener(comboboxlistener);
		
		BetListener betlistener = new BetListener(this.frame);
		bet.addActionListener(betlistener);
		
		deallistener = new dealListener(this.frame);
		deal.addActionListener(deallistener);
		
		this.changeDealState(false);
		this.changebetState(false);
	}
		
	public void updateCombobox(Player player)
	{
		this.playerSelector.removeAllItems();
		
		for(Player next : this.frame.getplayersSet())
			this.playerSelector.addItem(next);
		this.playerSelector.setSelectedItem(player);
	}
	
	public void RemoveFromComboBox(Player player)
	{
		this.playerSelector.removeItem(player);
	}
	
	public JComboBox<Player> getCombobox()
	{
		return this.playerSelector;
	}
	
	public dealListener getdealListener()
	{
		return this.deallistener;
	}
	
	public Player gethouseplayer()
	{
		return house;
	}
	
	public void changeDealState(Boolean state)
	{
		this.deal.setEnabled(state);
	}
	
	public void changebetState(Boolean state)
	{
		this.bet.setEnabled(state);
	}

	public Player getplayerfromCombobox(String playerID)
	{
		for(int i = 0;i<this.getCombobox().getItemCount();i++)
		{
			if(this.getCombobox().getItemAt(i).getPlayerId().equals(playerID))
			{
				return this.getCombobox().getItemAt(i);
			}
		}
		return null;
	}
	
	public void changestates(Player player)
	{
		if(player.getPlayerId().equals(houseID))
		{
			this.changeDealState(false);
			this.changebetState(false);
		}
		else
		{
			this.changebetState(this.frame.getPlayerinfo(player).getcanDeal() && !this.frame.getPlayerinfo(player).getdealt());
			this.changeDealState(this.frame.getPlayerinfo(player).getbet() && !this.frame.getPlayerinfo(player).getdealt());
		}
	}

}