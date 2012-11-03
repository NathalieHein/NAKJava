package de.nordakademie.nakjava.client.game.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Steinbruch;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Verlies;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;

public class ArtifactInfoPanel extends JPanel {
	public ArtifactInfoPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void setArtifacts(final List<Artifact> artifacts) {

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					ArtifactInfoPanel.this.removeAll();
					Map<Class<? extends Artifact>, List<Artifact>> sortedByArtifacts = splitListToMap(artifacts);

					for (Entry<Class<? extends Artifact>, List<Artifact>> entry : sortedByArtifacts
							.entrySet()) {
						ArtifactInfoPanel.this.add(new ArtifactTypeInfoPanel(
								entry.getValue()));
					}

					ArtifactInfoPanel.this.revalidate();

				}
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ArtifactInfoPanel infoPanel = new ArtifactInfoPanel();
		frame.getContentPane().add(infoPanel);

		frame.setSize(new Dimension(800, 600));
		frame.setVisible(true);

		List<Artifact> tupels = new LinkedList<>();
		tupels.add(ArtifactFactory.createArtifact(Ziegel.class));
		tupels.add(ArtifactFactory.createArtifact(Monster.class));
		tupels.add(ArtifactFactory.createArtifact(Turm.class));

		infoPanel.setArtifacts(tupels);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tupels.add(ArtifactFactory.createArtifact(Mauer.class));
		tupels.add(ArtifactFactory.createArtifact(Steinbruch.class));
		tupels.add(ArtifactFactory.createArtifact(Verlies.class));

		infoPanel.setArtifacts(tupels);
	}
}