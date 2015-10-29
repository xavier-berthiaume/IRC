package client;

import java.net.*;
import java.awt.event.*;
import java.io.*;

public class ClientConnect implements ActionListener, Runnable{

	private ClientGui gui;
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private final int PORT = 12345;
	private String host;
	private String username;
	
	public ClientConnect(ClientGui gui){
		this.gui = gui;
	}
	
	public void createConnection(){
		try{	
			socket = new Socket(host, PORT);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e1){
			System.err.println("Exception caught when creating the socket connection");
			System.exit(-1);
		}
	}
		
	public String readFromStream(){
		try{
			return reader.readLine();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to read from the stream");
			return null;
		}
	}
	
	public void writeToStream(String toWrite){
		try{
			writer.write(username + ": " + toWrite);
			writer.newLine();
			writer.flush();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to write to the stream");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		//Check if the source of the ActionEvent is the send button
		if(source.equals(gui.getSendButton())){
			this.writeToStream(gui.getUserInput());
			gui.setUserInput("");
		}else if(source.equals(gui.getConnectButton())){
			host = gui.getHost();
			username = gui.getUsername();
			gui.hideLogin();
		}
	}

	@Override
	public void run() {
		while(true){
			gui.appendDisplay(this.readFromStream());
			gui.appendDisplay("\n");
		}
	}
	
}
