package gioco.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import gioco.controller.PlayerController;


public class GameInterface extends JPanel {
	


	private static final long serialVersionUID = -5129791240805142753L;
	private PlayerController controller;
	private int height; 
	private int width;
	private GamePanel giocoView;
	private pointsPanel pointView;
	
	public GameInterface(int width , int height) {
		this.height = height;
		this.width = width;
		this.setPreferredSize(new Dimension(width , height));
		this.setBackground(Color.black);
		this.setOpaque(false);		
		Border border = BorderFactory.createLineBorder(Color.black , 2 , true);
		this.setBorder(border);
	}
		
	
	public void setController(PlayerController controller) {
		this.controller = controller;
		addKeyListener(controller);	
		requestFocus();
		giocoView = new GamePanel(controller , 715 , 715);
		pointView = new pointsPanel(false);
		BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout( b );
		this.add(pointView , b);
		this.add(giocoView , b);
		
		
//giocoView.setPreferredSize(new Dimension(715,715));	
	}
	
	public void setStat(int time) {
		if(controller.getGioco().isMultiplayer())
			pointView.setPoints(controller.getGioco().getPlayer1().getPoints(), controller.getGioco().getPlayer2().getPoints(), controller.getGioco().getEnemies().size());
		else
			pointView.setPoints(controller.getGioco().getPlayer1().getPoints(),0 ,  controller.getGioco().getEnemies().size());
		
		pointView.setTime(time);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
	}
	
	public void paintMap() {
		repaint();
	}
	

	public GamePanel getGiocoView() {
		return giocoView;
	}
	
	
}
