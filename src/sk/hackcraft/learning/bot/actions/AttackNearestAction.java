package sk.hackcraft.learning.bot.actions;


import java.util.ArrayList;
import java.util.List;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;
import sk.hackcraft.learning.bot.selection.Units;

public class AttackNearestAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		/*final Units enemyUnits = new Units(game.getAllUnits()).minus(new Units(game.self().getUnits()));

		final NearestPicker nearestPicker = new NearestPicker(unit);
		final Unit closestEnemy = nearestPicker.pickFrom(enemyUnits);
		
		if(closestEnemy == null) {
			if(unit.isIdle()) {
				unit.attack(closestEnemy);
			}
		} else {
			unit.attack(closestEnemy, false);
		}*/
		
		final List<Unit> enemyUnits = game.getAllUnits();
		final List<Unit> myUnits = game.self().getUnits();
		
		for (Unit myUnit : myUnits) {
			enemyUnits.remove(myUnit);
		}
		
		Unit closestEnemy = null;
		double shortestDistance = Double.MAX_VALUE;
		
		for (Unit setUnit : enemyUnits)
		{
			Position position = unit.getPosition();
			double distance = setUnit.getDistance(position);
			
			if (distance < shortestDistance)
			{
				shortestDistance = distance;
				closestEnemy = setUnit;
			}
		}
		
		if(closestEnemy != null) {
			unit.attack(closestEnemy, false);
		}
	}
	
	@Override
	public String toString() {
		return "Action[Attack nearest]";
	}
}
