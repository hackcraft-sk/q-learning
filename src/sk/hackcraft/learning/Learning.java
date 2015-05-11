package sk.hackcraft.learning;

public interface Learning {
	
	public Action estimateBestActionIn(State state);
	
	public void experience(State originalState, Action tookAction, State currentState, double gainedReward);
}
