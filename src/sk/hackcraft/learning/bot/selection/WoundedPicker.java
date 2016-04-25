package sk.hackcraft.learning.bot.selection;

import java.util.Collection;

import bwapi.Position;
import bwapi.Unit;

public class WoundedPicker implements Picker
{	
	public WoundedPicker()
	{
		
	}
	
	@Override
	public Unit pickFrom(Collection<Unit> units)
	{
		Unit selectedUnit = null;
		double lowestHP = Double.POSITIVE_INFINITY;
		
		for (Unit setUnit : units)
		{
			double hp = setUnit.getHitPoints()+setUnit.getShields();
			
			if (hp < lowestHP)
			{
				lowestHP = hp;
				selectedUnit = setUnit;
			}
		}
		
		return selectedUnit;
	}
}
