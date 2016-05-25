package sk.hackcraft.learning.bot.actions;


import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;
import bwapi.Game;
import bwapi.Unit;

public class AttackNearestAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (!game.enemy().getUnits().isEmpty()) {
		Unit target = new NearestPicker(unit).pickFrom(game.enemy().getUnits());	
			if (unit.getTargetPosition() != target.getPosition()) {
				unit.attack(target);
			}
		}

	}
	
	@Override
	public String toString() {
		return "Action[Attack nearest]";
	}
}
