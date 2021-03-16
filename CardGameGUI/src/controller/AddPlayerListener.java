package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.SimplePlayer;
import model.interfaces.Player;
import view.AppFrame;

public class AddPlayerListener implements ActionListener
{
	
	private AppFrame frame;
	
	public AddPlayerListener(AppFrame frame)
	{
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.addPlayerDialog();
	}

	/**
	 * <pre>
	 * This method is used to get player details through JOptionPane dialog.
	 * 1. Created JTestField to store input data
	 * 2. Created JPanel to add the JtextFields.
	 * 3. Instantiating a JOptionPane.ConfirmDialog() to get input
	 * 4. Instantiating a new player with input values.
	 * 5. informing views and model to update.
 	 * <pre>
	 */
	public void addPlayerDialog()
	{
		JTextField addplayer = new JTextField(5);
		JTextField playerno = new JTextField(5);
		JTextField initialpoints = new JTextField(5);
		
		JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("Name:"));
	    myPanel.add(addplayer);
	    myPanel.add(new JLabel("Player index number:"));
	    myPanel.add(playerno);
	    
	    myPanel.add(new JLabel("Player Initial points:"));
	    myPanel.add(initialpoints);
		
		int result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter player Details", JOptionPane.OK_CANCEL_OPTION);
	    
		if (result == JOptionPane.OK_OPTION) 
		{
			try
			{
			Player newplayer = new SimplePlayer(playerno.getText(),addplayer.getText(),Integer.parseInt(initialpoints.getText()));
			
			Player oldplayer = this.frame.getgameEngine().getPlayer(newplayer.getPlayerId());
			this.frame.getgameEngine().addPlayer(newplayer);
			if(oldplayer != null)
			{	this.frame.addPlayer(oldplayer,newplayer);}
			else
				this.frame.addPlayer(newplayer);
			this.frame.gettoolbar().changeDealState(false);
			this.frame.gettoolbar().updateCombobox(newplayer);
			this.frame.getsummarypanel().changePLayerDetails(newplayer);
			this.frame.getStatusbar().updateStatusBar(String.format("%s has been added to this Game.",addplayer.getText()));
			}
			catch(Exception e)
			{new WarningDialog("Please input the data in correct format.");}
		}
	}
}