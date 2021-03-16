package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.AppFrame;

public class dealListener implements ActionListener
{
	private AppFrame frame;
	private int delay;
	private Boolean isdealing;
	
	public dealListener(AppFrame frame)
	{
		this.frame = frame;
		this.delay = 100;
		this.isdealing = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Player player  = (Player) this.frame.gettoolbar().getCombobox().getSelectedItem();
		if(this.frame.getPlayerinfo(player).getbet() && !this.frame.getPlayerinfo(player).getdealt())
		{
			this.startdeal(player, delay);
			this.frame.getPlayerinfo(player).setdealt(true);
			this.frame.getPlayerinfo(player).setcanDeal(false);
			frame.getStatusbar().updateStatusBar("Dealing Cards for " + player.getPlayerName());	
			}
	}
	
	/**
	 * <pre>
	 * This method checks if all the players have dealt or not and call appropriate methods accordingly.
	 * 1. Runs a for loop over the players set and check if anyone has not dealt
	 * 2. If everyone has dealt then calls dealHouse() to deal cards for house.
	 * <pre>
	 */
	public void checkforHouse()
	{
		Boolean flag = true;
		if(this.frame.getplayersSet().size()>1)
		{
			for (Player player :  this.frame.getplayersSet())
			{
				if(!frame.getPlayerinfo(player).getdealt() && !player.getPlayerId().equals(this.frame.gettoolbar().gethouseplayer().getPlayerId()))
				flag = false;
			}
			if(flag)
			{
			frame.gettoolbar().getCombobox().setSelectedItem(frame.gettoolbar().getplayerfromCombobox(this.frame.gettoolbar().gethouseplayer().getPlayerId()));
			this.startdeal(this.frame.gettoolbar().gethouseplayer(), delay);
			}
		}
	}
	
	/**
	 * <pre>
	 * This method will call appropriate methods for dealing in a new thread.
	 * 
	 * 1. Calls method resetList() from  {@link model.PlayerInfo#resetList()} to empty the list.
	 * 2. Starts a new thread
	 * 3. Change dealing button state to false so that no other player can deal in between ongoing deal.
	 * 3. Update Status bar with who is dealing at the moment.
	 * 4. After player has dealt, checking for house by calling  {@link controller.dealListener#checkforHouse()} .
	 *   
	 * 	@param player 
	 * 				player for which dealing has to be done.
	 * 
	 * 	@param delay
	 * 				delay time between players.
	 * <pre>
	 */
	private void startdeal(Player player,int delay)
	{
		
		this.frame.getPlayerinfo(player).resetList();
		new Thread() 
		{
			@Override
			public void run() 
			{
				isdealing = true;
				frame.gettoolbar().changeDealState(false);
				frame.gettoolbar().changebetState(false);
				frame.getStatusbar().updateStatusBar(String.format("Dealing Cards for %s",player.getPlayerName()));
				if(player.equals(frame.gettoolbar().gethouseplayer()))
				{
					frame.getgameEngine().dealHouse(delay);
					
				}
				else if(frame.getgameEngine().getAllPlayers().contains(player))
					frame.getgameEngine().dealPlayer(player, delay);
				
				isdealing = false;
				checkforHouse();
				
			} 
		
		}.start();
		
	}
	
	public Boolean isDealing()
	{
		return this.isdealing;
	}
}
