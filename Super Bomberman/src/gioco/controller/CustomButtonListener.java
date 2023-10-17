package gioco.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import gioco.gui.CustomButton;
import gioco.sound.SoundsHandler;

//Controller che permette a un CustomButton, tipologia principale di bottoni nell'applicazione , di reagire al cursore in maniera audiovisiva
public class CustomButtonListener implements MouseListener{
	
	private CustomButton button;
	public  CustomButtonListener(CustomButton cb) {
		button = cb;
		//per evitare che si veda il focus che circonda il bottone
		button.setFocusable(false);
	}
	
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		button.select(true);
		if(button.getType() == CustomButton.MENU)
			SoundsHandler.getSoundsHandler().enteredMenuButton();
		else 
			SoundsHandler.getSoundsHandler().enteredStandardButton();
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		button.select(false);
		if(button.getType() == CustomButton.MENU)
			SoundsHandler.getSoundsHandler().exitedMenuButton();
		else 
			SoundsHandler.getSoundsHandler().exitedStandardButton();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(button.getType() == CustomButton.MENU) 
			SoundsHandler.getSoundsHandler().clickSound();
		button.select(false);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
