package view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import controller.AddPlayerListener;
import controller.ExitItemListener;
import controller.RemoveplayerListener;

public class JMenuBarview extends JMenuBar
{
	private AppFrame frame;
	private RemoveplayerListener removeplayerListener;

	public JMenuBarview(AppFrame frame)
	{
		
		this.frame = frame;
		
		removeplayerListener = new RemoveplayerListener(this.frame);
		
	    JMenu fileMenu = new JMenu("Player Settings");
	    fileMenu.setMnemonic(KeyEvent.VK_S);
	    this.add(fileMenu);

	    JMenuItem addPlayerItem = new JMenuItem("Add Player", KeyEvent.VK_A);
	    
	    JMenuItem removePlayerItem = new JMenuItem("Remove Player", KeyEvent.VK_R);
	    JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
	    exitItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.ALT_DOWN_MASK));
	       
	    fileMenu.add(addPlayerItem);
	    fileMenu.add(removePlayerItem);
	    fileMenu.addSeparator();
	    fileMenu.add(exitItem);
	       
	    AddPlayerListener addplayerListener = new AddPlayerListener(this.frame);    
	    addPlayerItem.addActionListener(addplayerListener);
	    removePlayerItem.addActionListener(removeplayerListener);
		      
	    exitItem.addActionListener(new ExitItemListener());

	    this.add(fileMenu);   
	}
	
}
