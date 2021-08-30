package gioco.controller;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gioco.gui.ToChoose;
import gioco.gui.WindowsHandler;

public class ToChooseController implements MouseListener {
	
	ArrayList<ToChoose> toChoose;
	JLabel selected ;
	ToChoose label; 
	
	public ToChooseController(ToChoose label , ArrayList<ToChoose> toChoose , JLabel selected ) {
		this.label = label;
		this.selected = selected;
		this.toChoose = toChoose;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(ToChoose t : toChoose) {
			t.setChoosen(false);
		}
		label.setChoosen( true );
		WindowsHandler.getWindowsHandler().setMap(label.getIndex());
		selected.setIcon(new ImageIcon(((ImageIcon) label.getIcon()).getImage().getScaledInstance(250 , 250 , Image.SCALE_SMOOTH)));
		
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked(e);
		
	}
	
	
	
}
