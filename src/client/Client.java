
package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.JButton;

import database.Question;
import gui.GUI;

public class Client implements ActionListener {
	
	GUI gui = new GUI();
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private int port = 4200;
	private String ip = "127.0.0.1";
	private Socket connection;
	
	String fromServer;
	Question question;
	String[] categories;
	
	boolean ready = false;
	boolean inGame = true;
	
	private Object objFromServer;
	
	private Client() {
		
		setupConnection();
		setupWriter(); 
		setupReader(); 
		
		awaitingOpponent(); 
		InGame();
		
	}
	
	private void setupConnection() {
		try {
			connection = new Socket(ip, port);
		} catch (UnknownHostException e) {
			System.out.println("Could not find host...");
		} catch (IOException e) {
			System.out.println("No connection to server...");
		}
	}
	private void setupWriter() {
		
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not set up OutputStream...");
		}
	}
	
	private void setupReader() {
		
		try {
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("Could not set up InputStream...");
		}
	}
	
	private void awaitingOpponent() {
		while (true) {
			try { 
				fromServer = (String) in.readObject();
				if(fromServer.equals("player1")) {
					gui.setTitle("Spelare ett");
					break;
				}
				else if(fromServer.equals("player2")) {
					gui.setTitle("Spelare två");
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				break;
			}
		}
	}
	
	
	private void InGame() {

		while(inGame) {
			try {	
				objFromServer = in.readObject();
				if (objFromServer instanceof String) {				//String ("wait")
					fromServer = (String) objFromServer;
					if (fromServer.equals("wait")) {
						gui.waitingForOpponent();
					}
				}
				else if (objFromServer instanceof String[]) {			//Categories
					gui.setupCategoryGUI(); 
					setupCategoryChoices(); 
					awaitReady();
					gui.removeCategoryGUI();
				}
				else if (objFromServer instanceof Question) {			//Question
					gui.setupQuestionGUI(); 
					setupQuestion();
					awaitReady();
					gui.removeQuestionGUI();
				}
				else if (objFromServer instanceof int[]) {
					int[] scores =  (int[]) objFromServer;
					gui.setupScoreGUI(scores[0], scores[1]);
					gui.getAvsluta().addActionListener(this);
				}
				else if (objFromServer == null) {
					System.out.println("objFromServer is null...");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
		}
		
	}
	
	
	private void setupCategoryChoices() {
		
		categories = (String[]) objFromServer;
		// gör dynmaisk efter hur många categorier vi får in eller från properties?
		//läs properties och skapa antal kategorier

		gui.getbC1().setText(categories[0]);
		gui.getbC1().addActionListener(this);

		gui.getbC2().setText(categories[1]);
		gui.getbC2().addActionListener(this);

		gui.getbC3().setText(categories[2]);
		gui.getbC3().addActionListener(this);

		gui.getbC4().setText(categories[3]);
		gui.getbC4().addActionListener(this);
		ready = false;
	}
	
	
	private void setupQuestion() {
		
		question = (Question) objFromServer;
		
		gui.getOkButton().addActionListener(this);
		gui.getPanel3().setVisible(false);
		
		gui.getLabel1().setText(question.getQuestion());
		gui.getPanel1().revalidate();
		
		gui.getbA1().setText(question.getAnswerA()); 
		gui.getbA1().addActionListener(this);
		gui.getbA1().setBackground(null);
		
		gui.getbA2().setText(question.getAnswerB()); 
		gui.getbA2().addActionListener(this);
		gui.getbA2().setBackground(null);
		
		gui.getbA3().setText(question.getAnswerC()); 
		gui.getbA3().addActionListener(this);
		gui.getbA3().setBackground(null);
		
		gui.getbA4().setText(question.getAnswerD()); 
		gui.getbA4().addActionListener(this);
		gui.getbA4().setBackground(null);

		gui.revalidate();
		ready = false;
	}
	
	private void awaitReady() {
		while(!ready) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("Sleep interrupted for while(!ready)...");
			}
		}
		try {
			out.writeObject("ready");
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not send ready...");
		}
		ready = false;
	}
	
	
	public static void main(String[] args) {
		new Client();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// if source is a button do following
		if (e.getSource() == gui.getbC1() || e.getSource() == gui.getbC2() 	//button is category
			|| e.getSource() == gui.getbC3() || e.getSource() == gui.getbC4()) {

			for (JButton b : gui.getKategoriAlternativ()) {
				if (e.getSource() == b) {
					sendTextOfButton(b);
				}
			}
			ready = true;
		}
		else if(e.getSource() == gui.getbA1() || e.getSource() == gui.getbA2() 	//button is answer
				|| e.getSource() == gui.getbA3() || e.getSource() == gui.getbA4()) {
			
			for (JButton b : gui.getSvarsAlternativ()) {
				if (b.getText().equals(question.getCorrectAnswer())) {
					b.setBackground(Color.GREEN);
					gui.getPanel2().repaint();
				}
				if (e.getSource() == b) {
					if (!b.getText().equals(question.getCorrectAnswer())) {
						b.setBackground(Color.RED);
						gui.getPanel2().repaint();
					}
					sendTextOfButton(b);
					for (JButton bA : gui.getSvarsAlternativ()) {
						bA.removeActionListener(this);
					}
					gui.getPanel3().setVisible(true);
					gui.getPanel3().revalidate();
				}
			}
		}
		if (e.getSource() == gui.getOkButton()) {
			gui.getPanel3().setVisible(false);
			gui.getPanel3().revalidate();
			ready = true;
		}
		if (e.getSource() == gui.getAvsluta()) {
			System.exit(0);
		}
	}
	
	
	private void sendTextOfButton(JButton b) {
		
		try {
			out.writeObject(b.getText()); out.flush();
		} catch (IOException e1) {
			System.out.println("Could not send out text of button...");
		}
	}
}
