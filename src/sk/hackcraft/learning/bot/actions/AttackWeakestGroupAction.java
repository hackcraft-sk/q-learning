package sk.hackcraft.learning.bot.actions;

import java.util.ArrayList;
import java.util.LinkedList;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.bot.UnitAction;
import sk.hackcraft.learning.bot.selection.NearestPicker;

public class AttackWeakestGroupAction extends UnitAction {

	@Override
	public void executeOn(Game game, Unit unit) {

		if (!game.enemy().getUnits().isEmpty()) {
			Unit closestEnemy = null;
			ArrayList<Unit> units[] = new ArrayList[game.enemy().getUnits().size()];
			double hp[] = new double[units.length];
			int min = 0;
			for (int i = 0; i < game.enemy().getUnits().size(); i++) {
				hp[i] += game.enemy().getUnits().get(i).getHitPoints() + game.enemy().getUnits().get(i).getShields();
				for (int j = 0; j < game.enemy().getUnits().size(); j++) {
					if (i != j) {
						if (game.enemy().getUnits().get(i).getDistance(game.enemy().getUnits().get(j)) < 15) {
							units[i].add(game.enemy().getUnits().get(j));
							hp[i] += game.enemy().getUnits().get(j).getHitPoints()
									+ game.enemy().getUnits().get(j).getShields();

						}
					}
				}
				if (hp[min] > hp[i]) {
					min = i;
				}
			}
			closestEnemy = (new NearestPicker(unit).pickFrom(units[min]));
			unit.attack(closestEnemy.getPosition());
		}

	}

	@Override
	public String toString() {
		return "Action[Attack weakest group]";
	}
}
