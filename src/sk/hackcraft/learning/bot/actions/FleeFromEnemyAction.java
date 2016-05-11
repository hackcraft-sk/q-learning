package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;

public class FleeFromEnemyAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (unit.isUnderAttack()) {
			if (unit.isStuck()) {
				unit.attack(new NearestPicker(unit).pickFrom(game.enemy().getUnits()));
			} else {
				Unit closestEnemy = null;
				for (Unit e : game.enemy().getUnits()) {
					if (closestEnemy == null || unit.getDistance(e) < unit.getDistance(closestEnemy)) {
						closestEnemy = e;
					}
				}
				int ex = closestEnemy.getPosition().getX();
				int ey = closestEnemy.getPosition().getY();
				int ux = unit.getPosition().getX();
				int uy = unit.getPosition().getY();
				Position position = new Position((2 * ux - ex), (2 * uy - ey));
				unit.move(position);
			}
		}

	}

	@Override
	public String toString() {
		return "Action[Flee from enemy]";
	}
}
