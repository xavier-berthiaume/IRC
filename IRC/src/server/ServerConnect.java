package server;

import java.io.*;
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
	
	
	/**
	 * Using the accepted client socket, will create a BufferedWriter and BufferedReader object to allow IO operations with the socket's stream.
	 */
	private void createConnections() {
		try{
			writer = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		}catch(IOException e1){
			System.err.println("Exception caught when trying to fetch InputStreamReader and OutputStreamWriter");
		}
	}
	
	
	/**
	 * Will write to a single stream the String parameter. After writing, will add a new line and flush the BufferedWriter object's stream.
	 * 
	 * @param input is the message to be written to the stream.
	 */
	private void writeToStream(String input) {
		try{
			this.writer.write(input);
			this.writer.newLine();
			this.writer.flush();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to write to the socket connection");
		}
	}
	
	/**
	 * Will read from the InputStream and return a String object of the message.
	 * 
	 * @return String object of what is inside the InputStream.
	 */
	private String readFromStream(){
		try{
			return this.reader.readLine();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to read from the stream");
			return null;
		}
	}
	
	/**
	 * Will close the BufferedWriter, BufferedReader, then the socket in their respective orders.
	 */
	private void closeConnections(){
		try{
			this.writer.close();
			this.reader.close();
			this.clientSocket.close();
		}catch(IOException e1){
			System.err.println("Exception caught when trying to close the socket connection");
		}
	}
	
	/**
	 * Should be used exclusively to shut down the server-side application. It will close the socket from recieving any connections.
	 * 
	 * @param socket The server socket that hosts all the client sockets.
	 */
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
