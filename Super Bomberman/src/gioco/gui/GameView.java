package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gioco.controller.PlayerController;
import gioco.model.Block;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//Il pannello contiene un EntitiesPanel per i modificabili
// gestisce la visualizzazione della mappa , elemento statico 
// inizializza e permette l'accesso a entitiesPanel
public class GameView extends JPanel {

	private static final long serialVersionUID = -4664820033827585199L;

	private PlayerController controller;
	private Image map;
	private EntitiesPanel notStatics; 

	public GameView(PlayerController controller , int h , int w) {
		this.controller = controller;
		this.setPreferredSize(new Dimension(w, h));

		map = Resources.maps.get(controller.getGioco().getMap()-1);
		this.setOpaque(false);
		notStatics = new EntitiesPanel(controller);
		this.add(notStatics);
		BoxLayout a = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(a);
		notStatics.setPreferredSize(getPreferredSize());

	}
	


	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(map.getScaledInstance(Settings.BLOCKSIZEX*Settings.LOGICWIDTH, Settings.BLOCKSIZEY*Settings.LOGICHEIGHT, Image.SCALE_SMOOTH),0,0, null);

	}

	public EntitiesPanel getEntitiesPanel() {
		return notStatics;
	}
	
	
}
