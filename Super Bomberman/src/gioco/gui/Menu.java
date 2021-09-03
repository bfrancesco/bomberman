package gioco.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import gioco.WindowsHandler;
import gioco.utilities.Resources;

public class Menu extends JPanel {
	private JButton multiplayer;
	private JButton singleplayer;
	private JButton battleRoyale;
	
	public Menu(int w , int h ) {
		setPreferredSize(new Dimension( w,h));
		JPanel buttons = new JPanel(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		setBorder(new EmptyBorder(10 ,10,10,10));
		setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
	
		gbc.fill = GridBagConstraints.HORIZONTAL;
		multiplayer = new JButton("MULTIPLAYER");
		multiplayer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.BLACK , Color.DARK_GRAY));
		multiplayer.setPreferredSize(new Dimension(300, 75));
		multiplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler windowshandler = WindowsHandler.getWindowsHandler();
				windowshandler.setConnectingView(false);
				super.mouseClicked(e);
			}
		
		});
		battleRoyale = new JButton("BATTLE ROYALE");
		battleRoyale.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK , Color.DARK_GRAY));
		battleRoyale.setPreferredSize(new Dimension(300, 75));
		battleRoyale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler windowshandler = WindowsHandler.getWindowsHandler();
				windowshandler.setConnectingView(true);
				super.mouseClicked(e);
			}
		
		});
		singleplayer = new JButton("SINGLEPLAYER");
		singleplayer.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK , Color.DARK_GRAY));
		singleplayer.setPreferredSize(new Dimension(300, 75));
		singleplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler.getWindowsHandler().setMapChooser();
				super.mouseClicked(e);
			}
		
		});
		multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		singleplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel logo = new JLabel (new ImageIcon(Resources.logo.getScaledInstance(600, 300, Image.SCALE_SMOOTH)));
		gbc.anchor = GridBagConstraints.NORTH;	
		add(logo , gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		buttons.add(singleplayer , gbc);
		buttons.add(Box.createRigidArea(new Dimension(300 , 20)) , gbc);
		buttons.add(multiplayer , gbc);
		buttons.add(Box.createRigidArea(new Dimension(300 , 20)) , gbc);
		buttons.add(battleRoyale , gbc);
		gbc.weighty = 1;
		buttons.setOpaque(false);
		buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		add(buttons , gbc);
		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.wallpaper, 0, 0, this.getWidth() , this.getHeight() ,null);
		
	}
	
}
