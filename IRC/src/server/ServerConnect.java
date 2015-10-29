package server;

import java.io.*;
import java.util.*;
import java.net.*;

public class ServerConnect implements Runnable{

	private Socket clientSocket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private static final int PORT = 12345;
	private static ThreadGroup group;
	
	public ServerConnect(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	private void createConnections() {
		try{
			writer = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		}catch(IOException e1){
			System.err.println("Exception caught when trying to fetch InputStreamReader and OutputStreamWriter");
		}
	}
	
	private void writeToStream(String input) {
		try{
			this.writer.write(input);
			this.writer.newLine();
			this.writer.flush();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to write to the socket connection");
		}
	}
	
	private String readFromStream(){
		try{
			return this.reader.readLine();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to read from the stream");
			return null;
		}
	}
	
	private void closeConnections(){
		try{
			this.writer.close();
			this.reader.close();
			this.clientSocket.close();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to close the socket connection");
		}
	}
	
	private static void closeServer(ServerSocket socket){
		try {
			socket.close();
		} catch (IOException e1) {
			System.err.println("Exception caught when trying to close the server Socket");
		}
	}
	
	@Override
	public void run() {
		String input;
		this.createConnections();
		while(!(input = this.readFromStream()).equals(null)){
			System.out.println(input);
			this.writeToStream(input);
		}
		this.closeConnections();
	}
	
	public static void main(String[] args) throws IOException {
		//Create the Server Side Socket that will accept incoming connections
		ServerSocket serverSocket = new ServerSocket(ServerConnect.PORT);
		group = new ThreadGroup("Client Threads");
		boolean isRunning = true;
		while(isRunning){
			//Accept incoming request
			Socket socket = serverSocket.accept();
			//Deal with the incoming request
			new Thread(group, new ServerConnect(socket)).start();
		}
		//Close the server
		ServerConnect.closeServer(serverSocket);
	}
}
