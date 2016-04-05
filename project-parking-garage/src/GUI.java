import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.TextField;
import javax.swing.JTextPane;
import java.awt.Label;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtPlaatsen;
	private JTextField txtVerdiepingen;
	private JTextField txtRijen;
	private JLabel lblRijen;
	private JLabel lblPlaatsen;
	private JLabel Verdiepingen;
	private JLabel lblError;
	private JTextField txtLicense;
	private JComboBox<String> cbPayment;
	private JButton btnBuy;
	private JTable tblReservations;
	private JLabel lblName;
	private JTextField txtName;
	
	private Simulator simulator;
	private ArrayList<Reservation> reservations;

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
		reservations = new ArrayList<Reservation>();//instantieer reservartion arraylist
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 678, 86);
		contentPane.add(panel);
		panel.setLayout(null);
		
		Verdiepingen = new JLabel("Verdiepingen");
		Verdiepingen.setBounds(10, 9, 86, 14);
		panel.add(Verdiepingen);
		
		txtRijen = new JTextField();
		txtRijen.setBounds(93, 6, 86, 20);
		panel.add(txtRijen);
		txtRijen.setColumns(10);
		
		lblRijen = new JLabel("Rijen");
		lblRijen.setBounds(189, 9, 46, 14);
		panel.add(lblRijen);
		
		txtVerdiepingen = new JTextField();
		txtVerdiepingen.setBounds(229, 6, 86, 20);
		panel.add(txtVerdiepingen);
		txtVerdiepingen.setColumns(10);
		
		lblPlaatsen = new JLabel("Plaatsen");
		lblPlaatsen.setBounds(325, 9, 58, 14);
		panel.add(lblPlaatsen);
		
		txtPlaatsen = new JTextField();
		txtPlaatsen.setBounds(393, 6, 86, 20);
		panel.add(txtPlaatsen);
		txtPlaatsen.setColumns(10);
		
		JButton btnKnopje = new JButton("Simulate");
		btnKnopje.setBounds(484, 5, 115, 23);
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
				
					simulator = new Simulator(verdiepingenGetal, rijenGetal, plaatsenGetal);	
					// simulator.run();//WERKT NIET -> WAAROM, Threading?
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
		
		lblError = new JLabel("");
		lblError.setBounds(10, 34, 589, 14);
		panel.add(lblError);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 149, 678, 406);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNaam = new JLabel("License plate");
		lblNaam.setBounds(10, 45, 95, 14);
		panel_1.add(lblNaam);
		
		txtLicense = new JTextField();
		txtLicense.setBounds(115, 42, 95, 20);
		panel_1.add(txtLicense);
		txtLicense.setColumns(10);
		
		JComboBox cbPayment = new JComboBox();
		cbPayment.addItem("Parking meter");//Item aan combobox toegevoegd
		cbPayment.addItem("Parking permit");//Item aan combobox toegevoegd
		cbPayment.addItem("Parking reservation");//Item aan combobox toegevoegd
		cbPayment.setBounds(328, 8, 119, 20);
		panel_1.add(cbPayment);
		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setBounds(230, 11, 80, 14);
		panel_1.add(lblPayment);
		
		btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String licensePlate = txtLicense.getText(); //Haal tekst op uit license-input
				String name = txtName.getText(); //Haal tekst op uit name-input
				String selectedPayment = cbPayment.getSelectedItem().toString(); //Haal tekst op uit payment-input
				
				//Controleer of name of licenseplate leeg is
				if(licensePlate == "" || name == "")
				{
					return;//Als name of licenseplate leeg is stop functie (zodat het onderstaande niet uitgevoerd wordt)
				}
				
				Customer customer = new WebCustomer(name, "hertet@sdf.nl");//Maak nieuwe customer aan met ingevoerde naam
				Reservation newReservation = new Reservation(licensePlate, selectedPayment, customer);//Maak nieuwe reservation aan met ingevoerde licenseplate en paymenttype EN zojuist aangemaakte customer (hierboven)
				
				boolean isBetaald = newReservation.pay(5.20);
				
				if(isBetaald == false)
				{
					return;
				}
				
				reservations.add(newReservation);//Voeg reservation toe aan de lijst met alle reservations maar dit wordt verder niet gebruikt 
				
				DefaultTableModel tblModel = (DefaultTableModel)tblReservations.getModel();//Haal DefaultTableModel op (bestaande kolommen op en bestaande rijen etc)
				tblModel.addRow(new String[]
						{
								newReservation.getCustomer().getName(),
								newReservation.getLicensePlate(),
								newReservation.getPaymentType()
						});//Stop nieuwe rij in DefaultTableModel
				
				txtLicense.setText(""); //Haal de tekst leeg nadat alles is gelukt
				txtName.setText(""); //Haal de tekst leeg nadat alles is gelukt
			}
		});
		btnBuy.setBounds(483, 7, 119, 23);
		panel_1.add(btnBuy);
		
		/*
		 * DefaultTableModel is iets van Java.Swing, het bevat de kolomnamen en de data (rijen) zelf
		 */
		
		String[] columnNames = {"Name", "License plates", "Payment"};//Definieer de kolomnamen
		DefaultTableModel tblModel = new DefaultTableModel(columnNames, 0);//Zet de kolommen in de DefaultTableModel
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 592, 297);
		panel_1.add(scrollPane);
		
		tblReservations = new JTable(tblModel);//Maak de tabel aan op het scherm en vul deze met DefaultTableModel (dus de data)
		scrollPane.setViewportView(tblReservations);
		
		lblName = new JLabel("Name");
		lblName.setBounds(10, 11, 95, 14);
		panel_1.add(lblName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(115, 8, 95, 20);
		panel_1.add(txtName);
		
		JLabel lblTicketsale = new JLabel("Ticketsale");
		lblTicketsale.setBounds(10, 124, 117, 14);
		contentPane.add(lblTicketsale);
	}
}
