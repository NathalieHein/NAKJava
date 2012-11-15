package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.server.internal.VisibleModelField;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ComboBox extends JComboBox<String> implements ActionContextHolder,
		ValueHolder {

	private long currentBatch;
	private ActionContextSelector selector;
	private Map<String, SelectAction> actions;
	private boolean listenerActive = false;

	private String currentSelection;
	private VisibleModelField<? extends Object> currentSelectionField;

	public ComboBox(final Class<? extends SelectAction> selectAction,
			VisibleModelField<? extends Object> currentSelectionField) {
		this.currentSelectionField = currentSelectionField;
		currentSelection = "";

		selector = new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				return selectAction.isAssignableFrom(context.getClass());
			}
		};

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (listenerActive) {
					String item = (String) getSelectedItem();

					actions.get(item).perform();

				}

			}
		});

		setEnabled(false);
		actions = new HashMap<String, SelectAction>();
		ActionContextDelegator.getInstance().registerActionContextHolder(this);
	}

	@Override
	public void setSelectedIndex(int anIndex) {
		listenerActive = false;
		super.setSelectedIndex(anIndex);
		currentSelection = (String) getSelectedItem();
		listenerActive = true;
	}

	@Override
	public void setSelectedItem(Object anObject) {
		listenerActive = false;
		currentSelection = (String) anObject;
		if (actions.get(anObject) == null) {
			addItem((String) anObject);
		}
		super.setSelectedItem(anObject);
		listenerActive = true;
	}

	@Override
	public void setActionContext(ActionContext actionContext, long batch) {
		listenerActive = false;
		if (!(actionContext instanceof SelectAction)) {
			return;
		}

		SelectAction action = (SelectAction) actionContext;

		if (batch > currentBatch) {
			currentBatch = batch;
			actions.clear();
			removeAllItems();
		} else if (batch < currentBatch) {
			return;
		}

		actions.put(action.getValue(), action);
		addItem(action.getValue());
		if (action.getValue().equals(currentSelection)) {
			setSelectedItem(currentSelection);
		}
		listenerActive = true;
		setEnabled(true);

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return selector.select(context);
	}

	@Override
	public void noActionContextAvailable() {
		listenerActive = false;
		setEnabled(false);
		listenerActive = true;

	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		this.selector = selector;
	}

	@Override
	public void revokeActionContext(long batch) {
		if (batch >= currentBatch) {
			noActionContextAvailable();
		}
	}

	@Override
	public boolean isDisposed() {
		return this.isShowing();
	}

	@Override
	public void pickValue(Map<String, Object> genericValues) {
		listenerActive = false;
		Object value = currentSelectionField.getValue(genericValues);
		if (value != null) {
			setSelectedItem(currentSelectionField.getValue(genericValues)
					.toString());
		}
		listenerActive = true;
	}

}
