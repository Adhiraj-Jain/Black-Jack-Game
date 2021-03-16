package model;

import model.interfaces.Player;

/**
 * @author Adhiraj Jain (s3821245)
 */

public class SimplePlayer implements Player {

	private String playerID;
	private String playername;
	private int playerpoints;
	private int playerBet = 0;
	private int playerResult = 0;
	
	public SimplePlayer(String id, String name, int initialPoints) {
		if (id == null || name == null)
			throw new IllegalArgumentException();
		this.playerID = id;
		this.playername = name;
		this.playerpoints = initialPoints;
	}

	@Override
	public String getPlayerName() {
		return this.playername;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playername = playerName;
	}

	@Override
	public int getPoints() {
		return this.playerpoints;
	}

	@Override
	public void setPoints(int points) {
		this.playerpoints = points;
		
	}

	@Override
	public String getPlayerId() {
		return this.playerID;
	}

	@Override
	public boolean setBet(int bet) {
		if (bet == 0)
		{
			this.resetBet();
		}
		else if(bet >0 && bet<=this.getPoints()) 
		{	
			this.playerBet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() {
		return this.playerBet;
	}

	@Override
	public void resetBet() {
		this.playerBet = 0;
		
	}

	@Override
	public int getResult() {
		return this.playerResult;
	}

	@Override
	public void setResult(int result) {
		this.playerResult = result;
		
	}

	@Override
	public boolean equals(Player player) {
		return ((this.getPlayerId()).equals(player.getPlayerId()));
	}
	@Override
	public boolean equals(Object player) {
		if (player instanceof Player)
			return (this.equals((Player) player));
		return false;
	}
	@Override
	public int compareTo(Player player) {
		return (this.getPlayerId()).compareTo(player.getPlayerId());
	}
	
	@Override
	public  int hashCode()
	{
		return this.getPlayerId().hashCode();
	}
	
	@Override
	public  String toString()
	{
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d",this.getPlayerId(),this.getPlayerName(),this.getBet(),this.getPoints(),this.getResult());

	}
}
