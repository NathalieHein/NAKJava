package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.Label;
import de.nordakademie.nakjava.client.internal.gui.component.Panel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.internal.VisibleModelField.ClientFieldTransformer;

public class PlayerPanel extends Panel {

	public PlayerPanel() {
		this.setLayout(new BorderLayout());

		ArtifactInfoPanel selfArtifactsPanel = new ArtifactInfoPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_SELF);
		ArtifactInfoPanel opponentArtifactsPanel = new ArtifactInfoPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_OPPONENT);
		CardHandPanel handPanel = new CardHandPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_CARDS_SELF);
		CardHandPanel lastPlayedCard = new CardHandPanel(
				VisibleModelFields.MODEL_LASTPLAYEDCARD_SELF
						.setTransformer(new ClientFieldTransformer<CardInformation, List<CardInformation>>() {

							@Override
							public List<CardInformation> transform(
									CardInformation value) {
								List<CardInformation> result = new LinkedList<>();
								if (value != null) {
									result.add(value);
								}
								return result;
							}

						}), false);
		JLabel stateLabel = new Label("",
				VisibleModelFields.PLAYERSTATE_STATE_SELF, "");
		stateLabel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel selfPanel = new JPanel();
		selfPanel.setLayout(new BorderLayout());

		JPanel opponentPanel = new JPanel();
		opponentPanel.setLayout((new BorderLayout()));

		opponentPanel.add(opponentArtifactsPanel, BorderLayout.CENTER);
		opponentPanel.add(lastPlayedCard, BorderLayout.SOUTH);

		selfPanel.add(selfArtifactsPanel, BorderLayout.NORTH);
		selfPanel.add(handPanel, BorderLayout.CENTER);

		this.add(opponentPanel, BorderLayout.NORTH);
		this.add(selfPanel, BorderLayout.CENTER);
		this.add(stateLabel, BorderLayout.SOUTH);
	}

	// public static void main(String[] args) {
	// JFrame frame = new JFrame();
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//
	// PlayerPanel panel = new PlayerPanel();
	// frame.add(panel);
	//
	// List<CardInformation> cards = new LinkedList<>();
	// cards.add(new CardInformation(
	// "Test1",
	// "tut blub\n tut bläh\n nochmehr bla blub bläh \n auf ein letztes",
	// "5 Holz, 3 Gold, 4 Steine", CardType.SPEZIAL));
	// cards.add(new CardInformation(
	// "Test2",
	// "tut blub\n tut bläh\n nochmehr muh mäh blub bläh \n auf ein letztes",
	// "5 Holz, 3 Gold, 4 Steine", CardType.SPEZIAL));
	//
	// panel.handPanel.setCards(cards);
	//
	// List<Artifact> tupels = new LinkedList<>();
	// tupels.add(ArtifactFactory.createArtifact(Ziegel.class));
	// tupels.add(ArtifactFactory.createArtifact(Monster.class));
	// tupels.add(ArtifactFactory.createArtifact(Turm.class));
	//
	// panel.infoPanel.setArtifacts(tupels);
	//
	// frame.setSize(800, 600);
	//
	// frame.setVisible(true);
	//
	// }

	@Override
	public State[] getStates() {
		return new State[] { State.PLAYCARDSTATE, State.ADJUSTCARDHANDSTATE,
				State.STOP };
	}
}
