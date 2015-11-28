package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	private ServerSocket serverSocket;
	private Hashtable<Socket, BufferedWriter> outputStreams;
	
	public Server(int port) throws IOException{
		listen(port);
	}
	
	/**
	 * Creates the ServerSocket object that will listen for connections on the desired port number.
	 * @param port
	 * @throws IOException
	 */
	private void listen(int port) throws IOException{
		//Make the socket that listens for connections.
		serverSocket = new ServerSocket(port);
		System.out.println("Listening for connections on port " + port);
		//Make the Hashtable to store every socket with their corresponding streams
		outputStreams = new Hashtable<Socket, BufferedWriter>();
		while(true){
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connection from " + clientSocket);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			outputStreams.put(clientSocket, writer);
			new ServerConnect(this, clientSocket);
		}
	}
	
	/**
	 * Remove the client that was connected from the hashtable, along with their corresponding BufferedWriter
	 * @param client The socket that has disconnected
	 */
	public void removeConnection(Socket client){
		synchronized(outputStreams){
			//System.out.println(client + " is leaving the chat");
			//this.broadcastMessage(client + " is leaving the chat");
			outputStreams.remove(client);
			try{
				client.close();
			}catch(IOException e1){
				System.err.println("Connection impossible to close");
			}
		}
	}
	
	private Enumeration<BufferedWriter> getBufferedWriters(){
		return outputStreams.elements();
	}
	
	public void broadcastMessage(String message){
		synchronized(outputStreams){
			for(Enumeration<?> e = getBufferedWriters(); e.hasMoreElements();){
				BufferedWriter writer = (BufferedWriter) e.nextElement();
				Server.writeToStream(writer, message);
			}
		}
	}
	
	private static void writeToStream(BufferedWriter writer, String input){
		try{
			writer.write(input);
			writer.newLine();
			writer.flush();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to write a message to a stream");
		}
	}
	
	private static int getPort(String[] args){
		try{
			int port = Integer.parseInt(args[0]);
			if(port > 1024)
				return port;
			return -1;
		}catch(NumberFormatException e1){
			return -1;
		}
	}
	
	public static void main(String[] args){
		int port = Server.getPort(args);
		if(port == -1){
			System.err.println("Usage: java Server <port> ; <port> must be above 1024");
		}
		try {
			new Server(port);
		} catch (IOException e) {
			System.err.println("Exception caught when creating the server.");
			System.exit(-1);
		}
	}
}
