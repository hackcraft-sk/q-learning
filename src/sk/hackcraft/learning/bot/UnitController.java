package sk.hackcraft.learning.bot;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.iface.ILearning;
import sk.hackcraft.learning.stat.Statistics;

public class UnitController {

	private UnitState [] states;
	
	private ILearning learning;
	
	private Statistics statistics;
	
	private Unit unit;
	
	private UnitState lastState = null;

	private UnitAction executingAction = null;
	
	private int possibleStateChangeFrame = 0;
	
	public UnitController(UnitState[] states, ILearning learning, Unit unit, Statistics statistics) {
		this.unit = unit;
		this.states = states;
		this.learning = learning;
		this.statistics = statistics;
	}
	
	public void update(Game game) {
		if(shouldDecideAction(game)) {
			statistics.print("Deciding action");
			
			UnitState currentState = detectState(game);
			
			statistics.print("Current state "+currentState);
			
			if(lastState != null) {
				double reward = currentState.getValue(game, unit) - lastState.getValue(game, unit); // TODO why is reward always 0
				if (reward != 0) {
					learning.experience(lastState, executingAction, currentState, reward);
					statistics.print("Learned...");
				} else {
					statistics.print("Nothing to learned...");
				}
			}
			
			
			executingAction = (UnitAction)learning.estimateBestActionIn(currentState);
			statistics.print(lastState+" -> "+currentState+" ACT: "+executingAction);
			lastState = currentState;

			statistics.print("ACTION: " + executingAction.toString());
			executingAction.executeOn(game, unit);
			possibleStateChangeFrame += 15;
		}
	}
	
	private boolean shouldDecideAction(Game game) {
		return lastState == null || game.getFrameCount() >= possibleStateChangeFrame;
	}
	
	private UnitState detectState(Game game) { // TODO build state instead of searching in array
		for(UnitState state : states) {
			if(state.isUnitInIt(game, unit)) {
				return state;
			}
		}
		throw new IllegalStateException("Unit has no detected state");
	}
}
