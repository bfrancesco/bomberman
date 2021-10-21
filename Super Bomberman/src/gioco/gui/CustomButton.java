package gioco.gui;

import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import gioco.utilities.Resources;
import gioco.utilities.Settings;

//I bottoni utilizzati nel gioco
//permettono di avere una imamgine come sfondo , con il controller si ingrandiscono se l'evento specificato è avenuto
//possono essere di tre tipi : icone , Standard o Menu , svolgono tutti la stessa funzione , ma cambia la dimensione di visualizzazione
//non sono opachi nè focusabili , perciò permettono all'elemento che ha il focus di non perderlo 
public class CustomButton extends JButton{
	public static int MENU = 0;
	public static int STANDARD = 1;
	public static int ICON = 2;
	private Image image;
	private int w;
	private int h;
	private int wSelected;
	private int hSelected;
	private int type;
	public CustomButton(Image image , int type ) {
		this.type = type;
		if(this.type == MENU) {
			this.w = Settings.wMenuButton ; 
		this.h = Settings.hMenuButton;
		this.wSelected = Settings.wMenuButtonSelected;
		this.hSelected =Settings.hMenuButtonSelected;
		}
		else if(this.type == ICON) {
			this.w = Settings.iconButtonWidth; 
			this.h = Settings.iconButtonHeight;
			this.wSelected = Settings.iconButtonWidthSelected;
			this.hSelected =Settings.iconButtonHeightSelected;
		}
		else {
			this.w = Settings.wCustomButton  ; 
			this.h = Settings.hCustomButton;
			this.wSelected = Settings.wCustomButtonSelected;
			this.hSelected =Settings.hCustomButtonSelected;
		}
		this.image = image ;
		setContentAreaFilled(false);
		setFocusable(false);
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setIcon(new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
	}
	//l'immagine aumenta se true , altrimenti ritorna quella standard
	public void select(boolean selected) {
		if(selected) {
			setIcon(new ImageIcon(image.getScaledInstance(wSelected, hSelected, Image.SCALE_SMOOTH)));			
		}
		else {
			setIcon(new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH)));
			
	
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getwSelected() {
		return wSelected;
	}

	public void setwSelected(int wSelected) {
		this.wSelected = wSelected;
	}

	public int gethSelected() {
		return hSelected;
	}

	public void sethSelected(int hSelected) {
		this.hSelected = hSelected;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
