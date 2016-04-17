package sk.hackcraft.learning.bot.selection.actions;

import bwapi.Game;
import bwapi.Unit;

public interface UnitAction {
	public void execute(Game game, Unit unit);
}
