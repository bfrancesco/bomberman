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

import com.sun.jdi.event.EventQueue;
import com.sun.source.tree.LabeledStatementTree;

import gioco.WindowsHandler;
import gioco.controller.MapButtonController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ChooserView extends JPanel {
	private JScrollPane scrollPane;
	private JPanel choosePanel;
	private JSplitPane splitPane;
	private JLabel choosen;
	private ArrayList<ToChoose> toChoose;

	public ChooserView(Vector<Image> images, int dimImage) {
		JPanel completePanel = new JPanel();
		JPanel selected = new JPanel();
		selected.setOpaque(false);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbl.setConstraints(selected, gbc);
		selected.setLayout(gbl);
		selected.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY), "Selezionato"));
		choosen = new JLabel(new ImageIcon(images.get(0).getScaledInstance(300, 300, Image.SCALE_SMOOTH)));
		// choosen.setAlignmentY(CENTER_ALIGNMENT);
		// choosen.setAlignmentX(CENTER_ALIGNMENT);
		choosen.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK));
		choosen.setOpaque(false);
		completePanel.setPreferredSize(new Dimension(Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT * 5 / 8));
		completePanel.setLayout(new BorderLayout());
		completePanel.setOpaque(false);
		toChoose = new ArrayList<ToChoose>();
		choosePanel = new JPanel();
		choosePanel.setOpaque(false);

		WrapLayout layoutMaps = new WrapLayout();
		layoutMaps.setAlignment(FlowLayout.CENTER);
		choosePanel.setLayout(layoutMaps);
		JViewport viewPort = new JViewport();
		viewPort.setView(choosePanel);
		for (int i = 0; i < images.size(); ++i) {
			ToChoose button = new ToChoose();
			MapButtonController controller = new MapButtonController(button, toChoose, choosen, i + 1);
			button.setIcon(new ImageIcon(images.get(i).getScaledInstance(dimImage, dimImage, Image.SCALE_SMOOTH)));
			button.setOpaque(false);
			button.setController(controller);
			toChoose.add(button);
			choosePanel.add(button);
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
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY),
				"Campi di battaglia"));
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



	public void reposition() {
		for (int i = 0; i < choosePanel.getComponents().length; ++i) {
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
