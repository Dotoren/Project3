public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    /*
     * this code sets the car to pay if the car does not have a parkpass
     * otherwise the car is set to not pay.
     */
    boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }
    ParkPass ParkPass = new ParkPass();{
    	if (!ParkPass.isPass){
    		setIsPaying(isPaying);
    	}
    	else{
    		setIsPaying(!isPaying);
    	}
    }
    public void tick() {
        minutesLeft--;
    }

}