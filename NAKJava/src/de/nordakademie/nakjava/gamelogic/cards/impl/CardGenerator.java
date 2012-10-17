package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

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

		List<String> packages = Arrays
				.asList((System.getProperty(
						"de.nordakademie.nakjava.cardPackages", "") + "de.nordakademie.nakjava.gamelogic.cards")
						.split(","));

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		for (String pack : packages) {
			URL resource = classLoader.getResource(pack.replace(".", "/"));
			File directory = null;

			try {
				directory = new File(resource.toURI());
			} catch (URISyntaxException e) {
				throw new RuntimeException(
						"Could not load file from packagename.");
			}

			String[] classNames = directory.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});

			for (String className : classNames) {
				Class<?> clazz;

				try {
					clazz = Class.forName(pack + "."
							+ className.substring(0, className.length() - 6));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Class could not be loaded.");
				}

				Card annotation = clazz.getAnnotation(Card.class);

				if (annotation != null
						&& AbstractCard.class.isAssignableFrom(clazz)) {

					String cardCostDescription = generateCostDescription(annotation
							.costs());
					String cardDescription = generateBasicEffectDescription(
							annotation.self(), annotation.enemy());
					if (!cardDescription.equals("")
							&& !annotation.additionalDescription().equals("")) {
						cardDescription += "/"
								+ annotation.additionalDescription();
					} else {
						cardDescription += annotation.additionalDescription();
					}

					library.getCardInformation().put(
							annotation.name(),
							new CardInformation(annotation.name(),
									cardDescription, cardCostDescription,
									annotation.type()));
				}

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

				sb.append(cost.amount() + " " + cost.ressource());
			}

			return sb.toString();
		}
	}

	private static String generateBasicEffectDescription(Effect self,
			Effect enemy) {
		return "";
	}

	public static void main(String[] args) {
		generateCards();
	}
}
