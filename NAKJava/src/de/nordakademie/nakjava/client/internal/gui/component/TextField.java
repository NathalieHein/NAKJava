package de.nordakademie.nakjava.client.internal.gui.component;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextField;

import de.nordakademie.nakjava.client.internal.gui.ActionContextHolder;
import de.nordakademie.nakjava.client.internal.gui.ActionContextSelector;
import de.nordakademie.nakjava.server.shared.proxy.actions.KeyAction;
import de.nordakademie.nakjava.server.shared.serial.ActionContext;

public class TextField extends JTextField implements ActionContextHolder {

	public TextField() {
		actionMap = new HashMap<>();
		this.setEditable(true);
		// setInputMap(WHEN_IN_FOCUSED_WINDOW, null);
		// setInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, null);
		// ComponentInputMap inputMap = new ComponentInputMap(this);
		// inputMap.clear();
		// inputMap.put(KeyStroke.getKeyStroke('a'), "b");
		// setInputMap(WHEN_FOCUSED, inputMap);
	}

	public Map<Integer, KeyAction> actionMap;

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isActionContextApplicable(ActionContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void noActionContextAvailable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActionContextSelector(ActionContextSelector selector) {
		// TODO Auto-generated method stub

	}

	@Override
	public void revokeActionContext(long batch) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");

		frame.setLayout(new BorderLayout());
		frame.add(new TextField());

		frame.setVisible(true);
	}
}
