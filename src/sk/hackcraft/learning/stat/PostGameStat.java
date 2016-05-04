package sk.hackcraft.learning.stat;

import java.util.LinkedList;

import sk.hackcraft.learning.pers.FileIO;

public class PostGameStat {
	private double frameCount;
	private double win;
	private double loose;
	private double lifeDifference;
	private double unitDifference;
	private double sum;
	private LinkedList<Double> Last10Percent[];
	private double Last10PercentStats[];

	private FileIO fileIO = null;

	public PostGameStat(String fileName) {
		Last10Percent = new LinkedList[5];
		Last10PercentStats = new double[5];
		for(int i=0;i<Last10Percent.length;i++)
		{
			Last10Percent[i] = new LinkedList<>();
		}
		fileIO = new FileIO(fileName);
		frameCount = 0;
		win = 0;
		loose = 0;
		lifeDifference = 0;
		unitDifference = 0;
		sum = 0;
	}

	private void saveToFile(String... postStats) {
		fileIO.saveToFile(postStats);
	}

	public void loadFromFile() {
		LinkedList<double[]> loaded = fileIO.loadFromFileStats();
		if (loaded == null) {
			return;
		}
		for (int i = 0; i < loaded.size(); i++) {
			add(loaded.get(i)[0], loaded.get(i)[1] > loaded.get(i)[2], loaded.get(i)[3], loaded.get(i)[4],false);
		}
	}

	public void add(double fc, boolean w, double ld, double ud, boolean writeToFile) {
		sum++;
		if (w == true) {
			win++;
		} else {
			loose++;
		}
		frameCount += fc;
		lifeDifference += ld;
		unitDifference += ud;
		if (writeToFile) {
			saveToFile(String.valueOf(fc), String.valueOf((w) ? 1 : 0), String.valueOf((w) ? 0 : 1), String.valueOf(ld),
					String.valueOf(ud));
		}
		if (((int)(sum * 0.1) < Last10Percent[0].size())) {
			for (int i = 0; i < Last10Percent.length; i++) {
				Last10PercentStats[i] -= Last10Percent[i].removeFirst();
			}
		}
		Last10Percent[0].add(fc);
		Last10PercentStats[0] += fc;
		Last10Percent[1].add(w ? 1.0 : 0.0);
		Last10PercentStats[1] += (w ? 1.0 : 0.0);
		Last10Percent[2].add(w ? 0.0 : 1.0);
		Last10PercentStats[2] += (w ? 0.0 : 1.0);
		Last10Percent[3].add(ld);
		Last10PercentStats[3] += ld;
		Last10Percent[4].add(ud);
		Last10PercentStats[4] += ud;
	}

	public double[] getLast10PercentStats() {
		double mean[] = new double[Last10PercentStats.length];
		double c = Last10Percent[0].size();
			mean[0] = Last10PercentStats[0] / c;
			mean[1] = Last10PercentStats[1];
			mean[2] = Last10PercentStats[2];
			mean[3] = Last10PercentStats[3] / c;
			mean[4] = Last10PercentStats[4] / c;
			for(int i=0;i<mean.length;i++)
			{
				mean[i] = mean[i]*100;
				mean[i] = Math.round(mean[i]);
				mean[i] = mean[i] / 100;
			}
		return mean;
	}

	public double[] getTrend() {
		double trend[] = new double[Last10PercentStats.length - 2];
		trend[0] = Last10PercentStats[0]/Last10Percent[0].size() - getFrameCount();
		trend[1] = Last10PercentStats[3]/Last10Percent[0].size() - getLifeDifference();
		trend[2] = Last10PercentStats[4]/Last10Percent[0].size() - getUnitDifference();
		for(int i=0;i<trend.length;i++)
		{
			trend[i] = trend[i]*100;
			trend[i] = Math.round(trend[i]);
			trend[i] = trend[i] / 100;
		}
		return trend;

	}

	public double getFrameCount() {
		double ret = frameCount / sum;
		ret = ret*100;
		ret = Math.round(ret);
		ret = ret / 100;
		return ret;
	}

	public double getWin() {
		return win;
	}

	public double getLoose() {
		return loose;
	}

	public double getLifeDifference() {
		double ret = lifeDifference / sum;
		ret = ret*100;
		ret = Math.round(ret);
		ret = ret / 100;
		return ret;
	}

	public double getUnitDifference() {
		double ret = unitDifference / sum;
		ret = ret*100;
		ret = Math.round(ret);
		ret = ret / 100;
		return ret;
	}
}
