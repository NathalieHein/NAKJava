package de.nordakademie.nakjava.gamelogic.shared.ressources;

public enum RessourceFactory {

	STEINBRUCH(Ressource.ZIEGEL, 1), ZAUBERLABOR(Ressource.KRISTALLE, 1), VERLIES(
			Ressource.MONSTER, 1);

	private Ressource ressource;
	private int amount;

	RessourceFactory(Ressource ressource, int amount) {
		this.ressource = ressource;
		this.amount = amount;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public int getAmount() {
		return amount;
	}

}
