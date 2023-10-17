package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
import javax.swing.border.TitledBorder;

import gioco.WindowsHandler;
import gioco.controller.BombermanButtonController;
import gioco.controller.NavigationButtonController;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//Panel che mostra la personalizzazione della partita
//ha un border layout
//in Center è presente uno split pane con un ChooserView nella parte superiore
//in quella inferiore troviamo un panel che contiere le immagini di tutti i personaggi selezionabili nell'ordine descritto all'interno di Settings
//ad ogni immagine viene associato un colore nell'ordine principale dei colori all'interno della classe Setting
//in South sono presenti i NavigationButton che permettono di tornare al menu principale o di inizare la partita
//
public class CreateGamePanel extends JPanel{
	private static final long serialVersionUID = -5069828666606797601L;
	
	private ChooserView mapPanel;
	private JPanel playersPanel;
	private NavigationButton cancel;
	private NavigationButton continua;
	private ArrayList<ToChoose> bombermanColors;
	
	public CreateGamePanel() {
		this.setPreferredSize(new Dimension( Settings.WINDOWWIDTH,Settings.WINDOWHEIGHT));
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		cancel = new NavigationButton("Indietro", Resources.indietro , Resources.indietroSelected);

		continua  =new NavigationButton("Gioca", Resources.gioca , Resources.giocaSelected);
		continua.setHorizontalTextPosition(JLabel.LEFT);
		continua.addMouseListener(new NavigationButtonController(continua));
		continua.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			WindowsHandler.getWindowsHandler().setGamePanel(false, false);			
		}});
		cancel.addMouseListener(new NavigationButtonController(cancel));
		cancel.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				WindowsHandler.getWindowsHandler().setMenu();
			}
		});
			
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		BoxLayout bl = new BoxLayout(buttons , BoxLayout.X_AXIS);
		buttons.setLayout(bl);
		cancel.setFont(Resources.myFont.deriveFont(30f));
		buttons.add(Box.createHorizontalStrut(Settings.iconWidth));
		buttons.add(cancel);
		buttons.add(Box.createHorizontalGlue());
		buttons.add(continua);
		buttons.add(Box.createHorizontalStrut(Settings.iconWidth));
		JPanel splitPane = new JPanel();
		splitPane.setLayout(new BoxLayout(splitPane, BoxLayout.Y_AXIS));
		mapPanel = new ChooserView(Resources.maps,Settings.mapsImages , Settings.imageShow);
		playersPanel = new JPanel();
		playersPanel.setOpaque(false);
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.CENTER);
		playersPanel.setLayout(fl);
		bombermanColors = new ArrayList<ToChoose>();
		for(int i = 0 ; i < Resources.bombermanIcons.size();++i) {
			ToChoose button = new ToChoose();
			BombermanButtonController controller = new BombermanButtonController(button , bombermanColors , Settings.WHITE+i );
			button.setIcon(new ImageIcon(Resources.bombermanIcons.get(i).getScaledInstance(Settings.bobmermenImages, Settings.bobmermenImages, Image.SCALE_SMOOTH)));
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
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		TitledBorder bombermen = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.DARK_GRAY , Color.LIGHT_GRAY) , "Bombermen");
		bombermen.setTitleColor(Color.BLACK);
		bombermen.setTitleFont(Resources.myFont);
		scrollPane.setBorder(bombermen);
		splitPane.add(mapPanel);
		splitPane.add(scrollPane);
		splitPane.setOpaque(false);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		JLabel title = new JLabel("PARTITA PERSONALIZZATA");
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setAlignmentY(TOP_ALIGNMENT);
		title.setFont(Resources.myFont.deriveFont(40f));
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
	
	public void reposition() {
		mapPanel.reposition();
	}
	
	public ChooserView getMapPanel() {
		return mapPanel;
	}
	
	

	public void setMapPanel(ChooserView mapPanel) {
		this.mapPanel = mapPanel;
	}



	public JLabel getCancel() {
		return cancel;
	}


	public JLabel getContinua() {
		return continua;
	}

	//reset della schermata e delle scelte di mappa e giocatore, riporta il panel allo stato che aveva alla creazione
	public void reset() {
		Settings.selectedbomberman = Settings.WHITE;
		Settings.selectedMap = 1;
		mapPanel.reset();
		for(int i = 0; i<bombermanColors.size();++i) {
			if (Settings.selectedbomberman == Settings.WHITE+i) {
				bombermanColors.get(i).getController().mouseClicked(new MouseEvent(bombermanColors.get(i), MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));
				return;
			}
		}
		
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
