package server;

import java.io.*;
import java.net.*;

public class ServerConnect extends Thread{

	private Socket clientSocket;
	private Server server;
	
	public ServerConnect(Server server, Socket socket){
		this.server = server;
		this.clientSocket = socket;
		this.start();
	}

	@Override
	public void run() {
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while(true){
				String message = reader.readLine();
				System.out.println("Sending '" + message + "' to server");
				server.broadcastMessage(message);
			}
		}catch(IOException e1){
			
		}finally{
			server.removeConnection(clientSocket);
		}
		
	}
	
}
