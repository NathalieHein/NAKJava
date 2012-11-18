package de.nordakademie.nakjava.gamelogic.cards.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.nordakademie.nakjava.gamelogic.shared.cards.CardInformation;

/**
 * Looks for metatext attributes in effects and parses them according to the
 * following rules: {...} denotes a type of effect. type.method invokes that a
 * method on the type.
 */
public class AnnotationMetatextProcessor {

	/**
	 * This method is automatically called by the card generator to add text
	 * information to {@link CardInformation} objects of geneneric effects.
	 * 
	 * @param annotation
	 * @return
	 */
	public static String extractMetatext(Object annotation) {
		Class<?> clazz = annotation.getClass();

		Field metaTextField = null;
		try {
			metaTextField = clazz.getField("metaText");
		} catch (NoSuchFieldException | SecurityException e1) {
			return "";
		}

		List<String> toReplace = new LinkedList<>();
		String metaText = null;

		try {
			metaText = (String) metaTextField.get(new String());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return "";
		}

		Pattern pattern = Pattern.compile("\\{.[^\\{]*\\}");
		Matcher matcher = pattern.matcher(metaText);

		while (matcher.find()) {
			toReplace.add(matcher.group());
		}

		for (String token : toReplace) {
			String tokenContent = token.substring(1, token.length() - 1);
			String[] subTokens;
			subTokens = tokenContent.split("\\.");

			Object tempObject = null;
			try {
				tempObject = clazz.getMethod(subTokens[0], new Class<?>[0])
						.invoke(annotation, new Object[0]);

				if (subTokens.length > 1) {
					for (int i = 1; i < subTokens.length; i++) {
						Class<?> tempObjectClass = tempObject.getClass();
						tempObject = tempObjectClass.getMethod(subTokens[i],
								new Class<?>[0]).invoke(tempObject,
								new Object[0]);
					}
				}
			} catch (NoSuchMethodException | SecurityException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				return metaText;
			}

			metaText = metaText.replace(token, tempObject.toString());

		}

		return metaText;
	}

}
