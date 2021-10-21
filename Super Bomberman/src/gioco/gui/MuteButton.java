package gioco.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//il muteButton è un particolare tipo di bottone che differisce dal Custom perchè ha due imamgini intercambiabili fra loro che dipendono dall'audio del gioco 
//se il gioco è mutato ci sarà l'icona di muto , altrimenti icona di non muto
public class MuteButton extends JButton{
	

	public MuteButton( boolean muted ) {	
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		reset();
	}
	
	//cambia l'icon del bottone 
	public void changeMute(boolean muted) {
		if(!muted)
			this.setIcon(new ImageIcon(Resources.notMuteEffects.getScaledInstance(Settings.iconButtonWidthSelected, Settings.iconButtonHeightSelected, Image.SCALE_SMOOTH)));
		else {
			this.setIcon(new ImageIcon(Resources.muteEffects.getScaledInstance(Settings.iconButtonWidthSelected, Settings.iconButtonHeightSelected, Image.SCALE_SMOOTH)));
		}
	}
	
	//rimette come immagini quella che rispetta lo stato attuale
	public void reset() {
		if(SoundsHandler.getSoundsHandler().isEffectMute())
			this.setIcon(new ImageIcon(Resources.muteEffects.getScaledInstance(Settings.iconButtonWidth ,Settings.iconButtonHeight, Image.SCALE_SMOOTH)));
		else this.setIcon(new ImageIcon(Resources.notMuteEffects.getScaledInstance(Settings.iconButtonWidth, Settings.iconButtonHeight, Image.SCALE_SMOOTH)));
	}
	
	
	
}
