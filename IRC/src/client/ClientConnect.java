package client;

import java.net.*;
import java.awt.event.*;
import java.io.*;

public class ClientConnect implements ActionListener, Runnable, MouseListener, WindowFocusListener {

	private ClientGui gui;
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private int port = 12345;
	private String host;
	private String username;
	private boolean connected;
	private FontManager manager;
	
	public ClientConnect(ClientGui gui) {
		this.gui = gui;
	}
	
	/**
	 * Creates the socket connection and its reader/writer. Will also create and start the thread that does the reading operations.
	 * If no exceptions are caught, will append to the display a success message and return true.
	 * @return boolean The success or failure of the connections creation.
	 */
	private boolean createConnection() {
		try {	
			socket = new Socket(host, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			connected = true;
			Thread thread = new Thread(this);
			thread.start();
			this.gui.appendDisplay("Success connecting to " + host + "!\n");
			return true;
		} catch(IOException e1) {
			this.gui.appendDisplay("There was an issue when connecting to " + host + "\n");
			return false;
		}
	}
		
	/**
	 * Reads from the stream.
	 * @return String The string read from the stream.
	 */
	private String readFromStream() {
		try {
			return reader.readLine();
		} catch(IOException e1) {
			System.err.println("Exception caught when trying to read from the stream");
			return null;
		}
	}
	
	/**
	 * Write to the socket connection a string. After writing the string, write a newline and flush the streams buffer.
	 * @param toWrite The string to be written.
	 * @param writeUsername Decides if you add the username to the message or not.
	 */
	private void writeToStream(String toWrite, boolean writeUsername) {
		if(writeUsername) {
			try {
				writer.write(username + ": " + toWrite);
				writer.newLine();
				writer.flush();
			} catch(IOException e1) {
				System.err.println("Exception caught when trying to write to the stream");
			}
		} else {
			try{
				writer.write(toWrite);
				writer.newLine();
				writer.flush();
			}catch(IOException e1){
				System.err.println("Exception caught when trying to write to the stream");
			}
		}
		
	}
	
	/**
	 * Check if the message that is being sent is an empty message or not. Used to verify spam.
	 * @param input The message that is to be verified.
	 * @return boolean The value of wether the message is spam or not.
	 */
	private boolean checkMessage(String input) {
		if(input.equals(null) || input.equals(""))
			return false;
		return true;
	}
	
	/**
	 * Close the socket connection and all its readers/writers.
	 */
	private void closeConnection() {
		try {
			this.reader.close();
			this.writer.close();
			this.socket.close();
		} catch(IOException e1) {
			System.err.println("Exception caught when trying to close the socket connection");
		}
	}
	
	private void logout(){
		this.writeToStream("User: " + username + " is disconnecting from the server", false);
		connected = false;
		this.closeConnection();
		this.gui.lockChat();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		//Check if the source of the ActionEvent is the send button
		if(source.equals(gui.getSendButton())) {
			if(this.checkMessage(gui.getUserInput()))
				this.writeToStream(gui.getUserInput(), true);
			gui.setUserInput("");
		} else if(source.equals(gui.getConnectButton())) {
			host = gui.getHost();
			username = gui.getUsername();
			port = gui.getPort();
			if(this.createConnection()) {
				this.gui.unlockChat();
				this.writeToStream(username + " has joined the chat!", false);
			}
		} else if(source.equals(gui.getLogoutButton())) {
			this.logout();
		}
	}

	@Override
	public void run() {
		while(connected) { 
			gui.appendDisplay(this.readFromStream());
			gui.appendDisplay("\n");
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Object event = arg0.getSource();
		if(gui.getDayMode().equals(event)){
			gui.dayMode();
		}else if(gui.getNightMode().equals(event)){
			gui.nightMode();
		}else if(gui.getLogout().equals(event)){
			this.logout();
		}else if(gui.getFontEdit().equals(event)){
			this.manager = new FontWindow().getManager();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		try{
			if(!manager.equals(null)){
				gui.getDisplay().setFont(manager.getFont());
				gui.getInputArea().setFont(manager.getFont());
			}
		}catch(NullPointerException e1){
			
		}
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
