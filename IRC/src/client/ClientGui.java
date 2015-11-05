package client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
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
	private JLabel portLabel;
	private JTextField portInput;
	private JButton connect;
	
	//All the elements below are linked with the application
	private ClientConnect connection;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel userPanel;
	private JScrollPane userInputScrollPane;
	private JTextArea userInput;
	private JScrollPane displayScrollPane;
	private JTextArea display;
	private JButton send;
	
	//All elements below are linked with the JMenuBar
	private JMenuBar menu;
	private JMenu file;
	private JMenuItem logout;
	private JMenuItem save;
	private JMenu edit;
	private JMenuItem cut;
	private JMenuItem copy;
	private JMenuItem paste;
	private JMenu window;
	private JMenuItem font;
	private JSeparator daynightSplit;
	private ButtonGroup modeButton;
	private JRadioButtonMenuItem dayMode;
	private JRadioButtonMenuItem nightMode;
	
	/**
	 * Creates the JMenuBar for the frame.
	 * @return JMenuBar The menu bar to be added to the frame
	 */
	private JMenuBar createMenu(){
		menu = new JMenuBar();
		//File menu and submenus
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		logout = new JMenuItem("Logout");
		save = new JMenuItem("Save chat");
		//Edit menu and submenus
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		//Window menu and submenus
		window = new JMenu("Window");
		window.setMnemonic(KeyEvent.VK_W);
		font = new JMenuItem("Font");
		//Separate the radio buttons from the rest of the menu options.
		daynightSplit = new JSeparator();
		modeButton = new ButtonGroup();
		dayMode = new JRadioButtonMenuItem("Day mode");
		dayMode.setSelected(true);
		nightMode = new JRadioButtonMenuItem("Night mode");
		nightMode.setSelected(false);
		
		//Assemble all menu components together
		file.add(logout);
		file.add(save);
		
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		
		window.add(font);
		modeButton.add(dayMode);
		modeButton.add(nightMode);
		window.add(daynightSplit);
		window.add(dayMode);
		window.add(nightMode);
		
		menu.add(file);
		menu.add(edit);
		menu.add(window);
		
		return menu;
	}
	
	/**
	 * Create the initial UI box that will take in the user's name and the ip they wish to connect to.
	 * Will become deprecated in the following pushes.
	 * 
	 * @deprecated
	 */
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
	
	/**
	 * The main client window. Is where the client enters messages to be typed and sends them to the server. 
	 * The user also sees messages that were typed from this box.
	 */
	public void createGui() {
		connection = new ClientConnect(this);
		//Create the top level frame with Dimensions 300 x 400, with no possibility of resizing it
		frame = new JFrame("Internet Relay Chat");
		frame.setSize(new Dimension(600, 400));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(this.createMenu());
		//Create main panel
		mainPanel = new JPanel();
		//Create the are where the user enters the hosts information
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		userLabel = new JLabel("Username:\t");
		usernameInput = new JTextField(16);
		hostLabel = new JLabel("Host:\t");
		hostInput = new JTextField(16);
		portLabel = new JLabel("Port:\t");
		portInput = new JTextField(16);
		connect = new JButton("Connect!");
		connect.addActionListener(connection);
		//Add all elements to the loginPanel
		loginPanel.add(userLabel);
		loginPanel.add(usernameInput);
		loginPanel.add(hostLabel);
		loginPanel.add(hostInput);
		loginPanel.add(portLabel);
		loginPanel.add(portInput);
		loginPanel.add(connect);
		//Create the area that displays text received
		display = new JTextArea();
		display.setEditable(false);
		display.setLineWrap(true);
		displayScrollPane = new JScrollPane(display);
		displayScrollPane.setPreferredSize(new Dimension(290, 200));
		//Create user panel and all user elements
		userPanel = new JPanel();
		userInput = new JTextArea();
		userInput.setLineWrap(true);
		userInputScrollPane = new JScrollPane(userInput);
		userInputScrollPane.setPreferredSize(new Dimension(150, 200));
		send = new JButton("Send");
		send.setPreferredSize(new Dimension(100, 200));
		send.addActionListener(connection);
		//Add all elements in order to the user panel
		userPanel.add(userInputScrollPane);
		userPanel.add(send);
		//Add elements to the main panel
		mainPanel.add(loginPanel);
		mainPanel.add(displayScrollPane);
		mainPanel.add(userPanel);
		
		this.lockChat();
		frame.add(mainPanel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Hides the login gui, disposes the frame and then calls on createGui to make the ui that the client interacts with.
	 * @deprecated
	 */
	public void hideLogin() {
		loginFrame.setVisible(false);
		loginFrame.dispose();
		this.createGui();
	}

	/**
	 * Getter for connect button.
	 * 
	 * @return JButton The JButton that is clicked when the user has input their information
	 */
	public JButton getConnectButton() {
		return connect;
	}
	
	/**
	 * Getter for the send button.
	 * 
	 * @return JButton The button that is clicked when the user has finished entering a message to the input box.
	 */
	public JButton getSendButton() {
		return send;
	}
	
	/**
	 * Getter for the text within the usernameInput JTextField. If no information is set and the value is null, the default will be set to Anonymous. 
	 * @return String The value of usernameInput
	 */
	public String getUsername() {
		String username = usernameInput.getText();
		usernameInput.setText("");
		if(username.equals("")) {
			return "Anonymous";
		}
		return username;	
	}
	
	/**
	 * Getter for the text within the hostInput JTextField. If no information is set and the value is empty, then the default becomes localhost.
	 * @return String The value of hostInput
	 */
	public String getHost() {
		String host = hostInput.getText();
		hostInput.setText("");
		if(host.equals("")){
			return "localhost";
		}
		return host;
	}
	
	public int getPort(){
		String portText = portInput.getText();
		portInput.setText("");
		if(portText.equals("")){
			return 12345;
		}
		try{
			int port = Integer.parseInt(portText);
			if(port > 1024)
				return port;
			return 12345;
		}catch(IllegalFormatException e1){
			return 12345;
		}
	}
	
	/**
	 * Getter for the text within the userInput JTextField.
	 * @return String The value of userInput
	 */
	public String getUserInput() {
		if(userInput.getText().equals(null)){
			return "";
		}
		return userInput.getText();
	}
	
	/**
	 * Setter for the text in the userInput JTextField.
	 * @param input String that will replace the text in the userInput JTextField
	 */
	public void setUserInput(String input) {
		userInput.setText(input);
	}
	
	/**
	 * Will append to the display (the chat) the String parameter.
	 * @param input String that will append itself to the display JTextArea
	 */
	public void appendDisplay(String input){
		display.append(input);
	}

	public void lockChat(){
		userInput.setEditable(false);
		send.setEnabled(false);
		usernameInput.setEditable(true);
		hostInput.setEditable(true);
		portInput.setEditable(true);
		connect.setEnabled(true);
	}
	
	public void unlockChat(){
		userInput.setEditable(true);
		send.setEnabled(true);
		usernameInput.setEditable(false);
		hostInput.setEditable(false);
		portInput.setEditable(false);
		connect.setEnabled(false);
	}
	
}
