package de.nordakademie.nakjava.client.internal.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class PanelPicker {

	private Map<State, Class<StatePanel>> statePanels;
	private State currentState = null;
	private StatePanel currentPanel;

	private boolean actor;

	public PanelPicker(boolean actor) {
		this.actor = actor;
		statePanels = new EnumMap<State, Class<StatePanel>>(State.class);
		List<Class<?>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.client.game.gui", "",
				new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class clazz) {
						return StatePanel.class.isAssignableFrom(clazz);
					}
				});

		for (Class clazz : classes) {

			StatePanel statePanel;
			try {
				Constructor constructor = ((Class<StatePanel>) clazz)
						.getConstructor(Boolean.class);

				statePanel = (StatePanel) constructor.newInstance(actor);

				for (State state : statePanel.getStates()) {
					statePanels.put(state, clazz);
				}
			} catch (InstantiationException | IllegalAccessException
					| NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Picks, if the state has changed a new panel. Otherwise it will return
	 * <code>null</code>.
	 * 
	 * @param state
	 * @return
	 */
	public StatePanel pickPanel(State state) {
		if (state != currentState) {
			currentState = state;
			Class<StatePanel> panelClass = statePanels.get(state);

			if (currentPanel == null
					|| !panelClass.equals(currentPanel.getClass())) {
				try {

					Constructor constructor = panelClass
							.getConstructor(Boolean.class);

					StatePanel newPanel = (StatePanel) constructor
							.newInstance(actor);

					currentPanel = newPanel;
					return newPanel;

				} catch (InstantiationException | IllegalAccessException
						| NoSuchMethodException | SecurityException
						| IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return null;
	}

	public StatePanel getCurrentPanel() {
		return currentPanel;
	}

}
