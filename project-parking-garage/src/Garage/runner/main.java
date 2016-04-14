package Garage.runner;

import Garage.model.*;

/**
*
* @author		Sytske Anema (345010)
* 				Remy Buitenkamp (340987)
* 				Cordell Stirling (323643)
* 				Nicole Mulder (350591)
* @date			13-04-2016				
* @version		1.0
* 
*/

public class main {

	public static void main(String[] args) {
		
		// simulator with params. The params determine how big the garage will be, 3,60,30 is standard.
		// if the params are empty, it will be set to default (3,6,30)
		// Runs the GUI / Simulator.
		
		Simulator simulator = new Simulator(3, 6, 30);
		
	}
}