package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;

public class AttackMostWoundedAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (!game.enemy().getUnits().isEmpty()) {
			Unit target = (new NearestPicker(unit).pickFrom(game.enemy().getUnits()));
			for (Unit e : game.enemy().getUnits()) {
				if (target == null) {
					target = e;
				}
				if (target.getShields() + target.getHitPoints() > e.getShields() + e.getHitPoints()) {
					target = e;
				}
			}
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
