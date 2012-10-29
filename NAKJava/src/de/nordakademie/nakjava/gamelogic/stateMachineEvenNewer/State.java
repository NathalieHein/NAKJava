package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer;

import java.util.Map;

import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.AdjustCardhand;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.PlayCard;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Postaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.Preaction;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.actions.RoundEndAction;

public enum State {
	PREACTIONSTATE {
		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			Preaction.perform(playerStateMap);
		}

		@Override
		public State getFollowUpState() {
			return PLAYCARDSTATE;
		}
	},
	PLAYCARDSTATE {
		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			PlayCard.perform(playerStateMap);
		}

		@Override
		public State getFollowUpState() {
			return POSTACTIONSTATE;
		}
	},
	POSTACTIONSTATE {
		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			Postaction.perform(playerStateMap);
		}

		@Override
		public State getFollowUpState() {
			return ADJUSTCARDHANDSTATE;
		}
	},
	ADJUSTCARDHANDSTATE {
		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			AdjustCardhand.perform(playerStateMap);
		}

		@Override
		public State getFollowUpState() {
			return STOP;
		}
	},
	STOP {
		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			RoundEndAction.perform(playerStateMap);
		}

		@Override
		public State getFollowUpState() {
			return NEXT;
		}
	},
	NEXT {

		@Override
		public void perform(Map<Target, PlayerState> playerStateMap) {
			// TODO Auto-generated method stub

		}

		@Override
		public State getFollowUpState() {
			return PREACTIONSTATE;
		}
	};

	public abstract void perform(Map<Target, PlayerState> playerStateMap);

	public abstract State getFollowUpState();
}
