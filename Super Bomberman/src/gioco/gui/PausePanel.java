package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import gioco.WindowsHandler;
import gioco.controller.CustomButtonListener;
import gioco.controller.PlayerController;
import gioco.controller.MuteSoundController;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//JPanel che viene usato come GlassPane , con background offuscato dall'alfa , permette di visualizzare il menu di pausa 
//composto in centro da un pannello che contiene CustomButton Standard , che permettono di riprendere , ricominciare(singleplayer) o abbandonare la partita
// ogni bottone ha un dei semplici listener che permettono di eseguire la scelta effettuata notificata la controller dalla grafica
//In SOUTH è presente un bottone per mutare l'audio
public class PausePanel extends JPanel {

	
	private static final long serialVersionUID = 1876323234460322142L;
	private MuteButton effects;
	
	public PausePanel(PlayerController controller) {
		this.setLayout(new BorderLayout());
		this.setFocusable(false);
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());		
		JLabel pausedLabel = new JLabel("PAUSA");
		pausedLabel.setFont(Resources.myFont.deriveFont(40f));
		pausedLabel.setHorizontalAlignment(JLabel.CENTER);
        pausedLabel.setForeground(Color.WHITE);
        pausedLabel.setOpaque(false);
        pausedLabel.setBackground(new Color(0 , 0 ,0, 150));
        JPanel pausedPanel = new JPanel() {
        	@Override
        	protected void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		g.drawImage(Resources.pauseWallpaper.getScaledInstance(this.getPreferredSize().width, this.getPreferredSize().height, Image.SCALE_SMOOTH) , 0 ,0 , null);
        		
        	}
        };
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        pausedPanel.setOpaque(true);
        pausedPanel.setLayout(new BorderLayout());
        
        pausedPanel.setPreferredSize(new Dimension(Settings.WINDOWWIDTH/3, Settings.WINDOWHEIGHT*5/12));
        int hgap = 10;
        int vgap  = 10 ;
        if(controller.isMultiplayer())
        	vgap = 20;
        buttons.setLayout(new GridLayout(0, 1, hgap, vgap));                
        CustomButton resume = new CustomButton(Resources.riprendi , CustomButton.STANDARD );
        CustomButton restart = new CustomButton(Resources.ricomincia,CustomButton.STANDARD);
        restart.setFocusable(false);
        CustomButton quit = new CustomButton(Resources.abbandona,CustomButton.STANDARD);
      
       resume.addMouseListener(new CustomButtonListener(resume));
       restart.addMouseListener(new CustomButtonListener(restart));
       quit.addMouseListener(new CustomButtonListener(quit));
        
        restart.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String message = "Sei sicuro di voler ricominciare la partita?";
        		if(WindowsHandler.getWindowsHandler().showConfirmationDialog("RICOMINCIA LA PARTITA", message)!=MyDialog.YES_OPTION)
        			return;
        		WindowsHandler.getWindowsHandler().showGlassPane(false);
        		WindowsHandler.getWindowsHandler().interruptGame();
        		WindowsHandler.getWindowsHandler().setGamePanel(false, false);
        		super.mouseClicked(e);
        	}
        	
        	
        });
        quit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		
        		CustomButton indietro = new CustomButton(Resources.indietroButton, CustomButton.STANDARD);      
        		indietro.addMouseListener(new MouseAdapter() {
        			@Override
        			public void mouseClicked(MouseEvent e) {
        				JOptionPane.getRootFrame().dispose();
        				super.mouseClicked(e);
        			}
        		});
        		String message = "Sei sicuro di voler uscire?";
        		Frame f = new Frame();
        		Point p = new Point(WindowsHandler.getWindowsHandler().getFrame().getLocationOnScreen().x + Settings.WINDOWWIDTH/2 ,WindowsHandler.getWindowsHandler().getFrame().getLocationOnScreen().y + Settings.WINDOWHEIGHT/2);
        		MyDialog dialog = new MyDialog(new Frame(), "ESCI DALLA PARTITA" ,  message , p );
        		int confirmed = dialog.showAndWait();      		
        		if(confirmed!=MyDialog.YES_OPTION)
        			return;
        		WindowsHandler.getWindowsHandler().showGlassPane(false);
        		WindowsHandler.getWindowsHandler().interruptGame();
        		if(!controller.isMultiplayer())
        			WindowsHandler.getWindowsHandler().setMapChooser();
        		else {
        			WindowsHandler.getWindowsHandler().setMenu();
        		}
        		
        		super.mouseClicked(e);
        	}
        });
        resume.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		controller.setPaused(false);
        		SoundsHandler.getSoundsHandler().resumeGameSoundtrack();
        		WindowsHandler.getWindowsHandler().showGlassPane(false);
        	}
        });
        buttons.add(pausedLabel);
        buttons.add(resume);
        if(!controller.isMultiplayer())
        	buttons.add(restart);
        buttons.add(quit);
        pausedPanel.add(buttons , BorderLayout.CENTER);
        pausedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK , 3));
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        center.add(pausedPanel);
        center.setOpaque(false);
        repaint();
        setOpaque(false);
		setBackground(new Color(0 , 0 , 0 , 175));
		
		JPanel icons = new JPanel();	
		icons.setLayout(new BoxLayout(icons , BoxLayout.X_AXIS));
		icons.setOpaque(false);
		effects= new MuteButton(SoundsHandler.getSoundsHandler().isEffectMute());	
		effects.addMouseListener(new MuteSoundController(effects));
		effects.setPreferredSize(new Dimension(Settings.iconButtonWidthSelected ,Settings.iconButtonHeightSelected+10  ));
		icons.add(Box.createHorizontalStrut(Settings.iconWidth)); 
		icons.add(Box.createHorizontalGlue());
		icons.add(effects);
		icons.add(Box.createHorizontalStrut(Settings.iconWidth));
		this.add(center , BorderLayout.CENTER);
		this.add(icons , BorderLayout.SOUTH);
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
}