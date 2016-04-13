package Garage.logic;
import java.util.LinkedList;
import java.util.Queue;

/**
*
* @author		Sytske Anema (345010)
* 				Remy Buitenkamp (340987)
* 				Cordell Stirling (323643)
* 				Nicole Mulder (350591)
* @date			13-04-2016				
* @version		1.0
*/

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

}
