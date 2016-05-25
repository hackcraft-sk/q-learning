package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;
import sk.hackcraft.learning.bot.selection.WoundedPicker;

public class AttackMostWoundedAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (!game.enemy().getUnits().isEmpty()) {
			Unit target = (new WoundedPicker().pickFrom(game.enemy().getUnits()));
			if (unit.getTargetPosition() != target.getPosition()) {
				unit.attack(target);
			}
		}

	}

	@Override
	public String toString() {
		return "Action[Attack most wounded]";
	}
}
