package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import controller.WarningDialog;
import model.PlayerInfo;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class AppFrame extends JFrame
{
	private GameEngine gameEngine;
	private SummaryPanel summaryPanel;
	private StatusBar statusbar;
	private Toolbar toolbar;
	private CardPanel cardPanel;
	private Container center;
	private HashMap<Player,PlayerInfo> playersInfo;
	private JMenuBar menubar;
	
	public AppFrame(GameEngine gameEngine)
	{
		super("Card Game");
		this.setLayout(new BorderLayout(2, 2));
		this.gameEngine = gameEngine;
		setLayout(new BorderLayout());
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(200, 100, (int) dimension.getWidth()/2,  (int) dimension.getHeight() / 2);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
        setMinimumSize(new Dimension((int) dimension.getWidth() / 2, (int) dimension.getHeight() / 2));
		
		this.playersInfo = new HashMap<Player,PlayerInfo>();

		
		menubar = new JMenuBarview(this);
		this.toolbar = new Toolbar(this);	
		this.center = new JPanel();

		this.summaryPanel = new SummaryPanel(this); 
		this.cardPanel = new CardPanel(this); 
		this.statusbar = new StatusBar(this); 
		
		
		center.setLayout(new BorderLayout());

		center.add(this.summaryPanel,BorderLayout.NORTH);
		center.add(this.cardPanel,BorderLayout.CENTER);
		
		this.setJMenuBar(menubar);
		this.add(toolbar, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(statusbar, BorderLayout.SOUTH);
			
	}
	
	public void setPlayerinfo(Player player,PlayerInfo info)
	{
		this.playersInfo.replace(player, info);
	}
	
	// Function Overloading has been used here 
	/**
	 * <pre>
	 * This method will be called if there is not previous player with same id.
	 * 	@param player 
	 * 				new player to be added in the list.
	 * <pre>
	 */
	public void addPlayer(Player player)
	{
		this.playersInfo.put(player, new PlayerInfo());
	}
	
	/**
	 * <pre>
	 * This method will be called when there is a previous player with same id.
	 * 	@param oldplayer 
	 * 				old player with same ID which needs to be removed from the list first.
	 * 
	 * 	@param newplayer
	 * 				new player with same ID which needs to be added in the list.
	 * <pre>
	 */
	public void addPlayer(Player oldplayer,Player newplayer)
	{
		this.playersInfo.remove(oldplayer);
		this.playersInfo.put(newplayer, new PlayerInfo());
	}
	
	public void removePlayer(Player player)
	{
		this.playersInfo.remove(player);
	}
			
	/**
	 * <pre> Reset of the game is done as follows:
	 * 1. Loop over the elements of players set
	 * 2. If house set dealt to true
	 * 3. If other than house then set isbet and isdealt to false;
	 * 4. Updating the UI with changes.
	 * 5. Giving a warning dialog to let player know that game has been reset.
	 * <pre>
	 */
	public void resetGame()
	{
		for (Player player :  this.getplayersSet())
		{
			if(player.equals(this.gettoolbar().gethouseplayer()))
				this.getPlayerinfo(player).setdealt(true);
			else
			{
				this.getPlayerinfo(player).setbet(false);
				this.getPlayerinfo(player).setdealt(false);
				this.getPlayerinfo(player).setcanDeal(true);
			}
		}
		this.gettoolbar().changestates((Player) this.gettoolbar().getCombobox().getSelectedItem());

		new WarningDialog("Game has been reset.");
	}
	
	public GameEngine getgameEngine()
	{
		return this.gameEngine;
	}
	
	public Toolbar gettoolbar()
	{
		return this.toolbar;
	}

	public SummaryPanel getsummarypanel()
	{
		return this.summaryPanel;
	}
	
	public CardPanel getcardpanel()
	{
		return this.cardPanel;
	}
	
	public StatusBar getStatusbar()
	{
		return this.statusbar;
	}
	
	public Container getcenter()
	{
		return this.center;
	}
	
	public Set<Player> getplayersSet()
	{
		return this.playersInfo.keySet();
	}
	
	public PlayerInfo getPlayerinfo(Player player)
	{
		return this.playersInfo.get(player);
	}
	
}