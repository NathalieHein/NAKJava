package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states;

import java.io.Serializable;

/**
 * Enum that contains State that Player's can have throughout a game and their
 * followup state
 * 
 * @author Nathalie Hein (12154)
 * 
 */
public enum State implements Serializable {
	LOGIN {

		@Override
		public State getFollowUpState() {
			return CONFIGUREGAME;
		}

	},
	CONFIGUREGAME {

		@Override
		public State getFollowUpState() {
			return READYTOSTARTSTATE;
		}

	},
	READYTOSTARTSTATE {

		@Override
		public State getFollowUpState() {
			return STOP;
		}

	},
	EDITDECK {

		@Override
		public State getFollowUpState() {
			return CONFIGUREGAME;
		}

	},

	PREACTIONSTATE {

		@Override
		public State getFollowUpState() {
			return PLAYCARDSTATE;
		}
	},
	PLAYCARDSTATE {

		@Override
		public State getFollowUpState() {
			return POSTACTIONSTATE;
		}
	},
	POSTACTIONSTATE {

		@Override
		public State getFollowUpState() {
			return ADJUSTCARDHANDSTATE;
		}
	},
	ADJUSTCARDHANDSTATE {

		@Override
		public State getFollowUpState() {
			return STOP;
		}
	},
	STOP {

		@Override
		public State getFollowUpState() {
			return PREACTIONSTATE;
		}
	},
	OTHERPLAYERLEFTGAME {

		@Override
		public State getFollowUpState() {
			return READYTOSTARTSTATE;
		}

	},
	ENDOFGAMESTATE {

		@Override
		public State getFollowUpState() {
			return null;
		}

	},
	SIMULATIONSTATE {

		@Override
		public State getFollowUpState() {
			return PLAYCARDSTATE;
		}

	},
	DISCARDONECARDSTATE {

		@Override
		public State getFollowUpState() {
			return PLAYCARDSTATE;
		}

	};

	public abstract State getFollowUpState();

}
