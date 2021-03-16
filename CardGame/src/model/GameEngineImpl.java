package model;

import java.util.SortedMap;
import java.util.TreeMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * @author Adhiraj Jain (s3821245)
 */

public class GameEngineImpl implements GameEngine{
	
	private SortedMap<String,Player> players = new TreeMap<String,Player>();
	private List<GameEngineCallback> GameEngineCallbackCollection = new ArrayList<GameEngineCallback>();
	private Deque<PlayingCard> deck = new LinkedList<PlayingCard>();
	
	public GameEngineImpl()
	{
		this.deck = this.getShuffledHalfDeck();
	}
	
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException 
	{	
		int	result = this.deal(delay, player);
		for(GameEngineCallback next : GameEngineCallbackCollection)
			next.result(player, result, this);
		player.setResult(result);
	}
	
	
	@Override
	public void dealHouse(int delay) throws IllegalArgumentException 
	{
		int houseResult = this.deal(delay, null);
		
		for (Player player : this.players.values())
			this.applyWinLoss(player, houseResult);
			
		for(GameEngineCallback next : GameEngineCallbackCollection)
			next.houseResult(houseResult, this);
		
		for (Player player : this.players.values())
			player.resetBet();
	
	}
	


	@Override
	public void applyWinLoss(Player player, int houseResult) {
		if (player.getResult() > houseResult)
			player.setPoints(player.getPoints() + player.getBet());
		else if (player.getResult() < houseResult)
			player.setPoints(player.getPoints() - player.getBet());
		
	}

	@Override
	public void addPlayer(Player player) 
	{
			if (players.containsKey(player.getPlayerId()))
				players.replace(player.getPlayerId(), players.get(player.getPlayerId()), player);
			
			else
				players.put(player.getPlayerId(),player);
		
	}

	@Override
	public Player getPlayer(String id) 
	{
		return players.get(id);
	}

	@Override
	public boolean removePlayer(Player player) 
	{
		return players.remove(player.getPlayerId(), player);
	}

	@Override
	public boolean placeBet(Player player, int bet) 
	{	
		return players.get(player.getPlayerId()).setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		GameEngineCallbackCollection.add(gameEngineCallback);
		
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		for (GameEngineCallback next : GameEngineCallbackCollection)
		{
			if (next.equals(gameEngineCallback))
				GameEngineCallbackCollection.remove(gameEngineCallback);
				return true;
		}
		return false;
	}

	@Override
	public Collection<Player> getAllPlayers() 
	{
		return Collections.unmodifiableCollection(players.values());
	}

	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() 
	{
		
		for (PlayingCard.Suit suit : PlayingCard.Suit.values())
		{
			for (PlayingCard.Value value : PlayingCard.Value.values())
			{
				this.deck.add(new PlayingCardImpl(suit,value));
			}
		}
		Collections.shuffle((List<?>) this.deck);
		return this.deck;
	}

	/**
	    * <pre>Information regarding the method Givecard()
	    * 
	    * 1. called by {@link model.GameEngineImpl#deal(int delay, Player player)}
	    * 2. It first poll the first card from deque and checks if its null or not.
	    * 	If null then gets a new shuffle deck and returns a new card from it
	    * 	else returns the card from previous deck.
	    * 
	    * @return a card for deal
	    * </pre>
	    */	
	private PlayingCard Givecard()
	{
		PlayingCard card = deck.pollFirst();
		if (card == null)
		{
			this.deck = this.getShuffledHalfDeck();
			card = deck.pollFirst();
		}
		return card;
	}
	
	/**
	    * <pre>Information regarding the method deal(int delay, Player player)
	    * 
	    * 1. called by {@link model.GameEngineImpl#dealPlayer(Player player, int delay)}
	    * 	and by {@link model.GameEngineImpl#dealHouse(int delay)}
	    * 
	    * @param delay
	    *            the delay between cards being dealt (in milliseconds (ms))
	    *
	    *@param player : can have either player object or null value.
	    *            	 If called by dealPlayer() then it will have the player object
	    *            	else it will have null value if called by dealHouse().
	    *
	    *@return the result
	    * </pre>
	    */
	private int deal(int delay, Player player)
	{
		if(delay < 0 || delay > 1000)
			throw new IllegalArgumentException();
		
		int result = 0;
		while (true)
		{	
			PlayingCard card = this.Givecard();
			
			result += card.getScore();
			if (result >= BUST_LEVEL)
			{
				if(result > BUST_LEVEL)
				{
					if (player == null) 
					{
						for(GameEngineCallback next : GameEngineCallbackCollection)
							next.houseBustCard(card, this);
					}
					else
					{
						for(GameEngineCallback next : GameEngineCallbackCollection)
							next.bustCard(player, card, this);
					}
					result -= card.getScore();
				}	
				break;
			
			}
			if(player == null)
			{
				for(GameEngineCallback next : GameEngineCallbackCollection)
					next.nextHouseCard(card, this);
			}
			else
			{
				for(GameEngineCallback next : GameEngineCallbackCollection)
					next.nextCard(player, card, this);
			}
			try
				{
				Thread.sleep(delay);
				}
			catch (Exception e)
				{}
		}
		
		return result;
	}
	
}
