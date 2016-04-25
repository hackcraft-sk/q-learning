package sk.hackcraft.learning.bot;

import bwapi.Game;
import bwapi.Unit;
import sk.hackcraft.learning.iface.IState;

public class UnitState implements IState {

	private String hashCode;

	private int lifeFrom, lifeTo;

	private int underAttack;

	private double armyLifeRatioFrom, armyLifeRationTo;

	private double closestEnemyDistanceFrom, closestEnemyDistanceTo;

	private int closestEnemyLifeFrom, closestEnemyLifeTo;

	private double closestArmyLifeRatioFrom, closestArmyLifeRatioTo;

	private double mostWoundedEnemyDistanceFrom, mostWoundedEnemyDistanceTo;

	private int mostWoundedEnemyLifeFrom, mostWoundedEnemyLifeTo;

	private double mostWoundedArmyLifeRatioFrom, mostWoundedArmyLifeRatioTo;

	private double groupCenterDinstanceFrom, groupCenterDistanceTo;

	public UnitState(String hashCode, int lifeFrom, int lifeTo, int underAttack,
			double armyLifeRatioFrom, double armyLifeRationTo, double closestEnemyDistanceFrom,
			double closestEnemyDistanceTo, int closestEnemyLifeFrom, int closestEnemyLifeTo,
			double closestArmyLifeRatioFrom, double closestArmyLifeRatioTo, double mostWoundedEnemyDistanceFrom,
			double mostWoundedEnemyDistanceTo, int mostWoundedEnemyLifeFrom, int mostWoundedEnemyLifeTo,
			double mostWoundedArmyLifeRatioFrom, double mostWoundedArmyLifeRatioTo, double groupCenterDinstanceFrom,
			double groupCenterDistanceTo) {
		this.hashCode = hashCode;
		this.lifeFrom = lifeFrom;
		this.lifeTo = lifeTo;
		this.underAttack = underAttack;
		this.armyLifeRatioFrom = armyLifeRatioFrom;
		this.armyLifeRationTo = armyLifeRationTo;
		this.closestEnemyDistanceFrom = closestEnemyDistanceFrom;
		this.closestEnemyDistanceTo = closestEnemyDistanceTo;
		this.closestEnemyLifeFrom = closestEnemyLifeFrom;
		this.closestEnemyLifeTo = closestEnemyLifeTo;
		this.closestArmyLifeRatioFrom = closestArmyLifeRatioFrom;
		this.closestArmyLifeRatioTo = closestArmyLifeRatioTo;
		this.mostWoundedEnemyDistanceFrom = mostWoundedEnemyDistanceFrom;
		this.mostWoundedEnemyDistanceTo = mostWoundedEnemyDistanceTo;
		this.mostWoundedEnemyLifeFrom = mostWoundedEnemyLifeFrom;
		this.mostWoundedEnemyLifeTo = mostWoundedEnemyLifeTo;
		this.mostWoundedArmyLifeRatioFrom = mostWoundedArmyLifeRatioFrom;
		this.mostWoundedArmyLifeRatioTo = mostWoundedArmyLifeRatioTo;
		this.groupCenterDinstanceFrom = groupCenterDinstanceFrom;
		this.groupCenterDistanceTo = groupCenterDistanceTo;
	}

	@Override
	public String getHashCode() {
		return hashCode;
	}

	public double getValue(Game game, Unit unit) {
		double value = 0;

		if (game.enemy().getUnits().isEmpty()) {
			return value;
		}

		for (Unit myUnit : game.self().getUnits()) {
			value += myUnit.getHitPoints() + myUnit.getShields();
		}

		for (Unit enemyUnit : game.enemy().getUnits()) {
			value -= enemyUnit.getHitPoints() + enemyUnit.getShields();
		}

		return value;
	}

	@Override
	public String toString() {
		return ("State " + hashCode + " [" + "life[" + lifeFrom + "," + lifeTo + "] " + "underA[" + underAttack + "] "
				+ "aLife[" + armyLifeRatioFrom + "," + armyLifeRationTo + "] "
				+ "ceDis[" + (int) closestEnemyDistanceFrom + "," + (int) closestEnemyDistanceTo + "] " + "ceLife["
				+ closestEnemyLifeFrom + "," + closestEnemyLifeTo + "] " + "caLife[" + closestArmyLifeRatioFrom + ","
				+ closestArmyLifeRatioTo + "] " + "mweDis[" + (int) mostWoundedEnemyDistanceFrom + ","
				+ (int) mostWoundedEnemyDistanceTo + "] " + "mweLife[" + mostWoundedEnemyLifeFrom + ","
				+ mostWoundedEnemyLifeTo + "] " + "mwaLife[" + mostWoundedArmyLifeRatioFrom + ","
				+ mostWoundedArmyLifeRatioTo + "] " + "gcDis[" + (int) groupCenterDinstanceFrom + ","
				+ (int) groupCenterDistanceTo + "]");
	}

}