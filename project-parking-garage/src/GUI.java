import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.TextField;
import javax.swing.JTextPane;
import java.awt.Label;

public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtPlaatsen;
	private JTextField txtVerdiepingen;
	private JTextField txtRijen;
	private JLabel lblRijen;
	private JLabel lblPlaatsen;
	private JLabel Verdiepingen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		Verdiepingen = new JLabel("Verdiepingen");
		panel.add(Verdiepingen);
		
		txtRijen = new JTextField();
		panel.add(txtRijen);
		txtRijen.setColumns(10);
		
		lblRijen = new JLabel("Rijen");
		panel.add(lblRijen);
		
		txtVerdiepingen = new JTextField();
		panel.add(txtVerdiepingen);
		txtVerdiepingen.setColumns(10);
		
		lblPlaatsen = new JLabel("Plaatsen");
		panel.add(lblPlaatsen);
		
		txtPlaatsen = new JTextField();
		panel.add(txtPlaatsen);
		txtPlaatsen.setColumns(10);
		
		JButton btnKnopje = new JButton("Simulate");
		btnKnopje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Color wit = new Color(255, 255, 255);
				txtVerdiepingen.setBackground(wit);
				txtRijen.setBackground(wit);
				txtPlaatsen.setBackground(wit);
				lblError.setText("");
				
				// Input string Text
				String verdiepingen = txtVerdiepingen.getText();
				String rijen = txtRijen.getText();
				String plaatsen = txtPlaatsen.getText();
				
				try
				{
					int verdiepingenGetal = Integer.parseInt(verdiepingen);
					int rijenGetal = Integer.parseInt(rijen);
					int plaatsenGetal = Integer.parseInt(plaatsen);
				
					new Simulator(verdiepingenGetal, rijenGetal, plaatsenGetal);
				}
				catch(NumberFormatException ex)
				{
					lblError.setText("Make sure to use integers only");
					Color rood = new Color(232, 29, 78);
					txtVerdiepingen.setBackground(rood);
					txtRijen.setBackground(rood);
					txtPlaatsen.setBackground(rood);
				}
			}
		});
		
		panel.add(btnKnopje);
	}
}
