package sk.hackcraft.learning.bot.selection;

import sk.hackcraft.learning.bot.math.Vector2D;

public class MostInFrontSelector extends NegativeRealSelector {

	public MostInFrontSelector(Vector2D origin, Vector2D forward) {
		super(new DotProductSelector(origin, forward));
	}
	
}
