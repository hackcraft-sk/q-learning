package sk.hackcraft.learning.bot.selection;

import bwapi.Unit;
import sk.hackcraft.learning.bot.selection.UnitSelector.RealSelector;
import sk.hackcraft.learning.bot.math.Vector2D;
import sk.hackcraft.learning.bot.math.Vector2DMath;

public class DotProductSelector implements RealSelector {

	private Vector2D base;
	private Vector2D origin;
	
	public DotProductSelector(Vector2D base) {
		this(Vector2D.ZERO, base);
	}
	
	public DotProductSelector(Vector2D origin, Vector2D base) {
		this.base = base;
		this.origin = origin;
	}
	
	@Override
	public double getValue(Unit unit) {
		Vector2D relative = Vector2DMath.toVector(unit.getPosition()).sub(origin);
		return Vector2DMath.dotProduct(relative, base);
	}

}
