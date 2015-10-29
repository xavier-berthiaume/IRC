package client;

import java.awt.*;
import javax.swing.*;

public class ClientGui {

	//All the elements below are linked with the login
	private JFrame loginFrame;
	private JPanel loginPanel;
	private JPanel usernamePanel;
	private JLabel userLabel;
	private JTextField usernameInput;
	private JPanel hostPanel;
	private JLabel hostLabel;
	private JTextField hostInput;
	private JButton connect;
	
	//All the elements below are linked with the application
	private ClientConnect connection;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel userPanel;
	private JTextArea userInput;
	private JTextArea display;
	private JButton send;
	
	public void createLogin() {
		connection = new ClientConnect(this);
		//Create a top level frame with no possibility of resizing
		loginFrame = new JFrame("Login");
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create the main panel
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		//Create the gui elements
		usernamePanel = new JPanel();
		userLabel = new JLabel("Username:\t");
		usernameInput = new JTextField(16);
		hostPanel = new JPanel();
		hostLabel = new JLabel("Host:\t");
		hostInput = new JTextField(16);
		connect = new JButton("connect");
		connect.addActionListener(connection);
		//Add all elements to their respective panels
		usernamePanel.add(userLabel);
		usernamePanel.add(usernameInput);
		hostPanel.add(hostLabel);
		hostPanel.add(hostInput);
		loginPanel.add(usernamePanel);
		loginPanel.add(hostPanel);
		loginPanel.add(connect);
		//Prepare the frame and display it
		loginFrame.add(loginPanel);
		loginFrame.pack();
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
	}
	
	public void createGui(){
		connection.createConnection();
		//Create the top level frame with Dimensions 300 x 400, with no possibility of resizing it.
		frame = new JFrame("Internet Relay Chat");
		frame.setSize(new Dimension(300, 400));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create main panel
		mainPanel = new JPanel();
		//Create the area that displays text received
		display = new JTextArea();
		display.setPreferredSize(new Dimension(290, 200));
		display.setEditable(false);
		display.setLineWrap(true);
		//Create user panel and all user elements
		userPanel = new JPanel();
		userInput = new JTextArea();
		userInput.setPreferredSize(new Dimension(150, 200));
		userInput.setLineWrap(true);
		send = new JButton("Send");
		send.setPreferredSize(new Dimension(100, 100));
		send.addActionListener(connection);
		//Add all elements in order to the user panel
		userPanel.add(userInput);
		userPanel.add(send);
		//Add elements to the main panel
		mainPanel.add(display);
		mainPanel.add(userPanel);
		
		frame.add(mainPanel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		Thread thread = new Thread(connection);
		thread.start();
	}
	
	public void hideLogin(){
		loginFrame.setVisible(false);
		loginFrame.dispose();
		this.createGui();
	}

	public JButton getConnectButton(){
		return connect;
	}
	
	public JButton getSendButton(){
		return send;
	}
	
	public String getUsername(){
		if(usernameInput.getText().equals(null)){
			return "";
		}
		return usernameInput.getText();
	}
	
	public String getHost(){
		if(hostInput.getText().equals(null)){
			return "localhost";
		}
		return hostInput.getText();
	}
	
	public String getUserInput(){
		if(userInput.getText().equals(null)){
			return "";
		}
		return userInput.getText();
	}
	
	public void setUserInput(String input){
		userInput.setText(input);
	}
	
	public void appendDisplay(String input){
		display.append(input);
	}

}
