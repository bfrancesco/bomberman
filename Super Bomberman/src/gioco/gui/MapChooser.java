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
import java.util.ArrayList;

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

import gioco.WindowsHandler;
import gioco.controller.BombermanButtonController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class MapChooser extends JPanel{
	private JSplitPane splitPane;
	private ChooserView mapPanel;
	private JPanel playersPanel;
	private JButton cancel;
	private JButton continua;
	private ArrayList<ToChoose> bombermanColors;
	
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
		FlowLayout bl = new FlowLayout();
		buttons.setLayout(bl);
		
		
		cancel.setPreferredSize(new Dimension(200, 40));
		cancel.setOpaque(true);
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
		bombermanColors = new ArrayList<ToChoose>();
		for(int i = 0 ; i < Resources.bombermanIcons.size();++i) {
			ToChoose button = new ToChoose();
			BombermanButtonController controller = new BombermanButtonController(button , bombermanColors , Settings.WHITE+i );
			button.setIcon(new ImageIcon(Resources.bombermanIcons.get(i).getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
			button.setOpaque(false);
			button.setController(controller);
			bombermanColors.add(button);
			playersPanel.add(button);
			if(i+Settings.WHITE == Settings.selectedbomberman) {
				controller.mouseClicked(new MouseEvent(button ,MouseEvent.MOUSE_CLICKED , 100 , 0 , 0 , 1 ,1 , false));
			}
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
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.DARK_GRAY , Color.LIGHT_GRAY) , "Bombermen"));
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
		mapPanel.reposition();
		repaint();
	}
	
	
	
	public ChooserView getMapPanel() {
		return mapPanel;
	}
	
	

	public void setMapPanel(ChooserView mapPanel) {
		this.mapPanel = mapPanel;
	}



	public JButton getCancel() {
		return cancel;
	}



	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}



	public JButton getContinua() {
		return continua;
	}



	public void setContinua(JButton continua) {
		this.continua = continua;
	}



	public ArrayList<ToChoose> getBombermanColors() {
		return bombermanColors;
	}



	public void setBombermanColors(ArrayList<ToChoose> bombermanColors) {
		this.bombermanColors = bombermanColors;
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Resources.createGameMenu.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		
	}
	
	
}
