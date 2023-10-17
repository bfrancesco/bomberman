package gioco.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import gioco.controller.PlayerController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

/* 
 * Pannello che permette di visualizzare i punteggi di riferimento 
 * viene modificato dal player controller che lo gestisce
 * ha visualizzazioni diverse per le varie modalità di gioco
 * per il multiplayer infatti sono presenti due punteggi per i due player, per le altre modalità viene visualizzato solo il punteggio del player
 * Ogni statistica è una JLabel con immagine e testo
 */
public class PointsPanel extends JPanel {
	private static final long serialVersionUID = -401237639649406189L;
	private JLabel pointsPlayer1;
	private JLabel pointsPlayer2;
	private JLabel enemiesLeft; 
	private JLabel clock; 
	private PlayerController controller;
	
	public PointsPanel(PlayerController controller) {
		this.controller = controller;
		this.setOpaque(true);
		this.setBackground(Color.ORANGE);
		GridLayout f = new  GridLayout(1 , 5);
		this.setLayout(f);
		Border border = BorderFactory.createLineBorder(Color.BLACK , 3);
		this.setBorder(border);
		ImageIcon p1 =  new ImageIcon( Resources.bombermanIcons.get(Settings.selectedbomberman-Settings.WHITE).getScaledInstance(Settings.iconWidth, Settings.iconHeight ,Image.SCALE_SMOOTH));
		ImageIcon p2 =  new ImageIcon( Resources.bombermanIcons.get((Settings.selectedbomberman-Settings.WHITE+1)%2).getScaledInstance(Settings.iconWidth, Settings.iconHeight ,Image.SCALE_SMOOTH));
		ImageIcon time =  new ImageIcon( Resources.iconClock.getScaledInstance(Settings.iconWidth, Settings.iconHeight ,Image.SCALE_SMOOTH));
		ImageIcon enemy =  new ImageIcon( Resources.iconEnemy.getScaledInstance(Settings.iconWidth, Settings.iconHeight  ,Image.SCALE_SMOOTH));
		pointsPlayer1 = new JLabel("0" , p1 , JLabel.LEFT);
		pointsPlayer2 = new JLabel("0" , p2, JLabel.LEFT);
		clock = new JLabel("200" , time , JLabel.LEFT);
		enemiesLeft = new JLabel("0" , enemy , JLabel.LEFT);	
		pointsPlayer1.setForeground(Color.BLACK);
		pointsPlayer1.setOpaque(false);
		pointsPlayer1.setIconTextGap(25);
		pointsPlayer1.setFont(Resources.myFont.deriveFont(25f));
		pointsPlayer2.setForeground(Color.BLACK);
		pointsPlayer2.setOpaque(false);
		pointsPlayer2.setIconTextGap(25);
		pointsPlayer2.setFont(Resources.myFont.deriveFont(25f));
		clock.setForeground(Color.black);
		clock.setOpaque(false);
		clock.setIconTextGap(25);
		clock.setFont(Resources.myFont.deriveFont(25f));
		enemiesLeft.setForeground(Color.BLACK);
		enemiesLeft.setOpaque(false);
		enemiesLeft.setIconTextGap(25);
		enemiesLeft.setFont(Resources.myFont.deriveFont(25f));
		this.add(pointsPlayer1);
		if(controller.isMultiplayer() && !controller.isBattleRoyale())
			this.add(pointsPlayer2);
		this.add(clock);
		this.add(enemiesLeft);
		
	}
	
	
	public void setPoints() {
		if(controller.isMultiplayer()) {
			pointsPlayer1.setText(Integer.toString(controller.getGioco().getPlayer(controller.getClient().getOrderConnection()).getPoints()));
			if(!controller.isBattleRoyale()) {
				pointsPlayer2.setText(Integer.toString(controller.getGioco().getPlayer((controller.getClient().getOrderConnection()+1)%2+Settings.PLAYER1).getPoints()));
			}
		}
		else 
			pointsPlayer1.setText(Integer.toString(controller.getGioco().getPlayer(Settings.PLAYER1).getPoints()));
		enemiesLeft.setText(Integer.toString(controller.getGioco().getEnemies().size()));
	}
	
	public void setTime(int time) {
		clock.setText(Integer.toString(time));
	}
}
