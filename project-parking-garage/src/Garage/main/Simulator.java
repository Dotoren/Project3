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
    private Thread draad;
    private boolean isWeekend;
    private boolean isSunday;
    private boolean isWeekDay;
    private boolean isExtraLeaving;
    public int enterSpeed;
    public int exitSpeed;
    public int paymentSpeed;
    public int averageNumberOfCarsPerHour;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;

    private int totalTicketCars;
    private int totalParkPassCars;
    private int totalReservationCars;
    
    public boolean getIsWeekend() {
        return isWeekend;
    }

    public void setIsWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
    }
    public boolean getIsSunday() {
        return isSunday;
    }

    public void setIsSunday(boolean isSunday) {
        this.isSunday = isSunday;
    }
    
    public boolean getIsWeekDay() {
        return isWeekDay;
    }

    public void setIsWeekDay(boolean isWeekDay) {
        this.isWeekDay = isWeekDay;
    }
    
    public boolean getIsExtraLeaving() {
        return isExtraLeaving;
    }

    public void setIsExtraLeaving(boolean isExtraLeaving) {
        this.isExtraLeaving = isExtraLeaving;
    }
    
    public int getTotalTicketCars() {
        return totalTicketCars;
    }

    public int getTotalParkPassCars() {
        return totalParkPassCars;
    }

    public int getTotalReservationCars() {
        return totalReservationCars;
    }
    
    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

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
    	
    	draad = new Thread() {
    		
    		public void run() {
    			for (int i = 0; i < 1000; i++) {
    	            tick();
    	        }
    		}
    	};
    	draad.start();
    }
    
    
    public void tickHundred() {
    	
    	draad = new Thread() {
        		
    		public void run() {
        		for (int i = 0; i < 101; i++) {
        			tick();
        	    }
        	}
    	};
    	draad.start();
    }
    
    public void pause() {
    	
    	draad.stop();
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
        
        // methods for returning the value of cars in the simulation
        


        // Get the average number of cars that arrive per hour.
        if (day == 7){
        	setIsSunday(true);
        	setIsWeekend(false);
        	setIsWeekDay(false);
        	setIsExtraLeaving(false);
        }
        else if (day == 4-6 && hour == 19-24){
        	setIsSunday(false);
        	setIsWeekend(true);
        	setIsWeekDay(false);
        	setIsExtraLeaving(false);
        }
        else if (day == 5-7 && hour == 0-1){
        	setIsSunday(false);
        	setIsWeekend(false);
        	setIsWeekDay(false);
        	setIsExtraLeaving(true);
        }
        else {
        	setIsSunday(false);
        	setIsWeekend(false);
        	setIsWeekDay(true);
        	setIsExtraLeaving(false);
        }
        
        int enterSpeed1 = 2;
        int enterSpeed2 = 3;
        int enterSpeed3 = 5;
        int exitSpeed1 = 10;
        int exitSpeed2 = 15;
        int paymentSpeed1 = 10;
        
        if (getIsSunday()){
        	averageNumberOfCarsPerHour = 50;
        	exitSpeed = exitSpeed1;
        	enterSpeed = enterSpeed1;
        	paymentSpeed = paymentSpeed1;
        }
        
        if (getIsWeekDay()){
        	averageNumberOfCarsPerHour = 75;
        	exitSpeed = exitSpeed1;
        	enterSpeed = enterSpeed2;
        	paymentSpeed = paymentSpeed1;
        }
        
        if (getIsWeekend()){
        	averageNumberOfCarsPerHour = 100;
        	exitSpeed = exitSpeed1;
        	enterSpeed = enterSpeed3;
        	paymentSpeed = paymentSpeed1;
        }
        
        if (getIsExtraLeaving()){
        	averageNumberOfCarsPerHour = 50;
        	exitSpeed = exitSpeed2;
        	enterSpeed = enterSpeed1;
        	paymentSpeed = paymentSpeed1;
        }

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            Car car = new AdHocCar();
            car.setIsPass(Math.random() < 0.2);
            if (!car.getIsPass()){
            	car.setIsReserved(Math.random() <0.3);
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
            if (car.getIsReserved() == true){
            	Location freeReservedLocation = simulatorView.getReservedFreeLocation();
                if (freeReservedLocation != null) {
                    simulatorView.setCarAt(freeReservedLocation, car);
                    int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                    car.setMinutesLeft(stayMinutes);
                }
            }
            else {
            	Location freeLocation = simulatorView.getRandomFreeLocation();
                if (freeLocation != null) {
                    simulatorView.setCarAt(freeLocation, car);
                    int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                    car.setMinutesLeft(stayMinutes);
                }
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