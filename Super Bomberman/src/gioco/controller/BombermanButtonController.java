package gioco.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


import gioco.gui.ToChoose;
import gioco.sound.SoundsHandler;
import gioco.utilities.Settings;
/*
 * Il controller possiede un arraylist di ToChoose , il funzionamento è esclusivo , ovvero può essere selezionato un solo bottone fra quelli della lista
 * Selezionare il bottone button di riferimento , a cui è collegato il controller , implica che tutti gli altri bottoni vengano disattivati
 * Una volta che l' elemento è stato selezionato, viene aggiornata la variabile in Settings che indica quale bomberman è stato scelto
 * */
public class BombermanButtonController implements MouseListener {
	private ArrayList<ToChoose> bombermen;
	private ToChoose button ;
	private int color;
	public BombermanButtonController( ToChoose button,ArrayList<ToChoose> bombermen , int color ) {
		this.bombermen = bombermen ;
		this.button = button;
		this.color = color;
	}
	//deseleziona tutti gli altri toChoose e seleziona quello cliccato
	@Override
	public void mouseClicked(MouseEvent e) {
		Settings.selectedbomberman = color;
		for(ToChoose elem : bombermen)
			elem.setChoosen(false);
		button.setChoosen(true);
		
	}@Override
	public void mouseEntered(MouseEvent e) {
		SoundsHandler.getSoundsHandler().enteredStandardButton();
		button.setHighlighted(true);
		
	}@Override
	public void mouseExited(MouseEvent e) {
		SoundsHandler.getSoundsHandler().exitedStandardButton();
		button.setHighlighted(false);
		
	}@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	

}
