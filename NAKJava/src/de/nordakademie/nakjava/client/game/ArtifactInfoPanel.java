package de.nordakademie.nakjava.client.game;

import java.awt.Dimension;
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
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactTupel;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.factories.Factory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Infrastructure;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ressource;

public class ArtifactInfoPanel extends JPanel {
	public ArtifactInfoPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void setArtifacts(final List<ArtifactTupel> artifacts) {

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					ArtifactInfoPanel.this.removeAll();
					Map<Class<? extends Artifact>, List<ArtifactTupel>> sortedByArtifacts = splitListToMap(artifacts);

					for (Entry<Class<? extends Artifact>, List<ArtifactTupel>> entry : sortedByArtifacts
							.entrySet()) {
						ArtifactInfoPanel.this.add(new ArtifactTypeInfoPanel(
								entry.getValue()));
					}

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

	private Map<Class<? extends Artifact>, List<ArtifactTupel>> splitListToMap(
			List<ArtifactTupel> artifacts) {
		Map<Class<? extends Artifact>, List<ArtifactTupel>> sortedByArtifacts = new TreeMap<Class<? extends Artifact>, List<ArtifactTupel>>(
				new Comparator<Class<? extends Artifact>>() {

					@Override
					public int compare(Class<? extends Artifact> o1,
							Class<? extends Artifact> o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});

		for (ArtifactTupel tupel : artifacts) {

			Class<? extends Artifact> artifactClazz = tupel.getArtifact()
					.getDeclaringClass();

			List<ArtifactTupel> tupels = sortedByArtifacts.get(artifactClazz);
			if (tupels == null) {
				tupels = new ArrayList<>();
				sortedByArtifacts.put(artifactClazz, tupels);
			}

			tupels.add(tupel);
		}

		for (List<ArtifactTupel> tupellList : sortedByArtifacts.values()) {
			Collections.sort(tupellList, new Comparator<ArtifactTupel>() {

				@Override
				public int compare(ArtifactTupel o1, ArtifactTupel o2) {
					// This can not be expressed with generics...
					// Those enums are from the same type, but we do not know
					// the type
					return ((Enum) o1.getArtifact()).compareTo(o2.getArtifact());
				}
			});
		}

		return sortedByArtifacts;
	}

	private class ArtifactTypeInfoPanel extends JPanel {

		public ArtifactTypeInfoPanel(List<ArtifactTupel> tupels) {
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

			for (ArtifactTupel tupel : tupels) {
				this.add(new Label(tupel.getArtifact().name() + ": "
						+ tupel.getCount()));
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

		List<ArtifactTupel> tupels = new LinkedList<>();
		tupels.add(new ArtifactTupel(Ressource.ZIEGEL, 15));
		tupels.add(new ArtifactTupel(Ressource.MONSTER, 10));
		tupels.add(new ArtifactTupel(Infrastructure.TURM, 5));
		tupels.add(new ArtifactTupel(Infrastructure.MAUER, 17));
		tupels.add(new ArtifactTupel(Factory.STEINBRUCH, 3));
		tupels.add(new ArtifactTupel(Factory.VERLIES, 4));

		infoPanel.setArtifacts(tupels);
	}
}
