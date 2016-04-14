package Garage.logic;

import Garage.logic.Location;

/**
*
* @author		Sytske Anema (345010)
* 				Remy Buitenkamp (340987)
* 				Cordell Stirling (323643)
* 				Nicole Mulder (350591)
* @date			13-04-2016				
* @version		1.0
*/

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean isPass;
    private boolean isReserved;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    // returns the location
    public Location getLocation() {
        return location;
    }
    
    // method to set the location of the car
    public void setLocation(Location location) {
        this.location = location;
    }

    // returns the amount of minutes that are left before the car leaves
    public int getMinutesLeft() {
        return minutesLeft;
    }

    // method that sets the amount of minutes before the car leaves
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    // method that returns ticket customers
    public boolean getIsPaying() {
        return isPaying;
    }

    // method to determine whether a car (customer) is a paid car (normal customer)
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }
    
    // returns customers that have a pass
    public boolean getIsPass() {
        return isPass;
    }

    // set a car to passholder
    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }
    
    // returns reserved cars
    public boolean getIsReserved() {
        return isReserved;
    }

    // sets a car to reserved 
    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
    
    public void tick() {
        minutesLeft--;
    }

}