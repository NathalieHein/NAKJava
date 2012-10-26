package de.nordakademie.nakjava.gamelogic.stateMachine.events;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachine.Event;

public class ChangeRoundEvent implements Event {
	public Event perform(Map<Target, PlayerState> map) {
		PlayerState current = map.get(Target.SELF);
		map.remove(Target.SELF);
		map.put(Target.SELF, map.get(Target.OPPONENT));
		map.remove(Target.OPPONENT);
		map.put(Target.OPPONENT, current);

		return new EpsilonEvent();
	}
}
