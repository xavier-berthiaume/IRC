package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;

public class FontWindow extends JFrame {

	private JPanel contentPane;
	private FontManager manager;
	private JList<String> fontList;
	private JTextArea txtrloremIpsumDolor;
	private JButton btnCancel;
	private JButton btnApply;
	private JComboBox<String> comboBox;
	private JSpinner size;

	/**
	 * Create the frame.
	 */
	public FontWindow() {
		createGUI();
	}
	
	private void createGUI(){
		manager = new FontManager(this);
		setResizable(false);
		setTitle("Edit Font");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JScrollPane fontScroll = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, fontScroll, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, fontScroll, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, fontScroll, 447, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, fontScroll, 210, SpringLayout.WEST, contentPane);
		contentPane.add(fontScroll);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontList = new JList<String>(fonts);
		fontList.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		fontList.addListSelectionListener(manager);
		fontScroll.setViewportView(fontList);
		
		JPanel buttonPanel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, buttonPanel, -36, SpringLayout.SOUTH, fontScroll);
		sl_contentPane.putConstraint(SpringLayout.WEST, buttonPanel, 6, SpringLayout.EAST, fontScroll);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, buttonPanel, 0, SpringLayout.SOUTH, fontScroll);
		sl_contentPane.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, contentPane);
		contentPane.add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(manager);
		buttonPanel.add(btnCancel);
		
		btnApply = new JButton("Apply");
		btnApply.addActionListener(manager);
		buttonPanel.add(btnApply);
		
		JPanel testTextDisplay = new JPanel();
		testTextDisplay.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Test Display Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.NORTH, testTextDisplay, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, testTextDisplay, 6, SpringLayout.EAST, fontScroll);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, testTextDisplay, 210, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, testTextDisplay, 264, SpringLayout.EAST, fontScroll);
		contentPane.add(testTextDisplay);
		testTextDisplay.setLayout(new BorderLayout(0, 0));
		
		JScrollPane textScrollPane = new JScrollPane();
		testTextDisplay.add(textScrollPane, BorderLayout.CENTER);
		
		txtrloremIpsumDolor = new JTextArea();
		txtrloremIpsumDolor.setWrapStyleWord(true);
		txtrloremIpsumDolor.setText("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"");
		txtrloremIpsumDolor.setLineWrap(true);
		textScrollPane.setViewportView(txtrloremIpsumDolor);
		
		JPanel optionsPanel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, optionsPanel, 6, SpringLayout.SOUTH, testTextDisplay);
		optionsPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.WEST, optionsPanel, 6, SpringLayout.EAST, fontScroll);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, optionsPanel, -6, SpringLayout.NORTH, buttonPanel);
		sl_contentPane.putConstraint(SpringLayout.EAST, optionsPanel, 264, SpringLayout.EAST, fontScroll);
		contentPane.add(optionsPanel);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
		
		JPanel colorPanel = new JPanel();
		optionsPanel.add(colorPanel);
		
		JLabel lblColor = new JLabel("Color:");
		colorPanel.add(lblColor);
		
		String[] displayedColors = {"Black", "Blue", "Red", "Green"};
		comboBox = new JComboBox<String>(displayedColors);
		comboBox.addItemListener(manager);
		colorPanel.add(comboBox);
		
		
		
		
		JPanel sizePanel = new JPanel();
		optionsPanel.add(sizePanel);
		
		JLabel lblSize = new JLabel("Size:");
		sizePanel.add(lblSize);
		
		size = new JSpinner();
		size.setModel(new SpinnerNumberModel(12, 10, 20, 1));
		size.addChangeListener(manager);
		sizePanel.add(size);
		
		setVisible(true);
	}
	
	public JList<String> getFontName(){
		return fontList;
	}
	
	public JTextArea getTestText(){
		return txtrloremIpsumDolor;
	}
	
	public JButton getApply(){
		return btnApply;
	}
	
	public JButton getCancel(){
		return btnCancel;
	}
	
	public JComboBox<String> getBox(){
		return comboBox;
	}
	
	public JSpinner getSizeSpinner(){
		return size;
	}
	
	public void hideWindow(){
		setEnabled(false);
		dispose();
	}
	
	public FontManager getManager(){
		return manager;
	}
}
