package sk.hackcraft.learning.bot;

import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import sk.hackcraft.learning.bot.selection.NearestPicker;
import sk.hackcraft.learning.bot.selection.WoundedPicker;
import sk.hackcraft.learning.iface.ILearning;
import sk.hackcraft.learning.iface.IState;
import sk.hackcraft.learning.stat.Statistics;

public class UnitController {

	private UnitState[] states;

	private ILearning learning;

	private Statistics statistics;

	private Unit unit;

	private UnitState lastState = null;
	private double lastStateValue;

	private UnitAction executingAction = null;

	private int possibleStateChangeFrame = 0;

	public UnitController(UnitState[] states, ILearning learning, Unit unit, Statistics statistics) {
		this.unit = unit;
		this.states = states;
		this.learning = learning;
		this.statistics = statistics;
	}

	public void update(Game game) {
		if (shouldDecideAction(game)) {
			statistics.print("Deciding action");

			UnitState currentState = detectState(game);

			statistics.print("Current state " + currentState);

			if (lastState != null) {
				double reward = currentState.getValue(game, unit) - lastStateValue;
				learning.experience(lastState, executingAction, currentState, reward);
				statistics.print("Learned...");
			}

			executingAction = (UnitAction) learning.estimateBestActionIn(currentState);
			statistics.print(lastState + " -> " + currentState + " ACT: " + executingAction);
			lastState = currentState;
			lastStateValue = currentState.getValue(game, unit);

			statistics.print("ACTION: " + executingAction.toString());
			executingAction.executeOn(game, unit);
			possibleStateChangeFrame += 15;
		}
		//game.setScreenPosition(unit.getPosition());
		game.drawTextMap(unit.getPosition(), executingAction.toString());
	}

	private boolean shouldDecideAction(Game game) {
		return lastState == null || game.getFrameCount() >= possibleStateChangeFrame;
	}

