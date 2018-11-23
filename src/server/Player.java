package server;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Player {

	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	protected int score = 0;
	
	private String answer;
	private String answerCheck;
	
	
	Player(Socket socket){
		
		connection = socket;
		
		try {
			out = new ObjectOutputStream(connection.getOutputStream()); out.flush();
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("Could not set up streams...");
		}
		
	}
	
	
	protected void sendObject(Object object) {
		
		try {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			System.out.println("Could not send object...");
		}
	}
	

	protected String retrieveAnswer() {
		answerCheck = answer;
		try {
			while(true) {
				answer = (String) in.readObject(); 
				if (!answer.equals(answerCheck)) {
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException...");
		} catch (IOException e) {
			System.out.println("No connection...");
			System.exit(0);
		}
		
		return answer; 
		
	}
	
	protected void incrementScore() {
		score++;
	}

	protected int getScore() {
		return score;
	}
	
}
