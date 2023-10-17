package gioco.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;

import gioco.WindowsHandler;
import gioco.controller.CustomButtonListener;
import gioco.controller.NavigationButtonController;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//uda il borderlayout
//Pannello che contiene le informazioni che riguardano il gioco
//è composto da un serie di infoSection poste nel centro , all'interno di uno scrollpane con l'uso di BoxLayout
//in SOUTH è presente un NavigationButton per tornare indietro al menu principale
public class InfoPanel extends JPanel{
	
	private InfoSection controls;
	private InfoSection rules;
	private InfoSection enemies;
	private InfoSection powerUp;
	private InfoSection mode;
	private InfoSection credit;
	private JScrollPane scrollPane;
	
	public InfoPanel() {
		this.setPreferredSize(new Dimension(Settings.WINDOWWIDTH , Settings.WINDOWHEIGHT));
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		JPanel sections = new JPanel();
		sections.setOpaque(false);
		JLabel title = new JLabel("INFORMAZIONI");
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(Resources.myFont.deriveFont(55f));
		title.setForeground(Color.black);
		sections.setLayout(new BoxLayout(sections, BoxLayout.Y_AXIS));
	
		
		
		JPanel buttons = new JPanel(new FlowLayout());
		BoxLayout bl = new BoxLayout(buttons ,BoxLayout.X_AXIS);
		buttons.setLayout(bl);
		buttons.setOpaque(false);

		NavigationButton back = new NavigationButton ( "INDIETRO", Resources.indietro, Resources.indietroSelected);
		back.addMouseListener(new NavigationButtonController(back));
		back.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				WindowsHandler.getWindowsHandler().removeInfoPanel();
			}
		});	
		buttons.add(Box.createHorizontalStrut(Settings.iconWidth));
		buttons.add(back);
		
		buttons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black,Color.black));
		
		controls = new InfoSection(Resources.controls, Resources.keys);
		enemies = new InfoSection(Resources.enemies,  Resources.enemiesImage);
		rules = new InfoSection(Resources.rules , Resources.maps.get(0));
		powerUp = new InfoSection(Resources.powerup , Resources.firePowerUp);
		mode = new InfoSection(Resources.mode , null);
		credit = new InfoSection(Resources.credit, null); 
		sections.add(title);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(controls);		
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(rules);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(enemies);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(powerUp);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(mode);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		sections.add(credit);
		sections.add(Box.createVerticalStrut(Settings.iconButtonHeight/2));
		JViewport viewPort = new JViewport();
		scrollPane = new JScrollPane(viewPort);
		viewPort.setView(sections);
		viewPort.setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		this.add(scrollPane , BorderLayout.CENTER);
		this.add(buttons , BorderLayout.SOUTH);
	}
	
	//resetta la posizione della barra dello scrollpane , ossia della zona visualizzabile , all'inizio
	public void reset() {
		scrollPane.getVerticalScrollBar().setValue(0);
	}
	//metodo che permette di muoversi nello scrollPane
	public void moveBar(int times) {
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getValue() + scrollPane.getVerticalScrollBar().getUnitIncrement()*times );
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(Resources.createGameMenu.getScaledInstance(Settings.WINDOWWIDTH,Settings.WINDOWWIDTH, Image.SCALE_SMOOTH), 0 , 0,null);
		super.paintComponent(g);
	}
}
