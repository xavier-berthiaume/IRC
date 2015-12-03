package client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FontManager implements ItemListener, ChangeListener, ListSelectionListener, ActionListener{

	private FontWindow gui;
	private Font font;
	private Color color;
	private static final Color[] COLORS = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN, Color.WHITE};
	
	public FontManager(FontWindow gui){
		this.gui = gui;
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if(event.getStateChange() == ItemEvent.SELECTED)
			gui.getTestText().setForeground(FontManager.COLORS[gui.getBox().getSelectedIndex()]);
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
		if(source.equals(gui.getSizeSpinner())){
			gui.getTestText().setFont(new Font(gui.getFontName().getSelectedValue(),Font.PLAIN, (int) gui.getSizeSpinner().getValue()));
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		Object source = event.getSource();
		if(source.equals(gui.getFontName())){
			gui.getTestText().setFont(new Font(gui.getFontName().getSelectedValue(),Font.PLAIN, (int) gui.getSizeSpinner().getValue()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source.equals(gui.getCancel())){
			gui.hideWindow();
		}else if(source.equals(gui.getApply())){
			font = new Font(gui.getFontName().getSelectedValue(),Font.PLAIN, (int) gui.getSizeSpinner().getValue());
			color = FontManager.COLORS[gui.getBox().getSelectedIndex()];
			gui.hideWindow();
		}
		
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

}
