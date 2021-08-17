package gioco.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	private JButton multiplayer;
	private JButton allenamento;
	
	public Menu(int w , int h ) {
		setPreferredSize(new Dimension( w,h));
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		multiplayer = new JButton("Multiplayer");
		multiplayer.setPreferredSize(new Dimension(400, 100));
		multiplayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler windowh = WindowsHandler.getWindowsHandler();
				WindowsHandler.setMultiplayer(true);
				WindowsHandler.setGamePanel();
				super.mouseClicked(e);
			}
		
		});
		allenamento = new JButton("Allenamento");
		allenamento.setPreferredSize(new Dimension(400, 100));
		allenamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				WindowsHandler.getWindowsHandler().setMultiplayer(false);
				WindowsHandler.getWindowsHandler().setGamePanel();
				super.mouseClicked(e);
			}
		
		});
		multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
		allenamento.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(multiplayer , layout);
		add(allenamento , layout);
		setBackground(new Color(180, 0, 40));
	}
}
