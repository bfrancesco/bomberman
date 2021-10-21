package gioco.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import gioco.gui.NavigationButton;
import gioco.sound.SoundsHandler;

//Controller che gestisce bottoni personalizzati che reagiscono al mouse.
public class NavigationButtonController extends MouseAdapter {
	private NavigationButton button;

	public NavigationButtonController(NavigationButton nb) {
		button = nb;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		SoundsHandler.getSoundsHandler().exitedMenuButton();
		button.setExited();
		super.mouseExited(e);

	}

	@Override
	public void mouseEntered(MouseEvent e) {

		SoundsHandler.getSoundsHandler().enteredMenuButton();
		button.setEntered();
		super.mouseEntered(e);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		button.setExited();
		super.mouseClicked(e);
	}
}
