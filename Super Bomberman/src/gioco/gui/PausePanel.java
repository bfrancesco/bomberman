package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.WindowsHandler;
import gioco.controller.PlayerController;
import gioco.controller.ResumeListener;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class PausePanel extends JPanel {

	
	private static final long serialVersionUID = 1876323234460322142L;
	
	public PausePanel(PlayerController controller) {
		this.setLayout(new GridBagLayout());		
		JLabel pausedLabel = new JLabel("PAUSA");
		pausedLabel.setHorizontalAlignment(JLabel.CENTER);
        pausedLabel.setForeground(Color.WHITE);
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
        
        pausedPanel.setPreferredSize(new Dimension(Settings.WINDOWHEIGHT/3, Settings.WINDOWHEIGHT*5/12));
        buttons.setLayout(new GridLayout(0, 1, 10, 10));
        buttons.add(pausedLabel);
        JButton resume = new JButton("RIPRENDI");
        JButton restart = new JButton("RICOMINCIA");
        JButton quit = new JButton("ABBANDONA");
        restart.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		WindowsHandler.getWindowsHandler().showGlassPane(false);
        		WindowsHandler.getWindowsHandler().interruptGame();
        		WindowsHandler.getWindowsHandler().setGamePanel(false, false, null);
        		super.mouseClicked(e);
        	}
        });
        quit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		WindowsHandler.getWindowsHandler().showGlassPane(false);
        		if(!controller.isMultiplayer())
        			WindowsHandler.getWindowsHandler().setMapChooser();
        		else 
        			WindowsHandler.getWindowsHandler().setMenu();
        		super.mouseClicked(e);
        	}
        });
        resume.addMouseListener(new ResumeListener(controller));
        buttons.add(resume);
        if(!controller.isMultiplayer())
        	buttons.add(restart);
        buttons.add(quit);
        pausedPanel.add(buttons , BorderLayout.CENTER);
        pausedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK , 3));
        buttons.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(pausedPanel);
        repaint();
        setOpaque(false);
		setBackground(new Color(0 , 0 , 0 , 175));
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
		super.paintComponent(g);
	}
}