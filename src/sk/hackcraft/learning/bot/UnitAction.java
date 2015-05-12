package sk.hackcraft.learning.bot;

import jnibwapi.Unit;
import sk.hackcraft.bwu.Game;
import sk.hackcraft.learning.Action;

public abstract class UnitAction implements Action {

	public abstract void executeOn(Game game, Unit unit);
}
