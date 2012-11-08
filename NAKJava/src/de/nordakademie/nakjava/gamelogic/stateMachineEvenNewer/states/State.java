package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states;

import java.io.Serializable;

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
		// TODO take out the stop of statemachine here

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

	};

	public abstract State getFollowUpState();

}
