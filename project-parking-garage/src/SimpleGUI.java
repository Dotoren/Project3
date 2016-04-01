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

public class SimpleGUI extends JFrame {
	//second commit
	private JPanel contentPane;
	private JTextField txtPlaatsen;
	private JTextField txtVerdiepingen;
	private JTextField txtRijen;
	private JLabel lblError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGUI frame = new SimpleGUI();
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
	public SimpleGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		lblError = new JLabel("");
		panel.add(lblError);
		
		txtRijen = new JTextField();
		panel.add(txtRijen);
		txtRijen.setColumns(10);
		
		txtVerdiepingen = new JTextField();
		panel.add(txtVerdiepingen);
		txtVerdiepingen.setColumns(10);
		
		txtPlaatsen = new JTextField();
		panel.add(txtPlaatsen);
		txtPlaatsen.setColumns(10);
		
		JButton btnKnopje = new JButton("Klik");
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
					lblError.setText("Tekst kon niet worden omgezet naar een getal");
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
