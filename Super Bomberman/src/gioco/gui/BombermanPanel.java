package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import gioco.Settings;
import gioco.controller.PlayerController;
import gioco.model.Player;

public class BombermanPanel extends JPanel implements Runnable{
	


	private static final long serialVersionUID = -5129791240805142753L;
	private PlayerController controller;
	private int height; 
	private int width;
	private GamePanel giocoView;
	
	public BombermanPanel(int height , int width) {
		this.height = height;
		this.width = width;

		this.setBackground(Color.black);
		this.setOpaque(false);		
	
	}
		
	
	public void setController(PlayerController controller) {
		this.controller = controller;
		addKeyListener(controller);
		requestFocus();
		giocoView = new GamePanel(controller);
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.add(giocoView, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
	}
	
	@Override
	public void run() {
		giocoView.repaint();
		while (!controller.getGioco().isGameOver()) {
			giocoView.getNotStatics().update();
			try {
				Thread.sleep(20);

			} catch (InterruptedException e) {
				return;
			}
			
		}
		
	}


	public GamePanel getGiocoView() {
		return giocoView;
	}
	
	
}
