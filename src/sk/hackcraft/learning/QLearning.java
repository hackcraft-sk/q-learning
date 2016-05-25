package sk.hackcraft.learning;

import java.util.HashMap;
import java.util.Random;

import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitState;
import sk.hackcraft.learning.iface.IAction;
import sk.hackcraft.learning.iface.ILearning;
import sk.hackcraft.learning.iface.IState;

public class QLearning implements ILearning {

	// learning rates
	private double alpha = 0.3;
	private double gamma = 0.9;
	private double random = 0;

	private IState[] states;
	private IAction[] actions;

	private double[][] qMatrix;
	
	//private int[][] rewardMatrix; 					// rewards of states and action

	private HashMap<IState, Integer> stateIndices = new HashMap<>(); 
	private HashMap<IAction, Integer> actionIndices = new HashMap<>();
	
	private final Random mProbabilityRandom;
	private final Random mActionIndexRandom;

	public QLearning(IState[] states, IAction[] actions) {
		this.states = states;
		this.actions = actions;
		buildIndices();
		buildMatrix();
		
		mProbabilityRandom = new Random();
		mActionIndexRandom = new Random();
	}
	
	public QLearning(IState[] states, IAction[] actions, double[][] qMatrix) {
		this.states = states;
		this.actions = actions;
		
		if (qMatrix != null && qMatrix.length == states.length && qMatrix[0].length == actions.length) {
			this.qMatrix = qMatrix;
		} else {
			buildMatrix();
		}
		
		buildIndices();
		
		mProbabilityRandom = new Random();
		mActionIndexRandom = new Random();
	}

	private void buildMatrix() {
		qMatrix = new double[states.length][actions.length];
	}
	
	// index´s for states and actions
	private void buildIndices() {
		for (int i = 0; i < states.length; i++) {
			stateIndices.put(states[i], i);
		}
		for (int i = 0; i < actions.length; i++) {
			actionIndices.put(actions[i], i);
		}
	}

	// TODO Optimize, maybe not needed every time, use some kind of caching and invalidation for states.
	protected double maxQ(int stateIndex) {
		double maxValue = Double.MIN_VALUE;

		for (int actionIndex = 0; actionIndex < actions.length; actionIndex++) {
			maxValue = Math.max(qMatrix[stateIndex][actionIndex], maxValue);
		}
		return maxValue;
	}
	
	@Override
	public IAction estimateBestActionIn(IState state) {
		int stateIndex = stateIndices.get((UnitState)state);
		int bestActionIndex = -1;
		double maxValue = Double.MIN_VALUE;

		for (int actionIndex = 0; actionIndex < actions.length; actionIndex++) {
			double value = qMatrix[stateIndex][actionIndex];

			if (bestActionIndex == -1 || value > maxValue) {
				maxValue = value;
				bestActionIndex = actionIndex;
			}
		}
		/////////
		if (mProbabilityRandom.nextDouble() >= (1-random))
		{
			bestActionIndex = mActionIndexRandom.nextInt(actions.length);
		}
		return actions[bestActionIndex];
	}

	public void experience(IState currentState, IAction action, IState nextState, double reward) {
		int currentStateIndex = stateIndices.get(currentState);
		int nextStateIndex = stateIndices.get(nextState);
		int actionIndex = actionIndices.get(action);
		
		// Using this possible action, consider to go to the next state
		double q = qMatrix[currentStateIndex][actionIndex];
		double r=reward;//double r = getReward(currentState.id, currentAction.id);

		double maxQ = maxQ(nextStateIndex);

		double value = q + alpha * (r + gamma * maxQ - q);
		qMatrix[currentStateIndex][actionIndex] = value;
	}

	@Override
	public double[][] getQMatrix() {
		return qMatrix;
	}
}
