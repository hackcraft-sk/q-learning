package sk.hackcraft.learning;

public class MockLearning implements Learning {

	private State[] states;
	private Action[] actions;
	private int actionIndex = 0;
	
	public MockLearning(State[] states, Action[] actions) {
		this.states = states;
		this.actions = actions;
	}
	
	@Override
	public Action estimateBestActionIn(State state) {
		Action result = actions[actionIndex];
		actionIndex = (actionIndex + 1) % actions.length;
		return result;
	}

	@Override
	public void experience(State originalState, Action tookAction, State currentState, double gainedReward) {
		// nothing actually
	}

}
