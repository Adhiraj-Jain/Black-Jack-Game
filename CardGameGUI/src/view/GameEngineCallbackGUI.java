package view;

import java.util.HashSet;
import java.util.Set;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

import javax.swing.SwingUtilities;

import controller.WarningDialog;

public class GameEngineCallbackGUI implements GameEngineCallback{

	private AppFrame frame; 
	
	public GameEngineCallbackGUI(GameEngine gameEngine) 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				frame = new AppFrame(gameEngine);
			} 
		});
	}
	
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) 
	{
		this.frame.getcardpanel().addCard(player, card);
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) 
	{
		this.frame.getcardpanel().addCard(player, card);
		this.frame.getStatusbar().updateStatusBar("YOU BUSTED");
		
	}

	@Override
	public void result(Player player, int result, GameEngine engine) 
	{
		this.frame.getStatusbar().updateStatusBar(String.format("%s your result is: %d",player.getPlayerName(),player.getResult()));
		
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) 
	{
		this.frame.getcardpanel().addCard(this.frame.gettoolbar().gethouseplayer(), card);
		
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) 
	{
		this.frame.getcardpanel().addCard(this.frame.gettoolbar().gethouseplayer(), card);
		this.frame.getStatusbar().updateStatusBar("HOUSE BUSTED");
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) 
	{
		this.frame.getStatusbar().updateStatusBar(String.format("%s your result is: %d",this.frame.gettoolbar().gethouseplayer().getPlayerId(),result));
		
		
		for(Player player : this.frame.getplayersSet())
		{
			if(!player.getPlayerId().equals(this.frame.gettoolbar().gethouseplayer().getPlayerId()))
			{
				if(player.getResult() == result)
					this.frame.getsummarypanel().updateResultDetails(player, 0);
				else if(player.getResult() > result)
					this.frame.getsummarypanel().updateResultDetails(player, 1);
				else if(player.getResult() < result)
					this.frame.getsummarypanel().updateResultDetails(player, 2);
				
			}
		}
		this.checkInitialpoints();
		this.frame.resetGame();
		
	}
	
	private void checkInitialpoints()
	{
		Set<Player> players = new HashSet<Player>();
		players.addAll(this.frame.getplayersSet());
		for(Player player : players)
		{
			if(!player.getPlayerId().equals(this.frame.gettoolbar().gethouseplayer().getPlayerId()) && player.getPoints() == 0)
			{
				this.frame.getsummarypanel().removePlayer(player);
				this.frame.gettoolbar().RemoveFromComboBox(player);
				this.frame.getgameEngine().removePlayer(player);
				this.frame.removePlayer(player);
				
				new WarningDialog(String.format("%s has been removed from the game due to insufficient points.",player.getPlayerName()));
			}
		}
	}

	
}
