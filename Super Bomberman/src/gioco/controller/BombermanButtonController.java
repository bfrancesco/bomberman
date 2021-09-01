package gioco.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


import gioco.gui.ToChoose;
import gioco.utilities.Settings;

public class BombermanButtonController implements MouseListener {
	private ArrayList<ToChoose> bombermen;
	private ToChoose button ;
	private int color;
	public BombermanButtonController( ToChoose button,ArrayList<ToChoose> bombermen , int color ) {
		this.bombermen = bombermen ;
		this.button = button;
		this.color = color;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Settings.selectedbomberman = color;
		for(ToChoose elem : bombermen)
			elem.setChoosen(false);
		button.setChoosen(true);
		
	}@Override
	public void mouseEntered(MouseEvent e) {
		button.setHighlighted(true);
		
	}@Override
	public void mouseExited(MouseEvent e) {
		button.setHighlighted(false);
		
	}@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	

}
