package sk.hackcraft.learning;

import sk.hackcraft.learning.iface.IAction;
import sk.hackcraft.learning.iface.ILearning;
import sk.hackcraft.learning.iface.IState;

public class MockLearning implements ILearning {

	private IState[] states;
	private IAction[] actions;
	private int actionIndex = 0;
	
	public MockLearning(IState[] states, IAction[] actions) {
		this.states = states;
		this.actions = actions;
	}
	
	@Override
	public IAction estimateBestActionIn(IState state) {
		IAction result = actions[actionIndex];
		actionIndex = (actionIndex + 1) % actions.length;
		return result;
	}

	@Override
	public void experience(IState originalState, IAction tookAction, IState currentState, double gainedReward) {
		// nothing actually
	}

	@Override
	public double[][] getQMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

}
