package sk.hackcraft.learning.bot.selection.aggregators;

import bwapi.Unit;
import sk.hackcraft.learning.bot.selection.UnitSelector.RealSelector;
import sk.hackcraft.learning.bot.selection.Units;

public class AverageRealAggregator implements Aggregator<Double>
{
	private RealSelector selector;
	
	public AverageRealAggregator(RealSelector selector)
	{
		this.selector = selector;
	}
	
	public Double aggregate(Units units)
	{
		double accumulated = 0;
		
		for(Unit unit : units)
		{
			accumulated += selector.getValue(unit);
		}
		
		if(units.size() > 0)
		{
			accumulated /= units.size();
		}
		
		return accumulated;
	}
}
