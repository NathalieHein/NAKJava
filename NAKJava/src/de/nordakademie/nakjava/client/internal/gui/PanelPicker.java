package de.nordakademie.nakjava.client.internal.gui;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class PanelPicker {

	private Map<State, Class<Panel>> panels;
	private State currentState = null;
	private Panel currentPanel;

	public PanelPicker() {
		panels = new EnumMap<State, Class<Panel>>(State.class);
		List<Class<?>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.client.game.gui", "",
				new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class clazz) {
						return Panel.class.isAssignableFrom(clazz);
					}
				});

		for (Class clazz : classes) {

			Panel panel;
			try {
				panel = ((Class<Panel>) clazz).newInstance();

				for (State state : panel.getStates()) {
					panels.put(state, clazz);
				}
			} catch (InstantiationException | IllegalAccessException e) {
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
	public Panel pickPanel(State state) {
		if (state != currentState) {
			currentState = state;
			Class<Panel> panelClass = panels.get(state);

			if (currentPanel == null
					|| !panelClass.equals(currentPanel.getClass())) {
				try {

					Panel newPanel = panelClass.newInstance();
					currentPanel = newPanel;
					return newPanel;

				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		return null;
	}

	public Panel getCurrentPanel() {
		return currentPanel;
	}

}
