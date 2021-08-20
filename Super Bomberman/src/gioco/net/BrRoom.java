package gioco.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class BrRoom extends Room{

	public BrRoom(Vector<Socket> players, String map1) throws IOException {
		super(players, map1);
	}

}
