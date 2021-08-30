package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.net.Client;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ConnectingView extends JPanel {
private Client client ;
	
public ConnectingView(Client client) {
	this.setLayout(new BorderLayout());
	this.setOpaque(true);
	this.setBackground(Color.BLACK);
	this.setPreferredSize(new Dimension( Settings.WINDOWWIDTH,Settings.WINDOWHEIGHT));
	ImageIcon loading = new ImageIcon(Resources.loading.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
	JLabel label= new JLabel(loading );
	label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
	label.setIconTextGap(20);
	label.setText("Connecting...");
	label.setForeground(Color.WHITE);
	label.setPreferredSize(new Dimension(100 , 100));
	label.setAlignmentX(CENTER_ALIGNMENT);
	label.setAlignmentY(TOP_ALIGNMENT);
	this.add(label,BorderLayout.CENTER);
	JLabel exitText = new JLabel(new ImageIcon(Resources.key.getScaledInstance(30,30 , Image.SCALE_SMOOTH)));
	exitText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
	exitText.setIconTextGap(20);
	exitText.setText("Premi un tasto qualsiasi per annullare la connessione...");
	exitText.setForeground(Color.WHITE);
	exitText.setPreferredSize(new Dimension(100 , 100));
	exitText.setAlignmentX(LEFT_ALIGNMENT);
	exitText.setAlignmentY(CENTER_ALIGNMENT);
	this.add(exitText, BorderLayout.SOUTH);
	this.addKeyListener(new KeyListener() {
		 
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			WindowsHandler.getWindowsHandler().setMenu();
			client.disconnect();
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	});
}



public Client getClient() {
	return client;
}



public void setClient(Client client) {
	this.client = client;
}



@Override
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(Resources.wallpaperConnecting.getScaledInstance(330, 300, Image.SCALE_SMOOTH) , 600 , 475 , null);
}
}
