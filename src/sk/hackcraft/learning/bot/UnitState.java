package sk.hackcraft.learning.bot;

import sk.hackcraft.bwu.Game;
import sk.hackcraft.learning.State;
import jnibwapi.Unit;

public abstract class UnitState implements State {

	public abstract boolean isUnitInIt(Unit unit);
	
	public double getValue(Game game, Unit unit) {
		return 0; // TODO
	}
}
