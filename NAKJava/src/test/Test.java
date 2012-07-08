package test;

import java.awt.event.KeyEvent;

public class Test {

	public static void main(String[] args) {
		for (int i = KeyEvent.VK_A; i <= KeyEvent.VK_Z; i++) {
			System.out.println(KeyEvent.getKeyText(i));
		}
	}
}
