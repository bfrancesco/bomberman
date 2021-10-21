package gioco.controller;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


import gioco.gui.ToChoose;
import gioco.sound.SoundsHandler;
import gioco.utilities.Settings;

/*
 * Il controller possiede un arraylist di ToChoose , il funzionamento è esclusivo , ovvero può essere selezionato un solo bottone fra quelli della lista
 * Selezionare il bottone button di riferimento , a cui è collegato il controller , implica che tutti gli altri bottoni vengano disattivati
 * L'elemento selezionato viene mostrato nella JLabel selected
 * Una volta che l' elemento è stato selezionato, viene aggiornata la variabile in Settings che indica quale mappa è stata scelta
 * */
public class MapButtonController implements MouseListener {
	
	private ArrayList<ToChoose> toChoose;
	private JLabel selected ;
	private ToChoose button; 
	private int index;
	
	//nota che le mappe vanno da 1 mapsnumber, l'indice deve andare da 1 a mapsnumber
	public MapButtonController(ToChoose button , ArrayList<ToChoose> toChoose , JLabel selected , int index ) {
		this.button = button;
		this.selected = selected;
		this.toChoose = toChoose;
		this.index = index;
		button.setTitle("Mondo " + index);
		button.setTitleVisibile(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(ToChoose t : toChoose) {
			t.setChoosen(false);
		}
		button.setChoosen( true );
		Settings.selectedMap = index;
		selected.setIcon(new ImageIcon(((ImageIcon) button.getIcon()).getImage().getScaledInstance(Settings.imageShow , Settings.imageShow , Image.SCALE_SMOOTH)));
		
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		SoundsHandler.getSoundsHandler().enteredStandardButton();
		button.setHighlighted(true);
		
	}@Override
	public void mouseExited(MouseEvent e) {
		button.setHighlighted(false);
		SoundsHandler.getSoundsHandler().exitedStandardButton();
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked(e);
		
	}
	
	
	
}
