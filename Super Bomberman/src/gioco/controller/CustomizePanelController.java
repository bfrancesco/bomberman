package gioco.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gioco.WindowsHandler;
import gioco.gui.MapChooser;
import gioco.gui.ToChoose;
import gioco.utilities.Settings;

public class CustomizePanelController extends KeyAdapter{
	private MapChooser mapChooser;
	public CustomizePanelController(MapChooser mc) {
		this.mapChooser = mc;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			
			break;
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_RIGHT:
			ToChoose rightbutton = mapChooser.getBombermanColors().get((Settings.selectedbomberman-Settings.WHITE+1)%Settings.COLORS);
			rightbutton.getController().mouseClicked(new MouseEvent(rightbutton, MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));		
			break;
		case KeyEvent.VK_LEFT:
			int index = (Settings.selectedbomberman-Settings.WHITE-1);
			if(index<0)
				index = Settings.COLORS-1;
			ToChoose leftbutton = mapChooser.getBombermanColors().get(index);
			leftbutton.getController().mouseClicked(new MouseEvent(leftbutton, MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));
			break;
		case KeyEvent.VK_ENTER:
			WindowsHandler.getWindowsHandler().setGamePanel(false, false, null);
			break;
		case KeyEvent.VK_ESCAPE:
			WindowsHandler.getWindowsHandler().setMenu();
			break;
		}
	}
	
}
