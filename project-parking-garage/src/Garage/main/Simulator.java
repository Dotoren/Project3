package Garage.main;

import Garage.logic.*;
import Garage.view.*;

import javax.swing.*;

import Garage.logic.AdHocCar;
import Garage.logic.Car;
import Garage.logic.CarQueue;
import Garage.logic.Location;
import Garage.view.SimulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;



public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30, this);
    }
    
    public Simulator(int floors, int rows, int places) {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(floors, rows, places, this);
    }

    public void start() {
    	
    	new Thread() {
    		
    		public void run() {
    			for (int i = 0; i < 1000; i++) {
    	            tick();
    	        }
    		}
    	}.start();
    }
    
    
    public void tickHundred() {
    	
    	new Thread() {
        		
    		public void run() {
        		for (int i = 0; i < 101; i++) {
        			tick();
        	    }
        	}
    	}.start();
    }
    
    
    public void tick() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
 

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            Car car = new AdHocCar();
            car.setIsPass(Math.random() < 0.3);
            if (!car.getIsPass()){
            	car.setIsReserved(Math.random() <0.2);
            }
            if (car.getIsReserved() || car.getIsPass()){
            	car.setIsPaying(false);
            }
            else {
            	car.setIsPaying(true);
            }
            entranceCarQueue.addCar(car);
            break;
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        simulatorView.tick();

        // Add leaving cars to the exit queue.
        while (true){
        	Car car = simulatorView.getFirstLeavingCar();
        	if (car == null) {
        		break;
        	}
	        	if (car.getIsPaying()){
	        		paymentCarQueue.addCar(car);
	        	}
	        		else{
	                    simulatorView.removeCarAt(car.getLocation());
	        			exitCarQueue.addCar(car);
	        		}
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }

        // Update the car park view.
        simulatorView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

