package sk.hackcraft.learning.iface;

import sk.hackcraft.learning.enums.EDistance;
import sk.hackcraft.learning.enums.ELifeStatus;

public interface IState {
	int countOfEnemyUnits = 0;
	int countOfAlliedUnits = 0;
	
	EDistance distanceOfNearestEnemy = EDistance.OUT_OF_SIGHT;
	ELifeStatus lifeStatus = ELifeStatus.HIGH;
}
