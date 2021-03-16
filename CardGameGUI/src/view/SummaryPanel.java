package view;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import model.interfaces.Player;

public class SummaryPanel extends JScrollPane
{
	private DefaultTableModel model;
	private JTable table;
	private int playerIDCol = 0;
	private int playernameCol = 1;
	private int playerpointscol = 2;
	private int betPointsCol = 3;
	private int resultCol = 4;
	private AppFrame frame;
	private String draw = "DRAW";
	private String loss = "LOSS";
	private String win = "WIN";
	private static String status = "Not yet played";
	private int index;
	
	public SummaryPanel(AppFrame frame)
	{
		this.frame = frame;
		this.setPreferredSize(new Dimension(this.frame.getWidth()-10,this.frame.getHeight()/4));

		Object[] header = {"Player ID","Player Name","Player Initial Points","Bet Points","Win/Loss"};
		this.model = new DefaultTableModel(header,0); 
		this.table = new JTable(model);

		this.getViewport().add(table);
		this.createVerticalScrollBar();
	}
	
	public void changePLayerDetails(Player player)
	{
		index = this.updateResultDetailsHelper(player);
			if(index !=-1)	
			{
				this.model.setValueAt(player.getPlayerName(), index, this.playernameCol);
				this.model.setValueAt(player.getPoints(), index, this.playerpointscol);
				this.model.setValueAt(player.getBet(), index, this.betPointsCol);
				this.model.setValueAt("Not yet played", index, this.resultCol);
			}
			else
				this.model.addRow(new Object[]{player.getPlayerId(),player.getPlayerName(),player.getPoints(),
						player.getBet(),status});
	}
	
	public void updateBetDetail(Player player)
	{
		index = this.updateResultDetailsHelper(player);
		if(index !=-1)
			this.model.setValueAt(player.getBet(),this.updateResultDetailsHelper(player), this.betPointsCol);
	}
	
	public void updateResultDetails(Player player,int number)
	{
		int index = this.updateResultDetailsHelper(player);
		if(index!=-1)
		{
			int i = 0;
			this.model.setValueAt(i, index, this.betPointsCol);
			this.model.setValueAt(player.getPoints(), index, this.playerpointscol);
		
			switch(number)
			{
			case 0: this.model.setValueAt(this.draw,index , this.resultCol); break;
		
			case 1: this.model.setValueAt(this.win, index, this.resultCol); break;
			
			case 2: this.model.setValueAt(this.loss, index, this.resultCol); break;
				
			default: break;
			}
		}
	}
	
	public void removePlayer(Player player)
	{
		int index = this.updateResultDetailsHelper(player);
		if(index!=-1)
			this.model.removeRow( this.updateResultDetailsHelper(player));
	}
	
	private int updateResultDetailsHelper(Player player)
	{	
		for(int i = 0; i<this.model.getRowCount();i++)
		{
			if(player.getPlayerId().equals(this.model.getValueAt(i,this.playerIDCol)))	
				{
					return i;
				}
		}
		return -1;
	}
	

}
