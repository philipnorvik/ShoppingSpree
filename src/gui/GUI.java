package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{

	private JPanel panel1 = new JPanel();			//panel1 har en JLabel (dvs bara text)
	private JPanel panel2 = new JPanel();			//panel2 har svars- eller kategoriknappar
	private JPanel panel3 = new JPanel();			//panel3 har okButton eller Avsluta
	
	private JLabel label1 = new JLabel("Väntar på att spel ska starta");
	
	
	
	private JButton bA1 = new JButton();
	private JButton bA2 = new JButton();
	private JButton bA3 = new JButton();
	private JButton bA4 = new JButton();
	private JButton[] svarsAlternativ = {bA1, bA2, bA3, bA4};	//Lista med alla svarsknappar
	
	
	private JButton bC1 = new JButton();
	private JButton bC2 = new JButton();
	private JButton bC3 = new JButton();
    private JButton bC4 = new JButton();

    private JButton[] kategoriAlternativ = {bC1, bC2, bC3,bC4};		//Lista med alla kategoriknappar
	
	
	
	private JButton okButton = new JButton("Tryck för att gå vidare");
	private JButton avsluta = new JButton("Avsluta");

	

	public GUI(){
		
		setLayout(new GridLayout(3, 1));
		
		label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 15));
		panel1.add(label1);
		
		add(panel1); panel1.setVisible(true); 
		add(panel2); panel2.setVisible(false);
		add(panel3); panel3.setVisible(false);
		
		int sizeHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.5);
		int sizeWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3);
		
		setSize(sizeWidth, sizeHeight);
		
		int locHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
		int locWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
		setLocation(locWidth, locHeight);
		setVisible(true);
	}
	


	public void waitingForOpponent() {
		label1.setText("Motståndare spelar");
		panel2.setVisible(false);
		panel3.setVisible(false);
		revalidate();
	}

	
	public void setupCategoryGUI() {
		label1.setText("Välj kategori");
		panel2.setVisible(true);
		panel3.setVisible(false);
		
		panel2.setLayout(new FlowLayout());
		
		for (JButton b : kategoriAlternativ) {
			b.setFont(new Font("Arial", Font.BOLD, 15));
			panel2.add(b);
		}
		
		revalidate();
	}
	


	public void removeCategoryGUI() {
		
		for (JButton b : kategoriAlternativ) {
			panel2.remove(b);
		}
		
		panel2.revalidate();
		panel2.repaint();
	}
	

	public void setupQuestionGUI() {
		
		panel2.setVisible(true);
		panel3.setVisible(false);
		panel3.add(okButton);

		panel2.setLayout(new GridLayout(2, 2));
		
		for (JButton b : svarsAlternativ) {
			b.setFont(new Font("Arial", Font.BOLD, 15));
			panel2.add(b);
		}
		
		revalidate();
	}

	public void removeQuestionGUI() {
		
		for (JButton b : svarsAlternativ) {
			panel2.remove(b);
		}
		
		panel2.revalidate();
		panel2.repaint();
	}
	

	public void setupScoreGUI(int i1, int i2){
		
		panel2.setVisible(false);
		panel3.remove(okButton);
		panel3.add(avsluta); 
		panel3.setVisible(true);
		
		label1.setText("Spelare ett: " + i1 + "  -  Spelare två: " + i2);
		revalidate();
		repaint();
	}


	
	public JPanel getPanel1() {
		return panel1;
	}

	public JPanel getPanel2() {
		return panel2;
	}

	public JPanel getPanel3() {
		return panel3;
	}

	public JLabel getLabel1() {
		return label1;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getbA1() {
		return bA1;
	}

	public JButton getbA2() {
		return bA2;
	}

	public JButton getbA3() {
		return bA3;
	}

	public JButton getbA4() {
		return bA4;
	}

	public JButton getbC1() {
	    return bC1;
	}

	public JButton getbC2() {
		return bC2;
	}

	public JButton getbC3() {
		return bC3;
	}
    public JButton getbC4() {
        return bC4;
    }
	public JButton getAvsluta() {
		return avsluta;
	}
	
	public JButton[] getKategoriAlternativ() {
		return kategoriAlternativ;
	}

	public JButton[] getSvarsAlternativ() {
		return svarsAlternativ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
