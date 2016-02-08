package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;

public class AttackNearestAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		/*Unit closestEnemy = game.getEnemyUnits().pick(new NearestPicker(unit));
		if(closestEnemy == null) {
			if(unit.isIdle()) {
				Vector2D position = Vector2DMath.randomVector().scale(game.getMap());
				unit.attack(position);
			}
		} else {
			unit.attack(closestEnemy, false);
		}*/
	}
	
	@Override
	public String toString() {
		return "Action[Attack nearest]";
	}
}
