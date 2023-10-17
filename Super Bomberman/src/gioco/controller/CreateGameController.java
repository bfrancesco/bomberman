package gioco.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gioco.WindowsHandler;
import gioco.gui.CreateGamePanel;
import gioco.gui.ToChoose;
import gioco.utilities.Settings;

//Controller del pannello di creazione di una partita, offre la possibilità di scegliere fra le opzioni con l'aiuto delle frecce direzionali 
//Permette di uscire tramite Escape e avviare la partita tramite ENTER
//Il controller simula il click con il mouse sull'elemento successivo nella direzione della freccia premuta sulla tastiera
public class CreateGameController extends KeyAdapter{
	private CreateGamePanel mapChooser;
	public CreateGameController(CreateGamePanel mc) {
		this.mapChooser = mc;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			ToChoose downbutton = mapChooser.getMapPanel().getToChoose().get(((Settings.selectedMap-1)+1)%Settings.MAPSNUMBER);
			downbutton.getController().mouseClicked(new MouseEvent(downbutton, MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));	
			mapChooser.getMapPanel().reposition();
			break;
		case KeyEvent.VK_UP:
			int i= Settings.selectedMap-2;
			if(i<0)
				i = Settings.MAPSNUMBER-1;
			ToChoose upbutton = mapChooser.getMapPanel().getToChoose().get(i);
			upbutton.getController().mouseClicked(new MouseEvent(upbutton, MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));	
			mapChooser.getMapPanel().reposition();
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
			WindowsHandler.getWindowsHandler().setGamePanel(false, false);
			break;
		case KeyEvent.VK_ESCAPE:
			WindowsHandler.getWindowsHandler().setMenu();
			break;
		}
	}
	
}
