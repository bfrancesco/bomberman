package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.sun.jdi.event.EventQueue;
import com.sun.source.tree.LabeledStatementTree;

import gioco.WindowsHandler;
import gioco.controller.MapButtonController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;
//Il pannello permette di avere una serie di elementi in una lista , visualizzati poi in uno scrollpane nella parte destra di uno splitpane 
//l'immagine scelta è visualizzata sulla sinistra dello splitpane
// i bottoni sono di tipo ToChoose che permettono di essere selezionato uno alla volta
public class ChooserView extends JPanel {
	private JScrollPane scrollPane;
	private JPanel choosePanel;
	private JLabel choosen;
	private ArrayList<ToChoose> toChoose;
	
	//creazione del pannello
	public ChooserView(Vector<Image> images, int dimImage , int selection) {
		JPanel completePanel = new JPanel();
		JPanel selected = new JPanel();
		selected.setFont(Resources.myFont);
		selected.setOpaque(false);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(selected, gbc);
		selected.setLayout(gbl);
		TitledBorder selezionatoBorder = BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY), " Selezionato ");
		selezionatoBorder.setTitleFont(Resources.myFont);
		selezionatoBorder.setTitleColor(Color.black);
		selected.setBorder(selezionatoBorder);
		choosen = new JLabel(new ImageIcon(images.get(0).getScaledInstance(selection, selection, Image.SCALE_SMOOTH)));
		choosen.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK));
		choosen.setOpaque(false);
		completePanel.setPreferredSize(new Dimension(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT * 5 / 8));
		completePanel.setLayout(new BorderLayout());
		completePanel.setOpaque(false);
		toChoose = new ArrayList<ToChoose>();
		choosePanel = new JPanel();
		choosePanel.setOpaque(false);

		BoxLayout layoutMaps= new BoxLayout(choosePanel, BoxLayout.Y_AXIS);
		choosePanel.setLayout(layoutMaps);
		JViewport viewPort = new JViewport();
		viewPort.setView(choosePanel);
		for (int i = 0; i < images.size(); ++i) {
			ToChoose button = new ToChoose();
			MapButtonController controller = new MapButtonController(button, toChoose, choosen, i + 1);
			button.setIcon(new ImageIcon(images.get(i).getScaledInstance(dimImage, dimImage, Image.SCALE_SMOOTH)));
			button.setOpaque(false);
			button.setAlignmentX(CENTER_ALIGNMENT);
			button.setController(controller);
			toChoose.add(button);
			choosePanel.add(button);
			if( i < images.size()-1)
			choosePanel.add(Box.createVerticalStrut(2));
			if (Settings.selectedMap == i + 1) {
				controller.mouseClicked(new MouseEvent(button, MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));
			}

		}
		viewPort.setOpaque(false);
		scrollPane = new JScrollPane(viewPort);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		TitledBorder campoBorder = BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY), " Campi di battaglia ");
		campoBorder.setTitleColor(Color.black);
		campoBorder.setTitleFont(Resources.myFont);
		scrollPane.setBorder(campoBorder);
		// completePanel.add(scrollPane, BorderLayout.CENTER);

		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplit.setBorder(BorderFactory.createEmptyBorder());
		horizontalSplit.setDividerSize(0);
		horizontalSplit.setOpaque(false);
		selected.add(choosen);
		horizontalSplit.setLeftComponent(selected);
		horizontalSplit.setRightComponent(scrollPane);
		horizontalSplit.setDividerLocation(Settings.WINDOWWIDTH / 2);
		completePanel.add(horizontalSplit, BorderLayout.CENTER);
		this.setOpaque(false);
		this.add(completePanel);
	}
	
	

	public ArrayList<ToChoose> getToChoose() {
		return toChoose;
	}



	public void setToChoose(ArrayList<ToChoose> toChoose) {
		this.toChoose = toChoose;
	}

	//riposiziona il pannello al primo elemento disponibile
	public void reset() {
		scrollPane.getVerticalScrollBar().setValue(0);
		for(int i = 0; i<toChoose.size();++i) {
			if (Settings.selectedMap == i + 1) {
				toChoose.get(i).getController().mouseClicked(new MouseEvent(toChoose.get(i), MouseEvent.MOUSE_CLICKED, 100, 0, 0, 1, 1, false));
				return;
			}
		}
	}
	
	//Il metodo permette di riposizionare lo scrollpane nella posizione corretta una volta che è stata effettuata una scelta
	//il corretto funzionamento deve essere applicato alla fine , quando la posizione dell'elemento tc è stata determinata nel layout
	public void reposition() {
		for (int i = 0; i < choosePanel.getComponents().length; ++i) {
			if(choosePanel.getComponents()[i].getClass() != ToChoose.class) {
				i++;
			}
			ToChoose tc = (ToChoose) choosePanel.getComponents()[i];
			if (tc.isChoosen()) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						scrollPane.getVerticalScrollBar().setValue(tc.getLocation().y);
					}
				});
			}
		}
	}
}
