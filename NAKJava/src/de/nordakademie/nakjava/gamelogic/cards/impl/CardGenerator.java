package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

/**
 * Looks for cards in classpath, instantiates them and generates
 * {@link CardInformation}.
 * 
 * @author Kai
 * 
 */
public class CardGenerator {

	/**
	 * Loads all cards with {@link CardInformation} annotation. For performance
	 * reasons just the cards package will be scanned. The class must be loaded
	 * for introspection :-/. Normally we would use javassist...
	 * 
	 * Additional packages may be given by property:
	 * de.nordakademie.nakjava.cardPackages
	 */
	public static void generateCards() {
		CardLibrary library = new CardLibrary();

		List<Class<?>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.gamelogic.cards",
				"de.nordakademie.nakjava.cardPackages", new ClassAcceptor() {

					@Override
					public boolean acceptClass(Class<?> clazz) {
						Card annotation = clazz.getAnnotation(Card.class);
						return annotation != null
								&& AbstractCard.class.isAssignableFrom(clazz);
					}
				});

		for (Class<?> clazz : classes) {
			Card annotation = clazz.getAnnotation(Card.class);

			String cardCostDescription = generateCostDescription(annotation
					.costs());
			String cardDescription = generateBasicEffectDescription(
					annotation.artifactEffects(), annotation.damageEffects());
			if (!cardDescription.equals("")
					&& !annotation.additionalDescription().equals("")) {
				cardDescription += "/" + annotation.additionalDescription();
			} else {
				cardDescription += annotation.additionalDescription();
			}

			library.getCardInformation().put(
					annotation.name(),
					new CardInformation(annotation.name(), cardDescription,
							cardCostDescription, annotation.type()));
		}

	}

	private static String generateCostDescription(Cost[] costs) {
		if (costs.length == 0) {
			return "keine";
		} else {
			StringBuilder sb = new StringBuilder();

			boolean firstRun = true;
			for (Cost cost : costs) {
				if (firstRun) {
					firstRun = false;
				} else {
					sb.append("/");
				}

				sb.append(cost.amount() + " " + cost.ressource());
			}

			return sb.toString();
		}
	}

	private static String generateBasicEffectDescription(
			ArtifactEffect[] artifactEffect, DamageEffect[] damage) {
		return "";
	}

	public static void main(String[] args) {
		generateCards();
	}
}
