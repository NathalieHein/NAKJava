package de.nordakademie.nakjavagenerator.generators;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class VisibleModelFieldsClass {

	private Set<String> imports;
	private List<VisibleModelField> visibleModelFields;

	private static final String PACKAGE = "de.nordakademie.nakjava.generated";

	public VisibleModelFieldsClass() {
		imports = new HashSet<>();
		imports.add("test.genericBeans.VisibleModelField");
		visibleModelFields = new LinkedList<>();
	}

	public String getSourceContent() {
		StringBuilder content = new StringBuilder();

		content.append("package " + PACKAGE + ";\n");

		for (String importt : imports) {
			content.append("import " + importt + ";\n");
		}
		content.append("\n");
		content.append("public class VisibleModelFields {\n\n");

		for (VisibleModelField field : visibleModelFields) {
			content.append("\tpublic static final VisibleModelField<"
					+ field.getType() + "> " + field.getName()
					+ " = new VisibleModelField<>(\"" + field.getIdentifier()
					+ "\");\n");
		}

		content.append("\n}");

		return content.toString();
	}

	public void addField(VisibleModelField field) {
		visibleModelFields.add(field);
		imports.add(field.getRawType());
	}
}
