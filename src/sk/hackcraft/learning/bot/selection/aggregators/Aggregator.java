package sk.hackcraft.learning.bot.selection.aggregators;

import sk.hackcraft.learning.bot.selection.Units;

public interface Aggregator<T>
{
	public T aggregate(Units units);
}
