package sk.hackcraft.learning.stat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sk.hackcraft.learning.iface.IState;
import sk.hackcraft.learning.pers.FileIO;

public class Statistics {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm hh:mm:ss"); 
	
	private ArrayList<String> statsLogArray;
	private FileIO fileIO = null;
	
	public Statistics(String fileName) {
		fileIO = new FileIO(fileName);
		
		statsLogArray = new ArrayList<>();
	}
	
	// BASIC FUNDAMENTAL
	private void print(final String value) {
		if (fileIO != null) statsLogArray.add(value);
		
		System.out.println(value);
	}
	
	private void saveLogs() {
		fileIO.saveToFile(statsLogArray);
	}
	
	public void clearLogFile() {
		fileIO.clearFile();
	}
	
	// BASIC LIFECYCLE OF GAME
	public void startGame(int round) {
		print(">--- STARTING game (" + round + ". round) at " + dateFormat.format(new Date()));
	}
	
	public void endGame() {
		if (fileIO != null) saveLogs();
		
		print(">--- ENDING game at " + dateFormat.format(new Date()));
	}
	
	// IN GAME
}
