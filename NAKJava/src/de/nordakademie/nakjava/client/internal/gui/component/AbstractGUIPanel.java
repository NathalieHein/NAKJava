package de.nordakademie.nakjava.client.internal.gui.component;

import javax.swing.JPanel;

/**
 * Base class for panels which can contain action elements. These action
 * elements should be able to be turned on and off
 * 
 */
public abstract class AbstractGUIPanel extends JPanel {

	protected boolean actor = false;

	/**
	 * Sets whether this panel is an actor (GUIClient) or not (BotClient)
	 * 
	 * @param actor
	 */
	public void setActor(boolean actor) {
		this.actor = actor;
	}
}
