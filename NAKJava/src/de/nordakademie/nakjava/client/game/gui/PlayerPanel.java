package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;

public class PlayerPanel extends JPanel {

	private ArtifactInfoPanel infoPanel;
	private CardHandPanel handPanel;
	private JLabel stateLabel;

	public PlayerPanel() {
		this.setLayout(new BorderLayout());
		infoPanel = new ArtifactInfoPanel();
		handPanel = new CardHandPanel();
		stateLabel = new JLabel();
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		stateLabel.setText("Initialisiere...");

		this.add(infoPanel, BorderLayout.NORTH);
		this.add(handPanel, BorderLayout.CENTER);
		this.add(stateLabel, BorderLayout.SOUTH);
	}

	public void updatePanel(PlayerState state) {

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PlayerPanel panel = new PlayerPanel();
		frame.add(panel);

		List<CardInformation> cards = new LinkedList<>();
		cards.add(new CardInformation(
				"Test1",
				"tut blub\n tut bläh\n nochmehr bla blub bläh \n auf ein letztes",
				"5 Holz, 3 Gold, 4 Steine", CardType.SPEZIAL));
		cards.add(new CardInformation(
				"Test2",
				"tut blub\n tut bläh\n nochmehr muh mäh blub bläh \n auf ein letztes",
				"5 Holz, 3 Gold, 4 Steine", CardType.SPEZIAL));

		panel.handPanel.setCards(cards);

		List<Artifact> tupels = new LinkedList<>();
		tupels.add(ArtifactFactory.createArtifact(Ziegel.class));
		tupels.add(ArtifactFactory.createArtifact(Monster.class));
		tupels.add(ArtifactFactory.createArtifact(Turm.class));

		panel.infoPanel.setArtifacts(tupels);

		frame.setSize(800, 600);

		frame.setVisible(true);

	}
}
