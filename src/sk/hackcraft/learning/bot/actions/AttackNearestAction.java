package sk.hackcraft.learning.bot.actions;


import sk.hackcraft.learning.bot.UnitAction;
import bwapi.Game;
import bwapi.Unit;

public class AttackNearestAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (!game.enemy().getUnits().isEmpty()) {
			Unit closestEnemy = null;
			for (Unit enemy : game.enemy().getUnits()) {
				if (closestEnemy == null
						|| unit.getDistance(enemy) < unit
								.getDistance(closestEnemy)) {
					closestEnemy = enemy;
				}
			}
			unit.attack(closestEnemy.getPosition());
		}

	}
	
	@Override
	public String toString() {
		return "Action[Attack nearest]";
	}
}
