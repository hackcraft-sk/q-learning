package sk.hackcraft.learning.bot.actions;

import java.util.Random;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;

public class RunAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {
		Random rand = new Random();

		if (unit.isIdle()) {
			int mapWidht = game.mapWidth() * 32;
			int mapHeight = game.mapWidth() * 32;

			int x = unit.getPosition().getX()
					+ (rand.nextInt(mapWidht / 2) - (mapWidht / 4));
			int y = unit.getPosition().getY()
					+ (rand.nextInt(mapHeight / 2) - (mapHeight / 4));

			unit.move(new Position(x, y));
		}

	}
	
	@Override
	public String toString() {
		return "Action[Run]";
	}
}
