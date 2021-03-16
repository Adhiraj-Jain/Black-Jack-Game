package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardPanel extends JPanel
{
	private int card_X;
	private int card_Y;
	private int card_W;
	private int card_H;
	private int str_XUP;
	private int str_YUP;
	private int str_XLW;
	private int str_YLW;
	private int img_X;
	private int img_Y;
	private int img_W;
	private int img_H;
	ImageIcon img;
	private String letter;
	private AppFrame frame;
	private static Map<Enum<PlayingCard.Suit>, ImageIcon> imageIconMap;
	
	public CardPanel(AppFrame frame)
	{
		
		this.frame = frame;
		this.setPreferredSize(new Dimension(this.frame.getWidth()-10,this.frame.getHeight()/6));
		this.setVisible(true);
		this.createImageIcons();
		
	}
	
	/**
	 * <pre>
	 * This is an Overriden paintComponent(Graphics g) method.
	 * 
	 * Process is going like this:
	 * 1. Change the font to "Times New Roman" and size of the string according to card's width, 
	 * 	  fontsize will always be 1/6 of the card's width
	 * 2. 
	 *   
	 * 	@param g 
	 * 				Graphics g through which paint has to be done.
	 * <pre>
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Font fontcard = new Font("Times New Roman",Font.BOLD,(int)(this.card_W/6));

		
		Player player = (Player) this.frame.gettoolbar().getCombobox().getSelectedItem();
		if(player != null)
		{
			
			ArrayList<PlayingCard> cards = this.getcards(player);
			if(cards != null)
			{
				int i = 0;
				for(PlayingCard card : cards)
				{
					this.changeface(card,i);
			
					g.setColor(Color.WHITE);
					g.fillRoundRect(this.card_X,this.card_Y,this.card_W,this.card_H,10,10);
			
					if(card.getSuit().name() == "SPADES" || card.getSuit().name() == "CLUBS")
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.RED);
					g.setFont(fontcard);
					g.drawString(this.letter, str_XUP, str_YUP);
					g.drawString(this.letter,str_XLW ,str_YLW);
					g.drawImage(this.img.getImage(), img_X, img_Y, img_W, img_H, this);
					i = i +1;
				}
			}
		}
				
	}
	
	/**
	 * <pre>
	 * This method will store the png files of icons into a HashMap
	 * 
	 * 1. Run a forloop over the values of PlayingCard.Suit.values
	 * 2. creates the Icon path where png file is stored.
	 * 3. extracts png file from the path and store that into imageIconMap.
	 * <pre>
	 */ 
	private void createImageIcons()
	{
		imageIconMap = new HashMap<Enum<PlayingCard.Suit>, ImageIcon>();

	    for (PlayingCard.Suit next : PlayingCard.Suit.values())
	    {
	    	imageIconMap.put(next, new ImageIcon("img/"+ next.name()+ ".png"));
	    }
	}
	
	/**
	 * <pre>
	 * This method will update the variables for the card to be painted on UI.
	 * 
	 * 1. Updates the img with the icon according to card.getSuit().value().
	 * 2. Card width will always be 1/7 of the view.CardPanel
	 * 3. Card Height will always be 1.5 times of the card width.
	 * 4. Icon's width and height will always be 1/4 of the card width and 1/5 of the card height respectively.
	 * 5. According to card.getvalue().name() the string will be updated.
	 *   
	 * 	@param card 
	 * 				card which needs to be painted on the UI
	 * 
	 * 	@param i
	 * 				which cards is being painted like 1st card, 2nd card, 3rd card etc.
	 * <pre>
	 */
	public void changeface(PlayingCard card,int i)
	{
		this.img =  this.getImageIcon(card.getSuit());

		int gap = 10*(i+1);
		this.card_W = (this.getWidth()/7);
		this.card_H = card_W*15/10;
		this.card_X = this.card_W*i + gap;
		this.card_Y = this.getHeight()/3 - this.card_W/2;	
		
		this.str_XUP = this.card_X + this.card_W/38;
		this.str_YUP = this.card_Y + this.card_H/9;
		
		this.str_XLW =  this.card_X + this.card_W - this.card_W/6;
		this.str_YLW = this.card_Y + this.card_H - this.card_H/35;
		
		this.img_W = this.card_W/4;
		this.img_H = card_H/5;
		img_X = this.card_X + this.img_W*14/10;
		this.img_Y = this.card_Y + this.img_H*2;

		switch(card.getValue().name())
		{
			case "EIGHT":	
				{ this.letter = "8"; break; }
		
			case "NINE":	
				{ this.letter = "9"; break; } 
			
			case "TEN":
				{  this.letter = "T"; break; }
			
			case "JACK":
				{  this.letter = "J"; break; }
		
			case "QUEEN":
				{  this.letter = "Q"; break; }
			
			case "KING":
				{  this.letter = "K"; break; }
			
			case "ACE":
				{  this.letter = "A"; break; }
			
		}
	}

	public void addCard(Player player,PlayingCard card)
	{
		
		if(player !=null)
		{
			this.frame.getPlayerinfo(player).addCards(card);
			this.paintagain();
		}
	}

	public ArrayList<PlayingCard> getcards(Player player)
	{
		return this.frame.getPlayerinfo(player).getcards();
	}
	
	 public ImageIcon getImageIcon(PlayingCard.Suit suit)
	   {
	      if (imageIconMap == null)
	         this.createImageIcons();

	      return imageIconMap.get(suit);
	   }
	
	public void paintagain()
	{
		repaint();
	}
		
}
