package test.functional;

public class Example {

	public void exampleMethod() {

		Function<String, Integer> function = new Function<String, Integer>() {

			@Override
			public Integer transformWithChange(String input) {
				// Buchstaben zählen
				return input.length();
			}

			@Override
			public String transform(String input) {
				// Verdoppeln: "abc" -> "abcabc"
				return input.concat(input);
			}
		};

		// Nun kann das "Funktionsobjekt" ganz normal übergeben werden:
		functionalFunction1("hallo", function); // "hallohallo"

		functionalFunction2("welt", function); // 4

	}

	/**
	 * Wir erwarten hier einen String als input, sowie eine Function mit einem
	 * Generic, gesetzt auf String, als Paramater
	 * 
	 * @param input
	 * @param function
	 * @return
	 */
	public String functionalFunction1(String input, Function<String, ?> function) {
		return function.transform(input);
	}

	/**
	 * Wir erwarten hier ebenfalls eine Function mit zwei gesetzten Generics als
	 * Parameter, String und Integer. --> Was diese Generics genau tun, ist in
	 * der Signatur nicht gefordert!
	 * 
	 * @param input
	 * @param function
	 * @return
	 */
	public int functionalFunction2(String input,
			Function<String, Integer> function) {
		return function.transformWithChange(input);
	}
}
