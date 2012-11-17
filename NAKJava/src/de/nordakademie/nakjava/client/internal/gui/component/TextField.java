package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.server.internal.VisibleModelField;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class TextField extends JTextField implements ActionContextHolder,
		ValueHolder {

	private ComponentInputMap inputMap;
	private ActionMap actionMap;

	private Class<? extends KeyAction> desiredAction;

	private long currentBatch = Long.MIN_VALUE;
	private VisibleModelField<String> value;

	public TextField(Class<? extends KeyAction> desiredAction,
			VisibleModelField<String> desiredValue, boolean actor) {
		this.desiredAction = desiredAction;
		this.value = desiredValue;
		this.setEditable(false);
		setInputMap(WHEN_IN_FOCUSED_WINDOW, null);
		setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, null);
		inputMap = new ComponentInputMap(this);
		inputMap.clear();

		setInputMap(WHEN_FOCUSED, inputMap);

		actionMap = new ActionMap();
		setActionMap(actionMap);
		this.setPreferredSize(new Dimension(100, 25));
		if (actor) {
			ActionContextDelegator.getInstance().registerActionContextHolder(
					this);
		}
		setHighlighter(null);
	}

	@Override
	public void setActionContext(ActionContext actionContext, long batch) {
		if (!(actionContext instanceof KeyAction)) {
			return;
		}

		KeyAction action = (KeyAction) actionContext;

		if (batch > currentBatch) {
			currentBatch = batch;
			actionMap.clear();
			inputMap.clear();
		} else if (batch < currentBatch) {
			return;
		}

		inputMap.put(KeyStroke.getKeyStroke(action.getKey()), action.getKey());
		actionMap.put(action.getKey(), new ActionAdapter(action));
		setEditable(true);
	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return desiredAction.isAssignableFrom(context.getClass());
	}

	@Override
	public void noActionContextAvailable() {
		actionMap.clear();
		inputMap.clear();
		setEditable(false);
	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		throw new UnsupportedOperationException(
				"ActionContextSelector can not be set here");
	}

	@Override
	public void revokeActionContext(long batch) {
		if (currentBatch <= batch) {
			noActionContextAvailable();
		}
	}

	@Override
	public boolean isDisposed() {
		return this.isShowing();
	}

	private class ActionAdapter implements Action {

		private KeyAction action;

		public ActionAdapter(KeyAction action) {
			this.action = action;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			action.perform();

		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public void putValue(String key, Object value) {
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public void setEnabled(boolean b) {
		}

	}

	@Override
	public void setText(String string) {
		super.setText(string);
		setCaretPosition(string.length());
	}

	@Override
	public void pickValue(Map<String, Object> genericValues) {
		setText(value.getValue(genericValues));
	}

	@Override
	public boolean consumeAction() {
		return true;
	}
}
