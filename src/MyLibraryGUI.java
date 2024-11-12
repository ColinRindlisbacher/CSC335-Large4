/*
 * Authors: AJ Cronin and Colin Rindlisbacher
 * Usernames: ajcronin  | ckrindlisbacher
 * File: MyLibrary.java
 * Purpose: Serves as the view/UI for our Library.
 * Gets and validates user inputs and calls the 
 * related methods.
 * Encaspulation: Encaspulation is maintained by having all methods other
 * than main be set to private so that if this was used elsewhere, none of
 * our methods could be called and therefore have no access to the controller/model.
 */

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.text.View;

 public class MyLibraryGUI extends JFrame{
 
	private LibraryController controller;
	private JPanel panel;
	
	
	public MyLibraryGUI() {
		this.controller = new LibraryController();
		this.setTitle("CroRind Library");
		this.setSize(750,500);
		this.setUp();
	}
	
	private void setUp() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel welcomeLabel = new JLabel("Welcome to the CroRind Library!");

		panel.add(welcomeLabel);
		JLabel optionsLabel = new JLabel("Please choose from one of the below options:");
		panel.add(optionsLabel);
		this.add(panel);

		// ends the program when window is closed
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		this.setVisible(true);
	}
	
	public void start() {
		ArrayList<JRadioButton> JRadioButtons = new ArrayList<JRadioButton>();
		JRadioButtons.add(new JRadioButton("Search"));
		JRadioButtons.add(new JRadioButton("Add A Book"));
		JRadioButtons.add(new JRadioButton("Set To Read"));
		JRadioButtons.add(new JRadioButton("Rate A Book"));
		JRadioButtons.add(new JRadioButton("Get List of Books"));
		JRadioButtons.add(new JRadioButton("Get Suggestion"));
		JRadioButtons.add(new JRadioButton("Add Book File"));

		ButtonGroup G = new ButtonGroup();

		for(JRadioButton currButton: JRadioButtons) {
			panel.add(currButton);
			G.add(currButton);
		}

	}

	public static void main(String[] args) {
		MyLibraryGUI myLib = new MyLibraryGUI();
		myLib.start();
		myLib.setVisible(true);
	}
 
 }
 