package de.nordakademie.nakjava.client.game.gui;

import java.awt.FlowLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.ValueHolder;
import de.nordakademie.nakjava.client.internal.gui.component.AbstractGUIPanel;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.server.internal.VisibleModelField;

public class ArtifactInfoPanel extends AbstractGUIPanel implements ValueHolder {

	private VisibleModelField<List<Artifact>> artifactsField;

	public ArtifactInfoPanel(VisibleModelField<List<Artifact>> artifactsField) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.artifactsField = artifactsField;
	}

	public void setArtifacts(final List<Artifact> artifacts) {

		ArtifactInfoPanel.this.removeAll();
		Map<Class<? extends Artifact>, List<Artifact>> sortedByArtifacts = splitListToMap(artifacts);

		for (Entry<Class<? extends Artifact>, List<Artifact>> entry : sortedByArtifacts
				.entrySet()) {
			ArtifactInfoPanel.this.add(new ArtifactTypeInfoPanel(entry
					.getValue()));
		}

		ArtifactInfoPanel.this.revalidate();

	}

	private Map<Class<? extends Artifact>, List<Artifact>> splitListToMap(
			List<Artifact> artifacts) {
		Map<Class<? extends Artifact>, List<Artifact>> sortedByArtifacts = new TreeMap<Class<? extends Artifact>, List<Artifact>>(
				new Comparator<Class<? extends Artifact>>() {

					@Override
					public int compare(Class<? extends Artifact> o1,
							Class<? extends Artifact> o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

		for (Artifact artifact : artifacts) {

			Class<? extends Artifact> artifactClazz = (Class<? extends Artifact>) artifact
					.getClass().getSuperclass();

			List<Artifact> tupels = sortedByArtifacts.get(artifactClazz);
			if (tupels == null) {
				tupels = new ArrayList<>();
				sortedByArtifacts.put(artifactClazz, tupels);
			}

			tupels.add(artifact);
		}

		for (List<Artifact> artifactList : sortedByArtifacts.values()) {
			Collections.sort(artifactList, new Comparator<Artifact>() {

				@Override
				public int compare(Artifact o1, Artifact o2) {
					// This can not be expressed with generics...
					// Those enums are from the same type, but we do not know
					// the type
					return (o1.getClass().getName().compareTo(o2.getClass()
							.getName()));
				}
			});
		}

		return sortedByArtifacts;
	}

	private class ArtifactTypeInfoPanel extends JPanel {

		public ArtifactTypeInfoPanel(List<Artifact> artifacts) {
			this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

			for (Artifact artifact : artifacts) {
				this.add(new Label(artifact.getClass().getSimpleName() + ": "
						+ artifact.getCount()));
			}
		}

	}

	@Override
	public void pickValue(Map<String, Object> genericValues) {
		setArtifacts(artifactsField.getValue(genericValues));
	}

}
