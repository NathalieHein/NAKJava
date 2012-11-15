package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.nordakademie.nakjava.client.internal.gui.component.Label;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardType;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;

public class PlayerPanel extends Panel {

	private ArtifactInfoPanel infoPanel;
	private CardHandPanel handPanel;
	private JLabel stateLabel;

	public PlayerPanel() {
		this.setLayout(new BorderLayout());
		infoPanel = new ArtifactInfoPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_SELF);
		handPanel = new CardHandPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_CARDS_SELF);
		stateLabel = new Label("", VisibleModelFields.PLAYERSTATE_STATE_SELF,
				"");
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.black));

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

	@Override
	public State[] getStates() {
		return new State[] { State.PLAYCARDSTATE, State.ADJUSTCARDHANDSTATE,
				State.STOP };
	}
}
