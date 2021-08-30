package gioco.net;

import java.io.PrintWriter;

public class Sender implements Runnable {
	private PrintWriter writer;
	String message;
	public Sender(PrintWriter p) {
		// TODO Auto-generated constructor stub
		writer = p;
	}
	
	
	
	public PrintWriter getWriter() {
		return writer;
	}



	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	public void write(String message) {
		if(writer!=null)
			writer.println(message);
	}


	public void close() {
		writer.close();
	}
	
	@Override
	public void run() {	
		write(message);
//		super.run();
		
		
	}
	
}
