package sk.hackcraft.learning.bot;

import java.util.HashMap;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwapi.Unit;
import sk.hackcraft.learning.QLearning;
import sk.hackcraft.learning.bot.actions.AttackNearestAction;
import sk.hackcraft.learning.bot.actions.RunAction;
import sk.hackcraft.learning.iface.ILearning;

public class Bot extends DefaultBWListener {
	
	static public void main(String[] arguments) {
		new Bot().run();
	}
	
    private Mirror mirror = new Mirror();

    private Game game;
    
    private Player self;
	
	private UnitState[] states = UnitStates.build();
	private UnitAction[] actions = new UnitAction[]{ new AttackNearestAction(), new RunAction() };
	
	private ILearning learning = new QLearning(states, actions);
	
	private HashMap<bwapi.Unit, UnitController> controllers = new HashMap<>();
	
    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }
	

    @Override
    public void onStart() {
        game = mirror.getGame();
        self = game.self();

        for(Unit unit : self.getUnits()) {
			controllers.put(unit, new UnitController(states, learning, unit));
		}
    }

    @Override
    public void onFrame() {
        game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace()+" with frame "+game.getFrameCount());
        
		for (Unit unit : self.getUnits()) {
			controllers.get(unit).update(game);
			game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace()+" with frame "+game.getFrameCount());
		}
    }
}
