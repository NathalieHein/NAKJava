package gamelocgicOO.cards.condition;

/**
 * Somit kann eine direkte Angabe des Typs, quasi als Parameter erfolgen.
 * 
 * @author Kai
 * 
 * @param <T>
 */
public interface Function<T, U> {

	/**
	 * Diese Funktion transformiert einen gegebenen Wert in ggf. einen anderen
	 * desselben Typs.
	 * 
	 * @param input
	 * @return
	 */
	public T transform(T input);

	/**
	 * Kann eine Umwandlung des Typs vornehmen
	 * 
	 * @param input
	 * @return
	 */
	public U transformWithChange(T input);
}
