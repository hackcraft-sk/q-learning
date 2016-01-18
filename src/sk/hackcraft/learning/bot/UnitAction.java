package sk.hackcraft.learning.bot;

import jnibwapi.Unit;
import sk.hackcraft.bwu.Game;
import sk.hackcraft.learning.iface.IAction;

public abstract class UnitAction implements IAction {

	public abstract void executeOn(Game game, Unit unit);
}
