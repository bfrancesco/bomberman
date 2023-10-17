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
import gioco.net.Client;
import gioco.utilities.Settings;

//pannello che contiene la visualizazione del gioco e dei punti 
//permette l'accesso a quest'ultimi
public class GamePanel extends JPanel {
	private static final long serialVersionUID = -5129791240805142753L;
	private PlayerController controller;
	private int height; 
	private int width;
	private GameView giocoView;
	private PointsPanel pointView;
	
	public GamePanel() {
		this.height = Settings.WINDOWHEIGHT;
		this.width = Settings.WINDOWWIDTH;
		this.setPreferredSize(new Dimension(width , height));
		this.setBackground(Color.black);
		this.setOpaque(true);		
		Border border = BorderFactory.createLineBorder(Color.black , 2 , true);
		this.setBorder(border);
	}
		
	
	public void setController(PlayerController controller) {
		this.controller = controller;
		addKeyListener(controller);	
		requestFocus();
		giocoView = new GameView(controller , Settings.WINDOWHEIGHT , Settings.WINDOWWIDTH);
		pointView = new PointsPanel(controller);
		BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout( b );
		this.add(pointView , b);

		pointView.setPreferredSize(new Dimension(Settings.WINDOWWIDTH ,Settings.HEIGHTSTAT));
		this.add(giocoView , b);
		
		controller.setBlockSizes(Settings.WINDOWWIDTH , Settings.WINDOWHEIGHT-Settings.HEIGHTSTAT);	  
	}
	
	public void setStat(int time) {
		pointView.setPoints();
		pointView.setTime(time);
	}
	

	public GameView getGiocoView() {
		return giocoView;
	}
	
	
}
