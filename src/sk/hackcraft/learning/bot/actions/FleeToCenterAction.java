package sk.hackcraft.learning.bot.actions;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;

public class FleeToCenterAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		
		int x = 0;
		int y = 0;
		int unitCount = game.self().getUnits().size();
			for (Unit e : game.self().getUnits()) {
				x += e.getPosition().getX();
				y += e.getPosition().getY();
			}
			Position position = new Position(x/unitCount, y/unitCount);
			unit.move(position);
	}

	@Override
	public String toString() {
		return "Action[Flee to center]";
	}
}
