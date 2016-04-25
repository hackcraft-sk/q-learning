package sk.hackcraft.learning.stat;

import sk.hackcraft.learning.pers.FileIO;

public class PostGameStat {
	private double frameCount;
	private double win;
	private double loose;
	private double lifeDifference;
	private double unitDifference;
	private double sum;

	private FileIO fileIO = null;

	public PostGameStat(String fileName) {
		fileIO = new FileIO(fileName);
		frameCount = 0;
		win = 0;
		loose = 0;
		lifeDifference = 0;
		unitDifference = 0;
		sum = 0;
	}

	private void saveToFile(String ... postStats) {
		fileIO.saveToFile(postStats);
	}
	
	public void loadFromFile() {
		double loaded[] = fileIO.loadFromFileStats();
		if (loaded == null) {
			return;
		}
		frameCount = loaded[0];
		win = loaded[1];
		loose = loaded[2];
		lifeDifference = loaded[3];
		unitDifference = loaded[4];
		sum = loaded[5];
	}

	public void add(double fc, boolean w, double ld, double ud) {
		sum++;
		if (w == true) {
			win++;
		} else {
			loose++;
		}
		frameCount += fc;
		lifeDifference += ld;
		unitDifference += ud;
		saveToFile(String.valueOf(fc),
				String.valueOf((w) ? 1 : 0),
				String.valueOf((w) ? 0 : 1),
				String.valueOf(ld),
				String.valueOf(ud));
	}

	public double getFrameCount() {
		return frameCount/sum;
	}

	public double getWin() {
		return win;
	}

	public double getLoose() {
		return loose;
	}

	public double getLifeDifference() {
		return lifeDifference/sum;
	}

	public double getUnitDifference() {
		return unitDifference/sum;
	}
}
