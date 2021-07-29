package gioco.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gioco.gui.GameInterface;
import gioco.model.Gioco;
import gioco.model.Player;

public class PlayerController extends KeyAdapter {
	private GameInterface panel;
	private Gioco gioco;
	private ArrayList<Integer> movements;

	// Client client;
	public PlayerController(GameInterface panel, Gioco gioco) {
		super();
		this.panel = panel;
		this.gioco = gioco;
		this.movements = new ArrayList<Integer>();
		movements.add(Player.IDLE_DOWN);
	}

	public GameInterface getPanel() {
		return panel;
	}

	public Gioco getGioco() {
		return gioco;
	}

	public void setGioco(Gioco gioco) {
		this.gioco = gioco;
	}

	/*
	 * @Override public void keyTyped(KeyEvent e) { //if (!game.isStarted())
	 * //return; if (!gioco.isMultiplayer()) { switch (e.getKeyCode()) { case
	 * KeyEvent.VK_LEFT: gioco.movePlayer(gioco.getPlayer1(), Settings.LEFT); break;
	 * case KeyEvent.VK_RIGHT: gioco.movePlayer(gioco.getPlayer1(), Settings.RIGHT);
	 * break; case KeyEvent.VK_UP: gioco.movePlayer(gioco.getPlayer1(),
	 * Settings.UP); break; case KeyEvent.VK_DOWN:
	 * gioco.movePlayer(gioco.getPlayer1(), Settings.DOWN); break; } } }
	 */

	public void keyReleased(KeyEvent e) {
		// if (!game.isStarted())
		// return;
		if (!gioco.isMultiplayer()) {
			if (!gioco.isGameOver()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SHIFT:
				case KeyEvent.VK_SPACE:
					gioco.addBomb("player1");
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					movements.remove((Object) Player.WALKING_LEFT);
					movements.set(0, Player.IDLE_LEFT);
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					movements.remove((Object) Player.WALKING_RIGHT);
					movements.set(0, Player.IDLE_RIGHT);
					break;
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					movements.remove((Object) Player.WALKING_UP);
					movements.set(0, Player.IDLE_UP);
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					movements.remove((Object) Player.WALKING_DOWN);
					movements.set(0, Player.IDLE_DOWN);
					break;

				}
				gioco.getPlayer1().setState(movements.get(movements.size() - 1));
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gioco.isGameOver())
			return;
		if (!gioco.isMultiplayer()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				gioco.getPlayer1().setState(Player.WALKING_LEFT);
				if (!movements.contains(Player.WALKING_LEFT))
					movements.add(Player.WALKING_LEFT);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				gioco.getPlayer1().setState(Player.WALKING_RIGHT);
				if (!movements.contains(Player.WALKING_RIGHT))
					movements.add(Player.WALKING_RIGHT);
				break;
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				gioco.getPlayer1().setState(Player.WALKING_UP);
				if (!movements.contains(Player.WALKING_UP))
					movements.add(Player.WALKING_UP);
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				gioco.getPlayer1().setState(Player.WALKING_DOWN);
				if (!movements.contains(Player.WALKING_DOWN))
					movements.add(Player.WALKING_DOWN);

				break;
			}

		} else {
			/*
			 * switch (e.getKeyCode()) { case KeyEvent.VK_LEFT:
			 * client.sendMessage(Settings.moveLeft()); break; case KeyEvent.VK_RIGHT:
			 * client.sendMessage(Settings.moveRight()); break; case KeyEvent.VK_UP:
			 * client.sendMessage(Settings.moveUp()); break; case KeyEvent.VK_DOWN:
			 * client.sendMessage(Settings.moveDown()); break; }
			 */

		}
	}

}
