package sk.hackcraft.learning.bot.selection;

import java.util.Collection;
import java.util.Set;

import bwapi.Unit;

public class Pickers
{
	public static Picker FIRST = new Picker()
	{
		@Override
		public Unit pickFrom(Collection<Unit> units)
		{
			if (units.isEmpty())
			{
				return null;
			}
			else
			{
				return units.iterator().next();
			}
		}
	};
}
