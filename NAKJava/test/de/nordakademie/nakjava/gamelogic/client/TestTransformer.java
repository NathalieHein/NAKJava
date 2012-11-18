package de.nordakademie.nakjava.gamelogic.client;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.nordakademie.nakjava.client.bot.genericbot.transformers.ArtifactCheckTransformer;
import de.nordakademie.nakjava.client.bot.genericbot.transformers.WinCheckMeasurement;
import de.nordakademie.nakjava.gamelogic.cards.impl.Target;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.Artifact;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Mauer;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.infrastructure.Turm;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Kristalle;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Monster;
import de.nordakademie.nakjava.gamelogic.shared.artifacts.ressources.Ziegel;
import de.nordakademie.nakjava.gamelogic.shared.playerstate.PlayerState;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinCheck;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategies;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategyInformation;
import de.nordakademie.nakjava.server.internal.model.InGameSpecificModel;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

public class TestTransformer {

	ArtifactCheckTransformer transformer;
	WinStrategyInformation turmbauInfo;

	Map<Target, List<? extends Artifact>> model;

	Turm turm;

	@Before
	public void init() {
		ClasspathScanner.lookupAnnotatedScanners();

		transformer = new ArtifactCheckTransformer();
		turmbauInfo = WinStrategies.getInstance()
				.getStrategyInformationForName("Turmbau");

		model = new EnumMap<>(Target.class);

		List<Artifact> initialArtifacts = new LinkedList<>();
		Ziegel ziegel = new Ziegel();
		ziegel.setCount(15);

		Kristalle kristalle = new Kristalle();
		kristalle.setCount(25);

		Monster monster = new Monster();
		monster.setCount(10);

		Mauer mauer = new Mauer();
		mauer.setCount(3);

		turm = new Turm();
		turm.setCount(100);

		initialArtifacts.add(ziegel);
		initialArtifacts.add(kristalle);
		initialArtifacts.add(monster);
		initialArtifacts.add(mauer);
		initialArtifacts.add(turm);

		PlayerState state = new PlayerState();
		state.setStateSpecificModel(new InGameSpecificModel(initialArtifacts));

		model.put(Target.SELF, initialArtifacts);
		model.put(Target.OPPONENT, initialArtifacts);
	}

	@Test
	public void testArtifactTransformer() {
		List<WinCheckMeasurement> measurements = new LinkedList<>();
		for (WinCheck check : turmbauInfo.getWinChecks()) {
			measurements.add(transformer.transform(check));
		}

		Assert.assertEquals(true, !measurements.isEmpty());

		WinCheckMeasurement measure = measurements.get(0);
		Assert.assertEquals(0.0, measure.measure(model));

		turm.setCount(50);
		Assert.assertTrue(Math.abs(0.5 - measure.measure(model)) < 0.01);

	}
}
