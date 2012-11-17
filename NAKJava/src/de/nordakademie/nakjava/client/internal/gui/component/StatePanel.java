package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.Component;
import java.awt.Container;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.server.shared.serial.PlayerModel;

public abstract class StatePanel extends AbstractGUIPanel {

	public StatePanel(boolean actor) {
		setActor(actor);
	}

	public abstract State[] getStates();

	/**
	 * This method is called on the actual panel by the client to set a
	 * playermodel. Its genericTransfer Map will be selected and passed to each
	 * containing value holder.
	 * 
	 * @param model
	 */
	public final void setModel(PlayerModel model) {
		setModelImpl(model.getGenericTransfer());
		for (ValueHolder holder : getAllComponentsOfType(ValueHolder.class,
				this)) {
			holder.pickValue(model.getGenericTransfer());
		}
	}

	/**
	 * To add additional behaviour clients may override this method.
	 * 
	 * @param values
	 */
	protected void setModelImpl(Map<String, Object> values) {
	}

	private <T> List<T> getAllComponentsOfType(Class<T> clazz,
			Container container) {
		List<T> components = new LinkedList<>();
		for (Component comp : container.getComponents()) {
			if (clazz.isAssignableFrom(comp.getClass())) {
				components.add((T) comp);
			} else if (comp instanceof Container) {
				components.addAll(getAllComponentsOfType(clazz,
						(Container) comp));
			}
		}

		return components;
	}

}
