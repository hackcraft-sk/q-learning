package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.math.Vector2D;
import sk.hackcraft.learning.bot.math.Vector2DMath;
import sk.hackcraft.learning.bot.selection.Units;

public class RunAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		final Units enemyUnits = new Units(game.getAllUnits()).minus(new Units(game.self().getUnits()));
		if(!enemyUnits.isEmpty()) {
			if(unit.isIdle()) {
				Vector2D enemyPosition = enemyUnits.getArithmeticCenter();
				Vector2D myPosition = Vector2DMath.toVector(unit.getPosition());
				
				Vector2D runPosition = myPosition.sub(enemyPosition).add(myPosition);
				
				unit.move(runPosition.toPosition(), false);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Action[Run]";
	}
}
