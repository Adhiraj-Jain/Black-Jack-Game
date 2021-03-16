package model;

import java.util.ArrayList;
import java.util.ListIterator;
import model.interfaces.PlayingCard;

/*This Class is used to store each player information such as 
 * player has bet or not 
 * player has dealt or not
 * Previous dealt cards of player 
 * result i.e. WIN/LOSS/DRAW
 */
public class PlayerInfo 
{
	private Boolean hasbet;
	private Boolean hasDealt;
	private ArrayList<PlayingCard> cards;
	private String result;
	private Boolean candeal;
	public PlayerInfo()
	{
		this.hasbet = false;
		this.hasDealt = false;
		this.cards = new ArrayList<>();
		this.result = "Not yet decided";	
		this.candeal = true;
	}
	
	public Boolean getbet()
	{
		return this.hasbet;
	}
	
	public Boolean getdealt()
	{
		return this.hasDealt;
	}

	public ArrayList<PlayingCard> getcards()
	{
		return this.cards;
	}
	
	public String getresult()
	{
		return this.result;
	}
	
	public void setbet(Boolean bet)
	{
		this.hasbet = bet;
	}
	
	public void setdealt(Boolean dealt)
	{
		this.hasDealt = dealt;
	}

	public void addCards(PlayingCard card)
	{
		ListIterator<PlayingCard> iterator = this.cards.listIterator();
		while(iterator.hasNext())
		{
			iterator.next();
		}
		iterator.add(card);
	}
	
	public void setresult(String str)
	{
		this.result = str;
	}
	
	public void resetList()
	{
		this.cards.clear();
	}

	public Boolean getcanDeal()
	{
		return this.candeal;
	}
	
	public void setcanDeal(Boolean state)
	{
		this.candeal = state;
	}
}
