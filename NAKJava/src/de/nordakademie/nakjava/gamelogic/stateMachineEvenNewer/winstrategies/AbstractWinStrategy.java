package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;

/**
 * Base class for a winstrategy which decides whether a player has win/loose or
 * a neutral result after a round of playing. This is checked after each round.
 * Special cases like win/win, loose/neutral etc... are handled here.
 * 
 * @author Kai
 * 
 */
public abstract class AbstractWinStrategy implements WinStrategy {

	private List<WinCheck> winChecks;

	/**
	 * Lost has a higher priority than win
	 */
	@Override
	public Map<Target, RoundResult> getRoundResult(
			Map<Target, PlayerState> playerMap) {

		Map<Target, PlayerState> inversePlayerMap = new EnumMap<>(Target.class);

		inversePlayerMap.put(Target.SELF, playerMap.get(Target.OPPONENT));
		inversePlayerMap.put(Target.OPPONENT, playerMap.get(Target.SELF));

		Map<Target, RoundResult> checkResult = new EnumMap<>(Target.class);

		RoundResult selfResult = checkSelfRoundResult(playerMap);
		RoundResult opponentResult = checkSelfRoundResult(inversePlayerMap);

		if (selfResult == RoundResult.NEUTRAL
				^ opponentResult == RoundResult.NEUTRAL) {

			if (selfResult == RoundResult.NEUTRAL) {
				selfResult = RoundResult.invert(opponentResult);
			} else {
				opponentResult = RoundResult.invert(selfResult);
			}

		}

		checkResult.put(Target.SELF, selfResult);
		checkResult.put(Target.OPPONENT, opponentResult);

		return checkResult;
	}

	private RoundResult checkSelfRoundResult(Map<Target, PlayerState> stateMap) {

		RoundResult result = RoundResult.NEUTRAL;

		for (WinCheck check : winChecks) {
			RoundResult tempResult = check.check(stateMap);

			if (tempResult == RoundResult.LOST) {
				result = RoundResult.LOST;
				break;
			}
			if (tempResult == RoundResult.WIN) {
				result = RoundResult.WIN;
			}
		}

		return result;
	}

	List<WinCheck> getChecks() {
		return new LinkedList<>(winChecks);
	}

	void setChecks(List<WinCheck> checks) {
		winChecks = checks;
	}

}
