package sk.hackcraft.learning.bot;

import java.util.HashMap;

import jnibwapi.Player;
import jnibwapi.Unit;
import sk.hackcraft.bwu.BWU;
import sk.hackcraft.bwu.Game;
import sk.hackcraft.bwu.Graphics;
import sk.hackcraft.bwu.Vector2D;
import sk.hackcraft.learning.Learning;
import sk.hackcraft.learning.QLearning;
import sk.hackcraft.learning.State;
import sk.hackcraft.learning.bot.actions.AttackNearestAction;
import sk.hackcraft.learning.bot.actions.RunAction;

public class Bot extends sk.hackcraft.bwu.AbstractBot {
	
	static public void main(String[] arguments) {
		BWU bwu = new BWU()
		{
			@Override
			protected Bot createBot(Game game)
			{
				return new Bot(game);
			}
		};
		
		bwu.start();
	}
	
	private UnitState[] states = UnitStates.build();
	private UnitAction[] actions = new UnitAction[]{ new AttackNearestAction(), new RunAction() };
	
	private Learning learning = new QLearning(states, actions);
	
	private HashMap<Unit, UnitController> controllers = new HashMap<>();
	
	public Bot(Game game) {
		super(game);
	}
	
	@Override
	public void gameStarted() {
		try {
			for(Unit unit : getGame().getMyUnits()) {
				controllers.put(unit, new UnitController(states, learning, unit));
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void gameUpdated() {
		try {
			for (Unit unit : getGame().getMyUnits()) {
				controllers.get(unit).update(getGame());
			}
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void gameEnded(boolean isWinner) {}

	@Override
	public void keyPressed(int keyCode) {}

	@Override
	public void playerLeft(Player player) {}

	@Override
	public void playerDropped(Player player) {}

	@Override
	public void nukeDetected(Vector2D target) {}

	@Override
	public void unitDiscovered(Unit unit) {}

	@Override
	public void unitDestroyed(Unit unit) {}

	@Override
	public void unitEvaded(Unit unit) {}

	@Override
	public void unitCreated(Unit unit) {}

	@Override
	public void unitCompleted(Unit unit) {}

	@Override
	public void unitMorphed(Unit unit) {}

	@Override
	public void unitShowed(Unit unit) {}

	@Override
	public void unitHid(Unit unit) {}

	@Override
	public void unitRenegaded(Unit unit) {}

	@Override
	public void draw(Graphics graphics) {}

	@Override
	public void messageSent(String message) {}

	@Override
	public void messageReceived(String message) {}

	@Override
	public void gameSaved(String gameName) {}
}
