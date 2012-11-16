package de.nordakademie.nakjava.client.bot;

import java.util.Random;

import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.SubmitPlayerNameAction;
import de.nordakademie.nakjava.server.shared.proxy.actions.login.TypePlayerNameAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;
import de.nordakademie.nakjava.server.shared.serial.PlayerState;
import de.nordakademie.nakjava.util.StringUtilities;

public class LoginBehaviour implements BotBehaviour {

	private String name = "Bot ";
	private char[] chars = new char[] { 'a', 'b', 'c', 'd', 'e' };

	@Override
	public State[] getStates() {
		return new State[] { State.LOGIN };
	}

	@Override
	public void act(PlayerState state) {
		final String name = VisibleModelFields.LOGINSPECIFICMODEL_CURRENTPARTOFNAME_SELF
				.getValue(state.getModel().getGenericTransfer());
		if (StringUtilities.isNotNullOrEmpty(name)) {
			ActionContext submit = AbstractActionSelector.selectAction(state
					.getActions(), new ActionContextSelector() {

				@Override
				public boolean select(ActionContext context) {
					return context instanceof SubmitPlayerNameAction;
				}
			});

			if (name.startsWith(this.name)) {
				if (name.length() != this.name.length() && submit != null) {
					submit.perform();
				} else {

					final char randomChar = chars[new Random()
							.nextInt(chars.length)];
					AbstractActionSelector.selectAction(state.getActions(),
							new ActionContextSelector() {

								@Override
								public boolean select(ActionContext context) {
									if (context instanceof KeyAction) {
										KeyAction action = (KeyAction) context;
										return (action.getKey() == randomChar);
									}

									return false;
								}
							}).perform();
				}
			} else {

				AbstractActionSelector.selectAction(state.getActions(),
						new ActionContextSelector() {

							@Override
							public boolean select(ActionContext context) {
								if (context instanceof KeyAction) {
									KeyAction action = (KeyAction) context;
									return (action.getKey() == LoginBehaviour.this.name
											.charAt(name.length()));
								}

								return false;
							}
						}).perform();

			}

		} else {
			AbstractActionSelector.selectAction(state.getActions(),
					new ActionContextSelector() {

						@Override
						public boolean select(ActionContext context) {
							if (context instanceof TypePlayerNameAction) {
								TypePlayerNameAction action = (TypePlayerNameAction) context;
								return (action.getKey() == 'B');
							}

							return false;
						}
					}).perform();
		}
	}
}
