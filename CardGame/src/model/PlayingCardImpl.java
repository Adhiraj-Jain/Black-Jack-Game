package model;

import model.interfaces.PlayingCard;

/**
 * @author Adhiraj Jain (s3821245)
 */

public class PlayingCardImpl implements PlayingCard 
{

	private PlayingCard.Suit suit;
	private PlayingCard.Value value;
	private int score;

	public PlayingCardImpl(Suit suit, Value value) 
	{
		this.suit = suit;
		this.value = value;
		this.score = this.setScore();
	}

	@Override
	public Suit getSuit() 
	{
		return this.suit;
	}

	@Override
	public Value getValue() 
	{
		return this.value;
	}

	@Override
	public int getScore() 
	{
		return this.score;
	}

	private int setScore()
	{
		int facevalue = -1;

		switch(this.getValue().name())
		{
		case "EIGHT":	
		{ facevalue = 8; break; }
		
		case "NINE":	
		{ facevalue = 9; break; } 
			
		case "TEN":
		{ facevalue = 10; break; }
			
		case "JACK":
		{ facevalue = 10; break; }
		
		case "QUEEN":
		{ facevalue = 10; break; }
			
		case "KING":
		{ facevalue = 10; break; }
			
		case "ACE":
		{ facevalue = 11; break; }
			
		}
		
		return facevalue;
		
	}
	
	@Override
	public String toString()
	{
		return String.format("Suit: %s, Value: %s, Score: %d",this.correctName(this.getSuit().name()),this.correctName(this.getValue().name()),this.getScore());
	}
	
	/**
	    * <pre>Information regarding the method correctName(String s)
	    * 
	    * 1. called by {@link model.PlayingCardImpl#toString()}
	    * 2. This function will convert the Uppercase string to an English format string
	    * 	 for example if a string is "CLUBS" so it will convert it to "Clubs"
	    * 
	    * @return the changed string
	    * </pre>
	    */
	private String correctName(String s)
	   {
		   String s_upd = "" + s.charAt(0);
		   for (int i = 1;i < s.length();i++)
			   s_upd += Character.toLowerCase(s.charAt(i));
		   
		   return s_upd;
	   }	
	
	@Override
	public boolean equals(PlayingCard card) 
	{
		if ((this.getSuit()).equals(card.getSuit()) && (this.getValue()).equals(card.getValue()))
			return true;
		
		return false;
	}
	
	@Override
	public boolean equals(Object card)
	{
		return this.equals((PlayingCard) card);
	}
	
	public int hashCode()
	{
		return this.getSuit().hashCode() + this.getValue().hashCode();
	}
}
