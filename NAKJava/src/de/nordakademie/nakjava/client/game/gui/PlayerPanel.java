package de.nordakademie.nakjava.client.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.nordakademie.nakjava.client.internal.gui.component.Label;
import de.nordakademie.nakjava.client.internal.gui.component.StatePanel;
import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.states.State;
import de.nordakademie.nakjava.generated.VisibleModelFields;
import de.nordakademie.nakjava.server.internal.VisibleModelField.ClientFieldTransformer;

public class PlayerPanel extends StatePanel {

	public PlayerPanel(Boolean actor) {
		super(actor);
		this.setLayout(new BorderLayout());

		ArtifactInfoPanel selfArtifactsPanel = new ArtifactInfoPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_SELF);
		ArtifactInfoPanel opponentArtifactsPanel = new ArtifactInfoPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_ARTIFACTS_OPPONENT);
		CardHandPanel handPanel = new CardHandPanel(
				VisibleModelFields.INGAMESPECIFICMODEL_CARDS_SELF, actor);
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

	@Override
	public State[] getStates() {
		return new State[] { State.PLAYCARDSTATE, State.ADJUSTCARDHANDSTATE,
				State.STOP };
	}
}
