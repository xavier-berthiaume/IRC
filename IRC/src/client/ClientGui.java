package client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class ClientGui {
	
	//All the elements below are linked with the user info area
	private JPanel login;
	private JPanel loginPanel;
	private JLabel userLabel;
	private JTextField usernameInput;
	private JLabel hostLabel;
	private JTextField hostInput;
	private JLabel portLabel;
	private JTextField portInput;
	private JButton connect;
	private JButton logoutButton;
	
	//All the elements below are linked with the display area
	private JPanel displayArea;
	private DefaultCaret displayCaret;
	private JScrollPane displayScrollPane;
	private JTextArea display;
	
	//All the elements below are linked with the user area
	private JPanel userPanel;
	private DefaultCaret userCaret;
	private JScrollPane userInputScrollPane;
	private JTextArea userInput;
	private JButton send;
	
	//All the elements below are linked with the application
	private ClientConnect connection;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel userPanelDivider;
	
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
	 * @return JMenuBar The menu bar to be added to the frame.
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
	 * Create the login area as a JPanel to be placed in the mainPanel.
	 * @return JPanel The login area.
	 */
	private JPanel createLogin(){
		login = new JPanel();
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		//Create the are where the user enters the hosts information
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		userLabel = new JLabel("Username:\t");
		usernameInput = new JTextField(16);
		hostLabel = new JLabel("Host:\t");
		hostInput = new JTextField(16);
		portLabel = new JLabel("Port:\t");
		portInput = new JTextField(16);
		//Create a JPanel to fit two buttons, connect and logout, side by side.
		JPanel buttons = new JPanel();
		connect = new JButton("Connect!");
		connect.addActionListener(connection);
		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(connection);
		buttons.add(connect);
		buttons.add(logoutButton);
		//Add all elements to the loginPanel
		loginPanel.add(userLabel);
		loginPanel.add(usernameInput);
		loginPanel.add(hostLabel);
		loginPanel.add(hostInput);
		loginPanel.add(portLabel);
		loginPanel.add(portInput);
		loginPanel.add(buttons);
		login.add(loginPanel);
		login.add(this.createBufferZone(new Dimension(100, 250)));
		return login;
	}
	
	
	/**
	 * Create a buffer zone between elements of the desired size.
	 * @param size The size of the buffer zone.
	 */
	private JPanel createBufferZone(Dimension size){
		JPanel buffer = new JPanel();
		buffer.setPreferredSize(size);
		return buffer;
	}
	
	/**
	 * Creates the display area with a scrolling bar. The scrolling area with automatically follow new text because of the caret.
	 * @return JPanel The display area as a JPanel.
	 */
	private JPanel createDisplayArea(){
		//Create the area that displays text received
		displayArea = new JPanel();
		display = new JTextArea();
		display.setEditable(false);
		display.setLineWrap(true);
		displayScrollPane = new JScrollPane(display);
		displayScrollPane.setPreferredSize(new Dimension(375, 300));
		displayCaret = (DefaultCaret) display.getCaret();
		displayCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		displayArea.add(displayScrollPane);
		return displayArea;
	}
	
	/**
	 * Creates the user's area containing the input textbox and the send button.
	 * @return JPanel The user's area in JPanel form.
	 */
	private JPanel createUserArea(){
		//Create user panel and all user elements
		userPanel = new JPanel();
		userInput = new JTextArea();
		userInput.setLineWrap(true);
		userInputScrollPane = new JScrollPane(userInput);
		userInputScrollPane.setPreferredSize(new Dimension(300, 75));
		userCaret = (DefaultCaret) userInput.getCaret();
		userCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		send = new JButton("Send");
		send.setPreferredSize(new Dimension(75, 75));
		send.addActionListener(connection);
		//Add all elements in order to the user panel
		userPanel.add(userInputScrollPane);
		userPanel.add(send);
		return userPanel;
	}
	
	/**
	 * The main client window. Is where the client enters messages to be typed and sends them to the server. 
	 * The user also sees messages that were typed from this box.
	 */
	public void createGui() {
		connection = new ClientConnect(this);
		//Create the top level frame with no possibility of resizing it
		frame = new JFrame("Internet Relay Chat");
		frame.setSize(new Dimension(700, 700));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(this.createMenu());
		//Create main panel
		mainPanel = new JPanel(new BorderLayout());
		//Create the user panel (Where the user's display and text box is placed)
		userPanelDivider = new JPanel();
		userPanelDivider.setLayout(new BoxLayout(userPanelDivider, BoxLayout.Y_AXIS));
		//Add elements to the user panel
		userPanelDivider.add(this.createDisplayArea());
		userPanelDivider.add(this.createUserArea());
		
		//Add elements to the main panel
		mainPanel.add(this.createLogin(), BorderLayout.WEST);
		mainPanel.add((userPanelDivider), BorderLayout.CENTER);
		
		this.lockChat();
		frame.add(mainPanel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Getter for connect button.
	 * @return JButton The JButton that is clicked when the user has input their information
	 */
	public JButton getConnectButton() {
		return connect;
	}
	
	/**
	 * Getter for the send button.
	 * @return JButton The button that is clicked when the user has finished entering a message to the input box.
	 */
	public JButton getSendButton() {
		return send;
	}
	
	/**
	 * Getter for the logout button.
	 * @return JButton The logout button
	 */
	public JButton getLogoutButton(){
		return logoutButton;
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
	 * Getter for the text within the hostInput JTextField. If no information is set and the value is empty, the default becomes localhost.
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
	
	/**
	 * Getter for the text within the portInput JTextField. If no information is set and the value is empty, the default becomes 12345.
	 * @return int The port number to connect to
	 */
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

	/**
	 * Will allow the user to enter login information but will completely lock the chat from being edited, or have information set.
	 */
	public void lockChat(){
		userInput.setEditable(false);
		send.setEnabled(false);
		usernameInput.setEditable(true);
		hostInput.setEditable(true);
		portInput.setEditable(true);
		connect.setEnabled(true);
		logoutButton.setEnabled(false);
	}
	
	/**
	 * Will stop the user from entering login information, but will allow the user to type messages and send them.
	 */
	public void unlockChat(){
		userInput.setEditable(true);
		send.setEnabled(true);
		usernameInput.setEditable(false);
		hostInput.setEditable(false);
		portInput.setEditable(false);
		connect.setEnabled(false);
		logoutButton.setEnabled(true);
	}
	
}
