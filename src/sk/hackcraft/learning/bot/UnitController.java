package sk.hackcraft.learning.bot;

import sk.hackcraft.bwu.Game;
import sk.hackcraft.learning.Learning;
import sk.hackcraft.learning.State;
import jnibwapi.Unit;

public class UnitController {

	private UnitState [] states;
	
	private Learning learning;
	
	private Unit unit;
	
	private UnitState currentState = null;

	private UnitState executingAction = null;
	
	private int possibleStateChangeFrame = 0;
	
	public UnitController(UnitState[] states, Learning learning, Unit unit) {
		this.unit = unit;
		this.states = states;
	}
	
	public void update(Game game) {
		if(shouldDecideAction(game)) {
			if(currentState != null) {
				
			}
		}
	}
	
	private boolean shouldDecideAction(Game game) {
		return currentState == null || game.getFrameCount() >= possibleStateChangeFrame;
	}
	
	private State detectState() {
		for(UnitState state : states) {
			if(state.isUnitInIt(unit)) {
				return state;
			}
		}
		throw new IllegalStateException("Unit has no detected state");
	}
}
