package de.nordakademie.nakjavagenerator.generators;

public class VisibleModelField {

	private String type;
	private String name;
	private String identifier;

	public VisibleModelField(String type, String name, String identifier) {
		super();
		this.type = autoBoxing(type);
		this.name = name;
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public String getRawType() {
		return removeGenerics(removeArray(type));
	}

	public String getName() {
		return name;
	}

	public String getIdentifier() {
		return identifier;
	}

	private String autoBoxing(String inputType) {

		String tempType = removeGenerics(removeArray(inputType));
		String ending = inputType.replace(tempType, "");

		switch (tempType) {
		case "int":
			tempType = "java.lang.Integer";
			break;
		case "boolean":
			tempType = "java.lang.Boolean";
			break;
		case "byte":
			tempType = "java.lang.Byte";
			break;
		case "short":
			tempType = "java.lang.Short";
			break;
		case "long":
			tempType = "java.lang.Long";
			break;
		case "float":
			tempType = "java.lang.float";
			break;
		case "double":
			tempType = "java.lang.Double";
			break;
		case "char":
			tempType = "java.lang.Char";
			break;

		default:
			break;
		}

		return tempType += ending;

	}

	private String removeArray(String input) {
		return input.replaceAll("\\[.*\\]", "");
	}

	private String removeGenerics(String input) {
		return input.replaceAll("\\<.*\\>", "");
	}

}
