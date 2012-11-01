package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states;

import java.io.Serializable;

public enum State implements Serializable {
	LOGIN {

		@Override
		public State getFollowUpState() {
			return CHOOSEDECK;
		}

	},
	CHOOSEDECK {

		@Override
		public State getFollowUpState() {
			return CHOOSESTRATEGY;
		}

	},
	CHOOSESTRATEGY {

		@Override
		public State getFollowUpState() {
			return READYTOSTARTSTATE;
		}

	},
	READYTOSTARTSTATE {

		@Override
		public State getFollowUpState() {
			return PREACTIONSTATE;
		}

	},
	EDITDECK {

		@Override
		public State getFollowUpState() {
			return CHOOSEDECK;
		}

	},
	GAMETOSTARTSTATE {

		@Override
		public State getFollowUpState() {
			return STOP;
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
			return NEXT;
		}
	},
	NEXT {

		@Override
		public State getFollowUpState() {
			return PREACTIONSTATE;
		}
	};

	public abstract State getFollowUpState();

}
