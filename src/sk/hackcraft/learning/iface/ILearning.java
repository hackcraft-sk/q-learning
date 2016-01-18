package sk.hackcraft.learning.iface;

public interface ILearning {
	
	public IAction estimateBestActionIn(IState state);
	
	public void experience(IState originalState, IAction tookAction, IState currentState, double gainedReward);
}
