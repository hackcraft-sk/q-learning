package sk.hackcraft.learning.stat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sk.hackcraft.learning.iface.IAction;
import sk.hackcraft.learning.iface.IState;
import sk.hackcraft.learning.pers.FileIO;

public class Statistics {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm hh:mm:ss");
	private final String TAB_SEPARATOR = "\t";
	
	private ArrayList<String> statsLogArray;
	private FileIO fileIO = null;
	private Date currentDate;
	
	public Statistics() {
		currentDate = new Date();
	}
	
	public Statistics(String fileName) {
		currentDate = new Date();
		fileIO = new FileIO(fileName);
		
		statsLogArray = new ArrayList<>();
	}
	
	// BASIC FUNDAMENTAL
	public void print(final String value) {
		String log = getActualDateTime() + value;
		if (fileIO != null) statsLogArray.add(log);
		
		System.out.println(log);
	}
	
	private String getActualDateTime() {
		return "[" + dateFormat.format(currentDate) + "] ";
	}
	
	private void saveLogs() {
		fileIO.saveToFile(statsLogArray);
	}
	
	public void clearLogFile() {
		fileIO.clearFile();
	}
	
	// BASIC LIFECYCLE OF GAME
	public void startGame(/*int round*/) {
		print(">---- STARTING game "/* + round + ". round)"*/);
	}
	
	public void endGame() {
		if (fileIO != null) saveLogs();
		
		print(">---- ENDING game");
	}
	
	// IN GAME
	public void currentState(IState state) {
		print("Unit state: life("+state.lifeStatus.toString()+") " + TAB_SEPARATOR
				+ "allies arround("+state.countOfAlliedUnits+") " + TAB_SEPARATOR
				+ "enemies arround("+state.countOfEnemyUnits+") " + TAB_SEPARATOR
				+ "distance of nearest enemy("+state.distanceOfNearestEnemy.toString()+")"
				);
	}
	
	public void choosenAction(IAction action) {
		print("Unit action: " + action.toString());
	}
}
