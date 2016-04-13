package Garage.model;

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
import java.util.Arrays;
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
    int enterSpeed = 6;
    int exitSpeed = 9;
    int paymentSpeed = 10;
    public int averagetotalNumberOfCarsPerHour;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int totalNormalCars;
    private int totalNumberOfCars;
    private int totalParkPassCars;
    private int totalReservationCars;
    private int entranceQueueNumber;
    private int paymentQueueNumber;
    private int exitQueueNumber;
    private String dayType;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int tickPause = 100;
    
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
    
    public int getTotalNumberOfCars() {
        return totalNumberOfCars;
    }
    
    public int getTotalNormalCars() {
    	return totalNormalCars;
    }

    public int getTotalParkPassCars() {
        return totalParkPassCars;
    }

    public int getTotalReservationCars() {
        return totalReservationCars;
    }
    
    public int getEntranceQueueNumber() {
    	return entranceQueueNumber;
    }
    
    public int getPaymentQueueNumber() {
    	return paymentQueueNumber;
    }
    
    public int getExitQueueNumber() {
    	return exitQueueNumber;
    }
    
    public String getDayType() {
    	return dayType;
    }
    
    public int getHour() {
    	return hour;
    }
    
    public int getMinute(){
    	return minute;
    }
    
    public void setDay(int day){
    	this.day = day;
    }
    
    public void setHour(int hour){
    	this.hour = hour;
    }
    

    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30, this);
        totalNumberOfCars = 0;
        entranceQueueNumber = 0;
        paymentQueueNumber = 0;
        exitQueueNumber = 0;
    }
    
    public Simulator(int floors, int rows, int places) {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        totalNumberOfCars = 0;
        entranceQueueNumber = 0;
        paymentQueueNumber = 0;
        exitQueueNumber = 0;
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
        
        int[] nums1 = {4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        int[] nums2 = {19,20,21,22,23};
        int[] nums3 = {0,1,2,3};
        int[] nums4 = {4,5,6};
        int[] nums5 = {5,6,7};
        

        // Get the average number of cars that arrive per hour.
        if (day == 7 && Arrays.binarySearch(nums1,  hour) >= 0){
        	setIsSunday(true);
        	setIsWeekend(false);
        	setIsWeekDay(false);
        }
        
        else if (Arrays.binarySearch(nums4,  day) >= 0 && Arrays.binarySearch(nums2,  hour) >= 0){
        	setIsSunday(false);
        	setIsWeekend(true);
        	setIsWeekDay(false);
        }
        
        else if (Arrays.binarySearch(nums5,  day) >= 0 && Arrays.binarySearch(nums3,  hour) >= 0){
        	setIsSunday(false);
        	setIsWeekend(true);
        	setIsWeekDay(false);
        }
        
        else {
        	setIsSunday(false);
        	setIsWeekend(false);
        	setIsWeekDay(true);
        }
        
        
        if (getIsSunday()){
        	averagetotalNumberOfCarsPerHour = 25;
        }
        
        if (getIsWeekDay()){
        	averagetotalNumberOfCarsPerHour = 100;
        }
        
        if (getIsWeekend()){
        	averagetotalNumberOfCarsPerHour = 250;
        }
        if (day == 0){
        	dayType = "Monday";
        }
        else if (day == 1){
        	dayType = "Tuesday";
        }
        else if (day == 2){
        	dayType = "Wednesday";
        }
        else if (day == 3){
        	dayType = "Thursday";
        }
        else if (day == 4){
        	dayType = "Friday";
        }
        else if (day == 5){
        	dayType = "Saturday";
        }
        else if (day == 6){
        	dayType = "Sunday";
        }

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averagetotalNumberOfCarsPerHour * 0.1;
        double totalNumberOfCarsPerHour = averagetotalNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int totalNumberOfCarsPerMinute = (int)Math.round(totalNumberOfCarsPerHour / 60);
        
        // Add the cars to the back of the queue.
        for (int i = 0; i < totalNumberOfCarsPerMinute; i++) {
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
            entranceQueueNumber++;
            entranceCarQueue.addCar(car);
        }
                
        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
        	entranceQueueNumber--; 
            
            // Find a space for this car.
            if (car.getIsReserved() == true){
            	Location freeReservedLocation = simulatorView.getReservedFreeLocation();
                if (freeReservedLocation != null) {
                    simulatorView.setCarAt(freeReservedLocation, car);
                    int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                    car.setMinutesLeft(stayMinutes);
                	totalReservationCars++;
                	totalNumberOfCars++;
                }
            }
            else {
            	Location freeLocation = simulatorView.getRandomFreeLocation();
                if (freeLocation != null) {
                    simulatorView.setCarAt(freeLocation, car);
                    int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                    car.setMinutesLeft(stayMinutes);
                    if (car.getIsPass()){
                    	totalParkPassCars++;
                    	totalNumberOfCars++;
                    }
                    else if (car.getIsPaying()){
                    	totalNumberOfCars++;
                    	totalNormalCars++;
                    }
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
	        		paymentQueueNumber++;
	        		paymentCarQueue.addCar(car);
	        		
	        	}
	        		else{
	                    simulatorView.removeCarAt(car.getLocation());
	                    
	                    if (car.getIsPass()){
	                    	totalParkPassCars--;
	                    }
	                    else if (car.getIsReserved()){
	                    	totalReservationCars--;
	                    }
                    	totalNumberOfCars--;
                    	exitQueueNumber++;
	        			exitCarQueue.addCar(car);
	        			
	        		}
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            paymentQueueNumber--;
            exitQueueNumber++;
            
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            totalNumberOfCars--;
            totalNormalCars--;
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            exitQueueNumber--;
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