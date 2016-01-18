package sk.hackcraft.learning.bot;

import sk.hackcraft.bwu.Game;
import sk.hackcraft.learning.iface.ILearning;
import jnibwapi.Unit;

public class UnitController {

	private UnitState [] states;
	
	private ILearning learning;
	
	private Unit unit;
	
	private UnitState lastState = null;

	private UnitAction executingAction = null;
	
	private int possibleStateChangeFrame = 0;
	
	public UnitController(UnitState[] states, ILearning learning, Unit unit) {
		this.unit = unit;
		this.states = states;
		this.learning = learning;
	}
	
	public void update(Game game) {
		if(shouldDecideAction(game)) {
			System.out.println("Deciding action");
			
			UnitState currentState = detectState(game);
			
			System.out.println("Current state "+currentState);
			
			if(lastState != null) {
				double reward = currentState.getValue(game, unit) - lastState.getValue(game, unit);
				learning.experience(lastState, executingAction, currentState, reward);
			}
			
			System.out.println("Learned...");
			
			executingAction = (UnitAction)learning.estimateBestActionIn(currentState);
			System.out.println(lastState+" -> "+currentState+" ACT: "+executingAction);
			lastState = currentState;
			
			executingAction.executeOn(game, unit);
			possibleStateChangeFrame += 15;
		}
	}
	
	private boolean shouldDecideAction(Game game) {
		return lastState == null || game.getFrameCount() >= possibleStateChangeFrame;
	}
	
	private UnitState detectState(Game game) {
		for(UnitState state : states) {
			if(state.isUnitInIt(game, unit)) {
				return state;
			}
		}
		throw new IllegalStateException("Unit has no detected state");
	}
}
