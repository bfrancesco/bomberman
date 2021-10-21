package gioco.gui;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gioco.utilities.Resources;
import gioco.utilities.Settings;

//Estende una JLabel per avere la possibilità di avere testo e immagine , ma il comportamento è simile ad un bottone
// reagisce al mouse cambiando il colore quando selezionato
//è usato tipicamente quando ci è la possibilità di tornare indietro o andare avanti
public class NavigationButton extends JLabel {
	private Image exitedImage;
	private Image enteredImage;
	private String text;
	public NavigationButton(String text , Image exitedImage , Image enteredImage) {
		super(text);
		this.text = text;
		this.exitedImage = exitedImage;
		this.enteredImage = enteredImage;
		setForeground(Color.black);
		setIcon(new ImageIcon(exitedImage.getScaledInstance(Settings.iconWidth, Settings.iconHeight, Image.SCALE_SMOOTH)));
		setForeground(Color.BLACK);
		setOpaque(false);
		setFont(Resources.myFont.deriveFont(30f));
	}
	
	public void setEntered() {
		setIcon(new ImageIcon(enteredImage.getScaledInstance(Settings.iconWidth, Settings.iconHeight, Image.SCALE_SMOOTH)));
		setForeground(Color.white);
	}
	
	public void setExited() {
		setIcon(new ImageIcon(exitedImage.getScaledInstance(Settings.iconWidth, Settings.iconHeight, Image.SCALE_SMOOTH)));
		setForeground(Color.black);
	}

	public Image getExitedImage() {
		return exitedImage;
	}

	public void setExitedImage(Image exitedImage) {
		this.exitedImage = exitedImage;
	}

	public Image getEnteredImage() {
		return enteredImage;
	}

	public void setEnteredImage(Image enteredImage) {
		this.enteredImage = enteredImage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	
	
}
