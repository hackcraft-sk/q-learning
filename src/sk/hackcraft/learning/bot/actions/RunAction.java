package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;

public class RunAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		/*if(game.getEnemyUnits().isEmpty()) {
			Vector2D position = Vector2DMath.randomVector().scale(game.getMap());
			unit.move(position.toPosition(), false);
		} else {
			if(unit.isIdle()) {
				Vector2D enemyPosition = game.getEnemyUnits().getArithmeticCenter();
				Vector2D myPosition = unit.getPositionVector();
				
				Vector2D runPosition = myPosition.sub(enemyPosition).add(myPosition);
				
				unit.move(runPosition.toPosition(), false);
			}
		}*/
	}
	
	@Override
	public String toString() {
		return "Action[Run]";
	}
}
