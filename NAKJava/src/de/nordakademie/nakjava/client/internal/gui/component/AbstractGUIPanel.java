package de.nordakademie.nakjava.client.internal.gui.component;

import javax.swing.JPanel;

public abstract class AbstractGUIPanel extends JPanel {

	protected boolean actor = false;

	public void setActor(boolean actor) {
		this.actor = actor;
	}
}
