package gioco.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class pointsPanel extends JPanel {
	private JLabel pointsPlayer1;
	private JLabel pointsPlayer2;
	private JLabel enemiesLeft; 
	private JLabel clock; 
	private Font font;
	private boolean multiplayer;
	private boolean battleRoyale;
	
	
	public pointsPanel(boolean multiplayer , boolean battle) {
		this.setOpaque(true);
		this.setBackground(Color.ORANGE);
		GridLayout f = new  GridLayout(1 , 5);
		f.setHgap(100);
		this.setLayout(f);
		Border border = BorderFactory.createLineBorder(Color.BLACK , 3);
		this.setBorder(border);
		ImageIcon p1 =  new ImageIcon( Resources.bombermanIcons.get(Settings.selectedbomberman-Settings.WHITE).getScaledInstance(40, 40 ,Image.SCALE_SMOOTH));
		ImageIcon p2 =  new ImageIcon( Resources.bombermanIcons.get(1).getScaledInstance(40, 40 ,Image.SCALE_SMOOTH));
		ImageIcon time =  new ImageIcon( Resources.iconClock.getScaledInstance(40, 40 ,Image.SCALE_SMOOTH));
		ImageIcon enemy =  new ImageIcon( Resources.iconEnemy.getScaledInstance(40, 40 ,Image.SCALE_SMOOTH));
		pointsPlayer1 = new JLabel("0" , p1 , JLabel.LEFT);
		pointsPlayer2 = new JLabel("0" , p2, JLabel.LEFT);
		clock = new JLabel("200" , time , JLabel.LEFT);
		enemiesLeft = new JLabel("0" , enemy , JLabel.LEFT);
		font = new Font("Avant-Garde" , Font.BOLD , 25);	
		pointsPlayer1.setForeground(Color.BLACK);
		pointsPlayer1.setBackground(new Color(255 ,255 ,255 ));
		pointsPlayer1.setOpaque(false);
		pointsPlayer1.setIconTextGap(25);
		pointsPlayer1.setFont(font);
		pointsPlayer2.setForeground(Color.BLACK);
		pointsPlayer2.setBackground(new Color(255 ,255 ,255 ));
		pointsPlayer2.setOpaque(false);
		pointsPlayer2.setIconTextGap(25);
		pointsPlayer2.setFont(font);
		clock.setForeground(Color.black);
		clock.setBackground(new Color(255 ,255 ,255 ));
		clock.setOpaque(false);
		clock.setIconTextGap(25);
		clock.setFont(font);
		enemiesLeft.setForeground(Color.BLACK);
		enemiesLeft.setBackground(new Color(255 ,255 ,255 ));
		enemiesLeft.setOpaque(false);
		enemiesLeft.setIconTextGap(25);
		enemiesLeft.setFont(font);
		this.add(pointsPlayer1);
		if(multiplayer && !battle)
			this.add(pointsPlayer2);
		this.add(clock);
		this.add(enemiesLeft);
		
	}
	
	
	public void setPoints(int p1 , int p2 , int e ) {
		if(pointsPlayer2 != null)
			pointsPlayer2.setText(Integer.toString(p2));
		pointsPlayer1.setText(Integer.toString(p1));
		enemiesLeft.setText(Integer.toString(e));
	}
	
	public void setTime(int time) {
		clock.setText(Integer.toString(time));
	}
}
