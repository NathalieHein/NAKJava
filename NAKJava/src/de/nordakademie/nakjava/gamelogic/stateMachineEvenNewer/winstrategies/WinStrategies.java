package de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.WinStrategy;
import de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.ArtifactChecker;
import de.nordakademie.nakjava.util.classpathscanner.ClassAcceptor;
import de.nordakademie.nakjava.util.classpathscanner.ClassLookup;
import de.nordakademie.nakjava.util.classpathscanner.ClasspathScanner;

/**
 * Gives access to automatically loaded {@link WinStrategy}s. Thoses instances
 * can be accessed statically by name.
 * 
 */
public class WinStrategies {
	private Map<String, WinStrategy> strategies;
	private Map<String, WinStrategyInformation> strategyInformation;

	private static WinStrategies instance;

	private WinStrategies(List<AbstractWinStrategy> strategies) {
		this.strategies = new HashMap<>();
		this.strategyInformation = new HashMap<>();

		for (AbstractWinStrategy strategy : strategies) {
			String strategyName = strategy.getClass().getSimpleName();
			this.strategies.put(strategyName, strategy);

			StringBuffer description = new StringBuffer();
			description.append("Du hast gewonnen wenn:\n");
			for (WinCheck check : strategy.getChecks()) {
				description.append("- " + check.getDescription() + "\n");
			}

			this.strategyInformation.put(strategyName,
					new WinStrategyInformation(strategyName, description
							.toString(), strategy.getChecks().toArray(
							new WinCheck[strategy.getChecks().size()])));
		}
	}

	public Set<String> getStrategies() {
		return strategies.keySet();
	}

	public WinStrategy getStrategyForName(String name) {
		return strategies.get(name);
	}

	public WinStrategyInformation getStrategyInformationForName(String name) {
		return strategyInformation.get(name);
	}

	public static WinStrategies getInstance() {
		return instance;
	}

	@ClassLookup
	private static void loadWinStrategies() {
		List<Class<AbstractWinStrategy>> classes = ClasspathScanner
				.findClasses(
						"de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies",
						"de.nordakademie.nakjava.gamelogic.winstrategies",
						new ClassAcceptor<AbstractWinStrategy>() {

							@Override
							public boolean acceptClass(
									Class<AbstractWinStrategy> clazz) {
								de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy annotation = clazz
										.getAnnotation(de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy.class);
								return (annotation != null);

							}
						}, AbstractWinStrategy.class);

		List<AbstractWinStrategy> instances = new LinkedList<>();

		for (Class<?> clazz : classes) {
			de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy annotation = clazz
					.getAnnotation(de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.WinStrategy.class);
			AbstractWinStrategy instance = null;
			try {
				instance = (AbstractWinStrategy) clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			List<WinCheck> winChecks = new LinkedList<>();
			for (ArtifactChecker aChecker : annotation.artifactCheckers()) {
				winChecks
						.add(new de.nordakademie.nakjava.gamelogic.stateMachineEvenNewer.winstrategies.genericRoundCheckers.impl.ArtifactChecker(
								aChecker.target(), aChecker.artifacts(),
								aChecker.comparator(), aChecker.count(),
								aChecker.operator()));
			}

			instance.setChecks(winChecks);
			instances.add(instance);
		}

		instance = new WinStrategies(instances);
	}

}