	private UnitState detectState(Game game) {
		
		int lifeFrom = 0, lifeTo = 0;

		int underAttack = 0;

		double armyLifeRatioFrom = 0, armyLifeRationTo = 0;
		
		double attackingUnitsRatioFrom = 0, attackingUnitsRatioTo = 0;

		double closestEnemyDistanceFrom = 0, closestEnemyDistanceTo = 0;

		int closestEnemyLifeFrom = 0, closestEnemyLifeTo = 0;

		double closestArmyLifeRatioFrom = 0, closestArmyLifeRatioTo = 0;

		double mostWoundedEnemyDistanceFrom = 0, mostWoundedEnemyDistanceTo = 0;

		int mostWoundedEnemyLifeFrom = 0, mostWoundedEnemyLifeTo = 0;

		double mostWoundedArmyLifeRatioFrom = 0, mostWoundedArmyLifeRatioTo = 0;

		double groupCenterDinstanceFrom = 0, groupCenterDistanceTo = 0;

		int[] lifes = UnitStates.getLifesIntervals();
		int[] distances = UnitStates.getDistancesIntervals();
		double[] powers = UnitStates.getPowerIntervals();

		// LIFE
		int hp = (unit.getHitPoints() + unit.getShields())
				/ (unit.getType().maxHitPoints() + unit.getType().maxShields()) * 100;
		//

		// UNDER ATTACK
		underAttack = unit.isUnderAttack() ? 1 : 0;

		// ARMY LIFE RATIO
		int groupHp = 0;
		for (Unit u : game.self().getUnits()) {
			groupHp += u.getHitPoints() + u.getShields();
		}

		int enemyGroupHp = 0;
		for (Unit u : game.enemy().getUnits()) {
			enemyGroupHp += u.getHitPoints() + u.getShields();
		}

		double armyLifeRatio = 1;
		if (enemyGroupHp != 0) {
			armyLifeRatio = groupHp / enemyGroupHp;
		}
		//
		
		//ATTACKING UNITS RATIO
		int myAttackingCount = 0;
		for (Unit u : game.self().getUnits()) {
			if (u.isAttacking()) {
				myAttackingCount++;
			}
		}

		int enemyAttackingCount = 0;
		for (Unit u : game.enemy().getUnits()) {
			if (u.isAttacking()) {
				enemyAttackingCount++;
			}
		}

		double attackingUnitsRatio = Double.MAX_VALUE - 1;
		if (game.enemy().getUnits().isEmpty()) {
			attackingUnitsRatio = 1;
		} else {
			attackingUnitsRatio = 1;
			if (enemyAttackingCount != 0) {
				attackingUnitsRatio =  myAttackingCount / enemyAttackingCount;
			}
		}		
		//

		// CLOSEST ENEMY DISTANCE
		Unit closestEnemy = (new NearestPicker(unit).pickFrom(game.enemy().getUnits()));
		double cEnemyDistance = closestEnemy == null ? Integer.MAX_VALUE - 1 : closestEnemy.getDistance(unit);
		//

		// CLOSEST ENEMY HP
		int cEnemyHp = closestEnemy == null ? Integer.MAX_VALUE - 1
				: ((closestEnemy.getHitPoints() + closestEnemy.getShields())
						/ (closestEnemy.getType().maxHitPoints() + closestEnemy.getType().maxShields()) * 100);
		//

		// CLOSEST ARMY HP RATIO
		groupHp = 0;
		for (Unit u : game.self().getUnits()) {
			if (unit.getDistance(u) <= unit.getType().sightRange()) {
				groupHp += u.getHitPoints() + u.getShields();
			}
		}

		enemyGroupHp = 0;
		for (Unit u : game.enemy().getUnits()) {
			if (closestEnemy.getDistance(u) <= unit.getType().sightRange()) {
				enemyGroupHp += u.getHitPoints() + u.getShields();
			}
		}
		
		double cArmyLifeRatio = 1;
		if (enemyGroupHp != 0) {
			cArmyLifeRatio = groupHp / enemyGroupHp;
		}
		//

		// MOST WOUNDED ENEMY DISTANCE
		Unit mostWoundedEnemy = (new WoundedPicker().pickFrom(game.enemy().getUnits()));
		double mwEnemyDistance = mostWoundedEnemy == null ? Integer.MAX_VALUE - 1 : mostWoundedEnemy.getDistance(unit);
		//

		// MOST WOUNDED ENEMY HP
		int mwEnemyHp = mostWoundedEnemy == null ? Integer.MAX_VALUE - 1
				: ((mostWoundedEnemy.getHitPoints() + mostWoundedEnemy.getShields())
						/ (mostWoundedEnemy.getType().maxHitPoints() + mostWoundedEnemy.getType().maxShields()) * 100);
		//

		// MOST WOUNDED ARMY HP RATIO
		groupHp = 0;
		for (Unit u : game.self().getUnits()) {
			if (unit.getDistance(u) <= unit.getType().sightRange()) {
				groupHp += u.getHitPoints() + u.getShields();
			}
		}

		enemyGroupHp = 0;
		for (Unit u : game.enemy().getUnits()) {
			if (mostWoundedEnemy.getDistance(u) <= unit.getType().sightRange()) {
				enemyGroupHp += u.getHitPoints() + u.getShields();
			}
		}

		double mwArmyLifeRatio = 1;
		if (enemyGroupHp != 0) {
			mwArmyLifeRatio = groupHp / enemyGroupHp;
		}
		//

		// GROUP CENTER DISTANCE
		int gCenterX = 0;
		int gCenterY = 0;
		int unitCount = game.self().getUnits().size();
		for (Unit e : game.self().getUnits()) {
			gCenterX += e.getPosition().getX();
			gCenterY += e.getPosition().getY();
		}
		Position gCenter = new Position(gCenterX / unitCount, gCenterY / unitCount);
		double gCenterDistance = unit.getDistance(gCenter);
		//

		///////////////////////////////////////////////////////////////////////////////////////////
		String code = "";

		for (int i = 1; i < lifes.length; i++) {
			if (hp >= lifes[i - 1] && hp < lifes[i]) {
				lifeFrom = lifes[i - 1];
				lifeTo = lifes[i];
				code += (i - 1);
			}
		}
		
		code += underAttack;
		
		for (int i = 1; i < powers.length; i++) {
			if (armyLifeRatio >= powers[i - 1] && armyLifeRatio < powers[i]) {
				armyLifeRatioFrom = powers[i - 1];
				armyLifeRationTo = powers[i];
				code += (i - 1);
			}
			if (attackingUnitsRatio >= powers[i - 1] && attackingUnitsRatio < powers[i]) {
				attackingUnitsRatioFrom = powers[i - 1];
				attackingUnitsRatioTo = powers[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < distances.length; i++) {
			if (cEnemyDistance >= distances[i - 1] && cEnemyDistance < distances[i]) {
				closestEnemyDistanceFrom = distances[i - 1];
				closestEnemyDistanceTo = distances[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < lifes.length; i++) {
			if (cEnemyHp >= lifes[i - 1] && cEnemyHp < lifes[i]) {
				closestEnemyLifeFrom = lifes[i - 1];
				closestEnemyLifeTo = lifes[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < powers.length; i++) {
			if (cArmyLifeRatio >= powers[i - 1] && cArmyLifeRatio < powers[i]) {
				closestArmyLifeRatioFrom = powers[i - 1];
				closestArmyLifeRatioTo = powers[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < distances.length; i++) {
			if (mwEnemyDistance >= distances[i - 1] && mwEnemyDistance < distances[i]) {
				mostWoundedEnemyDistanceFrom = distances[i - 1];
				mostWoundedEnemyDistanceTo = distances[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < lifes.length; i++) {
			if (mwEnemyHp >= lifes[i - 1] && mwEnemyHp < lifes[i]) {
				mostWoundedEnemyLifeFrom = lifes[i - 1];
				mostWoundedEnemyLifeTo = lifes[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < powers.length; i++) {
			if (mwArmyLifeRatio >= powers[i - 1] && mwArmyLifeRatio < powers[i]) {
				mostWoundedArmyLifeRatioFrom = powers[i - 1];
				mostWoundedArmyLifeRatioTo = powers[i];
				code += (i - 1);
			}
		}
		for (int i = 1; i < distances.length; i++) {
			if (gCenterDistance >= distances[i - 1] && gCenterDistance < distances[i]) {
				groupCenterDinstanceFrom = distances[i - 1];
				groupCenterDistanceTo = distances[i];
				code += (i - 1);
			}
		}
		return new UnitState(code, 
				lifeFrom, lifeTo, 
				underAttack, 
				armyLifeRatioFrom, armyLifeRationTo,
				attackingUnitsRatioFrom, attackingUnitsRatioTo,
				closestEnemyDistanceFrom, closestEnemyDistanceTo, 
				closestEnemyLifeFrom, closestEnemyLifeTo,
				closestArmyLifeRatioFrom, closestArmyLifeRatioTo, 
				mostWoundedEnemyDistanceFrom, mostWoundedEnemyDistanceTo, 
				mostWoundedEnemyLifeFrom, mostWoundedEnemyLifeTo,
				mostWoundedArmyLifeRatioFrom, mostWoundedArmyLifeRatioTo, 
				groupCenterDinstanceFrom, groupCenterDistanceTo);
	}
}
