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


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.View;

 public class MyLibrary extends JFrame{
 
	private LibraryController controller;
	
	
	public MyLibrary() {
		this.controller = new LibraryController();
		this.setTitle("CroRind Library");
		this.setSize(500,500);
		this.setUp();
	}
	
	private void setUp() {
		JPanel panel = new JPanel();
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
	
	public static void main(String[] args) {
		MyLibrary myLib = new MyLibrary();
	}
 
 }
 