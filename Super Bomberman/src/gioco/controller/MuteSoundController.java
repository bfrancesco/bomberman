package gioco.controller;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import gioco.gui.MuteButton;
import gioco.sound.SoundsHandler;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//Permette di togliere l'audio all'applicazione e cambia la sua icona per indicare se l'audio è chiuso oppure aperto
public class MuteSoundController extends MouseAdapter{
	private MuteButton button;
	public MuteSoundController(MuteButton button) {
		this.button = button;
		EmptyBorder border =(EmptyBorder) BorderFactory.createEmptyBorder(0, 0,10, 0);	
		this.button.setBorder(border);
		this.button.setFocusable(false);
	}
	
	//silenzia i suoni e  cambia l'icona
	@Override
	public void mouseReleased(MouseEvent e) {
		SoundsHandler.getSoundsHandler().changeEffectMute();
		SoundsHandler.getSoundsHandler().changeMusicMute();
		SoundsHandler.getSoundsHandler().resetGameSoundtrack();
		button.changeMute(SoundsHandler.getSoundsHandler().isEffectMute());
		super.mouseReleased(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		SoundsHandler.getSoundsHandler().enteredStandardButton();
		if(!SoundsHandler.getSoundsHandler().isEffectMute())
			button.setIcon(new ImageIcon(Resources.notMuteEffects.getScaledInstance(Settings.iconButtonWidthSelected, Settings.iconButtonHeightSelected, Image.SCALE_SMOOTH)));
		else
			button.setIcon(new ImageIcon(Resources.muteEffects.getScaledInstance(Settings.iconButtonWidthSelected, Settings.iconButtonHeightSelected, Image.SCALE_SMOOTH)));
		super.mouseEntered(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		SoundsHandler.getSoundsHandler().exitedStandardButton();
		if(!SoundsHandler.getSoundsHandler().isEffectMute())
			button.setIcon(new ImageIcon(Resources.notMuteEffects.getScaledInstance(Settings.iconButtonWidth, Settings.iconButtonHeight, Image.SCALE_SMOOTH)));
		else 
			button.setIcon(new ImageIcon(Resources.muteEffects.getScaledInstance(Settings.iconButtonWidth, Settings.iconButtonHeight, Image.SCALE_SMOOTH)));
		super.mouseExited(e);
	}
}
