package de.nordakademie.nakjava.server.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import de.nordakademie.nakjava.server.internal.Player;

public abstract class DeckPersister {

	public static final String FOLDER = "persistence";

	public static void saveDeck(Deck deck, Player player) {
		File file = new File(FOLDER + "/" + player.getName() + "_decks");

		if (!file.exists()) {

			ObjectOutputStream oos = null;
			try {
				file.createNewFile();
				oos = new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));

				oos.writeObject(new LinkedList<Deck>());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					oos.close();
				} catch (Exception e) {
				}
			}

			List<Deck> decks = getDecks(player);
			decks.add(deck);

			try {
				oos = new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
				oos.writeObject(decks);
				try {
					oos.close();
				} catch (Exception e) {
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static List<Deck> getDecks(Player player) {
		File file = new File(FOLDER + "/" + player.getName() + "_decks");

		List<Deck> result = new LinkedList<>();

		if (file.exists()) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(file)));
				result = (List<Deck>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} catch (Exception ex) {
				}
			}
		}

		return result;
	}
}