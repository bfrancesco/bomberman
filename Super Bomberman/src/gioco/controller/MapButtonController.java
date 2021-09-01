package gioco.controller;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gioco.WindowsHandler;
import gioco.gui.ToChoose;
import gioco.utilities.Settings;

public class MapButtonController implements MouseListener {
	
	ArrayList<ToChoose> toChoose;
	JLabel selected ;
	ToChoose button; 
	int index;
	
	//nota che le mappe vanno da 1 mapsnumber, l'indice deve andare da 1 a mapsnumber
	public MapButtonController(ToChoose button , ArrayList<ToChoose> toChoose , JLabel selected , int index ) {
		this.button = button;
		this.selected = selected;
		this.toChoose = toChoose;
		this.index = index;
		button.setTitle("Mappa N. " + index);
		button.setTitleVisibile(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(ToChoose t : toChoose) {
			t.setChoosen(false);
		}
		button.setChoosen( true );
		Settings.selectedMap = index;
		selected.setIcon(new ImageIcon(((ImageIcon) button.getIcon()).getImage().getScaledInstance(300 , 300 , Image.SCALE_SMOOTH)));
		
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		button.setHighlighted(true);
		
	}@Override
	public void mouseExited(MouseEvent e) {
		button.setHighlighted(false);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked(e);
		
	}
	
	
	
}
