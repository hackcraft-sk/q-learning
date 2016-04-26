package sk.hackcraft.learning.bot;

import java.util.HashMap;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwapi.Unit;
import sk.hackcraft.learning.QLearning;
import sk.hackcraft.learning.bot.actions.AttackMostWoundedAction;
import sk.hackcraft.learning.bot.actions.AttackNearestAction;
import sk.hackcraft.learning.bot.actions.AttackWeakestGroupAction;
import sk.hackcraft.learning.bot.actions.DoNothingAction;
import sk.hackcraft.learning.bot.actions.FleeFromEnemyAction;
import sk.hackcraft.learning.bot.actions.FleeToCenterAction;
import sk.hackcraft.learning.bot.actions.RunAction;
import sk.hackcraft.learning.iface.ILearning;
import sk.hackcraft.learning.pers.FileIO;
import sk.hackcraft.learning.stat.PostGameStat;
import sk.hackcraft.learning.stat.Statistics;

public class Bot extends DefaultBWListener {

	static public void main(String[] arguments) {
		new Bot().run();
	}

	private Mirror mirror = new Mirror();

	private Game game;

	private Player self;

	private UnitState[] states = UnitStates.build();
	private UnitAction[] actions = new UnitAction[] { new RunAction(),
			new AttackMostWoundedAction(), new AttackNearestAction(),
			new DoNothingAction(), new FleeFromEnemyAction(),
			new FleeToCenterAction(), new AttackWeakestGroupAction() };

	private ILearning learning;

	private Statistics statistics;
	private PostGameStat postGameStats;
	private FileIO qMatrixFile;

	private HashMap<bwapi.Unit, UnitController> controllers = new HashMap<>();

	public void run() {
		mirror.getModule().setEventListener(this);
		mirror.startGame();
	}

	@Override
	public void onStart() {
		game = mirror.getGame();
		game.sendText("black sheep wall");
		game.setLocalSpeed(15);
		self = game.self();

		qMatrixFile = new FileIO("qMatrix.txt");
		statistics = new Statistics("statistics.txt");
		postGameStats = new PostGameStat("stats.txt");
		statistics.startGame();
		postGameStats.loadFromFile();

		learning = new QLearning(states, actions, qMatrixFile.loadFromFile());

		for (Unit unit : self.getUnits()) {
			controllers.put(unit, new UnitController(states, learning, unit, statistics));
		}
	}

	@Override
	public void onFrame() {
		game.drawTextScreen(10, 10,
				"Playing as " + self.getName() + " - " + self.getRace() + " with frame " + game.getFrameCount());
		game.drawTextScreen(10, 20,
				"Score :" + postGameStats.getWin() + " : " + postGameStats.getLoose() + "\tLast 10 percent games: "
						+ postGameStats.getLast10PercentStats()[1] + " : " + postGameStats.getLast10PercentStats()[2]);
		game.drawTextScreen(10, 30, "Average statistic");
		game.drawTextScreen(10, 40, "Winrate : "
				+ postGameStats.getWin() / (postGameStats.getWin() + postGameStats.getLoose())
				+ "\tLast 10 percent games: " + postGameStats.getLast10PercentStats()[1]
						/ (postGameStats.getLast10PercentStats()[1] + postGameStats.getLast10PercentStats()[2]));
		game.drawTextScreen(10, 50, "Frame count: " + postGameStats.getFrameCount() + "\t\tLast 10 percent games: "
				+ postGameStats.getLast10PercentStats()[0] + "\t\tTrend: " + postGameStats.getTrend()[0]);
		game.drawTextScreen(10, 60,
				"Life difference count: " + postGameStats.getLifeDifference() + "\t\tLast 10 percent games: "
						+ postGameStats.getLast10PercentStats()[3] + "\t\tTrend: " + postGameStats.getTrend()[1]);
		game.drawTextScreen(10, 70,
				"Unit count difference: " + postGameStats.getUnitDifference() + "\t\tLast 10 percent games: "
						+ postGameStats.getLast10PercentStats()[4] + "\t\tTrend: " + postGameStats.getTrend()[2]);

		for (Unit unit : self.getUnits()) {
			controllers.get(unit).update(game);
			game.drawTextScreen(10, 10,
					"Playing as " + self.getName() + " - " + self.getRace() + " with frame " + game.getFrameCount());
		}
		if (game.getFrameCount() > 50000) {
			// game.restartGame();
			game.leaveGame();
		}
	}

	@Override
	public void onEnd(boolean winner) {
		super.onEnd(winner);
		qMatrixFile.saveToFile(learning.getQMatrix());

		int groupHp = 0;
		for (Unit u : game.self().getUnits()) {
			groupHp += u.getHitPoints() + u.getShields();
		}

		int enemyGroupHp = 0;
		for (Unit u : game.enemy().getUnits()) {
			enemyGroupHp += u.getHitPoints() + u.getShields();
		}

		postGameStats.add(game.getFrameCount(), winner, groupHp - enemyGroupHp,
				game.self().getUnits().size() - game.enemy().getUnits().size(), true);
		statistics.endGame();
	}

}
