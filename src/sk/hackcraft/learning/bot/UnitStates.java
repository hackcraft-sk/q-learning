package sk.hackcraft.learning.bot;

import java.util.LinkedList;

public class UnitStates {
	private static final int lifeResolution = 25;
	private static final int maxLife = 100;
	private static final int distanceResolution = 150;
	private static final int maxDistance = 450;
	private static final double powerResolution = 0.5;
	private static final double maxPower = 2;
	
	public static UnitState[] build() {		
		
		LinkedList<UnitState> result = new LinkedList<UnitState>();
	
		int[] lifes = list(lifeResolution, maxLife);
		int[] distances = list(distanceResolution, maxDistance);
		double[] powers = list(powerResolution, maxPower);
		
		for (int i = 0; i < lifes.length - 1; i++) {
			for (int x = 0; x < 2; x++) {
				for (int j = 0; j < powers.length - 1; j++) {
					for (int k = 0; k < distances.length - 1; k++) {
						for (int l = 0; l < lifes.length - 1; l++) {
							for (int m = 0; m < powers.length - 1; m++) {
								for (int n = 0; n < distances.length - 1; n++) {
									for (int o = 0; o < lifes.length - 1; o++) {
										for (int p = 0; p < powers.length - 1; p++) {
											for (int q = 0; q < distances.length - 1; q++) {
												String hashCode = "" + i + x + j
														+ k + l + m + n + o + p
														+ q;
												result.add(new UnitState(
														hashCode, lifes[i],
														lifes[i + 1], x,
														powers[j],
														powers[j + 1],
														distances[k],
														distances[k + 1],
														lifes[l], lifes[l + 1],
														powers[m],
														powers[m + 1],
														distances[n],
														distances[n + 1],
														lifes[o], lifes[o + 1],
														powers[p],
														powers[p + 1],
														distances[q],
														distances[q + 1]));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		UnitState[] ar = new UnitState[result.size()];
		
		int i=0;
		while(!result.isEmpty()) {
			ar[i++] = result.removeFirst();
		}
		
		return ar;
	}
	
	private static int[] list(int step, int max) {
		int[] result = new int[(max/step) + 1];
		
		int i=0;
		for(int v=0; v < max; v += step) {
			result[i] = v;
			
			i++;
		}
		result[result.length-1] = Integer.MAX_VALUE;
		return result;
	}
	
	private static double[] list(double step, double max) {
		double[] result = new double[(int) ((max/step) + 1)];
		
		int i=0;
		for(double v=0; v < max; v += step) {
			result[i] = v;
			
			i++;
		}
		result[result.length-1] = Double.MAX_VALUE;
		return result;
	}
	
	public static int[] getLifesIntervals() {
		return list(lifeResolution, maxLife);
	}
	
	public static int[] getDistancesIntervals() {
		return list(distanceResolution, maxDistance);
	}
	
	public static double[] getPowerIntervals() {
		return list(powerResolution, maxPower);
	}
}
