package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;
import de.nordakademie.nakjava.util.StringUtilities;
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

		List<Class<AbstractCard>> classes = ClasspathScanner.findClasses(
				"de.nordakademie.nakjava.gamelogic.cards",
				"de.nordakademie.nakjava.cardPackages",
				new ClassAcceptor<AbstractCard>() {

					@Override
					public boolean acceptClass(Class<AbstractCard> clazz) {
						Card annotation = clazz.getAnnotation(Card.class);
						return annotation != null;

					}
				}, AbstractCard.class);

		for (Class<?> clazz : classes) {
			Card annotation = clazz.getAnnotation(Card.class);

			String cardCostDescription = generateCostDescription(annotation
					.costs());
			String cardDescription = generateBasicEffectDescription(annotation
					.artifactEffects(), annotation.damageEffects());
			if (!annotation.additionalDescription().equals("")) {
				cardDescription += annotation.additionalDescription() + "/";
			}
			if (!annotation.canDrop()) {
				cardDescription += "Kann nicht verworfen werden.";
			}

			library.getCardInformation().put(
					annotation.name(),
					new CardInformation(annotation.name(), cardDescription,
							cardCostDescription, annotation.type()));

			try {
				library.getCards().put(annotation.name(),
						(AbstractCard) clazz.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
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

				sb.append(cost.amount() + " "
						+ cost.ressource().getSimpleName());
			}

			return sb.toString();
		}
	}

	private static String generateBasicEffectDescription(
			ArtifactEffect[] artifactEffects, DamageEffect[] damages) {
		StringBuilder sb = new StringBuilder();
		for (ArtifactEffect artifactEffect : artifactEffects) {
			String text = AnnotationMetatextProcessor
					.extractMetatext(artifactEffect);

			if (StringUtilities.isNotNullOrEmpty(text)) {
				sb.append(text);
				sb.append("/");
			}
		}

		return sb.toString();
	}
}
