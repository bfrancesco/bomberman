package gioco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gioco.WindowsHandler;

public class ResumeListener implements MouseListener {

	private PlayerController playercontroller;
	public ResumeListener(PlayerController controller ) {
		playercontroller = controller;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		playercontroller.setPaused(false);
		WindowsHandler.getWindowsHandler().showGlassPane(false);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
