package sk.hackcraft.learning.bot;

import java.util.LinkedList;

public class UnitStates {
	public static UnitState[] build() {
		int lifeResolution = 25;
		int maxLife = 100;
		int distanceResolution = 150;
		int maxDistance = 450;
		
		LinkedList<UnitState> result = new LinkedList<UnitState>();
	
		int[] lifes = list(lifeResolution, maxLife);
		int[] distances = list(distanceResolution, maxDistance);
		
		for(int i=0; i<lifes.length-1; i++) {
			for(int j=0; j<lifes.length-1; j++) {
				for(int k=0; k<distances.length-1; k++) {
					result.add(new UnitState(lifes[i], lifes[i+1], lifes[j], lifes[j+1], distances[k], distances[k+1]));
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
}
