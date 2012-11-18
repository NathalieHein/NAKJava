package de.nordakademie.nakjava.client.internal.gui.component;

import java.util.Map;

import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.server.internal.VisibleModelField;

public class Label extends JLabel implements ValueHolder {

	private String before;
	private VisibleModelField<? extends Object> field;
	private String after;

	public Label(String before, VisibleModelField<? extends Object> field,
			String after) {
		super();
		this.before = before;
		this.field = field;
		this.after = after;
	}

	@Override
	public void pickValue(Map<String, Object> genericValues) {
		Object value = field.getValue(genericValues);

		if (value != null) {
			setText(before + value.toString() + after);
		}
	}

}
