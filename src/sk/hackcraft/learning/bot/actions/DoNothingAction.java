package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;

public class DoNothingAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		
		unit.stop();

	}

	@Override
	public String toString() {
		return "Action[Do nothing]";
	}
}
