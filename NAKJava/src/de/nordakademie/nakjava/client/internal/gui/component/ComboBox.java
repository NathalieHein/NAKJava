package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import de.nordakademie.nakjava.client.internal.gui.ActionContextDelegator;
import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.proxy.actions.SelectAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class ComboBox extends JComboBox<String> implements ActionContextHolder {

	private long currentBatch;
	private ActionContextSelector selector;
	private Map<String, SelectAction> actions;

	public ComboBox(final Class<? extends SelectAction> selectAction) {
		selector = new ActionContextSelector() {

			@Override
			public boolean select(ActionContext context) {
				return selectAction.isAssignableFrom(context.getClass());
			}
		};

		addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String item = (String) getSelectedItem();
				try {
					actions.get(item).perform();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setEnabled(false);
		actions = new HashMap<String, SelectAction>();
		ActionContextDelegator.getInstance().registerActionContextHolder(this);
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		if (!(actionContext instanceof SelectAction)) {
			return;
		}

		SelectAction action = (SelectAction) actionContext;

		if (actionContext.getBatch() > currentBatch) {
			currentBatch = actionContext.getBatch();
			actions.clear();
			removeAllItems();
		} else if (actionContext.getBatch() < currentBatch) {
			return;
		}

		actions.put(action.getValue(), action);
		setEnabled(true);

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		return selector.select(context);
	}

	@Override
	public void noActionContextAvailable() {
		actions.clear();
		removeAllItems();
		setEnabled(false);

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

}
