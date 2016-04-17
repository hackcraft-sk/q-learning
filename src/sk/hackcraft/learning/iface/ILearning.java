package sk.hackcraft.learning.iface;

import java.util.ArrayList;

public interface ILearning {
	
	public IAction estimateBestActionIn(IState state);
	
	public void experience(IState originalState, IAction tookAction, IState currentState, double gainedReward);

	public double[][] getQMatrix();
}
