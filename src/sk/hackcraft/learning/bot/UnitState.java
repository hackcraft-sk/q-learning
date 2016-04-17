package sk.hackcraft.learning.bot;

import java.util.List;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.iface.IState;

public class UnitState implements IState {
	
	private int lifeFrom, lifeTo;
	
	private int closestEnemyLifeFrom, closestEnemyLifeTo;
	
	private double closestEnemyDistanceFrom, closestEnemyDistanceTo;
	
	public UnitState(int lifeFrom, int lifeTo, int closestEnemyLifeFrom, int closestEnemyLifeTo, double closestEnemyDistanceFrom, double closestEnemyDistanceTo) {
		this.lifeFrom = lifeFrom;
		this.lifeTo = lifeTo;
		this.closestEnemyLifeFrom = closestEnemyLifeFrom;
		this.closestEnemyLifeTo = closestEnemyLifeTo;
		this.closestEnemyDistanceFrom = closestEnemyDistanceFrom;
		this.closestEnemyDistanceTo = closestEnemyDistanceTo;
	}
	
	public boolean isUnitInIt(Game game, Unit unit) {
		int hp = unit.getHitPoints();
		
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
		
		double distance = closestEnemy == null ? Integer.MAX_VALUE-1 : closestEnemy.getDistance(unit);
		int enemyHp = closestEnemy == null ? Integer.MAX_VALUE - 1 : closestEnemy.getHitPoints();
		
		return	hp >= lifeFrom && hp < lifeTo &&
				distance >= closestEnemyDistanceFrom && distance < closestEnemyDistanceTo &&
				enemyHp >= closestEnemyLifeFrom && enemyHp < closestEnemyLifeTo;
	}
	
	public double getValue(Game game, Unit unit) {
		double value = 0;
		
		for(Unit myUnit : game.self().getUnits()) {
			value += myUnit.getHitPoints();
		}
		
		for(Unit enemyUnit : game.enemy().getUnits()) {
			value -= enemyUnit.getHitPoints();
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		return "State[life["+lifeFrom+","+lifeTo+"] eLife["+closestEnemyLifeFrom+","+closestEnemyLifeTo+"] eDis["+(int)closestEnemyDistanceFrom+","+(int)closestEnemyDistanceTo+"]";
	}
}
