package gioco.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import gioco.WindowsHandler;
import gioco.controller.CustomButtonListener;
import gioco.controller.MuteSoundController;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

/*
 * menu è composto da 3 CustomButton di tipo Menu posti in CENTER in un JPanel che usa GridBag
 * in SOUTH sno presenti due CustomButton di tipo icona che permettono di mutare l'audio oppure di visualizzare infoPanel
 */
public class Menu extends JPanel {
	private CustomButton multiplayer;
	private CustomButton singleplayer;
	private CustomButton battleRoyale;
	private MuteButton muteEffects;
	private CustomButton info;
	private JTextField ip ;
	
	public Menu(int w , int h ) {
		int hgapButtons = 20;
		this.setLayout(new BorderLayout());
		JPanel menu = new JPanel();
		menu.setOpaque(false);
	 	setPreferredSize(new Dimension( w,h));
	 	
		JPanel buttons = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		menu.setLayout(layout);
		menu.setBorder(new EmptyBorder(10 ,0,5,0));
		buttons.setLayout(new GridLayout(0 , 1 , hgapButtons , hgapButtons));

		ip = new JTextField() {
		   
		};
		ip.setBackground( new Color(250, 170, 0) );
		ip.setFont(Resources.myFont);
		ip.setForeground(Color.WHITE);
		ip.setText(Settings.HOST);
		ip.setHorizontalAlignment(JTextField.CENTER);
		ip.setPreferredSize(new Dimension(Settings.iconButtonWidthSelected+8,Settings.iconButtonHeightSelected-10));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;	
		
		multiplayer = new CustomButton(Resources.multiPlayer , CustomButton.MENU);
		multiplayer.addMouseListener(new CustomButtonListener(multiplayer));
		multiplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler windowshandler = WindowsHandler.getWindowsHandler();
				Settings.HOST=ip.getText();
				windowshandler.setConnectingView(false);
				super.mouseClicked(e);
			}		
		});
		
		battleRoyale = new CustomButton(Resources.battleRoyale, CustomButton.MENU);
		battleRoyale.addMouseListener(new CustomButtonListener(battleRoyale));
		battleRoyale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Settings.HOST=ip.getText();
				WindowsHandler windowshandler = WindowsHandler.getWindowsHandler();
				windowshandler.setConnectingView(true);
				super.mouseClicked(e);
			}
		
		});
		
		singleplayer = new CustomButton(Resources.singlePlayer, CustomButton.MENU);
		singleplayer.addMouseListener(new CustomButtonListener(singleplayer));
		singleplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler.getWindowsHandler().resetMapChooser();
				WindowsHandler.getWindowsHandler().setMapChooser();
				super.mouseClicked(e);
			}
		
		});
		
		JLabel logo = new JLabel (new ImageIcon(Resources.logo.getScaledInstance(Settings.wLogo,Settings.hLogo, Image.SCALE_SMOOTH)));
		gbc.anchor = GridBagConstraints.NORTH;	
		menu.add(ip);
		menu.add(logo , gbc);
		
		buttons.setPreferredSize(new Dimension(Settings.wMenuButtonSelected , (Settings.hMenuButtonSelected)*3+hgapButtons*2));
		buttons.add(singleplayer );
		buttons.add(multiplayer );
		buttons.add(battleRoyale );
		gbc.weighty = 1;
		buttons.setOpaque(false);
		buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		gbc.anchor = GridBagConstraints.CENTER;
		menu.add(buttons , gbc);
		
		JPanel icons = new JPanel();
		
		icons.setLayout(new BoxLayout(icons , BoxLayout.X_AXIS));
		icons.setOpaque(false); 
			
		muteEffects= new MuteButton(SoundsHandler.getSoundsHandler().isEffectMute());	
		muteEffects.setAlignmentX(CENTER_ALIGNMENT);
		muteEffects.setPreferredSize(new Dimension(Settings.iconButtonWidthSelected+10,Settings.iconButtonHeightSelected+10));		
		muteEffects.addMouseListener(new MuteSoundController(muteEffects));
		info= new CustomButton(Resources.info , CustomButton.ICON);
		info.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler.getWindowsHandler().setInfoPanel();
				
				super.mouseClicked(e);
			}
		
		});
		info.setPreferredSize(new Dimension(Settings.iconButtonWidthSelected+10,Settings.iconButtonHeightSelected+10));
		info.setAlignmentY(TOP_ALIGNMENT);
		info.addMouseListener(new CustomButtonListener(info));
		muteEffects.setAlignmentY(TOP_ALIGNMENT);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		icons.add(Box.createHorizontalGlue());
		menu.add(ip);
		icons.add(muteEffects);
		icons.add(info);
		icons.add(Box.createHorizontalStrut(Settings.iconWidth));
		
		this.add(icons , BorderLayout.SOUTH);
		this.add(menu,BorderLayout.CENTER);
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.wallpaper, 0, 0, this.getWidth() , this.getHeight() ,null);
		
	}
	
	public void reset() {
		muteEffects.reset();
	}
	
}
