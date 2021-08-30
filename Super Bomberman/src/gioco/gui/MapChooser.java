package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Image;
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
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;


import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class MapChooser extends JPanel{
	private JSplitPane splitPane;
	private ChooserView mapPanel;
	private JPanel playersPanel;
	private JButton cancel;
	private JButton continua;

	
	public MapChooser() {
		this.setPreferredSize(new Dimension( Settings.WINDOWWIDTH,Settings.WINDOWHEIGHT));
		this.setOpaque(true);
		//this.setBackground(new Color(255,185,0));
		this.setLayout(new BorderLayout());
		cancel = new JButton("Indietro");
		
		continua = new JButton("Gioca");
		continua.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			WindowsHandler.getWindowsHandler().setGamePanel(false, false, null);
			
		}
		});
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				WindowsHandler.getWindowsHandler().setMenu();
				
			}
			});
	
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		BoxLayout bl = new BoxLayout(buttons, BoxLayout.LINE_AXIS);
		buttons.setLayout(bl);
		
		
		cancel.setPreferredSize(new Dimension(200, 40));
		buttons.add(cancel);
		buttons.add(Box.createHorizontalGlue());
		continua.setPreferredSize(new Dimension(200, 40));
		buttons.add(continua);
		JPanel splitPane = new JPanel();
		splitPane.setLayout(new BoxLayout(splitPane, BoxLayout.Y_AXIS));
		//splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mapPanel = new ChooserView(Resources.maps,200);
		playersPanel = new JPanel();
		playersPanel.setOpaque(false);
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.CENTER);
		playersPanel.setLayout(fl);
		for(Image i : Resources.bombermanIcons) {
			JLabel label = new JLabel(new ImageIcon(i.getScaledInstance(140, 140, Image.SCALE_SMOOTH)));
			label.setOpaque(false);
			label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE , Color.DARK_GRAY));
			playersPanel.add(label);
		}
		for(Image i : Resources.bombermanIcons) {
			JLabel label = new JLabel(new ImageIcon(i.getScaledInstance(140, 140, Image.SCALE_SMOOTH)));
			label.setOpaque(false);
			label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE , Color.DARK_GRAY));
			playersPanel.add(label);
		}
		
		JViewport viewPort = new JViewport();
		viewPort.add(playersPanel);
		viewPort.setAlignmentY(CENTER_ALIGNMENT);
		viewPort.setOpaque(false);
		playersPanel.setOpaque(false);
		playersPanel.setAlignmentX(CENTER_ALIGNMENT);
		playersPanel.setAlignmentY(BOTTOM_ALIGNMENT);
		JScrollPane scrollPane = new JScrollPane(viewPort);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		//scrollPane.setPreferredSize(new Dimension(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT/4));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.DARK_GRAY , Color.LIGHT_GRAY) , "Bomberman"));
		splitPane.add(mapPanel);
		splitPane.add(scrollPane);
		//splitPane.setDividerSize(0);
		//splitPane.setDividerLocation(Settings.WINDOWHEIGHT/2);
		splitPane.setOpaque(false);
	//	splitPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED , Color.BLACK, Color.DARK_GRAY ));
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		JLabel title = new JLabel("PARTITA PERSONALIZZATA");
		//title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setAlignmentY(TOP_ALIGNMENT);
		title.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 30));
		title.setForeground(Color.BLACK);
		JPanel titlePanel = new JPanel();
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		this.add(titlePanel , BorderLayout.NORTH);
		this.add(splitPane , BorderLayout.CENTER);
		this.add(buttons , BorderLayout.SOUTH);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.createGameMenu.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		
	}
	
	
}
