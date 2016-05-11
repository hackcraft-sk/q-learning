package sk.hackcraft.learning.bot;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.iface.IAction;

public abstract class UnitAction implements IAction {

	public abstract void executeOn(Game game, Unit unit);
}
