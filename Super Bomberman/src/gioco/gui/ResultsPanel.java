package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.WindowsHandler;
import gioco.controller.CustomButtonListener;
import gioco.controller.PlayerController;
import gioco.controller.ResultController;
import gioco.model.Gioco;
import gioco.net.Client;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//JPanel che deve essere impostato come GlassPane , con background semitrasparente
//permette di visualizzare i risultati di una partita, in CENTER ha un boxLayout che contiene l'immagine relativa al risultato e una label con il punteggio
//in SOUTH ci sono i CustomButton standard che permettono di effettuare una scelta e notificarla al controller 
public class ResultsPanel extends JPanel {
	private Color c ; 
	private ResultController controller;
	private JPanel buttons;
	private JPanel center;
	private CustomButton indietro;
	private CustomButton avanti;
	private JLabel label ;
	
	public ResultsPanel() {
		this.setLayout(new BorderLayout());
		center = new JPanel();
		BoxLayout centerLayout = new BoxLayout(center , BoxLayout.Y_AXIS);
		center.setLayout(centerLayout);
		center.setOpaque(false);
		label = new JLabel();
		buttons = new JPanel();
		buttons.setOpaque(false);
		BoxLayout bl = new BoxLayout(buttons , BoxLayout.X_AXIS);
		buttons.setLayout(bl);	
		buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setOpaque(false);
		this.setOpaque(false);
		
	}
	
	public void setController(ResultController rc) {
		this.controller = rc;
		addKeyListener(rc);	
		indietro = new CustomButton(Resources.esci, CustomButton.STANDARD);
		if(controller.getGioco().getResult() == Gioco.LOSS ) {
			SoundsHandler.getSoundsHandler().loss();
			c = new Color(0 , 0 , 0 , 120);
			label.setIcon(new ImageIcon(Resources.loss.getScaledInstance(Settings.wresultIcon,Settings.hresultIcon, Image.SCALE_SMOOTH)));	
			avanti = new CustomButton(Resources.rivincita, CustomButton.STANDARD);
		}
		else if(controller.getGioco().getResult() == Gioco.VICTORY) {
			SoundsHandler.getSoundsHandler().victory();
			c = new Color(0 , 0 , 0 , 120);
			label.setIcon(new ImageIcon(Resources.victory(Settings.selectedbomberman).getScaledInstance(Settings.wresultIcon,Settings.hresultIcon, Image.SCALE_SMOOTH)));
			if(!controller.getGioco().isMultiplayer())
				avanti = new CustomButton(Resources.prossimo , CustomButton.STANDARD);	
			else avanti = new CustomButton(Resources.giocaAncora , CustomButton.STANDARD);	
		}
		else {
			SoundsHandler.getSoundsHandler().draw();
			label.setIcon(new ImageIcon(Resources.draw.getScaledInstance(Settings.wresultIcon,Settings.hresultIcon, Image.SCALE_SMOOTH)));
			c = new Color(170, 170, 170, 120);
			avanti = new CustomButton(Resources.giocaAncora, CustomButton.STANDARD);
		}
		JLabel points = new JLabel();
		points.setAlignmentX(CENTER_ALIGNMENT);
		label.setAlignmentX(CENTER_ALIGNMENT);
		points.setHorizontalAlignment(JLabel.CENTER);
		points.setFont(Resources.myFont.deriveFont(30f));
		points.setOpaque(false);
		points.setForeground(Color.WHITE);
		if(!controller.getGioco().isMultiplayer())
			points.setText("TOTAL POINTS : " + controller.getGioco().getPlayer(Settings.PLAYER1).getPoints());
		else points.setText("TOTAL POINTS : " + controller.getGioco().getPlayer(Client.getClient().getOrderConnection()).getPoints());		
		label.setAlignmentY(CENTER_ALIGNMENT);
		label.setHorizontalAlignment(JLabel.CENTER);
		center.add(Box.createVerticalGlue());
		center.add(Box.createVerticalGlue());
		center.add(label);
		center.add(Box.createVerticalStrut(Settings.hCustomButton));
		center.add(points);
		center.add(Box.createVerticalGlue());
		this.add(center , BorderLayout.CENTER);
		indietro.setPreferredSize(new Dimension(Settings.wCustomButtonSelected ,Settings.hCustomButtonSelected));
		avanti.setPreferredSize(new Dimension(Settings.wCustomButtonSelected ,Settings.hCustomButtonSelected));
		indietro.setAlignmentY(TOP_ALIGNMENT);
		avanti.setAlignmentY(TOP_ALIGNMENT);
		indietro.addMouseListener(new CustomButtonListener(indietro));
		avanti.addMouseListener(new CustomButtonListener(avanti));
		
		indietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				WindowsHandler.getWindowsHandler().showGlassPane(false);
				WindowsHandler.getWindowsHandler().interruptGame();
				if(!controller.getGioco().isMultiplayer()) {				
					WindowsHandler.getWindowsHandler().setMapChooser();
				}
				else 
					WindowsHandler.getWindowsHandler().setMenu();
				super.mouseClicked(e);
			}
		});
		avanti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				WindowsHandler.getWindowsHandler().showGlassPane(false);
				WindowsHandler.getWindowsHandler().interruptGame();
				int map = controller.getGioco().getMap();
				if (map >= Settings.MAPSNUMBER) {
					WindowsHandler.getWindowsHandler().setMapChooser();
				} else {
					if(controller.getGioco().getResult() == Gioco.VICTORY)
						Settings.selectedMap = (map + 1);
					if(controller.getGioco().isMultiplayer())
						WindowsHandler.getWindowsHandler().setConnectingView(controller.getGioco().isBattleRoyale());
					else 
						WindowsHandler.getWindowsHandler().setGamePanel(false, false);
				}
				super.mouseReleased(e);
			}
		});
		

		buttons.add(Box.createHorizontalStrut(Settings.iconWidth));
		buttons.add(indietro);
		buttons.add(Box.createHorizontalGlue());
		buttons.add(avanti);
		buttons.add(Box.createHorizontalStrut(Settings.iconWidth));
		this.add(buttons , BorderLayout.SOUTH); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(c);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}
