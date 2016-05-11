package sk.hackcraft.learning.bot;

import java.util.LinkedList;

public class UnitStates {
	private static final int lifeResolution = 25;
	private static final int maxLife = 100;
	private static final int distanceResolution = 150;
	private static final int maxDistance = 450;
	//
	private static final double powerResolution = 0.5;
	private static final double maxPower = 2;
	
	private static final int[] lifes = list(lifeResolution, maxLife);
	private static final int[] distances = list(distanceResolution, maxDistance);
	//private static final double[] powers = list(powerResolution, maxPower);
	private static final double[] powers = {0, 0.8, 1.2, Double.MAX_VALUE};
	
	public static UnitState[] build() {		
		
		LinkedList<UnitState> result = new LinkedList<UnitState>();
		String code = "";
		
		for (int i = 0; i < lifes.length - 1; i++) {
			for (int attack = 0; attack < 2; attack++) {		
				for (int j = 0; j < powers.length - 1; j++) {
					for (int jj = 0; jj < powers.length - 1; jj++) {
						for (int k = 0; k < distances.length - 1; k++) {
							for (int l = 0; l < lifes.length - 1; l++) {
								for (int m = 0; m < powers.length - 1; m++) {
									for (int n = 0; n < distances.length - 1; n++) {
										for (int o = 0; o < lifes.length - 1; o++) {
											for (int p = 0; p < powers.length - 1; p++) {
												for (int q = 0; q < distances.length - 1; q++) {
													code = "" + i + attack + j + jj + k + l + m + n + o + p + q;
													result.add(new UnitState(
															code, 
															lifes[i], lifes[i + 1], 
															attack,
															powers[j], powers[j + 1],
															powers[jj], powers[jj + 1],
															distances[k], distances[k + 1],
															lifes[l], lifes[l + 1],
															powers[m], powers[m + 1],
															distances[n], distances[n + 1],
															lifes[o], lifes[o + 1],
															powers[p], powers[p + 1],
															distances[q], distances[q + 1]));
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
		return lifes;
	}
	
	public static int[] getDistancesIntervals() {
		return distances;
	}
	
	public static double[] getPowerIntervals() {
		return powers;
	}
}
