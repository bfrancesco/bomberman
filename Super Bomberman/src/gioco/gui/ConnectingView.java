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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.WindowsHandler;
import gioco.net.Client;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ConnectingView extends JPanel {
private Client client ;
	
//permette di visualizzare la schermata di connessione
public ConnectingView(Client client) {
	this.setLayout(new BorderLayout());
	this.setOpaque(true);
	this.setBackground(Color.BLACK);
	this.setPreferredSize(new Dimension( Settings.WINDOWWIDTH,Settings.WINDOWHEIGHT));
	ImageIcon loading = new ImageIcon(Resources.loading.getScaledInstance(Settings.iconWidthSelected, Settings.iconHeightSelected, Image.SCALE_DEFAULT));
	JLabel label= new JLabel(loading );
	label.setFont(Resources.myFont.deriveFont(40f));
	label.setIconTextGap(20);
	label.setText("Connecting . . .");
	label.setForeground(Color.WHITE);
	label.setAlignmentX(CENTER_ALIGNMENT);
	label.setAlignmentY(TOP_ALIGNMENT);
	this.add(label,BorderLayout.CENTER);
	JLabel exitText = new JLabel(new ImageIcon(Resources.key.getScaledInstance(Settings.iconWidth,Settings.iconHeight , Image.SCALE_SMOOTH)));
	exitText.setFont(Resources.myFont.deriveFont(17f));
	exitText.setIconTextGap(20);
	exitText.setText("Premi un tasto qualsiasi per annullare la connessione . . .");
	exitText.setForeground(Color.WHITE);
	exitText.setBorder(BorderFactory.createEmptyBorder(20,20, 20, 20));
	exitText.setAlignmentX(LEFT_ALIGNMENT);
	exitText.setAlignmentY(CENTER_ALIGNMENT);
	this.add(exitText, BorderLayout.SOUTH);
	this.addKeyListener(new KeyListener() {
		 
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		//interrompe il client per evitare che modifichi la grafica contemporaneamente, una volta interrotto , imposta il menu come principale
		@Override
		public void keyReleased(KeyEvent e) {
			client.interrupt();
			WindowsHandler.getWindowsHandler().setMenu();
			
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
	g.drawImage(Resources.wallpaperConnecting.getScaledInstance(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT, Image.SCALE_SMOOTH) , 0 , 0, null);
}
}
