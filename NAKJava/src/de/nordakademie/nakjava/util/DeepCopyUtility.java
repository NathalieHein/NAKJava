package de.nordakademie.nakjava.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCopyUtility {
	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T toBeCopied) throws Exception {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		new ObjectOutputStream(output).writeObject(toBeCopied);
		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
		Object copy = new ObjectInputStream(input).readObject();
		return (T) copy;
	}

}
