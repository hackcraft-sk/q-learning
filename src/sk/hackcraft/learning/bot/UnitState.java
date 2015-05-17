package sk.hackcraft.learning.bot;

import sk.hackcraft.bwu.Game;
import sk.hackcraft.bwu.selection.IntegerComparisonSelector;
import sk.hackcraft.bwu.selection.UnitSelector;
import sk.hackcraft.bwu.selection.UnitSelector.BooleanSelector;
import sk.hackcraft.learning.State;
import jnibwapi.Unit;

public class UnitState implements State {
	
	private BooleanSelector selector;
	
	public UnitState(BooleanSelector selector) {
		this.selector = selector;
	}
	
	public boolean isUnitInIt(Unit unit) {
		return selector.isTrueFor(unit);
	}
	
	public double getValue(Game game, Unit unit) {
		double value = 0;
		
		for(Unit myUnit : game.getMyUnits()) {
			value += myUnit.getHitPoints();
		}
		
		for(Unit enemyUnit : game.getEnemyUnits()) {
			value -= enemyUnit.getHitPoints();
		}
		
		return value;
	}
}
