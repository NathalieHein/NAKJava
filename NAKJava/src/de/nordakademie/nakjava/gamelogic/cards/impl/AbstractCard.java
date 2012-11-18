package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ArtifactFactory;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Infrastructure;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;

/**
 * Cards will be defined with annotations. In State enums vs. annotations and
 * naming conventions
 * 
 * @author Kai
 * 
 */
public abstract class AbstractCard {

	private Card annotation;

	protected AbstractCard() {
		annotation = getClass().getAnnotation(Card.class);
	}

	/**
	 * Generated from annotations
	 */
	public final void performActionImpl(Map<Target, PlayerState> states) {
		performAction(states);
		performArtifactEffects(states);
		performDamageEffects(states);
	}

	private final void performArtifactEffects(Map<Target, PlayerState> states) {
		for (ArtifactEffect artifactEffect : annotation.artifactEffects()) {

			InGameSpecificModel model = ((InGameSpecificModel) states.get(
					artifactEffect.target()).getStateSpecificModel());

			for (Artifact artifact : model
					.getTupelsForArtifactType(artifactEffect.artifact())) {
				artifact.merge(ArtifactFactory.createArtifact(artifact
						.getClass(), artifactEffect.count()));
			}

		}
	}

	private final void performDamageEffects(Map<Target, PlayerState> states) {
		for (DamageEffect damageEffect : annotation.damageEffects()) {
			int damageToDeal = damageEffect.count();
			Target target = damageEffect.target();

			performOneDamage(states, damageToDeal, target);
		}
	}

	protected void performOneDamage(Map<Target, PlayerState> states,
			int damageToDeal, Target target) {
		List<Infrastructure> infrastructure = ((InGameSpecificModel) states
				.get(target).getStateSpecificModel())
				.getTupelsForArtifactType(Infrastructure.class);

		Collections.sort(infrastructure);

		int infrastructurePointer = infrastructure.size() - 1;

		while (damageToDeal > 0 && infrastructurePointer >= 0) {
			Infrastructure building = infrastructure.get(infrastructurePointer);

			if (building.getCount() >= damageToDeal) {
				building.setCount(building.getCount() - damageToDeal);
				damageToDeal = 0;
			} else {
				damageToDeal -= building.getCount();
				building.setCount(0);
			}

			infrastructurePointer--;
		}
	}

	/**
	 * May be overridden for additional Actions
	 */
	protected void performAction(Map<Target, PlayerState> states) {

	}

	/**
	 * Checks, whether a card could be played or not. Generated from annotations
	 * 
	 * @return
	 */
	public final boolean checkPrerequirementsImpl(
			Map<Target, PlayerState> states) {
		boolean result = true;

		for (Cost cost : annotation.costs()) {
			result = result
					&& ((InGameSpecificModel) states.get(Target.SELF)
							.getStateSpecificModel()).getTupelForClass(
							cost.ressource()).getCount() >= cost.amount();
		}

		return result && checkPrerequirements(states);
	}

	/**
	 * May be overridden for additional actions
	 * 
	 * @return
	 */
	protected boolean checkPrerequirements(Map<Target, PlayerState> states) {
		return true;
	}

	/**
	 * Pay for playing a card
	 */
	public void payImpl(Map<Target, PlayerState> states) {
		for (Cost cost : annotation.costs()) {
			((InGameSpecificModel) states.get(Target.SELF)
					.getStateSpecificModel())
					.getTupelForClass(cost.ressource()).merge(
							ArtifactFactory.createArtifact(cost.ressource(),
									-cost.amount()));
		}
	}

	public int getTotalCosts() {
		int result = 0;

		for (Cost cost : annotation.costs()) {
			result += cost.amount();
		}

		return result;
	}

	public boolean canDrop() {
		return annotation.canDrop();
	}
}
