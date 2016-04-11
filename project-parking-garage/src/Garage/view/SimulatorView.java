package Garage.view;

import Garage.logic.*;
import Garage.main.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

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
    private JPanel westPanel;
    private Container contentPane;
    private GridLayout westLayout;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.simulator = simulator;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();
        
        contentPane = getContentPane();
        createView(); //Own UI Stuff
       
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(westPanel, BorderLayout.WEST);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);

        updateView();
    }
    
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
    
    public void createView()
    {
    	westPanel = new JPanel();
    	westPanel.setVisible(true);

    	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

    	JPanel panel1 = new JPanel();
    	JPanel panel2 = new JPanel();
    	JPanel panel3 = new JPanel();
    	JPanel panel4 = new JPanel();
    	
    	StartEvent startEvent = new StartEvent();
    	StepOnceEvent stepOnceEvent = new StepOnceEvent();
    	StepHundredEvent stepHundredEvent = new StepHundredEvent();
    	PauseEvent pauseEvent = new PauseEvent();
    	
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
    	panel2.add(btnStepOnce);
    	panel3.add(btnStepHundred);
    	panel4.add(btnPause);
    	
    	westPanel.add(panel1);
    	westPanel.add(panel2);
    	westPanel.add(panel3);
    	westPanel.add(panel4);
    }
    
    public void updateView() {
        carParkView.updateView();
    }
    
     public int getNumberOfFloors() {
            return numberOfFloors;
        }
    
        public int getNumberOfRows() {
            return numberOfRows;
        }
    
        public int getNumberOfPlaces() {
            return numberOfPlaces;
        }
    
        public Car getCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            return cars[location.getFloor()][location.getRow()][location.getPlace()];
        }
    
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
    
        public Location getFirstFreeLocation() {
        	Random randomGenerator = new Random();
        	int randomFloor = randomGenerator.nextInt(getNumberOfFloors());
        	int randomRow = randomGenerator.nextInt(getNumberOfRows());
        	int randomPlace = randomGenerator.nextInt(getNumberOfPlaces());
            for (int floor = randomFloor; floor < getNumberOfFloors(); floor++) {
                for (int row = randomRow; row < getNumberOfRows(); row++) {
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

                        Color color = Color.white;
                        
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
                            else
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
