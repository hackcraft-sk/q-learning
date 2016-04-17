package sk.hackcraft.learning.bot.selection;

import sk.hackcraft.learning.bot.math.Vector2D;

public class MostInBackSelector extends DotProductSelector {

	public MostInBackSelector(Vector2D origin, Vector2D forward) {
		super(origin, forward);
	}

}
