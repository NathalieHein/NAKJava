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

/**
 * Use the right actions to type your name: Bot ..... The points are random
 * chars which are used for havin a unique name.
 * 
 * @author Kai
 * 
 */
public class LoginBehaviour implements BotBehaviour {

	private static final String NAME = "Bot ";
	private static final char[] CHARS = new char[] { 'a', 'b', 'c', 'd', 'e' };

	@Override
	public State[] getStates() {
		return new State[] { State.LOGIN };
	}

	/**
	 * Three cases: no input -> Begin input -> Fill up until "Bot " "Bot " ->
	 * add some random letters until login is possible
	 */
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

			if (name.startsWith(LoginBehaviour.NAME)) {
				if (name.length() != LoginBehaviour.NAME.length()
						&& submit != null) {
					submit.perform();
				} else {

					final char randomChar = CHARS[new Random()
							.nextInt(CHARS.length)];
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
									return (action.getKey() == LoginBehaviour.NAME
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
