package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;

import gioco.utilities.Resources;
import gioco.utilities.Settings;

/*
 * Ogni infoSection contiene un titolo , Juna textarea  e un pannello che la contiene
 * il border layout permette di porre il titolo in NORTH
 * in CENTER vi è un JPanel con BoxLaout verticale che contiene l'immagine eventuale di riferimento e la JTextArea con il testo da visualizzare
 * il titolo e testo da visualizzare sono stati letti da input da un file di testo
 */
public class InfoSection extends JPanel {
	private String title;
	private JTextArea text;
	private JPanel center;

	public InfoSection(File file, Image image) {
		center = new JPanel();
		this.setLayout(new BorderLayout(Settings.iconButtonHeight/4,Settings.iconButtonHeight/4));
		this.setBorder(BorderFactory.createEmptyBorder(0 ,3,0,0));
		this.setOpaque(false );	
		center.setOpaque(false);
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		text = new JTextArea();
		JLabel imageLabel = new JLabel();
		if(image != null)
			imageLabel.setIcon(new ImageIcon(image.getScaledInstance(Settings.xInfoImage, Settings.yInfoImage, Image.SCALE_SMOOTH)));
		imageLabel.setAlignmentX(CENTER_ALIGNMENT);
		imageLabel.setAlignmentY(TOP_ALIGNMENT);
		imageLabel.setOpaque(false);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel titleLabel = new JLabel();
		parseFile(file);
		titleLabel.setText(title);
		titleLabel.setForeground(Color.black);
		titleLabel.setFont(Resources.myFont.deriveFont(40f));
		JPanel textPanel = new JPanel();
		textPanel.setOpaque(false);
		textPanel.setLayout(new BoxLayout(textPanel , BoxLayout.Y_AXIS));
		textPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.black , Color.darkGray));
		text.setOpaque(true);
		text.setBackground(new Color(0, 0, 0, 160));
		text.setForeground(Color.white);
		text.setFocusable(false);
		text.setEditable(false);
		text.setWrapStyleWord(true);
		text.setFont(Resources.myFont.deriveFont(18f));
		text.setLineWrap(true);
		text.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		text.getCaret().deinstall(text);
		textPanel.add(text);
		titleLabel.setOpaque(false);
		//titleLabel.setBackground(new Color(0, 0, 0, 170));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.setFocusable(false);
		center.add(imageLabel);
		center.add(Box.createVerticalStrut(Settings.iconButtonHeight/4));
		center.add(textPanel);
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
	}

	//lettura del file di testo 
	//si legge il titolo da inserire nella titleLabel
	//si legge il testo da inserire nella JtestArea
	public void parseFile(File file) {
		BufferedReader reader ;
		try {
			reader = new BufferedReader(new FileReader(file));
			String str = reader.readLine();
			title = str;
			while(reader.ready()) {
				text.append("\n");
				str = reader.readLine(); 
				String[] elements = str.split(" ");
				for(String s : elements) {
					text.append(s+" ");				
				}
			}
			text.append("\n");
		} catch (IOException e2) {
			
		}
	}
	
}
