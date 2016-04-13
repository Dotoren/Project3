package Garage.runner;

import Garage.logic.*;
import Garage.model.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Objects;

public class main {

	public static void main(String[] args) {
		
		// simulator with params. The params determine how big the garage will be, 3,60,30 is standard.
		// if the params are empty, it will be set to default (3,6,30)
		
		Simulator simulator = new Simulator(3, 6, 30);
		
	}
}