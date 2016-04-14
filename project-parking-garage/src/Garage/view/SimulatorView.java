package Garage.view;

import Garage.logic.*;
import Garage.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
*
* @author		Sytske Anema (345010)
* 				Remy Buitenkamp (340987)
* 				Cordell Stirling (323643)
* 				Nicole Mulder (350591)
* @date			13-04-2016				
* @version		1.0
*/

	public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private Simulator simulator;
    private JButton btnStart;
    private JButton btnStepOnce;
    private JButton btnStepHundred;
    private JButton btnPause;
    private JButton btnWeekDay;
    private JButton btnWeekend;
    private JButton btnSunday;
    private JPanel northPanel;
    private Container contentPane;
    private JPanel southPanel;
    private ImageIcon image1;
    private JLabel ImageLabel;
    private JPanel ImagePanel;
    private JLabel Label1;
    private JLabel Label2;
    private JLabel Label3;
    private JLabel Label4;
    private JLabel Label5;
    private JLabel Label6;
    private JLabel Label7;
    private JLabel Label8;
    private JLabel Label9;
    

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.simulator = simulator;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

        carParkView = new CarParkView();
        contentPane = getContentPane();
        
        northView(); // Display the northView (buttons)
        southView(); // Display the southView (data)

        // add contentPanes to panels in different Borderlayout directions.
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);


        pack();
        
        // sets the simulatorView to visible
        setVisible(true);

        // updates the data that is visible in the simulatorView
        updateView();
    }

    
    // Here you determine the action of a button after it is pressed, we used ActionListeners for this.
    
    public class StartEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Thread queryThread = new Thread(); {
				simulator.start();
            }
		}
    }

    public class StepOnceEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			simulator.tick();
		}
    }

    public class StepHundredEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Thread queryThread = new Thread(); {
				simulator.tickHundred();
			}
		}
    }

    public class PauseEvent implements ActionListener {
    	public void actionPerformed(ActionEvent e) {

    		simulator.pause();
    	}
    }
    
    public class WeekDayEvent implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		simulator.setDay(0);
    		simulator.setHour(8);
    	}
    }
    
    public class WeekendEvent implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		simulator.setDay(4);
    		simulator.setHour(19);
    	}
    }

    public class SundayEvent implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		simulator.setDay(6);
    		simulator.setHour(4);
    	}
    }


    // This method determines how the northView looks like, it contains panels and buttons.
    
    public void northView()
    {
    	northPanel = new JPanel();
    	northPanel.setVisible(true);

    	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

    	JPanel panel5 = new JPanel();
    	JPanel panel6 = new JPanel();
    	JPanel panel7 = new JPanel();
    	JPanel panel1 = new JPanel();
    	JPanel panel2 = new JPanel();
    	JPanel panel3 = new JPanel();
    	JPanel panel4 = new JPanel();

    	StartEvent startEvent = new StartEvent();
    	StepOnceEvent stepOnceEvent = new StepOnceEvent();
    	StepHundredEvent stepHundredEvent = new StepHundredEvent();
    	PauseEvent pauseEvent = new PauseEvent();
    	WeekDayEvent weekDayEvent = new WeekDayEvent();
    	WeekendEvent weekendEvent = new WeekendEvent();
    	SundayEvent sundayEvent = new SundayEvent();

    	btnWeekDay = new JButton();
    	btnWeekDay.setText("WeekDay");
    	btnWeekDay.setPreferredSize(new Dimension (125,30));
    	btnWeekDay.addActionListener(weekDayEvent);
    	
    	btnWeekend = new JButton();
    	btnWeekend.setText("Weekend");
    	btnWeekend.setPreferredSize(new Dimension (125,30));
    	btnWeekend.addActionListener(weekendEvent);
    	
    	btnSunday = new JButton();
    	btnSunday.setText("Sunday");
    	btnSunday.setPreferredSize(new Dimension (125,30));
    	btnSunday.addActionListener(sundayEvent);
    	
    	btnStart = new JButton();
    	btnStart.setText("Start");
    	btnStart.setPreferredSize(new Dimension(125, 30));
    	btnStart.addActionListener(startEvent);

    	btnStepOnce = new JButton();
    	btnStepOnce.setText("Step once");
    	btnStepOnce.setPreferredSize(new Dimension(125, 30));
    	btnStepOnce.addActionListener(stepOnceEvent);

    	btnStepHundred = new JButton();
    	btnStepHundred.setText("Step hundred");
    	btnStepHundred.setPreferredSize(new Dimension(125, 30));
    	btnStepHundred.addActionListener(stepHundredEvent);

    	btnPause = new JButton();
    	btnPause.setText("Pause");
    	btnPause.setPreferredSize(new Dimension(125, 30));
    	btnPause.addActionListener(pauseEvent);

    	panel1.add(btnStart);
    	panel5.add(btnWeekDay);
    	panel6.add(btnWeekend);
    	panel7.add(btnSunday);
    	panel2.add(btnStepOnce);
    	panel3.add(btnStepHundred);
    	panel4.add(btnPause);

    	northPanel.add(panel1);
    	northPanel.add(panel5);
    	northPanel.add(panel6);
    	northPanel.add(panel7);
    	northPanel.add(panel2);
    	northPanel.add(panel3);
    	northPanel.add(panel4);
    }

    // This method determines how the southView looks like, it contains panels, images and data of the parking garage.
    public void southView() {

        southPanel = new JPanel();
        getContentPane().add(southPanel);
        
    	//ImageLabel = new JLabel("");
    	//ImageLabel.setIcon(new ImageIcon("C:\\Users\\Remy\\git\\Project3\\project-parking-garage\\Images\\Legenda.png"));
    	//southPanel.add(ImageLabel);
        
        image1 = new ImageIcon(getClass().getResource("Legenda.png"));
        ImageLabel = new JLabel(image1);
        southPanel.add(ImageLabel);
         
        ImagePanel = new JPanel();
        ImagePanel.setPreferredSize(new Dimension(100,100));
        southPanel.add(ImagePanel);
        
        Box box = Box.createVerticalBox();
        southPanel.add(box);
                
        Label8 = new JLabel();
        box.add(Label8);
        Label9 = new JLabel();
        box.add(Label9);
        Label1 = new JLabel();
        box.add(Label1);
        Label4 = new JLabel();
        box.add(Label4);
        Label2 = new JLabel();
        box.add(Label2);
        Label3 = new JLabel();
        box.add(Label3);
        Label5 = new JLabel();
        box.add(Label5);
        Label6 = new JLabel();
        box.add(Label6);
        Label7 = new JLabel();
        box.add(Label7);
        

    }

    // The updateView method is used to update the data displayed in the simulator.
    
    public void updateView() {
        carParkView.updateView();
        Label1.setText("Total amount of cars: " + simulator.getTotalNumberOfCars());
        Label2.setText("Amount of PassHolders: " + simulator.getTotalParkPassCars());
        Label3.setText("Amount of Reservations: " + simulator.getTotalReservationCars());
        Label4.setText("Amount of normal customers: " + simulator.getTotalNormalCars());
        Label5.setText("Amount of cars in entrance queue: " + simulator.getEntranceQueueNumber());
        Label6.setText("Amount of cars in payment queue: " + simulator.getPaymentQueueNumber());
        Label7.setText("Amount of cars in exit queue: " + simulator.getExitQueueNumber());
        Label8.setText("Day: " + simulator.getDayType());
        Label9.setText("Time: " + simulator.getHour() + ":" + simulator.getMinute());
    }

    	// method to return the amount of floors of the garage
    	public int getNumberOfFloors() {
            return numberOfFloors;
        }
     	
    	// method to return the amount of rows of the garage
        public int getNumberOfRows() {
            return numberOfRows;
        }

        // method to return the amount of places of the garage
        public int getNumberOfPlaces() {
            return numberOfPlaces;
        }
        
        public Car getCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            return cars[location.getFloor()][location.getRow()][location.getPlace()];
        }


        // --------------------------------------
        // Should be in the controller / model
        public boolean setCarAt(Location location, Car car) {
            if (!locationIsValid(location)) {
                return false;
            }
            Car oldCar = getCarAt(location);
            if (oldCar == null) {
                cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
                car.setLocation(location);
                return true;
            }
            return false;
        }

        // --------------------------------------
        // Should be in the controller / model
        public Car removeCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            Car car = getCarAt(location);
            if (car == null) {
                return null;
            }
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            return car;
        }

        // --------------------------------------
        // Should be in the controller / model
        public Location getRandomFreeLocation() {
        	Random randomGenerator = new Random();
        	int randomFloor = randomGenerator.nextInt(getNumberOfFloors());
        	int randomRow = randomGenerator.nextInt(getNumberOfRows());
        	int randomPlace = randomGenerator.nextInt(getNumberOfPlaces());
            for (int floor = randomFloor; floor < getNumberOfFloors(); floor++) {
                for (int row = randomRow; row < getNumberOfRows() - 1; row++) {
                    for (int place = randomPlace; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            return location;
                        }
                    }
                }
            }
            return null;
        }

        // --------------------------------------
        // Should be in the controller / model
        public Location getReservedFreeLocation() {
        	Random randomGenerator = new Random();
        	int randomFloor = randomGenerator.nextInt(getNumberOfFloors());
        	int randomPlace = randomGenerator.nextInt(getNumberOfPlaces());
        	for (int floor = randomFloor; floor < getNumberOfFloors(); floor++) {
        		for (int row = getNumberOfRows() - 1; row<getNumberOfRows(); row++) {
        			for (int place = randomPlace; place < getNumberOfPlaces(); place++) {
        				Location location = new Location(floor, row, place);
        				if (getCarAt(location) == null){
        					return location;
        				}
        			}
        		}
        	}
        	return null;
        }

        // --------------------------------------
        // Should be in the controller / model
        public Car getFirstLeavingCar() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                            return car;
                        }
                    }
                }
            }
            return null;
        }

        // --------------------------------------
        // Should be in the controller / model
        public void tick() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        if (car != null) {
                            car.tick();
                        }
                    }
                }
            }
        }

        // --------------------------------------
        // Should be in the controller / model
        private boolean locationIsValid(Location location) {
            int floor = location.getFloor();
            int row = location.getRow();
            int place = location.getPlace();
            if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
                return false;
            }
            return true;
        }

    private class CarParkView extends JPanel {

        private Dimension size;
        private Image carParkImage;
        private boolean isBlack;

        public boolean getIsBlack() {
            return isBlack;
        }

        public void setIsBlack(boolean isBlack) {
            this.isBlack = isBlack;
        }

        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }

        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }

        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }

        // --------------------------------------
        // Should be in the controller / model?
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);

                        //Color color = car == null ? Color.white : car.getIsReserved() == true ? Color.orange : car.getIsPass() == true ? Color.green : Color.red;

                        if (row == getNumberOfRows()-1){
                        	setIsBlack(true);
                        }
                        else{
                        	setIsBlack(false);
                        }

                        Color color = getIsBlack() == true ? Color.black : Color.white;

                        if(car != null)
                        {
                            if(car.getIsPass())
                            {
                            	color = color.green;
                            }
                            else if(car.getIsReserved())
                            {
                            	color = color.orange;
                            }
                            else if (car.getIsPaying())
                            {
                            	color = color.red;
                            }
                        }

                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }

        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
