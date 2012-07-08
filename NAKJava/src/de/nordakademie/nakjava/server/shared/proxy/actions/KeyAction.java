package de.nordakademie.nakjava.server.shared.proxy.actions;

import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import de.nordakademie.nakjava.server.internal.Model;
import de.nordakademie.nakjava.server.shared.proxy.ActionAbstractImpl;

public class KeyAction extends ActionAbstractImpl {

	// TODO
	// Proxies k�nnen lediglich ein Interface ver�ffentlichen. Das macht:
	// 1. einen Typecast unm�glich
	// 2. Zugriff auf evtl. komplexere Inhalte unm�glich

	// Alternativen dazu
	// - Serializable r�berschicken, welches inhalt und
	// "perform Proxy beinhaltet"
	// (Vorteil ist, dass gecastet werden kann)
	// - proxy mit zwei Methoden ausstatten: 1. ID (String, gibt den "Typ" an)
	// 2. getContent (liefert ein Object zur�ck, welches gecastet werden kann.)
	// (Nachteil, l�uft auf Server, vorteil: es wird nur �bertragen, was auch
	// angeschaut wird)
	private int key;

	public KeyAction(int key) throws RemoteException {
		super();
		this.key = key;
	}

	@Override
	protected void performImpl(Model model) {
		model.setName(model.getName() + KeyEvent.getKeyText(key));

	}

	public int getKey() {
		return key;
	}

}
