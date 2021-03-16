package controller;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class WarningDialog
{
	public WarningDialog(String message)
	{
		JPanel myPanel = new JPanel();
    	JOptionPane.showMessageDialog(myPanel,message,"WARNING!!",JOptionPane.WARNING_MESSAGE);
	}
}
