package sk.hackcraft.learning.bot.selection;

import bwapi.Unit;
import sk.hackcraft.learning.bot.selection.UnitSelector.RealSelector;

public class NegativeRealSelector implements RealSelector {

	private RealSelector base;
	
	public NegativeRealSelector(RealSelector base) {
		this.base = base;
	}
	
	@Override
	public double getValue(Unit unit) {
		return base.getValue(unit)*-1;
	}

}
