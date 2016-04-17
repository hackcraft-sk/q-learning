package sk.hackcraft.learning.bot.selection;

import bwapi.Unit;
import sk.hackcraft.learning.bot.selection.UnitSelector.BooleanSelector;
import sk.hackcraft.learning.bot.selection.UnitSelector.RealSelector;
import sk.hackcraft.learning.bot.math.Comparison;

public class RealComparisonSelector implements BooleanSelector
{
	private RealSelector selector;
	private double value;
	private Comparison comparison;

	public RealComparisonSelector(RealSelector selector, double value, Comparison comparison)
	{
		if (selector == null)
		{
			throw new NullPointerException("Selector cannot be null");
		}
		if (comparison == null)
		{
			throw new NullPointerException("Comparison cannot be null");
		}

		this.selector = selector;
		this.value = value;
		this.comparison = comparison;
	}

	@Override
	public boolean isTrueFor(Unit unit)
	{
		return comparison.compare(selector.getValue(unit), value);
	}
}
